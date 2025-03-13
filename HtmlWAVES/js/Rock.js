import { Petal } from './Petal.js';

export class RockPetal extends Petal {
    constructor(rarity = 'rare') {
        const color = '#8B4513'; // Earthy brown color
        const radius = 10; // Fixed radius for all rarities
        super(color, radius, 'rock', true, null, rarity);

        // Rock Petal specific image
        this.image = new Image();
        this.image.src = 'res/rockpetal.png';
        this.image.onerror = () => {
            console.error('Failed to load Rock Petal image');
        };

        // Customize recharge time by rarity - longer for rock petals
        this.rechargeTimeByRarity = {
            'common': 7000,
            'uncommon': 7000,
            'rare': 7000,
            'epic': 7000,
            'legendary': 7000,
            'mythic': 7000
        };

        // Override recharge time
        this.rechargeTime = this.rechargeTimeByRarity[rarity] || 7000;
    }

    calculateMaxHP() {
        const healthMap = {
            'common': 100,
            'uncommon': 250,
            'rare': 500,
            'epic': 1000,
            'legendary': 2000,
            'mythic': 4000,
            'ultra': 8000,
            'super': 16000
        };
        return healthMap[this.rarity] || 500;
    }

    calculateDamage() {
        const damageMap = {
            'common': 20,
            'uncommon': 40,
            'rare': 80,
            'epic': 160,
            'legendary': 320,
            'mythic': 640,
            'ultra': 1280,
            'super': 2560
        };
        return damageMap[this.rarity] || 80;
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
                // Fallback to drawing a rock-like shape
                ctx.beginPath();
                ctx.moveTo(this.x, this.y - this.radius);
                ctx.lineTo(this.x + this.radius * 0.8, this.y - this.radius * 0.5);
                ctx.lineTo(this.x + this.radius * 0.5, this.y + this.radius * 0.8);
                ctx.lineTo(this.x - this.radius * 0.5, this.y + this.radius * 0.8);
                ctx.lineTo(this.x - this.radius * 0.8, this.y - this.radius * 0.5);
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

        // Deal damage to mob
        mob.takeDamage(this.damage);

        // Reduce petal HP less aggressively due to high health
        this.currentHP -= Math.max(mob.health * 0.5, 10);

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