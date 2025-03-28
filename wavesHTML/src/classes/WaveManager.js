const WAVE_CONFIG = [];

// Function to generate wave configurations
function generateWaveConfig() {
    for (let wave = 1; wave <= 100; wave++) {
        let waveConfig = { wave: wave, common: 0, uncommon: 0, rare: 0, epic: 0, legendary: 0, mythic: 0, ultra: 0 };

        // Early waves (1-10): Primarily common and uncommon enemies
        if (wave <= 10) {
            waveConfig.common = Math.min(8, wave + 2);
            waveConfig.uncommon = Math.floor(wave/3);
        }
        // Waves 11-20: Introduce rare enemies
        else if (wave <= 20) {
            waveConfig.uncommon = 3;
            waveConfig.rare = Math.floor((wave - 10) / 2);
            waveConfig.epic = Math.floor((wave - 15) / 5);
        }
        // Waves 21-40: Increase epic and legendary enemy count
        else if (wave <= 40) {
            waveConfig.rare = 3;
            waveConfig.epic = Math.floor((wave - 20) / 2);
            waveConfig.legendary = Math.floor((wave - 35) / 5);
        }
        // Waves 41-60: More legendary and mythic enemies
        else if (wave <= 60) {
            waveConfig.rare = 2;
            waveConfig.epic = 4;
            waveConfig.legendary = Math.floor((wave - 40) / 2);
            waveConfig.mythic = Math.floor((wave - 55) / 5);
        }
        // Waves 61-99: Extremely difficult waves
        else if (wave < 100) {
            waveConfig.epic = 3;
            waveConfig.legendary = 3;
            waveConfig.mythic = Math.floor((wave - 60) / 3);
        }
        // Wave 100: Ultra Boss Wave
        else {
            waveConfig.epic = 5;
            waveConfig.legendary = 3;
            waveConfig.mythic = 2;
            waveConfig.ultra = 1; // Spawn the Ultra enemy
        }

        WAVE_CONFIG.push(waveConfig);
    }
}

// Call the function to generate the configuration
generateWaveConfig();
const SPAWN_DISTANCE = 200; // Minimum distance from the player to spawn enemies
import { Enemy } from "./Enemy.js";

export class WaveManager {
    constructor(game) {
        this.game = game;
        this.currentWave = 0;
        this.enemiesToSpawn = 0;
        this.enemiesSpawned = 0;
        this.currentWidth = 0; // Initialize current width for animation
        // Removed arena size growth factor and base arena size
    }

    // Modify startNextWave method
    startNextWave() {
        this.currentWave++;
        if (this.currentWave > WAVE_CONFIG.length) {
            console.log("All waves completed!");
            return; // No more waves
        }

        const waveConfig = WAVE_CONFIG[this.currentWave - 1];
        this.enemiesToSpawn = waveConfig.common + waveConfig.uncommon + waveConfig.rare + waveConfig.epic + waveConfig.legendary + waveConfig.mythic + waveConfig.ultra;

        for (let i = 0; i < waveConfig.common; i++) {
            this.spawnEnemy('common');
        }
        for (let i = 0; i < waveConfig.uncommon; i++) {
            this.spawnEnemy('uncommon');
        }
        for (let i = 0; i < waveConfig.rare; i++) {
            this.spawnEnemy('rare');
        }
        for (let i = 0; i < waveConfig.epic; i++) {
            this.spawnEnemy('epic');
        }
        for (let i = 0; i < waveConfig.legendary; i++) {
            this.spawnEnemy('legendary');
        }
        for (let i = 0; i < waveConfig.mythic; i++) {
            this.spawnEnemy('mythic');
        }
        for (let i = 0; i < waveConfig.ultra; i++) {
            this.spawnEnemy('ultra'); // Spawn Ultra enemy if present
        }
    }

    spawnEnemy(rarity) {
        const player = this.game.player;
        let x, y;
        let spawnValid = false;

        // Ensure the spawn location is valid (not too close to the player)
        while (!spawnValid) {
            x = Math.random() * this.game.worldWidth;
            y = Math.random() * this.game.worldHeight;

            const distanceToPlayer = Math.sqrt(Math.pow(x - player.x, 2) + Math.pow(y - player.y, 2));
            if (distanceToPlayer >= SPAWN_DISTANCE) {
                spawnValid = true; // Valid spawn position found
            }
        }

        // Special handling for Ultra enemy
        if (rarity === 'ultra') {
            // Potentially add special spawn logic for Ultra enemy
            // For example, spawn it at the center of the map or with special effects
            x = this.game.worldWidth / 2;
            y = this.game.worldHeight / 2;
        }

        const enemy = new Enemy(x, y, rarity, this.game.petalManager);

        this.game.enemies.push(enemy);
        this.enemiesSpawned++;
    }

