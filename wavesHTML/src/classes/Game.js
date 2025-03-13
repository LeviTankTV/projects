import { World } from './World.js';
import { Camera } from './Camera.js';
import { Player } from './Player.js';
import { PetalManager } from "./PetalManager.js";
import { BasicPetal } from "./Basic.js";
import {ThirdEyePetal} from "./ThirdEyePetal.js";
import { WaveManager } from "./WaveManager.js";
import {SquarePetal} from "./Square.js";
import {PentagonPetal} from "./Pentagon.js";
import {Stinger} from "./Stinger.js";
export class Game {
    constructor(canvas) {

        this.canvas = canvas;
        this.ctx = canvas.getContext('2d');
        this.keys = {};
        this.worldWidth = window.innerWidth;
        this.worldHeight = window.innerHeight;
        this.tileSize = this.worldWidth / 6;
        this.world = new World(this.worldWidth, this.worldHeight);

        // Create player
        this.player = new Player(this.tileSize / 2, this.worldHeight / 2, 20, 5);

        // Camera setup
        this.camera = new Camera(this.worldWidth, this.worldHeight, canvas.width, canvas.height);

        // Setup event listeners
        this.setupEventListeners();

        // Petal Manager
        this.petalManager = new PetalManager();
        this.droppedPetals = [];
        // Player state
        this.playerState = {
            spacebar: false,
            tKey: false
        };

        // Initialize 10 common petals
        this.initializeCommonPetals();

        // Spawn enemies
        this.enemies = [];

        // Initialize hotbar with 20 slots (10 in first row, 10 in second)
        this.selectedHotbarSlots = new Array(20).fill(null);

        // Populate first row of hotbar with initial petals
        for (let i = 0; i < 10; i++) {
            this.selectedHotbarSlots[i] = this.player.selectedPetals[i];
        }

        // Dragging state
        this.draggedPetal = null;

        this.gameOver = false; // Flag to check if the game is over
        this.restartButton = { x: this.worldWidth / 2 - 50, y: this.worldHeight / 2, width: 100, height: 50 };

        this.waveManager = new WaveManager(this); // Initialize WaveManager
        this.waveManager.startNextWave(); // Start the first wave
        this.isWaveTransitioning = false; // Flag to check if a wave transition is happening

        this.sellMode = false;
        this.sellZone = {
            x: 10,
            y: this.worldHeight - 250, // Position it above the hotbar
            width: 50,
            height: 50,
            color: '#FF4444' // Red color to indicate selling
        };
        this.isSellingPetal = false;
    }



    addDroppedItem(petal) {
        this.droppedPetals.push(petal);
    }

    checkPetalCollection() {
        this.droppedPetals = this.droppedPetals.filter(petal => {
            // Skip recharging petals
            if (petal.isRecharging) return true;

            const dx = this.player.x - petal.x;
            const dy = this.player.y - petal.y;
            const distance = Math.sqrt(dx * dx + dy * dy);
            const minDistance = this.player.radius + (petal.radius || 5);

            if (distance < minDistance) {
                // Find the first available slot in the first hotbar row
                const firstRowFreeIndex = this.selectedHotbarSlots.findIndex((slot, index) =>
                    index < 10 && slot === null
                );

                // If no slot in first row, check the second row
                const secondRowFreeIndex = firstRowFreeIndex === -1
                    ? this.selectedHotbarSlots.findIndex((slot, index) =>
                        index >= 10 && index < 20 && slot === null
                    )
                    : -1;

                // Determine the slot to place the petal
                const slotIndex = firstRowFreeIndex !== -1
                    ? firstRowFreeIndex
                    : secondRowFreeIndex;

                if (slotIndex !== -1) {
                    // Add petal to the first free slot
                    this.selectedHotbarSlots[slotIndex] = petal;

                    // Add to player's petals
                    this.player.addPetal(petal);

                    // Update PetalManager if needed
                    this.petalManager.selectedPetals[slotIndex] = petal;

                    return false; // Remove from dropped petals
                } else {
                    // Both hotbar rows are full, can't pick up
                    console.log("Hotbar is full. Cannot pick up petal.");
                    return true; // Keep the petal in dropped items
                }
            }
            return true;
        });
    }

