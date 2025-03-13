import { DesertMob } from "./DesertMob.js";
import { OceanMob } from "./OceanMob.js";
import { Bee } from "./Bee.js";
import { BabyAnt } from "./BabyAnt.js";
import { Hornet } from "./Hornet.js";
import { Drop } from "./Drop.js";
import { RockMob } from "./RockMob.js";
import { Ladybug } from "./Ladybug.js";
import { WorkerAnt } from "./WorkerAnt.js";

export class WaveManager {
    constructor(arena, theme, ctx, player) {
        this.arena = arena;
        this.theme = theme;
        this.ctx = ctx;
        this.player = player;

        // Wave management
        this.currentWave = 1;
        this.mobs = [];
        this.mobPool = [];
        this.totalMobs = 0;

        // Spawning parameters
        this.spawnInterval = 3000; // 3 seconds between spawn batches
        this.lastSpawnTime = 0;
        this.minSpawnBatch = 2;
        this.maxSpawnBatch = 5;
        this.minDistanceFromPlayer = 150;

        // Theme-based mob selection
        this.MobClass = this.selectMobClass(theme);

        // Wave progression animation
        this.progressBarWidth = 0;
        this.targetProgressBarWidth = 0;
        this.progressAnimationSpeed = 5;
        this.setWave = this.setWave.bind(this);
        this.getCurrentWave = this.getCurrentWave.bind(this);
        this.resetWave = this.resetWave.bind(this);

        // Expose methods globally
        window.setWave = (waveNumber) => this.setWave(waveNumber);
        window.getCurrentWave = () => this.getCurrentWave();
        window.resetWave = () => this.resetWave();
        // Wave text animation
        this.waveTextOpacity = 0;
        this.waveTextScale = 0.5;
        this.cleanup = this.cleanup.bind(this);
        window.spawnMob = (type, rarity) => this.spawnSpecificMob(type, rarity);
        window.spawnRandomMob = () => this.spawnRandomMob();

        // Drops
        this.drops = [];
    }

    spawnDrop(dropConfig) {
        this.drops.push(dropConfig);
    }

    // When adding a mob, ensure it's tracked
    addMob(mob) {
        if (!this.mobs) this.mobs = [];

        // Add additional tracking or game-specific logic
        this.mobs.push(mob);

        // Optional: Assign the game reference to the mob
        mob.game = this;
    }

    updateAndDrawDrops(ctx, player) {
        // Update and draw drops
        this.drops.forEach(drop => {
            drop.draw(ctx);
            drop.checkPlayerCollision(player);
        });

        // Remove collected or expired drops
        this.drops = this.drops.filter(drop => !drop.collected);
    }

    spawnSpecificMob(type, rarity) {
        // Mapping of mob types to their classes
        const mobTypes = {
            'Bee': Bee,
            'BabyAnt': BabyAnt,
            'DesertMob': DesertMob,
            'OceanMob': OceanMob,
            'SuperBee': SuperBee,
            'UltraBee': UltraBee,
            // Add other mob types as needed
        };

        // Validate inputs
        const validRarities = [
            'common', 'uncommon', 'rare',
            'epic', 'legendary', 'mythic',
            'ultra', 'super'
        ];

        if (!mobTypes[type]) {
            console.error(`Invalid mob type: ${type}. Available types:`, Object.keys(mobTypes));
            return null;
        }

        if (!validRarities.includes(rarity)) {
            console.error(`Invalid rarity: ${rarity}. Available rarities:`, validRarities);
            return null;
        }

        // Generate safe spawn point
        const spawnPoint = this.generateSafeSpawnPoint();

        // Create and add mob
        const MobClass = mobTypes[type];
        const newMob = new MobClass(spawnPoint.x, spawnPoint.y, rarity);

        this.mobs.push(newMob);
        console.log(`Spawned ${rarity} ${type} at (${spawnPoint.x.toFixed(2)}, ${spawnPoint.y.toFixed(2)})`);

        return newMob;
    }

    spawnRandomMob() {
        const mobTypes = [
            'Bee', 'BabyAnt', 'DesertMob', 'OceanMob',
            'SuperBee', 'UltraBee'
        ];

        const rarities = [
            'common', 'uncommon', 'rare',
            'epic', 'legendary', 'mythic',
            'ultra', 'super'
        ];

        const randomType = mobTypes[Math.floor(Math.random() * mobTypes.length)];
        const randomRarity = rarities[Math.floor(Math.random() * rarities.length)];

        return this.spawnSpecificMob(randomType, randomRarity);
    }

