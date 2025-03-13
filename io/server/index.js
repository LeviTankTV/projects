const express = require('express');
const socketIO = require('socket.io');
const http = require('http');
const app = express();
const index = http.createServer(app);
const io = socketIO(index);
const SocketHandler = require('./networking.js');
const GameServer = require('./Sclasses/Game.js');
const Routes = require('./routes.js');
const mongoose = require('mongoose');
const bodyParser = require("body-parser");
const game = require('./Sclasses/Game.js');
const grantPetalToUser = require("./Admin");
const Admin = require('./Admin.js');

const MONGO_URI = 'mongodb://localhost:27017/Topi'; // Replace with your MongoDB URI
// Connect to MongoDB
mongoose.connect(MONGO_URI, {})
    .then(() => console.log('MongoDB connected'))
    .catch(err => console.error('MongoDB connection error:', err));


/* -----------------------------------------------------------------------------------------------------
                        Let's say that were const for requires and mongodb
--------------------------------------------------------------------------------------------------------- */

app.use(bodyParser.json());
const routes = new Routes();
app.use(routes.router);
app.use(express.static(__dirname + '/../client'));

const gameServer = new GameServer(io);
// Admin.grantPetalToUser('LeviTank_TV', 'Arc', 'common', 5);
index.listen(3000, () => {
    console.log('Server is up on 3000 port');
})