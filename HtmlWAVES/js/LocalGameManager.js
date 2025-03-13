import { WaveManager } from './waveManager.js';
import { Player } from './player.js';
import { Camera } from './camera.js';
 import { Drop } from "./Drop.js";

export class LocalGameManager {
    constructor(canvas, playerName, theme) {
        this.canvas = canvas;
        this.ctx = canvas.getContext('2d');
        this.playerName = playerName;
        this.theme = theme;

        // World configuration
        this.worldWidth = 12000;
        this.worldHeight = 12000;

        // Create arena in the center of the world
        this.arena = this.createArena();

        // Create player at arena center
        this.player = this.createPlayer();

        // Create camera with world dimensions
        this.camera = new Camera(
            canvas,
            this.worldWidth,
            this.worldHeight
        );

        // Initialize Wave Manager
        this.waveManager = new WaveManager(this.arena, this.theme, this.ctx, this.player);

        this.isGameRunning = true;

        // Time tracking for consistent movement
        this.lastFrameTime = 0;
        this.targetFPS = 60;
        this.frameTime = 1000 / this.targetFPS;

        this.minZoom = 0.5;   // Minimum zoom level
        this.maxZoom = 2.0;   // Maximum zoom level
        this.zoomSensitivity = 0.1;  // Zoom speed

        // Add mouse wheel event listener
        this.setupZoomControls(canvas);

        // Generate initial wave of mobs
        this.waveManager.generateMobs();
        this.drops = [];
    }

     spawnDrop(dropConfig) {
        console.log('Spawning drop in LocalGameManager:', dropConfig);
        const drop = new Drop(
            dropConfig.x,
            dropConfig.y,
            dropConfig.type,
            dropConfig.rarity,
            dropConfig.imagePath
        );

        // Add additional logging
        console.log('Drop created:', drop);

        this.drops.push(drop);
    }

    // Update and draw drops method
     updateAndDrawDrops() {
         // Update drops
         this.drops.forEach(drop => {
             // Ensure draw method receives camera context
             drop.draw(this.ctx, this.camera);
         });

         // Remove collected or expired drops
         this.drops = this.drops.filter(drop => !drop.collected);
     }


    setupZoomControls(canvas) {
        canvas.addEventListener('wheel', (event) => {
            event.preventDefault();

            // Determine zoom direction
            const zoomDelta = event.deltaY > 0 ? -1 : 1;

            // Calculate new zoom level
            const newZoom = this.clamp(
                this.camera.zoom + (zoomDelta * this.zoomSensitivity),
                this.minZoom,
                this.maxZoom
            );

            // Update camera zoom
            this.camera.setZoom(newZoom);
        }, {passive: false});
    }


    // Utility method to clamp value between min and max
    clamp(value, min, max) {
        return Math.min(Math.max(value, min), max);
    }

    drawZoomIndicator() {
        const zoomPercentage = Math.round(this.camera.zoom * 100);

        this.ctx.save();
        this.ctx.resetTransform(); // Ensure drawing is not affected by camera transform

        // Draw zoom level text
        this.ctx.fillStyle = 'white';
        this.ctx.font = '16px Arial';
        this.ctx.fillText(`Zoom: ${zoomPercentage}%`, 10, 30);

        this.ctx.restore();
    }

    createArena() {
        return {
            x: this.worldWidth / 2,
            y: this.worldHeight / 2,
            radius: 500, // Starting radius of 500 (1000/2)
            color: this.getArenaColor(),
            width: this.worldWidth,
            height: this.worldHeight,
            baseRadius: 500,  // Store base radius for wave progression
            radiusIncrement: 25  // Half of 50x50
        };
    }

    nextWave() {
        // Increase arena radius
        this.arena.radius = this.arena.baseRadius +
            (this.waveManager.currentWave * this.arena.radiusIncrement);

        // Proceed to next wave
        this.waveManager.nextWave();
    }

    getArenaColor() {
        const themeColors = {
            'garden': 'rgba(34, 139, 34, 0.2)',
            'desert': 'rgba(238, 214, 175, 0.2)',
            'ocean': 'rgba(64, 164, 223, 0.2)'
        };
        return themeColors[this.theme.toLowerCase()] || themeColors['garden'];
    }

