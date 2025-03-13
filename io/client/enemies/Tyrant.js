class Tyrant {
    constructor(id, x, y, rarity) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.rarity = rarity;
        this.size = this.getSizeByRarity(rarity);
        this.borderColor = this.getBorderColorByRarity(rarity);
        this.name = 'Tyrant';
    }

    getSizeByRarity(rarity) {
        const sizeMap = {
            common: 15,
            unusual: 30,
            rare: 40,
            epic: 50,
            legendary: 75,
            mythic: 100,
            ultra: 250,
            Topi: 250
        }
        return sizeMap[rarity] || 10;
    }

    getFontSizeByRarity(rarity) {
        const fontSizeMap = {
            common: 12,
            unusual: 12,
            rare: 14,
            epic: 16,
            legendary: 18,
            mythic: 20,
            ultra: 22,
            Topi: 22
        };
        return fontSizeMap[rarity] || 8; // Default to 8 if rarity not found
    }

    getBorderColorByRarity(rarity) {
        const colorMap = {
            common: 'SpringGreen',
            unusual: 'blue',
            rare: 'green',
            epic: 'purple',
            legendary: 'red',
            mythic: 'cyan',
            ultra: 'pink',
            Topi: 'Ivory'
        };
        return colorMap[rarity] || 'black'; // Default to black if rarity not found
    }

    drawHeart(ctx, x, y, size) {
        ctx.beginPath();
        ctx.moveTo(x, y);
        ctx.bezierCurveTo(x, y - size / 2, x - size / 2, y - size / 2, x - size / 2, y);
        ctx.bezierCurveTo(x - size / 2, y + size, x, y + size, x, y + size / 2);
        ctx.bezierCurveTo(x, y + size, x + size / 2, y + size, x + size / 2, y);
        ctx.bezierCurveTo(x + size / 2, y - size / 2, x, y - size / 2, x, y);
        ctx.closePath();
        ctx.fillStyle = 'red'; // Heart color
        ctx.fill(); // Fill the heart
        ctx.strokeStyle = 'black'; // Heart border color
        ctx.stroke(); // Stroke the heart
    }

    draw(ctx, cameraOffsetX, cameraOffsetY) {
        // Calculate the adjusted position for rendering
        const adjustedX = this.x - cameraOffsetX;
        const adjustedY = this.y - cameraOffsetY;

        // Size adjustments based on rarity
        const triangleHeight = this.size ; // Height of the triangle based on the size
        const triangleBase = this.size * 1.2; // Base of the triangle slightly larger than height

        // Draw the triangle
        const triangleX1 = adjustedX; // Top vertex (pointing up)
        const triangleY1 = adjustedY; // Top vertex Y position (set to adjustedY directly)
        const triangleX2 = adjustedX - triangleBase / 2; // Bottom left vertex
        const triangleY2 = adjustedY + triangleHeight; // Bottom left vertex Y position
        const triangleX3 = adjustedX + triangleBase / 2; // Bottom right vertex
        const triangleY3 = adjustedY + triangleHeight; // Bottom right vertex Y position

        // Draw the triangle
        ctx.fillStyle = this.getBorderColorByRarity(this.rarity); // Triangle color
        ctx.beginPath();
        ctx.moveTo(triangleX1, triangleY1);
        ctx.lineTo(triangleX2, triangleY2);
        ctx.lineTo(triangleX3, triangleY3);
        ctx.closePath(); // Close the path to form the triangle
        ctx.fill(); // Fill the triangle

        // Draw triangle border
        ctx.strokeStyle = 'black'; // Border color
        ctx.lineWidth = this.size * 0.1; // Border width based on size
        ctx.stroke(); // Stroke the triangle border

        // Set pentagonCenterY based on rarity
        let pentagonYOffset;
        switch (this.rarity) {
            case 'common':
                pentagonYOffset = 1.3;
                break;
            case 'unusual':
            case 'rare':
                pentagonYOffset = 4;
                break;
            case 'epic':
                pentagonYOffset = 5;
                break;
            case 'legendary':
                pentagonYOffset = 5.5;
                break;
            case 'mythic':
                pentagonYOffset = 9;
                break;
            case 'ultra':
            case 'Topi':
                pentagonYOffset = 25;
                break;
            default:
                pentagonYOffset = 0; // Default offset if rarity is not recognized
        }

        // Draw the pentagon in the center of the triangle
        ctx.fillStyle = 'Indigo'; // Set pentagon color
        ctx.beginPath();

        const pentagonSize = this.size * 0.2; // Size of the pentagon based on the rarity size
        const pentagonCenterX = adjustedX; // Center X of the pentagon
        const pentagonCenterY = adjustedY + triangleHeight / 2 + pentagonYOffset; // Center Y of the pentagon

        for (let i = 0; i < 5; i++) {
            const angle = (i * 2 * Math.PI) / 5 - Math.PI / 2; // Rotate pentagon to make the base parallel
            const x = pentagonCenterX + pentagonSize * Math.cos(angle); // X coordinate
            const y = pentagonCenterY + pentagonSize * Math.sin(angle); // Y coordinate
            ctx.lineTo(x, y); // Draw line to the vertex
        }

        ctx.closePath(); // Close the pentagon path
        ctx.fill(); // Fill the pentagon

        // Draw pentagon border
        ctx.strokeStyle = 'OrangeRed'; // Border color
        ctx.lineWidth = this.size * 0.05; // Border width based on size
        ctx.stroke(); // Stroke the pentagon border

        const showMobRarities = localStorage.getItem('ShowMobRarities') === 'true';

        // Draw the rarity name below the circle if enabled
        if (showMobRarities) {
            ctx.fillStyle = this.borderColor; // Use the border color for rarity
            const rarityFontSize = this.getFontSizeByRarity(this.rarity); // Slightly smaller than the name
            ctx.font = `${rarityFontSize}px Comic Sans MS, sans-serif`; // Font style for the rarity
            ctx.fillText(this.rarity, adjustedX, adjustedY + this.size + 10); // Position below the triangle
        }

        // Draw heart inside the pentagon
        this.drawHeart(ctx, pentagonCenterX, pentagonCenterY, pentagonSize * 0.5); // Draw heart at the center of the pentagon, size based on pentagon size
    }
}