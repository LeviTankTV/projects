const mobConfig = {
    Arcane: {
        rarities: {
            common: {
                petalDrop: 1,
                dropChance: 0.1, // 10% chance to drop common petal
            },
            unusual: {
                petalDrop: 1,
                dropChance: 0.3, // 30% chance to drop common petal
                unusualDropChance: 0.1 // 10% chance to drop unusual petal
            },
            rare: {
                petalDrop: 1,
                dropChance: 0.7, // 70% chance to drop common petal
                unusualDropChance: 0.2, // 20% chance to drop unusual petal
                rareDropChance: 0.1 // 10% chance to drop rare petal
            },
            epic: {
                petalDrop: 1,
                dropChance: 0.4, // 40% chance to drop common petal
                unusualDropChance: 0.3, // 30% chance to drop unusual petal
                rareDropChance: 0.25, // 25% chance to drop rare petal
                epicDropChance: 0.05 // 5% chance to drop epic petal
            },
            legendary: {
                petalDrop: 1,
                dropChance: 0.95, // 95% chance to drop rare petal
                epicDropChance: 0.04, // 4% chance to drop epic petal
                legendaryDropChance: 0.01 // 1% chance to drop legendary petal
            },
            mythic: {
                petalDrop: 1,
                dropChance: 0.9, // 90% chance to drop epic petal
                legendaryDropChance: 0.1, // 10% chance to drop legendary petal
                mythicDropChance: 0.001 // 0.1% chance to drop mythic petal
            },
            ultra: {
                petalDrop: 1,
                dropChance: 0.9899, // 98.99% chance to drop legendary petal
                mythicDropChance: 0.01, // 1% chance to drop mythic petal
                ultraDropChance: 0.0001 // 0.01% chance to drop ultra petal
            },
            Topi: {
                petalDrop: 1,
                dropChance: 0.795, // 79.5% chance to drop legendary petal
                mythicDropChance: 0.2, // 20% chance to drop mythic petal
                ultraDropChance: 0.005 // 0.5% chance to drop ultra petal
            },
        },

    },
    Tyrant: {
    rarities: {
        common: {
            petalDrop: 1,
                dropChance: 0.1, // 10% chance to drop common petal
        },
        unusual: {
            petalDrop: 1,
                dropChance: 0.3, // 30% chance to drop common petal
                unusualDropChance: 0.1 // 10% chance to drop unusual petal
        },
        rare: {
            petalDrop: 1,
                dropChance: 0.7, // 70% chance to drop common petal
                unusualDropChance: 0.2, // 20% chance to drop unusual petal
                rareDropChance: 0.1 // 10% chance to drop rare petal
        },
        epic: {
            petalDrop: 1,
                dropChance: 0.4, // 40% chance to drop common petal
                unusualDropChance: 0.3, // 30% chance to drop unusual petal
                rareDropChance: 0.25, // 25% chance to drop rare petal
                epicDropChance: 0.05 // 5% chance to drop epic petal
        },
        legendary: {
            petalDrop: 1,
                dropChance: 0.95, // 95% chance to drop rare petal
                epicDropChance: 0.04, // 4% chance to drop epic petal
                legendaryDropChance: 0.01 // 1% chance to drop legendary petal
        },
        mythic: {
            petalDrop: 1,
                dropChance: 0.9, // 90% chance to drop epic petal
                legendaryDropChance: 0.1, // 10% chance to drop legendary petal
                mythicDropChance: 0.001 // 0.1% chance to drop mythic petal
        },
        ultra: {
            petalDrop: 1,
                dropChance: 0.9899, // 98.99% chance to drop legendary petal
                mythicDropChance: 0.01, // 1% chance to drop mythic petal
                ultraDropChance: 0.0001 // 0.01% chance to drop ultra petal
        },
        Topi: {
            petalDrop: 1,
                dropChance: 0.795, // 79.5% chance to drop legendary petal
                mythicDropChance: 0.2, // 20% chance to drop mythic petal
                ultraDropChance: 0.005 // 0.5% chance to drop ultra petal
        },
    },
},
    // TODO: Add more mob configurations in future
};

