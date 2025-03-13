import { Mob } from "./Mob.js";

export class WorkerAnt extends Mob {
    constructor(x, y, rarity, arena = null) {
        super(x, y, rarity, 'Worker Ant');

        // Worker Ant-specific health based on rarity
        this.healthByRarity = {
            'common': 100,
            'uncommon': 200,
            'rare': 400,
            'epic': 800,
            'legendary': 1600,
            'mythic': 3200,
            'ultra': 6400,
            'super': 12800
        };

        // Size multipliers for different rarities
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

        // Worker Ant-specific attributes
        this.size *= sizeMultipliers[rarity] || 1;
        this.health = this.healthByRarity[rarity] || 400;
        this.maxHealth = this.health;
        this.speed = this.calculateWorkerAntSpeed(rarity);

        // Behavior states
        this.behavior = 'neutral';
        this.isAggro = false;
        this.aggroTarget = null;

        // Movement attributes
        this.currentPath = arena
            ? this.generateRandomPath(arena)
            : this.generateDefaultPath(x, y);
        this.pathIndex = 0;
        this.targetPoint = this.currentPath[0];

        // Sprite and animation
        this.image = new Image();
        this.image.src = 'res/workerant.png';

        // Walking animation
        this.walkAnimationOffset = 0;
        this.walkAnimationSpeed = 0.2;
    }

    calculateWorkerAntSpeed(rarity) {
        const speedMultipliers = {
            'common': 0.2,
            'uncommon': 0.25,
            'rare': 0.3,
            'epic': 0.35,
            'legendary': 0.4,
            'mythic': 0.45,
            'ultra': 0.5,
            'super': 0.55
        };
        return speedMultipliers[rarity] || 0.2;
    }

    generateRandomPath(arena) {
        if (!arena) return this.generateDefaultPath(this.x, this.y);

        const pathPoints = [];
        const numPoints = 5 + Math.floor(Math.random() * 5); // 5-10 points

        for (let i = 0; i < numPoints; i++) {
            const angle = Math.random() * Math.PI * 2;
            const radius = arena.radius * 0.7 * Math.random(); // Stay within 70% of arena radius

            pathPoints.push({
                x: arena.x + Math.cos(angle) * radius,
                y: arena.y + Math.sin(angle) * radius
            });
        }

        return pathPoints;
    }

    generateDefaultPath(startX, startY) {
        return [
            { x: startX, y: startY },
            {
                x: startX + (Math.random() - 0.5) * 100,
                y: startY + (Math.random() - 0.5) * 100
            },
            {
                x: startX + (Math.random() - 0.5) * 200,
                y: startY + (Math.random() - 0.5) * 200
            }
        ];
    }

    update(player, arena, otherMobs) {
        if (this.isDead) return;
        const currentTime = Date.now();
        this.updateStatusEffects(currentTime);
        // Walking animation
        this.walkAnimationOffset += this.walkAnimationSpeed;

        // Aggro behavior
        if (this.isAggro && player) {
            this.chasePlayer(player, arena);
        } else if (arena) {
            // Normal wandering behavior
            this.updatePath(arena, otherMobs);
        }
    }

