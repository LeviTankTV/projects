export class Camera {
    constructor(canvas, worldWidth, worldHeight) {
        this.canvas = canvas;
        this.ctx = canvas.getContext('2d');
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        // Zoom properties
        this.zoom = 1.0;  // Default zoom level
        this.minZoom = 0.5;
        this.maxZoom = 2.0;
    }

    setZoom(zoomLevel) {
        // Validate and set zoom level
        this.zoom = Math.max(this.minZoom, Math.min(zoomLevel, this.maxZoom));
    }

    follow(player) {
        // Calculate the visible area size at current zoom level
        const visibleWidth = this.canvas.width / this.zoom;
        const visibleHeight = this.canvas.height / this.zoom;

        // Calculate camera position to center the player
        this.x = player.x + (player.width / 2) - (visibleWidth / 2);
        this.y = player.y + (player.height / 2) - (visibleHeight / 2);

        // Clamp camera to world boundaries
        this.x = Math.max(0, Math.min(this.x,
            this.worldWidth - visibleWidth
        ));
        this.y = Math.max(0, Math.min(this.y,
            this.worldHeight - visibleHeight
        ));
    }

    begin() {
        // Save canvas state
        this.ctx.save();

        // Apply zoom transformation
        this.ctx.scale(this.zoom, this.zoom);

        // Translate based on camera position
        this.ctx.translate(-this.x, -this.y);
    }

    end() {
        // Restore canvas state
        this.ctx.restore();
    }
}