export default class IndexManager {
    constructor(baseURL) {
        this.baseURL = baseURL;
    }

    static async create() {
        const instance = new IndexManager(this.getBaseURL());
        await instance.init(); // Await the init method to ensure promises are handled
        return instance; // Return the instance after initialization
    }

    static getBaseURL() {
        const protocol = 'https:';
        const host = window.location.hostname;
        const port = window.location.port;
        return `${protocol}//${host}:${port}`;
    }

    async init() {
        await this.checkAuthToken(); // Await the checkAuthToken promise
    }

    async checkAuthToken() {
        const token = localStorage.getItem('authToken');
        if (token) {
            await this.verifyToken(token);
        } else {
            window.location.href = './login.html';
        }
    }

    async verifyToken(token) {
        try {
            const response = await fetch(`${this.baseURL}/api/verify-token`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            });
            if (response.ok) {
                console.log('Token is valid, redirecting to Main Menu');
                window.location.href = '/game/MainMenu.html';
            } else {
                console.log('Token is invalid or expired, redirecting to login');
                window.location.href = './login.html';
            }
        } catch (error) {
            console.error('Error verifying token:', error);
            window.location.href = './login.html';
        }
    }
}

// Initialize IndexManager on page load
document.addEventListener('DOMContentLoaded', async () => {
    const token = localStorage.getItem('authToken');
    if (token) {
        window.location.href = '/game/MainMenu.html';
    }
    try {
        await IndexManager.create(); // Use the static method to create and initialize
    } catch (error) {
        console.error('Error initializing IndexManager:', error);
    }
});
