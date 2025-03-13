const SocketHandler = require("../networking");
const MobFactory = require('./MobFactory.js');

class GameServer {
    constructor(io) {
        this.io = io;
        this.connectedPlayers = {}; // Keep this initialized
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
        this.spawnCooldowns = {};
        this.mobs = {};
        this.zones.forEach(zone => {
            this.mobs[zone.id] = [];
        });
        this.socketHandler = new SocketHandler(io, this.connectedPlayers, this); // Pass the same connectedPlayers
        this.spawnInterval = 5000;
        this.lastUpdateTime = Date.now(); // Initialize last update time
        this.startGameLoop();
    }

    startGameLoop() {
        setInterval(() => {
            const currentTime = Date.now();
            const deltaTime = (currentTime - this.lastUpdateTime) / 1000; // Convert to seconds
            this.lastUpdateTime = currentTime; // Update last update time

            this.updateGame(deltaTime); // Pass deltaTime to updateGame]
            this.updateGameState();
            this.updatePlayerPosition(); // Update player positions
            this.updatePetalPositions();
            this.spawnMobs();
        }, 1000 / 30); // 30 FPS
    }

    checkCollision(entityA, entityB) {
        const dx = entityA.x - entityB.x;
        const dy = entityA.y - entityB.y;
        const distance = Math.sqrt(dx * dx + dy * dy);
        const combinedSize = (entityA.radius + entityB.size) / 2; // Average size for collision detection

        return distance < combinedSize; // Return true if they are colliding
    }

    updateGame(deltaTime) {
        Object.keys(this.mobs).forEach(zoneId => {
            const playersInZone = this.getPlayersInZone(zoneId);
            const mobsInZone = this.mobs[zoneId]; // Get all mobs in the current zone

            mobsInZone.forEach(mob => {
                mob.update(playersInZone, deltaTime, mobsInZone); // Pass the array of mobs in the same zone
                if (mob.isDead) {
                    this.handleMobDeath(mob, zoneId);
                }

                // Check collision with players
                playersInZone.forEach(player => {
                    if (this.checkCollision(player, mob)) {
                        // Player deals damage to mob
                        if (!mob.isDead) {
                            mob.takeDamage(player.bodyDamage);
                        }

                        // Mob deals damage to player
                        if (player.canTakeDamage() && !mob.isDead) {
                            player.takeDamage(mob.bodyDamage);
                            player.lastDamageTime = Date.now(); // Update last damage time
                            this.io.emit('playerDamaged', {
                                id: player.id,
                                health: player.health,
                                MobWhoDamaged: mob.name
                            });
                        }
                    }
                });

                // Check collision with petals
                Object.values(this.connectedPlayers).forEach(player => {
                    player.petals.forEach(petal => {
                        if (this.checkCollision(petal, mob)) {
                            // Petal can only deal damage if it is alive
                            if (petal.isAlive) {
                                // Petal deals damage to mob
                                if (!mob.isDead) {
                                    mob.takeDamage(petal.damage);
                                }
                            }

                            // Mob deals damage to petal if the petal is alive
                            if (petal.canTakeDamage() && !mob.isDead) {
                                petal.takeDamage(mob.bodyDamage); // Assuming mob has a bodyDamage property
                            }
                        }
                    });
                });
            });
        });
    }

    updatePetalPositions() {
        // Collect petal states and emit to all clients
        const petalStates = [];

        Object.keys(this.connectedPlayers).forEach(playerId => {
            const player = this.connectedPlayers[playerId];
            player.petals.forEach(petal => {
                petalStates.push({
                    id: player.id,
                    x: petal.x,
                    y: petal.y,
                    angle: petal.angle, // Optional: send the angle if needed for client-side calculations
                    rarity: petal.rarity,
                    name: petal.name,
                    health: petal.health
                });
            });
        });
        // Emit the current petal states to all connected clients
        this.io.emit('petalPositions', petalStates);
    }

    logMobsInZones() {
        Object.keys(this.mobs).forEach(zoneId => {
            const mobCount = this.mobs[zoneId].length; // Count mobs in the zone
            console.log(`Zone: ${zoneId}, Mob Count: ${mobCount}`);
        });
    }

    updatePlayerPosition() {
        // Emit updated positions of all players to all connected clients
        Object.keys(this.connectedPlayers).forEach(playerId => {
            const player = this.connectedPlayers[playerId];
            this.io.emit('playerUpdated', {
                id: player.id,
                x: player.x,
                y: player.y,
                emotion: player.emotion,
            });
        });
    }


