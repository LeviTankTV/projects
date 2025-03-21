import { Mob } from "./Mob.js";

export class Ladybug extends Mob {
    constructor(x, y, rarity, arena = null) {
        super(x, y, rarity, 'Ladybug');

        // Ladybug-specific health based on rarity
        this.healthByRarity = {
            'common': 80,
            'uncommon': 160,
            'rare': 320,
            'epic': 640,
            'legendary': 1280,
            'mythic': 2560,
            'ultra': 5120,
            'super': 10240
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

        // Ladybug-specific attributes
        this.size *= sizeMultipliers[rarity] || 1;
        this.health = this.healthByRarity[rarity] || 320;
        this.maxHealth = this.health;
        this.speed = this.calculateLadybugSpeed(rarity);

        // Neutral behavior
        this.behavior = 'passive';
        this.isAggravated = false;

        // Advanced movement attributes
        this.currentPath = this.generateRandomPath();
        this.pathIndex = 0;
        this.targetPoint = this.currentPath[0];

        // Smooth rotation attributes
        this.currentRotation = 0;
        this.targetRotation = 0;
        this.rotationSmoothing = 0.05;

        // Sprite and animation
        this.image = new Image();
        this.image.src = 'res/ladybug.png';

        // Walking animation
        this.walkAnimationOffset = 0;
        this.walkAnimationSpeed = 0.2;
        this.currentPath = arena
            ? this.generateRandomPath(arena)
            : this.generateDefaultPath(x, y);

        this.pathIndex = 0;
        this.targetPoint = this.currentPath[0];
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

    calculateLadybugSpeed(rarity) {
        const speedMultipliers = {
            'common': 0.15,
            'uncommon': 0.2,
            'rare': 0.25,
            'epic': 0.3,
            'legendary': 0.35,
            'mythic': 0.4,
            'ultra': 0.45,
            'super': 0.5
        };
        return speedMultipliers[rarity] || 0.15;
    }

    smoothRotation() {
        // Calculate target rotation based on movement direction
        const dx = this.targetPoint.x - this.x;
        const dy = this.targetPoint.y - this.y;
        this.targetRotation = Math.atan2(dy, dx);

        // Smooth rotation interpolation
        const angleDiff = this.targetRotation - this.currentRotation;

        // Normalize angle difference
        const normalizedDiff =
            angleDiff > Math.PI ? angleDiff - Math.PI * 2 :
                angleDiff < -Math.PI ? angleDiff + Math.PI * 2 :
                    angleDiff;

        this.currentRotation += normalizedDiff * this.rotationSmoothing;
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

    update(player, arena, otherMobs) {
        if (this.isDead) return;
        const currentTime = Date.now();
        this.updateStatusEffects(currentTime);
        // Walking animation
        this.walkAnimationOffset += this.walkAnimationSpeed;

        // Only update path if arena is provided
        if (arena) {
            this.updatePath(arena, otherMobs);
            this.smoothRotation();
        }
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
    draw(ctx) {
        if (this.isDead) {
            super.draw(ctx);
            return;
        }

        ctx.save();
        ctx.translate(this.x, this.y);

        // Rotation based on movement direction
        ctx.rotate(this.currentRotation);

        // Slight walking animation
        const walkOffset = Math.sin(this.walkAnimationOffset) * 2;

        // Draw ladybug sprite
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

    // Neutral: No aggression when hit
    onPetalHit(petal) {
        // Ladybug remains passive
        return;
    }

    // Neutral: No collision aggression
    onPlayerCollision(player) {
        // Ladybug remains passive
        return;
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
                    'common':     { nothing: 40, roseOnly: 30, lightOnly: 15, both: 15 },
                    'uncommon':   { nothing: 30, roseOnly: 25, lightOnly: 25, both: 20 },
                    'rare':       { nothing: 20, roseOnly: 20, lightOnly: 30, both: 30 },
                    'epic':       { nothing: 15, roseOnly: 15, lightOnly: 35, both: 35 },
                    'legendary':  { nothing: 10, roseOnly: 10, lightOnly: 40, both: 40 },
                    'mythic':     { nothing: 5, roseOnly: 5, lightOnly: 45, both: 45 },
                    'ultra':      { nothing: 2, roseOnly: 3, lightOnly: 50, both: 45 },
                    'super':      { nothing: 1, roseOnly: 2, lightOnly: 50, both: 47 }
                };

                const chances = dropChances[this.rarity];
                const dropType = this.calculateDropType(random, chances);

                const petalTypes = ['standard', 'focused', 'spread'];
                const petalType = petalTypes[Math.floor(Math.random() * petalTypes.length)];

                // Dropping logic for rose and light petals
                let roseDropped = false;
                let lightDropped = false;

                switch(dropType) {
                    case 'roseOnly':
                        this.spawnRosePetalDrop();
                        roseDropped = true;
                        break;
                    case 'lightOnly':
                        this.spawnLightPetalDrop(petalType);
                        lightDropped = true;
                        break;
                    case 'both':
                        this.spawnRosePetalDrop();
                        this.spawnLightPetalDrop(petalType);
                        roseDropped = true;
                        lightDropped = true;
                        break;
                }

                // Create visual arrangement for drops
                this.arrangeDrops(roseDropped, lightDropped);
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

        // Adjust probabilities based on the ladybug's rarity
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

    spawnRosePetalDrop() {
        const roseRarity = this.generateDropRarity() || 'common';
        this.waveManager.spawnDrop({
            x: this.x,
            y: this.y,
            type: 'rose',
            rarity: roseRarity,
            imagePath: `/res/${roseRarity}rose.png`
        });
    }

    spawnLightPetalDrop(petalType) {
        const lightRarity = this.generateDropRarity() || 'common';
        this.waveManager.spawnDrop({
            x: this.x,
            y: this.y,
            type: 'light',
            rarity: lightRarity,
            petalType: petalType,
            imagePath: `/res/${lightRarity}light.png`
        });
    }

    arrangeDrops(roseDropped, lightDropped) {
        const dropPositions = [];

        if (lightDropped) {
            dropPositions.push({
                x: this.x,
                y: this.y,
                type: 'rose',
                rarity: this.generateDropRarity() || 'common'
            });
        }
        if (roseDropped) {
            dropPositions.push({
                x: this.x + 40,
                y: this.y,
                type: 'light',
                rarity: this.generateDropRarity() || 'common',
                petalType: ['standard', 'focused', 'spread'][Math.floor(Math.random() * 3)]
            });
        }

        // If both dropped, create a slight triangle formation
        if (roseDropped && lightDropped) {
            dropPositions[1] = {
                x: this.x + 20,
                y: this.y - 20,
                type: 'light',
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