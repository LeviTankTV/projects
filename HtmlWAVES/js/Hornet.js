import {Mob} from "./Mob.js";

export class Hornet extends Mob {
    constructor(x, y, rarity) {
        super(x, y, rarity, 'Hornet');

        // Always aggressive
        this.behavior = 'aggressive';

        // Hornet-specific health based on rarity
        this.healthByRarity = {
            'common': 50,
            'uncommon': 100,
            'rare': 200,
            'epic': 400,
            'legendary': 800,
            'mythic': 1600,
            'ultra': 3200,
            'super': 6400
        };

        // Hornet-specific body damage
        this.bodyDamageByRarity = {
            'common': 15,
            'uncommon': 30,
            'rare': 60,
            'epic': 120,
            'legendary': 240,
            'mythic': 480,
            'ultra': 960,
            'super': 1920
        };

        // Override health and body damage
        this.health = this.healthByRarity[rarity] || 200;
        this.maxHealth = this.health;
        this.bodyDamage = this.bodyDamageByRarity[rarity] || 60;

        // Adjust size for rarities
        const sizeMultipliers = {
            'common': 1,
            'uncommon': 1.2,
            'rare': 1.4,
            'epic': 1.6,
            'legendary': 1.8,
            'mythic': 2,
            'ultra': 2.5,
            'super': 3
        };
        this.size *= sizeMultipliers[rarity] || 1;

        this.mobType = 'shooter';
        this.minShootingDistance = 300; // Minimum distance to start shooting
        this.maxShootingDistance = 500; // Maximum shooting range
        this.preferredDistance = 350; // Ideal distance from player

        // Missile properties
        this.missiles = [];
        this.lastMissileTime = 0;
        this.missileInterval = 6000; // Changed from 2 to 6000 milliseconds (6 seconds

        // Missile parameters based on rarity
        this.missileParametersByRarity = {
            'common': {
                speed: 3,
                damage: 10,
                health: 5,
                size: 10
            },
            'uncommon': {
                speed: 4,
                damage: 20,
                health: 10,
                size: 15
            },
            'rare': {
                speed: 5,
                damage: 40,
                health: 20,
                size: 20
            },
            'epic': {
                speed: 6,
                damage: 80,
                health: 40,
                size: 25
            },
            'legendary': {
                speed: 7,
                damage: 160,
                health: 80,
                size: 30
            },
            'mythic': {
                speed: 8,
                damage: 320,
                health: 160,
                size: 35
            },
            'ultra': {
                speed: 9,
                damage: 640,
                health: 320,
                size: 40
            },
            'super': {
                speed: 10,
                damage: 1280,
                health: 640,
                size: 45
            }
        };

        // Set current missile parameters
        this.missileParams = this.missileParametersByRarity[rarity] ||
            this.missileParametersByRarity['common'];

        // Hornet image
        this.image = new Image();
        this.image.src = 'res/Hornet.png';
        this.player = null; // Add this line
    }

    update(player, arena, otherMobs) {
        if (this.isDead) return;
        const currentTime = Date.now();
        this.updateStatusEffects(currentTime);
        // Shooter-specific movement logic
        const dx = player.x - this.x;
        const dy = player.y - this.y;
        const distanceToPlayer = Math.sqrt(dx * dx + dy * dy);

        // If too close, move away
        if (distanceToPlayer < this.minShootingDistance) {
            this.x -= (dx / distanceToPlayer) * this.speed;
            this.y -= (dy / distanceToPlayer) * this.speed;
        }
        // If too far, move closer to preferred distance
        else if (distanceToPlayer > this.maxShootingDistance) {
            this.x += (dx / distanceToPlayer) * this.speed;
            this.y += (dy / distanceToPlayer) * this.speed;
        }

        // Check arena boundary
        const centerDx = this.x - arena.x;
        const centerDy = this.y - arena.y;
        const distanceFromCenter = Math.sqrt(centerDx * centerDx + centerDy * centerDy);

        // Ensure staying within arena
        if (distanceFromCenter > arena.radius - this.size) {
            const angle = Math.atan2(centerDy, centerDx);
            this.x = arena.x + Math.cos(angle) * (arena.radius - this.size);
            this.y = arena.y + Math.sin(angle) * (arena.radius - this.size);
        }

        // Shoot missiles when in preferred shooting range
        if (distanceToPlayer >= this.minShootingDistance &&
            distanceToPlayer <= this.maxShootingDistance) {
            const currentTime = Date.now();
            if (currentTime - this.lastMissileTime > this.missileInterval) {
                this.shootMissile(player, arena);
                this.lastMissileTime = currentTime;
            }
        }

        // Update and manage missiles
        this.updateMissiles(player, arena, otherMobs);

        // Handle mob collisions
        if (otherMobs) {
            this.handleMobCollisions(otherMobs.filter(mob => mob !== this));
        }
    }

    shootMissile(player, arena) {
        const angle = Math.atan2(player.y - this.y, player.x - this.x);
        const missile = {
            x: this.x,
            y: this.y,
            angle: angle,
            speed: this.missileParams.speed,
            damage: this.missileParams.damage,
            health: this.missileParams.health,
            size: this.missileParams.size,
            length: this.missileParams.size * 2 || 20,  // Add a default length
            maxHealth: this.missileParams.health
        };
        this.missiles.push(missile);
    }


