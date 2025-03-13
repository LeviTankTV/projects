package GameV2;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;
public class MainMenu {
    private Scanner scanner;
    private Game game;
    private Random random;
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    // Жирные
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    // Подчеркнутые
    public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

    // Фоновые
    public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
    public static final String RED_BACKGROUND = "\033[41m";    // RED
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE

    // Высокой интенсивности
    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

    // Жирные высокой интенсивности
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    // Высокой интенсивности фоны
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE

    public MainMenu() {
        this.scanner = new Scanner(System.in);
        this.random = new Random();
    }

    private void showAchievements() {
        clearScreen();
        System.out.println("\n╔════════════════════ ДОСТИЖЕНИЯ ════════════════════╗");

        if (game == null || game.getAchievements() == null || game.getAchievements().isEmpty()) {
            System.out.println("║                                                    ║");
            System.out.println("║    Достижения пока недоступны.                     ║");
            System.out.println("║    Начните новую игру, чтобы разблокировать их.    ║");
            System.out.println("║                                                    ║");
        } else {
            for (Achievement achievement : game.getAchievements()) {
                System.out.println("║                                                    ║");
                System.out.println("║  " + formatText(achievement.getName(), 46) + "║");
                System.out.println("║  " + formatText(achievement.getDescription(), 46) + "║");
                String status = achievement.isUnlocked()
                        ? GREEN + "✅ Разблокировано" + RESET
                        : RED + "❌ Не разблокировано" + RESET;
                System.out.println("║  " + formatText(status, 46) + "║");
                System.out.println("║  " + "─".repeat(46) + "║");
            }
        }

        System.out.println("║                                                    ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println("\n     Нажмите Enter, чтобы вернуться в меню...");
        scanner.nextLine();
    }

    // Константы для цветов
    private static final String RESET = "\u001B[0m";

    // Вспомогательный метод для форматирования текста
    private String formatText(String text, int length) {
        if (text.length() > length) {
            return text.substring(0, length - 3) + "...";
        }
        return text + " ".repeat(length - text.length());
    }

    public void displayMenu() throws InterruptedException {
        boolean running = true;
        while (running) {
            clearScreen();
            printLogo();
            System.out.println("\n" + WHITE_BOLD_BRIGHT + "=== ГЛАВНОЕ МЕНЮ ===" + RESET);
            System.out.println(WHITE_BOLD_BRIGHT + "1. Новая игра" + RESET);
            System.out.println(WHITE_BRIGHT + "2. Достижения" + RESET);
            System.out.println(WHITE_BRIGHT + "3. Об игре" + RESET);
            System.out.println(PURPLE_BRIGHT + "4. Запустить Supreme Dungeon I" + RESET); // Новый пункт
            System.out.println(RED + "5. Выход" + RESET);
            System.out.print(WHITE_BRIGHT + "\nВыберите действие (1-5): "+ RESET);

            int choice = getValidChoice(1, 5);

            switch (choice) {
                case 1:
                    startNewGame();
                    break;
                case 2:
                    showAchievements();
                    break;
                case 3:
                    showAbout();
                    break;
                case 4:
                    launchOriginalGame();
                    break;
                case 5:
                    running = !exitGame();
                    break;
            }
        }
        System.out.println("Программа завершена.");
        System.exit(0);
    }

    private boolean checkRequiredFiles() {
        String sourcePath = "C:\\программирование\\apps\\java\\laba3\\src\\tests";
        String[] requiredFiles = {
                "Entity.java",
                "Player.java",
                "Inventory.java",
                "Bow.java",
                "Room.java",
                "StartRoom.java",
                "ShopRoom.java",
                "NothingRoom.java",
                "Game.java",
                "Main.java",
                "Idiot.java"
        };

        for (String file : requiredFiles) {
            File f = new File(sourcePath + "\\" + file);
            if (!f.exists()) {
                System.out.println(RED_BOLD + "Ошибка: Файл " + f.getPath() + " не найден!" + RESET);
                return false;
            }
        }
        return true;
    }

    private void launchOriginalGame() {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "java",
                    "-cp",
                    "C:\\программирование\\apps\\java\\laba3\\src",
                    "tests.Main",
                    "v2"  // Добавляем аргумент для запуска второй версии
            );

            pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);

            Process process = pb.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Ошибка при запуске игры: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void printProcessOutput(Process process) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
             BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

