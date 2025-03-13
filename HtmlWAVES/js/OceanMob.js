import {Mob} from "./Mob.js";

export class OceanMob extends Mob {
    constructor(x, y, rarity) {
        super(x, y, rarity, 'ocean');

        // Ocean-specific modifications
        this.swimPattern = this.generateSwimPattern();
        this.bioluminescence = this.calculateBioluminescence();
    }

    generateSwimPattern() {
        return {
            amplitude: Math.random() * 5,
            frequency: Math.random() * 0.1,
            phase: Math.random() * Math.PI * 2
        };
    }

    calculateBioluminescence() {
        const bioluminescenceLevels = {
            'common': 0.1,
            'uncommon': 0.2,
            'rare': 0.3,
            'epic': 0.4,
            'legendary': 0.5,
            'mythic': 0.6,
            'ultra': 0.7,
            'super': 0.8
        };
        return bioluminescenceLevels[this.rarity] || 0.1;
    }
    update(player, arena) {
        if (this.isDead) return;

        const time = Date.now() * 0.001;
        const { amplitude, frequency, phase } = this.swimPattern;

        // Sinusoidal movement
        this.x += Math.cos(time * frequency + phase) * amplitude;
        this.y += Math.sin(time * frequency + phase) * amplitude;

        // Existing movement logic
        if (this.behavior === 'aggressive') {
            this.moveTowardsPlayer(player);
        } else if (this.behavior === 'neutral') {
            this.oceanWander(arena);
        }
    }

    oceanWander(arena) {
        // More fluid, wave-like movement
        const boundaries = {
            minX: this.size,
            maxX: arena.width - this.size,
            minY: this.size,
            maxY: arena.height - this.size
        };

        // Constrain to arena with elastic bounce
        if (this.x < boundaries.minX || this.x > boundaries.maxX) {
            this.swimPattern.phase *= -1;
        }
        if (this.y < boundaries.minY || this.y > boundaries.maxY) {
            this.swimPattern.frequency *= -1;
        }
    }

    draw(ctx) {
        ctx.save();

        // Bioluminescent effect
        const glowIntensity = this.bioluminescence;
        const glowColor = this.getRarityColor();

        // Create a radial gradient for bioluminescent glow
        const gradient = ctx.createRadialGradient(
            this.x, this.y, 0,
            this.x, this.y, this.size * 1.5
        );
        gradient.addColorStop(0, `rgba(${this.hexToRgb(glowColor)}, ${glowIntensity})`);
        gradient.addColorStop(1, 'transparent');

        // Underwater-like shape with tentacle-like extensions
        ctx.beginPath();
        ctx.fillStyle = gradient;

        // Main body
        ctx.ellipse(
            this.x, this.y,
            this.size, this.size * 1.3,
            Math.PI / 4, 0, Math.PI * 2
        );

        // Tentacle-like extensions
        for (let i = 0; i < 4; i++) {
            const angle = (i / 4) * Math.PI * 2;
            const tentacleLength = this.size * 0.7;

            ctx.moveTo(this.x, this.y);
            ctx.quadraticCurveTo(
                this.x + Math.cos(angle) * tentacleLength,
                this.y + Math.sin(angle) * tentacleLength,
                this.x + Math.cos(angle) * (this.size * 1.5),
                this.y + Math.sin(angle) * (this.size * 1.5)
            );
        }

        ctx.fill();

        ctx.restore();

        // Dynamic health bar positioning based on mob size
        this.healthBarOffsetY = this.size + 15;
        this.nameOffsetY = this.size + 25;

        if (!this.isDead) {
            this.drawHealthBar(ctx);
            this.drawName(ctx);
        }
    }

    // Utility method to convert hex to RGB
    hexToRgb(hex) {
        const shorthandRegex = /^#?([a-f\d])([a-f\d])([a-f\d])$/i;
        hex = hex.replace(shorthandRegex, (m, r, g, b) => r + r + g + g + b + b);
        const result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
        return result
            ? `${parseInt(result[1], 16)}, ${parseInt(result[2], 16)}, ${parseInt(result[3], 16)}`
            : '255, 255, 255';
    }
}