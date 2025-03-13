class Petal {
    constructor(owner, radius, speed, angleOffset, rarity, name) {
        this.owner = owner; // Reference to the player who owns this petal
        this.radius = radius; // Distance from the player
        this.speed = speed; // Speed of rotation
        this.angle = angleOffset; // Initial angle offset for the petal
        this.rarity = rarity;
        this.name = name;
        this.borderColor = this.getColorByRarity(rarity); // Set the color based on rarity
        this.size = 10; // Size of the petal for drawing
        this.health = 1;
    }

    // Method to get the color based on rarity
    getColorByRarity(rarity) {
        const rarityColors = {
            common: 'SpringGreen',
            unusual: 'blue',
            rare: 'green',
            epic: 'purple',
            legendary: 'red',
            mythic: 'cyan',
            ultra: 'pink',
            Topi: 'Ivory'
        };

        return rarityColors[rarity] || 'white'; // Default to white if rarity is not found
    }

    update() {
        // Update the angle based on the speed
        this.angle += this.speed;
        // Calculate the petal's position based on the owner's position
        this.x = this.owner.x + Math.cos(this.angle) * this.radius;
        this.y = this.owner.y + Math.sin(this.angle) * this.radius;
    }

    draw(ctx, cameraOffsetX, cameraOffsetY) {
        // Calculate the adjusted position for rendering
        const adjustedX = this.x - cameraOffsetX;
        const adjustedY = this.y - cameraOffsetY;

        // Check if there's a rendering function for this petal's name
        if (petalRenderMap[this.name]) {
            petalRenderMap[this.name](this, ctx, adjustedX, adjustedY);
        } else {
            console.warn(`No render function defined for petal type: ${this.name}`);
            // Optionally, you can implement a default drawing behavior here
        }
    }
}

const petalRenderMap = {
    Arc: (petal, ctx, adjustedX, adjustedY) => {
        ctx.fillStyle = petal.borderColor; // Set border color based on rarity
        ctx.beginPath();
        ctx.arc(adjustedX, adjustedY, petal.size, 0, Math.PI * 2); // Outer circle
        ctx.fill(); // Fill the outer circle

        // Draw the inner black circle
        ctx.fillStyle = 'black'; // Inner circle color
        ctx.beginPath();
        ctx.arc(adjustedX, adjustedY, petal.size * 0.8, 0, Math.PI * 2); // Inner circle (80% of outer circle)
        ctx.fill(); // Fill the inner circle

        // Draw the triangle inside the black circle
        ctx.fillStyle = petal.borderColor; // Set the triangle color to borderColor
        ctx.beginPath();

        // Calculate the triangle vertices
        const triangleHeight = petal.size * 0.5; // Height of the triangle
        const triangleBase = petal.size * 0.6; // Base of the triangle
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
    },
    // Add more petal types here...
};
