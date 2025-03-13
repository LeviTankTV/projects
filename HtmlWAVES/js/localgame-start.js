document.addEventListener('DOMContentLoaded', () => {
    const startContainer = document.querySelector('.start-container');
    const startGameBtn = document.getElementById('start-game-btn');
    const playerNameInput = document.getElementById('player-name');
    const gameCanvas = document.getElementById('gameCanvas');

    // Create loading screen
    const loadingScreen = document.createElement('div');
    loadingScreen.style.position = 'fixed';
    loadingScreen.style.top = '0';
    loadingScreen.style.left = '0';
    loadingScreen.style.width = '100%';
    loadingScreen.style.height = '100%';
    loadingScreen.style.background = 'rgba(0,0,0,0.8)';
    loadingScreen.style.display = 'flex';
    loadingScreen.style.justifyContent = 'center';
    loadingScreen.style.alignItems = 'center';
    loadingScreen.style.zIndex = '10000';
    loadingScreen.style.color = 'white';
    loadingScreen.style.fontFamily = "'Orbitron', sans-serif";
    loadingScreen.innerHTML = `
        <div style="text-align: center;">
            <div class="loader" style="
                border: 5px solid #f3f3f3;
                border-top: 5px solid #3498db;
                border-radius: 50%;
                width: 50px;
                height: 50px;
                animation: spin 1s linear infinite;
                margin: 0 auto 20px;
            "></div>
            <h2>Loading Game...</h2>
        </div>
    `;
    document.body.appendChild(loadingScreen);
    loadingScreen.style.display = 'none';

    // Add style for loader animation
    const styleSheet = document.createElement("style")
    styleSheet.innerText = `
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
    `;
    document.head.appendChild(styleSheet);

    // Player name input validation
    playerNameInput.addEventListener('input', () => {
        const playerName = playerNameInput.value.trim();
        const startGameBtn = document.getElementById('start-game-btn');

        // Disable button if name is empty
        startGameBtn.disabled = playerName.length === 0;
    });

    // Start game button event listener
    startGameBtn.addEventListener('click', () => {
        const playerName = playerNameInput.value.trim();
        const gameMode = startContainer.getAttribute('data-game-mode');

        console.log('Start Game Clicked', { playerName, gameMode }); // Debug log

        if (playerName && gameMode === 'local') {
            // Hide all UI elements
            startContainer.style.display = 'none';
            document.querySelector('.theme-selector').style.display = 'none';
            document.getElementById('theme-pointer').style.display = 'none';

            // Show loading screen
            loadingScreen.style.display = 'flex';

            // Ensure canvas is visible
            gameCanvas.style.display = 'block';

            // Simulate loading
            setTimeout(() => {
                loadingScreen.style.display = 'none';

                // Trigger local game start with player name
                const localGameEvent = new CustomEvent('localGameStart', {
                    detail: { playerName: playerName }
                });
                document.dispatchEvent(localGameEvent);
            }, 1000); // 1 second loading screen
        }
    });
});