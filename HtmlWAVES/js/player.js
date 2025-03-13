import { PlayerMovement } from "./playerMovement.js";
import { PlayerSprite } from "./playersprite.js";
import { BasicPetal } from "./Basic.js";
 import { PlayerHotbar } from "./PlayerHotbar.js";
import { RockPetal } from "./Rock.js";
import { Stinger } from "./Stinger.js";
import { PlayerInventory } from "./PlayerInventory.js";
import { RicePetal } from "./Rice.js";
import { LightPetal } from "./Light.js";
import { LeafPetal } from "./Leaf.js";
import { HeavyPetal } from "./Heavy.js";
import { RosePetal } from "./RosePetal.js";
import { CornPetal } from "./Corn.js";
import { IrisPetal } from "./Iris.js";
import {CanvasUtils} from "./CanvasUtils.js";

export class Player {
    constructor(x, y, arena, playerConfig = {}) {
// Positioning and Arena
        this.x = x - 25;
        this.y = y - 25;
        this.arena = arena;

        // Dimensions
        this.width = playerConfig.width || 50;
        this.height = playerConfig.height || 50;
        this.radius = this.width / 2;

        // Movement Properties
        this.baseSpeed = playerConfig.baseSpeed || 5;
        this.maxSpeed = playerConfig.maxSpeed || 5;
        this.movement = new PlayerMovement(this);

        // Visual Properties
        this.color = playerConfig.color || 'rgba(0, 0, 255, 0.7)';
        this.name = playerConfig.name || 'Player';
        this.sprite = new PlayerSprite("res/playersprite.png");
        this.petalDistanceStates = {
            normal: 45,    // Default orbit distance
            expanded: 150, // Spacebar distance
            contracted: 30 // Ctrl distance
        };
        // Health and Defense
        this.maxHealth = playerConfig.maxHealth || 100;
        this.health = playerConfig.health || this.maxHealth;
        this.defense = playerConfig.defense || 0;

        // State Management
        this.isDead = false;
        this.invulnerabilityTime = 0;
        this.maxInvulnerabilityTime = 1000; // 1 second
        this.respawnTimer = 0;
        this.maxRespawnTime = 3000; // 3 seconds
        // Progression
        this.level = 1;
        this.experience = 0;
        this.experienceToNextLevel = this.calculateExpToNextLevel();

        // Score and Tracking
        this.score = playerConfig.score || 0;
        this.kills = 0;

        // Petal Management
        this.selectedPetals = [];
        this.petalOrbitRadius = 60;
        this.petalOrbitSpeed = this.petalOrbitRadius;

        // Bind methods
        this.update = this.updatePlayer.bind(this);
        this.draw = this.drawPlayer.bind(this);
        this.defaultPetalOrbitRadius = 40;  // Normal reach
        this.petalOrbitRadius = this.defaultPetalOrbitRadius;
        this.initDefaultPetals();

        Player.hotbar = Player.hotbar || new PlayerHotbar(this);
         this.hotbar = Player.hotbar;
         this.inventory = new PlayerInventory(this);
    }

// Petal Management Methods
    initDefaultPetals() {
        const defaultPetals = [

            new BasicPetal('rare'),
            new RockPetal('rare'),
            new RicePetal('rare'),
            new LightPetal('super'),
            new HeavyPetal('rare'),
            new RosePetal('mythic'),
            new LeafPetal('rare'),
            new CornPetal('super'),
            new Stinger('ultra'),
            new IrisPetal('legendary')

        ];

        defaultPetals.forEach(petal => this.addPetal(petal));
    }

    addPetal(petal) {
        petal.isPlayerControlled = true;
        this.selectedPetals.push(petal);

        if (typeof petal.onAddToPlayer === 'function') {
            petal.onAddToPlayer(this);
        }
    }



    removePetal(petal) {
        const index = this.selectedPetals.indexOf(petal);
        if (index > -1) {
            if (typeof petal.onRemoveFromPlayer === 'function') {
                petal.onRemoveFromPlayer(this);
            }
            this.selectedPetals.splice(index, 1);
        }
    }