    createPlayer() {
        return new Player(
            this.arena.x,
            this.arena.y,
            this.arena,
            {
                name: this.playerName || 'Player'
            }
        );
    }

    updatePlayer(deltaTime) {
        // Normalize movement speed based on delta time
        // This ensures consistent movement across different frame rates
        const speedMultiplier = deltaTime / this.frameTime;

        // Temporarily modify player's base speed for this frame
        const originalSpeed = this.player.baseSpeed;
        this.player.baseSpeed *= speedMultiplier;

        const keyState = this.player.movement.keys;
        // Update player movement
        this.player.update(deltaTime, keyState);

        // Restore original speed
        this.player.baseSpeed = originalSpeed;
    }

    checkPetalMobCollisions() {
        this.player.selectedPetals.forEach(petal => {
            // Only check collision if petal is not recharging
            if (!petal.isRecharging) {
                this.waveManager.mobs.forEach(mob => {
                    if (petal.checkCollisionWithMob(mob)) {
                        // Petal hits mob
                        const petalRecharging = petal.dealDamageToMob(mob);

                        if (mob.health <= 0) {
                            // Mob dies
                            mob.die();
                            this.player.addScore(10);
                            this.player.addKill();
                        }

                        // Note: No removal, just recharging
                    }
                });
            }
        });
    }

    gameLoop(currentTime = 0) {
        if (!this.isGameRunning) return;

        // Calculate delta time
        const deltaTime = currentTime - this.lastFrameTime;
        this.lastFrameTime = currentTime;

        // Limit update rate
        if (deltaTime > 0) {
            // Clear canvas
            this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);

            // Update camera to follow player
            this.camera.follow(this.player);

            // Begin camera rendering
            this.camera.begin();

            // Draw world background
            this.drawWorldBackground();
            // Draw arena
            this.drawArena();

            // Update player with delta time
            this.updatePlayer(deltaTime);

            // Update and draw wave manager
            this.waveManager.update(this.player);

            this.checkPetalMobCollisions();

            this.checkMobCollisions();

            // Draw player
            this.player.draw(this.ctx);

            // Draw mobs from wave manager
            this.waveManager.draw(this.ctx);

            if (this.waveManager.drops.length > 0) {
                console.log(`Transferring ${this.waveManager.drops.length} drops from WaveManager`);

                this.waveManager.drops.forEach(dropConfig => {
                    // Spawn each drop through LocalGameManager
                    this.spawnDrop({
                        x: dropConfig.x,
                        y: dropConfig.y,
                        type: dropConfig.type,
                        rarity: dropConfig.rarity,
                        imagePath: dropConfig.imagePath
                    });
                });

                // Clear wave manager drops after transfer
                this.waveManager.drops = [];
            }

            // Call update and draw drops method
            this.updateAndDrawDrops();


            // End camera rendering
            this.camera.end();

            // Check wave completion
            if (this.waveManager.isWaveCleared()) {
                // Use the LocalGameManager's nextWave method
                this.nextWave();
            }
        }

        // Draw UI elements outside of camera transform
        this.drawZoomIndicator();

          this.player.hotbar.draw(
              this.ctx,
              this.canvas.width,
              this.canvas.height
          );

         this.player.inventory.drawIcon(this.ctx);

         this.player.inventory.drawInventoryView(
             this.ctx,
              this.canvas.width,
             this.canvas.height
          );

        // Continue game loop
        requestAnimationFrame(this.gameLoop.bind(this));
    }
    updateGameState(deltaTime) {
        this.player.update(deltaTime);
        this.waveManager.update(this.player);
    }

    renderGameObjects() {
        this.camera.begin();

        this.drawWorldBackground();
        this.player.draw(this.ctx);
        this.waveManager.draw(this.ctx);

        this.camera.end();
    }

    checkGameConditions() {
        if (this.waveManager.isWaveCleared()) {
            this.nextWave();
        }

        this.checkCollisions();
    }

    checkCollisions() {
        // Оптимизированные проверки столкновений
        this.checkPetalMobCollisions();
        this.checkMobCollisions();
    }

