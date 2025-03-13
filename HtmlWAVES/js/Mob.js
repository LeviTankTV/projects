// mob.js
import { CanvasUtils } from './CanvasUtils.js';
export class Mob {
    constructor(x, y, rarity, type) {
        this.x = x;
        this.y = y;
        this.rarity = rarity;
        this.type = type;
        this.name = ""; // To be set by subclasses

        // Health and damage mechanics
        this.health = this.calculateHealth();
        this.maxHealth = this.health;

        // Other existing properties from previous implementation
        this.speed = this.calculateSpeed();
        this.size = this.calculateSize();
        this.aggroRange = this.calculateAggroRange();
        this.behavior = this.determineBehavior();

        // Death and animation properties
        this.isDead = false;
        this.deathTimer = 0;
        this.deathDuration = 500;
        this.deathFade = 1;
        this.deathScale = 1;

        // Health bar properties
        this.healthBarWidth = 40;
        this.healthBarHeight = 5;
        this.healthBarOffsetY = -15;
        this.nameOffsetY = -25;
        this.bodyDamage = this.calculateBodyDamage();

        this.targetX = x;
        this.targetY = y;
        this.moveSmoothing = 0.05; // Lower value = slower, smoother movement
        this.wanderChangeInterval = 2000; // Change wander direction every 2 seconds
        this.lastWanderChange = Date.now();
        this.wanderDirectionX = 0;
        this.wanderDirectionY = 0;
        this.activeStatusEffects = [];
        this.isStunned = false;
        this.currentSpeed = this.speed;
    }

    applyStatusEffect(effect) {
        // Remove any existing effect of the same type
        this.activeStatusEffects = this.activeStatusEffects.filter(e => e.type !== effect.type);

        const newEffect = {
            type: effect.type,
            duration: effect.duration,
            damagePerTick: effect.damagePerTick,
            tickInterval: effect.tickInterval,
            startTime: Date.now(),
            nextTickTime: Date.now() + effect.tickInterval,
            slowFactor: effect.slowFactor || 1, // Optional slow effect
            stunDuration: effect.stunDuration || 0 // Optional stun effect
        };

        this.activeStatusEffects.push(newEffect);
    }
    updateStatusEffects(currentTime) {
        this.activeStatusEffects = this.activeStatusEffects.filter(effect => {
            // Check if effect has expired
            if (currentTime - effect.startTime > effect.duration) {
                // Remove any stun effect when duration ends
                if (effect.type === 'stun') {
                    this.isStunned = false;
                }
                return false;
            }

            // Apply damage for poison-type effects
            if (effect.type === 'poison' && currentTime >= effect.nextTickTime) {
                this.takeDamage(effect.damagePerTick);
                effect.nextTickTime = currentTime + effect.tickInterval;
            }

            // Handle stun effect
            if (effect.type === 'stun') {
                this.isStunned = true;
            }

            // Handle slow effect
            if (effect.type === 'slow') {
                // Modify movement speed
                this.currentSpeed = this.speed * effect.slowFactor;
            }

            return true;
        });
    }
    calculateBodyDamage() {
        const bodyDamageMultipliers = {
            'common': 10,
            'uncommon': 15,
            'rare': 20,
            'epic': 30,
            'legendary': 40,
            'mythic': 50,
            'ultra': 70,
            'super': 100
        };
        return bodyDamageMultipliers[this.rarity] || 10;
    }

    takeDamage(amount) {
        this.health = Math.max(0, this.health - amount);
        if (this.health <= 0) {
            this.die();
        }
    }

    die() {
        if (!this.isDead) {
            this.isDead = true;
            this.deathTimer = Date.now();
            this.deathDuration = 100; // Duration of death animation
            this.deathScale = 1; // Initial scale up
            this.deathFade = 1; // Start fully opaque
            this.experienceValue = this.getExperienceValue();
        }
    }


