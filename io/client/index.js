const socket = io('http://localhost:3000');
const canvas = document.getElementById('gameCanvas');
const chatInput = document.getElementById('chatInput');
let chatVisible = true; // Track if chat messages are visible
let showMinimap;
let showZone;
// Function to handle the Enter key behavior
function handleEnterKey(event) {
    if (event.key === 'Enter') {
        event.preventDefault(); // Prevent default behavior (like form submission)

        if (document.activeElement !== chatInput) {
            // If chat input is not focused, focus it
            chatInput.focus();
        } else if (chatInput.value.trim() === '') {
            // If input is empty, unfocus the chat input
            chatInput.blur(); // Unfocus the chat input
        } else {
            // If input has a message, send it
            const message = chatInput.value.trim();
            socket.emit('sendMessage', { username: currentPlayer.username, message });
            chatInput.value = ''; // Clear the input field
        }
    }
}

// Add keydown event listener to the window
window.addEventListener('keydown', handleEnterKey);

// Handle chat input focus
chatInput.addEventListener('focus', () => {
    chatVisible = true; // Chat input is focused
    renderMessages(); // Render all messages again
});

// Optional: If you want to fade out messages after a delay when chat is unfocused
chatInput.addEventListener('blur', () => {
    chatVisible = false;
    setTimeout(() => {
        if (!chatVisible) {
            const messagesDiv = document.getElementById('messages');
            Array.from(messagesDiv.children).forEach((msg) => {
                msg.style.opacity = '0'; // Fade out messages
                setTimeout(() => {
                    messagesDiv.removeChild(msg); // Remove from DOM after fade out
                }, 5000); // Wait for fade out duration
            });
        }
    }, 1000); // Slight delay to ensure blur event has completed
});

const chat = document.getElementById('chatContainer');

const ctx = gameCanvas.getContext('2d');
canvas.width = window.innerWidth;
canvas.height = window.innerHeight;

let isGPressed = false; // Флаг для отслеживания нажатия 'G'
let targetWidth = canvas.width;
let targetHeight = canvas.height;
let scalingFactor = 1; // Фактор масштабирования
const scalingSpeed = 0.05; // Скорость изменения размеров

window.addEventListener('keydown', (event) => {
    if (event.key === 'g' && !isGPressed) { // Проверка, нажата ли клавиша "G"
        isGPressed = true; // Установить флаг
        targetWidth *= 1.5; // Установить целевую ширину
        targetHeight *= 1.5; // Установить целевую высоту
    }
});

window.addEventListener('keyup', (event) => {
    if (event.key === 'g') {
        isGPressed = false; // Сбросить флаг
        targetWidth /= 1.5; // Установить целевую ширину обратно
        targetHeight /= 1.5; // Установить целевую высоту обратно
    }
});

// Функция для плавного изменения размеров канваса
function updateCanvasSize() {
    // Плавно изменять ширину и высоту канваса
    if (canvas.width !== targetWidth) {
        canvas.width += (targetWidth - canvas.width) * scalingSpeed;
    }
    if (canvas.height !== targetHeight) {
        canvas.height += (targetHeight - canvas.height) * scalingSpeed;
    }

    // Запросить следующий кадр анимации
    requestAnimationFrame(updateCanvasSize);
}

const rarities = ['common', 'unusual', 'rare', 'epic', 'legendary', 'mythic', 'ultra', 'Topi'];
let currentRarityIndex = 0; // Initialize the index for the current rarity

window.addEventListener('keydown', (event) => {
    if (event.key === 'p') {
        // Check if the current player can add another petal
        if (currentPlayer.petals.length < 7) { // Check if the current number of petals is less than 7
            // Get the current rarity using the index
            const rarity = rarities[currentRarityIndex];

            // Add the petal with the current rarity
            currentPlayer.addPetal(rarity, 'Arc');
            socket.emit('addPetal', {
                playerId: currentPlayerId, // Or use currentPlayerId if you have it
                rarity: rarity,
                name: 'Arc',
                radius: 50,
                angleOffset: Math.random() * Math.PI * 2,
                speed: 0.05
            });

            // Log the current state
            console.log(currentPlayer.petals.length);
            console.log(currentPlayer.petals);

            // Update the rarity index for the next petal
            currentRarityIndex = (currentRarityIndex + 1) % rarities.length; // Wrap around using modulo
        } else {
            alert("Cannot add more petals, maximum limit of 7 reached."); // Console feedback
            // Optionally, you could also display a message in the game UI
            // displayMessage("You have reached the maximum number of petals.");
        }
    }
});
// Начать анимацию
updateCanvasSize();
const mobs = {};
const Players = {};
let currentPlayerId;
const inputManager = new InputManager();
const playerRenderer = new PlayerRenderer(ctx);
const zoneRenderer = new RenderZones(ctx);
// Important!
socket.on('ClientSocketID And PlayersList', (id, players) => {
    currentPlayerId = id; // Set the current player's ID
    // Populate the Players object with the received players
    players.forEach(player => {
        Players[player.id] = new Flower(player.username, player.level, player.experience, player.x, player.y, player.radius, player.health, playerRenderer); // Add each player to the Players object
    });
});

