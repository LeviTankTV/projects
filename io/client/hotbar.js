const hotbarRows = 2; // Two rows for the hotbar
let hotbarSlots = [[], []]; // Array to hold the slots for both rows

const petalRarityColors = {
    'Common': 'darkgreen',
    'Unusual': 'orange',
    'Rare': 'aqua',
    'Epic': 'purple',
    'Legendary': 'red',
    'Mythic': 'navy',
    'Ultra': 'deeppink',
    'Topi': 'black'
};

function calculateHotbarSlots(level) {
    let slotsPerRow;
    if (level < 5) {
        slotsPerRow = 5;
    } else if (level < 11) {
        slotsPerRow = 6;
    } else if (level < 16) {
        slotsPerRow = 7;
    } else if (level < 21) {
        slotsPerRow = 8;
    } else if (level < 26) {
        slotsPerRow = 9;
    } else {
        slotsPerRow = 10;
    }

    // Clear previous hotbar slots
    hotbarSlots = [[], []];

    // Create hotbar slots
    for (let row = 0; row < hotbarRows; row++) {
        for (let i = 0; i < slotsPerRow; i++) {
            hotbarSlots[row].push({ item: null }); // Initialize with empty slots
        }
    }
}

function drawMenuHotbars() {
    const menuHotbarContainer = document.getElementById('menuHotbar');
    menuHotbarContainer.innerHTML = ''; // Clear previous hotbar slots
    const user = JSON.parse(localStorage.getItem('user'));
    const username = user.username; // Store username for later use

    // Create hotbar slots
    for (let row = 0; row < hotbarRows; row++) {
        const rowContainer = document.createElement('div');
        rowContainer.style.display = 'flex'; // Flexbox for horizontal layout
        rowContainer.style.justifyContent = 'center'; // Center the row

        for (let i = 0; i < hotbarSlots[row].length; i++) {
            const slot = document.createElement('div');
            slot.classList.add('hotbar-slot'); // Add the base class for styling

            // Check if there is an item in the current slot
            if (hotbarSlots[row][i]) {
                const item = hotbarSlots[row][i]; // Get the item from the hotbar slot
                slot.classList.add(); // Add rarity class for color
                const itemName = document.createElement('div');
                itemName.classList.add('item-name');
                itemName.textContent = item.name; // Set item name
                slot.appendChild(itemName); // Add item name to the slot

                // Make the slot draggable
                slot.setAttribute('draggable', 'true');

                // Add dragstart event listener to the slot
                slot.addEventListener('dragstart', (event) => {
                    event.dataTransfer.setData('text/plain', JSON.stringify(item)); // Set the data to be dragged
                    slot.innerHTML = ''; // Clear the slot content
                    slot.classList.remove(item.rarity); // Remove the rarity class
                    slot.classList.add('empty-slot'); // Add a class to indicate it's empty
                });

                // Add dragend event listener to reset the slot if dropped outside
                slot.addEventListener('dragend', () => {
                    if (slot.innerHTML === '') {
                        slot.classList.remove('empty-slot'); // Remove empty slot class
                        slot.classList.add('default-slot'); // Add a default class if needed
                    }
                });
            } else {
                // If the slot is empty, add an empty class
                slot.classList.add('empty-slot'); // Optionally add a class to indicate it's empty
            }

            // Add drop event listeners to the slot
            slot.addEventListener('dragover', (event) => {
                event.preventDefault(); // Prevent default to allow drop
                event.dataTransfer.dropEffect = 'move'; // Show move cursor
            });

            slot.addEventListener('drop', (event) => {
                event.preventDefault();
                const itemData = JSON.parse(event.dataTransfer.getData('text/plain')); // Get the dropped data
                const item = { name: itemData.petalType, rarity: itemData.rarity }; // Create item object

                // Update the hotbar slot with the item
                slot.classList.remove('empty-slot'); // Remove empty class
                slot.classList.add(item.rarity); // Add rarity class for color
                const itemName = document.createElement('div');
                itemName.classList.add('item-name');
                itemName.textContent = item.name; // Set item name
                slot.innerHTML = ''; // Clear existing content
                slot.appendChild(itemName); // Add item name to the slot
            });

            rowContainer.appendChild(slot); // Add the slot to the row
        }

        menuHotbarContainer.appendChild(rowContainer); // Add the row to the hotbar container
    }
}

