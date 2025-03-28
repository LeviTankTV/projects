export class World {
    constructor(width, height, numTiles = 6) {
        this.width = width;
        this.height = height;
        this.numTiles = numTiles;
        this.tiles = [];
        this.generateWorld();
    }

    generateWorld() {
        this.tiles = []; // Clear existing tiles

        // Create a single biome tile that covers the entire world
        this.tiles.push({
            x: 0,
            y: 0,
            width: this.width,
            height: this.height,
            biome: 'common' // You can change this to any specific biome you want
        });
    }

    // Call this method when the world size changes
    resize(width, height) {
        this.width = width;
        this.height = height;
        this.tileSize = width / this.numTiles; // Update tile size
        this.generateWorld(); // Regenerate tiles
    }
}