    calculateHealth() {
        const rarityMultipliers = {
            'common': 10,
            'uncommon': 20,
            'rare': 40,
            'epic': 80,
            'legendary': 160,
            'mythic': 320,
            'ultra': 640,
            'super': 1280
        };
        return rarityMultipliers[this.rarity] || 10;
    }

    calculateSpeed() {
        const speedMultipliers = {
            'common': 1,
            'uncommon': 1.2,
            'rare': 1.5,
            'epic': 1.8,
            'legendary': 2.2,
            'mythic': 2.6,
            'ultra': 3,
            'super': 3.5
        };
        return speedMultipliers[this.rarity] || 1;
    }

    calculateSize() {
        const sizeMultipliers = {
            'common': 30,
            'uncommon': 46,
            'rare': 60,
            'epic': 75,
            'legendary': 100,
            'mythic': 150,
            'ultra': 500,
            'super': 1000
        };
        return sizeMultipliers[this.rarity] || 10;
    }

    calculateAggroRange() {
        const aggroRanges = {
            'common': 50,
            'uncommon': 75,
            'rare': 100,
            'epic': 150,
            'legendary': 200,
            'mythic': 250,
            'ultra': 300,
            'super': 350
        };
        return aggroRanges[this.rarity] || 50;
    }

    determineBehavior() {
        const behaviorTypes = ['passive', 'aggressive', 'neutral'];
        const rarityBehaviorMap = {
            'common': 'passive',
            'uncommon': 'neutral',
            'rare': 'neutral',
            'epic': 'aggressive',
            'legendary': 'aggressive',
            'mythic': 'aggressive',
            'ultra': 'aggressive',
            'super': 'aggressive'
        };
        return behaviorTypes.includes(this.rarity) ? this.rarity : behaviorTypes[Math.floor(Math.random() * behaviorTypes.length)];
    }

    update(player, arena, otherMobs) {
        if (this.isDead) return;
        const currentTime = Date.now();
        this.updateStatusEffects(currentTime);
        // Existing arena boundary check
        const dx = this.x - arena.x;
        const dy = this.y - arena.y;
        const distanceFromCenter = Math.sqrt(dx * dx + dy * dy);

        // Smoother arena boundary push-back
        if (distanceFromCenter > arena.radius - this.size) {
            const angle = Math.atan2(dy, dx);
            const pushBackFactor = 0.1; // Slower push-back

            // Gradual movement back to arena
            this.x += (arena.x + Math.cos(angle) * (arena.radius - this.size) - this.x) * pushBackFactor;
            this.y += (arena.y + Math.sin(angle) * (arena.radius - this.size) - this.y) * pushBackFactor;
        }

        if (otherMobs) {
            this.handleMobCollisions(otherMobs.filter(mob => mob !== this));
        }

        // Existing movement logic with smoother transitions
        if (this.behavior === 'aggressive') {
            this.moveTowardsPlayer(player);
        } else if (this.behavior === 'neutral') {
            this.wanderRandomly(arena);
        }
    }


    bodyAttack(player) {
        const damageMultiplier = {
            'common': 1,
            'uncommon': 1.5,
            'rare': 2,
            'epic': 3,
            'legendary': 4,
            'mythic': 5,
            'ultra': 7,
            'super': 10
        };

        const rarityMultiplier = damageMultiplier[this.rarity] || 1;
        const totalDamage = this.bodyDamage * rarityMultiplier;

        const actualDamage = player.takeDamage(totalDamage, this.type);

        return actualDamage;
    }




    moveTowardsPlayer(player) {
        // Check if mob is stunned
        if (this.isStunned) return;

        const dx = player.x - this.x;
        const dy = player.y - this.y;
        const distance = Math.sqrt(dx * dx + dy * dy);

        if (distance <= this.aggroRange) {
            // Use current speed (which can be modified by slow effects)
            const moveSpeed = this.currentSpeed || this.speed;

            // Smooth interpolation towards player
            this.targetX = player.x;
            this.targetY = player.y;

            // Gradual movement with potential speed modification
            const moveX = (this.targetX - this.x) * this.moveSmoothing * moveSpeed;
            const moveY = (this.targetY - this.y) * this.moveSmoothing * moveSpeed;

            this.x += moveX;
            this.y += moveY;
        }
    }
    getExperienceValue() {
        const experienceValues = {
            'common': 10,
            'uncommon': 30,
            'rare': 90,
            'epic': 300,
            'legendary': 500,
            'mythic': 1000,
            'ultra': 3000,
            'super': 15000
        };
        return experienceValues[this.rarity] || 10;
    }

