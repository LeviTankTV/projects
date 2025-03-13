import { Petal } from './Petal.js';

export class IrisPetal extends Petal {
    constructor(rarity = 'rare') {
        const color = '#800080'; // Purple color for Iris
        const radius = 10; // Standard radius
        super(color, radius, 'iris', true, 'res/iris.png', rarity);

        // Poison-specific properties
        this.poisonDuration = 2100; // 2.1 seconds
        this.poisonDamagePerTick = this.calculatePoisonDamage();
        this.rechargeTime = 2100; // 2.1 seconds matching poison duration

        // Load sprite
        this.sprite = new Image();
        this.sprite.src = 'res/iris.png';
    }

    calculatePoisonDamage() {
        const poisonDamageMap = {
            'common': 5,
            'uncommon': 15,
            'rare': 45,
            'epic': 135,
            'legendary': 250,
            'mythic': 500,
            'ultra': 1250,
            'super': 5000
        };
        return poisonDamageMap[this.rarity] || 15;
    }

    applyPoison(mob) {
        if (this.isRecharging) return false;

        // Apply poison effect
        mob.applyStatusEffect({
            type: 'poison',
            duration: this.poisonDuration,
            damagePerTick: this.poisonDamagePerTick,
            tickInterval: 500 // Damage every 0.5 seconds
        });

        // Reduce petal HP
        this.currentHP -= 10; // Small HP reduction on use

        // Check for recharge
        if (this.currentHP <= 0) {
            this.destroy();
            return true;
        }

        return false;
    }

    dealDamageToMob(mob) {
        return this.applyPoison(mob);
    }

    draw(ctx) {
        // Modify drawing to show recharge state
        if (this.isRecharging) {
            ctx.globalAlpha = 0.3; // Semi-transparent when recharging
        }

        // Draw sprite instead of simple circle
        if (this.currentDistance > 0 && this.sprite.complete) {
            ctx.save();
            ctx.beginPath();
            ctx.arc(this.x, this.y, this.radius, 0, Math.PI * 2);
            ctx.clip(); // Clip to circular area

            // Draw image centered and scaled
            ctx.drawImage(
                this.sprite,
                this.x - this.radius,
                this.y - this.radius,
                this.radius * 2,
                this.radius * 2
            );

            ctx.restore();
        }

        ctx.globalAlpha = 1; // Reset alpha
    }

    // Optional: Additional method for mob interaction
    checkCollisionWithMob(mob) {
        const dx = this.x - mob.x;
        const dy = this.y - mob.y;
        const distance = Math.sqrt(dx * dx + dy * dy);

        return distance < (this.radius + mob.size);
    }
}