    updateMissiles(player, arena, otherMobs) {
        for (let i = this.missiles.length - 1; i >= 0; i--) {
            const missile = this.missiles[i];

            // Move missile
            missile.x += Math.cos(missile.angle) * missile.speed;
            missile.y += Math.sin(missile.angle) * missile.speed;

            // Check collision with player and petals
            this.checkMissileCollisions(missile, player, otherMobs);

            // Check arena boundary
            const centerDx = missile.x - arena.x;
            const centerDy = missile.y - arena.y;
            const distanceFromCenter = Math.sqrt(centerDx * centerDx + centerDy * centerDy);

            // Remove missile if out of arena or health depleted
            if (distanceFromCenter > arena.radius || missile.health <= 0) {
                this.missiles.splice(i, 1);
            }
        }
    }

    checkMissileCollisions(missile, player, otherMobs) {
        // Check collision with player
        const playerDx = missile.x - player.x;
        const playerDy = missile.y - player.y;
        const playerDistance = Math.sqrt(playerDx * playerDx + playerDy * playerDy);

        if (playerDistance < player.radius + missile.size) {
            player.takeDamage(missile.damage);
            missile.health = 0; // Destroy missile on hit
        }

        // Check collision with player's petals
        player.selectedPetals.forEach(petal => {
            const petalDx = missile.x - petal.x;
            const petalDy = missile.y - petal.y;
            const petalDistance = Math.sqrt(petalDx * petalDx + petalDy * petalDy);

            if (petalDistance < petal.radius + missile.size) {
                petal.currentHP -= missile.damage;
                missile.health -= petal.currentHP; // Missile takes damage too

                // Check if petal is destroyed
                if (petal.currentHP <= 0) {
                    petal.destroy();
                }

                // Check if missile is destroyed
                if (missile.health <= 0) {
                    missile.health = 0;
                }
            }
        });
    }

    updateShooterMovement(player, arena) {
        const dx = player.x - this.x;
        const dy = player.y - this.y;
        const distanceToPlayer = Math.sqrt(dx * dx + dy * dy);

        // If too close, move away
        if (distanceToPlayer < this.minShootingDistance) {
            this.x -= (dx / distanceToPlayer) * this.speed;
            this.y -= (dy / distanceToPlayer) * this.speed;
        }
        // If too far, move closer to preferred distance
        else if (distanceToPlayer > this.maxShootingDistance) {
            this.x += (dx / distanceToPlayer) * this.speed;
            this.y += (dy / distanceToPlayer) * this.speed;
        }
        // Otherwise, maintain distance and shoot
        else {
            // Shoot missiles periodically
            const currentTime = Date.now();
            if (currentTime - this.lastMissileTime > this.missileInterval) {
                this.shootMissile(player, arena);
                this.lastMissileTime = currentTime;
            }
        }
    }

    draw(ctx, player) {
        if (this.isDead) {
            super.draw(ctx);
            return;
        }

        // Draw Hornet
        ctx.save();
        ctx.translate(this.x, this.y);

        // Safely check if player exists before calculating angle
        let angle = 0;
        if (player) {
            const dx = player.x - this.x;
            const dy = player.y - this.y;
            angle = Math.atan2(dy, dx);
        }

        // Rotate to face player
        ctx.rotate(angle + Math.PI+103);

        // Use image if loaded, otherwise draw basic shape
        if (this.image.complete && this.image.naturalWidth !== 0) {
            ctx.drawImage(
                this.image,
                -this.size,
                -this.size,
                this.size * 2,
                this.size * 2
            );
        } else {
            // Fallback drawing
            ctx.beginPath();
            ctx.fillStyle = this.getRarityColor();
            ctx.arc(0, 0, this.size, 0, Math.PI * 2);
            ctx.fill();
        }

        ctx.restore();

        // Draw missiles with safety check
        this.missiles.forEach(missile => {
            ctx.save();
            ctx.translate(missile.x, missile.y);

            // Rotate to point sharp end towards player
            ctx.rotate(missile.angle + -Math.PI/2);

            // Outer black frame
            ctx.beginPath();
            ctx.moveTo(0, missile.length);
            ctx.lineTo(-missile.size / 1.8, -missile.size / 1.8);
            ctx.lineTo(missile.size / 1.8, -missile.size / 1.8);
            ctx.closePath();
            ctx.fillStyle = 'black';
            ctx.fill();

            // Inner dark gray frame
            ctx.beginPath();
            ctx.moveTo(0, missile.length * 0.9);
            ctx.lineTo(-missile.size / 2.2, -missile.size / 2.2);
            ctx.lineTo(missile.size / 2.2, -missile.size / 2.2);
            ctx.closePath();
            ctx.fillStyle = '#333333';
            ctx.fill();

            // Main missile body
            ctx.beginPath();
            ctx.moveTo(0, missile.length);
            ctx.lineTo(-missile.size / 2.5, -missile.size / 2.5);
            ctx.lineTo(missile.size / 2.5, -missile.size / 2.5);
            ctx.closePath();
            ctx.fillStyle = 'darkred';
            ctx.fill();

            // Subtle highlight
            ctx.beginPath();
            ctx.moveTo(0, missile.length * 0.7);
            ctx.lineTo(-missile.size / 3, -missile.size / 3);
            ctx.lineTo(missile.size / 3, -missile.size / 3);
            ctx.closePath();
            ctx.fillStyle = 'rgba(255,255,255,0.3)';
            ctx.fill();

            ctx.restore();
        });

        // Draw health bar and name
        this.drawHealthBar(ctx);
        this.drawName(ctx);
    }
    moveTowardsPlayer(player) {
        const dx = player.x - this.x;
        const dy = player.y - this.y;
        const distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            const moveRatio = Math.min(this.speed, distance);
            this.x += (dx / distance) * moveRatio;
            this.y += (dy / distance) * moveRatio;
        }
    }
}