// New player joined (or this player)
socket.on('newPlayerJoined', (player) => {
    Players[player.id] = new Flower(player.username, player.level, player.experience, player.x, player.y, player.radius, player.health, playerRenderer);
})

// Disconnect from game or returning to game menu
socket.on('playerDisconnected', (id) => {
    delete Players[id];
})

let currentPlayer = new Flower(null, null, null, null, null, null, null, playerRenderer);
// Initializing player
socket.on('initPlayer', (player) => {
    currentPlayer = new Flower(player.username, player.level, player.experience, player.x, player.y, player.radius, player.health, playerRenderer);
    console.log(currentPlayer);
})

// Updating Player instance
socket.on('playerUpdated', (data) => {
    // Update the position of the player in the Players object
    if (Players[data.id]) {
        Players[data.id].x = data.x;
        Players[data.id].y = data.y;
        Players[data.id].emotion = data.emotion;
    }
});

socket.on('petalPositions', (petalStates) => {
    if (Array.isArray(petalStates)) {
        petalStates.forEach(({ id, x, y, angle, rarity, name, health }) => {
            // Here is where you want to fetch a specific player
            const player = Players[id]; // Get the player object
            if (player) {
                let petal = player.petals.find(p => p.name === name && p.rarity === rarity); // Try to find an existing petal
                if (petal) {
                    // Update petal position and angle
                    petal.x = x;
                    petal.y = y;
                    petal.angle = angle; // Update angle if needed
                    petal.health = health;
                } else {
                    // Petal does not yet exists for this player, create it
                    const newPetal = new Petal(id, 50, 0.05, Math.random() * Math.PI * 2, rarity, name);
                    newPetal.x = x;
                    newPetal.y = y;
                    newPetal.angle = angle;
                    newPetal.health = health;
                    player.petals.push(newPetal);
                }
            }
        });
    } else {
        console.error('Expected petalStates to be an array, but received:', petalStates);
    }
});

let KM;
socket.on('playerDamaged', (data) => {
    // Update health for the player who got damaged
    if (data.id !== currentPlayerId) {
        Players[data.id].health = data.health;
        console.log(Players[data.id].health);

        // Check if the player's health is 0 and set isDead to true
        if (data.health <= 0) {
            Players[data.id].isDead = true; // Mark the player as dead
        }
    }

    // Update health for the current player
    if (data.id === currentPlayerId) {
        currentPlayer.health = data.health;

        // Check if the current player's health is 0 and set isDead to true
        if (data.health <= 0) {
            KM = data.MobWhoDamaged;
            currentPlayer.isDead = true; // Mark the current player as dead
        }
    }
});

document.getElementById('continueButton').addEventListener('click', () => {
    socket.emit('CurrentPlayerRespawned');
});

socket.on('PlayerRespawned', (data) => {
    if (data.id !== currentPlayerId) {
        Players[data.id].health = data.health;
        Players[data.id].isDead = false;
        Players[data.id].x = data.x;
        Players[data.id].y = data.y;
    }
    if (data.id === currentPlayerId) {
        currentPlayer.isDead = false;
        currentPlayer.x = data.x;
        currentPlayer.y = data.y;
        currentPlayer.health = data.health;
        const gameOverMenu = document.getElementById('gameOverMenu');
        gameOverMenu.style.opacity = '0'; // Fade out
        gameOverMenu.style.display = 'none' // Match the duration of the fade out
    }
});

let isErrorState = false;
socket.on('connectionDenied', (data) => {
    isErrorState = true; // Set the error state
    showErrorScreen(data.message); // Show the error message on the screen
});

const messages = []; // Array to store messages with timestamps

socket.on('receiveMessage', (data) => {
    const message = {
        username: data.username,
        message: data.message,
        timestamp: Date.now() // Store the timestamp
    };
    messages.push(message); // Add the message to the array

    renderMessages(); // Render messages immediately
});