class Mob {
    constructor(zoneId, name, rarity, x, y, team = 'enemy', type = null) {
        this.zoneId = zoneId;
        this.name = name;
        this.rarity = rarity;
        this.x = x;
        this.y = y;
        this.speed = 25;
        this.id = this.generateID();
        this.health = this.calculateHealth(rarity);
        this.experienceDrop = this.calculateExperienceDrop(rarity);
        this.type = type;
        this.isAggroed = false;
        this.aggroRadius = this.calculateAggroRadius(rarity);
        this.targetPlayer = null;
        this.isDead = false;

        // New properties for tracking aggro state
        this.followTimer = 0; // Timer to track how long to follow the player
        this.followDuration = 10; // Duration to follow after leaving aggro radius (in seconds)

        // New properties for wandering
        this.wanderDirection = this.getRandomDirection();
        this.wanderDistance = this.getRandomDistance();
        this.wanderPauseDuration = this.getRandomPauseDuration();
        this.wanderTimer = 0;
        this.size = this.calculateRaritySize(rarity);
        this.petalDrop = this.calculatePetalDrop(rarity, name);
        this.damageTaken = {};

        this.zones = [
            { id: 'Common', color: 'darkgreen', bounds: { x: [0, 2125], y: [0, 2000] } },
            { id: 'Unusual', color: 'orange', bounds: { x: [2500, 4625], y: [0, 2000] } },
            { id: 'Rare', color: 'aqua', bounds: { x: [5000, 7125], y: [0, 2000] } },
            { id: 'Epic', color: 'purple', bounds: { x: [7500, 9625], y: [0, 2000] } },
            { id: 'Legendary', color: 'red', bounds: { x: [10000, 12125], y: [0, 2000] } },
            { id: 'Mythic', color: 'navy', bounds: { x: [12500, 14625], y: [0, 2000] } },
            { id: 'Ultra', color: 'deeppink', bounds: { x: [15000, 17125], y: [0, 2000] } },
            { id: 'Topi', color: 'black', bounds: { x: [17500, 20000], y: [0, 2000] } }
        ];

        this.bodyDamage = this.calculateDamage(rarity, name);
    }



    generateID() {
        return 'mob_' + Math.random().toString(36).substr(2, 9); // Simple unique ID generation
    }

    calculateDamage(rarity, name) {
        const damageMap = {
            Tyrant: {
                common: 10,
                unusual: 30,
                rare: 90,
                epic: 270,
                legendary: 1000,
                mythic: 4000,
                ultra: 40000,
                Topi: 150000
            },
            Arcane: {
                common: 15,
                unusual: 45,
                rare: 135,
                epic: 405,
                legendary: 1500,
                mythic: 6000,
                ultra: 60000,
                Topi: 200000
            }
        };

        // Check if the mob name exists in the damage map
        if (damageMap[name]) {
            // Return the damage value based on the rarity
            return damageMap[name][rarity] || 0; // Default to 0 if rarity is unknown
        }
        return 0; // Default to 0 if mob name is unknown
    }
    calculateHealth(rarity) {
        const healthMap = {
            common: 100,
            unusual: 300,
            rare: 900,
            epic: 2700,
            legendary: 10000,
            mythic: 40000,
            ultra: 400000,
            Topi: 15000000
        };
        return healthMap[rarity] || 100; // Default to 100 if rarity is unknown
    }

