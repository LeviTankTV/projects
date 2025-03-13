

const craftingMenu = document.getElementById('crafting-menu');
const craftingSquares = [
    document.getElementById('craft-square-1'),
    document.getElementById('craft-square-2'),
    document.getElementById('craft-square-3'),
    document.getElementById('craft-square-4'),
    document.getElementById('craft-square-5')
];
const craftButton = document.getElementById('craft-button');
const successChanceDisplay = document.getElementById('success-chance');

// Sample inventory for demonstration
const inventory = {
    petals: 10, // Number of petals available
};

// Function to toggle crafting menu visibility
function toggleCraftingMenu() {
    if (craftingMenu.style.display === 'none' || craftingMenu.style.display === '') {
        craftingMenu.style.display = 'block';
        resetCraftingSquares();
        disableOtherIcons('crafting-icon'); // Disable other icons except the crafting icon
    } else {
        craftingMenu.style.display = 'none';
        disableOtherIcons(); // Re-enable all icons
    }
}

// Reset crafting squares
function resetCraftingSquares() {
    craftingSquares.forEach(square => {
        square.style.backgroundColor = '#f0f0f0'; // Reset background
        square.dataset.item = ''; // Clear item data
    });
    craftButton.disabled = true; // Disable the crafting button
    successChanceDisplay.textContent = 'Place 5 petals of same rarity to craft!'; // Reset success chance
}

// Function to handle square clicks
craftingSquares.forEach(square => {
    square.addEventListener('click', () => {
        const item = 'petals'; // For simplicity, we are using petals
        if (inventory[item] >= 5) {
            square.style.backgroundColor = '#6a11cb'; // Change color to indicate selection
            square.dataset.item = item; // Store the item in the square
            checkCraftingReady(); // Check if crafting can be initiated
        }
    });
});

// Check if crafting is ready
function checkCraftingReady() {
    const selectedItems = craftingSquares.map(square => square.dataset.item).filter(item => item !== '');
    if (selectedItems.length === 5 && selectedItems.every(item => item === 'petals')) {
        craftButton.disabled = false; // Enable crafting button
        successChanceDisplay.textContent = 'Chance of Success: 75%'; // Set success chance
    } else {
        craftButton.disabled = true; // Disable crafting button
        successChanceDisplay.textContent = 'Chance of Success: 0%'; // Reset success chance
    }
}

// Crafting functionality
craftButton.addEventListener('click', () => {
    const item = 'petals';
    if (inventory[item] >= 5) {
        inventory[item] -= 5; // Deduct 5 petals from inventory
        alert('Crafted successfully!'); // Notify user
        resetCraftingSquares(); // Reset squares after crafting
        updateInventoryDisplay(); // Update inventory display
    } else {
        alert('Not enough petals to craft!'); // Notify user
    }
});

// Function to update inventory display
function updateInventoryDisplay() {
    const inventoryContent = document.getElementById('inventory-content');
    inventoryContent.innerHTML = ''; // Clear current inventory display

    for (const [item, count] of Object.entries(inventory)) {
        for (let i = 0; i < count; i++) {
            const itemElement = document.createElement('li');
            itemElement.textContent = item;
            itemElement.className = 'inventory-square'; // Make it clickable
            itemElement.addEventListener('click', () => handleInventoryItemClick(item));
            inventoryContent.appendChild(itemElement);
        }
    }
}

// Function to handle inventory item clicks
function handleInventoryItemClick(item) {
    const emptySquare = craftingSquares.find(square => square.dataset.item === '');
    if (emptySquare) {
        emptySquare.style.backgroundColor = '#6a11cb'; // Change color to indicate selection
        emptySquare.dataset.item = item; // Store the item in the square
        checkCraftingReady(); // Check if crafting can be initiated
    }
}

// Show crafting menu on icon click
document.getElementById('crafting-icon').addEventListener('click', toggleCraftingMenu);

// Initialize inventory display on page load
document.addEventListener('DOMContentLoaded', updateInventoryDisplay);

const galleryIcon = document.getElementById('gallery-icon');
const gallery = document.getElementById('gallery');
const galleryContent = document.getElementById('gallery-content');
const closeGalleryButton = document.getElementById('close-gallery');

