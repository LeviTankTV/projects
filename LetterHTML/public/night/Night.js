function loadProgress() {
    const encryptedProgress = localStorage.getItem('nightProgress');
    if (encryptedProgress) {
        const bytes = CryptoJS.AES.decrypt(encryptedProgress, 'your-secret-key'); // Используйте свой секретный ключ
        const decryptedProgress = bytes.toString(CryptoJS.enc.Utf8);
        return decryptedProgress ? parseInt(decryptedProgress, 10) : 0; // Возвращаем номер ночи или 0, если прогресс не сохранен
    }
    return 0;
}

// Функция для отображения текста ночи
function displayNightInfo() {
    const nightProgress = loadProgress();
    const letterCount = nightProgress < 6 ? 10 : 15; // Определяем количество писем
    const nightText = `Ночь ${nightProgress}`;
    const letterCountText = `Доставьте ${letterCount} писем для завершения ночи.`;

    // Устанавливаем текст
    const nightTextElement = document.getElementById('nightText');
    const letterCountTextElement = document.getElementById('letterCountText');

    nightTextElement.textContent = nightText;
    letterCountTextElement.textContent = letterCountText;

    // Добавляем класс для эффекта глитча
    nightTextElement.classList.add('glitch-text');
    nightTextElement.setAttribute('data-text', nightText); // Устанавливаем атрибут для анимации

    letterCountTextElement.classList.add('glitch-text');
    letterCountTextElement.setAttribute('data-text', letterCountText); // Устанавливаем атрибут для анимации

    // Убираем экран через 6 секунд
    setTimeout(() => {
        document.getElementById('darkScreen').style.opacity = '0'; // Убираем затемнение
        setTimeout(() => {
            initializeGame(); // Инициализируем игру
        }, 1000); // Ждем 1 секунду перед инициализацией
    }, 6000); // Показываем экран 6 секунд
}

// Функция для инициализации игры
function initializeGame() {
    const nightProgress = loadProgress();
    const game = new Game(nightProgress); // Замените на вашу логику инициализации
    game.start(); // Запускаем игру (или используйте другой метод инициализации)
}

// Ждем полной загрузки DOM
document.addEventListener('DOMContentLoaded', displayNightInfo);