    calculatePetalDrop(rarity, name) {
        const dropConfig = mobConfig[name].rarities[rarity];
        const randomValue = Math.random();

        // Check the drop chances based on the rarity
        if (randomValue < dropConfig.dropChance) {
            return { petalType: 'common', rarity: 'common', quantity: dropConfig.petalDrop };
        } else if (rarity === 'unusual' && randomValue < dropConfig.dropChance + dropConfig.unusualDropChance) {
            return { petalType: 'unusual', rarity: 'unusual', quantity: dropConfig.petalDrop };
        } else if (rarity === 'rare' && randomValue < dropConfig.dropChance + dropConfig.unusualDropChance + dropConfig.rareDropChance) {
            return { petalType: 'rare', rarity: 'rare', quantity: dropConfig.petalDrop };
        } else if (rarity === 'epic' && randomValue < dropConfig.dropChance + dropConfig.unusualDropChance + dropConfig.rareDropChance + dropConfig.epicDropChance) {
            return { petalType: 'epic', rarity: 'epic', quantity: dropConfig.petalDrop };
        } else if (rarity === 'legendary' && randomValue < dropConfig.dropChance + dropConfig.unusualDropChance + dropConfig.rareDropChance + dropConfig.epicDropChance + dropConfig.legendaryDropChance) {
            return { petalType: 'legendary', rarity: 'legendary', quantity: dropConfig.petalDrop };
        } else if (rarity === 'mythic' && randomValue < dropConfig.dropChance + dropConfig.unusualDropChance + dropConfig.rareDropChance + dropConfig.epicDropChance + dropConfig.legendaryDropChance + dropConfig.mythicDropChance) {
            return { petalType: 'mythic', rarity: 'mythic', quantity: dropConfig.petalDrop };
        } else if (rarity === 'ultra' && randomValue < dropConfig.dropChance + dropConfig.unusualDropChance + dropConfig.rareDropChance + dropConfig.epicDropChance + dropConfig.legendaryDropChance + dropConfig.mythicDropChance + dropConfig.ultraDropChance) {
            return { petalType: 'ultra', rarity: 'ultra', quantity: dropConfig.petalDrop };
        } else if (rarity === 'Topi' && randomValue < dropConfig.dropChance + dropConfig.mythicDropChance + dropConfig.ultraDropChance) {
            return { petalType: 'ultra', rarity: 'ultra', quantity: dropConfig.petalDrop };
        }

        return null; // No drop
    }

    calculateAggroRadius(rarity) {
        const radiusMap = {
            common: 200,
            unusual: 300,
            rare: 400,
            epic: 500,
            legendary: 500,
            mythic: 700,
            ultra: 1000,
            Topi: 1500
        }
        return radiusMap[rarity] || 50
    }

    calculateRaritySize(rarity) {
        const sizeMap = {
            common: 15,
            unusual: 30,
            rare: 40,
            epic: 50,
            legendary: 75,
            mythic: 100,
            ultra: 250,
            Topi: 650
        }
        return sizeMap[rarity] || 10;
    }

    calculateExperienceDrop(rarity) {
        const rewardMap = {
            common: 20,
            unusual: 60,
            rare: 180,
            epic: 540,
            legendary: 1620,
            mythic: 4860,
            ultra: 14580,
            Topi: 100000
        };
        return rewardMap[rarity] || 10; // Default to 10 if rarity is unknown
    }

    checkCollision(otherMob) {
        const dx = this.x - otherMob.x;
        const dy = this.y - otherMob.y;
        const distance = Math.sqrt(dx * dx + dy * dy);
        const combinedSize = (this.size + otherMob.size) / 2; // Average size for collision detection

        return distance < combinedSize; // Collision detected
    }

    resolveCollision(otherMob) {
        const dx = this.x - otherMob.x;
        const dy = this.y - otherMob.y;
        const distance = Math.sqrt(dx * dx + dy * dy);
        const combinedSize = (this.size + otherMob.size) / 2;

        // Calculate overlap
        const overlap = combinedSize - distance;

        // Normalize the direction vector
        const directionX = dx / distance;
        const directionY = dy / distance;

        // Push the mobs apart based on the overlap
        this.x += directionX * overlap / 2; // Move this mob
        otherMob.x -= directionX * overlap / 2; // Move other mob

        this.y += directionY * overlap / 2; // Move this mob
        otherMob.y -= directionY * overlap / 2; // Move other mob
    }

    update(players, deltaTime, allMobs) {
        if (this.isDead) return;

        // Existing behavior (aggressive/passive)
        if (this.type === 'aggressive') {
            this.handleAggressiveBehavior(players, deltaTime)
        } else if (this.type === 'passive') {
            this.wander(deltaTime);
        }

        // Ensure the mob stays within its zone
        this.checkBounds();

        // Handle collisions with other mobs in the same zone
        this.handleMobCollisions(allMobs);

    }

    handleMobCollisions(allMobs) {
        allMobs.forEach(otherMob => {
            if (otherMob !== this && this.checkCollision(otherMob)) {
                this.resolveCollision(otherMob);
            }
        });
    }

