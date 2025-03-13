import {Petal} from "./Petal.js";

export class PlayerInventory {
    constructor(player, config = {}) {
        this.player = player;

        // Inventory Configuration
        this.maxSlots = config.maxSlots || 100;
        this.currentSlots = [];

        // Inventory UI State
        this.isOpen = false;
        this.iconPosition = {
            x: 50,  // Configurable X position
            y: 50,  // Configurable Y position
            width: 40,
            height: 40
        };

        this.petalStats = {
            totalDamage: 0,
            totalHP: 0,
            rarityDistribution: {}
        };

        // Petal Sorting and Filtering
        this.sortOptions = [
            'rarity',
            'type',
            'level'
        ];
        this.currentSort = 'rarity';

        // Inventory Filtering
        this.filters = {
            rarity: null,
            type: null
        };
        this.isViewingInventory = false;
    }

    // Add a petal to inventory
    addPetal(petal) {
        // Check if inventory is full
        if (this.currentSlots.length >= this.maxSlots) {
            console.log('Inventory is full!');
            return false;
        }

        // Check if hotbar is full before adding to inventory
        const hotbar = this.player.hotbar;
        const isHotbarFull = hotbar.selectedSlots.every(slot => slot !== null) &&
            hotbar.secondarySlots.every(slot => slot !== null);

        if (isHotbarFull) {
            this.currentSlots.push(petal);
            this.sortInventory();
            return true;
        }

        // Try to add to hotbar first
        const addedToHotbar = hotbar.addPetal(petal);
        if (addedToHotbar) {
            return true;
        }

        if (this.currentSlots.length >= this.maxSlots) {
            console.log('Inventory is full!');
            return false;
        }

        // If hotbar is full, add to inventory
        this.currentSlots.push(petal);
        this.sortInventory();
        return true;
    }

    // Remove a petal from inventory
    removePetal(petal) {
        const index = this.currentSlots.indexOf(petal);
        if (index !== -1) {
            // Update petal stats tracking
            this.updatePetalStats(petal, 'remove');

            this.currentSlots.splice(index, 1);

            // Trigger remove effect if applicable
            petal.onRemoveFromPlayer(this.player);

            return true;
        }
        return false;
    }

    updatePetalStats(petal, action) {
        const multiplier = action === 'add' ? 1 : -1;

        // Update total damage
        this.petalStats.totalDamage += petal.damage * multiplier;

        // Update total HP
        this.petalStats.totalHP += petal.maxHP * multiplier;

        // Update rarity distribution
        if (!this.petalStats.rarityDistribution[petal.rarity]) {
            this.petalStats.rarityDistribution[petal.rarity] = 0;
        }
        this.petalStats.rarityDistribution[petal.rarity] += 1 * multiplier;
    }

    // Sort inventory based on current sort option
    sortInventory() {
        switch(this.currentSort) {
            case 'rarity':
                this.currentSlots.sort((a, b) => {
                    const rarityOrder = [
                        'common', 'uncommon', 'rare',
                        'epic', 'legendary', 'mythic',
                        'ultra', 'super'
                    ];
                    return rarityOrder.indexOf(a.rarity) -
                        rarityOrder.indexOf(b.rarity);
                });
                break;
            case 'type':
                this.currentSlots.sort((a, b) =>
                    a.type.localeCompare(b.type)
                );
                break;
        }
    }

    // Filter inventory
    filterInventory(options) {
        this.filters = { ...this.filters, ...options };
        return this.currentSlots.filter(petal =>
            (!this.filters.rarity || petal.rarity === this.filters.rarity) &&
            (!this.filters.type || petal.type === this.filters.type)
        );
    }

