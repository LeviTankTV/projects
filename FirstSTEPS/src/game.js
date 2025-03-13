function startGame() {
    document.getElementById('startMenu').style.display = 'none';
    document.getElementById('gameContainer').style.display = 'block';
    const game = new Game();
}

// Весь предыдущий код класса Game остается без изменений
class Game {
    // ... (код остается прежним)
}

function showSettings() {
    // Логика для отображения настроек
    console.log("Настройки открыты");
}

function exitGame() {
    // Логика для выхода из игры
    console.log("Выход из игры");
    window.close(); // Closes the current window
}