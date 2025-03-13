export class Camera {
    constructor(worldWidth, worldHeight, canvasWidth, canvasHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.x = 0;
        this.y = 0;
    }

    follow(player) {
        this.x = Math.max(0, Math.min(this.worldWidth - this.canvasWidth, player.x - this.canvasWidth / 2));
        this.y = Math.max(0, Math.min(this.worldHeight - this.canvasHeight, player.y - this.canvasHeight / 2));
    }

    resize(worldWidth, worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }
}