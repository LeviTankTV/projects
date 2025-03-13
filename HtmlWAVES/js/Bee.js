import { Mob } from "./Mob.js";
import { Stinger } from "./Stinger.js";

export class Bee extends Mob {
    constructor(x, y, rarity) {
        super(x, y, rarity, 'Bee');

        // Bee-specific health based on rarity
        this.healthByRarity = {
            'common': 60,
            'uncommon': 120,
            'rare': 240,
            'epic': 480,
            'legendary': 960,
            'mythic': 1920,
            'ultra': 3840,
            'super': 7680
        };

        // Expanded body damage by rarity
        this.bodyDamageByRarity = {
            'common': 10,
            'uncommon': 20,
            'rare': 40,
            'epic': 80,
            'legendary': 160,
            'mythic': 320,
            'ultra': 640,
            'super': 1280
        };

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
        this.speed = this.calculateBeeSpeed(rarity);
        // Override health and body damage
        this.health = this.healthByRarity[rarity] || 240;
        this.maxHealth = this.health;
        this.bodyDamage = this.bodyDamageByRarity[rarity] || 40;

        // Bee image and behavior
        this.image = new Image();
        this.image.src = 'res/bee.png';

        // Enhanced passive movement
        this.passiveMovement = {
            targetX: x,
            targetY: y,
            wanderRadius: 20, // Reduced wandering radius
            movementSmoothing: 0.01 // Even slower smoothing
        };

        // Slower aggression and movement
        this.aggressionDuration = 5000; // Longer aggression duration
        this.moveSmoothing = 0.02; // Very slow movement

        this.isAggravated = false;
        this.aggressionTimer = 0;
        this.aggressionDuration = 3000;
        this.behavior = 'passive';

        // Wing animation
        this.wingAnimationOffset = 0;
        this.wingAnimationSpeed = 0.2;
    }

    generateStingerDrop() {
        const random = Math.random() * 100;
        console.log(`Generating drop for ${this.rarity} mob. Random value: ${random}`);

        // Simplified drop generation with higher drop rates
        switch(this.rarity) {
            case 'common':
                return random < 50 ? 'common' : null; // 50% drop chance

            case 'uncommon':
                if (random < 60) return 'common';     // 60% common drop
                if (random < 90) return 'uncommon';   // 30% uncommon drop
                return null;

            case 'rare':
                if (random < 40) return 'common';     // 40% common drop
                if (random < 80) return 'uncommon';   // 40% uncommon drop
                if (random < 95) return 'rare';       // 15% rare drop
                return null;

            case 'epic':
                if (random < 30) return 'uncommon';   // 30% uncommon drop
                if (random < 70) return 'rare';       // 40% rare drop
                if (random < 90) return 'epic';       // 20% epic drop
                return null;

            case 'legendary':
                if (random < 20) return 'rare';       // 20% rare drop
                if (random < 60) return 'epic';       // 40% epic drop
                if (random < 85) return 'legendary';  // 25% legendary drop
                return null;

            case 'mythic':
                if (random < 10) return 'epic';       // 10% epic drop
                if (random < 50) return 'legendary';  // 40% legendary drop
                if (random < 80) return 'mythic';     // 30% mythic drop
                return null;

            case 'ultra':
                if (random < 20) return 'legendary';  // 20% legendary drop
                if (random < 60) return 'mythic';     // 40% mythic drop
                if (random < 90) return 'ultra';      // 30% ultra drop
                return null;

            case 'super':
                if (random < 30) return 'mythic';     // 30% mythic drop
                if (random < 70) return 'ultra';      // 40% ultra drop
                if (random < 95) return 'super';      // 25% super drop
                return null;

            default:
                console.warn(`No drop generation defined for rarity: ${this.rarity}`);
                return null;
        }
    }

    die() {
        if (!this.isDead) {
            console.log(`Die method called for ${this.constructor.name} with rarity ${this.rarity}`);
            super.die(); // Call parent method

            // Ensure waveManager is set before attempting to spawn drop
            if (this.waveManager) {
                const stingerDrop = this.generateStingerDrop() || 'common';
                console.log(`Generated drop: ${stingerDrop}`);

                // Spawn drop through waveManager
                this.waveManager.spawnDrop({
                    x: this.x,
                    y: this.y,
                    type: 'stinger',
                    rarity: stingerDrop,
                    imagePath: `/res/${stingerDrop}stinger.png`
                });
            } else {
                console.error('No WaveManager set for mob');
            }
        }
    }