// Function to render messages
function renderMessages() {
    const messagesDiv = document.getElementById('messages');
    messagesDiv.innerHTML = ''; // Clear existing messages

    messages.forEach((msg, index) => {
        const messageElement = document.createElement('div');
        messageElement.innerHTML = `<strong>${msg.username}:</strong> ${msg.message}`;
        messagesDiv.appendChild(messageElement);

        // Set a timeout to hide the message after 10 seconds
        setTimeout(() => {
            messageElement.style.opacity = '0'; // Fade out
            setTimeout(() => {
                messagesDiv.removeChild(messageElement); // Remove from DOM after fade out
            }, 5000); // Wait for fade out duration
        }, 5000); // 10 seconds
    });

    messagesDiv.scrollTop = messagesDiv.scrollHeight; // Scroll to the bottom
}

socket.on('spamWarning', (data) => {
    // Display the spam warning to the user
    alert(data.message); // You can customize this to use a more elegant UI approach
});

// Listen for current mobs event
socket.on('currentMobs', (currentMobs) => {
    /* Turn on when problems with current mobs
    console.log("Received current mobs:", currentMobs); */

    currentMobs.forEach(({ id, x, y, zoneId, rarity, name, health}) => {
        // Ensure the mobs array for the zone exists
        if (!mobs[zoneId]) {
            mobs[zoneId] = [];
        }

        // Find the existing mob or create a new one
        const existingMob = mobs[zoneId].find(mob => mob.id === id);
        if (existingMob) {
            // Update the existing mob's position
            existingMob.x = x;
            existingMob.y = y;
            // Optionally update other properties if needed (like rarity)
            existingMob.rarity = rarity; // Update rarity if it's a property that can change
            existingMob.name = name;
            existingMob.id = id;
            existingMob.health = health;
        } else {
            // Create a new mob object based on its name
            let newMob;
            if (name === "Tyrant") {
                newMob = new Tyrant(id, x, y, rarity); // Create a new Tyrant instance
            } else if (name === "Arcane") {
                newMob = new Arcane(id, x, y, rarity); // Create a new Arcane instance
            } else {
                console.warn(`Unknown mob name: ${name}`); // Handle unknown mob names
                return; // Skip if the mob name is not recognized
            }

            // Add the new mob object to the corresponding zone
            mobs[zoneId].push(newMob);
        }
    });
});

// Listen for mob spawn event
socket.on('mobSpawned', ({ mob, zoneId }) => {
    // Ensure the mobs array for the zone exists
    if (!mobs[zoneId]) {
        mobs[zoneId] = [];
    }
    // Create an arcane object for the mob based on its rarity
    let newMob;
    if (mob.name === "Tyrant") {
        newMob = new Tyrant(id, x, y, rarity); // Create a new Tyrant instance
    } else if (mob.name === "Arcane") {
        newMob = new Arcane(id, x, y, rarity); // Create a new Arcane instance
    } else {
        console.warn(`Unknown mob name: ${mob.name}`); // Handle unknown mob names
        return; // Skip if the mob name is not recognized
    }
});

// Listen for mob killed event
socket.on('mobKilled', ({ mob, zoneId }) => {
    if (mobs[zoneId]) {
        mobs[zoneId] = mobs[zoneId].filter(m => m.id !== mob.id); // Remove the mob from the zone
    }
});

function startGame(username, level, experience) {
    localStorage.getItem('ShowMinimap' === 'true' ? ShowMinimap = true : ShowMinimap = false);
    localStorage.getItem('ShowCurrentZone' === 'true' ? ShowCurrentZone = true : ShowCurrentZone = false);
    currentPlayer.username = username;
    currentPlayer.level = level;
    currentPlayer.experience = experience;
    showZone = JSON.parse(localStorage.getItem('ShowCurrentZone')) || false; // Default to false if null
    showMinimap = JSON.parse(localStorage.getItem('ShowMinimap')) || false; // Default to false if null

    calculateHotbarSlots(currentPlayer.level);

    chat.style.display = 'block';
    gameExit.style.display = 'block';

    socket.emit('playerJoined', currentPlayer);
    requestAnimationFrame(startGameLoop);
}

function emitPetalPositions() {
    if (!currentPlayer || !currentPlayer.petals) {
        console.error('currentPlayer is not defined or does not have petals:', currentPlayer);
        return; // Exit the function if currentPlayer or petals is not defined
    }

    const petalStates = currentPlayer.petals.map(petal => ({
        id: currentPlayerId,
        x: petal.x,
        y: petal.y,
        angle: petal.angle, // Include angle if necessary
        rarity: petal.rarity,
        name: petal.name,
        radius: petal.radius
    }));

    // Emit the petal states to the server
    socket.emit('petalPositions', petalStates);
}