    updatePetalPositions() { // Removed keyState
        const centerX = this.x + this.width / 2;
        const centerY = this.y + this.height / 2;

        let targetOrbitRadius = this.defaultPetalOrbitRadius;
        if (this.movement.keys.spacebar) { // Get keyState from PlayerMovement
            targetOrbitRadius = this.petalDistanceStates.expanded;
        } else if (this.movement.keys.ctrl) { // Get keyState from PlayerMovement
            targetOrbitRadius = this.petalDistanceStates.contracted;
        }

        // Improved Smoothing (Option 1: Simple Lerp)
        this.currentOrbitRadius = this.lerp(
            this.currentOrbitRadius || this.defaultPetalOrbitRadius,
            targetOrbitRadius,
            0.1 // Smoothing factor (adjust as needed)
        );


        const activeOrbitPetals = this.selectedPetals.slice(0, 10);
        const petalCount = activeOrbitPetals.length;
        const angleStep = (Math.PI * 2) / Math.max(1, petalCount);
        const baseRotation = Date.now() * 0.005;

        activeOrbitPetals.forEach((petal, index) => {
            const angle = baseRotation + (index * angleStep);
            petal.update(centerX, centerY, this.currentOrbitRadius, angle);
        });
    }

    applyKnockback(source, knockbackStrength = 10) {
        const angle = Math.atan2(
            this.y - source.y,
            this.x - source.x
        );

        this.x += Math.cos(angle) * knockbackStrength;
        this.y += Math.sin(angle) * knockbackStrength;

        // Ensure player stays within arena
        this.checkArenaBoundaries();
    }

// Improved Smoothing (Helper function)
    lerp(a, b, t) {
        return a + (b - a) * t;
    }


// Player Update Methods
    updatePlayer(deltaTime, keyState) {
        // Update movement

        const start = performance.now();
        this.updateMovement();

        // Update petal positions
        this.updatePetalPositions(keyState);

        // Manage invulnerability
        if (this.invulnerabilityTime > 0) {
            this.invulnerabilityTime = Math.max(0, this.invulnerabilityTime - deltaTime);
        }

        // Respawn handling
        if (this.isDead) {
            this.respawnTimer -= deltaTime;
            if (this.respawnTimer <= 0) {
                this.respawn();
            }
        }
        const duration = performance.now() - start;
        if (duration > 10) {
            console.warn(`Player update took ${duration}ms`);
        }
    }

    updateMovement() {
        this.movement.updateMovement();
        this.checkArenaBoundaries();
    }

