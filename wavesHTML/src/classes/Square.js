import {Petal} from "./Petal.js";

export class SquarePetal extends Petal {
    constructor(rarity = 'common', isPlayerControlled = true) {
        // Define color based on rarity
        const colors = {
            common: '#4CAF50',     // Green
            uncommon: '#FFEB3B',   // Yellow
            rare: '#2196F3',       // Blue
            epic: '#9C27B0',       // Purple
            legendary: '#F44336',  // Red
            mythic: '#E91E63'      // Pink
        };

        const color = colors[rarity] || colors.common;

        // Square petals are typically larger and slower
        const radius = SquarePetal.getRadiusByRarity(rarity);

        super(color, radius, 'square', isPlayerControlled, null, rarity);

        // Unique square petal properties
        this.shape = 'square';
        this.rotationSpeed = 0.05;  // Rotation speed for visual effect
        this.currentRotation = 0;
        this.penetrationPower = this.calculatePenetrationPower();
        this.specialAbilityCharge = 0;
        this.specialAbilityThreshold = 100;

        // Modify some inherited properties for square petals
        this.orbitSpeed = 0.005;  // Even slower orbit
        this.normalDistance = 55; // Slightly larger orbit distance
        this.currentDistance = this.normalDistance;
    }

    static getRadiusByRarity(rarity) {
        const rarityRadii = {
            common: 8,
            uncommon: 10,
            rare: 12,
            epic: 14,
            legendary: 16,
            mythic: 20
        };
        return rarityRadii[rarity] || 8;
    }

    calculatePenetrationPower() {
        const penetrationMap = {
            'common': 1,
            'uncommon': 2,
            'rare': 3,
            'epic': 4,
            'legendary': 5,
            'mythic': 6
        };
        return penetrationMap[this.rarity] || 1;
    }

    // Override draw method to draw a square instead of circle
    draw(ctx, cameraX = 0, cameraY = 0) {
        if (this.isRecharging) return;

        ctx.save();
        ctx.translate(this.x - cameraX, this.y - cameraY);
        ctx.rotate(this.currentRotation);

        ctx.beginPath();
        const halfSize = this.radius;
        ctx.moveTo(-halfSize, -halfSize);
        ctx.lineTo(halfSize, -halfSize);
        ctx.lineTo(halfSize, halfSize);
        ctx.lineTo(-halfSize, halfSize);
        ctx.closePath();

        ctx.fillStyle = this.color;
        ctx.fill();

        // Add a border for visual distinction
        ctx.strokeStyle = 'rgba(0,0,0,0.3)';
        ctx.lineWidth = 2;
        ctx.stroke();

        ctx.restore();
    }

    // Override update to add rotation
    update(state, playerX, playerY) {
        super.update(state, playerX, playerY);

        // Add rotation
        this.currentRotation += this.rotationSpeed;

        // Charge special ability
        if (this.isPlayerControlled) {
            this.specialAbilityCharge += 0.1;
            if (this.specialAbilityCharge >= this.specialAbilityThreshold) {
                this.triggerSpecialAbility(playerX, playerY);
            }
        }
    }

    triggerSpecialAbility(playerX, playerY) {
        // Powerful area of effect damage
        const damageMultiplier = {
            'common': 1.5,
            'uncommon': 2,
            'rare': 2.5,
            'epic': 3,
            'legendary': 3.5,
            'mythic': 4
        };

        const ability = {
            type: 'aoe',
            damage: this.damage * damageMultiplier[this.rarity],
            radius: this.radius * 3,
            x: playerX,
            y: playerY
        };

        // Reset charge
        this.specialAbilityCharge = 0;

        return ability;
    }

    // Enhanced collision detection with penetration
    checkCollisionWithEnemy(enemy) {
        const dx = this.x - enemy.x;
        const dy = this.y - enemy.y;
        const distance = Math.sqrt(dx * dx + dy * dy);

        // Penetration check
        if (distance < (this.radius + enemy.radius)) {
            // More damage and ability to damage multiple enemies
            return {
                hit: true,
                damage: this.damage * this.penetrationPower,
                penetrationCount: this.penetrationPower
            };
        }

        return { hit: false };
    }

    // Optional player effect methods
    onAddToPlayer(player) {
        // Increase player's defense slightly
        player.maxHP += 10 * this.penetrationPower;
        player.currentHP = Math.min(player.currentHP + 10, player.maxHP);
    }

    onRemoveFromPlayer(player) {
        // Remove the defense bonus
        player.maxHP -= 10 * this.penetrationPower;
        player.currentHP = Math.min(player.currentHP, player.maxHP);
    }
}