    wanderRandomly(arena) {
        const currentTime = Date.now();

        // Periodically change wander direction
        if (currentTime - this.lastWanderChange > this.wanderChangeInterval) {
            this.lastWanderChange = currentTime;

            // Random direction with reduced magnitude
            this.wanderDirectionX = (Math.random() - 0.5) * this.speed * 0.5;
            this.wanderDirectionY = (Math.random() - 0.5) * this.speed * 0.5;
        }

        const newX = this.x + this.wanderDirectionX;
        const newY = this.y + this.wanderDirectionY;

        // Calculate distance from arena center
        const centerDx = newX - arena.x;
        const centerDy = newY - arena.y;
        const distanceFromCenter = Math.sqrt(centerDx * centerDx + centerDy * centerDy);

        // Smooth interpolation within arena
        if (distanceFromCenter <= arena.radius - this.size) {
            // Gradual movement
            this.x += (newX - this.x) * this.moveSmoothing;
            this.y += (newY - this.y) * this.moveSmoothing;
        }
    }

    handleMobCollisions(otherMobs) {
        for (const otherMob of otherMobs) {
            if (otherMob === this || otherMob.isDead) continue;

            const dx = this.x - otherMob.x;
            const dy = this.y - otherMob.y;
            const distance = Math.sqrt(dx * dx + dy * dy);
            const minDistance = this.size + otherMob.size;

            // If mobs are overlapping
            if (distance < minDistance) {
                // Calculate overlap with smoother separation
                const overlap = minDistance - distance;
                const angle = Math.atan2(dy, dx);

                // Softer push-apart mechanism
                const pushFactor = 0.1; // Slower, more gradual separation
                const totalSize = this.size + otherMob.size;
                const thisRatio = this.size / totalSize;
                const otherRatio = otherMob.size / totalSize;

                // Gradual separation
                this.x += Math.cos(angle) * (overlap * otherRatio * pushFactor);
                this.y += Math.sin(angle) * (overlap * otherRatio * pushFactor);
                otherMob.x -= Math.cos(angle) * (overlap * thisRatio * pushFactor);
                otherMob.y -= Math.sin(angle) * (overlap * thisRatio * pushFactor);
            }
        }
    }


    // In your Mob base class or individual mob classes
    draw(ctx) {
        if (this.isDead) {
            const elapsedTime = Date.now() - this.deathTimer;
            const progress = Math.min(1, elapsedTime / this.deathDuration);

            ctx.save();

            // More dramatic death effect
            // Pulsing explosion effect
            const baseSize = this.size;
            const pulseSize = baseSize * (1 + progress * 10); // Expand more dramatically
            const opacity = 1 - progress; // Linear fade out

            // Radial gradient for explosion effect
            const gradient = ctx.createRadialGradient(
                this.x, this.y, 0,
                this.x, this.y, pulseSize
            );

            // Gradient colors based on rarity
            const rarityColors = {
                'common': ['rgba(46, 204, 113, 0.8)', 'rgba(46, 204, 113, 0)'],
                'uncommon': ['rgba(241, 196, 15, 0.8)', 'rgba(241, 196, 15, 0)'],
                'rare': ['rgba(52, 152, 219, 0.8)', 'rgba(52, 152, 219, 0)'],
                'epic': ['rgba(155, 89, 182, 0.8)', 'rgba(155, 89, 182, 0)'],
                'legendary': ['rgba(231, 76, 60, 0.8)', 'rgba(231, 76, 60, 0)'],
                'mythic': ['rgba(52, 73, 94, 0.8)', 'rgba(52, 73, 94, 0)'],
                'ultra': ['rgba(255, 105, 180, 0.8)', 'rgba(255, 105, 180, 0)'],
                'super': ['rgba(255, 69, 0, 0.8)', 'rgba(255, 69, 0, 0)']
            };

            const [startColor, endColor] = rarityColors[this.rarity] ||
            ['rgba(150, 150, 150, 0.8)', 'rgba(150, 150, 150, 0)'];

            gradient.addColorStop(0, startColor);
            gradient.addColorStop(1, endColor);

            // Global alpha for overall fade
            ctx.globalAlpha = opacity;

            // Draw explosion
            ctx.beginPath();
            ctx.fillStyle = gradient;
            ctx.arc(this.x, this.y, pulseSize, 0, Math.PI * 2);
            ctx.fill();

            ctx.restore();
        } else {
            // Normal drawing for live mobs
            ctx.beginPath();
            ctx.fillStyle = this.getRarityColor();
            ctx.arc(this.x, this.y, this.size, 0, Math.PI * 2);
            ctx.fill();
        }
    }