// Function to toggle gallery visibility
function toggleGallery() {
    if (gallery.style.display === 'none' || gallery.style.display === '') {
        const hasDefeatedMobs = true; // Change this to true for testing

        galleryContent.innerHTML = ''; // Clear previous content

        if (!hasDefeatedMobs) {
            // Show question mark if no mobs defeated
            const questionMarkSquare = document.createElement('div');
            questionMarkSquare.className = 'gallery-square question-mark';
            questionMarkSquare.textContent = '?';
            galleryContent.appendChild(questionMarkSquare);
        } else {
            // Add rows of squares (10 rows)
            for (let i = 0; i < 10; i++) {
                for (let j = 0; j < 8; j++) {
                    const square = document.createElement('div');
                    square.className = 'gallery-square';
                    square.textContent = `Rarity ${j + 1}`; // Placeholder text
                    galleryContent.appendChild(square);
                }
            }
        }

        gallery.style.display = 'block'; // Show gallery
        disableOtherIcons('gallery-icon'); // Disable other icons except the gallery icon
    } else {
        gallery.style.display = 'none'; // Hide gallery
        disableOtherIcons(); // Re-enable all icons
    }
}

// Function to close the gallery
function closeGallery() {
    gallery.style.display = 'none'; // Hide gallery
    disableOtherIcons(); // Re-enable all icons
}

// Add click event listener to the gallery icon
galleryIcon.addEventListener('click', toggleGallery);
// Add click event listener to the close button
closeGalleryButton.addEventListener('click', closeGallery);

const inventoryIcon = document.getElementById('inventory-icon');
const inventoryList = document.getElementById('inventory-list');

// Function to toggle inventory visibility
function toggleInventory() {
    if (inventoryList.style.display === 'none' || inventoryList.style.display === '') {
        inventoryList.style.display = 'block'; // Show inventory
        disableOtherIcons('inventory-icon'); // Disable other icons except the inventory icon
    } else {
        inventoryList.style.display = 'none'; // Hide inventory
        disableOtherIcons(); // Re-enable all icons
    }
}

// Add click event listener to the inventory icon
inventoryIcon.addEventListener('click', toggleInventory);

const settingsList = document.getElementById('settings-list');
const DevToggle = document.getElementById('In-Developing-toggle');

// Function to toggle the visibility of the settings menu
function toggleSettingsMenu() {
    if (settingsList.style.display === 'none' || settingsList.style.display === '') {
        settingsList.style.display = 'block';
        disableOtherIcons('settings-icon'); // Disable other icons except the settings icon
    } else {
        settingsList.style.display = 'none';
        disableOtherIcons(); // Re-enable all icons
    }
}

// Event listeners for the toggle buttons
DevToggle.addEventListener('change', () => {
    // TODO: Add code to handle the toggle
});

// Show settings menu on icon click
document.getElementById('settings-icon').addEventListener('click', toggleSettingsMenu);

document.addEventListener('DOMContentLoaded', () => {
    const updatesList = document.getElementById('updates-list');
    const updatesContent = document.getElementById('updates-content');

    // Sample updates
    const updates = [
        'Version 0.0: ',
        '- This Starts here.',
        'Version 0.1: ',
        '- Added Authentication System.',
        '- Added some interactions in Game Menu.'
    ];
    function toggleUpdatesIcon() {
        if (updatesList.style.display === 'none' || updatesList.style.display === '') {
            updatesList.style.display = 'block'; // Show updates
            disableOtherIcons('updates-icon'); // Disable other icons except the updates icon
        } else {
            updatesList.style.display = 'none'; // Hide updates
            disableOtherIcons(); // Re-enable all icons
        }

        // Clear existing updates
        updatesContent.innerHTML = '';

        // Populate the updates list
        updates.forEach(update => {
            const li = document.createElement('li');
            li.textContent = update;
            updatesContent.appendChild(li);
        });
    }

    document.getElementById('updates-icon').addEventListener('click', toggleUpdatesIcon);

    updatesList.style.display = 'none'; // Hide updates list on page load
});