    handleBeeCollisions(otherBees) {
        for (const otherBee of otherBees) {
            if (otherBee === this) continue;

            const dx = this.x - otherBee.x;
            const dy = this.y - otherBee.y;
            const distance = Math.sqrt(dx * dx + dy * dy);
            const minDistance = this.size + otherBee.size;

            // If bees are overlapping
            if (distance < minDistance) {
                // Calculate overlap
                const overlap = minDistance - distance;
                const angle = Math.atan2(dy, dx);

                // Soft separation with smooth pushing
                const separationFactor = 0.05; // Gentle pushing
                const totalSize = this.size + otherBee.size;

                // Proportional separation based on bee sizes
                const thisRatio = this.size / totalSize;
                const otherRatio = otherBee.size / totalSize;

                // Gradual, smooth separation
                this.x += Math.cos(angle) * (overlap * otherRatio * separationFactor);
                this.y += Math.sin(angle) * (overlap * otherRatio * separationFactor);

                otherBee.x -= Math.cos(angle) * (overlap * thisRatio * separationFactor);
                otherBee.y -= Math.sin(angle) * (overlap * thisRatio * separationFactor);
            }
        }
    }

    smoothPassiveMovement(arena) {
        const movementSmoothing = 0.01; // Extremely slow movement

        // Much less frequent target changes
        if (this.distanceTo(this.passiveMovement.targetX, this.passiveMovement.targetY) < 3 || Math.random() < 0.01) {
            const angle = Math.random() * Math.PI * 2;
            const maxRadius = arena.radius * 0.6; // Stay more centrally

            this.passiveMovement.targetX = arena.x + Math.cos(angle) * (Math.random() * maxRadius);
            this.passiveMovement.targetY = arena.y + Math.sin(angle) * (Math.random() * maxRadius);
        }

        // Ultra-smooth, very slow interpolation
        const dx = this.passiveMovement.targetX - this.x;
        const dy = this.passiveMovement.targetY - this.y;

        this.x += dx * movementSmoothing;
        this.y += dy * movementSmoothing;
    }

    calculateBeeSpeed(rarity) {
        const speedMultipliers = {
            'common': 0.2,
            'uncommon': 0.3,
            'rare': 0.4,
            'epic': 0.5,
            'legendary': 0.6,
            'mythic': 0.7,
            'ultra': 0.8,
            'super': 0.9
        };
        return speedMultipliers[rarity] || 0.2;
    }

    moveTowardsPlayerWithBoundaryCheck(player, arena) {
        const dx = player.x - this.x;
        const dy = player.y - this.y;
        const distance = Math.sqrt(dx * dx + dy * dy);

        if (distance <= this.aggroRange) {
            const angle = Math.atan2(dy, dx);
            const moveSpeed = 0.1; // Extremely reduced movement speed

            const newX = this.x + Math.cos(angle) * moveSpeed;
            const newY = this.y + Math.sin(angle) * moveSpeed;

            // Very soft boundary check
            const newDx = newX - arena.x;
            const newDy = newY - arena.y;
            const newDistanceFromCenter = Math.sqrt(newDx * newDx + newDy * newDy);

            // Tighter boundary constraint
            if (newDistanceFromCenter <= arena.radius - this.size * 1.2) {
                this.x = newX;
                this.y = newY;
            }
        }
    }

    distanceTo(x, y) {
        return Math.sqrt(
            Math.pow(this.x - x, 2) +
            Math.pow(this.y - y, 2)
        );
    }

    update(player, arena, otherMobs) {
        // Slower update cycle
        if (this.isDead) return;

        const currentTime = Date.now();
        this.updateStatusEffects(currentTime);
        // Slower wing animation
        this.wingAnimationOffset += this.wingAnimationSpeed * 0.5;

        // More relaxed aggression mechanics
        if (this.isAggravated) {
            this.moveTowardsPlayerWithBoundaryCheck(player, arena);

            // Longer aggression duration
            if (currentTime - this.aggressionTimer > this.aggressionDuration) {
                this.isAggravated = false;
            }
        } else {
            // Even slower passive movement
            this.smoothPassiveMovement(arena);
        }

        // Add bee-to-bee collision handling
        if (otherMobs) {
            const otherBees = otherMobs.filter(mob => mob instanceof Bee && mob !== this);
            if (otherBees.length > 0) {
                this.handleBeeCollisions(otherBees);
            }
        }
    }

    draw(ctx) {
        if (this.isDead) {
            super.draw(ctx);
            return;
        }

        ctx.save();

        // Center translation
        ctx.translate(this.x, this.y);

        // Slight wobble and wing flutter
        const wobbleAngle = Math.sin(this.wingAnimationOffset) * 0.1;
        const wingFlutter = Math.abs(Math.sin(this.wingAnimationOffset * 2)) * 5;

        ctx.rotate(wobbleAngle);

        // Draw bee body
        if (this.image.complete) {
            ctx.drawImage(
                this.image,
                -this.size,
                -this.size - wingFlutter,
                this.size * 2,
                this.size * 2
            );
        }

        ctx.restore();

        // Draw health bar and name
        this.drawHealthBar(ctx);
        this.drawName(ctx);
    }

    onPetalHit(petal) {
        if (!this.isAggravated) {
            this.isAggravated = true;
            this.aggressionTimer = Date.now();
        }
    }

    onPlayerCollision(player) {
        if (!this.isAggravated) {
            this.isAggravated = true;
            this.aggressionTimer = Date.now();
        }
    }
}