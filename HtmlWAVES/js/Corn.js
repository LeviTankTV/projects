import { Petal } from './Petal.js';

export class CornPetal extends Petal {
    constructor(rarity = 'rare', type = 'standard') {
        const color = '#F5DEB3'; // Wheat/corn color
        const radius = 12; // Slightly smaller than Heavy Petal
        super(color, radius, 'corn', true, type, rarity);

        // Corn Petal specific image
        this.image = new Image();
        this.image.src = `res/corn.png`;
        this.image.onerror = () => {
            console.error('Failed to load Corn Petal image');
        };

        // Consistent recharge time across all rarities (6 seconds)
        this.rechargeTime = 6000;
    }

    calculateMaxHP() {
        const healthMap = {
            'common': 2000,
            'uncommon': 4000,
            'rare': 8000,
            'epic': 16000,
            'legendary': 32000,
            'mythic': 64000,
            'ultra': 128000,
            'super': 256000
        };
        return healthMap[this.rarity] || 8000;
    }

    calculateDamage() {
        const damageMap = {
            'common': 5,
            'uncommon': 10,
            'rare': 20,
            'epic': 40,
            'legendary': 80,
            'mythic': 160,
            'ultra': 320,
            'super': 640
        };
        return damageMap[this.rarity] || 20;
    }

    static getRadiusByRarity(rarity) {
        // Slightly increase radius for higher rarities
        const baseRadius = 12;
        const rarityMultipliers = {
            'common': 1,
            'uncommon': 1.1,
            'rare': 1.2,
            'epic': 1.3,
            'legendary': 1.4,
            'mythic': 1.5,
            'ultra': 1.6,
            'super': 1.7
        };
        return baseRadius * (rarityMultipliers[rarity] || 1);
    }

    draw(ctx) {
        if (this.isRecharging) {
            ctx.globalAlpha = 0.3; // Semi-transparent when recharging
        }

        if (this.currentDistance > 0) {
            ctx.save();

            if (this.image && this.image.complete && this.image.naturalWidth !== 0) {
                ctx.translate(this.x, this.y);
                ctx.drawImage(
                    this.image,
                    -this.radius,
                    -this.radius,
                    this.radius * 2,
                    this.radius * 2
                );
            } else {
                // Fallback to drawing a corn-like shape
                ctx.beginPath();
                ctx.ellipse(
                    this.x,
                    this.y,
                    this.radius,
                    this.radius * 0.7,
                    Math.PI / 4,
                    0,
                    2 * Math.PI
                );
                ctx.fillStyle = this.color;
                ctx.fill();
            }

            ctx.restore();
        }

        ctx.globalAlpha = 1; // Reset alpha
    }

    dealDamageToMob(mob) {
        if (this.isRecharging) return false;

        // Small damage compared to health
        mob.takeDamage(this.damage);

        // Reduce petal HP less aggressively
        this.currentHP -= Math.max(mob.health * 0.2, 3);

        if (this.currentHP <= 0) {
            this.destroy();
            return true;
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