    setWave(waveNumber) {
        if (typeof waveNumber !== 'number' || waveNumber < 1) {
            console.error('Invalid wave number. Must be a positive number.');
            return;
        }

        // Set the wave
        this.currentWave = waveNumber;

        // Regenerate mobs for the new wave
        this.generateMobs();

        console.log(`Wave set to ${waveNumber}`);
    }

    getCurrentWave() {
        console.log(`Current Wave: ${this.currentWave}`);
        return this.currentWave;
    }

    resetWave() {
        this.currentWave = 1;
        this.generateMobs();
        console.log('Wave reset to 1');
    }


    selectMobClass(theme) {
        const mobClasses = {
            'garden': [Bee, BabyAnt],
            'desert': DesertMob,
            'ocean': OceanMob
        };

        const themeClasses = mobClasses[theme.toLowerCase()] || [Bee];
        return themeClasses[Math.floor(Math.random() * themeClasses.length)];
    }

    generateMobs() {
        // Clear existing mobs and mob pool
        this.mobs = [];
        this.mobPool = [];

        // Dynamic total mob pool size based on wave
        const baseMobPoolSize = this.calculateMobPoolSize();

        // Rarity distribution becomes more skewed towards rare mobs in higher waves
        const mobRarities = this.calculateRarityDistribution();

        // Populate mob pool
        mobRarities.forEach(({ rarity, percentage }) => {
            const rarityCount = Math.floor(baseMobPoolSize * percentage);
            for (let i = 0; i < rarityCount; i++) {
                this.mobPool.push(rarity);
            }
        });

        // Shuffle mob pool for randomness
        this.shuffleMobPool();

        // Adjust spawn parameters for higher waves
        this.adjustSpawnParametersForWave();

        // Initial spawn of first batch
        this.spawnMobBatch();

        this.totalMobs = this.mobPool.length + this.mobs.length;
        this.lastSpawnTime = Date.now();
        this.animateWaveText();
    }

    calculateMobPoolSize() {
        // Logarithmic scaling for total mob pool
        const basePoolSize = 50; // Starting pool size
        const maxPoolSize = 300; // Maximum pool size
        const scaleFactor = 10; // Controls pool size growth

        return Math.min(
            maxPoolSize,
            basePoolSize + Math.floor(Math.log(this.currentWave + 1) * scaleFactor)
        );
    }

    calculateRarityDistribution() {
        const baseRarities = [
            { rarity: 'common', basePercentage: 0.5 },
            { rarity: 'uncommon', basePercentage: 0.3 },
            { rarity: 'rare', basePercentage: 0.1 },
            { rarity: 'epic', basePercentage: 0.05 },
            { rarity: 'legendary', basePercentage: 0.03 },
            { rarity: 'mythic', basePercentage: 0.01 },
            { rarity: 'ultra', basePercentage: 0.01 },
            { rarity: 'super', basePercentage: 0.005 }
        ];

        // Similar scaling logic as before, but adjusted for garden theme
        return baseRarities.map(entry => {
            const rarityScaleFactor = Math.pow(1.2, this.currentWave / 10);
            return {
                rarity: entry.rarity,
                percentage: entry.basePercentage * rarityScaleFactor
            };
        }).filter(entry => entry.percentage > 0);
    }

    adjustRarityPercentage(basePercentage, waveFactor) {
        // Shift percentages towards rarer mobs as wave increases
        const rarityShiftFactor = Math.min(waveFactor * 0.05, 0.3);

        // Redistribute percentages, giving more weight to rarer mobs
        return basePercentage * (1 - rarityShiftFactor);
    }

    shuffleMobPool() {
        for (let i = this.mobPool.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [this.mobPool[i], this.mobPool[j]] = [this.mobPool[j], this.mobPool[i]];
        }
    }

    spawnMobBatch() {
        const remainingMobs = this.mobPool.length;
        const batchSize = Math.min(
            Math.max(this.minSpawnBatch, Math.min(this.maxSpawnBatch, remainingMobs)),
            remainingMobs
        );

        for (let i = 0; i < batchSize; i++) {
            if (this.mobPool.length === 0) break;

            const rarity = this.mobPool.shift();
            const spawnPoint = this.generateSafeSpawnPoint();

            let MobClass = this.selectMobClassForRarity(rarity);

            const newMob = new MobClass(spawnPoint.x, spawnPoint.y, rarity, this.arena);

            // IMPORTANT: Set the waveManager for drop generation
            newMob.waveManager = this;

            this.mobs.push(newMob);
        }

        this.lastSpawnTime = Date.now();
    }

