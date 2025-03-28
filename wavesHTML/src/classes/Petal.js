export class Petal {
    constructor(color, radius, type, isPlayerControlled = false, parentEnemy = null, rarity = 'common') {
        this.color = color;
        this.radius = radius;
        this.type = type;
        this.angle = Math.random() * Math.PI * 2;

        // Distances
        this.normalDistance = 45; // Default orbit distance
        this.spacebarDistance = 60; // Distance when spacebar is pressed
        this.defenseDistance = 30; // Distance when T key is pressed
        this.orbitSpeed = isPlayerControlled ? 0.01 : 0.05; // Much slower for player petals
        // State variables
        this.currentDistance = this.normalDistance; // Start at normal distance
        this.speed = 0.1; // Speed of smooth transition

        // Enemy-specific orbital properties
        this.parentEnemy = parentEnemy;
        this.enemyOrbitRadius = parentEnemy
            ? this.calculateEnemyOrbitRadius(parentEnemy)
            : this.normalDistance; // Default for player petals

        // Control mode
        this.isPlayerControlled = isPlayerControlled;
        this.isSelected = !isPlayerControlled; // Enemies have petals selected by default

        this.rarity = rarity;
        this.damage = this.calculateDamage();
        this.maxHP = this.calculateMaxHP();
        this.currentHP = this.maxHP;
        this.rechargeTime = 3000; // 3 seconds in milliseconds
        this.lastDestroyedTime = 0;
        this.isRecharging = false;
        this.x = 0; // Initialize x and y to prevent undefined errors
        this.y = 0;

        this.applyPlayerEffect = null; // Will be overridden by subclasses
        this.removePlayerEffect = null; // Will be overridden by subclasses
    }

    calculateDamage() {
        const damageMap = {
            'common': 10,
            'uncommon': 20,
            'rare': 30,
            'epic': 40,
            'legendary': 50,
            'mythic': 60
        };
        return damageMap[this.rarity] || 10;
    }
    
    onAddToPlayer(player) {
        if (this.applyPlayerEffect) {
            this.applyPlayerEffect(player);
        }
    }

    // Method to remove effect when removed from player
    onRemoveFromPlayer(player) {
        if (this.removePlayerEffect) {
            this.removePlayerEffect(player);
        }
    }
    calculateMaxHP() {
        const hpMap = {
            'common': 20,
            'uncommon': 40,
            'rare': 50,
            'epic': 70,
            'legendary': 100,
            'mythic': 150
        };
        return hpMap[this.rarity] || 20;
    }

    checkCollisionWithPlayer(player) {
        if (this.isPlayerControlled || this.isRecharging) return false;

        const dx = this.x - player.x;
        const dy = this.y - player.y;
        const distance = Math.sqrt(dx * dx + dy * dy);

        return distance < (this.radius + player.radius);
    }

    dealDamageToPlayer(player, game) {
        // Skip if the petal is already recharging
        if (this.isRecharging) return;

        // Calculate how many times this petal can attack
        const attackCount = Math.floor(this.currentHP / this.damage);

        // Deal damage to player
        for (let i = 0; i < attackCount; i++) {
            player.takeDamage(this.damage, game);
            this.currentHP -= this.damage;
        }

        // Destroy petal and start recharge
        this.destroy();
    }

    destroy(game = null) {
        this.isRecharging = true;
        this.lastDestroyedTime = Date.now();

        // If this is an enemy petal and game is provided, don't drop on ground
        if (!this.isPlayerControlled && game) {
            // Remove from enemy's active petals
            if (this.parentEnemy && this.parentEnemy.activePetals) {
                this.parentEnemy.activePetals = this.parentEnemy.activePetals.filter(p => p !== this);
            }
        }
    }

    calculateEnemyOrbitRadius(enemy) {
        return enemy.radius + this.radius + 20; // Add buffer space
    }

    update(state, playerX, playerY) {
        const currentTime = Date.now();

        // Check if the petal is recharging
        if (this.isRecharging) {
            if (currentTime - this.lastDestroyedTime >= this.rechargeTime) {
                this.isRecharging = false; // Reset recharging state
                this.currentHP = this.calculateMaxHP(); // Restore HP
            }

            // If recharging, set distance to 0 to make it disappear
            if (this.isPlayerControlled) {
                this.currentDistance = 0;
            }

            return; // Skip position updates while recharging
        }

        // Rest of the existing update method remains the same
        if (this.isPlayerControlled) {
            if (!this.isSelected) {
                this.currentDistance = 0;
                return;
            }

            let targetDistance = this.normalDistance;

            if (state.spacebar) {
                targetDistance = this.spacebarDistance;
            } else if (state.tKey) {
                targetDistance = this.defenseDistance;
            }

            // Smoothly transition to the target distance
            this.currentDistance += (targetDistance - this.currentDistance) * this.speed;

            // Update angle to create orbiting effect
            this.angle += this.orbitSpeed;

            // Update position based on player coordinates
            this.x = playerX + this.currentDistance * Math.cos(this.angle);
            this.y = playerY + this.currentDistance * Math.sin(this.angle);
        } else {
            // If this petal belongs to an enemy, orbit around the enemy
            if (this.parentEnemy) {
                this.angle += this.orbitSpeed; // Update angle for orbiting
                this.currentDistance = this.enemyOrbitRadius; // Keep the distance based on the enemy
                this.x = this.parentEnemy.x + this.currentDistance * Math.cos(this.angle);
                this.y = this.parentEnemy.y + this.currentDistance * Math.sin(this.angle);
            }
        }
    }

    draw(ctx) {
        if (!this.isRecharging) {
            ctx.beginPath();
            ctx.arc(this.x, this.y, this.radius, 0, Math.PI * 2);
            ctx.fillStyle = this.color;
            ctx.fill();
        }
    }
}
