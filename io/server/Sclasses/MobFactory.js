const Mob = require('./Mob.js');

class MobFactory {
    static createMob(zoneId, x, y) {
        const mobRarity = MobFactory.getMobRarity(zoneId);
        const mobName = MobFactory.getMobName(); // Get the mob name based on the random chance

        // Determine the mob type based on its name and rarity
        let mobType;
        if (mobName === 'Tyrant' && mobRarity === 'common') {
            mobType = 'passive'; // Make common Tyrant a passive mob
        } else {
            mobType = (mobName === 'Tyrant') ? 'aggressive' : 'passive'; // Default behavior for other cases
        }

        return new Mob(zoneId, mobName, mobRarity, x, y, 'enemy', mobType); // Pass the type to the Mob constructor
    }

    static getMobRarity(zoneId) {
        const randomValue = Math.random();

        switch (zoneId) {
            case 'Common':
                return "common"; // Only common mobs can spawn
            case 'Unusual':
                return randomValue < 0.9 ? 'common' : 'unusual'; // 90% common, 10% unusual
            case 'Rare':
                if (randomValue < 0.6) return 'common'; // 60% common
                if (randomValue < 0.95) return 'unusual'; // 35% unusual
                return 'rare'; // 5% rare
            case 'Epic':
                if (randomValue < 0.6) return 'unusual'; // 60% unusual
                if (randomValue < 0.7) return 'common'; // 10% common
                if (randomValue < 0.95) return 'rare'; // 25% rare
                return 'epic'; // 5% epic
            case 'Legendary':
                if (randomValue < 0.5) return 'unusual'; // 50% unusual
                if (randomValue < 0.8) return 'rare'; // 30% rare
                if (randomValue < 0.9) return 'epic'; // 10% epic
                if (randomValue < 0.98) return 'common'; // 8% common
                return 'legendary'; // 2% legendary
            case 'Mythic':
                if (randomValue < 0.25) return 'rare'; // 25% rare
                if (randomValue < 0.5) return 'epic'; // 25% epic
                if (randomValue < 0.75) return 'common'; // 25% common
                if (randomValue < 0.85) return 'unusual'; // 10% unusual
                if (randomValue < 0.97) return 'legendary'; // 12% legendary
                return 'mythic'; // 3% mythic
            case 'Ultra':
                if (randomValue < 0.8) return 'epic'; // 80% epic
                if (randomValue < 0.94) return 'legendary'; // 14% legendary
                if (randomValue < 0.998) return 'mythic'; // 5.9% mythic
                return 'ultra'; // 0.1% ultra
            case 'Topi':
                return randomValue < 0.9998 ? 'common' : 'Topi'; // 99.999% common, 0.01% Topi
            default:
                return 'common'; // Default to common if unknown zone
        }
    }

    static getMobName() {
        const randomValue = Math.random(); // Generate a random number between 0 and 1
        return randomValue < 0.5 ? 'Tyrant' : 'Arcane'; // 50% chance for each name
    }
}

module.exports = MobFactory;