    setupEventListeners() {
        window.addEventListener('keydown', (e) => {
            this.keys[e.key] = true;
        });

        window.addEventListener('keyup', (e) => {
            this.keys[e.key] = false;
        });

        this.canvas.addEventListener('mousedown', (e) => this.handleMouseDown(e));
        this.canvas.addEventListener('mouseup', (e) => this.handleMouseUp(e));
        this.canvas.addEventListener('mousemove', (e) => this.handleMouseMove(e));

        // Add click event for the restart button
        this.canvas.addEventListener('click', (e) => this.handleClick(e));
    }

    handleClick(e) {
        e.preventDefault();
        const { offsetX, offsetY } = e;

        // Check if the click is within the bounds of the restart button
        if (this.gameOver && offsetX >= this.restartButton.x && offsetX <= this.restartButton.x + this.restartButton.width &&
            offsetY >= this.restartButton.y && offsetY <= this.restartButton.y + this.restartButton.height) {
            this.restartGame(); // Restart the game
        }
    }

    handleMouseDown(e) {
        const { offsetX, offsetY } = e;
        const hotbarRect = this.getHotbarRect();

        // Check if in sell mode and clicked in hotbar
        if (this.sellMode && this.isInHotbarArea(offsetX, offsetY, hotbarRect)) {
            const index = this.getHotbarIndex(offsetX, offsetY);
            if (index !== null) {
                this.attemptSellPetal(index); // Attempt to sell from any of the 20 slots
                return;
            }
        }

        // Existing sell zone logic
        const isInSellZone =
            offsetX >= this.sellZone.x &&
            offsetX <= this.sellZone.x + this.sellZone.width &&
            offsetY >= this.sellZone.y &&
            offsetY <= this.sellZone.y + this.sellZone.height;

        if (isInSellZone) {
            this.toggleSellMode(); // Toggle sell mode when clicking sell zone
            return;
        }

        // Existing hotbar drag logic
        if (this.isInHotbarArea(offsetX, offsetY, hotbarRect)) {
            const index = this.getHotbarIndex(offsetX, offsetY);
            if (index !== null) {
                this.draggedPetal = {
                    petal: this.selectedHotbarSlots[index],
                    originalIndex: index
                };
                this.selectedHotbarSlots[index] = null;
            }
        }
    }

    toggleSellMode() {
        this.sellMode = !this.sellMode;
        console.log(`Sell mode ${this.sellMode ? 'activated' : 'deactivated'}`);
    }

    attemptSellPetal(index) {
        const petal = this.selectedHotbarSlots[index];
        if (!petal) return;

        // Count petals of this type across both rows
        const petalTypeCount = this.selectedHotbarSlots.filter(
            p => p && p.constructor === petal.constructor
        ).length;

        // Prevent selling last petal of a type
        if (petalTypeCount <= 1) {
            console.log("Cannot sell last petal of this type");
            return;
        }

        // Calculate sell value based on rarity
        const sellValues = {
            'common': 10,
            'uncommon': 25,
            'rare': 50,
            'epic': 100,
            'legendary': 250,
            'mythic': 500
        };

        const sellValue = sellValues[petal.rarity] || 10;

        // Add experience to player
        this.player.addExperience(sellValue);

        // Remove petal from hotbar and player's inventory
        this.selectedHotbarSlots[index] = null;

        // Remove from PetalManager
        const petalManagerIndex = this.petalManager.selectedPetals.indexOf(petal);
        if (petalManagerIndex !== -1) {
            this.petalManager.selectedPetals.splice(petalManagerIndex, 1);
        }

        // Remove from player's petals
        const playerPetalIndex = this.player.selectedPetals.indexOf(petal);
        if (playerPetalIndex !== -1) {
            this.player.selectedPetals.splice(playerPetalIndex, 1);
        }

        // Optional: Add visual or sound feedback
        this.showSellFeedback(sellValue);

        // Deactivate sell mode after selling
        this.sellMode = false;
    }

