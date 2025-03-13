export class Petal {
    constructor(color, radius, type, isPlayerControlled = false, parentEnemy = null, rarity = 'common') {
        this.color = color;
        this.radius = radius;
        this.type = type;
        this.angle = Math.random() * Math.PI * 2;

        this.distanceStates = {
            normal: 45,    // Default orbit distance
            expanded: 150, // Spacebar distance
            contracted: 30 // Ctrl distance
        };

        // Distances
        this.normalDistance = 150; // Default orbit distance
        this.orbitSpeed = isPlayerControlled ? 0.05 : 0.1; // Much slower for player petals
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

        this.isSelected = true;
        this.applyPlayerEffect = null; // Will be overridden by subclasses
        this.removePlayerEffect = null; // Will be overridden by subclasses
        this.petalDistanceStates = {
            normal: 45,    // Default orbit distance
            expanded: 150, // Spacebar distance
            contracted: 30 // Ctrl distance
        };
        this.currentPetalDistance = this.petalDistanceStates.normal;
        this.petalDistanceSmoothingFactor = 0.05;
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
        this.currentHP = 0; // Set HP to 0 during recharge

        // No removal, just recharging
    }

    calculateEnemyOrbitRadius(enemy) {
        return enemy.radius + this.radius + 20; // Add buffer space
    }

    update(centerX, centerY, distance, angle) {
        const currentTime = Date.now();

        if (this.isRecharging) {
            if (currentTime - this.lastDestroyedTime >= this.rechargeTime) {
                this.isRecharging = false;
                this.currentHP = this.maxHP;
            } else {
                return;
            }
        }

        this.x = centerX + distance * Math.cos(angle);
        this.y = centerY + distance * Math.sin(angle);
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