let lastUpdateTime = Date.now();
let debugMode = true;

function startGameLoop() {
    if (isErrorState) {
        return; // Stop the game loop if in error state
    }
    const currentTime = Date.now();
    const deltaTime = (currentTime - lastUpdateTime) / 1000; // Convert to seconds
    lastUpdateTime = currentTime;
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    const movement = inputManager.update();
    inputManager.updateEmotion(currentPlayer); // Update player emotion based on input
    currentPlayer.updateRadius(inputManager);


    const speedMultiplier = 225;
    // dont forget to change back to 175


    // Update player position based on speed and deltaTime
    if (currentPlayer.isDead) {
        // Show the game over menu
        const gameOverMenu = document.getElementById('gameOverMenu');
        const gameOverMessage = document.getElementById('gameOverMessage');

        gameOverMessage.innerText = 'You were destroyed by ' + KM; // Set the message
        gameOverMenu.style.display = 'flex'; // Show the menu
        gameOverMenu.style.opacity = '1'; // Fade in
    } else {
        currentPlayer.updatePosition(
            movement.x * speedMultiplier * deltaTime,
            movement.y * speedMultiplier * deltaTime
        );
        currentPlayer.updatePetals();
        emitPetalPositions(); // Emit petal positions to the server
    }

    for (const portal of zoneRenderer.portals) {
        const distance = Math.hypot(currentPlayer.x - portal.position[0], currentPlayer.y - portal.position[1]);
        if (distance < currentPlayer.radius + 30) { // 30 is the portal radius
            // Attempt to teleport the player
            if (currentPlayer.teleport(portal)) {
                // Emit teleport event to the server
                socket.emit('teleportPlayer', { portalId: portal.id });
                console.log(currentPlayer.x, currentPlayer.y);
            }
            break; // Exit loop after attempting to teleport
        }
    }

    // Send updated position to the server
    socket.emit('updatePlayer', { x: currentPlayer.x, y: currentPlayer.y, emotion: currentPlayer.emotion});

    const cameraOffsetX = currentPlayer.x - (canvas.width / 2); // Adjust for FOV
    const cameraOffsetY = currentPlayer.y - (canvas.height / 2); // Adjust for FOV

    // Draw zones first
    zoneRenderer.draw(cameraOffsetX, cameraOffsetY);

    // Draw current player using PlayerRenderer on top of zones
    currentPlayer.draw(cameraOffsetX, cameraOffsetY, movement, currentPlayer.emotion, currentPlayer.radius, currentPlayer.isDead); // Pass movement to draw method
    if (currentPlayer.petals && currentPlayer.petals.length > 0) { // Check if petals exist and are not empty
        currentPlayer.petals.forEach(petal => {
            // Check if the petal's health is greater than 0 before drawing
            if (petal.health > 0) {
                petal.draw(playerRenderer.ctx, cameraOffsetX, cameraOffsetY); // Draw each petal
            }
        });
    }

    const dollX = 50; //
    const dollY = 40; //

    // Draw health bar
    const healthBarWidth = 200; // Width of the health bar
    const healthBarHeight = 20; // Height of the health bar
    const healthBarX = dollX + 16; // Align with the doll
    const healthBarY = dollY - 15; // Below the doll
    drawHealthBar(ctx, healthBarX, healthBarY, healthBarWidth, healthBarHeight, currentPlayer.health, currentPlayer.maxHealth);

    // Draw experience bar
    const experienceBarWidth = 150; // Width of the experience bar
    const experienceBarHeight = 15; // Height of the experience bar
    const experienceBarX = dollX + 40; // Align with the doll
    const experienceBarY = healthBarY + healthBarHeight + 10; // Below the health bar
    drawExperienceBar(ctx, experienceBarX, experienceBarY, experienceBarWidth, experienceBarHeight, currentPlayer.experience, currentPlayer.maxExperience);

    ctx.fillStyle = 'white'; // Text color
    ctx.font = 'bold 18px Comic Sans Ms, sans-serif'; // Font style and size
    ctx.textAlign = 'left';

// Calculate the text width for centering
    const nickname = currentPlayer.username; // Get the current player's nickname
    const textWidth = ctx.measureText(nickname).width; // Measure the width of the nickname

// Draw the nickname centered beneath the doll
    const nicknameX = dollX + (currentPlayer.radius - textWidth / 2 + 90); // Center the text
    const nicknameY = dollY; // Position below the doll
    ctx.fillText(nickname, nicknameX, nicknameY); // Draw the nickname

    playerRenderer.drawDoll(dollX, dollY, currentPlayer.radius * 1.5, currentPlayer.emotion, currentPlayer.username, currentPlayer.isDead);
    // Display level number inside experience bar
    ctx.fillStyle = 'white'; // Text color
    ctx.font = 'bold 12px Comic Sans MS, sans-serif'; // Font style
    ctx.fillText(`Level: ${currentPlayer.level}`, experienceBarX + experienceBarWidth / 2 - 30, experienceBarY + experienceBarHeight / 2 + 4); // Centered text
    ctx.textAlign = 'center';
    const currentZone = getZone(currentPlayer.x, currentPlayer.y);
    if (currentZone && mobs[currentZone]) {
        mobs[currentZone].forEach(mob => {
            // Apply camera offset
            mob.draw(ctx, cameraOffsetX, cameraOffsetY); // Pass camera offsets to the draw method
        });
    }
    if (currentZone && mobs[currentZone]) {
        const topiMobs = mobs[currentZone].filter(mob => mob.rarity === 'Topi'); // Filter for Topi rarity mobs
        if (topiMobs.length > 0) {
            const bossName = mobs[currentZone][0].name; // Name of the boss
            const bossProgress = 0.5; // Replace with actual progress value (0 to 1)
            drawBossProgressBar(ctx, currentPlayer, bossName, bossProgress, 'Topi'); // Draw the boss progress bar
        }
    }

    if (showZone) {
        const currentZone = getZone(currentPlayer.x, currentPlayer.y);

        // Display current zone at top middle of the screen
        ctx.fillStyle = 'black'; // Text color
        if (currentZone) {
            ctx.fillStyle = 'white';
            ctx.textAlign = 'center';
            ctx.font = 'bold 32px Garamond';
            ctx.fillText(`${currentZone} zone`, canvas.width / 2 , 45); // Center text
     //       console.log(canvas.width, canvas.height);
        }
    }

    // Draw other players
    for (const id in Players) {
        const otherPlayer = Players[id]; // Get player data
        if (id === currentPlayerId) {

        }
        if (id !== currentPlayerId) {
            const flower = new Flower(otherPlayer.username, otherPlayer.level, otherPlayer.experience, otherPlayer.x, otherPlayer.y, otherPlayer.radius, otherPlayer.health, playerRenderer);
            flower.draw(cameraOffsetX, cameraOffsetY, { x: 0, y: 0 }, otherPlayer.emotion, otherPlayer.radius, otherPlayer.isDead); // No movement for other players

            // Calculate positions for nickname and level
            const flowerX = otherPlayer.x - cameraOffsetX; // X position of the flower
            const flowerY = otherPlayer.y - cameraOffsetY; // Y position of the flower

            // Set text properties for the nickname
            ctx.fillStyle = 'white'; // Text color for nickname
            ctx.font = 'bold 12px Arial'; // Font style and size for nickname
            ctx.textAlign = 'center'; // Center the text horizontally

            // Draw a background rectangle for nickname
            const nickname = otherPlayer.username;
            const level = otherPlayer.level;
            const nicknameWidth = ctx.measureText(nickname).width;
            ctx.fillStyle = 'rgba(0, 0, 0, 0)'; // Semi-transparent background color
            ctx.fillRect(flowerX - nicknameWidth / 2 - 5, flowerY - 30, nicknameWidth + 10, 25); // Background rectangle
            const healthBarWidth = 45; // Width of the health bar
            const adminBarWidth = 80;
            const healthBarHeight = 8; // Height of the health bar
            const adminBarX = flowerX - adminBarWidth / 2;
            const healthBarX = flowerX - healthBarWidth / 2; // Center the health bar
            const healthBarY = flowerY + 24; // Position below the player

// Calculate health percentage
            const healthPercentage = otherPlayer.health / currentPlayer.maxHealth;
            // Draw the nickname above the flower
            if (otherPlayer.petals) {
                otherPlayer.petals.forEach(petal => {
                    if(petal.health > 0) {
                        petal.draw(ctx, cameraOffsetX, cameraOffsetY);
                    }
                });
            }

            if (otherPlayer.isDead !== true) {
                if (nickname === 'LeviTank_TV') {

// Draw the health bar background (border)
                    ctx.fillStyle = 'rgba(0, 0, 0, 0.5)'; // Dark background for the health bar
                    drawRoundedRect(ctx, adminBarX, healthBarY, adminBarWidth, healthBarHeight, 5);

// Draw the health bar itself
                    ctx.fillStyle = 'green'; // Health color
                    drawRoundedRect(ctx, adminBarX, healthBarY, adminBarWidth * healthPercentage, healthBarHeight, 5);
                    ctx.fillStyle = 'red'; // Text color for nickname
                    ctx.fillText(nickname, flowerX, flowerY + 42); // Adjust the Y position as needed
                    ctx.font = 'bold 10px Comic Sans MS';
                    ctx.fillText(level, flowerX, flowerY + 52)

                    // Set text properties for the level
                    ctx.fillStyle = 'gold'; // Text color for level
                    ctx.font = '12px Arial'; // Font style and size for level
                } else {
                    ctx.fillStyle = 'rgba(0, 0, 0, 0.5)'; // Dark background for the health bar
                    drawRoundedRect(ctx, healthBarX, healthBarY, healthBarWidth, healthBarHeight, 5);

// Draw the health bar itself
                    ctx.fillStyle = 'green'; // Health color
                    drawRoundedRect(ctx, healthBarX, healthBarY, healthBarWidth * healthPercentage, healthBarHeight, 5);
                    ctx.fillStyle = 'white'; // Text color for nickname
                    ctx.fillText(nickname, flowerX, flowerY + 40); // Adjust the Y position as needed
                    ctx.font = 'bold 10px Comic Sans MS';
                    ctx.fillText(level, flowerX, flowerY + 52)

                    // Set text properties for the level
                    ctx.fillStyle = 'gold'; // Text color for level
                    ctx.font = '12px Arial'; // Font style and size for level
                }
            }
        }
    }

    if (showMinimap) {
        drawMinimap();
    }

    // Debug mode
    if (debugMode) {
        ctx.fillStyle = 'dark'; // Text color
        ctx.font = 'bold 16px Comic Sans MS';
        let startY = canvas.height/4; // Initial Y position for the first line
        ctx.textAlign = 'left';

        ctx.fillText('Debug Mode: ON', 3, startY);
        startY -= 20; // Move down for the next line
        ctx.fillText('Players Count: ' + Object.keys(Players).length, 3, startY);
        startY -= 20; // Move down for the next line
        ctx.fillText('Current Player ID: ' + currentPlayerId, 3, startY);
        startY -= 20; // Move down for the next line
        ctx.fillText('Player Position: (' + Math.floor(currentPlayer.x) + ', ' + Math.floor(currentPlayer.y) + ')', 3, startY);
        startY -= 20; // Move down for the next line
        ctx.fillText('Player health: ' + currentPlayer.health, 3, startY);
        startY -= 20;
        if (currentPlayer.petals.length > 0) {
            ctx.fillText('Player petal position: ' + currentPlayer.petals[0].x + ', ' + currentPlayer.petals[0].y, 3, startY);
        }

        // Display mob counts for each zone
    //    zones.forEach(zone => {
    //        const mobCount = mobs[zone.id] ? mobs[zone.id].length : 0; // Count mobs in the zone
    //        ctx.fillText(`${zone.id} Zone Mob Count: ${mobCount}`, 3, startY);
    //        startY += 20; // Move down for the next line
    //    });
    }


    drawHotbars();
    requestAnimationFrame(startGameLoop);
}

