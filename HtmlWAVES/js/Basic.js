import { Petal } from './Petal.js';

export class BasicPetal extends Petal {
    constructor(rarity = 'common') {
        const color = '#FFFFFF';
        const radius = 10; // Fixed radius for all rarities
        super(color, radius, 'basic', true, null, rarity);

        // Customize recharge time by rarity
        this.rechargeTimeByRarity = {
            'common': 2000,
            'uncommon': 3000,
            'rare': 4000,
            'epic': 5000,
            'legendary': 6000,
            'mythic': 7000
        };

        // Override recharge time
        this.rechargeTime = this.rechargeTimeByRarity[rarity] || 3000;
    }

    calculateMaxHP() {
        const healthMap = {
            'common': 10,
            'uncommon': 30,
            'rare': 90,
            'epic': 270,
            'legendary': 500,
            'mythic': 1000,
            'ultra': 2500,
            'super': 10000
        };
        return healthMap[this.rarity] || 10;
    }

    checkCollisionWithMob(mob) {
        const dx = this.x - mob.x;
        const dy = this.y - mob.y;
        const distance = Math.sqrt(dx * dx + dy * dy);

        return distance < (this.radius + mob.size);
    }

    dealDamageToMob(mob) {
        if (this.isRecharging) return false;

        // Deal damage to mob
        mob.takeDamage(this.damage);

        // Reduce petal HP
        this.currentHP -= mob.health;

        // Always recharge when HP reaches 0, never permanently destroy
        if (this.currentHP <= 0) {
            this.destroy();
            return true; // Indicate petal is recharging
        }

        return false;
    }

    static getRadiusByRarity(rarity) {
        return 10; // Always return 10 regardless of rarity
    }

    calculateDamage() {
        const damageMap = {
            'common': 10,
            'uncommon': 30,
            'rare': 90,
            'epic': 270,
            'legendary': 500,
            'mythic': 1000,
            'ultra': 2500,
            'super': 10000
        };
        return damageMap[this.rarity] || 10;
    }

    draw(ctx) {
        // Modify drawing to show recharge state
        if (this.isRecharging) {
            ctx.globalAlpha = 0.3; // Semi-transparent when recharging
        }

        // Only draw if not completely invisible
        if (this.currentDistance > 0) {
            ctx.beginPath();
            ctx.arc(this.x, this.y, this.radius, 0, Math.PI * 2);
            ctx.fillStyle = this.color;
            ctx.fill();
        }

        ctx.globalAlpha = 1; // Reset alpha
    }
}