    drawHealthBar(ctx) {
        const centerX = this.x + this.width / 2;
        const centerY = this.y + this.height / 2;

        const barWidth = this.width * 1.2;  // Slightly wider than player
        const barHeight = 8;  // Slightly thicker
        const cornerRadius = 4;  // Rounded corners

        const x = centerX - barWidth / 2;
        const y = centerY + this.radius + 10;  // Position below player

        // Background bar (dark transparent)
        ctx.save();
        ctx.beginPath();
        CanvasUtils.roundedRect(ctx, x, y, barWidth, barHeight, cornerRadius);
        ctx.fillStyle = 'rgba(0, 0, 0, 0.4)';
        ctx.fill();

        // Health bar
        const healthPercentage = this.health / this.maxHealth;
        const currentHealthWidth = barWidth * healthPercentage;

        // Health gradient (red to green)
        const healthGradient = ctx.createLinearGradient(x, y, x + barWidth, y + barHeight);
        healthGradient.addColorStop(0, 'rgba(255, 0, 0, 0.8)');   // Red
        healthGradient.addColorStop(0.5, 'rgba(255, 165, 0, 0.8)');  // Orange
        healthGradient.addColorStop(1, 'rgba(0, 255, 0, 0.9)');   // Green

        // Draw health progress
        ctx.beginPath();
        CanvasUtils.roundedRect(ctx, x, y, barWidth, barHeight, cornerRadius);
        ctx.fillStyle = healthGradient;
        ctx.fill();

        // Subtle shine effect on health bar
        ctx.globalAlpha = 0.3;
        ctx.beginPath();
        CanvasUtils.roundedRect(ctx, x, y, barWidth, barHeight/2, cornerRadius);
        ctx.fillStyle = 'white';
        ctx.fill();
        ctx.globalAlpha = 1;

        // Health text with advanced styling
        ctx.save();

        // Text shadow for depth
        ctx.shadowColor = 'rgba(0,0,0,0.5)';
        ctx.shadowBlur = 10;
        ctx.shadowOffsetX = 2;
        ctx.shadowOffsetY = 2;

        // Gradient for health text
        const healthTextGradient = ctx.createLinearGradient(
            centerX - 50,
            y + barHeight + 20,
            centerX + 50,
            y + barHeight + 40
        );
        healthTextGradient.addColorStop(0, '#FFFFFF');
        healthTextGradient.addColorStop(0.5, '#F0F0F0');
        healthTextGradient.addColorStop(1, '#FFFFFF');

        // Health text styling
        ctx.font = 'bold 16px "Orbitron", Arial, sans-serif';
        ctx.textAlign = 'center';

        // Outline effect
        ctx.lineWidth = 3;
        ctx.strokeStyle = 'rgba(0,0,0,0.3)';
        ctx.strokeText(
            `${Math.round(this.health)}/${Math.round(this.maxHealth)}`,
            centerX,
            y + barHeight + 25
        );

        // Gradient fill
        ctx.fillStyle = healthTextGradient;
        ctx.fillText(
            `${Math.round(this.health)}/${Math.round(this.maxHealth)}`,
            centerX,
            y + barHeight + 25
        );

        // Subtle shine effect
        ctx.globalAlpha = 0.3;
        ctx.fillStyle = 'rgba(255,255,255,0.6)';
        ctx.fillText(
            `${Math.round(this.health)}/${Math.round(this.maxHealth)}`,
            centerX,
            y + barHeight + 24
        );

        ctx.restore();
        ctx.restore();
    }

// Helper method for rounded rectangles (add to Player class)
// Enhanced name drawing method
    drawPlayerName(ctx) {
        if (this.name) {
            const centerX = this.x + this.width / 2;
            const centerY = this.y + this.height / 2;

            CanvasUtils.drawNameWithBackground(ctx, {
                name: this.name,
                x: centerX,
                y: centerY - this.radius,
                width: 120,
                height: 20
            });
        }
    }

// Modify drawPlayer method to include name drawing
    drawPlayer(ctx) {
        const centerX = this.x + this.width / 2;
        const centerY = this.y + this.height / 2;

        // Draw player sprite
        this.sprite.draw(ctx, centerX, centerY, this.radius);

        // Draw petals
        this.selectedPetals.forEach(petal => {
            petal.draw(ctx);
        });

        // Draw health bar
        this.drawHealthBar(ctx);

        // Draw player name
        this.drawPlayerName(ctx);
    }

// Damage and Health Methods
    takeDamage(amount, source = null) {
        if (this.invulnerabilityTime > 0) return 0;

        const actualDamage = Math.max(0, amount - this.defense);
        this.health = Math.max(0, this.health - actualDamage);
        this.invulnerabilityTime = this.maxInvulnerabilityTime;

        if (this.health <= 0) {
            this.die(source);
        }

        return actualDamage;
    }


    die(source = null) {
        if (this.isDead) return;

        this.isDead = true;
        this.health = 0;
        this.respawnTimer = this.maxRespawnTime;

        const experienceLoss = Math.floor(this.experience * 0.1);
        const scoreLoss = Math.floor(this.score * 0.05);

        this.experience = Math.max(0, this.experience - experienceLoss);
        this.score = Math.max(0, this.score - scoreLoss);

        console.log(`Player died${source ? ` to ${source}` : ''}`);
    }

    respawn() {
        this.health = this.maxHealth;
        this.isDead = false;
        this.invulnerabilityTime = this.maxInvulnerabilityTime;
        this.x = this.arena.x - this.width / 2;
        this.y = this.arena.y - this.height / 2;
    }

