package GameV2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public abstract class Room {

    private static final Random random = new Random();
    private Room previousRoom;
    private Room nextRoom;
    private List<Entity> enemies; // List of enemies in the room
    private boolean escaped = false;

    public Room() {
        this.enemies = new ArrayList<>(); // Initialize the list of enemies
    }

    public void clearEnemies() {
        enemies.clear();
    }

    public void showEnemiesInfo(Room room) {
        List<Entity> enemies = room.getEnemies();

        System.out.println("\n" + YELLOW_BOLD_BRIGHT + "━━━ ВРАГИ В КОМНАТЕ ━━━" + RESET);
        System.out.printf("%sОбщее количество врагов: %s%d%n",
                CYAN_BOLD_BRIGHT, RED_BOLD_BRIGHT, enemies.size());

        if (!enemies.isEmpty()) {
            System.out.println("\n" + WHITE_BOLD_BRIGHT + "━━━ ДЕТАЛИ ВРАГОВ ━━━" + RESET);

            for (int i = 0; i < enemies.size(); i++) {
                Entity enemy = enemies.get(i);

                // Информация о враге
                System.out.println(BLUE_BOLD_BRIGHT + "⊱ Враг #" + (i + 1) + RESET);
                System.out.printf("  %s%-12s%s%s%n",
                        GREEN_BOLD_BRIGHT, "Имя:", RESET, enemy.getName());

                System.out.printf("  %s%-12s%s%d%n",
                        GREEN_BOLD_BRIGHT, "Уровень:", RESET, enemy.getLevel());

                System.out.printf("  %s%-12s%s%.1f/%.1f%n",
                        GREEN_BOLD_BRIGHT, "Здоровье:", RESET, enemy.getHealth(), enemy.getMaxHealth());

                System.out.printf("  %s%-12s%s%d%n",
                        GREEN_BOLD_BRIGHT, "Атака:", RESET, enemy.getAttack());

                // Добавляем разделитель между врагами, кроме последнего
                if (i < enemies.size() - 1) {
                    System.out.println(WHITE_BOLD_BRIGHT + "  ━━━━━━━━━━━━━━━" + RESET);
                }
            }
        }

        System.out.println(YELLOW_BOLD_BRIGHT + "━━━━━━━━━━━━━━━━━━━━━" + RESET);
    }


    // Method to check if room is cleared (no enemies left)
    public boolean isCleared() {
        return enemies.isEmpty();
    }

    // Method to generate a random room
    public static Room generateRandomRoom(Game game) {
        Player player = game.getPlayer();
        Random random = new Random();
        int rel = random.nextInt(100);
        if (rel < 15) {
            return new LifeFountain();
        } else if (rel < 22) {
            return new ExperienceFountain();
        } else if (rel < 24) {
            return new ManaRitualRoom();
        } else if (rel < 30) {
            return new ChestRoom();
        } else if (rel < 36) {
            return new ZombieRoom(player);
        } else if (rel < 43) {
            return new SkeletonRoom(player);
        } else if (rel < 53) {
            return new GoblinRoom(player);
        } else if (rel < 60) {
            return new DungeonArena(player);
        } else if (rel < 70) {
            return new GolemRoom(player);
        } else if (rel < 74) {
            return new RitualRoom();
        } else if (rel < 89) {
            return new AllyRoom(game);
        } else if (rel < 94) {
            return new Library();
        } else if (rel < 97) {
            return new WanderingTrader(player, game);
        } else {
            return new MysteriousRoom(player);
        }
    }

    public static Room generateSecondGamePartRandomRoom(Game game) {
        Player player = game.getPlayer();
        Random random = new Random();
        int rel = random.nextInt(100);

        if (rel < 4) {
            return new MagicTree();
        } else if (rel < 8) {
            return new MagicGarden();
        } else if (rel < 12) {
            return new ChestRoom();
        } else if (rel < 16) {
            return new SecondZombieRoom(player);
        } else if (rel < 20) {
            return new SecondSkeletonRoom(player);
        } else if (rel < 23) {
            return new BigLifeFountain();
        } else if (rel < 26) {
            return new BigManaFountain();
        } else if (rel < 30) {
            return new RoyalLibrary();
        } else if (rel < 34) {
            return new GoblinRoom(player);
        } else if (rel < 38) {
            return new GolemRoom(player);
        } else if (rel < 42) {
            return new KnightRoom(game);
        } else if (rel < 45) {
            return new BanditRoom(player);
        } else if (rel < 55) {
            return new AllyRoom(game);
        } else if (rel < 57) {
            return new AssassinRoom(player);
        } else if (rel < 59) {
            return new ManaRitualRoom();
        } else if (rel < 63) {
            return new RitualRoom();
        } else if (rel < 67) {
            return new GoldenChestRoom();
        } else if (rel < 70) {
            return new WanderingTrader(player, game);
        } else if (rel < 76) {
            return new BossRoom(player);
        } else if (rel < 78) {
            return new DarkSilverChestRoom();
        } else if (rel < 81) {
            return new GrandArena(player);
        } else if (rel < 84) {
            return new RoyalArena(player);
        } else if (rel < 87) {
            return new DreamFountain();
        } else if (rel < 90) {
            return new ResettingFurnace();
        } else if (rel < 93) {
            return new FlowerGarden();
        } else if (rel < 95) {
            return new MagesRoom(player);
        } else if (rel < 96) {
            return new HallOfFameRoom();
        } else if (rel < 97) {
            return new CrystalCavernRoom();
        } else if (rel < 99) {
            return new ThroneRoom();
        } else {
            return new RoyalGallery();
        }
    }

    public static Room generateThirdGamePartRandomRoom(Game game) {
        Player player = game.getPlayer();
        return new GhostArena(player);
    }

    public void setPreviousRoom(Room previousRoom) {
        this.previousRoom = previousRoom;
    }

    public Room getPreviousRoom() {
        return previousRoom;
    }

    public void setNextRoom(Room nextRoom) {
        this.nextRoom = nextRoom;
    }

    public Room getNextRoom() {
        return nextRoom;
    }

    // Method to add a single enemy
    public void addEnemy(Entity enemy) {
        enemies.add(enemy);
    }

    // Method to add a list of enemies
    public void addEnemies(List<Entity> enemies) {
        this.enemies.addAll(enemies);
    }

    // Method to get the list of enemies
    public List<Entity> getEnemies() {
        return enemies;
    }

    // Method to check if there are any enemies in the room
    public boolean hasEnemies() {
        return !enemies.isEmpty();
    }

    public void playerTurn(Game game, Room room) {
        if (hasEnemies()) {
            System.out.println(RED_BOLD_BRIGHT + "╔════════════════════════════════╗");
            System.out.println("║    Вы столкнулись с врагами!    ║");
            System.out.println("╚════════════════════════════════╝" + RESET);
            handleCombat(game, this); // Обработка боя
        } else {
            System.out.println(GREEN_BOLD_BRIGHT + "╔════════════════════════════════════════╗");
            System.out.println("║    Комната пуста. Вы можете двигаться    ║");
            System.out.println("║              дальше.                      ║");
            System.out.println("╚════════════════════════════════════════╝" + RESET);
            handleEmptyRoom(game, room); // Позволяем игроку взаимодействовать с пустой комнатой
        }
    }

    public void setEscaped(boolean escaped) {
        this.escaped = escaped;
    }

    public boolean isEscaped() {
        return escaped;
    }

    public void handleCombat(Game game, Room room) {
        Player player = game.getPlayer();
        Scanner scanner = new Scanner(System.in);
        boolean turnEnded = false;

        while (game.getPlayer().isAlive() && hasEnemies() && !turnEnded) {
            try {
                // Ход игрока
                boolean playerTurnEnded = game.getPlayer().performAction(player, room);

                if (room.isEscaped()) {
                    System.out.println(YELLOW_BOLD_BRIGHT + "Вы покидаете текущую комнату..." + RESET);
                    game.moveForward();
                    return;
                }

                if (!game.getPlayer().isAlive()) {
                    System.out.println(RED_BOLD_BRIGHT + "Вы погибли! Ваши финальные характеристики:" + RESET);
                    game.getPlayer().showInfo();
                    System.out.println(RED_BOLD_BRIGHT + "Игра окончена." + RESET);
                    break;
                }

                if (playerTurnEnded) {
                    game.getPlayer().regenerateMana();
                    player.performAlliesActions(room);
                    game.getPlayer().updateEffects();
                    game.getPlayer().applyEffectDamage();

                    for (int i = 0; i < getEnemies().size(); i++) {
                        Entity enemy = getEnemies().get(i);
                        if (enemy.isAlive()) {
                            enemy.updateEffects();
                            enemy.applyEffectDamage();

                            if (enemy instanceof SupremeMage) {
                                SupremeMage supremeMage = (SupremeMage) enemy;
                                if (!supremeMage.isRitualCompleted()) {
                                    supremeMage.performAction(game, room);
                                    break;
                                }
                            }
                            enemy.performAction(game, room);

                            if (!game.getPlayer().isAlive()) {
                                System.out.println(RED_BOLD_BRIGHT + "Вы погибли! Ваши финальные характеристики:" + RESET);
                                game.getPlayer().showInfo();
                                handlePlayerDeath(game);
                                return;
                            }
                        } else {
                            enemy.rip(game);
                            removeEnemy(enemy);
                            game.incrementMonstersKilled();
                            i--;
                        }
                    }

                    if (!hasEnemies()) {
                        System.out.println(GREEN_BOLD_BRIGHT + "Вы победили всех врагов!" + RESET);
                        clearEnemies();
                        break;
                    }
                    Thread.sleep(0);
                    System.out.println(YELLOW_BOLD_BRIGHT + "Нажмите Enter, чтобы продолжить ход." + RESET);
                    scanner.nextLine();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void handlePlayerDeath(Game game) {
        Scanner scanner = new Scanner(System.in);
        boolean validChoice = false;

        while (!validChoice) {
            System.out.println(RED_BOLD_BRIGHT + "\n=== ИГРА ОКОНЧЕНА ===" + RESET);
            System.out.println(BLUE_BOLD_BRIGHT + "1. Начать новую игру");
            System.out.println("2. Загрузить сохранение");
            System.out.println("3. Вернуться в главное меню" + RESET);
            System.out.print(GREEN_BOLD_BRIGHT + "\nВыберите действие (1-3): " + RESET);

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        System.out.print(GREEN_BOLD_BRIGHT + "Введите имя нового героя: " + RESET);
                        String newPlayerName = scanner.nextLine();
                        Player newPlayer = new Player(newPlayerName, 1);
                        game = new Game(newPlayer);
                        game.start();
                        validChoice = true;
                        break;
                    case 2:
                        System.out.print(GREEN_BOLD_BRIGHT + "Введите имя файла сохранения: " + RESET);
                        String filename = scanner.nextLine();
                        try {
                            game.loadGame(filename);
                            game.start();
                            validChoice = true;
                        } catch (Exception e) {
                            System.out.println(RED_BOLD_BRIGHT + "Ошибка при загрузке сохранения: " + e.getMessage() + RESET);
                            System.out.println(WHITE_BOLD_BRIGHT + "Нажмите Enter, чтобы продолжить..." + RESET);
                            scanner.nextLine();
                        }
                        break;
                    case 3:
                        MainMenu menu = new MainMenu();
                        menu.displayMenu();
                        validChoice = true;
                        break;
                    default:
                        System.out.println(RED_BOLD_BRIGHT + "Пожалуйста, выберите корректный вариант (1-3)" + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED_BOLD_BRIGHT + "Пожалуйста, введите корректное число" + RESET);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void removeEnemy(Entity enemy) {
        if (enemies.contains(enemy)) {
            enemies.remove(enemy);
        } else {
            System.out.println(enemy.getName() + " is not in this room.");
        }
    }

    private Entity chooseRandomEnemy() {
        if (enemies.isEmpty()) return null;
        Random random = new Random();
        return enemies.get(random.nextInt(enemies.size()));
    }

    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE
    public static final String RESET = "\033[0m";  // Text Reset

    protected void handleEmptyRoom(Game game, Room room) {
        Player player = game.getPlayer();
        boolean continueChoosing = true;

        while (continueChoosing) {
            System.out.println(CYAN_BOLD + "\n╔════════════════════════════════════════════╗");
            System.out.println("║            ПУСТАЯ КОМНАТА                  ║");
            System.out.println("╠════════════════════════════════════════════╣" + RESET);
            System.out.println(YELLOW_BOLD + "║           Доступные действия:              ║" + RESET);
            System.out.println("║                                            ║");
            System.out.println(WHITE_BOLD_BRIGHT + "║  1. ⟹  Пойти в следующую комнату          ║");
            System.out.println("║  2. ⟸  Пойти в предыдущую комнату         ║");
            System.out.println("║  3. 🎒 Использовать инвентарь              ║");
            System.out.println("║  4. ℹ️  Информация о игроке                ║");
            System.out.println("║  5. 👥 Информация о союзниках              ║");
            System.out.println("║  6. 💾 Сохранить/Загрузить игру            ║" + RESET);
            System.out.println(CYAN_BOLD + "╠════════════════════════════════════════════╣" + RESET);
            System.out.print(GREEN_BOLD_BRIGHT + "║  Введите номер действия (1-6): " + RESET);

            Scanner scanner = new Scanner(System.in);

            try {
                String input = scanner.nextLine();
                int choice;

                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println(CYAN_BOLD + "╠════════════════════════════════════════════╣" + RESET);
                    System.out.println(RED_BOLD_BRIGHT + "║     Ошибка: Введите число от 1 до 6!       ║" + RESET);
                    System.out.println(CYAN_BOLD + "╚════════════════════════════════════════════╝" + RESET);
                    continue;
                }

                switch (choice) {
                    case 1:
                        System.out.println(CYAN_BOLD + "╠════════════════════════════════════════════╣" + RESET);
                        System.out.println(GREEN_BOLD_BRIGHT + "║      Переход в следующую комнату...       ║" + RESET);
                        System.out.println(CYAN_BOLD + "╚════════════════════════════════════════════╝" + RESET);
                        game.moveForward();
                        continueChoosing = false;
                        break;
                    case 2:
                        System.out.println(CYAN_BOLD + "╠════════════════════════════════════════════╣" + RESET);
                        System.out.println(GREEN_BOLD_BRIGHT + "║      Переход в предыдущую комнату...       ║" + RESET);
                        System.out.println(CYAN_BOLD + "╚════════════════════════════════════════════╝" + RESET);
                        game.moveBackward();
                        continueChoosing = false;
                        break;
                    case 3:
                        System.out.println(CYAN_BOLD + "╠════════════════════════════════════════════╣" + RESET);
                        useInventory(player, room);
                        break;
                    case 4:
                        System.out.println(CYAN_BOLD + "╠════════════════════════════════════════════╣" + RESET);
                        player.showInfo();
                        break;
                    case 5:
                        System.out.println(CYAN_BOLD + "╠════════════════════════════════════════════╣" + RESET);
                        player.showAlliesInfo(player.getAllies());
                        break;
                    case 6:
                        System.out.println(CYAN_BOLD + "╠════════════════════════════════════════════╣" + RESET);
                        handleSaveLoadMenu(game, scanner);
                        break;
                    default:
                        System.out.println(CYAN_BOLD + "╠════════════════════════════════════════════╣" + RESET);
                        System.out.println(RED_BOLD_BRIGHT + "║   Ошибка: Неверный выбор! (1-6)           ║" + RESET);
                        System.out.println(CYAN_BOLD + "╚════════════════════════════════════════════╝" + RESET);
                        break;
                }
            } catch (Exception e) {
                System.out.println(RED_BOLD_BRIGHT + "Произошла ошибка при вводе. Попробуйте снова." + RESET);
            }
        }
    }

    private static final String CYAN_BOLD = "\u001B[1;36m";
    private static final String YELLOW_BOLD = "\u001B[1;33m";

    private void handleSaveLoadMenu(Game game, Scanner scanner) {
        File saveDir = new File("saves");
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }

        while (true) {
            System.out.println(YELLOW_BOLD_BRIGHT + "\n=== МЕНЮ СОХРАНЕНИЯ/ЗАГРУЗКИ ===" + RESET);
            System.out.println(BLUE_BOLD_BRIGHT + "1. Сохранить игру");
            System.out.println("2. Загрузить игру");
            System.out.println("3. Вернуться назад" + RESET);
            System.out.print(GREEN_BOLD_BRIGHT + "Введите ваш выбор (1-3): " + RESET);

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println(YELLOW_BOLD_BRIGHT + "\nДоступные слоты сохранения:" + RESET);
                    File[] existingSaves = saveDir.listFiles((dir, name) -> name.endsWith(".sav"));
                    if (existingSaves != null && existingSaves.length > 0) {
                        for (int i = 0; i < existingSaves.length; i++) {
                            System.out.println(CYAN_BOLD_BRIGHT + (i + 1) + ". " + existingSaves[i].getName() + RESET);
                        }
                        System.out.println(CYAN_BOLD_BRIGHT + (existingSaves.length + 1) + ". Создать новое сохранение" + RESET);
                    } else {
                        System.out.println(CYAN_BOLD_BRIGHT + "1. Создать новое сохранение" + RESET);
                    }

                    System.out.print(GREEN_BOLD_BRIGHT + "\nВыберите слот для сохранения (0 для отмены): " + RESET);
                    int saveSlot = scanner.nextInt();
                    scanner.nextLine();

                    if (saveSlot == 0) {
                        break;
                    }

                    String saveFilename;
                    if (existingSaves != null && saveSlot <= existingSaves.length) {
                        saveFilename = existingSaves[saveSlot - 1].getName().replace(".sav", "");
                        System.out.println(YELLOW_BOLD_BRIGHT + "Перезапись сохранения: " + saveFilename + RESET);
                    } else {
                        System.out.print(GREEN_BOLD_BRIGHT + "Введите имя для нового сохранения: " + RESET);
                        saveFilename = scanner.nextLine();
                    }

                    game.saveGame(saveFilename);
                    System.out.println(GREEN_BOLD_BRIGHT + "Игра успешно сохранена!" + RESET);
                    break;

                case 2:
                    File[] saveFiles = saveDir.listFiles((dir, name) -> name.endsWith(".sav"));
                    if (saveFiles == null || saveFiles.length == 0) {
                        System.out.println(RED_BOLD_BRIGHT + "Нет доступных сохранений." + RESET);
                        break;
                    }

                    System.out.println(YELLOW_BOLD_BRIGHT + "\nДоступные сохранения:" + RESET);
                    for (int i = 0; i < saveFiles.length; i++) {
                        System.out.println(CYAN_BOLD_BRIGHT + (i + 1) + ". " + saveFiles[i].getName() + RESET);
                    }

                    System.out.print(GREEN_BOLD_BRIGHT + "Выберите номер сохранения (0 для отмены): " + RESET);
                    int loadChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (loadChoice > 0 && loadChoice <= saveFiles.length) {
                        try {
                            game.loadGame(saveFiles[loadChoice - 1].getName());
                            System.out.println(GREEN_BOLD_BRIGHT + "Игра успешно загружена!" + RESET);
                        } catch (Exception e) {
                            System.out.println(RED_BOLD_BRIGHT + "Ошибка при загрузке: " + e.getMessage() + RESET);
                        }
                    } else if (loadChoice != 0) {
                        System.out.println(RED_BOLD_BRIGHT + "Неверный выбор!" + RESET);
                    }
                    break;

                case 3:
                    return;

                default:
                    System.out.println(RED_BOLD_BRIGHT + "Неверный выбор! Попробуйте снова." + RESET);
            }
        }
    }
    public static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    public void useInventory(Player player, Room room) {
        Scanner scanner = new Scanner(System.in);
        List<Entity> enemies = room.getEnemies();

        // Красивый заголовок
        System.out.println("\n" + PURPLE_BOLD_BRIGHT + "╔════════════════════════════════╗");
        System.out.println("║         INVENTORY MENU         ║");
        System.out.println("╚════════════════════════════════╝" + RESET);

        // Показ врагов
        if (!enemies.isEmpty()) {
            System.out.println(RED_BOLD_BRIGHT + "\n◆ Current Enemies ◆" + RESET);
            for (int i = 0; i < enemies.size(); i++) {
                System.out.println(RED + "  " + (i + 1) + ". " + enemies.get(i).getName() +
                        " [HP: " + enemies.get(i).getHealth() + "]" + RESET);
            }
        }

        // Показ инвентаря
        System.out.println(BLUE_BOLD_BRIGHT + "\n◆ Your Inventory ◆" + RESET);
        player.getInventory().displayInventory();

        System.out.println(YELLOW_BOLD_BRIGHT + "\n◆ Your Amulets ◆" + RESET);
        player.getInventory().displayAmulets();

        // Меню действий
        System.out.println(GREEN_BOLD_BRIGHT + "\n◆ Available Actions ◆" + RESET);
        System.out.println(GREEN + "  1. ⚔ Equip Weapon");
        System.out.println("  2. ⚔ Unequip Weapon");
        System.out.println("  3. 🎒 Use Item" + RESET);

        if (!player.getInventory().getAmulets().isEmpty()) {
            System.out.println(YELLOW + "  4. 📿 Equip Amulet");
            System.out.println("  5. 📿 Unequip Amulet" + RESET);
        }

        System.out.print(CYAN_BOLD_BRIGHT + "\n➤ Enter your choice (1-" +
                (player.getInventory().getAmulets().isEmpty() ? "3" : "5") + "): " + RESET);

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println(BLUE_BOLD_BRIGHT + "\n◆ Choose a weapon to equip ◆" + RESET);
                for (int i = 0; i < player.getInventory().getItems().size(); i++) {
                    System.out.println(BLUE + "  " + (i + 1) + ". " +
                            player.getInventory().getItems().get(i).getName() + RESET);
                }
                System.out.print(CYAN_BOLD_BRIGHT + "\n➤ Select weapon number: " + RESET);
                int weaponIndex = scanner.nextInt() - 1;
                scanner.nextLine();

                if (weaponIndex >= 0 && weaponIndex < player.getInventory().getItems().size()) {
                    Item weapon = player.getInventory().getItems().get(weaponIndex);
                    System.out.println(BLUE_BOLD_BRIGHT + "\n◆ Choose hand to equip ◆" + RESET);
                    System.out.println(BLUE + "  1. ← Left Hand");
                    System.out.println("  2. → Right Hand" + RESET);
                    System.out.print(CYAN_BOLD_BRIGHT + "\n➤ Select hand: " + RESET);
                    int handChoice = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (handChoice == 0) {
                        player.getInventory().equipWeapon(weapon, "left");
                        System.out.println(GREEN_BOLD_BRIGHT + "✔ Weapon equipped to left hand!" + RESET);
                    } else if (handChoice == 1) {
                        player.getInventory().equipWeapon(weapon, "right");
                        System.out.println(GREEN_BOLD_BRIGHT + "✔ Weapon equipped to right hand!" + RESET);
                    } else {
                        System.out.println(RED_BOLD_BRIGHT + "✘ Invalid hand choice!" + RESET);
                    }
                } else {
                    System.out.println(RED_BOLD_BRIGHT + "✘ Invalid weapon choice!" + RESET);
                }
                break;

            case 2:
                System.out.println(BLUE_BOLD_BRIGHT + "\n◆ Choose hand to unequip ◆" + RESET);
                System.out.println(BLUE + "  1. ← Left Hand");
                System.out.println("  2. → Right Hand" + RESET);
                System.out.print(CYAN_BOLD_BRIGHT + "\n➤ Select hand: " + RESET);
                int handChoicee = scanner.nextInt() - 1;
                scanner.nextLine();

                if (handChoicee == 0) {
                    player.getInventory().unequipWeapon("left");
                    System.out.println(GREEN_BOLD_BRIGHT + "✔ Weapon unequipped from left hand!" + RESET);
                } else if (handChoicee == 1) {
                    player.getInventory().unequipWeapon("right");
                    System.out.println(GREEN_BOLD_BRIGHT + "✔ Weapon unequipped from right hand!" + RESET);
                } else {
                    System.out.println(RED_BOLD_BRIGHT + "✘ Invalid hand choice!" + RESET);
                }
                break;

            case 3:
                System.out.println(BLUE_BOLD_BRIGHT + "\n◆ Choose an item to use ◆" + RESET);
                for (int i = 0; i < player.getInventory().getItems().size(); i++) {
                    System.out.println(BLUE + "  " + (i + 1) + ". " +
                            player.getInventory().getItems().get(i).getName() + RESET);
                }
                System.out.print(CYAN_BOLD_BRIGHT + "\n➤ Select item number: " + RESET);
                int itemIndex = scanner.nextInt() - 1;
                scanner.nextLine();

                if (itemIndex >= 0 && itemIndex < player.getInventory().getItems().size()) {
                    Item item = player.getInventory().getItems().get(itemIndex);

                    if (item instanceof Consumable || item instanceof MagicalFruit) {
                        if (item instanceof Consumable) {
                            ((Consumable) item).applyEffect(player);
                        } else {
                            ((MagicalFruit) item).applyEffect(player);
                        }
                        player.getInventory().removeItem(item);
                        System.out.println(GREEN_BOLD_BRIGHT + "✔ Item used successfully!" + RESET);
                    } else if (item instanceof OneTargetHitItems) {
                        System.out.println(RED_BOLD_BRIGHT + "\n◆ Choose your target ◆" + RESET);
                        for (int i = 0; i < enemies.size(); i++) {
                            System.out.println(RED + "  " + (i + 1) + ". " +
                                    enemies.get(i).getName() + RESET);
                        }
                        System.out.print(CYAN_BOLD_BRIGHT + "\n➤ Select target (1-" +
                                enemies.size() + "): " + RESET);
                        int targetIndex = scanner.nextInt() - 1;
                        scanner.nextLine();
                        if (targetIndex >= 0 && targetIndex < enemies.size()) {
                            Entity target = enemies.get(targetIndex);
                            ((OneTargetHitItems) item).applyEffect(room, target);
                            player.getInventory().removeItem(item);
                            System.out.println(GREEN_BOLD_BRIGHT + "✔ Item used on target successfully!" + RESET);
                        } else {
                            System.out.println(RED_BOLD_BRIGHT + "✘ Invalid target choice!" + RESET);
                        }
                    } else if (item instanceof UniqueItem) {
                        // Анимация использования уникального предмета
                        System.out.println(PURPLE_BOLD_BRIGHT + "\n★ Using Unique Item ★" + RESET);
                        String loadingText = "Channeling ancient power";
                        for (int i = 0; i < 3; i++) {
                            System.out.print("\r" + PURPLE + loadingText + ".".repeat(i + 1) + " ".repeat(3 - i) + RESET);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println();

                        ((UniqueItem) item).applyEffect(player);
                        player.getInventory().removeItem(item);

                        // Эффект успешного использования
                        String successText = "✧ Ancient power flows through you ✧";
                        for (int i = 0; i < successText.length(); i++) {
                            System.out.print(PURPLE_BOLD_BRIGHT + successText.charAt(i) + RESET);
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("\n" + GREEN_BOLD_BRIGHT + "✔ Unique item used successfully!" + RESET);
                    } else {
                        System.out.println(RED_BOLD_BRIGHT + "✘ This item cannot be used!" + RESET);
                    }
                } else {
                    System.out.println(RED_BOLD_BRIGHT + "✘ Invalid item choice!" + RESET);
                }
                break;
            case 4:
                if (!player.getInventory().getAmulets().isEmpty()) {
                    System.out.println(YELLOW_BOLD_BRIGHT + "\n◆ Choose an amulet to equip ◆" + RESET);
                    for (int i = 0; i < player.getInventory().getAmulets().size(); i++) {
                        System.out.println(YELLOW + "  " + (i + 1) + ". " +
                                player.getInventory().getAmulets().get(i).getName() + RESET);
                    }
                    System.out.print(CYAN_BOLD_BRIGHT + "\n➤ Select amulet number: " + RESET);
                    int amuletIndex = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (amuletIndex >= 0 && amuletIndex < player.getInventory().getAmulets().size()) {
                        Amulet amulet = player.getInventory().getAmulets().get(amuletIndex);
                        player.getInventory().equipAmulet(amulet, player);
                        System.out.println(GREEN_BOLD_BRIGHT + "✔ Amulet equipped successfully!" + RESET);
                    } else {
                        System.out.println(RED_BOLD_BRIGHT + "✘ Invalid amulet choice!" + RESET);
                    }
                } else {
                    System.out.println(RED_BOLD_BRIGHT + "✘ No amulets available!" + RESET);
                }
                break;

            case 5:
                if (!player.getInventory().getAmulets().isEmpty()) {
                    player.getInventory().unequipAmulet(player);
                    System.out.println(GREEN_BOLD_BRIGHT + "✔ Amulet unequipped successfully!" + RESET);
                } else {
                    System.out.println(RED_BOLD_BRIGHT + "✘ No amulet equipped!" + RESET);
                }
                break;

            default:
                System.out.println(RED_BOLD_BRIGHT + "✘ Invalid choice!" + RESET);
                break;
        }
    }

    private static final String SCHEST = """
            \033[0;36m
            *******************************************************************************
                      |                   |                  |                     |
             _________|________________.=""_;=.______________|_____________________|_______
            |                   |  ,-"_,=""     `"=.|                  |
            |___________________|__"=._o`"-._        `"=.______________|___________________
                      |                `"=._o`"=._      _`"=._                     |
             _________|_____________________:=._o "=._."_.-="'"=.__________________|_______
            |                   |    __.--" , ; `"=._o." ,-""\"-._ ".   |
            |___________________|_._"  ,. .` ` `` ,  `"-._"-._   ". '__|___________________
                      |           |o`"=._` , "` `; .". ,  "-._"-._; ;              |
             _________|___________| ;`-.o`"=._; ." ` '`."\\` . "-._ /_______________|_______
            |                   | |o;    `"-.o`"=._``  '` " ,__.--o;   |
            |___________________|_| ;     (#) `-.o `"=.`_.--"_o.-; ;___|___________________
            ____/______/______/___|o;._    "      `".o|o_.--"    ;o;____/______/______/____
            /______/______/______/_"=._o--._        ; | ;        ; ;/______/______/______/_
            ____/______/______/______/__"=._o--._   ;o|o;     _._;o;____/______/______/____
            /______/______/______/______/____"=._o._; | ;_.--"o.--"_/______/______/______/_
            ____/______/______/______/______/_____"=.o|o_.--""___/______/______/______/____
            /______/______/______/______/______/______/______/______/______/______/_______/
            *******************************************************************************
            \033[0m
            """;

    public void openChest(Game game) {
        Player player = game.getPlayer();
        double luck = player.getLuck();
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("\n" + GREEN_BOLD_BRIGHT + "Вы открыли сундук! 🎉" + RESET);
            System.out.println(SCHEST);
            Thread.sleep(1500);

            // Гарантированный первый предмет
            Item firstItem = getRandomItem(game);
            player.getInventory().addItem(firstItem);
            System.out.println(GREEN_BOLD_BRIGHT + "Вы получили: " + firstItem.getName() + " 🏆" + RESET);
            Thread.sleep(1500);

            // Второй предмет зависит от удачи
            if (random.nextDouble() < luck * 0.2) {
                Item secondItem = getRandomItem(game);
                player.getInventory().addItem(secondItem);
                System.out.println(GREEN_BOLD_BRIGHT + "Благодаря удаче, вы также нашли: " + secondItem.getName() + " 🍀" + RESET);
                Thread.sleep(1500);
            }

            // Находим золото
            int rel = random.nextInt(25);
            int goldFound = (int) (50 + (luck * 100) + rel);
            player.setGold(player.getGold() + goldFound);
            System.out.println(GREEN_BOLD_BRIGHT + "Вы нашли " + goldFound + " золота! 💰" + RESET);
            Thread.sleep(1500);

            int lockpickCount = player.getInventory().getLockpickCount();

            while (player.getInventory().getLockpickCount() > 0) {
                System.out.println("\n" + GREEN_BOLD_BRIGHT + "У вас есть " + player.getInventory().getLockpickCount() + " отмычек. Хотите использовать одну для получения дополнительного предмета? (да/нет)" + RESET);
                String choice = scanner.nextLine().trim().toLowerCase();

                if (choice.equals("да")) {
                    System.out.println(GREEN_BOLD_BRIGHT + "Пытаетесь использовать отмычку..." + RESET);
                    Thread.sleep(2000);

                    double successChance = 0.5 + (luck * 0.15);
                    if (random.nextDouble() < successChance) {
                        Item additionalItem = getRandomItem(game);
                        player.getInventory().addItem(additionalItem);
                        System.out.println(GREEN_BOLD_BRIGHT + "Успех! Вы получили: " + additionalItem.getName() + " 🎊" + RESET);
                        Thread.sleep(1500);
                    } else {
                        System.out.println(RED_BOLD_BRIGHT + "Не удалось получить дополнительный предмет с помощью отмычки." + RESET);
                        Thread.sleep(1500);
                    }
                    player.getInventory().removeLockpick();
                } else if (choice.equals("нет")) {
                    break;
                } else {
                    System.out.println(RED_BOLD_BRIGHT + "Пожалуйста, введите 'да' или 'нет'" + RESET);
                    continue;
                }
            }

            // Добавляем явное ожидание ввода перед переходом к следующему состоянию
            System.out.println(WHITE_BOLD_BRIGHT + "Нажмите Enter для продолжения..." + RESET);
            scanner.nextLine();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Item getRandomItem(Game game) {
        if (!game.isSecondGamePart()) {
            return FirstLevelWeaponFactory.createRandomItem();
        } else {
            return SecondLevelWeaponFactory.createRandomItem();
        }
    }
}

