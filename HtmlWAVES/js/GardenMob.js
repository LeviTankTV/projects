import {Mob} from "./Mob.js";

export class ForestMob extends Mob {
    constructor(x, y, rarity) {
        super(x, y, rarity, 'forest');

        // Forest-specific modifications
        this.plantSymbiosis = this.calculatePlantSymbiosis();
        this.natureCamouflage = this.generateNatureCamouflage();
    }

    calculatePlantSymbiosis() {
        const symbiosisLevels = {
            'common': 1,
            'uncommon': 2,
            'rare': 3,
            'epic': 4,
            'legendary': 5,
            'mythic': 6,
            'ultra': 7,
            'super': 8
        };
        return symbiosisLevels[this.rarity] || 1;
    }

    generateNatureCamouflage() {
        const forestColors = [
            '#228B22', '#008000', '#006400', '#3CB371', '#2E8B57'
        ];
        return {
            baseColor: forestColors[Math.floor(Math.random() * forestColors.length)],
            pattern: Math.random() > 0.5 ? 'leafy' : 'bark'
        };
    }

    draw(ctx) {
        ctx.save();

        // Leafy or bark-like texture
        if (this.natureCamouflage.pattern === 'leafy') {
            ctx.beginPath();
            ctx.fillStyle = this.natureCamouflage.baseColor;
            ctx.arc(this.x, this.y, this.size, 0, Math.PI * 2);

            // Add leaf-like details
            for (let i = 0; i < 5; i++) {
                const angle = (i / 5) * Math.PI * 2;
                const leafSize = this.size / 3;
                ctx.moveTo(
                    this.x + Math.cos(angle) * this.size,
                    this.y + Math.sin(angle) * this.size
                );
                ctx.quadraticCurveTo(
                    this.x + Math.cos(angle) * (this.size + leafSize),
                    this.y + Math.sin(angle) * (this.size + leafSize),
                    this.x + Math.cos(angle) * this.size,
                    this.y + Math.sin(angle) * this.size
                );
            }

            ctx.fill();
        } else {
            // Bark-like texture
            ctx.beginPath();
            ctx.fillStyle = this.natureCamouflage.baseColor;
            ctx.ellipse(
                this.x, this.y,
                this.size, this.size * 1.2,
                Math.PI / 4, 0, Math.PI * 2
            );
            ctx.fill();
        }

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