function drawHotbars() {
    const hotbarWidth = 40; // Width of each slot
    const hotbarHeight = 40; // Height of each slot
    const spacing = 10; // Space between slots

    const startX = (canvas.width - (hotbarSlots[0].length * (hotbarWidth + spacing) - spacing)) / 2; // Center horizontally
    const startY = canvas.height - (hotbarHeight * 1.2) - 50; // Position above the bottom of the canvas, slightly raised

    // Draw each row of the hotbar
    for (let row = 0; row < hotbarRows; row++) {
        for (let i = 0; i < hotbarSlots[row].length; i++) {
            const x = startX + i * (hotbarWidth + spacing);
            const y = startY + row * (hotbarHeight * 0.8 + spacing); // Adjust the vertical position

            // Determine the slot color based on petals
            // Draw the rounded rectangle for the hotbar slot
            ctx.fillStyle = hotbarSlots[row][i].item ? petalRarityColors[hotbarSlots[row][i].item.rarity] : 'lightgray'; // Use the determined slot color
            drawRoundedRect(ctx, x, y, hotbarWidth, hotbarHeight, 10); // Draw rounded rectangle

            // Draw a border around the slot
            ctx.strokeStyle = 'black'; // Border color
            ctx.lineWidth = 2; // Border width
            ctx.stroke(); // Apply the border

            // Draw item if exists
            if (hotbarSlots[row][i].item) {
                ctx.fillStyle = 'white'; // Text color for item name
                ctx.fillText(hotbarSlots[row][i].item.name, x + 5, y + 25); // Draw item name
            }
        }
    }
}
// Function to draw a rounded rectangle
function drawRoundedRect(ctx, x, y, width, height, radius, fillStyle) {
    ctx.fillStyle = fillStyle; // Set the fill style before drawing
    ctx.beginPath();
    ctx.moveTo(x + radius, y);
    ctx.lineTo(x + width - radius, y);
    ctx.quadraticCurveTo(x + width, y, x + width, y + radius);
    ctx.lineTo(x + width, y + height - radius);
    ctx.quadraticCurveTo(x + width, y + height, x + width - radius, y + height);
    ctx.lineTo(x + radius, y + height);
    ctx.quadraticCurveTo(x, y + height, x, y + height - radius);
    ctx.lineTo(x, y + radius);
    ctx.quadraticCurveTo(x, y, x + radius, y);
    ctx.closePath();
    ctx.fill(); // Fill the rounded rectangle
}



function drawHealthBar(ctx, x, y, width, height, health, maxHealth) {
    const healthPercentage = health / maxHealth;

    // Draw the border
    ctx.fillStyle = 'black'; // Set the border color
    drawRoundedRect(ctx, x - 5, y - 5, width + 10, height + 10, 20, 'black'); // Draw the border rectangle (10px wider and taller)

    // Draw the background
    drawRoundedRect(ctx, x, y, width, height, 10, 'rgba(255, 0, 0, 0.7)'); // Background

    // Draw the health
    drawRoundedRect(ctx, x, y, width * healthPercentage, height, 10, 'rgba(0, 255, 0, 0.7)'); // Health
}

function drawExperienceBar(ctx, x, y, width, height, experience, maxExperience) {
    const experiencePercentage = experience / maxExperience;
    drawRoundedRect(ctx, x, y, width, height, 10, 'rgba(0, 0, 0, 0.5)'); // Background
    drawRoundedRect(ctx, x, y, width * experiencePercentage, height, 10, 'rgba(0, 0, 255, 0.7)'); // Experience
}
