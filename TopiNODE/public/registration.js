class RegistrationManager {
    constructor() {
        this.form = document.getElementById('registration-form');
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
        this.form.addEventListener('submit', this.handleRegistration.bind(this));
    }

    showNotification(message, type = 'success') {
        this.notificationElement.textContent = message;
        this.notificationElement.style.display = 'block';
        this.notificationElement.className = `notification notification-${type}`;

        setTimeout(() => {
            this.notificationElement.style.display = 'none';
        }, 5000);
    }

    async handleRegistration(event) {
        event.preventDefault();

        const username = document.getElementById('register-username').value;
        const password = document.getElementById('register-password').value;
        const confirmPassword = document.getElementById('confirm-password').value;

        // Добавим клиентскую валидацию
        if (password !== confirmPassword) {
            this.showNotification('Passwords do not match', 'error');
            return;
        }

        if (username.length < 3) {
            this.showNotification('Username must be at least 3 characters', 'error');
            return;
        }

        if (password.length < 6) {
            this.showNotification('Password must be at least 6 characters', 'error');
            return;
        }

        try {
            const response = await fetch(`${this.baseURL}/api/register`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest' // Для CSRF защиты
                },
                credentials: 'include',
                body: JSON.stringify({
                    username: username.trim(),
                    password: password
                })
            });

            const data = await response.json();

            if (response.ok) {
                this.showNotification(
                    data.message || 'Registration successful!',
                    'success'
                );

                // Сохраним токен в localStorage
                localStorage.setItem('authToken', data.token);

                // Перенаправление после регистрации
                setTimeout(() => {
                    window.location.href = 'login.html';
                }, 2000);
            } else {
                this.showNotification(
                    data.message || 'Registration failed',
                    'error'
                );
            }
        } catch (error) {
            console.error('Registration error:', error);
            this.showNotification(
                'Network error. Please try again.',
                'error'
            );
        }
    }
}

// Initialize registration manager
document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('authToken');
    if (token) {
        window.location.href = '/game/MainMenu.html';
    }
    new RegistrationManager();
});