window.addEventListener('resize', () => {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
});

function renderMob(mob) {
    // Implement the rendering logic for mobs
    ctx.fillStyle = 'red'; // Example mob color
    ctx.beginPath();
    ctx.arc(mob.x, mob.y, 15, 0, Math.PI * 2); // Draw a circle for the mob
    ctx.fill();
}

function drawMinimap() {

    const minimapWidth = 500; // Increased width for better visibility
    const minimapHeight = 75; // Keep the same height
    const minimapX = canvas.width - minimapWidth - 10; // 10 pixels from right
    const minimapY = 10; // 10 pixels from top

    // Draw minimap background
    ctx.fillStyle = 'rgba(255, 255, 255, 0.8)'; // Semi-transparent white
    ctx.fillRect(minimapX, minimapY, minimapWidth, minimapHeight);
    ctx.strokeStyle = 'black'; // Border color
    ctx.strokeRect(minimapX, minimapY, minimapWidth, minimapHeight);

    // Draw the zones in the minimap
    for (const zone of zoneRenderer.zones) {
        const zoneX1 = minimapX + (zone.bounds.x[0] / 20000) * minimapWidth;
        const zoneY1 = minimapY;
        const zoneX2 = minimapX + (zone.bounds.x[1] / 20000) * minimapWidth;
        const zoneY2 = minimapY + minimapHeight;

        // Draw zone rectangle
        ctx.fillStyle = zone.color;
        ctx.fillRect(zoneX1, zoneY1, zoneX2 - zoneX1, zoneY2 - zoneY1);

        ctx.strokeStyle = 'black'; // Set the stroke color to black
        ctx.lineWidth = 5; // Set the line width for the border
        ctx.strokeRect(zoneX1, zoneY1, zoneX2 - zoneX1, zoneY2 - zoneY1); // Draw the border
    }

    // Draw walls on the minimap
    ctx.fillStyle = 'black'; // Wall color
    for (const wall of zoneRenderer.walls) {
        const wallX1 = minimapX + (wall.bounds.x[0] / 20000) * minimapWidth;
        const wallY1 = minimapY;
        const wallX2 = minimapX + (wall.bounds.x[1] / 20000) * minimapWidth;
        const wallY2 = minimapY + minimapHeight;

        ctx.fillRect(wallX1, wallY1, wallX2 - wallX1, wallY2 - wallY1); // Draw rectangle for wall
    }

    // Draw portals on the minimap
    ctx.fillStyle = 'silver'; // Portal color
    for (const portal of zoneRenderer.portals) {
        const portalX = minimapX + (portal.position[0] / 20000) * minimapWidth;
        const portalY = minimapY + (1000 / 2000) * minimapHeight; // Center the portals vertically in the minimap

        ctx.beginPath();
        ctx.arc(portalX, portalY, 3, 0, Math.PI * 2); // Draw portal circle
        ctx.fill(); // Fill portal with color
    }

    // Draw player position in the minimap with a black outline
    const playerMinimapX = minimapX + (currentPlayer.x / 20000) * minimapWidth;
    const playerMinimapY = minimapY + (currentPlayer.y / 2000) * minimapHeight;

    ctx.fillStyle = 'red'; // Player dot color
    ctx.beginPath();
    ctx.arc(playerMinimapX, playerMinimapY, 3, 0, Math.PI * 2); // Draw player dot
    ctx.fill(); // Fill the player dot with red

    // Draw black outline around the player dot
    ctx.strokeStyle = 'aquamarine'; // Set stroke color to black
    ctx.lineWidth = 1; // Set stroke width
    ctx.beginPath();
    ctx.arc(playerMinimapX, playerMinimapY, 3, 0, Math.PI * 2); // Draw outline circle
    ctx.stroke(); // Apply the stroke
}
function getZone(x, y) {
    for (const zone of zones) {
        if (x >= zone.bounds.x[0] && x <= zone.bounds.x[1] &&
            y >= zone.bounds.y[0] && y <= zone.bounds.y[1]) {
            return zone.id;
        }
    }
    return null; // Player is outside all zones
}

