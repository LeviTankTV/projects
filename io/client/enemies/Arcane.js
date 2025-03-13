class Arcane {
    constructor(id, x, y, rarity) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.rarity = rarity;
        this.size = this.getSizeByRarity(rarity);
        this.borderColor = this.getBorderColorByRarity(rarity);
        this.name = 'Arcane';
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

    draw(ctx, cameraOffsetX, cameraOffsetY) {
        // Calculate the adjusted position for rendering
        const adjustedX = this.x - cameraOffsetX;
        const adjustedY = this.y - cameraOffsetY;

        // Draw the outer circle
        ctx.fillStyle = this.borderColor; // Set border color
        ctx.beginPath();
        ctx.arc(adjustedX, adjustedY, this.size, 0, Math.PI * 2); // Outer circle
        ctx.fill(); // Fill the outer circle

        // Draw the inner black circle
        ctx.fillStyle = 'black'; // Inner circle color
        ctx.beginPath();
        ctx.arc(adjustedX, adjustedY, this.size * 0.8, 0, Math.PI * 2); // Inner circle (80% of outer circle)
        ctx.fill(); // Fill the inner circle

        // Draw the triangle inside the black circle
        ctx.fillStyle = this.borderColor; // Set the triangle color to borderColor
        ctx.beginPath();

        // Calculate the triangle vertices
        const triangleHeight = this.size * 0.5; // Height of the triangle
        const triangleBase = this.size * 0.6; // Base of the triangle
        const triangleX1 = adjustedX; // Top vertex (pointing up)
        const triangleY1 = adjustedY - triangleHeight / 2; // Top vertex Y position
        const triangleX2 = adjustedX - triangleBase / 2; // Bottom left vertex
        const triangleY2 = adjustedY + triangleHeight / 2; // Bottom left vertex Y position
        const triangleX3 = adjustedX + triangleBase / 2; // Bottom right vertex
        const triangleY3 = adjustedY + triangleHeight / 2; // Bottom right vertex Y position

        // Move to the first vertex and draw the triangle
        ctx.moveTo(triangleX1, triangleY1);
        ctx.lineTo(triangleX2, triangleY2);
        ctx.lineTo(triangleX3, triangleY3);
        ctx.closePath(); // Close the path to form the triangle
        ctx.fill(); // Fill the triangle

        // Check the "Show Mob Rarities" setting
        const showMobRarities = localStorage.getItem('ShowMobRarities') === 'true';

        // Draw the rarity name below the circle if enabled
        if (showMobRarities) {
            ctx.fillStyle = this.borderColor; // Use the border color for rarity
            const rarityFontSize = this.getFontSizeByRarity(this.rarity); // Slightly smaller than the name
            ctx.font = `${rarityFontSize}px Comic Sans MS, sans-serif`; // Font style for the rarity
            ctx.fillText(this.rarity, adjustedX, adjustedY + this.size + 10); // Position below the circle
        }
    }
}