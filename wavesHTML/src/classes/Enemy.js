import { Petal } from "./Petal.js";
import { BasicPetal } from "./Basic.js"; // Ensure BasicPetal is imported
const RARITY_ROTATION_SPEED = {
    common: 2,      // Slowest
    uncommon: 2.5,
    rare: 3,
    epic: 3,
    legendary: 4,
    mythic: 8,       // Fastest
    ultra: 15
};

const RARITY_CONFIG = {
    common: {
        petalCount: 2,
        petalRadius: 5,
        radius: 15,
        baseSpeed: 6,
        speedReductionFactor: 0.05,
        health: 20
    },
    uncommon: {
        petalCount: 3,
        petalRadius: 10,
        radius: 20,
        baseSpeed: 5,
        speedReductionFactor: 0.08,
        health: 50
    },
    rare: {
        petalCount: 4,
        petalRadius: 12,
        radius: 25,
        baseSpeed: 4,
        speedReductionFactor: 0.1,
        health: 100
    },
    epic: {
        petalCount: 6,
        petalRadius: 15,
        radius: 30,
        baseSpeed: 3,
        speedReductionFactor: 0.12,
        health: 500
    },
    legendary: {
        petalCount: 10,
        petalRadius: 20,
        radius: 50,
        baseSpeed: 2,
        speedReductionFactor: 0.15,
        health: 1500
    },
    mythic: {
        petalCount: 10,
        petalRadius: 25,
        radius: 100,
        baseSpeed: 1,
        speedReductionFactor: 0.2,
        health: 5000
    },
    ultra: { // Adding the Ultra enemy
        petalCount: 15,
        petalRadius: 30,
        radius: 300,
        baseSpeed: 1.5,
        speedReductionFactor: 0.25,
        health: 100000
    }
};

export class Enemy {
    constructor(x, y, rarity, petalManager) {
        const config = RARITY_CONFIG[rarity];
        this.isDead = false; // Add this line

        this.x = x;
        this.y = y;
        this.radius = config.radius;
        this.rarity = rarity;

        this.maxHP = config.health;
        this.currentHP = this.maxHP;

        // Dynamic speed calculation
        this.calculateDynamicSpeed(config);

        // Enemy images
        const enemyImages = [
            '/res/devilmy.png',
            '/res/eyemy.png',
            '/res/fl.png',
            '/res/pl.png',
            '/res/lol.png'
        ];

        // Randomly select an image path
        const randomImagePath = enemyImages[Math.floor(Math.random() * enemyImages.length)];

        this.image = new Image();
        this.image.src = randomImagePath;

        // Enemy movement
        this.direction = {
            x: Math.random() * 2 - 1,
            y: Math.random() * 2 - 1
        };

        // Petal management
        this.petalManager = petalManager;
        this.activePetals = this.generateEnemyPetals(config.petalCount); // Generate petals based on rarity

        if (this.rarity === 'ultra') {
            this.specialAbilityCooldown = 5000; // Cooldown for special ability
            this.lastSpecialAbilityTime = 0;
        }

        if (rarity === 'ultra') {
            this.image.src = 'res/ultra_enemy_special.png';
        }

        // Enemy AI state
        this.state = {
            isAggressive: Math.random() > 0.5,
            detectionRange: 300,
            attackRange: 30
        };
    }

    performSpecialAbility(player, game) {
        const currentTime = Date.now();
        if (currentTime - this.lastSpecialAbilityTime > this.specialAbilityCooldown) {
            // Example special ability: heal itself
            this.currentHP = Math.min(this.maxHP, this.currentHP + 1000); // Heal 1000 HP
            this.lastSpecialAbilityTime = currentTime;
            console.log(`Ultra enemy used special ability! Current HP: ${this.currentHP}`);
        }}
    // Generate enemy petals based on the configured petal count and rarity
    generateEnemyPetals(petalCount) {
        const petals = [];
        for (let i = 0; i < petalCount; i++) {
            const petalRarity = this.rarity; // Use the enemy's rarity for the petals
            const petal = new BasicPetal(petalRarity); // Create a new BasicPetal with the enemy's rarity
            petals.push(petal);
        }
        return petals;
    }

    takeDamage(amount, game) {
        this.currentHP -= amount;
        if (this.currentHP <= 0) {
            this.currentHP = 0; // Prevent negative HP
            this.destroy(game); // Pass the game instance here
        }
    }

    calculateDynamicSpeed(config) {
        // Base speed calculation with size-based reduction
        const baseSpeed = config.baseSpeed;
        const sizeSpeedReduction = this.radius * config.speedReductionFactor;

        // Ensure speed doesn't go below a minimum threshold
        this.speed = Math.max(
            0.1,
            baseSpeed - sizeSpeedReduction
        );
    }

