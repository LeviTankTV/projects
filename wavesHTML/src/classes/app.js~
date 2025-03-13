import { Game } from './classes/Game.js';

document.addEventListener('DOMContentLoaded', () => {
    const canvas = document.getElementById('arena');
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
    const game = new Game(canvas);
    game.start();
    window.addEventListener('resize', () => {
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;
        game.resize();
    });
});