    drawHealthBar(ctx) {
        const healthPercentage = this.health / this.maxHealth;

        // Health bar dimensions
        const barWidth = this.size * 1.5; // Proportional to mob size
        const barHeight = 6;
        const cornerRadius = 3;

        // Positioning based on mob size
        const barX = this.x - barWidth / 2;
        const barY = this.y + this.size + 5; // Position below the circle

        // Consistent positioning for name and rarity
        const nameY = this.y - this.size - 5; // Name above circle
        const rarityFontSize = Math.min(
            this.size / 6,  // Base size
            12  // Maximum size cap
        );

        const rarityY = barY + barHeight + (rarityFontSize / 2) + 5;

        // Background bar with rounded corners and border
        ctx.save();

        // Gradient background for health bar
        const barGradient = ctx.createLinearGradient(barX, barY, barX + barWidth, barY + barHeight);
        barGradient.addColorStop(0, 'rgba(0, 0, 0, 0.3)');
        barGradient.addColorStop(1, 'rgba(0, 0, 0, 0.5)');

        ctx.beginPath();
        CanvasUtils.roundedRect(ctx, barX, barY, barWidth, barHeight, cornerRadius);
        ctx.fillStyle = barGradient;
        ctx.fill();

        // Border with subtle glow
        ctx.strokeStyle = 'rgba(255, 255, 255, 0.4)';
        ctx.lineWidth = 1.5;
        ctx.shadowColor = 'rgba(255, 255, 255, 0.3)';
        ctx.shadowBlur = 5;
        ctx.stroke();

        // Health bar with rounded corners
        ctx.beginPath();
        const currentWidth = barWidth * healthPercentage;

        // Health bar color gradient
        const healthGradient = ctx.createLinearGradient(barX, barY, barX + currentWidth, barY + barHeight);
        const healthColors = this.getHealthBarGradient(healthPercentage);
        healthGradient.addColorStop(0, healthColors[0]);
        healthGradient.addColorStop(1, healthColors[1]);

        CanvasUtils.roundedRect(ctx, barX, barY, barWidth, barHeight, cornerRadius);
        ctx.fillStyle = healthGradient;
        ctx.fill();

        // Subtle health bar shine effect
        ctx.globalAlpha = 0.3;
        ctx.fillStyle = 'rgba(255, 255, 255, 0.5)';
        CanvasUtils.roundedRect(ctx, barX, barY, barWidth, barHeight/2, cornerRadius);
        ctx.fill();
        ctx.globalAlpha = 1;

        // Draw name above circle
        ctx.fillStyle = 'white';
        ctx.font = `bold ${this.size / 2}px 'Montserrat', Arial, sans-serif`;
        ctx.textAlign = 'center';
        ctx.fillText(
            this.name || this.type,
            this.x,
            nameY
        );

        // Draw rarity text with advanced styling
        ctx.save();

        // Text shadow for depth
        ctx.shadowColor = 'rgba(0,0,0,0.5)';
        ctx.shadowBlur = 10;
        ctx.shadowOffsetX = 2;
        ctx.shadowOffsetY = 2;

        // Gradient for text color
        const textGradient = ctx.createLinearGradient(
            this.x - barWidth/2,
            rarityY,
            this.x + barWidth/2,
            rarityY + 20
        );

        // Vibrant, smooth color transitions based on rarity
        const gradientColors = {
            'common': ['#2ecc71', '#27ae60'],
            'uncommon': ['#f1c40f', '#f39c12'],
            'rare': ['#3498db', '#2980b9'],
            'epic': ['#9b59b6', '#8e44ad'],
            'legendary': ['#e74c3c', '#c0392b'],
            'mythic': ['#34495e', '#2c3e50'],
            'ultra': ['#ff69b4', '#ff1493'],
            'super': ['#ff4500', '#ff6347']
        };

        const [startColor, endColor] = gradientColors[this.rarity] || ['#95a5a6', '#7f8c8d'];

        textGradient.addColorStop(0, startColor);
        textGradient.addColorStop(1, endColor);

        // Thick, smooth font
        ctx.font = `bold ${rarityFontSize}px 'Montserrat', Arial, sans-serif`;
        ctx.textAlign = 'center';

        // Outline effect for extra thickness and readability
        ctx.lineWidth = Math.max(1, rarityFontSize / 20);
        ctx.strokeStyle = 'rgba(0,0,0,0.3)';
        ctx.strokeText(
            this.rarity.toUpperCase(),
            this.x,
            rarityY
        );

        // Gradient fill
        ctx.fillStyle = textGradient;
        ctx.fillText(
            this.rarity.toUpperCase(),
            this.x,
            rarityY
        );

        // Optional: Subtle shine effect
        ctx.globalAlpha = 0.3;
        ctx.fillStyle = 'rgba(255,255,255,0.6)';
        ctx.fillText(
            this.rarity.toUpperCase(),
            this.x,
            rarityY - 1
        );

        ctx.restore();
        ctx.restore();
    }
// Helper method for health bar gradient colors
    getHealthBarGradient(percentage) {
        if (percentage > 0.7) {
            return ['#2ecc71', '#27ae60']; // Green
        }
        if (percentage > 0.3) {
            return ['#f39c12', '#d35400']; // Orange
        }
        return ['#e74c3c', '#c0392b']; // Red
    }

// Helper method for rounded rectangles
    drawName(ctx) {
        if (this.name) {
            CanvasUtils.drawNameWithBackground(ctx, {
                name: this.name,
                x: this.x,
                y: this.y + this.size + 20,
                width: 120,
                height: 20
            });
        }
    }

    getHealthBarColor(percentage) {
        if (percentage > 0.7) return 'green';
        if (percentage > 0.3) return 'orange';
        return 'red';
    }

    getRarityColor() {
        const rarityColors = {
            'common': '#2ecc71',      // Green
            'uncommon': '#f1c40f',    // Yellow
            'rare': '#3498db',        // Blue
            'epic': '#9b59b6',        // Purple
            'legendary': '#e74c3c',   // Red
            'mythic': '#34495e',      // Dark Gray/Black
            'ultra': '#ff69b4',       // Pink
            'super': 'linear-gradient(to right, #e74c3c, #ff4500)' // Orange-Red Gradient
        };
        return rarityColors[this.rarity] || '#95a5a6'; // Default to silver
    }

// Method to draw rarity text in WaveManager or LocalGameManager
    drawRarityText(ctx) {
        if (!this.isDead) {
            ctx.save();
            ctx.fillStyle = this.getRarityColor();
            ctx.font = 'bold 14px Arial';
            ctx.textAlign = 'left';
            ctx.fillText(
                this.rarity.toUpperCase(),
                50,  // X position near progress bar
                120  // Y position below progress bar
            );
            ctx.restore();
        }
    }
}
