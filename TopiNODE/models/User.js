import mongoose from 'mongoose';
import crypto from 'crypto';

const DeviceSchema = new mongoose.Schema({
    deviceToken: {
        type: String,
        required: true,
        unique: true
    },
    ip: {
        type: String,
        required: true
    },
    userAgent: {
        type: String
    },
    lastLogin: {
        type: Date,
        default: Date.now
    },
    isActive: {
        type: Boolean,
        default: true
    },
    expiresAt: {
        type: Date,
        default: () => new Date(+new Date() + 60*24*60*60*1000) // 2 месяца
    }
});

const UserSchema = new mongoose.Schema({
    username: {
        type: String,
        required: true,
        unique: true
    },
    password: {
        type: String,
        required: true
    },
    devices: [DeviceSchema],
    mainDeviceToken: {
        type: String
    },
    lastLogin: {
        type: Date
    },
    registeredIp: {
        type: String
    }
}, {
    timestamps: true, collection: "Users"
});

// Обновим метод generateDeviceToken
UserSchema.methods.generateDeviceToken = function(ip, userAgent) {
    const deviceData = `${ip}${userAgent}${Date.now()}${this._id}`;
    return crypto
        .createHash('sha256')
        .update(deviceData)
        .digest('hex');
};

// Обновим метод addDevice
UserSchema.methods.addDevice = function(ip, userAgent) {
    const deviceToken = this.generateDeviceToken(ip, userAgent);

    const newDevice = {
        deviceToken,
        ip,
        userAgent,
        lastLogin: new Date(),
        isActive: true,
        expiresAt: new Date(+new Date() + 60*24*60*60*1000) // 2 месяца
    };

    this.devices.push(newDevice);
    return deviceToken;
};

// Улучшим метод validateDevice
UserSchema.methods.validateDevice = function(deviceToken) {
    const device = this.devices.find(d =>
        d.deviceToken === deviceToken &&
        d.isActive &&
        d.expiresAt > new Date()
    );
    return !!device;
};

UserSchema.methods.isDeviceValid = function(deviceToken) {
    const device = this.devices.find(d => d.deviceToken === deviceToken);
    return device && device.isActive && device.expiresAt > new Date();
};

const User = mongoose.model('User', UserSchema);
export default User;