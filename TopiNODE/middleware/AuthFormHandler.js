/*

export class AuthFormHandler {
    constructor(authManager) {
        this.authManager = authManager;
        this.initFormListeners();
    }

    initFormListeners() {
        const loginForm = document.getElementById('login-form');
        const registerForm = document.getElementById('register-form');

        if (loginForm) {
            loginForm.addEventListener('submit', this.handleLogin.bind(this));
        }

        if (registerForm) {
            registerForm.addEventListener('submit', this.handleRegister.bind(this));
        }
    }

    async handleLogin(event) {
        event.preventDefault();
        const username = document.getElementById('login-username').value;
        const password = document.getElementById('login-password').value;

        try {
            await this.authManager.login(username, password);
        } catch (error) {
            this.showError(error.message);
        }
    }

    async handleRegister(event) {
        event.preventDefault();
        const username = document.getElementById('register-username').value;
        const password = document.getElementById('register-password').value;
        const confirmPassword = document.getElementById('confirm-password').value;

        if (password !== confirmPassword) {
            this.showError('Passwords do not match');
            return;
        }

        try {
            await this.authManager.register(username, password);
        } catch (error) {
            this.showError(error.message);
        }
    }

    showError(message) {
        const errorContainer = document.getElementById('error-container');
        errorContainer.textContent = message;
        errorContainer.style.display = 'block';
    }
}

// Инициализация при загрузке страницы
document.addEventListener('DOMContentLoaded', () => {
    const authManager = new AuthManager();

    // Автоматическая проверка входа
    authManager.tryAutoLogin().then(isLoggedIn => {
        if (!isLoggedIn && window.location.pathname !== '/login') {
            window.location.href = '/login';
        }
    });
});

*/