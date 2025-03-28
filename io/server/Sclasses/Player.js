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
const SPetal = require("./SPetal");
class Player {
    constructor(id, username, x, y, radius, level, experience, health) {
        this.id = id;
        this.username = username;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.level = level;
        this.experience = experience;
        this.health = health;
        this.lastTeleportTime = 0; // Track the last teleport time
        this.teleportCooldown = 3000; // 10 seconds cooldown
        this.bodyDamage = 20; // Player's body damage
        this.lastDamageTime = 0; // Track the last time the player took damage
        this.damageCooldown = 400; // 400 ms cooldownt
        this.isDead = false;
        this.petals = [];
    }

    addPetal(rarity, name, ownerId) {
        const petal = new SPetal(ownerId, this.radius + 10, 0.05, Math.random() * Math.PI * 2, rarity, name);
        this.petals.push(petal); // Add the new petal to the player's petals
    }

    updatePosition(x, y) {
        // Restrict the player's position within the defined bounds
        if (x < 0) {
            this.x = 0;
        } else if (x > 20000) {
            this.x = 20000;
        } else {
            this.x = x;
        }

        if (y < 0) {
            this.y = 0;
        } else if (y > 2000) {
            this.y = 2000;
        } else {
            this.y = y;
        }
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
            }
        }
    }

    takeDamage(amount) {
        this.health -= amount;
        // Handle player death logic if needed
        if (this.health <= 0) {
            this.health = 0; // Ensure health doesn't go below zero
            this.isDead = true;
        }
    }

    canTakeDamage() {
        return Date.now() - this.lastDamageTime > this.damageCooldown;
    }

}

module.exports = Player;