function playMenuMusic() {
    const menuMusic = document.getElementById('menuMusic');
    menuMusic.play().catch(error => {
        console.log("Автовоспроизведение было предотвращено:", error);
    });
}

// Функция для загрузки прогресса
function loadProgress() {
    const encryptedProgress = localStorage.getItem('nightProgress');
    if (encryptedProgress) {
        const bytes = CryptoJS.AES.decrypt(encryptedProgress, 'your-secret-key'); // Используйте свой секретный ключ
        const decryptedProgress = bytes.toString(CryptoJS.enc.Utf8);
        return decryptedProgress ? parseInt(decryptedProgress, 10) : 0; // Возвращаем номер ночи или 0, если прогресс не сохранен
    }
    return 0;
}

// Функция для сохранения прогресса
function saveProgress(night) {
    const encryptedProgress = CryptoJS.AES.encrypt(night.toString(), 'your-secret-key').toString(); // Используйте свой секретный ключ
    localStorage.setItem('nightProgress', encryptedProgress);
}

// Функция для начала первой ночи
function startFirstNight() {
    const newspaper = document.getElementById('newspaper');
    const continueButton = document.getElementById('continueButton');
    const menuElements = document.querySelectorAll('.menu *');
    document.body.classList.add('body-dark');

    // Скрываем все элементы меню
    menuElements.forEach(element => {
        element.classList.add('hide');
    });
    // Показываем газету
    newspaper.classList.add('show');
    continueButton.classList.remove('hide');

    // Меняем музыку
    const menuMusic = document.getElementById('menuMusic');
    const newspaperMusic = document.getElementById('newspaperMusic');

    menuMusic.pause(); // Останавливаем музыку меню
    newspaperMusic.play(); // Включаем музыку газеты
}

// Ждем полной загрузки DOM
document.addEventListener('DOMContentLoaded', function() {
    const loadingScreen = document.getElementById('loadingScreen');
    const nightContinueButton = document.getElementById('nightContinueButton');
    const continueButton = document.getElementById('continueButton');

    // Загружаем прогресс игрока
    const nightProgress = loadProgress();

    // Если прогресс больше 0, показываем кнопку "Продолжить" и кнопку "Ночь"
    if (nightProgress > 0) {
        nightContinueButton.textContent = `Продолжить (Ночь ${nightProgress})`;
        nightContinueButton.classList.remove('hide');
    }

    // Добавляем обработчик события для загрузки экрана
    loadingScreen.addEventListener('click', function() {
        loadingScreen.style.display = 'none'; // Скрываем загрузочный экран
        playMenuMusic();
    });

    // Добавляем обработчик события для кнопки "Новая Игра"
    document.getElementById('newGameButton').addEventListener('click', function() {
        // Начинаем первую ночь
        saveProgress(1); // Сохраняем прогресс
        startFirstNight(); // Начинаем первую ночь
    });

    // Добавляем обработчик события для кнопки "Ночь"
    nightContinueButton.addEventListener('click', function() {
        window.location.href = 'Night.html';
    });

    // Добавляем обработчик события для кнопки "Продолжить" в газете
    continueButton.addEventListener('click', function() {
        window.location.href = 'Night.html';
    });
});