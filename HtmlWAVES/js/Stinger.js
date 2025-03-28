import { Petal } from './Petal.js';

export class Stinger extends Petal {
    constructor(rarity = 'common') {
        const color = '#FF0000'; // Bright red for stinger
        let radius = 10; // Base radius
        if (['common', 'uncommon', 'rare', 'epic', 'legendary'].includes(rarity)) {
            radius *= 0.75; // Half size for onestinger
        } else if ([ 'mythic'].includes(rarity)) {
            radius *= 1.5; // 1.5x size for threestinger
        } else if (['ultra', 'super'].includes(rarity)) {
            radius *= 1.5; // Double size for pinger
        }
        super(color, radius, 'stinger', true, null, rarity);

        // Select image based on rarity
        this.image = new Image();
        if (['common', 'uncommon', 'rare', 'epic', 'legendary'].includes(rarity)) {
            this.image.src = 'res/onestinger.png';
        } else if (['mythic'].includes(rarity)) {
            this.image.src = 'res/threestinger.png';
        } else if (['ultra', 'super'].includes(rarity)) {
            this.image.src = 'res/pinger.png';
        }

        this.image.onerror = () => {
            console.error('Failed to load Stinger image');
        };

        // Extremely short recharge time
        this.rechargeTimeByRarity = {
            'common': 5500,
            'uncommon': 5500,
            'rare': 5500,
            'epic': 5500,
            'legendary': 1650,
            'mythic': 1650,
            'ultra': 500,
            'super': 500
        };

        // Override recharge time
        this.rechargeTime = this.rechargeTimeByRarity[rarity] || 500;
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
            'common': 100,
            'uncommon': 250,
            'rare': 500,
            'epic': 1000,
            'legendary': 2000,
            'mythic': 3000,
            'ultra': 10000,
            'super': 50000
        };
        return damageMap[this.rarity] || 100;
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
                // Fallback to drawing a stinger-like shape
                ctx.beginPath();
                ctx.moveTo(this.x, this.y - this.radius);
                ctx.lineTo(this.x + this.radius * 0.5, this.y + this.radius * 0.8);
                ctx.lineTo(this.x - this.radius * 0.5, this.y + this.radius * 0.8);
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