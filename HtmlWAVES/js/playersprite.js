export class PlayerSprite {
    constructor(imagePath) {
        this.image = new Image();
        this.image.src = imagePath;
        this.isLoaded = false;
        this.loadError = false;

        // Handle image loading success
        this.image.onload = () => {
            this.isLoaded = true;
            console.log('Player sprite loaded successfully');
        };

        // Handle image loading error
        this.image.onerror = () => {
            this.loadError = true;
            console.error('Failed to load player sprite');
        };
    }

    draw(ctx, x, y, radius) {
        // If image is not loaded, draw a fallback circle
        if (!this.isLoaded || this.loadError) {
            ctx.beginPath();
            ctx.arc(x, y, radius, 0, Math.PI * 2);
            ctx.fillStyle = 'rgba(255, 0, 0, 0.5)'; // Red fallback color
            ctx.fill();
            return;
        }

        // Save the context state
        ctx.save();

        // Create a circular clipping path
        ctx.beginPath();
        ctx.arc(x, y, radius, 0, Math.PI * 2);
        ctx.clip();

        // Draw the image, scaled to fit the circular area
        const diameter = radius * 2;
        ctx.drawImage(
            this.image,
            x - radius,  // Centered X
            y - radius,  // Centered Y
            diameter,    // Width
            diameter     // Height
        );

        // Restore the context state
        ctx.restore();
    }

    // Optional: Method to check if sprite is ready
    isReady() {
        return this.isLoaded && !this.loadError;
    }
}
