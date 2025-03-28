import jwt from "jsonwebtoken";
import bcrypt from "bcryptjs";
import PlayerProfile from '../models/PlayerProfile.js';
import User from "../models/User.js";
export default class AuthController {
    constructor(secretKey) {
        this.SECRET_KEY = secretKey;
    }

    async registerUser (req, res) {
        try {
            const { username, password } = req.body;
            const clientIp = req.ip || 'Unknown';
            const userAgent = req.get('User -Agent') || 'Unknown';

            // Validate username and password
            if (!username || username.length < 3) {
                return res.status(400).json({
                    message: 'Username must be at least 3 characters'
                });
            }

            if (!password || password.length < 6) {
                return res.status(400).json({
                    message: 'Password must be at least 6 characters'
                });
            }

            // Check for existing user
            const existingUser  = await User.findOne({
                username: username.trim()
            });
            if (existingUser ) {
                return res.status(409).json({
                    message: 'Username already exists'
                });
            }

            // Hash password
            const saltRounds = 12;
            const hashedPassword = await bcrypt.hash(password, saltRounds);

            // Create new user
            const newUser  = new User({
                username: username.trim(),
                password: hashedPassword,
                registeredIp: clientIp
            });

            // Add the first device
            const deviceToken = newUser.addDevice(clientIp, userAgent);

            // Save the user
            await newUser .save();

            // Create a player profile for the new user
            const playerProfile = new PlayerProfile({
                userId: newUser ._id,
                username: newUser .username // Use the same username
            });

            await playerProfile.save(); // Save the player profile

            // Generate token
            const token = jwt.sign(
                {
                    userId: newUser ._id,
                    deviceToken: deviceToken
                },
                this.SECRET_KEY,
                { expiresIn: '60d' }
            );

            // Set cookie with additional options
            res.cookie('authToken', token, {
                httpOnly: true,
                secure: process.env.NODE_ENV === 'production',
                sameSite: 'strict',
                maxAge: 60 * 24 * 60 * 60 * 1000 // 60 days
            });

            res.status(201).json({
                message: 'User  registered successfully',
                token,
                userId: newUser ._id,
                playerProfile // Return the created player profile
            });

        } catch (error) {
            console.error('Registration error:', error);
            res.status(500).json({
                message: 'Server error during registration',
                error: process.env.NODE_ENV !== 'production' ? error.message : ''
            });
        }
    }

    async getUserProfile(req, res) {
        try {
            // Assuming req.user is set by authenticateToken middleware
            const userProfile = await PlayerProfile.findOne({ userId: req.user._id });
            if (!userProfile) {
                return res.status(404).json({ message: 'User  profile not found' });
            }
            res.json(userProfile);
        } catch (error) {
            console.error('Error fetching user profile:', error);
            res.status(500).json({ message: 'Server error' });
        }
    }

    async loginUser (req, res) {
        try {
            const { username, password } = req.body;
            const clientIp = req.ip || 'Unknown';
            const userAgent = req.get('User -Agent') || 'Unknown';

            // Find user by username
            const user = await User.findOne({ username });
            if (!user) {
                return res.status(400).json({ message: 'Invalid credentials' });
            }

            // Check password
            const isMatch = await bcrypt.compare(password, user.password);
            if (!isMatch) {
                return res.status(400).json({ message: 'Invalid credentials' });
            }

            // Generate a new device token
            const deviceToken = user.addDevice(clientIp, userAgent);

            // Create a long-term token
            const token = jwt.sign(
                {
                    userId: user._id,
                    deviceToken: deviceToken
                },
                this.SECRET_KEY,
                { expiresIn: '60d' }
            );

            // Set the authToken cookie
            res.cookie('authToken', token, {
                httpOnly: true,
                secure: process.env.NODE_ENV === 'production',
                sameSite: 'strict',
                maxAge: 60 * 24 * 60 * 60 * 1000 // 60 days
            });

            // Optionally update the user document (if needed)
            await user.save(); // Ensure any changes to the user are saved

            // Return successful response with user info
            res.json({
                message: 'Login successful',
                token,
                userId: user._id,
                username: user.username // Optionally return username
            });

        } catch (error) {
            console.error('Login error:', error); // Log the error for debugging
            res.status(500).json({
                message: 'Server error during login',
                error: process.env.NODE_ENV !== 'production' ? error.message : ''
            });
        }
    }

// Middleware для проверки токена
    async authenticateToken(req, res, next) {
        const token = req.cookies.authToken || req.headers.authorization?.split(' ')[1];

        if (!token) {
            console.log('No token provided');
            return res.status(401).json({
                message: 'No token provided',
                redirect: '/login.html'
            });
        }

        try {
            const decoded = jwt.verify(token, this.SECRET_KEY);
            const user = await User.findById(decoded.userId);

            if (!user) {
                console.log('User  not found');
                return res.status(401).json({
                    message: 'User  not found',
                    redirect: '/login.html'
                });
            }

            const isDeviceValid = user.validateDevice(decoded.deviceToken);
            if (!isDeviceValid) {
                console.log('Invalid or expired device');
                return res.status(401).json({
                    message: 'Invalid or expired device',
                    redirect: '/login.html'
                });
            }

            req.user = user; // Set the user on the request
            next();
        } catch (error) {
            console.error('Token verification error:', error);
            return res.status(401).json({
                message: 'Invalid token',
                redirect: '/login.html'
            });
        }
    }

    async validateSocketToken(token) {
        try {
            const decoded = jwt.verify(token, this.SECRET_KEY);
            const user = await User.findById(decoded.userId);

            if (!user || !user.validateDevice(decoded.deviceToken)) {
                return null;
            }

            return {
                id: user._id,
                username: user.username
            };
        } catch (error) {
            return null;
        }
    }

    async validateGameAccess(req, res) {
        const isValid = await this.checkDeviceValidity(req, res);

        if (isValid) {
            res.json({
                message: 'Access granted',
                redirect: '/game/MainMenu.html'
            });
        } else {
            res.status(403).json({
                message: 'Access denied',
                redirect: '/login.html'
            });
        }
    }

    async checkDeviceValidity(req) {
        const token = req.cookies.authToken || req.headers.authorization?.split(' ')[1];

        if (!token) {
            return false; // No token provided
        }

        try {
            const decoded = jwt.verify(token, this.SECRET_KEY);
            const user = await User.findById(decoded.userId);

            if (!user) {
                return false; // User not found
            }

            return user.validateDevice(decoded.deviceToken); // Return true or false based on device validation
        } catch (error) {
            console.error('Token verification error:', error);
            return false; // Token verification failed
        }
    }
}
