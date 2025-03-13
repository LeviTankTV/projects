import {Mob} from "./Mob.js";

export class DesertMob extends Mob {
    constructor(x, y, rarity) {
        super(x, y, rarity, 'desert');

        // Desert-specific modifications
        this.sandStormChance = this.calculateSandStormChance();
        this.heatResistance = this.calculateHeatResistance();
        this.camouflagePattern = this.generateCamouflagePattern();
    }

    calculateSandStormChance() {
        const sandStormChances = {
            'common': 0.1,
            'uncommon': 0.2,
            'rare': 0.3,
            'epic': 0.4,
            'legendary': 0.5,
            'mythic': 0.6,
            'ultra': 0.7,
            'super': 0.8
        };
        return sandStormChances[this.rarity] || 0.1;
    }

    calculateHeatResistance() {
        const heatResistanceValues = {
            'common': 10,
            'uncommon': 20,
            'rare': 30,
            'epic': 40,
            'legendary': 50,
            'mythic': 60,
            'ultra': 70,
            'super': 80
        };
        return heatResistanceValues[this.rarity] || 10;
    }

    generateCamouflagePattern() {
        const sandColors = [
            '#F4D03F', '#F5CBA7', '#D35400', '#CA6F1E', '#AF601A'
        ];
        return {
            baseColor: sandColors[Math.floor(Math.random() * sandColors.length)],
            pattern: Math.random() > 0.5 ? 'spotted' : 'gradient'
        };
    }

    draw(ctx) {
        ctx.save();

        // Apply camouflage
        if (this.camouflagePattern.pattern === 'spotted') {
            // Create a spotted texture
            for (let i = 0; i < 5; i++) {
                ctx.beginPath();
                ctx.fillStyle = this.getRarityColor();
                ctx.globalAlpha = 0.3;
                ctx.arc(
                    this.x + Math.random() * this.size * 2 - this.size,
                    this.y + Math.random() * this.size * 2 - this.size,
                    this.size / 3,
                    0,
                    Math.PI * 2
                );
                ctx.fill();
            }
        }

        // Main mob drawing
        ctx.beginPath();
        ctx.fillStyle = this.camouflagePattern.baseColor;
        ctx.arc(this.x, this.y, this.size, 0, Math.PI * 2);
        ctx.fill();

        ctx.restore();

        // Dynamic health bar positioning
        this.healthBarOffsetY = this.size + 10;
        this.nameOffsetY = this.size + 20;

        if (!this.isDead) {
            this.drawHealthBar(ctx);
            this.drawName(ctx);
        }
    }
}