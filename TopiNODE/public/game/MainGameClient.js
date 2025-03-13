import { io } from "https://cdn.socket.io/4.0.0/socket.io.min.js"; // Import Socket.IO

const socket = io('https://IP:PORT', {
    auth: {
        token: 'your_auth_token_here' // Replace with actual token logic
    }
});

let player;
const players = {};
const statsElement = document.createElement('div');
statsElement.id = 'player-stats';
document.body.appendChild(statsElement);

// Initialize the game
async function initGame() {
    try {
        const response = await fetch('/api/user/profile', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer your_auth_token_here` // Replace with actual token logic
            }
        });
        const userProfile = await response.json();

        // Create a player object with user profile data
        player = new Player(socket.id, userProfile.username);

        // Notify the server about the new player
        socket.emit('player:init', player);
    } catch (error) {
        console.error('Error fetching user profile:', error);
    }
}

// Update player position
function updatePlayerPosition() {
    // Example movement logic
    document.addEventListener('keydown', (event) => {
        switch (event.key) {
            case 'ArrowUp':
                player.y -= 5;
                break;
            case 'ArrowDown':
                player.y += 5;
                break;
            case 'ArrowLeft':
                player.x -= 5;
                break;
            case 'ArrowRight':
                player.x += 5;
                break;
        }
        // Emit new position to server
        socket.emit('player:move', { x: player.x, y: player.y });
    });
}

// Update stats display
function updateStatsDisplay() {
    statsElement.innerHTML = `
        <h2>Player Stats</h2>
        <p>Level: ${player.level}</p>
        <p>Experience: ${player.experience}</p>
        <p>Inventory: ${JSON.stringify(player.inventory)}</p>
    `;
}

// Handle incoming player updates from the server
socket.on('player:update', (updatedPlayer) => {
    players[updatedPlayer.id] = updatedPlayer;
});

// Handle player join
socket.on('player:joined', (newPlayer) => {
    players[newPlayer.id] = newPlayer;
});

// Handle player disconnect
socket.on('player:left', (data) => {
    delete players[data.id];
});

// Main game loop (update stats and render)
function gameLoop() {
    updateStatsDisplay();
    requestAnimationFrame(gameLoop);
}

// Start the game
initGame();
updatePlayerPosition();
gameLoop();