    updatePath(arena, otherMobs) {
        if (!arena) return;

        const dx = this.targetPoint.x - this.x;
        const dy = this.targetPoint.y - this.y;
        const distance = Math.sqrt(dx * dx + dy * dy);

        // Reached current target point
        if (distance < 5) {
            this.pathIndex = (this.pathIndex + 1) % this.currentPath.length;
            this.targetPoint = this.currentPath[this.pathIndex];
        }

        // Move towards target point
        const moveSpeed = this.speed;
        const angle = Math.atan2(dy, dx);

        const newX = this.x + Math.cos(angle) * moveSpeed;
        const newY = this.y + Math.sin(angle) * moveSpeed;

        // Boundary and collision checks
        const dx_center = newX - arena.x;
        const dy_center = newY - arena.y;
        const distanceFromCenter = Math.sqrt(dx_center * dx_center + dy_center * dy_center);

        // Advanced collision handling with pushing
        let canMove = true;
        let pushVector = { x: 0, y: 0 };

        if (otherMobs) {
            for (const otherMob of otherMobs) {
                if (otherMob === this || otherMob.isDead) continue;

                const mobDx = newX - otherMob.x;
                const mobDy = newY - otherMob.y;
                const mobDistance = Math.sqrt(mobDx * mobDx + mobDy * mobDy);
                const minDistance = this.size + otherMob.size;

                if (mobDistance < minDistance) {
                    // Calculate push vector
                    const overlapFactor = minDistance - mobDistance;
                    const pushAngle = Math.atan2(mobDy, mobDx);

                    pushVector.x += Math.cos(pushAngle) * overlapFactor * 0.5;
                    pushVector.y += Math.sin(pushAngle) * overlapFactor * 0.5;
                }
            }
        }

        // Apply push vector and movement
        const finalX = newX + pushVector.x;
        const finalY = newY + pushVector.y;

        // Boundary check
        const finalDx_center = finalX - arena.x;
        const finalDy_center = finalY - arena.y;
        const finalDistanceFromCenter = Math.sqrt(finalDx_center * finalDx_center + finalDy_center * finalDy_center);

        // Apply movement if within arena
        if (finalDistanceFromCenter <= arena.radius - this.size) {
            this.x = finalX;
            this.y = finalY;
        }
    }

    chasePlayer(player, arena) {
        const dx = player.x - this.x;
        const dy = player.y - this.y;
        const distance = Math.sqrt(dx * dx + dy * dy);
        const angle = Math.atan2(dy, dx);

        const newX = this.x + Math.cos(angle) * this.speed;
        const newY = this.y + Math.sin(angle) * this.speed;

        // Boundary check
        const dx_center = newX - arena.x;
        const dy_center = newY - arena.y;
        const distanceFromCenter = Math.sqrt(dx_center * dx_center + dy_center * dy_center);

        // Apply movement if within arena
        if (distanceFromCenter <= arena.radius - this.size) {
            this.x = newX;
            this.y = newY;
        }
    }

    onPetalHit(petal) {
        // Aggro on hit
        this.isAggro = true;
    }

    draw(ctx) {
        if (this.isDead) {
            super.draw(ctx);
            return;
        }

        ctx.save();
        ctx.translate(this.x, this.y);

        // Slight walking animation
        const walkOffset = Math.sin(this.walkAnimationOffset) * 2;

        // Draw worker ant sprite
        if (this.image.complete) {
            ctx.drawImage(
                this.image,
                -this.size,
                -this.size + walkOffset,
                this.size * 2,
                this.size * 2
            );
        }

        ctx.restore();

        // Draw health bar and name
        this.drawHealthBar(ctx);
        this.drawName(ctx);
    }

    die() {
        if (!this.isDead) {
            console.log(`Die method called for ${this.constructor.name} with rarity ${this.rarity}`);
            super.die(); // Call parent method

            // Ensure waveManager is set before attempting to spawn drops
            if (this.waveManager) {
                const random = Math.random() * 100;

                // Probability matrix for drops based on rarity
                const dropChances = {
                    'common':     { nothing: 40, leafOnly: 30, cornOnly: 15, both: 15 },
                    'uncommon':   { nothing: 30, leafOnly: 25, cornOnly: 25, both: 20 },
                    'rare':       { nothing: 20, leafOnly: 20, cornOnly: 30, both: 30 },
                    'epic':       { nothing: 15, leafOnly: 15, cornOnly: 35, both: 35 },
                    'legendary':  { nothing: 10, leafOnly: 10, cornOnly: 40, both: 40 },
                    'mythic':     { nothing: 5, leafOnly: 5, cornOnly: 45, both: 45 },
                    'ultra':      { nothing: 2, leafOnly: 3, cornOnly: 50, both: 45 },
                    'super':      { nothing: 1, leafOnly: 2, cornOnly: 50, both: 47 }
                };

                const chances = dropChances[this.rarity];
                const dropType = this.calculateDropType(random, chances);

                const petalTypes = ['standard', 'focused', 'spread'];
                const petalType = petalTypes[Math.floor(Math.random() * petalTypes.length)];

                // Dropping logic for leaf and corn petals
                let leafDropped = false;
                let cornDropped = false;

                switch(dropType) {
                    case 'leafOnly':
                        this.spawnLeafPetalDrop();
                        leafDropped = true;
                        break;
                    case 'cornOnly':
                        this.spawnCornPetalDrop(petalType);
                        cornDropped = true;
                        break;
                    case 'both':
                        this.spawnLeafPetalDrop();
                        this.spawnCornPetalDrop(petalType);
                        leafDropped = true;
                        cornDropped = true;
                        break;
                }

                // Create visual arrangement for drops
                this.arrangeDrops(leafDropped, cornDropped);
            } else {
                console.error('No WaveManager set for mob');
            }
        }
    }

