import {Petal} from "./Petal.js";
import {BasicPetal} from "./Basic.js";
export class PetalManager {
    constructor(isPlayerControlled = true) {
        this.petals = this.createPetals(isPlayerControlled);
        this.commonPetals = this.petals.slice(0, 10); // First 10 petals as common petals
        this.selectedPetals = this.commonPetals.slice(); // Start with common petals in the hotbar
        this.isOffensiveMode = false;
    }
    createBasicPetals(isPlayerControlled) {
        return Array.from({length: 20}, (_, i) => {
            const rarity = i < 10 ? 'common' :
                i < 15 ? 'uncommon' :
                    i < 18 ? 'rare' :
                        i < 19 ? 'epic' : 'legendary';
            return new BasicPetal(rarity);
        });
    }

    createPetals(isPlayerControlled) {
        const petalTypes = [
            { color: '#FF6B6B', type: 'attack' },
            { color: '#4ECDC4', type: 'defense' },
            { color: '#45B7D1', type: 'healing' },
            { color: '#FDCB6E', type: 'support' },
            { color: '#6C5CE7', type: 'magic' },
            { color: '#A3CB38', type: 'nature' },
            { color: '#FAD02E', type: 'light' },
            { color: '#FF7F50', type: 'fire' },
            { color: '#6A5ACD', type: 'water' },
            { color: '#FF69B4', type: 'wind' }
        ];



        return petalTypes.map(type =>
            new Petal(type.color, 5, type.type, isPlayerControlled)
        );
    }

    movePetal(fromIndex, toIndex) {
        if (fromIndex >= 0 && fromIndex < this.petals.length &&
            toIndex >= 0 && toIndex < this.petals.length) {

            // Swap petals
            const temp = this.petals[fromIndex];
            this.petals[fromIndex] = this.petals[toIndex];
            this.petals[toIndex] = temp;

            // Update selected petals (first 10 slots)
            this.selectedPetals = this.petals.slice(0, 10);
        }
    }

    updateHotbarSelection(selectedSlots) {
        // Reset selected petals
        this.selectedPetals = [];

        this.petals.forEach((petal, index) => {
            // Only petals in the first row are selectable
            if (selectedSlots[index] && index < 10) {
                petal.isSelected = true; // Mark as selected
                this.selectedPetals.push(petal); // Add to selected petals
            } else {
                petal.isSelected = false; // Deselect
            }
        });
    }

    updatePetals(playerState, playerX, playerY) {
        // Update only active petals from the first row (first 10 slots)
        this.selectedPetals.slice(0, 10).forEach(petal => {
            if (petal) {
                petal.update(playerState, playerX, playerY);
            }
        });
    }

    drawPetals(ctx, playerX, playerY) {
        // Draw only active petals from the first row (first 10 slots)
        this.selectedPetals.slice(0, 10).forEach(petal => {
            if (petal) {
                petal.draw(ctx);
            }
        });
    }

    renderHotbar(ctx, canvasWidth, canvasHeight) {
        const hotbarHeight = 160; // Doubled height for two rows
        const hotbarY = canvasHeight - hotbarHeight;
        const squareSize = 60;
        const spacing = 10;
        const totalWidth = 5 * (squareSize + spacing);
        const startX = (canvasWidth - totalWidth) / 2;

        // First row (active petals)
        for (let i = 0; i < 5; i++) {
            const x = startX + i * (squareSize + spacing);

            // Background square
            ctx.fillStyle = '#f0f0f0';
            ctx.fillRect(x, hotbarY, squareSize, squareSize);

            // Petal color indicator
            ctx.fillStyle = this.petals[i].color;
            ctx.beginPath();
            ctx.arc(
                x + squareSize / 2,
                hotbarY + squareSize / 2,
                20,
                0,
                Math.PI * 2
            );
            ctx.fill();
        }

        // Second row (inactive petals)
        const secondRowY = hotbarY + squareSize + spacing;
        for (let i = 5; i < 10; i++) {
            const x = startX + (i - 5) * (squareSize + spacing);

            // Background square (grayed out)
            ctx.fillStyle = '#d0d0d0';
            ctx.fillRect(x, secondRowY, squareSize, squareSize);

            // Petal color indicator (more transparent)
            ctx.fillStyle = `${this.petals[i].color}80`;
            ctx.beginPath();
            ctx.arc(
                x + squareSize / 2,
                secondRowY + squareSize / 2,
                20,
                0,
                Math.PI * 2
            );
            ctx.fill();
        }
    }
}