const zones = [
    {
        id: 'Common',
        bounds: { x: [0, 2125], y: [0, 2000] },
        portal1: { position: [2075, 1000], linkedZone: 'unusual' },
        portal2: { position: [2550, 1000], linkedZone: 'common' }
    },
    {
        id: 'Unusual',
        bounds: { x: [2500, 4625], y: [0, 2000] },
        portal1: { position: [4575, 1000], linkedZone: 'rare' },
        portal2: { position: [5050, 1000], linkedZone: 'unusual' }
    },
    {
        id: 'Rare',
        bounds: { x: [5000, 7125], y: [0, 2000] },
        portal1: { position: [7075, 1000], linkedZone: 'epic' },
        portal2: { position: [7550, 1000], linkedZone: 'rare' }
    },
    {
        id: 'Epic',
        bounds: { x: [7500, 9625], y: [0, 2000] },
        portal1: { position: [9575, 1000], linkedZone: 'legendary' },
        portal2: { position: [10050, 1000], linkedZone: 'epic' }
    },
    {
        id: 'Legendary',
        bounds: { x: [10000, 12125], y: [0, 2000] },
        portal1: { position: [12075, 1000], linkedZone: 'mythic' },
        portal2: { position: [12550, 1000], linkedZone: 'legendary' }
    },
    {
        id: 'Mythic',
        bounds: { x: [12500, 14625], y: [0, 2000] },
        portal1: { position: [14575, 1000], linkedZone: 'ultra' },
        portal2: { position: [15050, 1000], linkedZone: 'mythic' }
    },
    {
        id: 'Ultra',
        bounds: { x: [15000, 17125], y: [0, 2000] },
        portal1: { position: [17075, 1000], linkedZone: '???' },
        portal2: { position: [17550, 1000], linkedZone: 'ultra' }
    },
    {
        id: 'Topi',
        bounds: { x: [17500, 21500], y: [0, 2000] },
        portal1: null,
        portal2: null
    } // Last zone with no portals
];

