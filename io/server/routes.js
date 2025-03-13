const express = require('express');
const path = require('path');
const User = require('../MDB/UserSchema');

class Routes {
    constructor() {
        this.router = express.Router();
        this.initializeRoutes();
    }

    initializeRoutes() {
        this.router.get('/', (req, res) => {
            res.sendFile(path.join(__dirname, '../client/index.html'));
        });

        // User Registration
        this.router.post('/register', async (req, res) => {
            const {username, password} = req.body;
            try {
                const existingUser = await User.findOne({username});
                if (existingUser) {
                    return res.status(400).json({error: 'Username already exists'});
                }
                const user = new User({
                    username,
                    password,
                    inventory: [],
                    level: 1,
                    experience: 0
                });

                for (let i = 0; i < 5; i++) {
                    user.inventory.push({petalType: 'Arc', rarity: 'common'});
                }

                await user.save();
                res.status(201).json({message: 'User  registered successfully'});
            } catch (error) {
                res.status(400).json({error: 'User  registration failed'});
                console.log(error);
            }
        });

        // User Login
        this.router.post('/login', async (req, res) => {
            const {username, password} = req.body;
            const user = await User.findOne({username, password});
            if (user) {
                res.status(200).json({message: 'Login Successful', user});
            } else {
                res.status(401).json({error: 'Invalid username or password'});
            }
        });

        // Get User Profile
        this.router.get('/api/profile/:username', async (req, res) => {
            const {username} = req.params;
            try {
                const user = await User.findOne({username});
                if (user) {
                    const {password, ...profile} = user.toObject();
                    res.status(200).json(profile);
                } else {
                    res.status(404).json({error: 'User  not found'});
                }
            } catch (error) {
                res.status(500).json({error: 'Error fetching user profile'});
            }
        });

        // Add a petal to the user's inventory
        this.router.post('/api/users/:username/inventory', async (req, res) => {
            const {username} = req.params;
            const {petalType, rarity} = req.body;
            try {
                const user = await User.findOne({username});
                if (user) {
                    user.inventory.push({petalType, rarity});
                    await user.save();
                    res.status(201).json({message: 'Petal added to inventory', inventory: user.inventory});
                } else {
                    res.status(404).json({error: 'User  not found'});
                }
            } catch (error) {
                res.status(400).json({error: 'Failed to add petal to inventory'});
                console.log(error);
            }
        });

        // Get user's inventory
        this.router.get('/api/users/:username/inventory', async (req, res) => {
            const {username} = req.params;
            try {
                const user = await User.findOne({username});
                if (user) {
                    res.status(200).json(user.inventory);
                } else {
                    res.status(404).json({error: 'User  not found'});
                }
            } catch (error) {
                res.status(500).json({error: 'Failed to fetch inventory'});
                console.log(error);
            }
        });

        // Remove a petal from the user's inventory
        this.router.delete('/api/users/:username/inventory/:petalType', async (req, res) => {
            const {username, petalType} = req.params;
            try {
                const user = await User.findOne({username});
                if (user) {
                    user.inventory = user.inventory.filter(petal => petal.petalType !== petalType);
                    await user.save();
                    res.status(200).json({message: 'Petal removed from inventory', inventory: user.inventory});
                } else {
                    res.status(404).json({error: 'User  not found'});
                }
            } catch (error) {
                res.status(500).json({error: 'Failed to remove petal from inventory'});
                console.log(error);
            }
        });

        this.router.post('/api/users/:username/craft', async (req, res) => {
            const {username} = req.params;
            const {selectedPetal} = req.body; // Get selected petal from request body

            try {
                const user = await User.findOne({username});

                if (!user) {
                    return res.status(404).json({error: 'User  not found'});
                }

                // Check inventory for common petals
                const commonPetals = user.inventory.filter(petal => petal.rarity === 'common');
                const commonCount = commonPetals.length;

                if (commonCount < 2) {
                    return res.status(400).json({error: 'Not enough common petals to craft.'});
                }

                // Crafting chance logic
                const successChance = Math.random() < 0.64; // 64% chance
                let petalsLost = 0;

                if (successChance) {
                    // Success: Add the new petal based on selectedPetal
                    user.inventory.push({petalType: selectedPetal.petalType, rarity: 'unusual'});

                    // Remove 2 common petals on success.
                    const commonPetalIndices = user.inventory.reduce((acc, petal, index) => {
                        if (petal.rarity === 'common') {
                            acc.push(index);
                        }
                        return acc;
                    }, []).slice(0, 2); // indexes of first two common petals

                    // Remove petals by their indices
                    user.inventory = user.inventory.filter((_, index) => !commonPetalIndices.includes(index));

                    await user.save();
                    return res.status(200).json({message: `Crafting successful! You received an unusual petal: ${selectedPetal.petalType}.`});

                } else {
                    // Failure: Randomly lose 1 or 2 common petals
                    petalsLost = Math.floor(Math.random() * 2) + 1; // Randomly lose 1 or 2 petals
                    if (petalsLost > commonCount) petalsLost = commonCount; // Don't lose more than you have

                    const commonPetalIndices = user.inventory.reduce((acc, petal, index) => {
                        if (petal.rarity === 'common') {
                            acc.push(index);
                        }
                        return acc;
                    }, []).slice(0, petalsLost); // indexes of first n common petals

                    // Remove petals by their indices
                    user.inventory = user.inventory.filter((_, index) => !commonPetalIndices.includes(index));

                    await user.save();
                    return res.status(200).json({message: `Crafting failed! You lost ${petalsLost} common petal(s).`});
                }
            } catch (error) {
                res.status(500).json({error: 'Crafting failed'});
                console.log(error);
            }
        });
    }
}

module.exports = Routes;