    handleMouseUp(e) {
        const { offsetX, offsetY } = e;
        const hotbarRect = this.getHotbarRect();

        // Check if selling a petal
        if (this.isSellingPetal && this.draggedPetal) {
            const isInSellZone =
                offsetX >= this.sellZone.x &&
                offsetX <= this.sellZone.x + this.sellZone.width &&
                offsetY >= this.sellZone.y &&
                offsetY <= this.sellZone.y + this.sellZone.height;

            if (isInSellZone) {
                // Sell the petal and gain experience
                this.sellPetal(this.draggedPetal.petal);
                this.draggedPetal = null;
                this.isSellingPetal = false;
                return;
            }
        }

        // Existing hotbar drop logic
        if (this.isInHotbarArea(offsetX, offsetY, hotbarRect) && this.draggedPetal) {
            const targetIndex = this.getHotbarIndex(offsetX, offsetY);

            if (targetIndex !== null) {
                // If target slot is occupied, swap
                if (this.selectedHotbarSlots[targetIndex]) {
                    this.selectedHotbarSlots[this.draggedPetal.originalIndex] =
                        this.selectedHotbarSlots[targetIndex];
                }

                // Place dragged petal in new slot
                this.selectedHotbarSlots[targetIndex] = this.draggedPetal.petal;

                // Update PetalManager
                this.petalManager.movePetal(
                    this.draggedPetal.originalIndex,
                    targetIndex
                );
            } else {
                // If not dropped in a valid slot, return to original position
                this.selectedHotbarSlots[this.draggedPetal.originalIndex] =
                    this.draggedPetal.petal;
            }
        } else {
            // If dropped outside hotbar, return to original position
            if (this.draggedPetal) {
                this.selectedHotbarSlots[this.draggedPetal.originalIndex] =
                    this.draggedPetal.petal;
            }
        }

        this.draggedPetal = null;
        this.isSellingPetal = false;
    }

    sellPetal(petal) {
        // Calculate experience based on petal rarity
        const experienceValues = {
            'common': 10,
            'uncommon': 25,
            'rare': 50,
            'epic': 100,
            'legendary': 250,
            'mythic': 500
        };

        // Get experience value for the petal
        const expValue = experienceValues[petal.rarity] || 5;

        // Add experience to player
        this.player.addExperience(expValue);

        // Remove the petal from player's inventory
        const index = this.player.selectedPetals.indexOf(petal);
        if (index !== -1) {
            this.player.selectedPetals.splice(index, 1);
        }

        // Remove from PetalManager
        const petalManagerIndex = this.petalManager.selectedPetals.indexOf(petal);
        if (petalManagerIndex !== -1) {
            this.petalManager.selectedPetals.splice(petalManagerIndex, 1);
        }

        // Optional: Add a visual or sound effect for selling
        console.log(`Sold ${petal.rarity} petal for ${expValue} experience`);
    }
    handleMouseMove(event) {
        event.preventDefault();
        const offsetX = event.offsetX;
        const offsetY = event.offsetY;

        // If dragging a petal, update its visual position
        if (this.draggedPetal) {
            // Store mouse coordinates for rendering
            this.draggedPetal.mouseX = offsetX;
            this.draggedPetal.mouseY = offsetY;

            // Highlight potential drop zones
            const hotbarRect = this.getHotbarRect();
            if (this.isInHotbarArea(offsetX, offsetY, hotbarRect)) {
                const potentialIndex = this.getHotbarIndex(offsetX, offsetY);
                if (potentialIndex !== null) {
                    // Draw a highlight or outline for the potential drop slot
                    const squareSize = 60; // Size of hotbar slots
                    const spacing = 10;
                    const startX = (this.worldWidth - (5 * (squareSize + spacing))) / 2;
                    const hotbarY = this.worldHeight - hotbarRect.height;

                    const highlightX = startX + (potentialIndex % 5) * (squareSize + spacing);
                    const highlightY = hotbarY + Math.floor(potentialIndex / 5) * (squareSize + spacing);

                    // Draw highlight
                    this.ctx.strokeStyle = 'yellow'; // Highlight color
                    this.ctx.lineWidth = 2;
                    this.ctx.strokeRect(highlightX, highlightY, squareSize, squareSize);
                }
            }
        }
    }

    isInHotbarArea(x, y, hotbarRect) {
        return x >= hotbarRect.x && x <= hotbarRect.x + hotbarRect.width &&
            y >= hotbarRect.y && y <= hotbarRect.y + hotbarRect.height;
    }

    getHotbarRect() {
        const hotbarHeight = 160; // Height of hotbar
        return {
            x: 0,
            y: this.worldHeight - hotbarHeight,
            width: this.worldWidth,
            height: hotbarHeight
        };
    }