    selectMobClassForRarity(rarity) {
        if (this.theme.toLowerCase() === 'garden') {
            const mobChance = Math.random();
            if (mobChance < 0.2) return Hornet;
            if (mobChance < 0.4) return Ladybug;
            if (mobChance < 0.6) return Bee;
            if (mobChance < 0.8) return BabyAnt;
            if (mobChance < 0.9) return WorkerAnt;
            return RockMob;
        }

        // For other themes, keep existing logic or implement similar distribution
        // You might want to add similar logic for other themes
        return super.selectMobClassForRarity(rarity);
    }

    adjustSpawnParametersForWave() {
        // Dynamically adjust spawn parameters based on wave number
        const waveScaleFactor = Math.log(this.currentWave + 1);

        // Reduce spawn interval as waves progress
        this.spawnInterval = Math.max(
            1000, // Minimum interval of 1 second
            3000 / waveScaleFactor
        );

        // Increase spawn batch size
        this.minSpawnBatch = Math.floor(2 * waveScaleFactor);
        this.maxSpawnBatch = Math.floor(5 * waveScaleFactor);

        // Increase minimum player distance for higher waves
        this.minDistanceFromPlayer = Math.min(
            150 + (this.currentWave * 2),
            300 // Cap the distance
        );
    }

    generateSafeSpawnPoint() {
        const maxAttempts = 50;
        let attempts = 0;

        while (attempts < maxAttempts) {
            // Generate spawn point within arena
            const angle = Math.random() * Math.PI * 2;
            const radius = Math.random() * this.arena.radius;

            const x = this.arena.x + Math.cos(angle) * radius;
            const y = this.arena.y + Math.sin(angle) * radius;

            // Check distance from player
            const dx = x - this.player.x;
            const dy = y - this.player.y;
            const distance = Math.sqrt(dx * dx + dy * dy);

            // If far enough from player, return the point
            if (distance >= this.minDistanceFromPlayer) {
                return { x, y };
            }

            attempts++;
        }

        // Fallback to arena center if no safe point found
        return {
            x: this.arena.x,
            y: this.arena.y
        };
    }

    update(player) {
        // More efficient mob removal and memory management
        for (let i = this.mobs.length - 1; i >= 0; i--) {
            const mob = this.mobs[i];

            if (mob.isDead) {
                const elapsedTime = Date.now() - mob.deathTimer;

                // Increase the removal time to match the death animation duration
                if (elapsedTime > mob.deathDuration) { // Use deathDuration instead of a fixed 5000
                    console.log(`Removing dead mob: ${mob.constructor.name}`);

                    // Safely add experience
                    try {
                        player.addExperience(mob.experienceValue || 10);
                    } catch (error) {
                        console.error('Error adding experience:', error);
                    }

                    // Remove mob
                    this.mobs.splice(i, 1);
                    continue;
                }
            }

            // Update each mob
            mob.update(player, this.arena, this.mobs);
        }

        // Spawn new mobs in batches with time-based control
        const currentTime = Date.now();
        if (this.mobPool.length > 0 &&
            currentTime - this.lastSpawnTime >= this.spawnInterval) {
            this.spawnMobBatch();
        }

        // Optimize progress bar calculation
        const remainingMobsRatio = (this.mobs.length + this.mobPool.length) / this.totalMobs;
        const barWidth = 500;

        // Use lerp for smoother, more performant animation
        this.targetProgressBarWidth = (1 - remainingMobsRatio) * barWidth;
        this.progressBarWidth += (this.targetProgressBarWidth - this.progressBarWidth) * 0.2;

        // Optimize wave text animation with decay
        if (this.waveTextOpacity > 0) {
            this.waveTextOpacity = Math.max(0, this.waveTextOpacity - 0.02);
            this.waveTextScale = Math.min(1, this.waveTextScale + 0.01);
        }

        // Optional: Periodically clean up mob pool to prevent excessive growth
        if (this.mobPool.length > 500) {
            this.mobPool.length = 500;
        }
        this.drops = this.drops.filter(drop => !drop.collected);
    }

    draw(ctx) {
        this.mobs.forEach(mob => {
            // Check if mob is a Hornet and pass player
            if (mob.constructor.name === 'Hornet') {
                mob.draw(ctx, this.player);
            } else {
                // For other mob types, use standard draw method
                mob.draw(ctx);
            }
        });

        this.drawWaveProgressBar(ctx);
        this.drawCurrentWaveRarity(ctx);
        this.drawWaveText(ctx);
    }

    drawCurrentWaveRarity(ctx) {
        if (this.mobs.length > 0) {
            const firstMob = this.mobs[0];
            firstMob.drawRarityText(ctx);
        }
    }

