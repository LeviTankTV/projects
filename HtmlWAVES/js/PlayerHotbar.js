 export class PlayerHotbar {
    constructor(player, config = {}) {
        this.player = player;

        // Hotbar Configuration
        this.slotCount = config.slotCount || 20;
        this.rowCount = 2;
        this.slotsPerRow = this.slotCount / this.rowCount;

        // Styling
        this.slotWidth = config.slotWidth || 50;
        this.slotHeight = config.slotHeight || 50;
        this.slotSpacing = config.slotSpacing || 10;

        // Drag and Drop State
        this.draggedPetal = null;
        this.draggedFromRow = null;
        this.draggedFromIndex = -1;

        // Slots Management
        this.selectedSlots = new Array(this.slotsPerRow).fill(null);
        this.secondarySlots = new Array(this.slotsPerRow).fill(null);
        this.mouseX = 0;
        this.mouseY = 0;
        // Initial Population
        this.populateInitialSlots();
        this.petalRenderCache = new Map();

        // Performance optimization for drawing
        this.lastDrawTimestamp = 0;
        this.drawThrottleInterval = 16; // ~60 FPS
        this.addPetal = this.addPetal.bind(this);
        this.removePetal = this.removePetal.bind(this);
    }



    populateInitialSlots() {
        // Populate first row with initial petals
        const initialPetals = this.player.selectedPetals.slice(0, this.slotsPerRow);
        initialPetals.forEach((petal, index) => {
            this.selectedSlots[index] = petal;
        });
    }

    draw(ctx, canvasWidth, canvasHeight) {
        const currentTime = Date.now();

        // Throttle drawing to reduce performance overhead
        if (currentTime - this.lastDrawTimestamp < this.drawThrottleInterval) {
            return;
        }
        this.lastDrawTimestamp = currentTime;

        const startY = canvasHeight - (this.slotHeight + this.slotSpacing) * this.rowCount;

        // Use a single path for drawing to reduce draw calls
        ctx.beginPath();

        // Draw Selected Slots (First Row)
        this.drawRow(ctx, this.selectedSlots, canvasWidth, startY, 'selected');

        // Draw Secondary Slots (Second Row)
        this.drawRow(ctx, this.secondarySlots, canvasWidth, startY + this.slotHeight + this.slotSpacing, 'secondary');

        // Draw dragged petal
        if (this.draggedPetal) {
            this.drawDraggedPetal(ctx, this.draggedPetal);
        }
    }

    drawRow(ctx, slots, canvasWidth, startY, rowType) {
        const totalWidth = this.slotWidth * slots.length + this.slotSpacing * (slots.length - 1);
        const startX = (canvasWidth - totalWidth) / 2;

        slots.forEach((petal, index) => {
            const x = startX + index * (this.slotWidth + this.slotSpacing);

            // Simplified slot drawing
            ctx.fillStyle = petal
                ? this.getSlotBackgroundColor(petal.rarity)
                : 'rgba(100, 100, 100, 0.3)';
            ctx.fillRect(x, startY, this.slotWidth, this.slotHeight);

            // Simplified petal drawing
            if (petal && petal !== this.draggedPetal) {
                this.drawPetalInSlot(ctx, petal, x, startY, index, rowType);
            }
        });
    }

    activateBeetleEgg(beetleEgg) {
        // This method will be called when the egg is selected/activated from the hotbar
        if (beetleEgg && !beetleEgg.beetlePet) {
            const player = this.player; // Assuming the hotbar has a reference to the player
            const beetlePet = beetleEgg.summonBeetlePet(player);

            if (beetlePet) {
                // Add the beetle pet to the game world
                player.game.waveManager.addMob(beetlePet);

                // Optional: Remove the egg from hotbar or mark as used
                this.removePetal(beetleEgg);
            }
        }
    }

    drawPetalInSlot(ctx, petal, x, y, index, rowType) {
        const centerX = x + this.slotWidth / 2;
        const centerY = y + this.slotHeight / 2;

        // Cache color to reduce computation
        const petalColor = this.getPetalColor(petal.rarity);

        ctx.fillStyle = petalColor;
        ctx.beginPath();
        ctx.arc(centerX, centerY, this.slotWidth / 3, 0, Math.PI * 2);
        ctx.fill();

        // Minimal text rendering
        ctx.fillStyle = 'white';
        ctx.font = '8px Arial';
        ctx.textAlign = 'center';
        ctx.fillText(petal.type.substring(0, 3), centerX, centerY);
    }

    drawDraggedPetal(ctx, petal) {
        if (!this.draggedPetal) return;

        const mousePos = this.getMousePosition();
        ctx.fillStyle = this.getPetalColor(petal.rarity);
        ctx.beginPath();
        ctx.arc(mousePos.x, mousePos.y, this.slotWidth / 3, 0, Math.PI * 2);
        ctx.fill();

        // Optional: Add text for petal type and rarity
        ctx.fillStyle = 'white';
        ctx.font = '8px Arial';
        ctx.textAlign = 'center';
        ctx.fillText(petal.type, mousePos.x, mousePos.y + this.slotWidth / 4);
        ctx.fillText(petal.rarity, mousePos.x, mousePos.y - this.slotWidth / 4);
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

    handleMouseDown(mouseX, mouseY, canvasWidth, canvasHeight) {
        const startY = canvasHeight - (this.slotHeight + this.slotSpacing) * this.rowCount;

        // Check Selected Slots
        const selectedIndex = this.checkRowClick(
            mouseX, mouseY,
            this.selectedSlots,
            canvasWidth,
            startY
        );

        // Check Secondary Slots
        const secondaryIndex = this.checkRowClick(
            mouseX, mouseY,
            this.secondarySlots,
            canvasWidth,
            startY + this.slotHeight + this.slotSpacing
        );

        // Start drag if a petal is found
        if (selectedIndex !== -1) {
            this.draggedPetal = this.selectedSlots[selectedIndex];
            this.draggedFromRow = 'selected';
            this.draggedFromIndex = selectedIndex;
        } else if (secondaryIndex !== -1) {
            this.draggedPetal = this.secondarySlots[secondaryIndex];
            this.draggedFromRow = 'secondary';
            this.draggedFromIndex = secondaryIndex;
        }
    }

    handleMouseUp(mouseX, mouseY, canvasWidth, canvasHeight) {
        if (!this.draggedPetal) return;

        const startY = canvasHeight - (this.slotHeight + this.slotSpacing) * this.rowCount;

        // Check Selected Slots
        const selectedIndex = this.checkRowClick(
            mouseX, mouseY,
            this.selectedSlots,
            canvasWidth,
            startY
        );

        // Check Secondary Slots
        const secondaryIndex = this.checkRowClick(
            mouseX, mouseY,
            this.secondarySlots,
            canvasWidth,
            startY + this.slotHeight + this.slotSpacing
        );

        // Swap or move petals with new logic
        if (selectedIndex !== -1 && selectedIndex < this.slotsPerRow) {
            if (this.draggedFromRow === 'selected') {
                // Swap within selected row
                this.swapPetals('selected', this.draggedFromIndex, 'selected', selectedIndex);
            } else if (this.draggedFromRow === 'secondary') {
                // Move from secondary to selected ONLY if within first 10 slots
                this.movePetal('secondary', this.draggedFromIndex, 'selected', selectedIndex);
            }
        } else if (secondaryIndex !== -1) {
            if (this.draggedFromRow === 'selected') {
                // Move from selected to secondary
                this.movePetal('selected', this.draggedFromIndex, 'secondary', secondaryIndex);
            } else if (this.draggedFromRow === 'secondary') {
                // Swap within secondary row
                this.swapPetals('secondary', this.draggedFromIndex, 'secondary', secondaryIndex);
            }
        }

        // Reset drag state
        this.resetDragState();
    }

    movePetal(sourceRow, sourceIndex, targetRow, targetIndex) {
        const sourceSlots = sourceRow === 'selected' ? this.selectedSlots : this.secondarySlots;
        const targetSlots = targetRow === 'selected' ? this.selectedSlots : this.secondarySlots;

        // If target slot is empty, move the petal
        if (targetSlots[targetIndex] === null) {
            targetSlots[targetIndex] = sourceSlots[sourceIndex];
            sourceSlots[sourceIndex] = null;

            // Only update active petals if moving to/from selected row
            if (targetRow === 'selected' || sourceRow === 'selected') {
                this.updateActivePetals();
            }
        }
    }

    swapPetals(sourceRow, sourceIndex, targetRow, targetIndex) {
        const sourceSlots = sourceRow === 'selected' ? this.selectedSlots : this.secondarySlots;
        const targetSlots = targetRow === 'selected' ? this.selectedSlots : this.secondarySlots;

        // Swap petals between slots
        const temp = sourceSlots[sourceIndex];
        sourceSlots[sourceIndex] = targetSlots[targetIndex];
        targetSlots[targetIndex] = temp;

        this.updateActivePetals();
    }

    resetDragState() {
        this.draggedPetal = null;
        this.draggedFromRow = null;
        this.draggedFromIndex = -1;
    }

    getMousePosition() {
        return {
            x: this.mouseX || 0,
            y: this.mouseY || 0
        };
    }

    updateMousePosition(x, y) {
        this.mouseX = x;
        this.mouseY = y;
    }

    checkRowClick(mouseX, mouseY, slots, canvasWidth, startY) {
        const totalWidth = this.slotWidth * slots.length + this.slotSpacing * (slots.length - 1);
        const startX = (canvasWidth - totalWidth) / 2;

        for (let i = 0; i < slots.length; i++) {
            const x = startX + i * (this.slotWidth + this.slotSpacing);

            if (
                mouseX >= x &&
                mouseX <= x + this.slotWidth &&
                mouseY >= startY &&
                mouseY <= startY + this.slotHeight
            ) {
                return i;
            }
        }
        return -1;
    }

    updateActivePetals() {
        // Only petals in the first row (first 10 slots) will be active and selected
        this.player.selectedPetals = this.selectedSlots
            .filter(slot => slot !== null)
            .slice(0, this.slotsPerRow);  // Limit to first row
    }

// Method to add a new petal to the hotbar
    addPetal(petal) {
        // First, try to add to selected slots (first row)
        const selectedEmptyIndex = this.selectedSlots.findIndex(slot => slot === null);
        if (selectedEmptyIndex !== -1 && selectedEmptyIndex < this.slotsPerRow) {
            this.selectedSlots[selectedEmptyIndex] = petal;
            this.updateActivePetals();
            return true;
        }

        // If first row is full, try adding to secondary slots
        const secondaryEmptyIndex = this.secondarySlots.findIndex(slot => slot === null);
        if (secondaryEmptyIndex !== -1) {
            this.secondarySlots[secondaryEmptyIndex] = petal;
            return true;
        }

        return false; // Hotbar is full
    }

    // Method to remove a petal from the hotbar
    removePetal(petal) {
        const selectedIndex = this.selectedSlots.indexOf(petal);
        if (selectedIndex !== -1) {
            this.selectedSlots[selectedIndex] = null;
            this.updateActivePetals();
            return true;
        }

        const secondaryIndex = this.secondarySlots.indexOf(petal);
        if (secondaryIndex !== -1) {
            this.secondarySlots[secondaryIndex] = null;
            this.updateActivePetals();
            return true;
        }

        return false;
    }
}