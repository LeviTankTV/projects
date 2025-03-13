import { Mob } from "./Mob.js";

export class RockMob extends Mob {
    constructor(x, y, rarity) {
        super(x, y, rarity, 'Rock');

        // Override behavior to be stationary
        this.behavior = 'stationary';

        // Rock Mob specific health based on rarity
        this.healthByRarity = {
            'common': 500,
            'uncommon': 1000,
            'rare': 2000,
            'epic': 4000,
            'legendary': 8000,
            'mythic': 16000,
            'ultra': 32000,
            'super': 64000
        };

        // Override health
        this.health = this.healthByRarity[rarity] || 2000;
        this.maxHealth = this.health;

        // Adjust size for rarities
        const sizeMultipliers = {
            'common': 1,
            'uncommon': 1.2,
            'rare': 1.4,
            'epic': 1.6,
            'legendary': 1.8,
            'mythic': 2,
            'ultra': 2.5,
            'super': 3
        };
        this.size *= sizeMultipliers[rarity] || 1;

        // Extremely low movement speed to simulate being immovable
        this.speed = 0.1;

        // Rock Mob image
        this.image = new Image();
        this.image.src = 'res/RockMob.png';

        // Prevent easy pushing
        this.mass = 10000; // Extremely high mass
    }

    update(player, arena, otherMobs) {
        // Stationary mob, minimal update needed
        // Prevent movement
        this.x = this.x;
        this.y = this.y;
        if (otherMobs) {
            for (const otherMob of otherMobs) {
                if (otherMob === this || otherMob.isDead) continue;

                const dx = otherMob.x - this.x;
                const dy = otherMob.y - this.y;
                const distance = Math.sqrt(dx * dx + dy * dy);
                const minDistance = this.size + otherMob.size;

                // If another mob is too close, push it away
                if (distance < minDistance) {
                    const pushAngle = Math.atan2(dy, dx);
                    const overlapFactor = minDistance - distance;

                    // Push the other mob away from the rock
                    otherMob.x += Math.cos(pushAngle) * overlapFactor;
                    otherMob.y += Math.sin(pushAngle) * overlapFactor;
                }
            }
        }
    }

    die() {
        if (!this.isDead) {
            console.log(`Die method called for ${this.constructor.name} with rarity ${this.rarity}`);
            super.die(); // Call parent method

            if (this.waveManager) {
                const random = Math.random() * 100;

                // Probability matrix for drops based on rarity
                const dropChances = {
                    'common':     { nothing: 60, rockOnly: 30, heavyOnly: 10 },
                    'uncommon':   { nothing: 50, rockOnly: 40, heavyOnly: 10 },
                    'rare':       { nothing: 30, rockOnly: 30, heavyOnly: 40 },
                    'epic':       { nothing: 20, rockOnly: 20, heavyOnly: 60 },
                    'legendary':  { nothing: 10, rockOnly: 10, heavyOnly: 80 },
                    'mythic':     { nothing: 5, rockOnly: 5, heavyOnly: 90 },
                    'ultra':      { nothing: 2, rockOnly: 3, heavyOnly: 95 },
                    'super':      { nothing: 1, rockOnly: 2, heavyOnly: 97 }
                };

                const chances = dropChances[this.rarity];
                const dropType = this.calculateDropType(random, chances);

                let rockDropped = false;
                let heavyDropped = false;

                switch(dropType) {
                    case 'rockOnly':
                        this.spawnRockDrop();
                        rockDropped = true;
                        break;
                    case 'heavyOnly':
                        this.spawnHeavyDrop();
                        heavyDropped = true;
                        break;
                }

                // Create visual arrangement for drops
                this.arrangeDrops(rockDropped, heavyDropped);
            } else {
                console.error('No WaveManager set for mob');
            }
        }
    }

    generateDropRarity(options = {}) {
        const {
            baseRarities = ['rare', 'epic', 'legendary', 'mythic', 'ultra', 'super'],
            customWeights = null
        } = options;

        const random = Math.random() * 100;

        // Adjust probabilities based on the mob's rarity
        const rarityIndex = baseRarities.indexOf(this.rarity);
        const maxRarityIndex = Math.min(rarityIndex + 2, baseRarities.length - 1);

        const possibleRarities = baseRarities.slice(0, maxRarityIndex + 1);

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

    generateRockDrop() {
        return this.generateDropRarity();
    }

    generateHeavyDrop() {
        return this.generateDropRarity();
    }

    arrangeDrops(rockDropped, heavyDropped) {
        const dropPositions = [];

        if (rockDropped) {
            dropPositions.push({ x: this.x, y: this.y });
        }
        if (heavyDropped) {
            dropPositions.push({ x: this.x + 40, y: this.y }); // Offset for heavy drop
        }

        // Now spawn the drops at their respective positions
        dropPositions.forEach((pos, index) => {
            const type = index === 0 ? 'rock' : 'heavy';
            const rarity = index === 0 ? this.generateRockDrop() : this.generateHeavyDrop();
            this.waveManager.spawnDrop({
                x: pos.x,
                y: pos.y,
                type: type,
                rarity: rarity,
                imagePath: `/res/${rarity}${type}.png`
            });
        });
    }

    calculateDropType(random, chances) {
        let cumulative = 0;
        for (const [type, chance] of Object.entries(chances)) {
            cumulative += chance;
            if (random < cumulative) return type;
        }
        return 'nothing'; // Fallback
    }

    spawnRockDrop() {
        const rockDrop = this.generateRockDrop() || 'rare';
        this.waveManager.spawnDrop({
            x: this.x,
            y: this.y,
            type: 'rock',
            rarity: rockDrop,
            imagePath: `/res/${rockDrop}rock.png`
        });
    }

    spawnHeavyDrop() {
        const heavyDrop = this.generateHeavyDrop() || 'rare';
        this.waveManager.spawnDrop({
            x: this.x,
            y: this.y,
            type: 'heavy',
            rarity: heavyDrop,
            imagePath: `/res/${heavyDrop}heavy.png`
        });
    }

    draw(ctx) {
        if (this.isDead) {
            super.draw(ctx);
            return;
        }

        ctx.save();
        ctx.translate(this.x, this.y);

        // Draw rock mob image
        if (this.image.complete) {
            ctx.drawImage(
                this.image,
                -this.size,
                -this.size,
                this.size * 2,
                this.size * 2
            );
        }

        ctx.restore();

        // Draw health bar and name
        this.drawHealthBar(ctx);
        this.drawName(ctx);
    }
}