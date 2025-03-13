import express from 'express';
import https from 'https';
import fs from 'fs';
import {Server} from 'socket.io';
import mongoose from 'mongoose';
import path from 'path';
import { fileURLToPath } from 'url';
import cors from 'cors';
import cookieParser from 'cookie-parser';
import helmet from 'helmet'; // для безопасности

import AuthController from './middleware/AuthController.js';
import AuthRoutes from './middleware/AuthRoutes.js';

// Get __dirname equivalent in ES modules
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

class GameServer {
    constructor() {
        const sslOptions = {
            key: fs.readFileSync(path.join(__dirname, 'ssl/localhost+3-key.pem')),
            cert: fs.readFileSync(path.join(__dirname, 'ssl/localhost+3.pem'))
        };
        this.app = express();
        this.server = https.createServer(sslOptions, this.app);
        this.io = new Server(this.server, {
            cors: {
                origin: ["https://localhost:3000", "https://localhost:5000"], // Укажите ваши домены
                methods: ["GET", "POST"],
                allowedHeaders: ["Content-Type", "Authorization"],
                credentials: true,
                secure: true,
                rejectUnauthorized: false

            }
        });
        this.PORT = process.env.PORT || 3000;
        this.SECRET_KEY = process.env.SECRET_KEY || 'your_secret_key';

        this.connectedPlayersByIp = new Map(); // New map to track connections by IP
        this.connectedPlayers = new Map(); // Existing map for player connections

        this.initializeMiddleware();

        // Инициализация контроллеров и маршрутов
        this.authController = new AuthController(this.SECRET_KEY);
        this.authRoutes = new AuthRoutes(this.app, this.authController);
        this.setupRoutes();
        this.setupSocketEvents();
    }

    initializeMiddleware() {
        // Расширенная конфигурация CORS
        this.app.use(cors({
            origin: [
                'https://localhost:3000',
                'https://localhost:5000',
            ],
            methods: ['GET', 'POST', 'OPTIONS'],
            allowedHeaders: [
                'Content-Type',
                'Authorization',
                'Access-Control-Allow-Origin'
            ],
            credentials: true
        }));
        // Гибкая настройка Content Security Policy
        this.app.use(helmet.contentSecurityPolicy({
            directives: {
                defaultSrc: ["'self'", 'https:'],
                scriptSrc: ["'self'", "'unsafe-inline'", 'https:'],
                styleSrc: ["'self'", "'unsafe-inline'", 'https:'],
                formAction: [
                    "'self'",
                    'https://localhost:3000',
                ],
                connectSrc: [
                    "'self'",
                    'https://localhost:3000',
                ]
            }
        }));
        this.app.use(express.static(path.join(__dirname, 'public'), {
            setHeaders: (res, filePath) => {
                // Явно указываем заголовки для статических файлов
                if (path.extname(filePath).toLowerCase() === '.png') {
                    res.setHeader('Content-Type', 'image/png');
                }

                // Разрешаем кросс-доменные запросы для статики
                res.setHeader('Access-Control-Allow-Origin', '*');
            },
            // Кэширование статических файлов
            maxAge: '1d'
        }));
        // Дополнительные заголовки безопасности
        this.app.use((req, res, next) => {
            res.setHeader('Strict-Transport-Security', 'max-age=31536000; includeSubDomains');
            res.setHeader('X-Content-Type-Options', 'nosniff');
            res.setHeader('X-Frame-Options', 'SAMEORIGIN');
            next();
        });

        this.app.use((req, res, next) => {
            //console.log('Static file request:', {
           //     path: req.path,
            //    originalUrl: req.originalUrl,
            //    ip: req.ip,
            //    headers: req.headers
           // });
            next();
        });
        // Остальная middleware без изменений
        this.app.use(helmet());
        this.app.use(express.json());
        this.app.use(express.urlencoded({ extended: true }));
        this.app.use(cookieParser());
        this.app.use(express.static(path.join(__dirname, 'public')));
    }

    async connectDatabase() {
        try {
            await mongoose.connect('mongodb://localhost:27017/gameverse', {
            });
            console.log('MongoDB connected successfully');
        } catch (error) {
            console.error('MongoDB connection error:', error);
            process.exit(1);
        }
    }

