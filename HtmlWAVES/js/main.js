import {LocalGameManager} from './LocalGameManager.js';
import {Player} from './player.js';

document.addEventListener('localGameStart', (event) => {
    const playerName = event.detail.playerName;
    const theme = event.detail.theme || 'garden'; // Default to garden if no theme

    // Clear all UI elements
    const uiElements = [
        '.start-container',
        '.theme-selector',
        '#theme-pointer',
        '.game-mode-container',
        '#biome-info',
        '.animated-title'
    ];

    uiElements.forEach(selector => {
        const element = document.querySelector(selector);
        if (element) {
            element.remove();
        }
    });

    // Remove any existing overlays or modal screens
    const overlays = document.querySelectorAll('[data-overlay="true"]');
    overlays.forEach(overlay => overlay.remove());

    const canvas = document.getElementById('gameCanvas');
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;

    // Make canvas full screen and remove any margins/paddings
    canvas.style.position = 'fixed';
    canvas.style.top = '0';
    canvas.style.left = '0';
    canvas.style.width = '100%';
    canvas.style.height = '100%';
    canvas.style.margin = '0';
    canvas.style.padding = '0';
    canvas.style.zIndex = '10';

    // Ensure body has no margins
    document.body.style.margin = '0';
    document.body.style.padding = '0';
    document.body.style.overflow = 'hidden';

    // Create LocalGameManager instead of previous setup
    const localGameManager = new LocalGameManager(canvas, playerName, theme);

    // Override default game loop to add more complex logic if needed
    function customGameLoop() {
        // Additional game-wide logic can be added here
        localGameManager.gameLoop();

        // Optional: Add game-wide checks or global systems
        requestAnimationFrame(customGameLoop);
    }

    // Start the game
    localGameManager.start();
    customGameLoop();

    // Game Over Handling
    function handleGameOver() {
        localGameManager.end();

        // Create game over screen
        const gameOverScreen = document.createElement('div');
        gameOverScreen.style.position = 'fixed';
        gameOverScreen.style.top = '0';
        gameOverScreen.style.left = '0';
        gameOverScreen.style.width = '100%';
        gameOverScreen.style.height = '100%';
        gameOverScreen.style.background = 'rgba(0,0,0,0.8)';
        gameOverScreen.style.display = 'flex';
        gameOverScreen.style.justifyContent = 'center';
        gameOverScreen.style.alignItems = 'center';
        gameOverScreen.style.color = 'white';
        gameOverScreen.style.fontFamily = "'Orbitron', sans-serif";
        gameOverScreen.setAttribute('data-overlay', 'true');
        gameOverScreen.innerHTML = `
            <div style="text-align: center;">
                <h1>Game Over</h1>
                <p>Player: ${playerName}</p>
                <p>Waves Survived: ${localGameManager.waveManager.currentWave}</p>
                <button id="restart-btn">Restart</button>
            </div>
        `;
        document.body.appendChild(gameOverScreen);

        // Restart button functionality
        const restartBtn = gameOverScreen.querySelector('#restart-btn');
        restartBtn.addEventListener('click', () => {
            gameOverScreen.remove();

            // Dispatch event to restart game
            const restartEvent = new CustomEvent('gameRestart');
            document.dispatchEvent(restartEvent);
        });
    }

    // Add event listener for potential game over conditions
    document.addEventListener('gameOver', handleGameOver);
});

// Add event listener for game restart
document.addEventListener('gameRestart', () => {
    // Reload the page or reinitialize game
    window.location.reload();
});