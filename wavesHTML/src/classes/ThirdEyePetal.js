import { Petal } from "./Petal.js";

export class ThirdEyePetal extends Petal {
    constructor(rarity = 'legendary') {
        // Custom color and other properties for Third Eye Petal
        super('#000000', 10, 'ThirdEye', false, null, rarity);

        // Define effect methods
        this.applyPlayerEffect = (player) => {
            // Store original properties to restore later
            player.originalSkin = player.skin.src;
            player.originalPetalOrbitRadius = player.petalOrbitRadius;

            // Set petal orbit radius based on rarity
            let additionalRadius = 0;
            switch (this.rarity) {
                case 'legendary':
                    additionalRadius = 75;
                    break;
                case 'mythic':
                    additionalRadius = 150;
                    break;
                case 'epic':
                    additionalRadius = 50;
                    break;
                case 'ultra':
                    additionalRadius = 200;
                    break;
                default:
                    additionalRadius = 0; // No additional radius for other rarities
                    break;
            }

            // Increase petal orbit radius
            player.petalOrbitRadius += additionalRadius;

            console.log(`Third Eye Petal Effect Applied: ${this.rarity} - Additional Radius: ${additionalRadius}`);
        };

        this.removePlayerEffect = (player) => {
            // Restore original skin
            if (player.originalSkin) {
                player.skin.src = player.originalSkin;
            }

            // Restore original petal orbit radius
            if (player.originalPetalOrbitRadius) {
                player.petalOrbitRadius = player.originalPetalOrbitRadius;
            }

            console.log('Third Eye Petal Effect Removed');
        };
    }
}