export default class AuthRoutes {
    constructor(app, authController) {
        this.app = app;
        this.authController = authController;
    }

    setupRoutes() {
        // Регистрация
        this.app.post('/api/register',
            this.authController.registerUser.bind(this.authController)
        );

        this.app.use((req, res, next) => {
            console.log(`[${new Date().toISOString()}] ${req.method} ${req.path}`);
            next();
        });

        // Вход
        this.app.post('/api/login',
            this.authController.loginUser.bind(this.authController)
        );

        // Выход
        this.app.post('/api/logout',
            this.authController.authenticateToken.bind(this.authController),
            this.handleLogout
        );

        // Проверка токена
        this.app.post('/api/verify-token',
            this.authController.authenticateToken.bind(this.authController),
            this.handleTokenVerification
        );

        // Получение информации о пользователе
        this.app.get('/api/me',
            this.authController.authenticateToken.bind(this.authController),
            this.handleGetCurrentUser
        );

        // Управление устройствами
        this.app.get('/api/devices',
            this.authController.authenticateToken.bind(this.authController),
            this.handleGetDevices
        );

        this.app.delete('/api/devices/:deviceToken',
            this.authController.authenticateToken.bind(this.authController),
            this.handleRemoveDevice
        );

        this.app.get('/api/check-game-access',
            this.authController.validateGameAccess.bind(this.authController)
        );

        this.app.get('/api/user/profile',
            this.authController.authenticateToken.bind(this.authController),
            this.authController.getUserProfile.bind(this.authController)
        );

        this.app.use((req, res) => {
            res.status(404).json({
                message: 'Route not found',
                path: req.path
            });
        });

        this.app.use((err, req, res) => {
            console.error(err.stack);
            res.status(500).json({
                message: 'Something went wrong!',
                error: process.env.NODE_ENV === 'production' ? {} : err.message
            });
        });
    }

    handleGetDevices(req, res) {
        try {
            // Возвращаем список активных устройств пользователя
            const activeDevices = req.user.devices.filter(device =>
                device.isActive && device.expiresAt > new Date()
            );

            res.json(activeDevices.map(device => ({
                deviceToken: device.deviceToken,
                ip: device.ip,
                userAgent: device.userAgent,
                lastLogin: device.lastLogin,
                expiresAt: device.expiresAt
            })));
        } catch (error) {
            res.status(500).json({ message: 'Error fetching devices' });
        }
    }

    handleRemoveDevice(req, res) {
        try {
            const { deviceToken } = req.params;

            // Удаляем конкретное устройство
            req.user.devices = req.user.devices.filter(
                device => device.deviceToken !== deviceToken
            );

            req.user.save();
            res.json({ message: 'Device removed successfully' });
        } catch (error) {
            res.status(500).json({ message: 'Error removing device' });
        }
    }

    handleLogout(req, res) {
        try {
            // Деактивируем текущее устройство
            const currentDevice = req.user.devices.find(
                device => device.deviceToken === req.deviceToken
            );

            if (currentDevice) {
                currentDevice.isActive = false;
            }

            req.user.save();

            // Очищаем куки
            res.clearCookie('authToken');
            res.json({ message: 'Logged out successfully' });
        } catch (error) {
            res.status(500).json({ message: 'Logout failed' });
        }
    }

    handleTokenVerification(req, res) {
        // Если мы здесь, значит токен уже прошел проверку в middleware
        res.json({
            message: 'Token is valid',
            user: {
                id: req.user._id,
                username: req.user.username
            }
        });
    }

    handleGetCurrentUser(req, res) {
        res.json({
            id: req.user._id,
            username: req.user.username,
            lastLogin: req.user.lastLogin,
            registeredIp: req.user.registeredIp
        });
    }
}