function showErrorScreen(message) {
    const errorDiv = document.createElement('div');
    errorDiv.style.position = 'fixed';
    errorDiv.style.top = '0';
    errorDiv.style.left = '0';
    errorDiv.style.width = '100%';
    errorDiv.style.height = '100%';
    errorDiv.style.backgroundColor = 'red';
    errorDiv.style.color = 'black';
    errorDiv.style.display = 'flex';
    errorDiv.style.alignItems = 'center';
    errorDiv.style.justifyContent = 'center';
    errorDiv.style.fontSize = '24px';
    errorDiv.innerText = message;
    errorDiv.zIndex = 1123123;
    chatInput.style.display = 'none';
    document.body.appendChild(errorDiv);
}

function drawBossProgressBar(ctx, currentPlayer, bossName, bossProgress, bossRarity) {
    const barWidth = 500; // Width of the progress bar
    const barHeight = 30; // Height of the progress bar
    const barX = (canvas.width - barWidth) / 2; // Centered horizontally
    const barY = 90; // Lowered Y position for the bar (100 pixels lower)

    // Draw the rounded rectangle for the progress bar background
    ctx.fillStyle = 'rgba(0, 0, 0, 0.7)'; // Semi-transparent black background
    ctx.beginPath();
    const cornerRadius = 10; // Corner radius for the rounded rectangle
    ctx.moveTo(barX + cornerRadius, barY);
    ctx.lineTo(barX + barWidth - cornerRadius, barY);
    ctx.arcTo(barX + barWidth, barY, barX + barWidth, barY + cornerRadius, cornerRadius);
    ctx.lineTo(barX + barWidth, barY + barHeight - cornerRadius);
    ctx.arcTo(barX + barWidth, barY + barHeight, barX + barWidth - cornerRadius, barY + barHeight, cornerRadius);
    ctx.lineTo(barX + cornerRadius, barY + barHeight);
    ctx.arcTo(barX, barY + barHeight, barX, barY + cornerRadius, cornerRadius);
    ctx.lineTo(barX, barY + cornerRadius);
    ctx.arcTo(barX, barY, barX + cornerRadius, barY, cornerRadius);
    ctx.closePath();
    ctx.fill(); // Fill the background

    // Draw the filled portion of the progress bar with Aquamarine color
    ctx.fillStyle = 'aquamarine'; // Color of the filled portion
    const fillWidth = (barWidth - 2); // Calculate the width of the filled portion

    // Draw the filled portion with rounded corners
    ctx.beginPath(); // Start a new path for the filled portion
    ctx.moveTo(barX + 1 + cornerRadius, barY + 1); // Move to the start point with corner radius
    ctx.lineTo(barX + fillWidth + 1 - cornerRadius, barY + 1); // Top edge
    ctx.arcTo(barX + fillWidth + 1, barY + 1, barX + fillWidth + 1, barY + barHeight - 1, cornerRadius); // Top right corner
    ctx.lineTo(barX + fillWidth + 1, barY + barHeight - 1 - cornerRadius); // Right edge
    ctx.arcTo(barX + fillWidth + 1, barY + barHeight - 1, barX + fillWidth + 1 - cornerRadius, barY + barHeight - 1, cornerRadius); // Bottom right corner
    ctx.lineTo(barX + 1 + cornerRadius, barY + barHeight - 1); // Bottom edge
    ctx.arcTo(barX, barY + barHeight - 1, barX, barY + barHeight - 1 - cornerRadius, cornerRadius); // Bottom left corner
    ctx.lineTo(barX, barY + 1 + cornerRadius); // Left edge
    ctx.arcTo(barX, barY, barX + cornerRadius, barY, cornerRadius); // Top left corner
    ctx.closePath();
    ctx.fill(); // Fill the rounded filled portion

    // Draw the boss name above the progress bar
    ctx.fillStyle = 'black'; // Text color
    ctx.font = 'bold 32px Comic Sans MS, sans-serif'; // Font style for the boss name
    ctx.textAlign = 'center'; // Center text
    ctx.fillText(bossName, canvas.width / 2, barY - 10); // Position the name above the bar
}
