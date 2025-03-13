import {Petal} from "./Petal.js";

export class PentagonPetal extends Petal {
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

        // Pentagon petals are typically larger and more powerful
        const radius = PentagonPetal.getRadiusByRarity(rarity);

        super(color, radius, 'pentagon', isPlayerControlled, null, rarity);

        // Unique pentagon petal properties
        this.shape = 'pentagon';
        this.rotationSpeed = 0.04;  // Rotation speed for visual effect
        this.currentRotation = 0;
        this.specialAbilityCharge = 0;
        this.specialAbilityThreshold = 100;

        // Modify some inherited properties for pentagon petals
        this.orbitSpeed = 0.007;  // Slightly slower orbit
        this.normalDistance = 50; // Slightly larger orbit distance
        this.currentDistance = this.normalDistance;
    }

    static getRadiusByRarity(rarity) {
        const rarityRadii = {
            common: 7,
            uncommon: 9,
            rare: 11,
            epic: 13,
            legendary: 15,
            mythic: 20
        };
        return rarityRadii[rarity] || 7;
    }

    // Override draw method to draw a pentagon instead of a circle
    draw(ctx, cameraX = 0, cameraY = 0) {
        if (this.isRecharging) return;

        ctx.save();
        ctx.translate(this.x - cameraX, this.y - cameraY);
        ctx.rotate(this.currentRotation);

        ctx.beginPath();
        const angle = (2 * Math.PI) / 5; // Angle for pentagon
        const halfSize = this.radius;

        for (let i = 0; i < 5; i++) {
            const x = halfSize * Math.cos(angle * i);
            const y = halfSize * Math.sin(angle * i);
            ctx.lineTo(x, y);
        }
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

    // Optional player effect methods
    onAddToPlayer(player) {
        // Increase player's damage slightly
        player.bodyDamage += 5; // Increase damage by 5
    }

    onRemoveFromPlayer(player) {
        // Remove the damage bonus
        player.bodyDamage -= 5; // Decrease damage by 5
    }
}