    update(player, world, game) {
        if (this.isDead) return;

        this.x += this.direction.x * this.speed;
        this.y += this.direction.y * this.speed;

        // World boundary check
        if (this.x < 0 || this.x > world.width) this.direction.x *= -1;
        if (this.y < 0 || this.y > world.height) this.direction.y *= -1;

        // Player detection and interaction
        const distanceToPlayer = Math.sqrt(
            Math.pow(this.x - player.x, 2) +
            Math.pow(this.y - player.y, 2)
        );

        // AI behavior
        if (distanceToPlayer <= this.state.detectionRange) {
            // Track player
            const angle = Math.atan2(
                player.y - this.y,
                player.x - this.x
            );

            this.direction.x = Math.cos(angle);
            this.direction.y = Math.sin(angle);

            // Aggressive behavior if close
            if (distanceToPlayer <= this.state.attackRange && this.state.isAggressive) {
                this.attack(player, game);
            }

            // Use special ability if health is low
            if (this.currentHP < this.maxHP * 0.5) {
                this.performSpecialAbility(player, game);
            }
        }

        // Update petals
        this.updatePetals();
    }

    updatePetals() {
        const orbitDistance = this.radius + 20; // Dynamic orbit distance
        const time = Date.now() / 1000; // Use current time for animation
        const rotationSpeed = RARITY_ROTATION_SPEED[this.rarity]; // Get rotation speed based on rarity
        const petalCount = this.activePetals.length;

        this.activePetals.forEach((petal, index) => {
            if (petal.isRecharging) return; // Skip recharging petals

            // Calculate angle with even distribution
            const angleStep = (2 * Math.PI) / petalCount; // Divide circle evenly
            const baseAngle = time * rotationSpeed; // Rotate the entire pattern
            const individualAngle = baseAngle + (index * angleStep);

            // Calculate petal position based on orbit distance
            petal.x = this.x + Math.cos(individualAngle) * orbitDistance;
            petal.y = this.y + Math.sin(individualAngle) * orbitDistance;

            // Update petal state
            petal.update({
                spacebar: this.state.isAggressive,
                tKey: false
            });
        });
    }

    destroy(game) {
        this.isDead = true;
        this.dropPetal(game);

        // Add experience based on enemy rarity
        const experienceValues = {
            common: 5,
            uncommon: 20,
            rare: 50,
            epic: 100,
            legendary: 500,
            mythic: 2000
        };

        const experienceGained = experienceValues[this.rarity] || 5;
        game.player.addExperience(experienceGained);

        console.log(`${this.rarity} enemy destroyed! Gained ${experienceGained} XP`);
    }

    dropPetal(game) {
        // Check if the enemy has petals to drop
        if (this.activePetals.length > 0) {
            // Randomly select one of the active petals
            const randomIndex = Math.floor(Math.random() * this.activePetals.length);
            const petalToDrop = this.activePetals[randomIndex];

            // Create a new Petal object for the dropped petal
            const droppedPetal = new Petal(
                petalToDrop.color,
                petalToDrop.radius,
                petalToDrop.type,
                false, // Not player controlled
                null,  // No parent since it's dropped
                petalToDrop.rarity // Preserve the original petal's rarity
            );

            // Set the position of the dropped petal at the enemy's position
            droppedPetal.x = this.x;
            droppedPetal.y = this.y;

            // Add the dropped petal to the game's dropped items
            game.addDroppedItem(droppedPetal);

            // Remove the petal from the enemy's active petals
            this.activePetals.splice(randomIndex, 1);
        }
    }

    attack(player, game) {
        player.takeDamage(50, game);
        console.log(`${this.rarity} enemy attacking player!`);
    }

    draw(ctx, camera) {
        // Draw enemy using image
        ctx.drawImage(
            this.image,
            this.x - camera.x - this.radius,
            this.y - camera.y - this.radius,
            this.radius * 2,
            this.radius * 2
        );

        // Draw orbiting petals
        this.activePetals.forEach(petal => {
            petal.draw(ctx,
                this.x - camera.x,
                this.y - camera.y
            );
        });

        // Draw health bar
        this.drawHealthBar(ctx, camera);
    }

    drawHealthBar(ctx, camera) {
        const healthBarWidth = 50;
        const healthBarHeight = 5;

        // Calculate health bar position
        const barX = this.x - camera.x - healthBarWidth / 2;
        const barY = this.y - camera.y - this.radius - 10; // Position above the enemy

        // Draw the background of the health bar
        ctx.fillStyle = 'black';
        ctx.fillRect(barX, barY, healthBarWidth, healthBarHeight);

        // Calculate health percentage
        const healthPercentage = Math.max(0, this.currentHP / this.maxHP); // Prevent negative values

        // Draw the health bar based on current health
        ctx.fillStyle = 'red'; // Default color
        if (this.rarity === 'mythic') {
            ctx.fillStyle = 'magenta';
        } else if (this.rarity === 'legendary') {
            ctx.fillStyle = 'red';
        } else if (this.rarity === 'epic') {
            ctx.fillStyle = 'purple';
        } else if (this.rarity === 'rare') {
            ctx.fillStyle = 'blue';
        } else if (this.rarity === 'uncommon') {
            ctx.fillStyle = 'yellow';
        } else if (this.rarity === 'common') {
            ctx.fillStyle = 'green';
        }

        ctx.fillRect(barX, barY, healthBarWidth * healthPercentage, healthBarHeight);

        // Draw rarity label
        ctx.fillStyle = 'white';
        ctx.font = '10px Arial';
        ctx.textAlign = 'center';
        ctx.fillText(this.rarity.charAt(0).toUpperCase() + this.rarity.slice(1), this.x - camera.x, barY - 5);
    }
}