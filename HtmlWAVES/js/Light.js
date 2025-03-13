import { BasicPetal } from './Basic.js';

export class LightPetal extends BasicPetal {
    constructor(rarity = 'common', type = 'standard') {
        const color = LightPetal.getLightColor(type);
        const radius = 8;
        super(rarity);

        this.type = type;
        this.color = color;
        this.radius = radius;

        // Different light configurations based on type and rarity
        this.lightConfigurations = {
            'standard': {
                'common': 1, 'uncommon': 2, 'rare': 2,
                'epic': 3, 'legendary': 3, 'mythic': 5,
                'ultra': 5, 'super': 5
            },
            'focused': {
                'common': 1, 'uncommon': 1, 'rare': 2,
                'epic': 2, 'legendary': 3, 'mythic': 3,
                'ultra': 4, 'super': 5
            },
            'spread': {
                'common': 2, 'uncommon': 3, 'rare': 3,
                'epic': 4, 'legendary': 4, 'mythic': 5,
                'ultra': 5, 'super': 5
            }
        };
        // Unique damage and HP configurations
        this.damageConfigurations = {
            'standard': {
                'common': 5, 'uncommon': 10, 'rare': 15,
                'epic': 20, 'legendary': 25, 'mythic': 30,
                'ultra': 40, 'super': 50
            },
            'focused': {
                'common': 8, 'uncommon': 15, 'rare': 22,
                'epic': 30, 'legendary': 38, 'mythic': 45,
                'ultra': 55, 'super': 65
            },
            'spread': {
                'common': 3, 'uncommon': 6, 'rare': 9,
                'epic': 12, 'legendary': 15, 'mythic': 18,
                'ultra': 22, 'super': 25
            }
        };

        this.hpConfigurations = {
            'standard': {
                'common': 10, 'uncommon': 15, 'rare': 20,
                'epic': 25, 'legendary': 30, 'mythic': 35,
                'ultra': 45, 'super': 55
            },
            'focused': {
                'common': 7, 'uncommon': 12, 'rare': 17,
                'epic': 22, 'legendary': 27, 'mythic': 32,
                'ultra': 40, 'super': 50
            },
            'spread': {
                'common': 15, 'uncommon': 20, 'rare': 25,
                'epic': 30, 'legendary': 35, 'mythic': 40,
                'ultra': 50, 'super': 60
            }
        };

        this.rechargeTimeByRarity = {
            'common': 500, 'uncommon': 600, 'rare': 700,
            'epic': 800, 'legendary': 900, 'mythic': 1000,
            'ultra': 1200, 'super': 1500
        };

        this.rechargeTime = this.rechargeTimeByRarity[rarity] || 500;
        this.lights = this.createLightComponents(rarity);
    }

    // Static method to get light color based on type
    static getLightColor(type) {
        const colors = {
            'standard': '#FFFFE0', // Soft light yellow
            'focused': '#FF4500',  // Bright orange-red
            'spread': '#00FFFF'    // Cyan
        };
        return colors[type] || '#FFFFE0';
    }

    createLightComponents(rarity) {
        const numLights = this.lightConfigurations[this.type][rarity] || 1;
        const lights = [];

        for (let i = 0; i < numLights; i++) {
            lights.push({
                x: 0,
                y: 0,
                maxHealth: this.calculateIndividualLightHP(rarity),
                health: this.calculateIndividualLightHP(rarity),
                damage: this.calculateIndividualLightDamage(rarity),
                isActive: true,
                lastDestroyedTime: 0,
                rechargeTime: this.rechargeTime / 2 // Faster individual recharge
            });
        }

        return lights;
    }


    calculateIndividualLightHP(rarity) {
        return this.hpConfigurations[this.type][rarity] || 10;
    }

    calculateIndividualLightDamage(rarity) {
        return this.damageConfigurations[this.type][rarity] || 5;
    }

    update(centerX, centerY, distance, angle) {
        super.update(centerX, centerY, distance, angle);

        const currentTime = Date.now();
        const numLights = this.lights.length;
        const spacing = this.radius * 1.4;

        this.lights.forEach((light, index) => {
            // Check if light needs recharging
            if (!light.isActive) {
                if (currentTime - light.lastDestroyedTime >= light.rechargeTime) {
                    // Recharge the light
                    light.isActive = true;
                    light.health = light.maxHealth;
                }
                return; // Skip positioning for inactive lights
            }

            // Calculate position for active lights
            const offsetAngle = (index / numLights) * Math.PI * 2;
            light.x = this.x + Math.cos(angle + offsetAngle) * spacing;
            light.y = this.y + Math.sin(angle + offsetAngle) * spacing;
        });
    }

    dealDamageToMob(mob) {
        if (this.isRecharging) return false;

        let totalDamageDealt = 0;
        let activeLightsCount = 0;

        // Deal damage independently for each active light
        this.lights.forEach(light => {
            if (light.isActive && this.calculateLightCollision(light, mob)) {
                mob.takeDamage(light.damage);
                totalDamageDealt += light.damage;

                // Reduce individual light health on collision
                light.health -= mob.damage || 1;

                // Deactivate light if its health reaches 0
                if (light.health <= 0) {
                    light.isActive = false;
                    light.lastDestroyedTime = Date.now();
                }

                activeLightsCount++;
            }
        });

        // Reduce petal HP based on total damage and active lights
        this.currentHP -= totalDamageDealt;

        // Check if petal should be destroyed
        if (this.currentHP <= 0 || this.lights.every(light => !light.isActive)) {
            this.destroy();
            return true;
        }

        return activeLightsCount > 0;
    }

    draw(ctx) {
        if (this.isRecharging) {
            ctx.globalAlpha = 0;
        }

        // Draw only active lights
        this.lights.forEach(light => {
            if (light.isActive) {
                ctx.beginPath();
                ctx.arc(light.x, light.y, this.radius, 0, Math.PI * 2);
                ctx.fillStyle = this.color;
                ctx.fill();
            }
        });

        ctx.globalAlpha = 1;
    }

    checkCollisionWithMob(mob) {
        // Check collision for each active light independently
        return this.lights.some(light =>
            light.isActive && this.calculateLightCollision(light, mob)
        );
    }

    calculateLightCollision(light, mob) {
        const dx = light.x - mob.x;
        const dy = light.y - mob.y;
        const distance = Math.sqrt(dx * dx + dy * dy);

        return distance < (this.radius + mob.size);
    }
}