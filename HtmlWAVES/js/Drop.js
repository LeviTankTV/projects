 import {Petal} from "./Petal.js";

export class Drop {
    constructor(x, y, type, rarity, imagePath) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.rarity = rarity;
        this.imagePath = imagePath;
        this.collected = false;
        this.size = 30; // Default drop size

        // Load image
        this.image = new Image();
        this.image.src = this.imagePath;
        this.image.onerror = () => {
            console.error(`Failed to load drop image: ${this.imagePath}`);
        };
    }

    /* checkPlayerCollision(player) {
        if (this.collected) return;

        const dx = this.x - player.x;
        const dy = this.y - player.y;
        const distance = Math.sqrt(dx * dx + dy * dy);

        // Collision detection
        if (distance < 50) { // Adjust collision radius as needed
            this.collected = true;
            console.log(`Collected ${this.rarity} ${this.type} drop`);

            // Create a new Petal from the drop
            const newPetal = this.createPetalFromDrop(player);

            // Try to add to hotbar first
            const addedToHotbar = this.addPetalToHotbar(player, newPetal);

            // If hotbar is full, add to inventory
            if (!addedToHotbar) {
                this.addPetalToInventory(player, newPetal);
            }
        }
    } */

    createPetalFromDrop(player) {
        // You'll need to import or define Petal class
        // This is an example - adjust based on your actual Petal creation logic
        return new Petal(
            this.getRandomColor(),
            30, // default radius
            this.type,
            true,
            null,
            this.rarity
        );
    }

    getRandomColor() {
        const letters = '0123456789ABCDEF';
        let color = '#';
        for (let i = 0; i < 6; i++) {
            color += letters[Math.floor(Math.random() * 16)];
        }
        return color;
    }

    addPetalToHotbar(player, petal) {
        const hotbar = player.hotbar;

        // First, try to add to selected slots (first row)
        const selectedEmptyIndex = hotbar.selectedSlots.findIndex(slot => slot === null);
        if (selectedEmptyIndex !== -1) {
            hotbar.selectedSlots[selectedEmptyIndex] = petal;
            hotbar.updateActivePetals();
            return true;
        }

        // If first row is full, try adding to secondary slots
        const secondaryEmptyIndex = hotbar.secondarySlots.findIndex(slot => slot === null);
        if (secondaryEmptyIndex !== -1) {
            hotbar.secondarySlots[secondaryEmptyIndex] = petal;
            return true;
        }

        return false; // Hotbar is full
    }

    addPetalToInventory(player, petal) {
        const inventory = player.inventory;

        // Try to add to inventory
        const addedToInventory = inventory.addPetal(petal);

        if (addedToInventory) {
            console.log(`Added ${petal.rarity} ${petal.type} petal to inventory`);
        } else {
            console.log('Inventory is full. Cannot add petal.');
        }
    }

    draw(ctx, camera) {
        if (this.collected || !this.image.complete) return;

        ctx.save();

        // Draw drop with camera consideration
        ctx.drawImage(
            this.image,
            this.x - this.size / 2,
            this.y - this.size / 2,
            this.size,
            this.size
        );

        ctx.restore();
    }
}