    updateGameState() {
        // Create an array to hold the current state of mobs
        const mobStates = [];

        // Iterate over each zone and collect mob states
        Object.keys(this.mobs).forEach(zoneId => {
            this.mobs[zoneId].forEach(mob => {
                // Push the mob's state (id, x, y, etc.) into the array
                mobStates.push({
                    id: mob.id,
                    x: mob.x,
                    y: mob.y,
                    zoneId: zoneId,
                    rarity: mob.rarity,
                    name: mob.name,
                    health: mob.health,
                });
            });
        });

        // Emit the current mob states to all connected client
        this.io.emit('currentMobs', mobStates);
    }

    spawnMobs() {
        // Example logic to spawn mobs in each zone
        Object.keys(this.mobs).forEach(zoneId => {
            const zone = this.zones.find(z => z.id === zoneId);
            if (zone) {
                const playersInZone = this.getPlayersInZone(zoneId);
                const currentMobs = this.mobs[zoneId];

                // Check if the maximum number of mobs is reached
                if (currentMobs.length < 60) {
                    const mob = this.spawnMobInZone(zone, playersInZone);
                    if (mob) {
                        this.mobs[zoneId].push(mob);
                        this.io.emit('mobSpawned', { name: mob.name, zoneId: mob.zoneId}); // Notify clients about the new mob
                    }
                }
            }
        });
    }

    spawnMobInZone(zone, players) {
        const attempts = 10; // Number of attempts to find a valid spawn location
        for (let i = 0; i < attempts; i++) {
            const x = Math.random() * (zone.bounds.x[1] - zone.bounds.x[0]) + zone.bounds.x[0];
            const y = Math.random() * (zone.bounds.y[1] - zone.bounds.y[0]) + zone.bounds.y[0];

            // Check distance from the player
            const distance = players.length > 0 ? Math.min(...players.map(player => Math.sqrt(Math.pow(player.x - x, 2) + Math.pow(player.y - y, 2)))) : Infinity;
            if (distance >= 200) { // Ensure the mob spawns at least 200 pixels away from the player
                return MobFactory.createMob(zone.id, x, y); // Create a new Mob instance using MobFactory
            }
        }
        return null; // Return null if no valid spawn location was found after attempts
    }

    getPlayersInZone(zoneId) {
        // Log the players and the zone being checked
        const zone = this.zones.find(z => z.id === zoneId);
        // Return an array of players in the specified zone
        return Object.values(this.connectedPlayers).filter(player => {
            return player.x >= zone.bounds.x[0] && player.x <= zone.bounds.x[1] &&
                player.y >= zone.bounds.y[0] && player.y <= zone.bounds.y[1];
        });
    }
    handleMobDeath(mob) {
        const zoneId = mob.zoneId; // Get the zone ID from the mob object
        this.io.emit('mobKilled', { mob, zoneId }); // Emit the mob killed event with the mob and its zone

        // Calculate rewards for players who dealt damage
        const totalDamage = Object.values(mob.damageTaken).reduce((sum, damage) => sum + damage, 0);
        const playersRewarded = [];

        Object.keys(mob.damageTaken).forEach(playerId => {
            const damage = mob.damageTaken[playerId];
            let requiredPercentage = 0.2; // Default to 20%

            // Adjust requirements based on mob rarity
            if (mob.rarity === 'ultra') {
                requiredPercentage = 0.05; // 5% for ultra mobs
            } else if (mob.rarity === 'Topi') {
                requiredPercentage = 0.01; // 1% for Topi mobs
            }

            if (damage / totalDamage >= requiredPercentage) {
                playersRewarded.push(playerId);
                // Update experience for the player
                const player = this.connectedPlayers[playerId];
                if (player) {
                    player.experience += mob.experienceDrop; // Add experience
                    // You can also implement logic to give drops here
                    this.handleDrops(player, mob); // Call function to handle drops
                }
            }
        });

        // Remove mob from the zone
        this.mobs[mob.zoneId] = this.mobs[mob.zoneId].filter(m => m.id !== mob.id);
    }

    handleDrops(player, mob) {
        const petalDrop = mob.petalDrop; // Get the petal drop amount
        const petalType = mob.name; // Assuming petal type is the mob name
        const rarity = mob.rarity; // Get the rarity of the mob

        // Calculate the drop based on the petal configuration
        const petalConfigData = mobConfig[mob.name].rarities[rarity];
        if (petalConfigData && Math.random() < petalConfigData.dropChance) {
            // If the drop occurs, add to the player's inventory
            const existingPetal = player.inventory.find(petal => petal.petalType === petalType && petal.rarity === rarity);
            if (existingPetal) {
                existingPetal.quantity += petalDrop; // Increase quantity
            } else {
                player.inventory.push({ petalType, rarity, quantity: petalDrop }); // Add new petal type
            }
        }
    }
}

module.exports = GameServer;