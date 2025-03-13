export default class LoginManager {
    constructor() {
        this.form = document.getElementById('login-form');
        this.notificationElement = document.getElementById('notification');
        this.baseURL = this.getBaseURL();

        this.initEventListeners();
    }

    getBaseURL() {
        const protocol = 'https:';
        const host = window.location.hostname;
        const port = window.location.port || '3000';
        return `${protocol}//${host}:${port}`;
    }

    initEventListeners() {
        this.form.addEventListener('submit', this.handleLogin.bind(this));
    }

    showNotification(message, type = 'success') {
        this.notificationElement.textContent = message;
        this.notificationElement.style.display = 'block';
        this.notificationElement.className = `notification notification-${type}`;

        setTimeout(() => {
            this.notificationElement.style.display = 'none';
        }, 5000);
    }

    async handleLogin(event) {
        event.preventDefault();

        const username = document.getElementById('login-username').value;
        const password = document.getElementById('login-password').value;

        try {
            const response = await fetch(`${this.baseURL}/api/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify({ username, password })
            });

            const data = await response.json();

            if (response.ok) {
                // Сохраняем токен
                localStorage.setItem('authToken', data.token);

                // Редирект на MainMenu.html
                window.location.href = '/game/MainMenu.html';
            } else {
                this.showNotification(data.message || 'Login failed', 'error');
            }
        } catch (error) {
            console.error('Login error:', error);
            this.showNotification('Network error. Please try again.', 'error');
        }
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('authToken');
    if (token) {
        window.location.href = '/game/MainMenu.html';
    }
    new LoginManager();
});

