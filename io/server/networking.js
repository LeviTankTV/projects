const Player = require('./Sclasses/Player.js');
const portals = require('../constants/const').portals;
const ipConnections = {}; // Object to track connections by IP
const spamCooldown = 500; // Cooldown period in milliseconds

class SocketHandler {
    constructor(io, connectedPlayers, gameServer) {
        this.io = io;
        this.connectedPlayers = connectedPlayers;
        this.initializeSocketEvents();
        this.lastMessageTimes = {};
        this.gameServer = gameServer;
    }

    initializeSocketEvents() {
        this.io.on('connection', (socket) => {
            const ip = socket.handshake.address; // Get the client's IP address

            // Check if the IP is already connected
            if (ip !== '::ffff:192.168.1.1' && ipConnections[ip] && ipConnections[ip].length >= 1) {
                // Emit an error message to the client
                socket.emit('connectionDenied', { message: 'You cannot join from the same IP address.' });
                socket.disconnect();
                return; // Stop further processing
            }


            // Add the new connection to the IP tracking
            if (!ipConnections[ip]) {
                ipConnections[ip] = [];
            }
            ipConnections[ip].push(socket.id);

            socket.emit('ClientSocketID And PlayersList', socket.id, Object.values(this.connectedPlayers));

            console.log('User  with id: ' + socket.id + ' connected from IP: ' + ip);

            socket.on('playerJoined', (player) => {
                // Create a new player object
                const newPlayer = new Player(socket.id, player.username,
                    2000 * Math.random(), 2000 * Math.random(), 20, player.level, player.experience, 100);

                // Store the new player in connected players
                this.connectedPlayers[socket.id] = newPlayer;

                // Emit the initPlayer event to the newly connected client
                socket.emit('initPlayer', {
                    level: newPlayer.level,
                    experience: newPlayer.experience,
                    username: newPlayer.username,
                    x: newPlayer.x,
                    y: newPlayer.y,
                    radius: newPlayer.radius,
                    health: newPlayer.health
                });

                // Send current mobs to the new player
                const currentMobs = this.getCurrentMobs();
                socket.emit('currentMobs', currentMobs); // Send current mobs to the player

                // Notify all clients about the new player
                this.io.emit('newPlayerJoined', newPlayer);
            });

            socket.on('teleportPlayer', (data) => {
                const player = this.connectedPlayers[socket.id];
                if (player) {
                    // Find the portal that was collided with
                    const portal = portals.find(p => p.id === data.portalId);
                    if (portal) {
                        player.teleport(portal); // Teleport the player
                        // Broadcast updated player position to all clients
                        this.io.emit('playerUpdated', player);
                    }
                }
            });

            socket.on('updatePlayer', (data) => {
                if (this.connectedPlayers[socket.id]) {
                    this.connectedPlayers[socket.id].updatePosition(data.x, data.y);
                    this.connectedPlayers[socket.id].emotion = data.emotion;
                    // Broadcast updated player position to all clients
                    this.io.emit('playerUpdated', this.connectedPlayers[socket.id]);
                }
            });

            socket.on('addPetal', ({ playerId, rarity, name}) => {
                this.connectedPlayers[playerId].addPetal(rarity, name, playerId);
                console.log(this.connectedPlayers[playerId].petals.length);
                if(this.connectedPlayers[playerId].petals.length === 10) {
                    console.log(this.connectedPlayers[playerId].petals);
                }
            });

            socket.on('petalPositions', (petalStatesFromSinglePlayer) => {
                // Update the petal positions for the current player
                petalStatesFromSinglePlayer.forEach(({ id, x, y, angle, rarity, name }) => {
                    const player = this.connectedPlayers[id]; // Get the player object by ID
                    if (player) {
                        // Find the petal associated with the player
                        const petal = player.petals.find(p => p.name === name && p.rarity === rarity);
                        if (petal) {
                            // Update petal position and angle
                            petal.x = x;
                            petal.y = y;
                            petal.angle = angle; // Update angle if needed
                        }
                    }
                });

                // Prepare all petal states to emit to all clients
                const allPetalStates = [];
                for (const playerId in this.connectedPlayers) {
                    const player = this.connectedPlayers[playerId];
                    if (player && player.petals) {
                        player.petals.forEach(petal => {
                            allPetalStates.push({
                                id: playerId, // Player ID is essential
                                x: petal.x,
                                y: petal.y,
                                angle: petal.angle,
                                rarity: petal.rarity,
                                name: petal.name,
                                health: petal.health
                            });
                        });
                    }
                }

                // Emit all of the petals
                this.io.emit('petalPositions', allPetalStates);
            });

            socket.on('sendMessage', (data) => {
                const now = Date.now();
                if (!this.lastMessageTimes[socket.id] || now - this.lastMessageTimes[socket.id] > spamCooldown) {
                    // Allow sending the message and update the last message time
                    this.lastMessageTimes[socket.id] = now;
                    // Broadcast the message to all connected clients
                    this.io.emit('receiveMessage', data);
                } else {
                    // Optionally, you can send a warning back to the user about spamming
                    socket.emit('spamWarning', { message: 'You are sending messages too quickly. Please wait a moment.' });
                }
            });

            socket.on('CurrentPlayerRespawned', () => {
                    this.connectedPlayers[socket.id].isDead = false;
                    this.connectedPlayers[socket.id].health = 100; // Reset health
                    this.connectedPlayers[socket.id].x = Math.random() * 2000; // Set to initial position
                    this.connectedPlayers[socket.id].y = Math.random() * 2000; // Set to initial position

                    // Broadcast to all clients that this player has respawned
                    this.io.emit('PlayerRespawned', this.connectedPlayers[socket.id]);
            });

            socket.on('disconnect', () => {
                console.log('User  with id: ' + socket.id + ' disconnected from IP: ' + ip);
                delete this.connectedPlayers[socket.id];

                // Remove the socket ID from the IP tracking
                if (ipConnections[ip]) {
                    ipConnections[ip] = ipConnections[ip].filter(id => id !== socket.id);
                    // Clean up if no more sockets are associated with the IP
                    if (ipConnections[ip].length === 0) {
                        delete ipConnections[ip];
                    }
                }

                this.io.emit('playerDisconnected', socket.id);
            });
        });
    }

    getCurrentMobs() {
        const mobsArray = [];
        Object.keys(this.gameServer.mobs).forEach(zoneId => {
            this.gameServer.mobs[zoneId].forEach(mob => {
                mobsArray.push({ mob, zoneId });
            });
        });
        return mobsArray;
    }
}

module.exports = SocketHandler;