    // Draw inventory icon
    drawIcon(ctx) {
        ctx.save();

        // Background
        ctx.fillStyle = 'rgba(50, 50, 50, 0.7)';
        ctx.beginPath();

        // Use .roundRect with number array for borderRadius
        ctx.roundRect(
            this.iconPosition.x,
            this.iconPosition.y,
            this.iconPosition.width,
            this.iconPosition.height,
            [5]
        );
        ctx.fill();

        // Inventory icon (simplified bag/chest representation)
        ctx.fillStyle = 'white';
        ctx.font = '24px Arial';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.fillText('🎒',
            this.iconPosition.x + this.iconPosition.width / 2,
            this.iconPosition.y + this.iconPosition.height / 2
        );

        // Petal count badge
        if (this.currentSlots.length > 0) {
            ctx.fillStyle = 'red';
            ctx.beginPath();
            ctx.arc(
                this.iconPosition.x + this.iconPosition.width - 10,
                this.iconPosition.y + 10,
                10, 0, Math.PI * 2
            );
            ctx.fill();

            ctx.fillStyle = 'white';
            ctx.font = 'bold 12px Arial';
            ctx.textAlign = 'center';
            ctx.textBaseline = 'middle';
            ctx.fillText(
                this.currentSlots.length.toString(),
                this.iconPosition.x + this.iconPosition.width - 10,
                this.iconPosition.y + 10
            );
        }

        ctx.restore();
    }

    // Draw full inventory view
    drawInventoryView(ctx, canvasWidth, canvasHeight) {
        if (!this.isViewingInventory) return;

        // Slightly transparent overlay
        ctx.fillStyle = 'rgba(0, 0, 0, 0.5)';
        ctx.fillRect(0, 0, canvasWidth, canvasHeight);

        // Inventory window
        const windowWidth = canvasWidth * 0.8;
        const windowHeight = canvasHeight * 0.8;
        const windowX = (canvasWidth - windowWidth) / 2;
        const windowY = (canvasHeight - windowHeight) / 2;

        ctx.fillStyle = 'rgba(50, 50, 50, 0.9)';
        ctx.beginPath();
        ctx.roundRect(windowX, windowY, windowWidth, windowHeight, 10);
        ctx.fill();

        // Title
        ctx.fillStyle = 'white';
        ctx.font = 'bold 24px Arial';
        ctx.textAlign = 'center';
        ctx.fillText('Inventory', canvasWidth / 2, windowY + 50);

        // Draw grid of petals (similar to previous implementation)
        const gridColumns = 10;
        const cellSize = 60;
        const gridPadding = 20;

        this.currentSlots.forEach((petal, index) => {
            const row = Math.floor(index / gridColumns);
            const col = index % gridColumns;

            const cellX = windowX + gridPadding + col * (cellSize + 10);
            const cellY = windowY + 100 + row * (cellSize + 10);

            // Cell background
            ctx.fillStyle = this.getSlotBackgroundColor(petal.rarity);
            ctx.beginPath();
            ctx.roundRect(cellX, cellY, cellSize, cellSize, 5);
            ctx.fill();

            // Petal representation
            ctx.fillStyle = this.getPetalColor(petal.rarity);
            ctx.beginPath();
            ctx.arc(
                cellX + cellSize / 2,
                cellY + cellSize / 2,
                cellSize / 3,
                0, Math.PI * 2
            );
            ctx.fill();

            // Petal details
            ctx.fillStyle = 'white';
            ctx.font = '10px Arial';
            ctx.textAlign = 'center';
            ctx.fillText(petal.type, cellX + cellSize / 2, cellY + cellSize - 10);
            ctx.fillText(petal.rarity, cellX + cellSize / 2, cellY + cellSize - 20);
        });
    }
    getSlotBackgroundColor(rarity) {
        const rarityColors = {
            'common': 'rgba(200, 200, 200, 0.5)',
            'uncommon': 'rgba(0, 255, 0, 0.3)',
            'rare': 'rgba(0, 0, 255, 0.3)',
            'epic': 'rgba(128, 0, 128, 0.3)',
            'legendary': 'rgba(255, 215, 0, 0.3)',
            'mythic': 'rgba(255, 0, 0, 0.3)'
        };
        return rarityColors[rarity] || 'rgba(100, 100, 100, 0.3)';
    }

    getPetalColor(rarity) {
        const rarityColors = {
            'common': 'gray',
            'uncommon': 'green',
            'rare': 'blue',
            'epic': 'purple',
            'legendary': 'gold',
            'mythic': 'red'
        };
        return rarityColors[rarity] || 'black';
    }