    checkBounds() {
        const zone = this.zones.find(z => z.id === this.zoneId); // Get the zone based on zoneId
        if (zone) {
            const { bounds } = zone;

            // Restrict the mob's position within the defined bounds
            if (this.x < bounds.x[0]) {
                this.x = bounds.x[0];
            } else if (this.x > bounds.x[1]) {
                this.x = bounds.x[1];
            }

            if (this.y < bounds.y[0]) {
                this.y = bounds.y[0];
            } else if (this.y > bounds.y[1]) {
                this.y = bounds.y[1];
            }
        }
    }

    handleAggressiveBehavior(players, deltaTime) {
        if (!players || players.length === 0) {
            this.isAggroed = false; // No players, so not aggroed
            this.wander(deltaTime); // Wander around if not aggroed
            return;
        }

        // Find the nearest player
        let nearestPlayer = null;
        let nearestDistance = Infinity;

        players.forEach(player => {
            const distance = Math.sqrt(Math.pow(player.x - this.x, 2) + Math.pow(player.y - this.y, 2));
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestPlayer = player;
            }
        });

        // Aggro if within a certain distance (e.g., 500 pixels)
        if (nearestDistance < this.aggroRadius) {
            this.isAggroed = true;
            this.targetPlayer = nearestPlayer;
            this.followTimer = this.followDuration; // Reset the follow timer
            this.moveTowardsPlayer(nearestPlayer);
        } else {
            // If the player is out of aggro radius
            if (this.isAggroed) {
                this.followTimer -= deltaTime; // Decrease follow timer
                if (this.followTimer > 0) {
                    // Still follow the player for the remaining time
                    this.moveTowardsPlayer(this.targetPlayer);
                } else {
                    // Time has expired, stop following
                    this.isAggroed = false;
                    this.targetPlayer = null;
                }
            } else {
                this.wander(deltaTime); // Wander around if not aggroed
            }
        }
    }

    handlePassiveBehavior() {
        this.wander(); // Passive mobs wander around
    }

    moveTowardsPlayer(player) {
        // Move towards the player
        const directionX = player.x - this.x;
        const directionY = player.y - this.y;
        const distance = Math.sqrt(directionX ** 2 + directionY ** 2);
        if (distance > 0) {
            this.x += (directionX / distance) * this.speed / 10; // Move in the direction of the player
            this.y += (directionY / distance) * this.speed / 10;
        }
    }

    wander(deltaTime) {
        if (this.wanderTimer > 0) {
            // If the timer is still counting down, just decrease it
            this.wanderTimer -= deltaTime;
            return; // Don't move yet
        }

        // Move in the current direction
        const directionX = Math.cos(this.wanderDirection);
        const directionY = Math.sin(this.wanderDirection);

        // Move towards the direction based on speed and deltaTime
        this.x += directionX * this.speed * deltaTime;
        this.y += directionY * this.speed * deltaTime;

        // Decrease the distance left to move
        this.wanderDistance -= this.speed * deltaTime;

        // Check if we've moved the full distance
        if (this.wanderDistance <= 0) {
            // Reset the wander parameters
            this.wanderDirection = this.getRandomDirection();
            this.wanderDistance = this.getRandomDistance();
            this.wanderPauseDuration = this.getRandomPauseDuration();
            this.wanderTimer = this.wanderPauseDuration / 1000; // Convert milliseconds to seconds
        }
    }
    getRandomDirection() {
        return Math.random() * 2 * Math.PI; // Random angle in radians
    }

    getRandomDistance() {
        return Math.floor(Math.random() * 51) + 50; // Random distance between 50 and 100 pixels
    }

    getRandomPauseDuration() {
        return Math.floor(Math.random() * 2000) + 1000; // Random pause between 1000 ms (1 second) and 3000 ms (3 seconds)
    }

    takeDamage(amount) {
        this.health -= amount;
        // Handle mob death logic if needed
        if (this.health <= 0) {
            this.isDead = true;
        }
    }

    checkHealth() {
        if (this.health <= 0) {
            this.isDead = true;
        }
    }
}

module.exports = Mob;