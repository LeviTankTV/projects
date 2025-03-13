export class CanvasUtils {
    /**
     * Draws a rounded rectangle on canvas
     * @param {CanvasRenderingContext2D} ctx - Canvas rendering context
     * @param {number} x - X coordinate of top-left corner
     * @param {number} y - Y coordinate of top-left corner
     * @param {number} width - Width of rectangle
     * @param {number} height - Height of rectangle
     * @param {number} radius - Corner radius
     */
    static roundedRect(ctx, x, y, width, height, radius) {
        ctx.beginPath();
        ctx.moveTo(x + radius, y);
        ctx.arcTo(x + width, y, x + width, y + height, radius);
        ctx.arcTo(x + width, y + height, x, y + height, radius);
        ctx.arcTo(x, y + height, x, y, radius);
        ctx.arcTo(x, y, x + width, y, radius);
        ctx.closePath();
    }

    /**
     * Creates a linear gradient with predefined color stops
     * @param {CanvasRenderingContext2D} ctx - Canvas rendering context
     * @param {number} x - Start X coordinate
     * @param {number} y - Start Y coordinate
     * @param {number} width - Gradient width
     * @returns {CanvasGradient}
     */
    static createProgressBarGradient(ctx, x, y, width) {
        const gradient = ctx.createLinearGradient(x, y, x + width, y);
        gradient.addColorStop(0, 'rgba(46, 204, 113, 0.7)');   // Green
        gradient.addColorStop(0.5, 'rgba(241, 196, 15, 0.7)'); // Yellow
        gradient.addColorStop(1, 'rgba(231, 76, 60, 0.7)');    // Red
        return gradient;
    }

    /**
     * Creates a text gradient
     * @param {CanvasRenderingContext2D} ctx - Canvas rendering context
     * @param {number} x - Start X coordinate
     * @param {number} y - Start Y coordinate
     * @param {number} width - Gradient width
     * @returns {CanvasGradient}
     */
    static createTextGradient(ctx, x, y, width) {
        const textGradient = ctx.createLinearGradient(x, y, x + width, y);
        textGradient.addColorStop(0, 'rgba(46, 204, 113, 1)');   // Green
        textGradient.addColorStop(0.5, 'rgba(241, 196, 15, 1)'); // Yellow
        textGradient.addColorStop(1, 'rgba(231, 76, 60, 1)');    // Red
        return textGradient;
    }

    static drawNameWithBackground(ctx, {
        name,
        x,
        y,
        width = 120,
        height = 20,
        cornerRadius = 10,
        font = 'bold 14px "Montserrat", Arial, sans-serif',
        textColor = '#FFFFFF',
        backgroundColors = {
            start: 'rgba(255, 255, 255, 0.2)',
            middle: 'rgba(255, 255, 255, 0.4)',
            end: 'rgba(255, 255, 255, 0.2)'
        }
    } = {}) {
        ctx.save();

        // Gradient background for name
        const gradient = ctx.createLinearGradient(
            x - width/2,
            y - height,
            x + width/2,
            y
        );
        gradient.addColorStop(0, backgroundColors.start);
        gradient.addColorStop(0.5, backgroundColors.middle);
        gradient.addColorStop(1, backgroundColors.end);

        // Shadow and glow
        ctx.shadowColor = 'rgba(0, 0, 0, 0.6)';
        ctx.shadowBlur = 10;
        ctx.shadowOffsetX = 2;
        ctx.shadowOffsetY = 2;

        // Rounded rectangle background
        ctx.beginPath();
        this.roundedRect(
            ctx,
            x - width/2,
            y - height,
            width,
            height,
            cornerRadius
        );
        ctx.fillStyle = gradient;
        ctx.fill();

        // Text styling
        ctx.font = font;
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';

        // Outline effect
        ctx.lineWidth = 4;
        ctx.strokeStyle = 'rgba(0, 0, 0, 0.4)';
        ctx.strokeText(
            name,
            x,
            y - height/2
        );

        // Elegant text colors
        const textGradient = ctx.createLinearGradient(
            x - width/2,
            y - height,
            x + width/2,
            y
        );
        textGradient.addColorStop(0, textColor);
        textGradient.addColorStop(0.5, '#F0F0F0');
        textGradient.addColorStop(1, textColor);

        ctx.fillStyle = textGradient;
        ctx.fillText(
            name,
            x,
            y - height/2
        );

        // Subtle shine effect
        ctx.globalAlpha = 0.3;
        ctx.fillStyle = 'rgba(255, 255, 255, 0.5)';
        ctx.fillText(
            name,
            x,
            y - height/2 - 1
        );

        ctx.restore();
    }
}