    // Handle mouse interactions
    handleMouseClick(mouseX, mouseY) {
        // Check inventory icon click
        if (this.isPointInRect(mouseX, mouseY, this.iconPosition)) {
            this.isViewingInventory = !this.isViewingInventory;
            return true;
        }

        // If inventory view is open, handle petal interactions
        if (this.isViewingInventory) {
            return this.handleInventoryInteraction(mouseX, mouseY);
        }

        return false;
    }
    handleInventoryInteraction(mouseX, mouseY) {
        const canvasWidth = window.innerWidth;
        const canvasHeight = window.innerHeight;

        const windowWidth = canvasWidth * 0.8;
        const windowHeight = canvasHeight * 0.8;
        const windowX = (canvasWidth - windowWidth) / 2;
        const windowY = (canvasHeight - windowHeight) / 2;

        const gridColumns = 10;
        const cellSize = 60;
        const gridPadding = 20;

        // Check each petal slot
        for (let i = 0; i < this.currentSlots.length; i++) {
            const row = Math.floor(i / gridColumns);
            const col = i % gridColumns;

            const cellX = windowX + gridPadding + col * (cellSize + 10);
            const cellY = windowY + 100 + row * (cellSize + 10);

            // Check if clicked within a petal slot
            if (this.isPointInRect(mouseX, mouseY, {
                x: cellX,
                y: cellY,
                width: cellSize,
                height: cellSize
            })) {
                return this.handlePetalSlotClick(this.currentSlots[i]);
            }
        }

        return false;
    }

    handlePetalSlotClick(petal) {
        const hotbar = this.player.hotbar;

        // Try to add petal to hotbar
        if (hotbar.addPetal(petal)) {
            // If successfully added to hotbar, remove from inventory
            this.removePetal(petal);
            return true;
        }

        // Optional: Show petal details or provide feedback
        this.showPetalDetails(petal);
        return false;
    }

    showPetalDetails(petal) {
        // Lightweight petal details display
        console.log('Petal Details:', {
            type: petal.type,
            rarity: petal.rarity,
            damage: petal.damage,
            maxHP: petal.maxHP
        });
        // Could implement a small overlay or tooltip
    }

    isPointInRect(x, y, rect) {
        return x >= rect.x &&
            x <= rect.x + rect.width &&
            y >= rect.y &&
            y <= rect.y + rect.height;
    }

    // Serialization for saving/loading
    serialize() {
        return {
            slots: this.currentSlots.map(petal => ({
                color: petal.color,
                radius: petal.radius,
                type: petal.type,
                rarity: petal.rarity,
                damage: petal.damage,
                maxHP: petal.maxHP
            })),
            maxSlots: this.maxSlots,
            petalStats: this.petalStats
        };
    }

    deserialize(data) {
        this.maxSlots = data.maxSlots;
        this.petalStats = data.petalStats || {
            totalDamage: 0,
            totalHP: 0,
            rarityDistribution: {}
        };

        this.currentSlots = data.slots.map(petalData =>
            new Petal(
                petalData.color,
                petalData.radius,
                petalData.type,
                true,  // Assume player-controlled when deserializing
                null,
                petalData.rarity
            )
        );
    }

    combinePetals(petal1, petal2) {
        // Ensure petals are of same type
        if (petal1.type !== petal2.type) {
            console.log('Cannot combine different petal types');
            return null;
        }

        // Rarity progression
        const rarityProgression = [
            'common', 'uncommon', 'rare',
            'epic', 'legendary', 'mythic'
        ];

        const currentRarityIndex = rarityProgression.indexOf(petal1.rarity);
        const newRarityIndex = Math.min(currentRarityIndex + 1, rarityProgression.length - 1);
        const newRarity = rarityProgression[newRarityIndex];

        // Create new combined petal
        const combinedPetal = new Petal(
            petal1.color,  // Use first petal's color as base
            petal1.radius * 1.1,  // Slightly larger radius
            petal1.type,
            true,  // Assume player-controlled
            null,  // No parent enemy
            newRarity  // New rarity
        );

        // Remove original petals
        this.removePetal(petal1);
        this.removePetal(petal2);

        // Add new combined petal
        this.addPetal(combinedPetal);

        return combinedPetal;
    }
}