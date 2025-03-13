const User = require("../MDB/UserSchema");

async function grantPetalToUser (username, petalType, rarity, amount) {
    try {
        const user = await User.findOne({ username });
        if (user) {
            for (let i = 0; i < amount; i++) {
                user.inventory.push({ petalType, rarity });
            }
            await user.save();
            console.log(`Granted ${amount} ${petalType}(s) of ${rarity} rarity to ${username}`);
        } else {
            console.log(`User  ${username} not found`);
        }
    } catch (error) {
        console.error(`Failed to grant ${amount} petals to ${username}:`, error);
    }
}

module.exports = { grantPetalToUser };