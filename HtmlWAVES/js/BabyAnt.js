import { Mob } from "./Mob.js";
import {RarityUtils} from "./RarityUtils.js";

export class BabyAnt extends Mob {
    constructor(x, y, rarity) {
        super(x, y, rarity, 'Baby Ant');

        // Override behavior to be neutral
        this.behavior = 'neutral';

        // Baby Ant specific health based on rarity
        this.healthByRarity = {
            'common': 30,
            'uncommon': 60,
            'rare': 120,
            'epic': 240,
            'legendary': 480,
            'mythic': 960,
            'ultra': 1920,
            'super': 3840
        };

        // Override health
        this.health = this.healthByRarity[rarity] || 120;
        this.maxHealth = this.health;

        // Adjust size for ultra and super rarities
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

        // Baby Ant image
        this.image = new Image();
        this.image.src = 'res/babyant.png';

        // Panic state when injured
        this.isPanicking = false;
        this.panicTimer = 0;
        this.panicDuration = 2000; // 2 seconds of panic
        this.panicSpeed = this.speed * 1.5; // Faster when panicking

        // Passive movement parameters
        this.wanderRadius = 50;
        this.lastWanderTime = 0;
        this.wanderInterval = 3000; // Wander every 3 seconds
        this.generateRiceDrop = this.generateRiceDrop.bind(this);
        this.leafDrop = null;
    }

    // Override takeDamage to add panic behavior
    takeDamage(amount) {
        const previousHealth = this.health;
        super.takeDamage(amount);

        // If health decreased and not already panicking, start panic
        if (this.health < previousHealth && !this.isPanicking) {
            this.isPanicking = true;
            this.panicTimer = Date.now();
        }
    }

    update(player, arena, otherMobs) {
        // Call parent update method
        super.update(player, arena, otherMobs);

        const currentTime = Date.now();

        // Check panic state
        if (this.isPanicking) {
            if (currentTime - this.panicTimer > this.panicDuration) {
                this.isPanicking = false;
            }
            this.runFromPlayer(player, arena);
        } else {
            // Normal neutral behavior
            this.neutralWander(arena, currentTime);
        }
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
                    'common':     { nothing: 40, riceOnly: 30, lightOnly: 15, leafOnly: 10, both: 5 },
                    'uncommon':   { nothing: 30, riceOnly: 25, lightOnly: 25, leafOnly: 15, both: 5 },
                    'rare':       { nothing: 20, riceOnly: 20, lightOnly: 30, leafOnly: 20, both: 10 },
                    'epic':       { nothing: 15, riceOnly: 15, lightOnly: 35, leafOnly: 25, both: 10 },
                    'legendary':  { nothing: 10, riceOnly: 10, lightOnly: 30, leafOnly: 20, both: 30 },
                    'mythic':     { nothing: 5, riceOnly: 5, lightOnly: 25, leafOnly: 25, both: 40 },
                    'ultra':      { nothing: 2, riceOnly: 3, lightOnly: 30, leafOnly: 30, both: 35 },
                    'super':      { nothing: 1, riceOnly: 2, lightOnly: 30, leafOnly: 30, both: 37 }
                };

                const chances = dropChances[this.rarity];
                const dropType = this.calculateDropType(random, chances);

                const petalTypes = ['standard', 'focused', 'spread'];
                const petalType = petalTypes[Math.floor(Math.random() * petalTypes.length)];

                // Dropping logic for rice, light, and leaf petals
                let riceDropped = false;
                let lightDropped = false;
                let leafDropped = false;

                switch(dropType) {
                    case 'riceOnly':
                        this.spawnRiceDrop();
                        riceDropped = true;
                        break;
                    case 'lightOnly':
                        this.spawnLightPetalDrop(petalType);
                        lightDropped = true;
                        break;
                    case 'leafOnly':
                        const leafRarity = this.generateLeafDrop();
                        if (leafRarity) {
                            this.spawnLeafPetalDrop(leafRarity);
                            leafDropped = true;
                        }
                        break;
                    case 'both':
                        riceDropped = true;
                        lightDropped = true;
                        this.spawnRiceDrop();
                        this.spawnLightPetalDrop(petalType);
                        break;
                }

                // Handle the leaf drop if rice and light are also dropped
                if (riceDropped && lightDropped) {
                    const leafRarity = this.generateLeafDrop();
                    if (leafRarity) {
                        this.spawnLeafPetalDrop(leafRarity);
                        leafDropped = true;
                    }
                }

                // Create visual arrangement for drops
                this.arrangeDrops(riceDropped, lightDropped, leafDropped);
            } else {
                console.error('No WaveManager set for mob');
            }
        }
    }

    generateDropRarity(options = {}) {
        return RarityUtils.generateEntityDropRarity(this, options);
    }