    getHotbarIndex(x, y) {
        const squareSize = 60;
        const spacing = 10;
        const startX = (this.worldWidth - (5 * (squareSize + spacing))) / 2;

        for (let i = 0; i < 10; i++) {
            const row = Math.floor(i / 5);
            const col = i % 5;
            const hotbarX = startX + col * (squareSize + spacing);
            const hotbarY = this.worldHeight - squareSize - spacing - (row * (squareSize + spacing));

            if (x >= hotbarX && x <= hotbarX + squareSize &&
                y >= hotbarY && y <= hotbarY + squareSize) {
                return i; // Return the index of the hotbar slot
            }
        }
        return null; // Not in any hotbar slot
    }

    renderWorld() {
        this.ctx.fillStyle = '#55AA55'; // Use the color for the single biome
        this.ctx.fillRect(0, 0, this.worldWidth, this.worldHeight); // Fill the entire canvas
    }


    update() {
        this.player.update(this.keys, { width: this.worldWidth, height: this.worldHeight });
        this.camera.follow(this.player); // Ensure the camera follows the player
        this.droppedPetals.forEach((petal) => {
            petal.update(); // Update the petal if needed
            petal.draw(this.ctx, this.camera); // Draw the petal
        });

        // Check for collisions with the player to collect petals
        this.checkPetalCollection();
        this.waveManager.checkWaveCompletion();
    }

    gameLoop() {
        // Clear the canvas
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);

        // Check if the game is over
        if (this.gameOver) {
            this.renderGameOverScreen();
            return;
        }

        // Update game state
        this.playerState.spacebar = this.keys[' '];
        this.playerState.tKey = this.keys['t'];

        // Update dropped petals with recharging logic
        this.droppedPetals = this.droppedPetals.filter(petal => {
            // Only update non-recharging petals
            if (!petal.isRecharging) {
                petal.update();
            }
            return true; // Keep all petals in the array
        });

        // Update player and camera
        this.update();

        // Render world
        this.renderWorld();
        this.droppedPetals.forEach(petal => {
            petal.draw(this.ctx, this.camera.x, this.camera.y);
        });

        this.renderExperienceBar(); // Add this line
        // Update and draw player petals
        this.petalManager.updatePetals(
            this.playerState,
            this.player.x,
            this.player.y
        );
        this.petalManager.drawPetals(
            this.ctx,
            this.player.x - this.camera.x,
            this.player.y - this.camera.y
        );

        // Draw player
        this.player.draw(this.ctx, this.camera.x, this.camera.y);

        // Update and draw enemies
        this.enemies.forEach(enemy => {
            enemy.update(this.player, this.world, this);
            enemy.draw(this.ctx, this.camera);
        });
        this.waveManager.checkWaveCompletion();

        this.checkPetalCollisions();

        // Render hotbar
        this.renderHotbar();

        this.waveManager.renderCurrentWave(this.ctx);
        this.waveManager.renderProgressBar(this.ctx);

