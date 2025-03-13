const app = document.getElementById('app');
const formTitle = document.getElementById('formTitle');
const switchButton = document.getElementById('switchButton');
const userForm = document.getElementById('userForm');
const notification = document.getElementById('notification');
const confirmPasswordGroup = document.getElementById('confirmPasswordGroup');
const submitButton = document.getElementById('SubmitButton');
const loadingScreen = document.getElementById('loadingScreen');
const playButton = document.getElementById('playButton');
const gameMenu = document.getElementById('gameMenu');
const gameCanvas = document.getElementById('gameCanvas');
const settingsIcon = document.getElementById('settingsIcon');
const settingsMenu = document.getElementById('settingsMenu');
const showCurrentZoneCheckbox = document.getElementById('showCurrentZone');
const minimapCheckbox = document.getElementById('minimap');
const updatesIcon = document.getElementById('updatesIcon');
const updatesList = document.getElementById('updatesList');
const updatesContainer = document.getElementById('updatesContainer');
const closeUpdatesButton = document.getElementById('closeUpdates');
const inventoryIcon = document.getElementById('inventoryIcon');
const inventoryList = document.getElementById('inventoryList');
const inventoryItems = document.getElementById('inventoryItems');
const closeInventoryButton = document.getElementById('closeInventory');
const gameExit = document.getElementById('gameExit');

const updates = [
    "БУ! Испугался? Не бойся."
];


playButton.addEventListener("click", () => {
    loadingScreen.style.display = 'flex';

    // Get the username from the profile data
    const profileUsername = document.getElementById('profileUsername').textContent;
    const profileLevel = document.getElementById('profileLevel').textContent;
    const profileExperience = document.getElementById('profileExperience').textContent;

    initializeGameClient(profileUsername, profileLevel, profileExperience);
});

function initializeGameClient(username, level, experience) {
    // Deinitialize any existing game state before starting a new one

    // Create the script element for the client.js file
    const script = document.createElement('script');
    script.src = 'index.js'; // Path to your client.js file
    script.onload = () => {
        // Delay before starting the game
        setTimeout(() => {
            // Hide the loading screen after the client is initialized
            loadingScreen.style.display = 'none';
            gameMenu.style.display = 'none';
            gameCanvas.style.display = 'block'; // Show the game canvas

            // Initialize the game with the username
            startGame(username, level, experience);
        }, 2000); // Delay for 2000 milliseconds (2 seconds)
    };
    document.body.appendChild(script);
}
let isRegistering = true;

// Check local storage for user state
if (localStorage.getItem('user')) {
    fetchUserProfile();
}