// Simplified drop generation methods
    generateRiceDrop() {
        return this.generateDropRarity();
    }

    generateLightPetalDrop() {
        return this.generateDropRarity();
    }

    generateLeafDrop() {
        return this.generateDropRarity({ excludeCommon: true });
    }

    arrangeDrops(riceDropped, lightDropped, leafDropped) {
        const dropPositions = [];

        if (riceDropped) {
            dropPositions.push({ x: this.x, y: this.y });
        }
        if (lightDropped) {
            dropPositions.push({ x: this.x + 40, y: this.y }); // Offset for light drop
        }
        if (leafDropped) {
            dropPositions.push({ x: this.x - 40, y: this.y }); // Offset for leaf drop
        }

        // Adjust positions based on how many drops occurred
        if (riceDropped && lightDropped && leafDropped) {
            // Triangle formation
            dropPositions[1] = { x: this.x + 20, y: this.y - 20 }; // Light drop
            dropPositions[2] = { x: this.x - 20, y: this.y - 20 }; // Leaf drop
        } else if (riceDropped && lightDropped) {
            dropPositions[1] = { x: this.x + 20, y: this.y }; // Light drop
        } else if (riceDropped && leafDropped) {
            dropPositions[1] = { x: this.x - 20, y: this.y }; // Leaf drop
        } else if (lightDropped && leafDropped) {
            dropPositions[1] = { x: this.x + 20, y: this.y }; // Light drop
            dropPositions[2] = { x: this.x - 20, y: this.y }; // Leaf drop
        }

        // Now spawn the drops at their respective positions
        dropPositions.forEach((pos, index) => {
            const type = index === 0 ? 'rice' : (index === 1 ? 'light' : 'leaf');
            const rarity = index === 0 ? this.generateRiceDrop() : this.generateLeafDrop();
            this.waveManager.spawnDrop({
                x: pos.x,
                y: pos.y,
                type: type,
                rarity: rarity,
                imagePath: `/res/${rarity}${type}.png` // Adjust image path accordingly
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

    spawnRiceDrop() {
        const riceDrop = this.generateRiceDrop() || 'common';
        this.waveManager.spawnDrop({
            x: this.x,
            y: this.y,
            type: 'rice',
            rarity: riceDrop,
            imagePath: `/res/${riceDrop}rice.png`
        });
    }

    spawnLightPetalDrop(petalType) {
        const lightDrop = this.generateLightPetalDrop();
        if (lightDrop) {
            this.waveManager.spawnDrop({
                x: this.x,
                y: this.y,
                type: 'light',
                rarity: lightDrop,
                petalType: petalType,
                imagePath: `/res/${lightDrop}light.png`
            });
        }
    }

    spawnLeafPetalDrop(rarity) {
        this.waveManager.spawnDrop({
            x: this.x,
            y: this.y,
            type: 'leaf',
            rarity: rarity,
            imagePath: `/res/${rarity}leaf.png`
        });
    }
    runFromPlayer(player, arena) {
        // Calculate direction away from player
        const dx = this.x - player.x;
        const dy = this.y - player.y;
        const distance = Math.sqrt(dx * dx + dy * dy);

        // Only run if player is close
        if (distance < 200) {
            const angle = Math.atan2(dy, dx);
            const newX = this.x + Math.cos(angle) * this.panicSpeed;
            const newY = this.y + Math.sin(angle) * this.panicSpeed;

            // Check arena boundary
            const centerDx = newX - arena.x;
            const centerDy = newY - arena.y;
            const distanceFromCenter = Math.sqrt(centerDx * centerDx + centerDy * centerDy);

            // Stay within arena
            if (distanceFromCenter <= arena.radius - this.size) {
                this.x = newX;
                this.y = newY;
            }
        }
    }

    neutralWander(arena, currentTime) {
        // Periodic random wandering
        if (currentTime - this.lastWanderTime > this.wanderInterval) {
            const angle = Math.random() * Math.PI * 2;
            const maxRadius = arena.radius * 0.8; // Stay well inside arena

            // Calculate target within arena bounds
            const targetX = arena.x + Math.cos(angle) * (Math.random() * maxRadius);
            const targetY = arena.y + Math.sin(angle) * (Math.random() * maxRadius);

            // Move towards target
            const dx = targetX - this.x;
            const dy = targetY - this.y;
            const distance = Math.sqrt(dx * dx + dy * dy);

            if (distance > 5) {
                const moveRatio = Math.min(this.speed / distance, 1);
                this.x += dx * moveRatio;
                this.y += dy * moveRatio;
            }

            this.lastWanderTime = currentTime;
        }
    }

    draw(ctx) {
        if (this.isDead) {
            super.draw(ctx);
            return;
        }

        ctx.save();
        ctx.translate(this.x, this.y);

        // Slight wobble when panicking
        if (this.isPanicking) {
            const wobble = Math.sin(Date.now() * 0.1) * 0.1;
            ctx.rotate(wobble);
        }

        // Draw baby ant image
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
