const mongoose = require('mongoose');

const UserSchema = new mongoose.Schema({
    username: { type: String, required: true, unique: true},
    password: { type: String, required: true},
    inventory: [{
        petalType: { type: String, required: true }, // e.g., "Arc"
        rarity: { type: String, required: true }, // e.g., "common", "unusual"
    }],
    level: { type: Number, default: 1},
    experience: { type: Number, default: 0}
});

const User = mongoose.model('User', UserSchema);

module.exports = User;