const portals = [
    { id: '1', position: [2075, 1000], linkedPortal: '2' },
    { id: '2', position: [2550, 1000], linkedPortal: '1' },
    { id: '3', position: [4575, 1000], linkedPortal: '4' },
    { id: '4', position: [5050, 1000], linkedPortal: '3' },
    { id: '5', position: [7075, 1000], linkedPortal: '6' },
    { id: '6', position: [7550, 1000], linkedPortal: '5' },
    { id: '7', position: [9575, 1000], linkedPortal: '8' },
    { id: '8', position: [10050, 1000], linkedPortal: '7' },
    { id: '9', position: [12075, 1000], linkedPortal: '10' },
    { id: '10', position: [12550, 1000], linkedPortal: '9' },
    { id: '11', position: [14575, 1000], linkedPortal: '12' },
    { id: '12', position: [15050, 1000], linkedPortal: '11' },
    { id: '13', position: [17075, 1000], linkedPortal: '14' },
    { id: '14', position: [17550, 1000], linkedPortal: '13' },
];

class Flower {
    constructor(username, level, experience, x, y, radius, health, playerRenderer) {
        this.username = username;
        this.level = level;
        this.experience = experience;
        this.x = x;
        this.y = y;
        this.radius = radius; // Keep the base radius
        this.health = health;
        this.speed = 1; // Normal speed
        this.emotion = 'neutral';
        this.playerRenderer = playerRenderer; // Store the player renderer instance
        this.isDead = false;
        this.lastTeleportTime = 0; // Track the last teleport time
        this.teleportCooldown = 3000; // 3 seconds cooldown
        this.distance = 50;

        this.maxHealth = 100; // Example maximum health
        this.petals = []; // Array to hold active petals
        this.maxExperience = this.level * this.level * 100;
    }

    updateRadius(inputManager) {
        // Adjust the radius based on input
        if (inputManager.spacePressed) {
            this.distance = Math.min(this.distance + 1, 100); // Increase radius to a maximum of 100
        } else if (inputManager.rightMousePressed) {
            this.distance = Math.max(this.distance - 1, 30); // Decrease radius to a minimum of 30
        }
    }

    addPetal(rarity, name, owner) {
        const speed = 0.05; // Speed of rotation
        const angleOffset = Math.random() * Math.PI * 2; // Random initial angle
        const petal = new Petal(owner, this.distance, speed, angleOffset, rarity, name);
        this.petals.push(petal); // Add the new petal to the player's petals
    }

    updatePetals() {
        this.petals.forEach(petal => {
            // Update petal position based on some logic, e.g., rotating around the player
            petal.x = this.x + Math.cos(petal.angle) * this.distance; // Use the current radius
            petal.y = this.y + Math.sin(petal.angle) * this.distance; // Use the current radius

            // Optionally update the angle for rotation
            petal.angle += petal.speed; // Rotate the petal
        });
    }


    canTeleport() {
        return Date.now() - this.lastTeleportTime > this.teleportCooldown;
    }

    teleport(portal) {
        if (this.canTeleport()) {
            const linkedPortal = portals.find(p => p.id === portal.linkedPortal);
            if (linkedPortal) {
                this.x = linkedPortal.position[0];
                this.y = linkedPortal.position[1];
                this.lastTeleportTime = Date.now(); // Update the last teleport time
                return true; // Teleportation was successful
            }
        }
        return false; // Teleportation failed due to cooldown
    }
    setEmotion(emotion) {
        this.emotion = emotion;
    }

    updatePosition(deltaX, deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }

    die() {
        this.isDead = true;
    }

    draw(cameraOffsetX, cameraOffsetY, movement, emotion, radius, isDead) {
        // Create a gradient for the body
        const gradient = this.playerRenderer.createBodyGradient(this.playerRenderer.ctx, this.x - cameraOffsetX, this.y - cameraOffsetY, this.radius);

        // Draw the player's body
        this.playerRenderer.drawBody(this.x - cameraOffsetX, this.y - cameraOffsetY, radius, gradient, this.username);

        // Draw the player's eyes and mouth based on emotion
        this.playerRenderer.drawEyes(this.x - cameraOffsetX, this.y - cameraOffsetY, emotion, movement, isDead, radius);
        this.playerRenderer.drawMouth(this.x - cameraOffsetX, this.y - cameraOffsetY, emotion, movement, isDead, radius);
    }
}
