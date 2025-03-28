export class PlayerMovement {
    constructor(player) {
        this.player = player;

        // Comprehensive key tracking
        this.keys = {
            w: false, a: false, s: false, d: false,
            ArrowUp: false, ArrowLeft: false, ArrowDown: false, ArrowRight: false,
            ц: false, ф: false, ы: false, в: false,
            spacebar: false,  // Add spacebar tracking
            ctrl: false       // Add ctrl tracking
        };
        // Smooth movement properties
        this.velocity = { x: 0, y: 0 };
        this.acceleration = 0.5;  // Acceleration rate
        this.friction = 0.9;      // Friction to slow down
        this.maxSpeed = 5;        // Maximum speed cap

        // Movement state tracking
        this.movementStates = {
            up: false,
            down: false,
            left: false,
            right: false
        };

        // Key mapping for different keyboard layouts
        this.keyMap = {
            up: ['w', 'ц', 'ArrowUp'],
            down: ['s', 'ы', 'ArrowDown'],
            left: ['a', 'ф', 'ArrowLeft'],
            right: ['d', 'в', 'ArrowRight']
        };

        this.keyMap.spacebar = [' '];
        this.keyMap.ctrl = ['Control'];

        // Setup event listeners
        this.setupKeyListeners();
    }

    setupKeyListeners() {
        document.addEventListener('keydown', (e) => {
            this.handleKeyEvent(e.key, true);
        });

        document.addEventListener('keyup', (e) => {
            this.handleKeyEvent(e.key, false);
        });
    }

    handleKeyEvent(key, isPressed) {
        // Existing direction key handling
        Object.keys(this.keyMap).forEach(direction => {
            if (this.keyMap[direction].includes(key.toLowerCase())) {
                this.keys[key.toLowerCase()] = isPressed;

                // For directional keys, update movement states
                if (['up', 'down', 'left', 'right'].includes(direction)) {
                    this.movementStates[direction] = isPressed;
                }
            }
        });

        // Special key handling
        switch(key.toLowerCase()) {
            case ' ':
                this.keys.spacebar = isPressed;
                break;
            case 'control':
                this.keys.ctrl = isPressed;
                break;
        }
    }

    isSpacebarHeld() {
        return this.keys.spacebar;
    }

    isCtrlHeld() {
        return this.keys.ctrl;
    }

    updateMovement() {
        // Acceleration and direction calculation
        const accelX = this.calculateAcceleration('horizontal');
        const accelY = this.calculateAcceleration('vertical');

        // Update velocity with acceleration
        this.velocity.x = this.clamp(
            this.velocity.x + accelX,
            -this.maxSpeed,
            this.maxSpeed
        );
        this.velocity.y = this.clamp(
            this.velocity.y + accelY,
            -this.maxSpeed,
            this.maxSpeed
        );

        // Apply friction for smooth deceleration
        this.velocity.x *= this.friction;
        this.velocity.y *= this.friction;

        // Calculate potential new positions
        const newX = this.player.x + this.velocity.x;
        const newY = this.player.y + this.velocity.y;

        // Update player position with arena boundary checking
        this.updatePlayerPosition(newX, newY);
    }

    calculateAcceleration(axis) {
        let acceleration = 0;

        if (axis === 'horizontal') {
            if (this.movementStates.left) acceleration -= this.acceleration;
            if (this.movementStates.right) acceleration += this.acceleration;
        } else if (axis === 'vertical') {
            if (this.movementStates.up) acceleration -= this.acceleration;
            if (this.movementStates.down) acceleration += this.acceleration;
        }

        // Diagonal movement speed reduction
        return this.isDiagonalMovement() ? acceleration * 0.7071 : acceleration;
    }

    updatePlayerPosition(newX, newY) {
        const centerX = newX + this.player.width / 2;
        const centerY = newY + this.player.height / 2;

        // Calculate distance from arena center
        const dx = centerX - this.player.arena.x;
        const dy = centerY - this.player.arena.y;
        const distanceFromCenter = Math.sqrt(dx * dx + dy * dy);

        // Check if new position is within arena
        if (distanceFromCenter <= this.player.arena.radius - this.player.radius) {
            // Position is valid, update player coordinates
            this.player.x = newX;
            this.player.y = newY;
        } else {
            // If out of bounds, reset velocity
            this.velocity.x = 0;
            this.velocity.y = 0;
        }
    }

    // Utility methods
    isDiagonalMovement() {
        const verticalMovement = this.movementStates.up || this.movementStates.down;
        const horizontalMovement = this.movementStates.left || this.movementStates.right;
        return verticalMovement && horizontalMovement;
    }

    clamp(value, min, max) {
        return Math.max(min, Math.min(value, max));
    }

    resetMovement() {
        this.velocity = { x: 0, y: 0 };
        Object.keys(this.movementStates).forEach(direction => {
            this.movementStates[direction] = false;
        });
        Object.keys(this.keys).forEach(key => {
            this.keys[key] = false;
        });
    }
}

