document.addEventListener('DOMContentLoaded', () => {
    const body = document.body;
    const themePointer = document.getElementById('theme-pointer');
    const startContainer = document.querySelector('.start-container');
    const gameCanvas = document.getElementById('gameCanvas');
    const buttonStyleSheet = document.createElement('style');
    buttonStyleSheet.innerHTML = `
    .game-mode-btn {
        position: relative;
        transition: all 0.3s ease;
    }
    .game-mode-btn.selected-mode {
        transform: scale(1.05);
        box-shadow: 0 0 20px rgba(255, 255, 255, 0.5) !important;
        border: 2px solid white !important;
    }
    .game-mode-btn.selected-mode::after {
        content: '✓';
        position: absolute;
        top: 5px;
        right: 5px;
        background: rgba(0,255,0,0.7);
        color: white;
        border-radius: 50%;
        width: 25px;
        height: 25px;
        display: flex;
        justify-content: center;
        align-items: center;
        font-weight: bold;
    }
`;
    document.head.appendChild(buttonStyleSheet);
    const themes = [
        {
            name: 'Garden',
            bgColor: 'var(--bg-color-1)',
            titleGradient: 'linear-gradient(45deg, #00ff00, #228b22)',
            icon: 'res/gardenIcon.png',
            fullScreenBackground: 'linear-gradient(135deg, #2ecc71, #27ae60)',
            buttonColor: 'linear-gradient(135deg, #2ecc71, #27ae60)'
        },
        {
            name: 'Desert',
            bgColor: 'var(--bg-color-2)',
            titleGradient: 'linear-gradient(45deg, #ffd700, #ff8c00)',
            icon: 'res/desertIcon.png',
            fullScreenBackground: 'linear-gradient(135deg, #f39c12, #d35400)',
            buttonColor: 'linear-gradient(135deg, #f39c12, #d35400)'
        },
        {
            name: 'Ocean',
            bgColor: 'var(--bg-color-3)',
            titleGradient: 'linear-gradient(45deg, #1e90ff, #4169e1)',
            icon: 'res/oceanIcon.png',
            fullScreenBackground: 'linear-gradient(135deg, #3498db, #2980b9)',
            buttonColor: 'linear-gradient(135deg, #3498db, #2980b9)'
        }
    ];

    let currentThemeIndex = 0; // Default to Garden theme

    // Transition Overlay
    const transitionOverlay = document.createElement('div');
    transitionOverlay.style.position = 'fixed';
    transitionOverlay.style.top = '0';
    transitionOverlay.style.left = '0';
    transitionOverlay.style.width = '100vw';
    transitionOverlay.style.height = '100vh';
    transitionOverlay.style.background = themes[currentThemeIndex].fullScreenBackground;
    transitionOverlay.style.zIndex = '9999';
    transitionOverlay.style.opacity = '0';
    transitionOverlay.style.transition = 'opacity 1s ease';
    transitionOverlay.style.pointerEvents = 'none';
    document.body.appendChild(transitionOverlay);

    // Biome Bar
    const biomeBar = document.createElement('div');
    biomeBar.style.position = 'fixed';
    biomeBar.style.top = '20px';
    biomeBar.style.left = '50%';
    biomeBar.style.transform = 'translateX(-50%)';
    biomeBar.style.width = '80%';
    biomeBar.style.background = 'linear-gradient(135deg, #f39c12, #d35400)';
    biomeBar.style.borderRadius = '25px';
    biomeBar.style.padding = '10px';
    biomeBar.style.textAlign = 'center';
    biomeBar.style.color = 'white';
    biomeBar.style.fontFamily = "'Orbitron', sans-serif";
    biomeBar.style.fontSize = '1.5rem';
    biomeBar.style.boxShadow = '0 0 15px rgba(255, 165, 0, 0.3)';
    document.body.appendChild(biomeBar);

    // Game Mode Buttons
    const gameModeContainer = document.createElement('div');
    gameModeContainer.style.position = 'fixed';
    gameModeContainer.style.bottom = '50px';
    gameModeContainer.style.left = '50%';
    gameModeContainer.style.transform = 'translateX(-50%)';
    gameModeContainer.style.display = 'flex';
    gameModeContainer.style.gap = '20px';

    function createGameModeButton(text, theme) {
        const button = document.createElement('button');
        button.textContent = text;
        button.style.background = theme.buttonColor;
        button.style.color = 'white';
        button.style.border = 'none';
        button.style.padding = '15px 30px';
        button.style.borderRadius = '10px';
        button.style.cursor = 'pointer';
        button.style.transition = 'all 0.3s ease';
        button.style.fontFamily = "'Orbitron', sans-serif";
        button.style.boxShadow = '0 0 15px rgba(255, 165, 0, 0.3)';

        button.addEventListener('mouseenter', () => {
            if (!button.classList.contains('selected-mode')) {
                button.style.transform = 'scale(1.05)';
                button.style.boxShadow = '0 0 25px rgba(255, 165, 0, 0.5)';
            }
        });

        button.addEventListener('mouseleave', () => {
            if (!button.classList.contains('selected-mode')) {
                button.style.transform = 'scale(1)';
                button.style.boxShadow = '0 0 15px rgba(255, 165, 0, 0.3)';
            }
        });

        return button;
    }

    // Theme Selection Screen
    const themeScreen = document.createElement('div');
    themeScreen.style.position = 'fixed';
    themeScreen.style.top = '0';
    themeScreen.style.left = '0';
    themeScreen.style.width = '100vw';
    themeScreen.style.height = '100vh';
    themeScreen.style.background = 'rgba(0,0,0,0.8)';
    themeScreen.style.display = 'flex';
    themeScreen.style.justifyContent = 'center';
    themeScreen.style.alignItems = 'center';
    themeScreen.style.zIndex = '10000';
    themeScreen.style.opacity = '0';
    themeScreen.style.pointerEvents = 'none';
    themeScreen.style.transition = 'opacity 0.5s ease';
    document.body.appendChild(themeScreen);

    function createThemeOptions() {
        themeScreen.innerHTML = '';
        const themeOptionsContainer = document.createElement('div');
        themeOptionsContainer.style.display = 'flex';
        themeOptionsContainer.style.gap = '30px';

        themes.forEach((theme, index) => {
            const themeOption = document.createElement('div');
            themeOption.style.width = '150px';
            themeOption.style.height = '150px';
            themeOption.style.borderRadius = '50%';
            themeOption.style.background = theme.fullScreenBackground;
            themeOption.style.display = 'flex';
            themeOption.style.justifyContent = 'center';
            themeOption.style.alignItems = 'center';
            themeOption.style.cursor = 'pointer';
            themeOption.style.transition = 'transform 0.3s ease, box-shadow 0.3s ease';

            themeOption.addEventListener('mouseenter', () => {
                themeOption.style.transform = 'scale(1.1)';
                themeOption.style.boxShadow = '0 0 20px rgba(255,255,255,0.6)';
            });

            themeOption.addEventListener('mouseleave', () => {
                themeOption.style.transform = 'scale(1)';
                themeOption.style.boxShadow = 'none';
            });

            const themeIcon = document.createElement('img');
            themeIcon.src = theme.icon;
            themeIcon.style.width = '100px';
            themeIcon.style.height = '100px';
            themeIcon.style.objectFit = 'contain';
            themeOption.appendChild(themeIcon);
            themeOption.addEventListener('click', () => {
                // Trigger full-screen transition
                transitionOverlay.style.opacity = '1';

                setTimeout(() => {
                    // Update current theme index
                    currentThemeIndex = index;

                    // Change entire background
                    body.style.background = theme.fullScreenBackground;
                    gameCanvas.style.background = theme.fullScreenBackground;
                    startContainer.style.background = 'rgba(255, 255, 255, 0.1)';
                    startContainer.style.backdropFilter = 'blur(10px)';

                    // Update title gradient
                    const animatedTitle = document.querySelector('.animated-title');
                    animatedTitle.style.background = theme.titleGradient;
                    animatedTitle.style.webkitBackgroundClip = 'text';
                    animatedTitle.style.webkitTextFillColor = 'transparent';

                    // Update biome bar
                    biomeBar.innerHTML = theme.name;
                    biomeBar.style.background = theme.buttonColor;

                    // Update game mode buttons
                    const buttons = gameModeContainer.querySelectorAll('button');
                    buttons.forEach(button => {
                        button.style.background = theme.buttonColor;
                    });

                    // Hide theme screen
                    themeScreen.style.opacity = '0';
                    themeScreen.style.pointerEvents = 'none';

                    // Remove transition overlay
                    setTimeout(() => {
                        transitionOverlay.style.opacity = '0';
                    }, 500);
                }, 500);
            });

            themeOptionsContainer.appendChild(themeOption);
        });

        themeScreen.appendChild(themeOptionsContainer);
    }

    // Create game mode buttons
    const randomRoomBtn = createGameModeButton('Random Room', themes[currentThemeIndex]);
    const createRoomBtn = createGameModeButton('Create Room', themes[currentThemeIndex]);
    const joinRoomBtn = createGameModeButton('Join Room', themes[currentThemeIndex]);
    const localGameBtn = createGameModeButton('Local Game', themes[currentThemeIndex]);

    // Add buttons to container
    gameModeContainer.appendChild(randomRoomBtn);
    gameModeContainer.appendChild(createRoomBtn);
    gameModeContainer.appendChild(joinRoomBtn);
    gameModeContainer.appendChild(localGameBtn);
    document.body.appendChild(gameModeContainer);

    // Theme pointer event listener
    themePointer.addEventListener('click', () => {
        createThemeOptions();
        themeScreen.style.opacity = '1';
        themeScreen.style.pointerEvents = 'all';
    });

    localGameBtn.addEventListener('click', () => {
        // Clear player name input
        const playerNameInput = document.getElementById('player-name');
        if (playerNameInput) {
            playerNameInput.value = ''; // Clear the input field
        }

        // Add a data attribute to indicate local game mode is selected
        startContainer.setAttribute('data-game-mode', 'local');

        // Ensure start container is visible
        startContainer.style.display = 'flex';
        startContainer.style.opacity = '1';
        startContainer.style.visibility = 'visible';

        // Visually indicate the Local Game button is selected
        localGameBtn.classList.add('selected-mode');

        // Disable other game mode buttons
        randomRoomBtn.classList.remove('selected-mode');
        createRoomBtn.classList.remove('selected-mode');
        joinRoomBtn.classList.remove('selected-mode');

        // Disable start game button initially
        const startGameBtn = document.getElementById('start-game-btn');
        startGameBtn.disabled = true;

        const localGameEvent = new CustomEvent('localGameStart', {
            detail: {
                playerName: playerNameInput.value.trim(),
                theme: themes[currentThemeIndex].name.toLowerCase() // 'garden', 'desert', or 'ocean'
            }
        });

        // Optional: Add a visual reset to the input field
        playerNameInput.style.borderColor = ''; // Reset any error highlighting
        playerNameInput.placeholder = 'Local Game Mode'; // Reset placeholder if needed
    });


    // Initial setup
    biomeBar.innerHTML = themes[currentThemeIndex].name;
    body.style.background = themes[currentThemeIndex].fullScreenBackground;
    gameCanvas.style.background = themes[currentThemeIndex].fullScreenBackground;
    startContainer.style.background = 'rgba(255, 255, 255, 0.1)';
    startContainer.style.backdropFilter = 'blur(10px)';
});
