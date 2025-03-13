const canvas = document.getElementById('gameCanvas');
const ctx = canvas.getContext('2d');

const playerSize = 20;
const playerSpeed = 5;
const enemySize = 20;
const enemySpeed = 2;
let enemies = [];
let score = 0;
let gameTime = 90; // Game time in seconds
let isGameOver = false;

// Player object
const player = {
    x: canvas.width / 2,
    y: canvas.height / 2,
};

// Key controls
const keys = {
    up: false,
    down: false,
    left: false,
    right: false,
};

// Handle key presses
window.addEventListener('keydown', (e) => {
    if (e.key === 'ArrowUp') keys.up = true;
    if (e.key === 'ArrowDown') keys.down = true;
    if (e.key === 'ArrowLeft') keys.left = true;
    if (e.key === 'ArrowRight') keys.right = true;
});

window.addEventListener('keyup', (e) => {
    if (e.key === 'ArrowUp') keys.up = false;
    if (e.key === 'ArrowDown') keys.down = false;
    if (e.key === 'ArrowLeft') keys.left = false;
    if (e.key === 'ArrowRight') keys.right = false;
});

// Create enemies
function createEnemy() {
    const x = Math.random() * (canvas.width - enemySize);
    const y = Math.random() * (canvas.height - enemySize);
    enemies.push({ x, y });
}

// Update enemy positions
function checkEnemyCollisions() {
    for (let i = 0; i < enemies.length; i++) {
        for (let j = i + 1; j < enemies.length; j++) {
            const enemyA = enemies[i];
            const enemyB = enemies[j];

            if (
                enemyA.x < enemyB.x + enemySize &&
                enemyA.x + enemySize > enemyB.x &&
                enemyA.y < enemyB.y + enemySize &&
                enemyA.y + enemySize > enemyB.y
            ) {
                // Collision detected, handle it
                // For example, remove both enemies
                enemies.splice(j, 1); // Remove enemyB
                enemies.splice(i, 1);  // Remove enemyA
                i--; // Decrement i to account for the removed enemy
                break; // Break out of the inner loop
            }
        }
    }
}

// Update enemy positions
function updateEnemies() {
    enemies.forEach((enemy) => {
        // Move enemies towards the player
        if (enemy.x < player.x) enemy.x += enemySpeed;
        if (enemy.x > player.x) enemy.x -= enemySpeed;
        if (enemy.y < player.y) enemy.y += enemySpeed;
        if (enemy.y > player.y) enemy.y -= enemySpeed;
    });

    // Check for collisions between enemies
    checkEnemyCollisions();
}
// Check for collisions
function checkCollisions() {
    enemies.forEach((enemy) => {
        if (
            player.x < enemy.x + enemySize &&
            player.x + playerSize > enemy.x &&
            player.y < enemy.y + enemySize &&
            player.y + playerSize > enemy.y
        ) {
            isGameOver = true;
        }
    });
}

// Update game state
function update() {
    if (isGameOver) return;

    // Move player
    if (keys.up && player.y > 0) player.y -= playerSpeed;
    if (keys.down && player.y < canvas.height - playerSize) player.y += playerSpeed;
    if (keys.left && player.x > 0) player.x -= playerSpeed;
    if (keys.right && player.x < canvas.width - playerSize) player.x += playerSpeed;

    // Update enemies
    updateEnemies();

    // Check for collisions
    checkCollisions();

    // Clear canvas
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // Draw player
    ctx.fillStyle = 'blue';
    ctx.fillRect(player.x, player.y, playerSize, playerSize);

    // Draw enemies
    ctx.fillStyle = 'red';
    enemies.forEach((enemy) => {
        ctx.fillRect(enemy.x, enemy.y, enemySize, enemySize);
    });
}

// Game loop
function gameLoop() {
    if (isGameOver) {
        ctx.fillStyle = 'black';
        ctx.font = '30px Arial';
        ctx.fillText('Game Over!', canvas.width / 2 - 70, canvas.height / 2);
        return;
    }

    update();
    requestAnimationFrame(gameLoop);
}

// Start the game
function startGame() {
    setInterval(() => {
        if (!isGameOver) {
            createEnemy();
            score++;
        }
    }, 1000); // Create a new enemy every second

    const timer = setInterval(() => {
        if (gameTime <= 0) {
            clearInterval(timer);
            isGameOver = true;
        }
        gameTime--;
    }, 1000); // Decrease game time every second

    gameLoop();
}

startGame();