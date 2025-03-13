class InputManager {
    constructor() {
        this.keys = {};
        this.movement = { x: 0, y: 0 };
        this.spacePressed = false;
        this.rightMousePressed = false;

        this.init();
    }

    init() {
        window.addEventListener('keydown', (event) => {
            this.keys[event.key] = true;
            if (event.key === ' ') {
                this.spacePressed = true; // Spacebar held down
            }
        });

        window.addEventListener('keyup', (event) => {
            this.keys[event.key] = false;
            if (event.key === ' ') {
                this.spacePressed = false; // Spacebar released
            }
        });

        window.addEventListener('mousedown', (event) => {
            if (event.button === 2) { // Right mouse button
                this.rightMousePressed = true;
            }
        });

        window.addEventListener('mouseup', (event) => {
            if (event.button === 2) { // Right mouse button
                this.rightMousePressed = false;
            }
        });

        window.addEventListener('contextmenu', (event) => {
            event.preventDefault(); // Prevent the context menu from appearing
        });

        // Add event listeners for chat input focus
        const chatInput = document.getElementById('chatInput');
        chatInput.addEventListener('focus', () => {
            this.isChatFocused = true; // Chat input is focused
        });
        chatInput.addEventListener('blur', () => {
            this.isChatFocused = false; // Chat input is not focused
        });
    }

    update() {
        this.movement.x = 0;
        this.movement.y = 0;

        // Ignore movement input if chat input is focused
        if (this.isChatFocused) {
            return this.movement; // Return zero movement
        }

        // WASD controls
        if (this.keys['w'] || this.keys['ArrowUp'] || this.keys['ц']) {
            this.movement.y = -1;
        }
        if (this.keys['s'] || this.keys['ArrowDown'] || this.keys['ы']) {
            this.movement.y = 1;
        }
        if (this.keys['a'] || this.keys['ArrowLeft'] || this.keys['ф']) {
            this.movement.x = -1;
        }
        if (this.keys['d'] || this.keys['ArrowRight'] || this.keys['в']) {
            this.movement.x = 1;
        }

        // Normalize diagonal movement
        if (this.movement.x !== 0 && this.movement.y !== 0) {
            this.movement.x *= Math.SQRT1_2;
            this.movement.y *= Math.SQRT1_2;
        }

        const potentialX = currentPlayer.x + this.movement.x * currentPlayer.speed;
        const potentialY = currentPlayer.y + this.movement.y * currentPlayer.speed;

        // Apply boundary checks
        if (potentialX < 0) {
            this.movement.x = -currentPlayer.x; // Move to the left boundary
        } else if (potentialX > 20000) {
            this.movement.x = 20000 - currentPlayer.x; // Move to the right boundary
        }

        if (potentialY < 0) {
            this.movement.y = -currentPlayer.y; // Move to the top boundary
        } else if (potentialY > 2000) {
            this.movement.y = 2000 - currentPlayer.y; // Move to the bottom boundary
        }

        // Check for walls (you might want to adjust this logic)
        for (const walls of zoneRenderer.walls) {
            if (potentialX > walls.bounds.x[0] && potentialX < walls.bounds.x[1] - 50) {
                this.movement.x = -1;
            } else if (potentialX > walls.bounds.x[0] + 50 && potentialX < walls.bounds.x[1]) {
                this.movement.x = 1;
            }
        }

        return this.movement;
    }

    updateEmotion(currentPlayer) {
        if (this.isChatFocused) {
            return 'neutral'; // Return zero movement
        }
        if (this.spacePressed) {
            currentPlayer.setEmotion('angry');
        } else if (this.rightMousePressed) {
            currentPlayer.setEmotion('sad');
        } else {
            currentPlayer.setEmotion('neutral');
        }
    }
}
