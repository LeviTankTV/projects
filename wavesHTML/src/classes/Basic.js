import { Petal } from "./Petal.js";

export class BasicPetal extends Petal {
    constructor(rarity = 'common') {
        // Always white color for BasicPetal
        const color = '#FFFFFF';
        // Set radius based on rarity
        const radius = BasicPetal.getRadiusByRarity(rarity);
        super(color, radius, 'basic', true); // Call the parent constructor
        this.rarity = rarity; // Store rarity for future reference

        // Set a default damage value based on rarity
        this.damage = this.getDamageByRarity(rarity);
    }

    static getRadiusByRarity(rarity) {
        const rarityRadii = {
            common: 5,
            uncommon: 7,
            rare: 9,
            epic: 11,
            legendary: 13,
            mythic: 15,
            ultra: 30
        };
        return rarityRadii[rarity] || 5; // Default to common radius if rarity not found
    }

    getDamageByRarity(rarity) {
        const damageValues = {
            common: 5,
            uncommon: 15,
            rare: 50,
            epic: 100,
            legendary: 250,
            mythic: 1000,
            ultra: 10000
        };
        return damageValues[rarity] || 5; // Default to common damage if rarity not found
    }
}