            // Читаем и выводим стандартный вывод
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Читаем и выводим ошибки
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }
        }
    }




    private void printLogo() {
        clearScreen();
        final int WIDTH = 51;

        // Верхняя граница
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║                                                   ║");

        // Название игры (всегда пурпурное)
        String title = "SUPREME DUNGEON II";
        int padding = (WIDTH - title.length()) / 2;
        String paddedTitle = " ".repeat(padding) + WHITE_BOLD_BRIGHT + title + RESET + " ".repeat(WIDTH - title.length() - padding);
        System.out.println("║" + paddedTitle + "║");

        System.out.println("║                                                   ║");

        // Подзаголовок (белый, не анимированный)
        String subtitle = "The Legend Continues...";
        int subtitlePadding = (WIDTH - subtitle.length()) / 2;
        String paddedSubtitle = " ".repeat(subtitlePadding) + WHITE_BOLD_BRIGHT + subtitle + RESET + " ".repeat(WIDTH - subtitle.length() - subtitlePadding);
        System.out.println("║" + paddedSubtitle + "║");

        System.out.println("║                                                   ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
    }


    private void clearScreen() {
        // Очистка консоли (можно использовать разные команды для разных ОС)
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private int getValidChoice(int min, int max) {
        int choice = -1;
        do {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < min || choice > max) {
                    System.out.print("Пожалуйста, введите число от " + min + " до " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Пожалуйста, введите корректное число: ");
            }
        } while (choice < min || choice > max);
        return choice;
    }

    private static final String S = """
            \033[0;36m
⠀⠀⠀⢸⣦⡀⠀⠀⠀⠀⢀⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⢸⣏⠻⣶⣤⡶⢾⡿⠁⠀⢠⣄⡀⢀⣴⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⣀⣼⠷⠀⠀⠁⢀⣿⠃⠀⠀⢀⣿⣿⣿⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠴⣾⣯⣅⣀⠀⠀⠀⠈⢻⣦⡀⠒⠻⠿⣿⡿⠿⠓⠂⠀⠀⢀⡇⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠉⢻⡇⣤⣾⣿⣷⣿⣿⣤⠀⠀⣿⠁⠀⠀⠀⢀⣴⣿⣿⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠸⣿⡿⠏⠀⢀⠀⠀⠿⣶⣤⣤⣤⣄⣀⣴⣿⡿⢻⣿⡆⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠟⠁⠀⢀⣼⠀⠀⠀⠹⣿⣟⠿⠿⠿⡿⠋⠀⠘⣿⣇⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⢳⣶⣶⣿⣿⣇⣀⠀⠀⠙⣿⣆⠀⠀⠀⠀⠀⠀⠛⠿⣿⣦⣤⣀⠀⠀
⠀⠀⠀⠀⠀⠀⣹⣿⣿⣿⣿⠿⠋⠁⠀⣹⣿⠳⠀⠀⠀⠀⠀⠀⢀⣠⣽⣿⡿⠟⠃
⠀⠀⠀⠀⠀⢰⠿⠛⠻⢿⡇⠀⠀⠀⣰⣿⠏⠀⠀⢀⠀⠀⠀⣾⣿⠟⠋⠁⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠋⠀⠀⣰⣿⣿⣾⣿⠿⢿⣷⣀⢀⣿⡇⠁⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠋⠉⠁⠀⠀⠀⠀⠙⢿⣿⣿⠇⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⢿⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠀⠀⠀⠀⠀⠀⠀
            \033[0m
            """;

    private static final String D = """
            \033[0;36m
               _________________________________________________________
             /|     -_-                                             _-  |\\
            / |_-_- _                                         -_- _-   -| \\  \s
              |                            _-  _--                      |\s
              |                            ,                            |
              |      .-'````````'.        '(`        .-'```````'-.      |
              |    .` |           `.      `)'      .` |           `.    |         \s
              |   /   |   ()        \\      U      /   |    ()       \\   |
              |  |    |    ;         | o   T   o |    |    ;         |  |
              |  |    |     ;        |  .  |  .  |    |    ;         |  |
              |  |    |     ;        |   . | .   |    |    ;         |  |
              |  |    |     ;        |    .|.    |    |    ;         |  |
              |  |    |____;_________|     |     |    |____;_________|  | \s
              |  |   /  __ ;   -     |     !     |   /     `'() _ -  |  |
              |  |  / __  ()        -|        -  |  /  __--      -   |  |
              |  | /        __-- _   |   _- _ -  | /        __--_    |  |
              |__|/__________________|___________|/__________________|__|
             /                                             _ -        lc \\
            /   -_- _ -             _- _---                       -_-  -_ \\
            \s
            \033[0m
            """;

    private void startNewGame() throws InterruptedException {
        clearScreen();

        // Анимированное приветствие
        String title = """
            ╔═══════════════════════════════════════════╗
            ║           СОЗДАНИЕ ПЕРСОНАЖА              ║
            ╚═══════════════════════════════════════════╝""";

        displayAnimatedText(CYAN_BOLD_BRIGHT + title + RESET);

        // Анимированное вступление
        String intro = """
            
            ⚜️ Тьма снова сгущается над королевством...
            🌙 Древнее зло пробуждается от векового сна
            ⚔️ И лишь герой может изменить судьбу мира
            
            """;

        displayAnimatedText(PURPLE_BOLD_BRIGHT + intro + RESET);

        String playerName;
        boolean validName = false;

        while (!validName) {
            System.out.println(CYAN_BOLD + "╭─────────────────────────────────╮" + RESET);
            System.out.print(CYAN_BOLD + "│ " + WHITE_BOLD_BRIGHT + "Введите имя вашего героя: " + RESET);
            playerName = scanner.nextLine().trim();
            System.out.println(CYAN_BOLD + "╰─────────────────────────────────╯" + RESET);

            if (playerName.isEmpty()) {
                displayError("📛 Имя не может быть пустым!");
            } else if (playerName.matches("\\d+")) {
                displayError("📛 Имя не может состоять только из цифр!");
            } else if (playerName.equalsIgnoreCase("Верховный Маг")) {
                displayWarning("🤔 Хм... Верховный Маг? Не припомню такого. Может, попробуете другое имя?");
            } else {
                validName = true;

                // Специальные приветствия
                switch (playerName.toLowerCase()) {
                    case "levitank_tv" ->
                            displaySpecialGreeting("👑 О, создатель! Добро пожаловать в ваше творение!", PURPLE_BOLD_BRIGHT);
                    case "гендальф" ->
                            displaySpecialGreeting("🧙 Вы не пройдете! ... Ой, простите, привычка. Проходите, конечно!", CYAN_BOLD_BRIGHT);
                    case "артас" ->
                            displaySpecialGreeting("❄️ Да здравствует король-лич! ... Кхм, простите, не то время.", BLUE_BOLD_BRIGHT);
                    case "довакин" ->
                            displaySpecialGreeting("🐉 FUS RO... Ой, простите, не та игра!", YELLOW_BOLD_BRIGHT);
                }

                Player player = new Player(playerName, 1);
                game = new Game(player);

                // Приветственное сообщение
                String welcome = """
                    
                    ╔════════════════════════════════════════════════╗
                    ║  Приветствую тебя, %s!
                    ║  Судьба королевства снова в твоих руках.
                    ╚════════════════════════════════════════════════╝
                    """.formatted(CYAN_BOLD_BRIGHT + playerName + WHITE_BOLD_BRIGHT);

                displayAnimatedText(WHITE_BOLD_BRIGHT + welcome + RESET);

                // Начальные подсказки
                String hints = """
                    💡 Советы для начала пути:
                    ⚔️ Сражайтесь с монстрами, чтобы стать сильнее
                    🤝 Ищите союзников в своем путешествии
                    💰 Собирайте сокровища и улучшайте снаряжение
                    
                    """;

                displayAnimatedText(YELLOW_BOLD + hints + RESET);

                System.out.println(WHITE_BOLD_BRIGHT + "Нажмите Enter, чтобы начать новое приключение..." + RESET);
                scanner.nextLine();

                game.start();
            }
        }
    }

    private void displayError(String message) throws InterruptedException {
        System.out.println("\n" + RED_BOLD + message + RESET);
        Thread.sleep(1500);
        clearScreen();
    }

    private void displayWarning(String
                                        message) throws InterruptedException {
        System.out.println("\n" + YELLOW_BOLD + message + RESET);
        Thread.sleep(1500);
        clearScreen();
    }

    private void displaySpecialGreeting(String message, String color) throws InterruptedException {
        System.out.println("\n" + color + message + RESET);
        Thread.sleep(2000);
    }

    private void displayAnimatedText(String text) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }




    private void showAbout() {
        clearScreen();
        System.out.println(WHITE_BOLD_BRIGHT + "=== О ИГРЕ ===" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "Supreme Dungeon II - Продолжение легендарной саги" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "Версия: 2.0" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "\nВы оказываетесь на втором этаже подземелья." + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "Древнее зло пробудилось вновь, и только вы можете остановить его!" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "\nОсобенности игры:" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "- Новые подземелья и локации" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "- Улучшенная система боя" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "- Добавление Магии" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "- Новые противники и боссы" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "- Продолжение эпической истории" + RESET);
        System.out.println( WHITE_BOLD_BRIGHT +"\nРазработчик: " + RESET + PURPLE_BOLD_BRIGHT + "LeviTank_TV" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "© 2024 Все права защищены" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "\nНажмите Enter, чтобы вернуться в главное меню..." + RESET);
        scanner.nextLine();
    }

    private boolean exitGame() {
        clearScreen();
        System.out.println(GREEN + "Вы действительно хотите покинуть игру?" + RESET);
        System.out.println(RED + "[1] Да ❌" + RESET);
        System.out.println(GREEN + "[2] Нет ✅" + RESET);

        String input = scanner.nextLine().toLowerCase();

        if (input.equals("1") || input.equals("д") || input.equals("y")) {
            clearScreen();
            System.out.println(RED + "❌ Спасибо за игру! До встречи в Supreme Dungeon II! ❌" + RESET);
            return true; // Сигнал к выходу из игры
        } else {
            clearScreen();
            System.out.println(GREEN + "✅ Возвращаемся к игре!" + RESET);
            try {
                Thread.sleep(1500); // Пауза на 1.5 секунды
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return false; // Продолжаем игру
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MainMenu menu = new MainMenu();
        menu.displayMenu();
    }
}