// Function to disable other icons
function disableOtherIcons(activeIconId) {
    const icons = [
        'crafting-icon',
        'gallery-icon',
        'inventory-icon',
        'settings-icon',
        'updates-icon',
    ];

    icons.forEach(iconId => {
        const icon = document.getElementById(iconId);
        if (activeIconId && iconId !== activeIconId) {
            icon.style.pointerEvents = 'none';
            icon.style.opacity = '0.5'; // Change opacity for visual feedback
        } else {
            icon.style.pointerEvents = 'auto'; // Keep the active icon enabled
            icon.style.opacity = '1'; // Reset opacity for the active icon
        }
    });
}

const createServerButton = document.getElementById('create-server');
const joinServerButton = document.getElementById('join-server');

// Function to create a server (you can implement your logic here)
createServerButton.addEventListener('click', () => {
    alert('Create Server functionality goes here.');
});

// Function to join a server (you can implement your logic here)
joinServerButton.addEventListener('click', () => {
    alert('Join Server functionality goes here.');
});

const chooseServerButton = document.getElementById('start-game');

// Function to show the server menu
chooseServerButton.addEventListener('click', () => {
    const mainMenu = document.getElementById('main-menu');
    const serverMenu = document.getElementById('server-menu');

    // Start fading out the main menu
    mainMenu.classList.remove('show');
    mainMenu.classList.add('fade');

    // Wait for the fade-out transition to complete before showing the server menu
    setTimeout(() => {
        mainMenu.style.display = 'none'; // Hide main menu after fade out
        serverMenu.style.display = 'block'; // Show server menu
        serverMenu.classList.add('fade');
        serverMenu.classList.add('show'); // Fade in the server menu
        updateServerList(); // Call function to update server list
    }, 500); // Match this duration with your CSS transition duration
});

// Function to update the server list (you can replace this with actual server data)
function updateServerList() {
    const serverList = document.getElementById('server-list');
    serverList.innerHTML = ''; // Clear existing server list

    // Sample active servers (replace this with actual server data)
    const activeServers = ['Main Server', 'Server 1', 'Server 2'];

    activeServers.forEach(server => {
        const li = document.createElement('li');
        li.textContent = server; // Display server name

        // Check if the server is the main server
        if (server === 'Main Server') {
            li.className = 'main-server'; // Add a specific class for styling
            li.addEventListener('click', () => {
                window.location.href = 'MainGame.html'; // Navigate to the game session
            });
        } else {
            li.addEventListener('click', () => {
                alert(`Joining ${server}...`); // Placeholder for other servers
            });
        }

        serverList.appendChild(li);
    });
}

const returnToMenuButton = document.getElementById('return-to-menu');

// Function to return to the main menu
returnToMenuButton.addEventListener('click', () => {
    const serverMenu = document.getElementById('server-menu');
    const mainMenu = document.getElementById('main-menu');

    // Start fading out the server menu
    serverMenu.classList.remove('show');
    serverMenu.classList.add('fade');

    // Wait for the fade-out transition to complete before showing the main menu
    setTimeout(() => {
        serverMenu.style.display = 'none'; // Hide server menu after fade out
        mainMenu.style.display = 'block'; // Show main menu
        mainMenu.classList.add('fade');
        mainMenu.classList.add('show'); // Fade in the main menu
    }, 800); // Match this duration with your CSS transition duration
});
async function fetchUserProfile() {
    const token = localStorage.getItem('authToken');
    const response = await fetch('/api/user/profile', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });

    if (response.ok) {
        const profile = await response.json();
        document.getElementById('username-display').textContent = `Username: ${profile.username}`;
        document.getElementById('level-display').textContent = `Level: ${profile.level}`;

        // Обновляем полосу опыта
        const experienceBar = document.getElementById('experience-fill');
        const experiencePercentage = (profile.experience / (profile.level * 100)) * 100; // Пример расчета
        experienceBar.style.width = `${experiencePercentage}%`;
    } else {
        console.error('Failed to fetch user profile');
        const token = localStorage.getItem('authToken');
        const data = await response.json();
        console.log('Profile Data:', data);
        console.error('Token:', token);
    }
}

// Вызов функции после загрузки главного меню
document.addEventListener('DOMContentLoaded', fetchUserProfile);