// Обработка критических ошибок
    handleGameLoopError(error) {
        console.group('%c🔥 Game Loop Critical Error', 'color: red; font-weight: bold');
        console.error('Error Details:', error);

        // Расширенная диагностика состояния игры
        console.log('Game State:', {
            isRunning: this.isGameRunning,
            playerHealth: this.player?.health,
            currentWave: this.waveManager?.currentWave,
            mobCount: this.waveManager?.mobs?.length
        });

        console.groupEnd();

        // Диспетчеризация события завершения игры
        const gameOverEvent = new CustomEvent('gameOver', {
            detail: {
                reason: 'game_loop_failure',
                error: error.message
            }
        });
        document.dispatchEvent(gameOverEvent);
    }

    // Optional: Method to handle player-mob interactions
    checkMobCollisions() {
        this.waveManager.mobs.forEach(mob => {
            const distance = Math.hypot(
                this.player.x - mob.x,
                this.player.y - mob.y
            );

            const collisionThreshold = this.player.radius + mob.size;

            if (distance < collisionThreshold) {
                // Mob rams player
                mob.bodyAttack(this.player);

                // Aggravate mob on collision
                if (!mob.isAggravated) {
                    mob.isAggravated = true;
                    mob.aggressionTimer = Date.now();
                }
            }
        });
    }

    handlePlayerMobCollision(mob) {
        // Implement collision logic
        // For example:
        // - Reduce player health
        // - Apply knockback
        // - Mark mob as hit/dead
        mob.takeDamage(this.player.damage);
    }

    // Optional: Add wave-related UI or game state methods
    getCurrentWave() {
        return this.waveManager.currentWave;
    }

    getRemainingMobs() {
        return this.waveManager.mobs.length;
    }

    start() {
        // Reset game state if needed
        this.waveManager.generateMobs();
        this.gameLoop();
    }

    end() {
        this.isGameRunning = false;
        // Additional cleanup if needed
    }

    drawWorldBackground() {
        // Light grid or texture for world background
        this.ctx.fillStyle = 'rgba(0, 0, 0, 0.05)';
        this.ctx.fillRect(0, 0, this.worldWidth, this.worldHeight);

        // Optional: Draw grid lines
        this.ctx.strokeStyle = 'rgba(255,255,255,0.1)';
        this.ctx.lineWidth = 1;

        // Vertical lines
        for (let x = 0; x < this.worldWidth; x += 100) {
            this.ctx.beginPath();
            this.ctx.moveTo(x, 0);
            this.ctx.lineTo(x, this.worldHeight);
            this.ctx.stroke();
        }

        // Horizontal lines
        for (let y = 0; y < this.worldHeight; y += 100) {
            this.ctx.beginPath();
            this.ctx.moveTo(0, y);
            this.ctx.lineTo(this.worldWidth, y);
            this.ctx.stroke();
        }
    }

    drawArena() {
        this.ctx.beginPath();
        this.ctx.arc(
            this.arena.x,
            this.arena.y,
            this.arena.radius,
            0,
            Math.PI * 2
        );
        this.ctx.fillStyle = this.arena.color;
        this.ctx.fill();

        // Arena border
        this.ctx.strokeStyle = 'rgba(255,255,255,0.5)';
        this.ctx.lineWidth = 2;
        this.ctx.stroke();
    }

    start() {
        this.gameLoop();
    }


    end() {
        this.isGameRunning = false;
    }
}
class QuadTree {
    constructor(boundary, capacity = 4) {
        this.boundary = boundary;
        this.capacity = capacity;
        this.objects = [];
        this.divided = false;
        this.northwest = null;
        this.northeast = null;
        this.southwest = null;
        this.southeast = null;
    }

    insert(gameObject) {
        if (!this.boundary.contains(gameObject)) return false;

        if (this.objects.length < this.capacity) {
            this.objects.push(gameObject);
            return true;
        }

        if (!this.divided) this.subdivide();

        return (
            this.northwest.insert(gameObject) ||
            this.northeast.insert(gameObject) ||
            this.southwest.insert(gameObject) ||
            this.southeast.insert(gameObject)
        );
    }

    subdivide() {
        // Логика разделения квадранта
    }
}
