import {Petal} from "./Petal.js";

export class Stinger extends Petal {
    constructor(rarity = 'common', isPlayerControlled = true) {
        const colors = {
            common: '#212121',     // Dark Black
            uncommon: '#212121',   // Dark Black
            rare: '#212121',       // Dark Black
            epic: '#212121',       // Dark Black
            legendary: '#212121',  // Dark Black
            mythic: '#212121'      // Dark Black
        };

        const triangleCount = {
            common: 1,
            uncommon: 1,
            rare: 1,
            epic: 2,
            legendary: 3,
            mythic: 5
        };

        const color = colors[rarity] || colors.common;
        const triangles = triangleCount[rarity] || 1;

        super(color, Stinger.getRadiusByRarity(rarity), 'triangle', isPlayerControlled, null, rarity);

        this.shape = 'triangle';
        this.triangles = [];
        this.triangleCount = triangles;
        this.rotationSpeed = 0.05;
        this.currentRotation = 0;

        this.initializeTriangles();
    }

    static getRadiusByRarity(rarity) {
        const rarityRadii = {
            common: 5,
            uncommon: 6,
            rare: 7,
            epic: 8,
            legendary: 10,
            mythic: 12
        };
        return rarityRadii[rarity] || 5;
    }

    initializeTriangles() {
        // Each triangle is slightly different
        const rarityVariations = {
            common: [{ size: 1, offset: 0, rotationOffset: 0 }],
            uncommon: [{ size: 1, offset: 0, rotationOffset: 0 }],
            rare: [{ size: 1, offset: 0, rotationOffset: 0 }],
            epic: [
                { size: 0.8, offset: -10, rotationOffset: 0 },
                { size: 1.2, offset: 10, rotationOffset: Math.PI }
            ],
            legendary: [
                { size: 0.7, offset: -15, rotationOffset: 0 },
                { size: 1, offset: 0, rotationOffset: Math.PI / 2 },
                { size: 1.3, offset: 15, rotationOffset: Math.PI }
            ],
            mythic: [
                { size: 0.6, offset: -20, rotationOffset: 0 },
                { size: 0.8, offset: -10, rotationOffset: Math.PI / 4 },
                { size: 1, offset: 0, rotationOffset: Math.PI / 2 },
                { size: 1.2, offset: 10, rotationOffset: 3 * Math.PI / 4 },
                { size: 1.4, offset: 20, rotationOffset: Math.PI }
            ]
        };

        this.triangles = rarityVariations[this.rarity].map(triangleData => ({
            size: triangleData.size,
            offset: triangleData.offset,
            rotationOffset: triangleData.rotationOffset
        }));
    }

    draw(ctx, cameraX = 0, cameraY = 0) {
        if (this.isRecharging) return;

        ctx.save();
        ctx.translate(this.x - cameraX, this.y - cameraY);
        ctx.rotate(this.currentRotation);

        this.triangles.forEach(triangle => {
            ctx.save();
            ctx.rotate(triangle.rotationOffset);
            ctx.translate(triangle.offset, 0);

            ctx.beginPath();
            const size = this.radius * triangle.size;
            ctx.moveTo(0, -size);
            ctx.lineTo(-size, size);
            ctx.lineTo(size, size);
            ctx.closePath();

            ctx.fillStyle = this.color;
            ctx.fill();

            // Subtle highlight
            ctx.strokeStyle = 'rgba(255,255,255,0.2)';
            ctx.lineWidth = 1;
            ctx.stroke();

            ctx.restore();
        });

        ctx.restore();
    }

    update(state, playerX, playerY) {
        super.update(state, playerX, playerY);
        this.currentRotation += this.rotationSpeed;
    }

    triggerSpecialAbility(playerX, playerY) {
        const damageMultiplier = {
            'common': 1.5,
            'uncommon': 2,
            'rare': 2.5,
            'epic': 3,
            'legendary': 3.5,
            'mythic': 4
        };

        return {
            type: 'pierce',
            damage: this.damage * damageMultiplier[this.rarity],
            penetrationCount: this.triangleCount,
            x: playerX,
            y: playerY
        };
    }

    onAddToPlayer(player) {
        // Increase player's piercing damage
        player.pierceDamage += this.triangleCount * 2;
    }

    onRemoveFromPlayer(player) {
        // Remove the piercing damage bonus
        player.pierceDamage -= this.triangleCount * 2;
    }
}