    setupRoutes() {
        // Используем маршруты из AuthRoutes
        this.authRoutes.setupRoutes();

        this.app.get('/game/MainGame.html', (req, res) => {
            res.sendFile(path.join(__dirname, 'public', 'game', 'MainGame.html'));
        });

        this.app.get('/game/MainMenu.html', (req, res) => {
            res.sendFile(path.join(__dirname, 'public', 'game', 'MainMenu.html'));
        });

        this.app.get('/login.html', (req, res) => {
            res.sendFile(path.join(__dirname, 'public', 'login.html'));
        });

        this.app.get('/registration.html', (req, res) => {
            res.sendFile(path.join(__dirname, 'public', 'registration.html'));
        });

        // Catch-all route for single-page application
        this.app.get('*', (req, res) => {
            res.sendFile(path.join(__dirname, 'public', 'index.html'));
        });
    }

    setupSocketEvents() {
        this.io.use(async (socket, next) => {
            try {
                const token = socket.handshake.auth.token ||
                    socket.handshake.headers.authorization?.split(' ')[1];

                if (!token) {
                    return next(new Error('Authentication error: No token'));
                }

                // Validate the token and get the user
                const user = await this.authController.validateSocketToken(token);

                if (!user) {
                    return next(new Error('Authentication error: Invalid token'));
                }

                // Retrieve the IP address
                const ip = socket.handshake.headers['x-forwarded-for'] || socket.handshake.address; // Get the IP address of the socket

                // Check if the user is already connected from the same IP
                if (this.connectedPlayersByIp.has(ip)) {
                    return next(new Error('Connection error: Already connected from this IP'));
                }

                socket.user = user;
                this.connectedPlayersByIp.set(ip, socket.id); // Track the connection by IP
                next();
            } catch (error) {
                next(error);
            }
        });

        this.io.on('connection', (socket) => {
            console.log('New client connected:', socket.id);

            // Handle player initialization and other events
            socket.on('player:init', this.secureSocketHandler(socket, this.initializePlayer));
            socket.on('player:move', this.secureSocketHandler(socket, this.handlePlayerMovement));
            socket.on('chat:message', this.secureSocketHandler(socket, this.broadcastChatMessage));
            socket.on('disconnect', () => this.handlePlayerDisconnect(socket));
        });
    }

    secureSocketHandler(socket, handler) {
        return (data) => {
            if (!socket.user) {
                socket.emit('error', { message: 'Unauthorized' });
                return;
            }
            handler.call(this, socket, data);
        };
    }

    initializePlayer(socket, data) {
        // Find or create player profile
        const player = {
            id: socket.id,
            userId: socket.user.userId,
            username: socket.user.username,
            x: 0,
            y: 0,
            ...data
        };

        this.connectedPlayers.set(socket.id, player);

        // Broadcast player join
        socket.broadcast.emit('player:joined', player);
    }

    handlePlayerMovement(socket, moveData) {
        const player = this.connectedPlayers.get(socket.id);
        if (player) {
            // Update player position
            player.x = moveData.x;
            player.y = moveData.y;

            // Broadcast movement to other players
            socket.broadcast.emit('player:update', player);
        }
    }

    broadcastChatMessage(socket, message) {
        const chatMessage = {
            username: socket.user.username,
            text: message.text,
            timestamp: Date.now()
        };

        // Broadcast to all connected clients
        this.io.emit('chat:message', chatMessage);
    }

    handlePlayerDisconnect(socket) {
        const ip = socket.handshake.headers['x-forwarded-for'] || socket.handshake.address; // Get the IP address of the socket

        // Remove player from connected players
        this.connectedPlayers.delete(socket.id);
        this.connectedPlayersByIp.delete(ip); // Remove the IP tracking on disconnect

        // Broadcast player disconnect
        socket.broadcast.emit('player:left', { id: socket.id });
    }

    async start() {
        await this.connectDatabase();
        this.server.listen(this.PORT, () => {
            console.log(`Server running on port ${this.PORT}`);
        });
    }
}

// Initialize and start the server
const gameServer = new GameServer();

(async () => {
    try {
        await gameServer.start(); // Await the start method to handle any errors
    } catch (error) {
        console.error('Failed to start the server:', error);
        process.exit(1); // Exit the process with an error code
    }
})();
