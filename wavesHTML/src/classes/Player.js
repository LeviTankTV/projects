export class Player {
    constructor(x, y, radius, speed) {
        // Existing properties
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speed = speed;

        // Health and damage properties
        this.maxHP = 100;
        this.currentHP = this.maxHP;
        this.bodyDamage = 20;

        // Damage and invincibility mechanics
        this.isInvincible = false;
        this.invincibilityDuration = 100; // 1 second of invincibility after taking damage
        this.lastDamageTime = 0;

        // Damage visual feedback
        this.damageFlashDuration = 200; // 200ms flash when hit
        this.lastDamageFlashTime = 0;
        this.isDamageFlashing = false;

        // Skin loading
        this.skin = new Image();
        this.skin.src = 'res/fl.png';
        this.skinLoaded = false;

        this.skin.onload = () => {
            this.skinLoaded = true;
        };

        this.skin.onerror = () => {
            console.error('Failed to load player skin');
        };

        // Petal management
        this.selectedPetals = [];
        this.petalOrbitRadius = 60;
        this.petalOrbitSpeed = 0.003;
        this.deadImage = new Image();
        this.deadImage.src = '/res/deadplayer.png'; // Path to the dead player image
        this.isDead = false; // Flag to check if player is dead

        // Properties to track original state for effects
        this.originalSkin = this.skin.src;
        this.originalPetalOrbitRadius = this.petalOrbitRadius;
        this.level = 1;
        this.experience = 0;
        this.experienceToNextLevel = 100;
    }

    addExperience(amount) {
        this.experience += amount;

        // Check for level up
        while (this.experience >= this.experienceToNextLevel) {
            this.levelUp();
        }
    }

    // Level up method
    levelUp() {
        this.level++;
        this.experience -= this.experienceToNextLevel;

        // Increase XP requirement for next level (you can adjust this formula)
        this.experienceToNextLevel = Math.floor(this.experienceToNextLevel * 1.5);

        // Optional: Increase player stats on level up
        this.maxHP += 10;
        this.currentHP = this.maxHP;

        console.log(`Level Up! Now at level ${this.level}`);
    }

    takeDamage(amount, game) {
        // Check if player is currently invincible
        const currentTime = Date.now();
        if (this.isInvincible && currentTime - this.lastDamageTime < this.invincibilityDuration) {
            return; // Don't take damage if invincible
        }

        // Reduce HP
        this.currentHP -= amount;

        // Set invincibility and damage flash
        this.isInvincible = true;
        this.isDamageFlashing = true;
        this.lastDamageTime = currentTime;
        this.lastDamageFlashTime = currentTime;

        // Optional: Add sound effect for damage
        // this.playDamageSound();

        // Check for death
        if (this.currentHP <= 0) {
            this.currentHP = 0;
            this.handleDeath(game); // Pass the game instance
        }

        console.log(`Player took ${amount} damage. Current HP: ${this.currentHP}`);
    }

    handleDeath(game) {
        this.isDead = true; // Set the player as dead
        console.log('Player has died!');
        game.gameOver = true; // Notify the game that the player has died
    }

    update(keys, worldBounds) {
        const currentTime = Date.now();

        // Existing movement code
        if (keys['w']) this.y -= this.speed;
        if (keys['s']) this.y += this.speed;
        if (keys['a']) this.x -= this.speed;
        if (keys['d']) this.x += this.speed;

        // World boundary check
        this.x = Math.max(this.radius, Math.min(this.x, worldBounds.width - this.radius));
        this.y = Math.max(this.radius, Math.min(this.y, worldBounds.height - this.radius));

        // Update petal positions
        this.updatePetalPositions();

        // Manage invincibility
        if (this.isInvincible && currentTime - this.lastDamageTime >= this.invincibilityDuration) {
            this.isInvincible = false;
        }

        // Manage damage flash
        if (this.isDamageFlashing && currentTime - this.lastDamageFlashTime >= this.damageFlashDuration) {
            this.isDamageFlashing = false;
        }
    }

    updatePetalPositions() {
        // Filter petals to only include those in the first row of the hotbar
        const activeOrbitPetals = this.selectedPetals.filter((petal, index) => index < 10);

        // Update positions of active petals to orbit around player
        activeOrbitPetals.forEach((petal, index) => {
            // Calculate angle for each petal, evenly distributed
            const angleStep = (Math.PI * 2) / Math.max(1, activeOrbitPetals.length);
            const baseAngle = Date.now() * this.petalOrbitSpeed;
            const angle = baseAngle + (index * angleStep);

            // Calculate orbital position
            petal.x = this.x + Math.cos(angle) * this.petalOrbitRadius;
            petal.y = this.y + Math.sin(angle) * this.petalOrbitRadius;
        });
    }

    draw(ctx, cameraX, cameraY) {
        if (this.isDead) {
            ctx.drawImage(
                this.deadImage,
                this.x - cameraX - this.radius,
                this.y - cameraY - this.radius,
                this.radius * 2,
                this.radius * 2
            );
            return; // Skip the rest of the draw method
        }

        ctx.save();

        // Damage flash effect
        if (this.isDamageFlashing) {
            ctx.globalAlpha = 0.5; // Reduce opacity
            ctx.fillStyle = 'red'; // Flash red when hit
        }

        // Draw player skin (existing drawing code)
        if (this.skinLoaded) {
            ctx.beginPath();
            ctx.arc(
                this.x - cameraX,
                this.y - cameraY,
                this.radius,
                0,
                Math.PI * 2
            );
            ctx.clip();

            ctx.drawImage(
                this.skin,
                this.x - cameraX - this.radius,
                this.y - cameraY - this.radius,
                this.radius * 2,
                this.radius * 2
            );
        } else {
            // Fallback to colored circle
            ctx.beginPath();
            ctx.arc(
                this.x - cameraX,
                this.y - cameraY,
                this.radius,
                0,
                Math.PI * 2
            );
            ctx.fill();
        }

        ctx.restore();

        // Draw health bar
        this.drawHealthBar(ctx, cameraX, cameraY);
    }

    drawHealthBar(ctx, cameraX, cameraY) {
        const barWidth = this.radius * 2;
        const barHeight = 5;
        const x = this.x - cameraX - this.radius;
        const y = this.y - cameraY - this.radius - 10;

        // Background of HP bar (dark red)
        ctx.fillStyle = '#8B0000';
        ctx.fillRect(x, y, barWidth, barHeight);

        // Current HP (bright green)
        ctx.fillStyle = '#00FF00';
        ctx.fillRect(x, y, barWidth * (this.currentHP / this.maxHP), barHeight);
    }


    // Method to add or remove petals
    addPetal(petal) {
        petal.isPlayerControlled = true; // Ensure petals are marked as player-controlled
        this.selectedPetals.push(petal);

        // Apply petal effects
        if (typeof petal.onAddToPlayer === 'function') {
            petal.onAddToPlayer(this);
        }
    }

    removePetal(petal) {
        const index = this.selectedPetals.indexOf(petal);
        if (index > -1) {
            // Remove petal effects
            if (typeof petal.onRemoveFromPlayer === 'function') {
                petal.onRemoveFromPlayer(this);
            }
            this.selectedPetals.splice(index, 1);
        }
    }

    // Clear all selected petals
    clearPetals() {
        this.selectedPetals.forEach(petal => {
            if (typeof petal.onRemoveFromPlayer === 'function') {
                petal.onRemoveFromPlayer(this);
            }
        });
        this.selectedPetals = [];
    }
}
