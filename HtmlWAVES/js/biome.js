export class Biome {
    constructor(type, color) {
        this.type = type;
        this.color = color;
    }
}

export class WorldMap {
    constructor(width, height) {
        this.width = width;
        this.height = height;
        this.biomeMap = this.generateBiomes();
    }

    generateBiomes() {
        return [
            new Biome('forest', 'rgba(34, 139, 34, 0.7)'),     // Top-left
            new Biome('desert', 'rgba(238, 214, 175, 0.7)'),   // Top-right
            new Biome('mountain', 'rgba(139, 137, 137, 0.7)'), // Bottom-left
            new Biome('tundra', 'rgba(176, 224, 230, 0.7)')    // Bottom-right
        ];
    }

    renderWorld(ctx) {
        // Render full world with 4 biome quadrants
        ctx.fillStyle = this.biomeMap[0].color; // Forest
        ctx.fillRect(0, 0, this.width / 2, this.height / 2);

        ctx.fillStyle = this.biomeMap[1].color; // Desert
        ctx.fillRect(this.width / 2, 0, this.width / 2, this.height / 2);

        ctx.fillStyle = this.biomeMap[2].color; // Mountain
        ctx.fillRect(0, this.height / 2, this.width / 2, this.height / 2);

        ctx.fillStyle = this.biomeMap[3].color; // Tundra
        ctx.fillRect(this.width / 2, this.height / 2, this.width / 2, this.height / 2);
    }

    getBiomeAtPosition(x, y) {
        const halfWidth = this.width / 2;
        const halfHeight = this.height / 2;

        if (x < halfWidth && y < halfHeight) {
            return this.biomeMap[0]; // Forest
        } else if (x >= halfWidth && y < halfHeight) {
            return this.biomeMap[1]; // Desert
        } else if (x < halfWidth && y >= halfHeight) {
            return this.biomeMap[2]; // Mountain
        } else {
            return this.biomeMap[3]; // Tundra
        }
    }
}