// Handle form submission
userForm.addEventListener('submit', async (event) => {
    event.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    if (username.length < 5 || username.length > 15) {
        showNotification('Username must be between 5 and 15 characters');
        return;
    }

    if (isRegistering) {
        const confirmPassword = document.getElementById('confirmPassword').value;
        if (password !== confirmPassword) {
            showNotification("Passwords do not match!");
            return;
        }

        // Register user
        const response = await fetch('/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });
        const data = await response.json();
        showNotification(data.message || data.error);
    } else {
        // Login user
        const response = await fetch('/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });
        const data = await response.json();
        if (data.user) {
            localStorage.setItem('user', JSON.stringify(data.user));
            fetchUserProfile(); // Fetch user profile after login
        } else {
            showNotification(data.error);
        }
    }
});

// Function to fetch user profile
async function fetchUserProfile() {
    const user = JSON.parse(localStorage.getItem('user'));
    const response = await fetch(`/api/profile/${user.username}`);
    const profileData = await response.json();

    // Hide the registration/login form
    app.style.display = 'none'; // Hides the app container

    // Display user profile in the game menu
    displayGameMenu(profileData);
}

// Function to display the game menu
function displayGameMenu(profileData) {
    // Populate the game menu with user data
    document.getElementById('profileUsername').textContent = profileData.username;
    document.getElementById('profileLevel').textContent = profileData.level;
    document.getElementById('profileExperience').textContent = profileData.experience;

    // Update the progress bar based on the player's experience
    updateProgressBar(profileData.level, profileData.experience);

    calculateHotbarSlots(profileData.level);

    drawMenuHotbars();

    // Show the game menu
//    document.getElementById('craftingIcon').style.display = 'block';
    document.getElementById('hotbarText').style.display = 'block';
    document.getElementById('inventoryContainer').style.display = 'block';
    document.getElementById('levelContainer').style.display = 'block';
    document.getElementById('gameMenu').style.display = 'block';
    document.getElementById('settingsContainer').style.display = 'block';
}

// Function to update the progress bar
function updateProgressBar(level, experience) {
    const progressBar = document.getElementById('progress');
    const profileExperience = document.getElementById('profileExperience');
    const experienceNeeded = document.getElementById('experienceNeeded');

    const experienceRequired = level * level * 100; // Exponential formula
    const progressPercentage = (experience / experienceRequired) * 100;

    profileExperience.textContent = experience; // Current experience
    experienceNeeded.textContent = experienceRequired; // Experience needed

    // Update the progress bar width
    progressBar.style.width = `${progressPercentage}%`;
}


// Switch between registration and login
switchButton.addEventListener('click', () => {
    isRegistering = !isRegistering;
    formTitle.textContent = isRegistering ? 'Register' : 'Login';
    switchButton.textContent = isRegistering ? "Already have an account? Switch to Login" : "Don't have an account? Switch to Register";
    submitButton.textContent = isRegistering ? "Register" : 'Login';
    userForm.reset(); // Clear the form

    // Toggle visibility of confirm password field
    confirmPasswordGroup.style.display = isRegistering ? 'block' : 'none';

    const confirmPasswordInput = document.getElementById('confirmPassword');
    if (isRegistering) {
        confirmPasswordInput.setAttribute('required', 'required'); // Set required when registering
    } else {
        confirmPasswordInput.removeAttribute('required'); // Remove required when logging in
    }
});

// Show notification
function showNotification(message) {
    notification.textContent = message;
    notification.classList.add('notification-success');
    setTimeout(() => {
        notification.textContent = '';
        notification.classList.remove('notification-success');
    }, 3000);
}

settingsIcon.addEventListener('click', () => {
    toggleSettingsMenu();

    // Load the current settings from local storage
    const showCurrentZone = localStorage.getItem('ShowCurrentZone') === 'true';
    const minimap = localStorage.getItem('ShowMinimap') === 'true';

    showCurrentZoneCheckbox.checked = showCurrentZone;
    minimapCheckbox.checked = minimap;
});

// Event listener for "Show Current Zone" checkbox
showCurrentZoneCheckbox.addEventListener('change', (event) => {
    const isChecked = event.target.checked;
    localStorage.setItem('ShowCurrentZone', isChecked); // Store the state in local storage
});

// Event listener for "Show Minimap" checkbox
minimapCheckbox.addEventListener('change', (event) => {
    const isChecked = event.target.checked;
    localStorage.setItem('ShowMinimap', isChecked); // Store the state in local storage
});

// Function to toggle the settings menu
function toggleSettingsMenu() {
    if (settingsMenu.style.display === 'none' || settingsMenu.style.display === '') {
        settingsMenu.style.display = 'block'; // Show the settings menu
    } else {
        settingsMenu.style.display = 'none'; // Hide the settings menu
    }
}

// Close settings menu when clicking outside of it
window.addEventListener('click', function(event) {
    if (event.target !== settingsIcon && !settingsMenu.contains(event.target)) {
        settingsMenu.style.display = 'none'; // Hide the settings menu
    }
});

function displayUpdates() {
    updatesContainer.innerHTML = ""; // Clear previous updates
    updates.forEach(update => {
        const li = document.createElement('li');
        li.textContent = update; // Add update text
        li.classList.add('update-item'); // Add the CSS class to the list item
        updatesContainer.appendChild(li); // Append to updates list
    });
    updatesList.style.display = 'block'; // Show updates list
}

// Event listener for updates icon click
updatesIcon.addEventListener('click', () => {
    displayUpdates(); // Display updates when icon is clicked
});

// Event listener for close button
closeUpdatesButton.addEventListener('click', () => {
    updatesList.style.display = 'none'; // Hide updates list
});

const showMobRarities = localStorage.getItem('ShowMobRarities') === 'true';
document.getElementById('showMobRarities').checked = showMobRarities;

// Event listener for "Show Mob Rarities" checkbox
document.getElementById('showMobRarities').addEventListener('change', (event) => {
    const isChecked = event.target.checked;
    localStorage.setItem('ShowMobRarities', isChecked); // Store the state in local storage
});

inventoryIcon.addEventListener('click', async () => {
    // Fetch user's inventory
    const user = JSON.parse(localStorage.getItem('user'));
    const response = await fetch(`/api/users/${user.username}/inventory`);
    const inventoryData = await response.json();

    // Clear previous inventory items
    inventoryItems.innerHTML = '';

    // Group inventory items by type and rarity
    const groupedInventory = inventoryData.reduce((acc, petal) => {
        const key = `${petal.petalType}-${petal.rarity}`;
        if (!acc[key]) {
            acc[key] = { petalType: petal.petalType, rarity: petal.rarity, count: 0 };
        }
        acc[key].count += 1;
        return acc;
    }, {});

    // Populate inventory list
    Object.values(groupedInventory).forEach(({ petalType, rarity, count }) => {
        const rarityColors = {
            'common': 'darkgreen',
            'unusual': 'orange',
            'rare': 'aqua',
            'epic': 'purple',
            'legendary': 'red',
            'mythic': 'navy',
            'ultra': 'deeppink',
            'Topi': 'black'
        };

        const petalDiv = document.createElement('div');
        petalDiv.style.backgroundColor = rarityColors[rarity] || 'gray'; // Default to gray if rarity is unknown
        petalDiv.style.color = 'white'; // Text color
        petalDiv.style.position = 'relative'; // For positioning the count
        petalDiv.style.padding = '10px';
        petalDiv.style.margin = '5px';
        petalDiv.style.borderRadius = '5px';
        petalDiv.style.display = 'inline-block'; // Keep items in a row
        petalDiv.textContent = petalType;
        petalDiv.draggable = true; // Make the item draggable

        // Only show count if greater than 1
        if (count > 1) {
            const countDiv = document.createElement('div');
            countDiv.textContent = `x${count}`;
            countDiv.style.position = 'absolute';
            countDiv.style.top = '-3px';
            countDiv.style.right = '1px';
            countDiv.style.fontSize = '12px';
            countDiv.style.fontFamily = 'Comic Sans MS, sans-serif';
            countDiv.style.color = 'white'; // Count color
            petalDiv.appendChild(countDiv);
        }

        // Add drag event listeners
        petalDiv.addEventListener('dragstart', (event) => {
            event.dataTransfer.setData('text/plain', JSON.stringify({ petalType, rarity }));
            event.dataTransfer.effectAllowed = 'move';
        });

        inventoryItems.appendChild(petalDiv);
    });

    // Toggle visibility of the inventory list
    inventoryList.style.display = inventoryList.style.display === 'none' ? 'block' : 'none';
});

// Close inventory list
closeInventoryButton.addEventListener('click', () => {
    inventoryList.style.display = 'none'; // Hide the inventory list
});
