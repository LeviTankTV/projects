import mongoose from 'mongoose';

const PlayerProfileSchema = new mongoose.Schema({
    userId: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        required: true
    },
    username: {
        type: String,
        required: true,
        unique: true
    },
    level: {
        type: Number,
        default: 1
    },
    experience: {
        type: Number,
        default: 0
    },
    inventory: [{
        itemId: { type: mongoose.Schema.Types.ObjectId },
        quantity: { type: Number, default: 1 }
    }]
}, { collection: 'PlayerProfiles' } );

// Method to add experience
PlayerProfileSchema.methods.addExperience = function(amount) {
    this.experience += amount;

    // Level up logic
    if (this.experience >= this.level * 100) {
        this.level++;
        this.experience -= (this.level - 1) * 100;
    }
};

// Method to add item to inventory
PlayerProfileSchema.methods.addToInventory = function(itemId, quantity = 1) {
    const existingItem = this.inventory.find(item =>
        item.itemId.toString() === itemId.toString()
    );

    if (existingItem) {
        existingItem.quantity += quantity;
    } else {
        this.inventory.push({ itemId, quantity });
    }
};

// Method to remove item from inventory
PlayerProfileSchema.methods.removeFromInventory = function(itemId, quantity = 1) {
    const itemIndex = this.inventory.findIndex(item =>
        item.itemId.toString() === itemId.toString()
    );

    if (itemIndex !== -1) {
        this.inventory[itemIndex].quantity -= quantity;

        if (this.inventory[itemIndex].quantity <= 0) {
            this.inventory.splice(itemIndex, 1);
        }
    }
};

export default mongoose.model('PlayerProfile', PlayerProfileSchema);
