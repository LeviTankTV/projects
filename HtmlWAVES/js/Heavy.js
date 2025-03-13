import { Petal } from './Petal.js';

export class HeavyPetal extends Petal {
    constructor(rarity = 'rare') {
        const color = '#4A4A4A'; // Dark gray color
        const radius = 15; // Slightly larger radius to represent "heavy"
        super(color, radius, 'heavy', true, null, rarity);

        // Heavy Petal specific image
        this.image = new Image();
        this.image.src = 'res/heavy.png';
        this.image.onerror = () => {
            console.error('Failed to load Heavy Petal image');
        };

        // Consistent recharge time across all rarities
        this.rechargeTime = 8000;
    }

    calculateMaxHP() {
        const healthMap = {
            'common': 5000,
            'uncommon': 10000,
            'rare': 20000,
            'epic': 40000,
            'legendary': 80000,
            'mythic': 160000,
            'ultra': 320000,
            'super': 640000
        };
        return healthMap[this.rarity] || 20000;
    }

    calculateDamage() {
        const damageMap = {
            'common': 10,
            'uncommon': 20,
            'rare': 40,
            'epic': 80,
            'legendary': 160,
            'mythic': 320,
            'ultra': 640,
            'super': 1280
        };
        return damageMap[this.rarity] || 40;
    }

    static getRadiusByRarity(rarity) {
        // Slightly increase radius for higher rarities
        const baseRadius = 15;
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
                // Fallback to drawing a heavy-looking shape
                ctx.beginPath();
                ctx.rect(
                    this.x - this.radius,
                    this.y - this.radius,
                    this.radius * 2,
                    this.radius * 2
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

        // Very low damage compared to health
        mob.takeDamage(this.damage);

        // Reduce petal HP even less aggressively due to extremely high health
        this.currentHP -= Math.max(mob.health * 0.3, 5);

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