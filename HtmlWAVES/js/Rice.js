import { Petal } from './Petal.js';

export class RicePetal extends Petal {
    constructor(rarity = 'common') {
        const color = '#F5DEB3'; // Wheat/rice-like color
        const radius = 10; // Fixed radius for all rarities
        super(color, radius, 'rice', true, null, rarity);

        // Rice Petal specific image
        this.image = new Image();
        this.image.src = 'res/rice.png';
        this.image.onerror = () => {
            console.error('Failed to load Rice Petal image');
        };

        // Extremely short, fixed recharge time for all rarities
        this.rechargeTime = 20;
    }

    calculateMaxHP() {
        const healthMap = {
            'common': 5,
            'uncommon': 10,
            'rare': 15,
            'epic': 20,
            'legendary': 25,
            'mythic': 30,
            'ultra': 35,
            'super': 40
        };
        return healthMap[this.rarity] || 5;
    }

    calculateDamage() {
        const damageMap = {
            'common': 5,
            'uncommon': 10,
            'rare': 15,
            'epic': 20,
            'legendary': 25,
            'mythic': 30,
            'ultra': 35,
            'super': 40
        };
        return damageMap[this.rarity] || 5;
    }

    static getRadiusByRarity(rarity) {
        return 10; // Always return 10 regardless of rarity
    }

    draw(ctx) {
        // Modify drawing to show recharge state and use image
        if (this.isRecharging) {
            ctx.globalAlpha = 0.3; // Semi-transparent when recharging
        }

        // Only draw if not completely invisible
        if (this.currentDistance > 0) {
            ctx.save();

            // Check if image is loaded
            if (this.image && this.image.complete && this.image.naturalWidth !== 0) {
                // Draw image
                ctx.translate(this.x, this.y);
                ctx.drawImage(
                    this.image,
                    -this.radius,
                    -this.radius,
                    this.radius * 2,
                    this.radius * 2
                );
            } else {
                // Fallback to drawing a rice-like shape
                ctx.beginPath();
                ctx.moveTo(this.x, this.y - this.radius * 0.7);
                ctx.lineTo(this.x + this.radius * 0.5, this.y + this.radius * 0.7);
                ctx.lineTo(this.x - this.radius * 0.5, this.y + this.radius * 0.7);
                ctx.closePath();
                ctx.fillStyle = this.color;
                ctx.fill();
            }

            ctx.restore();
        }

        ctx.globalAlpha = 1; // Reset alpha
    }

    dealDamageToMob(mob) {
        if (this.isRecharging) return false;

        // Deal very low damage to mob
        mob.takeDamage(this.damage);

        // Reduce petal HP very quickly due to extremely low health
        this.currentHP -= Math.max(mob.health, 5);

        // Only recharge when HP reaches 0
        if (this.currentHP <= 0) {
            this.destroy();
            return true; // Indicate petal is recharging
        }

        return false;
    }

    checkCollisionWithMob(mob) {
        const dx = this.x - mob.x;
        const dy = this.y - mob.y;
        const distance = Math.sqrt(dx * dx + dy * dy);

        return distance < (this.radius + mob.size);
    }
}