        // Request next animation frame
        requestAnimationFrame(() => this.gameLoop());

    }

    restartGame() {
        this.player.isDead = false;
        this.player.currentHP = this.player.maxHP;
        this.player.x = Math.random() * this.worldWidth; // Random spawn position
        this.player.y = Math.random() * this.worldHeight;

        // Reset other game state variables
        this.enemies = [];
        this.waveManager.currentWave = 0; // Reset wave to 0
        this.waveManager.enemiesSpawned = 0; // Reset enemies spawned
        this.waveManager.startNextWave(); // Start the first wave
        this.gameOver = false; // Set game over to false

        // Restart the game loop
        this.start();
    }

    renderGameOverScreen() {
        // Darken the background
        this.ctx.fillStyle = 'rgba(0, 0, 0, 0.5)';
        this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);

        // Draw "You Died!" message
        this.ctx.fillStyle = 'white';
        this.ctx.font = '40px Arial';
        this.ctx.textAlign = 'center'; // Center the text
        this.ctx.fillText('You Died!', this.worldWidth / 2, this.worldHeight / 2 - 20);

        // Draw restart button
        this.ctx.fillStyle = 'blue';
        this.ctx.fillRect(this.restartButton.x, this.restartButton.y, this.restartButton.width, this.restartButton.height);

        this.ctx.fillStyle = 'white';
        this.ctx.font = '20px Arial';
        this.ctx.textAlign = 'center'; // Center the text
        this.ctx.fillText('Restart', this.restartButton.x + this.restartButton.width / 2, this.restartButton.y + 30);
    }

    renderExperienceBar() {
        const barWidth = 200; // Reduced width
        const barHeight = 25; // Slightly taller
        const x = 20; // Left side
        const y = this.worldHeight - 100; // Above hotbar

        // Gradient background
        const gradient = this.ctx.createLinearGradient(x, y, x + barWidth, y + barHeight);
        gradient.addColorStop(0, '#333');
        gradient.addColorStop(1, '#222');
        this.ctx.fillStyle = gradient;
        this.ctx.fillRect(x, y, barWidth, barHeight);

        // Calculate fill width
        const fillWidth = (this.player.experience / this.player.experienceToNextLevel) * barWidth;

        // Animated gradient fill
        const time = Date.now() * 0.001; // Use time for animation
        const animatedGradient = this.ctx.createLinearGradient(x, y, x + barWidth, y + barHeight);
        animatedGradient.addColorStop(0, `hsl(${Math.sin(time) * 360}, 100%, 60%)`);
        animatedGradient.addColorStop(1, `hsl(${Math.cos(time) * 360}, 100%, 50%)`);
        this.ctx.fillStyle = animatedGradient;
        this.ctx.fillRect(x, y, fillWidth, barHeight);

        // Border
        this.ctx.strokeStyle = 'rgba(255,255,255,0.3)';
        this.ctx.lineWidth = 2;
        this.ctx.strokeRect(x, y, barWidth, barHeight);

        // Level text with shadow and glow
        this.ctx.font = 'bold 20px "Press Start 2P", cursive';
        this.ctx.textAlign = 'left';

        // Shadow effect
        this.ctx.fillStyle = 'rgba(0,0,0,0.5)';
        this.ctx.fillText(`LVL ${this.player.level}`, x, y - 10);

        // Glowing text
        this.ctx.shadowBlur = 10;
        this.ctx.shadowColor = '#00ff00';
        this.ctx.fillStyle = '#00ff00';
        this.ctx.fillText(`LVL ${this.player.level}`, x, y - 10);
        this.ctx.shadowBlur = 0; // Reset shadow

        // XP progression text
        this.ctx.font = '14px "Press Start 2P", cursive';
        this.ctx.fillStyle = 'white';
        this.ctx.fillText(`${Math.floor(this.player.experience)}/${this.player.experienceToNextLevel}`, x, y + barHeight + 20);
    }

    renderHotbar() {
        const hotbarHeight = 160; // Height of hotbar
        const hotbarY = this.worldHeight - hotbarHeight;
        const squareSize = 50; // Slightly smaller to fit more
        const spacing = 5;
        const totalWidth = 10 * (squareSize + spacing);
        const startX = (this.worldWidth - totalWidth) / 2;
        this.renderSellZone();

        // Render first row (active petals)
        for (let i = 0; i < 10; i++) {
            const x = startX + i * (squareSize + spacing);
            this.ctx.fillStyle = '#f0f0f0';
            this.ctx.fillRect(x, hotbarY, squareSize, squareSize);

            // Draw petal if exists in the slot and is in first row
            const petal = this.selectedHotbarSlots[i];
            if (petal) {
                this.ctx.fillStyle = petal.color;
                this.ctx.beginPath();
                this.ctx.arc(x + squareSize / 2, hotbarY + squareSize / 2, 15, 0, Math.PI * 2);
                this.ctx.fill();
            }
        }

        // Render second row (inactive petals)
        const secondRowY = hotbarY + squareSize + spacing;
        for (let i = 10; i < 20; i++) {
            const x = startX + (i - 10) * (squareSize + spacing);
            this.ctx.fillStyle = '#d0d0d0';
            this.ctx.fillRect(x, secondRowY, squareSize, squareSize);

            // Draw petal if exists in the slot, but more transparently
            const petal = this.selectedHotbarSlots[i];
            if (petal) {
                this.ctx.fillStyle = `${petal.color}80`; // More transparent
                this.ctx.beginPath();
                this.ctx.arc(x + squareSize / 2, secondRowY + squareSize / 2, 15, 0, Math.PI * 2);
                this.ctx.fill();
            }
        }

        // Render sell mode overlay
        if (this.sellMode) {
            this.ctx.fillStyle = 'rgba(255, 0, 0, 0.3)'; // Translucent red overlay
            const squareSize = 50;
            const spacing = 5;
            const totalWidth = 10 * (squareSize + spacing);
            const startX = (this.worldWidth - totalWidth) / 2;
            const hotbarY = this.worldHeight - 160;

            // Overlay first two rows of hotbar
            for (let row = 0; row < 2; row++) {
                for (let i = 0; i < 5; i++) {
                    const x = startX + i * (squareSize + spacing);
                    const y = hotbarY + row * (squareSize + spacing);
                    this.ctx.fillRect(x, y, squareSize, squareSize);
                }
            }
        }
    }

    initializeCommonPetals() {
        const petalTypes = [Stinger,Stinger, Stinger, Stinger, Stinger, Stinger,Stinger, Stinger, Stinger, ThirdEyePetal];
        petalTypes.forEach((PetalClass, index) => {
            const petal = new PetalClass('mythic');
            this.player.addPetal(petal); // Add petal to player inventory
            this.petalManager.selectedPetals[index] = petal; // Also add to PetalManager
        });
    }

    start() {
        this.gameLoop();
    }

    checkPetalCollisions() {
        // Use PetalManager's selected petals for player
        const playerPetals = this.petalManager.selectedPetals.filter(petal => petal && !petal.isRecharging);
        this.enemies = this.enemies.filter(enemy => !enemy.isDead);
        this.enemies.forEach(enemy => {
            // Check collision between player petals and this enemy
            playerPetals.forEach(petal => {
                const dx = enemy.x - petal.x;
                const dy = enemy.y - petal.y;
                const distance = Math.sqrt(dx * dx + dy * dy);
                const minDistance = enemy.radius + petal.radius;

                if (distance < minDistance && !petal.isRecharging) {
                    // Deal damage to enemy
                    enemy.takeDamage(petal.damage, this); // Pass the game instance here
                    petal.destroy(); // Destroy the petal after dealing damage
                }
            });

            // Check collision between enemy petals and player
            if (enemy.activePetals) {
                enemy.activePetals.forEach(petal => {
                    const dx = this.player.x - petal.x;
                    const dy = this.player.y - petal.y;
                    const distance = Math.sqrt(dx * dx + dy * dy);
                    const minDistance = this.player.radius + petal.radius;

                    if (distance < minDistance && !petal.isRecharging) {
                        // Deal damage to player
                        this.player.takeDamage(petal.damage, this);
                        petal.destroy(); // Destroy the petal after dealing damage
                    }
                });
            }
        });
    }

    resize() {
        this.worldWidth = window.innerWidth;
        this.worldHeight = window.innerHeight;
        this.tileSize = this.worldWidth / 6; // Update tile size based on new width
        this.world.resize(this.worldWidth, this.worldHeight); // Regenerate world
        this.camera.resize(this.worldWidth, this.worldHeight); // Update camera dimensions
        // Reset player position or other game state if necessary
    }
    renderSellZone() {
        // Render sell zone
        this.ctx.fillStyle = this.isSellingPetal ? '#FF2222' : this.sellZone.color;
        this.ctx.fillRect(
            this.sellZone.x,
            this.sellZone.y,
            this.sellZone.width,
            this.sellZone.height
        );

        // Add text
        this.ctx.fillStyle = 'white';
        this.ctx.font = '12px Arial';
        this.ctx.textAlign = 'center';
        this.ctx.fillText('SELL',
            this.sellZone.x + this.sellZone.width / 2,
            this.sellZone.y + this.sellZone.height / 2
        );
    }

    showSellFeedback(value) {
        // Optional: Create a floating text effect
        const feedbackText = `+${value} XP`;
        const x = this.sellZone.x + this.sellZone.width / 2;
        const y = this.sellZone.y;

        // Create a temporary floating text
        const floatingText = {
            text: feedbackText,
            x: x,
            y: y,
            opacity: 1,
            update() {
                this.y -= 1; // Move up
                this.opacity -= 0.02; // Fade out
            },
            draw(ctx) {
                ctx.fillStyle = `rgba(255, 255, 255, ${this.opacity})`;
                ctx.font = '14px Arial';
                ctx.textAlign = 'center';
                ctx.fillText(this.text, this.x, this.y);
            }
        };

        // You might want to add this to a list of floating texts to render
        // For now, we'll just draw it directly in the gameLoop
        const startTime = Date.now();
        const drawFloatingText = () => {
            const elapsed = Date.now() - startTime;
            if (elapsed < 1000) { // Show for 1 second
                floatingText.update();
                floatingText.draw(this.ctx);
                requestAnimationFrame(drawFloatingText);
            }
        };
        drawFloatingText();
    }
}