    drawWaveProgressBar(ctx) {
        ctx.save();
        ctx.resetTransform();

        // Bar dimensions and positioning
        const barWidth = 500;  // Increased width
        const barHeight = 30;  // Increased height
        const x = (this.ctx.canvas.width - barWidth) / 2;  // Centered
        const y = 70;  // Slightly lower on screen

        // Rounded corner radius
        const cornerRadius = 15;

        // Background bar with shadow
        ctx.shadowColor = 'rgba(0,0,0,0.3)';
        ctx.shadowBlur = 10;
        ctx.shadowOffsetY = 3;

        ctx.fillStyle = 'rgba(50, 50, 50, 0.5)';
        this.roundedRect(ctx, x, y, barWidth, barHeight, cornerRadius);
        ctx.fill();

        // Reset shadow
        ctx.shadowColor = 'transparent';
        ctx.shadowBlur = 0;
        ctx.shadowOffsetY = 0;

        // Progress bar with gradient
        const gradient = ctx.createLinearGradient(x, y, x + barWidth, y);
        gradient.addColorStop(0, 'rgba(46, 204, 113, 0.7)');   // Green
        gradient.addColorStop(0.5, 'rgba(241, 196, 15, 0.7)'); // Yellow
        gradient.addColorStop(1, 'rgba(231, 76, 60, 0.7)');    // Red

        ctx.fillStyle = gradient;
        this.roundedRect(ctx, x, y, this.progressBarWidth, barHeight, cornerRadius);
        ctx.fill();

        // Border
        ctx.strokeStyle = 'rgba(255,255,255,0.5)';
        ctx.lineWidth = 2;
        this.roundedRect(ctx, x, y, barWidth, barHeight, cornerRadius);
        ctx.stroke();

        // Wave text above progress bar with advanced styling
        const waveText = `WAVE ${this.currentWave}`;

        // Outer glow effect
        ctx.shadowColor = 'rgba(46, 204, 113, 0.7)';
        ctx.shadowBlur = 10;

        // Gradient text
        const textGradient = ctx.createLinearGradient(
            this.ctx.canvas.width / 2 - 100,
            40,
            this.ctx.canvas.width / 2 + 100,
            40
        );
        textGradient.addColorStop(0, 'rgba(46, 204, 113, 1)');   // Green
        textGradient.addColorStop(0.5, 'rgba(241, 196, 15, 1)'); // Yellow
        textGradient.addColorStop(1, 'rgba(231, 76, 60, 1)');    // Red

        ctx.fillStyle = textGradient;
        ctx.font = 'bold 48px "Orbitron", sans-serif'; // Modern, tech-like font
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';

        // Text with slight 3D effect
        ctx.fillText(
            waveText,
            this.ctx.canvas.width / 2,
            y - 25
        );

        // Reset shadow
        ctx.shadowColor = 'transparent';
        ctx.shadowBlur = 0;

        ctx.restore();
    }

// Helper method for rounded rectangles
    roundedRect(ctx, x, y, width, height, radius) {
        ctx.beginPath();
        ctx.moveTo(x + radius, y);
        ctx.arcTo(x + width, y, x + width, y + height, radius);
        ctx.arcTo(x + width, y + height, x, y + height, radius);
        ctx.arcTo(x, y + height, x, y, radius);
        ctx.arcTo(x, y, x + width, y, radius);
        ctx.closePath();
    }

    cleanup() {
        // Explicitly clear all references
        this.mobs.forEach(mob => {
            mob = null;
        });
        this.mobs = [];
        this.mobPool = [];
    }

    drawWaveText(ctx) {
        ctx.save();
        ctx.resetTransform();

        // Animated wave text
        ctx.font = `bold ${40 * this.waveTextScale}px Arial`;
        ctx.fillStyle = `rgba(255, 255, 255, ${this.waveTextOpacity})`;
        ctx.textAlign = 'center';

        const waveText = `Wave ${this.currentWave}`;
        ctx.fillText(
            waveText,
            this.ctx.canvas.width / 2,
            this.ctx.canvas.height / 2
        );

        ctx.restore();
    }

    animateWaveText() {
        this.waveTextOpacity = 1;
        this.waveTextScale = 0.5;
    }

    getProgressBarColor() {
        const remainingMobsRatio = this.mobs.length / this.totalMobs;

        if (remainingMobsRatio > 0.75) return 'rgba(46, 204, 113, 0.7)'; // Green
        if (remainingMobsRatio > 0.5) return 'rgba(241, 196, 15, 0.7)'; // Yellow
        if (remainingMobsRatio > 0.25) return 'rgba(230, 126, 34, 0.7)'; // Orange
        return 'rgba(231, 76, 60, 0.7)'; // Red
    }

    nextWave() {
        this.currentWave++;
        this.generateMobs();
    }

    isWaveCleared() {
        // Wave is cleared when both mob pool and active mobs are empty
        return this.mobs.length === 0 && this.mobPool.length === 0;
    }
}