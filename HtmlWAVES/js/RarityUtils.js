export class RarityUtils {
    /**
     * Generate a weighted random rarity drop
     * @param {Object} options - Configuration options for rarity generation
     * @param {string[]} [options.baseRarities] - List of possible rarities
     * @param {boolean} [options.excludeCommon=false] - Whether to exclude common rarity
     * @param {number[]} [options.customWeights] - Custom weight distribution
     * @param {string} [currentRarity] - Current rarity of the entity
     * @returns {string} Selected rarity
     */
    static generateDropRarity(options = {}) {
        const {
            baseRarities = ['common', 'uncommon', 'rare', 'epic', 'legendary', 'mythic', 'ultra', 'super'],
            excludeCommon = false,
            customWeights = null,
            currentRarity = null
        } = options;

        const random = Math.random() * 100;

        // Filter rarities based on excludeCommon
        const dropRarities = excludeCommon
            ? baseRarities.filter(r => r !== 'common')
            : baseRarities;

        // Determine possible rarities based on current rarity
        let possibleRarities = dropRarities;
        if (currentRarity) {
            const rarityIndex = dropRarities.indexOf(currentRarity);
            const maxRarityIndex = Math.min(rarityIndex + 2, dropRarities.length - 1);
            possibleRarities = dropRarities.slice(0, maxRarityIndex + 1);
        }

        // Use custom weights if provided, otherwise use default
        const weights = customWeights || possibleRarities.map((_, index) => index + 1);
        const totalWeight = weights.reduce((a, b) => a + b, 0);

        const weightedRandom = random * totalWeight / 100;
        let cumulativeWeight = 0;

        for (let i = 0; i < possibleRarities.length; i++) {
            cumulativeWeight += weights[i];
            if (weightedRandom <= cumulativeWeight) {
                return possibleRarities[i];
            }
        }

        return null;
    }

    /**
     * Convenience method for generating drop rarity for an entity with a current rarity
     * @param {Object} entity - Entity with a rarity property
     * @param {Object} [options] - Additional generation options
     * @returns {string} Selected rarity
     */
    static generateEntityDropRarity(entity, options = {}) {
        return this.generateDropRarity({
            ...options,
            currentRarity: entity.rarity
        });
    }
}