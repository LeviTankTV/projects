class SPetal {
    constructor(ownerId, radius, speed, angleOffset, rarity, name) {
        this.ownerId = ownerId; // Reference to the player who owns this petal
        this.radius = radius; // Distance from the player
        this.speed = speed; // Speed of rotation
        this.angle = angleOffset; // Initial angle offset for the petal
        this.rarity = rarity;
        this.name = name;

        // Initialize petal stats based on the petalConfigMap
        const petalStats = petalConfigMap[name]?.[rarity];
        if (petalStats) {
            this.health = petalStats.health;
            this.damage = petalStats.damage;
            this.rechargeTime = petalStats.rechargeTime;
            this.damageCooldown = petalStats.damageCooldown;
            this.lastDamageTime = 0; // Track the last time the petal took damage
            this.isOnCooldown = false; // Track if the petal is on cooldown
            this.isAlive = true;
        } else {
            console.error(`No configuration found for petal: ${name} of rarity: ${rarity}`);
            this.health = 0;
            this.damage = 0;
            this.rechargeTime = 0;
            this.damageCooldown = 0;
            this.isAlive = false;
        }

        this.x = undefined;
        this.y = undefined;
    }

    canTakeDamage() {
        return this.isAlive && !this.isOnCooldown; // Petal can take damage if alive and not on cooldown
    }

    takeDamage(amount) {
        if (this.canTakeDamage()) {
            this.health -= amount;
            this.lastDamageTime = Date.now(); // Update last damage time
            if (this.health <= 0) {
                this.health = 0; // Ensure health doesn't go below 0
                this.startRecharge();
            }
        }
    }

    startRecharge() {
        this.isAlive = false; // Set alive status to false
        this.isOnCooldown = true; // Set cooldown flag
        setTimeout(() => {
            this.health = this.getMaxHealth(); // Restore health after recharge
            this.isAlive = true; // Set alive status back to true
            this.isOnCooldown = false; // Reset cooldown flag
        }, this.rechargeTime);
    }


    getMaxHealth() {
        // Return maximum health based on petal stats
        const petalStats = petalConfigMap[this.name]?.[this.rarity];
        return petalStats ? petalStats.health : 0;
    }
}

module.exports = SPetal;

const petalConfigMap = {
    "Arc": {
        "common": { health: 10, damage: 5000, rechargeTime: 1000, damageCooldown: 1000 },
        "unusual": { health: 15, damage: 7, rechargeTime: 1800, damageCooldown: 10 },
        "rare": { health: 20, damage: 10, rechargeTime: 1600, damageCooldown: 10 },
        "epic": { health: 25, damage: 15, rechargeTime: 1400, damageCooldown: 10 },
        "legendary": { health: 30, damage: 20, rechargeTime: 1200, damageCooldown: 10 },
        "mythic": { health: 35, damage: 25, rechargeTime: 1000, damageCooldown: 10 },
        "ultra": { health: 40, damage: 30, rechargeTime: 800, damageCooldown: 10 },
        "Topi": { health: 5000, damage: 50000, rechargeTime: 600, damageCooldown: 10 },
    },
    // Add more petal types as needed...
};