    checkWaveCompletion() {
        if (this.enemiesSpawned >= this.enemiesToSpawn && this.game.enemies.length === 0) {
            if (!this.game.isWaveTransitioning) {
                this.game.isWaveTransitioning = true; // Set the flag to indicate wave transition
                setTimeout(() => {
                    this.startNextWave();
                    this.game.isWaveTransitioning = false; // Reset the flag after the transition
                }, 2000); // 2 seconds delay
            }
        }
    }

    renderCurrentWave(ctx) {
        ctx.fillStyle = 'white';
        ctx.font = 'bold 36px Arial'; // Larger, more prominent font

        // Create a gradient for the wave text
        const gradient = ctx.createLinearGradient(
            (this.game.worldWidth - ctx.measureText(`Wave ${this.currentWave}`).width) / 2,
            0,
            (this.game.worldWidth + ctx.measureText(`Wave ${this.currentWave}`).width) / 2,
            0
        );
        gradient.addColorStop(0, 'rgba(255,255,255,0.7)');
        gradient.addColorStop(0.5, 'rgba(255,255,255,1)');
        gradient.addColorStop(1, 'rgba(255,255,255,0.7)');

        ctx.fillStyle = gradient;

        // Add slight shadow for depth
        ctx.shadowColor = 'rgba(0,0,0,0.5)';
        ctx.shadowBlur = 5;
        ctx.shadowOffsetX = 2;
        ctx.shadowOffsetY = 2;

        const text = `Wave ${this.currentWave}`;
        const textWidth = ctx.measureText(text).width;

        // Position the wave text slightly lower and a bit to the right
        const waveTextY = 55;
        const waveTextX = (this.game.worldWidth - textWidth) / 2;

        // Optional: Add a subtle pulsing animation
        const pulse = Math.sin(Date.now() * 0.005) * 2;
        ctx.font = `bold ${36 + pulse}px Arial`;

        ctx.fillText(text, waveTextX, waveTextY);

        // Reset shadow
        ctx.shadowColor = 'transparent';
        ctx.shadowBlur = 0;
        ctx.shadowOffsetX = 0;
        ctx.shadowOffsetY = 0;
    }

    renderProgressBar(ctx) {
        const totalEnemies = this.enemiesToSpawn;
        const remainingEnemies = this.enemiesToSpawn - this.game.enemies.length;

        // Draw the background of the progress bar
        ctx.fillStyle = 'rgba(0, 0, 0, 0.5)';
        ctx.fillRect((this.game.worldWidth - 300) / 2, 70, 300, 20);

        // Animate the filled part of the progress bar
        const targetWidth = (remainingEnemies / totalEnemies) * 300;
        const currentWidth = Math.min(targetWidth, this.currentWidth || targetWidth);

        // Gradient for progress bar
        const progressGradient = ctx.createLinearGradient(
            (this.game.worldWidth - 300) / 2,
            0,
            (this.game.worldWidth + 300) / 2,
            0
        );
        progressGradient.addColorStop(0, 'rgba(0,255,0,0.7)');
        progressGradient.addColorStop(0.5, 'rgba(0,255,0,1)');
        progressGradient.addColorStop(1, 'rgba(0,255,0,0.7)');

        ctx.fillStyle = progressGradient;
        ctx.fillRect((this.game.worldWidth - 300) / 2, 70, currentWidth, 20);

        // Smoothly transition the width
        if (currentWidth < targetWidth) {
            this.currentWidth = currentWidth + 5;
        } else {
            this.currentWidth = targetWidth;
        }

        // Stylized progress text
        ctx.font = 'bold 20px Arial';

        // Create a gradient for the progress text
        const textGradient = ctx.createLinearGradient(
            this.game.worldWidth / 2 - 50,
            0,
            this.game.worldWidth / 2 + 50,
            0
        );
        textGradient.addColorStop(0, 'rgba(255,255,255,0.7)');
        textGradient.addColorStop(0.5, 'rgba(255,255,255,1)');
        textGradient.addColorStop(1, 'rgba(255,255,255,0.7)');

        ctx.fillStyle = textGradient;

        // Add shadow for depth
        ctx.shadowColor = 'rgba(0,0,0,0.5)';
        ctx.shadowBlur = 3;
        ctx.shadowOffsetX = 1;
        ctx.shadowOffsetY = 1;

        // Center the text precisely
        const progressText = `${remainingEnemies} / ${totalEnemies}`;
        const progressTextWidth = ctx.measureText(progressText).width;
        ctx.fillText(progressText, this.game.worldWidth / 2 - 15, 110);

        // Reset shadow
        ctx.shadowColor = 'transparent';
        ctx.shadowBlur = 0;
        ctx.shadowOffsetX = 0;
        ctx.shadowOffsetY = 0;
    }
}