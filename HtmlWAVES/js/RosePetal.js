import {Petal} from "./Petal.js";

export class RosePetal extends Petal {
    constructor(rarity = 'common') {
        const color = '#e74c3c'; // Red color for rose
        let radius = 15; // Base radius

        // Adjust radius based on rarity
        if (['common', 'uncommon'].includes(rarity)) {
            radius *= 0.8;
        } else if (['rare', 'epic'].includes(rarity)) {
            radius *= 0.8;
        } else if (['legendary', 'mythic'].includes(rarity)) {
            radius *= 0.8;
        } else if (['ultra', 'super'].includes(rarity)) {
            radius *= 0.8;
        }

        super(color, radius, 'rose', true, null, rarity);

        // Load image
        this.image = new Image();
        this.image.src = 'res/rose.png';
        this.image.onerror = () => {
            console.error('Failed to load Rose Petal image');
        };

        // Healing configuration
        this.healRates = {
            'common': 5,
            'uncommon': 10,
            'rare': 15,
            'epic': 20,
            'legendary': 25,
            'mythic': 30,
            'ultra': 40,
            'super': 50
        };

        // Override recharge time
        this.rechargeTime = 4000; // 4 seconds

        // Healing rate
        this.healRate = this.healRates[rarity] || 10;

        // Animation properties
        this.animationProgress = 0;
        this.isHealing = false;
        this.healTarget = null;
    }

    calculateMaxHP() {
        const healthMap = {
            'common': 30,
            'uncommon': 40,
            'rare': 50,
            'epic': 60,
            'legendary': 70,
            'mythic': 80,
            'ultra': 90,
            'super': 100
        };
        return healthMap[this.rarity] || 30;
    }

    calculateDamage() {
        // Rose petals are focused on healing, so minimal damage
        const damageMap = {
            'common': 5,
            'uncommon': 10,
            'rare': 15,
            'epic': 20,
            'legendary': 25,
            'mythic': 30,
            'ultra': 40,
            'super': 50
        };
        return damageMap[this.rarity] || 5;
    }

    update(centerX, centerY, orbitRadius, angle) {
        // Check if player needs healing
        if (this.player && !this.isRecharging && this.player.health < this.player.maxHealth) {
            // Heal the player
            this.player.heal(this.healRate);

            // Destroy the petal after healing
            this.destroy();
        } else {
            // Normal orbital update
            super.update(centerX, centerY, orbitRadius, angle);
        }
    }


    draw(ctx) {
        if (!this.isRecharging) {
            ctx.save();

            // Draw image or fallback to color
            if (this.image && this.image.complete) {
                ctx.translate(this.x, this.y);
                ctx.drawImage(
                    this.image,
                    -this.radius,
                    -this.radius,
                    this.radius * 2,
                    this.radius * 2
                );
            } else {
                ctx.beginPath();
                ctx.arc(this.x, this.y, this.radius, 0, Math.PI * 2);
                ctx.fillStyle = this.color;
                ctx.fill();
            }

            ctx.restore();
        }
    }
    checkCollisionWithMob(mob) {
        const dx = this.x - mob.x;
        const dy = this.y - mob.y;
        const distance = Math.sqrt(dx * dx + dy * dy);

        return distance < (this.radius + mob.size);
    }

    dealDamageToMob(mob) {
        if (this.isRecharging) return false;

        // Rose petals do extremely minimal damage
        const damageAmount = Math.max(1, Math.floor(this.damage * 0.5)); // Very low damage
        mob.takeDamage(damageAmount);

        // Healing effect when touching mob
        if (this.player) {
            const healAmount = this.healRate * 0.5; // Slight healing on mob contact
            this.player.heal(healAmount);
        }

        // Reduce petal HP very gradually
        this.currentHP -= Math.max(mob.health * 0.05, 0.5);

        // Recharge when HP reaches 0
        if (this.currentHP <= 0) {
            this.destroy();
            return true; // Indicate petal is recharging
        }

        return false;
    }
    onAddToPlayer(player) {
        super.onAddToPlayer(player);
        this.player = player;
    }

    onRemoveFromPlayer() {
        this.player = null;
        super.onRemoveFromPlayer();
    }
}