    heal(amount) {
        this.health = Math.min(this.maxHealth, this.health + amount);
        return this.health;
    }

// Progression Methods
    addExperience(amount) {
        this.experience += amount;
        this.createExperiencePopup(amount);
        while (this.experience >= this.experienceToNextLevel) {
            this.levelUp();
        }
    }
    levelUp() {
        this.experience -= this.experienceToNextLevel;
        this.level++;
        this.maxHealth += 10;
        this.health = this.maxHealth;
        this.defense += 1;
        this.baseSpeed *= 1.1;
        this.experienceToNextLevel = this.calculateExpToNextLevel();

        console.log(`Level Up! New Level: ${this.level}`);
    }

    calculateExpToNextLevel() {
        return 100 * Math.pow(1.5, this.level - 1);
    }
    createExperiencePopup(amount) {
        // Create a floating text effect
        const popup = document.createElement('div');
        popup.textContent = `+${amount} EXP`;
        popup.style.position = 'absolute';
        popup.style.left = `${this.x}px`;
        popup.style.top = `${this.y}px`;
        popup.style.color = 'gold';
        popup.style.fontSize = '16px';
        popup.style.animation = 'expPopup 1s forwards';

        document.body.appendChild(popup);

        // Remove popup after animation
        setTimeout(() => {
            document.body.removeChild(popup);
        }, 1000);
    }


// Score and Tracking Methods
    addScore(points) {
        this.score += points;
        return this.score;
    }

    addKill() {
        this.kills++;
    }

// Arena Boundary Methods
    checkArenaBoundaries() {
        const centerX = this.x + this.width / 2;
        const centerY = this.y + this.height / 2;

        const dx = centerX - this.arena.x;
        const dy = centerY - this.arena.y;
        const distanceFromCenter = Math.sqrt(dx * dx + dy * dy);

        if (distanceFromCenter > this.arena.radius - this.radius) {
            const angle = Math.atan2(dy, dx);
            this.x = this.arena.x + Math.cos(angle) * (this.arena.radius - this.radius) - this.width / 2;
            this.y = this.arena.y + Math.sin(angle) * (this.arena.radius - this.radius) - this.height / 2;
        }
    }

// Utility Methods
    getAdjustedSpeed() {
        // Consider factors like health, powerups, etc.
        const healthFactor = this.health / this.maxHealth;
        return Math.min(this.baseSpeed * healthFactor, this.maxSpeed);
    }

    reset() {
        this.health = this.maxHealth;
        this.score = 0;
        this.kills = 0;
        this.level = 1;
        this.experience = 0;
        this.x = this.arena.x - this.width / 2;
        this.y = this.arena.y - this.height / 2;
        this.isDead = false;
        this.invulnerabilityTime = 0;

        // Reset petals
        this.selectedPetals = [];
        this.initDefaultPetals();
      //  Player.hotbar = new PlayerHotbar(this);
       // this.hotbar = Player.hotbar;
    }

// Serialization Methods
    serialize() {
        return {
            x: this.x,
            y: this.y,
            health: this.health,
            maxHealth: this.maxHealth,
            score: this.score,
            level: this.level,
            experience: this.experience,
            name: this.name,
            petals: this.selectedPetals.map(petal => petal.serialize())
        };
    }

// Power-up and Buff Methods
    applyTemporaryBuff(buffType, duration, magnitude) {
        switch(buffType) {
            case 'speed':
                const originalSpeed = this.baseSpeed;
                this.baseSpeed *= magnitude;
                setTimeout(() => {
                    this.baseSpeed = originalSpeed;
                }, duration);
                break;
            case 'defense':
                const originalDefense = this.defense;
                this.defense += magnitude;
                setTimeout(() => {
                    this.defense = originalDefense;
                }, duration);
                break;
            case 'healing':
                this.heal(magnitude);
                break;
        }
    }

// Debug and Development Methods
    debugInfo() {
        return {
            position: { x: this.x, y: this.y },
            health: `${this.health}/${this.maxHealth}`,
            speed: this.baseSpeed,
            level: this.level,
            experience: `${this.experience}/${this.experienceToNextLevel}`,
            petalCount: this.selectedPetals.length
        };
    }
}