    calculateDropType(random, chances) {
        let cumulative = 0;
        for (const [type, chance] of Object.entries(chances)) {
            cumulative += chance;
            if (random < cumulative) return type;
        }
        return 'nothing'; // Fallback
    }

    generateDropRarity(options = {}) {
        const {
            excludeCommon = false,
            baseRarities = ['common', 'uncommon', 'rare', 'epic', 'legendary', 'mythic', 'ultra', 'super'],
            customWeights = null
        } = options;

        const random = Math.random() * 100;

        // Filter rarities based on excludeCommon
        const dropRarities = excludeCommon
            ? baseRarities.filter(r => r !== 'common')
            : baseRarities;

        // Adjust probabilities based on the worker ant's rarity
        const rarityIndex = dropRarities.indexOf(this.rarity);
        const maxRarityIndex = Math.min(rarityIndex + 2, dropRarities.length - 1);

        const possibleRarities = dropRarities.slice(0, maxRarityIndex + 1);

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

    spawnLeafPetalDrop() {
        const leafRarity = this.generateDropRarity() || 'common';
        this.waveManager.spawnDrop({
            x: this.x,
            y: this.y,
            type: 'leaf',
            rarity: leafRarity,
            imagePath: `/res/${leafRarity}leaf.png`
        });
    }

    spawnCornPetalDrop(petalType) {
        const cornRarity = this.generateDropRarity() || 'common';
        this.waveManager.spawnDrop({
            x: this.x,
            y: this.y,
            type: 'corn',
            rarity: cornRarity,
            petalType: petalType,
            imagePath: `/res/${cornRarity}corn.png`
        });
    }

    arrangeDrops(leafDropped, cornDropped) {
        const dropPositions = [];

        if (cornDropped) {
            dropPositions.push({
                x: this.x,
                y: this.y,
                type: 'leaf',
                rarity: this.generateDropRarity() || 'common'
            });
        }
        if (leafDropped) {
            dropPositions.push({
                x: this.x + 40,
                y: this.y,
                type: 'corn',
                rarity: this.generateDropRarity() || 'common',
                petalType: ['standard', 'focused', 'spread'][Math.floor(Math.random() * 3)]
            });
        }

        // If both dropped, create a slight triangle formation
        if (leafDropped && cornDropped) {
            dropPositions[1] = {
                x: this.x + 20,
                y: this.y - 20,
                type: 'corn',
                rarity: this.generateDropRarity() || 'common',
                petalType: ['standard', 'focused', 'spread'][Math.floor(Math.random() * 3)]
            };
        }

        // Now spawn the drops
        dropPositions.forEach(drop => {
            this.waveManager.spawnDrop({
                x: drop.x,
                y: drop.y,
                type: drop.type,
                rarity: drop.rarity,
                petalType: drop.petalType,
                imagePath: `/res/${drop.rarity}${drop.type}.png`
            });
        });
    }
}