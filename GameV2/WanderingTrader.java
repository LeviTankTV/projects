package GameV2;

import java.util.*;

public class WanderingTrader extends Room {
    private Map<String, Map<String, Item>> traderInventory;
    private Player player;
    private boolean isSecondGamePart; // Флаг для проверки, является ли это второй частью игры
    private static final String RESET = "\u001B[0m";
    private static final String WHITE_BOLD_BRIGHT = "\u001B[1;97m";
    private static final String YELLOW_BOLD_BRIGHT = "\u001B[1;93m";
    private static final String CYAN_BOLD_BRIGHT = "\u001B[1;96m";
    private static final String GREEN_BOLD_BRIGHT = "\u001B[1;92m";
    private static final String PURPLE_BOLD_BRIGHT = "\u001B[1;95m";
    private static final String RED_BOLD_BRIGHT = "\u001B[1;91m";

    public WanderingTrader(Player player, Game game) {
        super();
        this.player = player;
        this.isSecondGamePart = game.isSecondGamePart();
        traderInventory = new HashMap<>();

        // Инициализация инвентаря торговца на основе части игры
        initializeTraderItems();
    }

    private void initializeTraderItems() {
        // Создание категорий
        Map<String, Item> spells = new HashMap<>();
        Map<String, Item> potions = new HashMap<>();
        Map<String, Item> equipment = new HashMap<>();

        // Товары, доступные в первой части игры
        if (!isSecondGamePart) {
            spells.put("Heal Cast Book", new HealCastBook());
            spells.put("Fireball Cast Book", new FireballCastBook());
        } else {
            // Если это вторая часть игры, добавляем дополнительные предметы
            spells.put("Heal Cast Book", new HealCastBook());
            spells.put("Fireball Cast Book", new FireballCastBook());
            spells.put("Mana Level Increase Book", new ManaLevelIncreaseBook());
            spells.put("Shadow Bolt Cast Book", new ShadowBoltCastBook());
            spells.put("Ice Spike Cast Book", new IceSpikeCastBook());
            spells.put("Earthquake Cast Book", new EarthquakeCastBook());
            spells.put("Blood Magic Spell Book", new BloodMagicSpellBook());
            spells.put("Arcane Missile Spell Book", new ArcaneMissileCastBook());

            potions.put("Minor Healing Potion", new MinorHealingPotion());
            potions.put("Minor Experience Potion", new MinorExperiencePotion());
            potions.put("Minor Mana Potion", new MinorManaPotion());
            potions.put("Greater Experience Potion", new GreaterExperiencePotion());
            potions.put("Greater Mana Potion", new GreaterManaPotion());
            potions.put("Greater Healing Potion", new GreaterHealingPotion());
            potions.put("Grand Experience Potion", new GrandExperiencePotion());

            equipment.put("Torch", new Torch());
            equipment.put("Golden Key", new GoldenKey());
            equipment.put("Amulet of Evasion", new AmuletOfEvasion());
            equipment.put("Amulet of Increased Mana Regeneration", new AmuletOfIncreasedManaRegeneration());
            equipment.put("Amulet of Luck", new AmuletOfLuck());
            equipment.put("Explosion Dagger", new ExplosionDagger());
            equipment.put("Ice Dagger", new IceDagger());
            equipment.put("Fireball Scroll", new FireballScroll());
            equipment.put("Ice Scroll", new IceScroll());
            equipment.put("Weaken Scroll", new WeakenScroll());
            equipment.put("Mark Scroll", new MarkScroll());
            equipment.put("Ultra Scroll", new UltraScroll());
        }

        // Добавление категорий в инвентарь торговца
        traderInventory.put("Заклинания", spells);
        traderInventory.put("Зелья", potions);
        traderInventory.put("Амуниция", equipment);
    }

    private static final String TRADER_SHOP_ART = """
        \033[0;36m
                   ⭐ Magic Items & Rare Goods ⭐
             .▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄.
             █                                         █
             █     ╭━━━━━━━━━━━━━━━━━━━━━━━━━━━━╮      █
             █     ┃  W A N D E R I N G         ┃      █
             █     ┃      T R A D E R           ┃      █
             █     ╰━━━━━━━━━━━━━━━━━━━━━━━━━━━━╯      █
             █        ╭─────╮    ╭─────╮   ╭─────╮     █
             █     🕯️  │ ~∆~ │    │ ◊◊◊ │   │ ○○○ │    █
             █        ╰─────╯    ╰─────╯   ╰─────╯     █
             █     ╭─────────╮  ╭─────────╮            █
             █     │  ≈≈≈≈≈  │  │   ∴∴∴   │    🕯️      █
             █     ╰─────────╯  ╰─────────╯            █
             █         🕯️                              █
             █     ╭──────╮  ╭──────╮  ╭──────╮        █
             █     │ ███  │  │ ▓▓▓  │  │ ░░░  │        █
             █     ╰──────╯  ╰──────╯  ╰──────╯        █
             █                                         █
             █▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█
                    ▀███████████████████████▀
                        ▀██████████████▀
        \033[0m
        """;

    @Override
    public void playerTurn(Game game, Room room) {
        System.out.println(TRADER_SHOP_ART);
        System.out.println(WHITE_BOLD_BRIGHT + "╔═══════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Вы зашли в магазин блуждающего торговца. Здесь вы можете купить или продать предметы. ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════╝" + RESET);
        showTraderMenu(game);
    }

    private void showTraderMenu(Game game) {
        boolean continueTrading = true;
        Scanner scanner = new Scanner(System.in);

        while (continueTrading) {
            System.out.println(YELLOW_BOLD_BRIGHT + "У вас " + player.getGold() + " золота." + RESET);

            if (!isSecondGamePart) {
                System.out.println(WHITE_BOLD_BRIGHT + "╔═══════════════ МЕНЮ ТОРГОВЦА ═══════════════╗");
                System.out.println("║ 1. Купить Heal Cast Book                    ║");
                System.out.println("║ 2. Купить Fireball Cast Book                ║");
                System.out.println("║ 3. Пойти в следующую комнату                ║");
                System.out.println("║ 4. Вернуться в предыдущую комнату           ║");
                System.out.println("╚═════════════════════════════════════════════╝" + RESET);

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        buySpecificItem("Heal Cast Book");
                        break;
                    case 2:
                        buySpecificItem("Fireball Cast Book");
                        break;
                    case 3:
                        continueTrading = false;
                        game.moveForward();
                        break;
                    case 4:
                        continueTrading = false;
                        game.moveBackward();
                        break;
                    default:
                        System.out.println(RED_BOLD_BRIGHT + "Неверный выбор! Попробуйте еще раз." + RESET);
                }
            } else {
                System.out.println(WHITE_BOLD_BRIGHT + "╔═══════════════ МЕНЮ ТОРГОВЦА ═══════════════╗");
                System.out.println("║ 1. Посмотреть товары                        ║");
                System.out.println("║ 2. Купить предмет                           ║");
                System.out.println("║ 3. Продать предмет                          ║");
                System.out.println("║ 4. Пойти в следующую комнату                ║");
                System.out.println("║ 5. Вернуться в предыдущую комнату           ║");
                System.out.println("╚═════════════════════════════════════════════╝" + RESET);

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        displayItems();
                        break;
                    case 2:
                        buyItem(scanner);
                        break;
                    case 3:
                        sellItem(scanner);
                        break;
                    case 4:
                        continueTrading = false;
                        game.moveForward();
                        break;
                    case 5:
                        continueTrading = false;
                        game.moveBackward();
                        break;
                    default:
                        System.out.println(RED_BOLD_BRIGHT + "Неверный выбор! Попробуйте еще раз." + RESET);
                }
            }
        }
    }

    private void buySpecificItem(String itemName) {
        Map<String, Item> spells = traderInventory.get("Заклинания");
        Item item = spells.get(itemName);

        if (item != null) {
            int price = item.getValue();
            if (player.getGold() >= price) {
                player.setGold(player.getGold() - price);
                player.getInventory().addItem(item);
                System.out.println(GREEN_BOLD_BRIGHT + "Вы купили " + WHITE_BOLD_BRIGHT + item.getName() + GREEN_BOLD_BRIGHT + "!" + RESET);
            } else {
                System.out.println(RED_BOLD_BRIGHT + "Недостаточно золота!" + RESET);
            }
        } else {
            System.out.println(RED_BOLD_BRIGHT + "Товар не найден!" + RESET);
        }
    }

    private void displayItems() {
        System.out.println(WHITE_BOLD_BRIGHT + "╔═══════════════ ДОСТУПНЫЕ ТОВАРЫ ═══════════════╗" + RESET);
        for (Map.Entry<String, Map<String, Item>> category : traderInventory.entrySet()) {
            String categoryColor;
            switch (category.getKey()) {
                case "Заклинания":
                    categoryColor = PURPLE_BOLD_BRIGHT;
                    break;
                case "Зелья":
                    categoryColor = GREEN_BOLD_BRIGHT;
                    break;
                case "Амуниция":
                    categoryColor = CYAN_BOLD_BRIGHT;
                    break;
                default:
                    categoryColor = WHITE_BOLD_BRIGHT;
            }
            System.out.println(categoryColor + "║ Категория: " + category.getKey());
            int index = 1;
            for (Map.Entry<String, Item> entry : category.getValue().entrySet()) {
                System.out.printf(categoryColor + "║ %2d. " + WHITE_BOLD_BRIGHT + "%-30s" + YELLOW_BOLD_BRIGHT + "%5d золота\n" + RESET,
                        index, entry.getKey(), entry.getValue().getValue());
                index++;
            }
        }
        System.out.println(WHITE_BOLD_BRIGHT + "╚═════════════════════════════════════════════════╝" + RESET);
    }

    private void buyItem(Scanner scanner) {
        try {
            displayItems();
            System.out.println(WHITE_BOLD_BRIGHT + "Введите категорию товара (например, 'Заклинания', 'Зелья', 'Амуниция'):" + RESET);
            String category = scanner.nextLine();

            // Проверка на наличие категории
            if (!traderInventory.containsKey(category)) {
                System.out.println(RED_BOLD_BRIGHT + "Неверная категория!" + RESET);
                return;
            }

            System.out.println(WHITE_BOLD_BRIGHT + "Введите индекс товара, который хотите купить:" + RESET);
            int itemIndex = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            Map<String, Item> itemsInCategory = traderInventory.get(category);

            // Проверка на корректность индекса
            if (itemIndex < 1 || itemIndex > itemsInCategory.size()) {
                System.out.println(RED_BOLD_BRIGHT + "Неверный индекс!" + RESET);
                return;
            }

            // Поиск товара по индексу
            int index = 1;
            for (Map.Entry<String, Item> entry : itemsInCategory.entrySet()) {
                if (index == itemIndex) {
                    Item item = entry.getValue();
                    if (item == null) {
                        System.out.println(RED_BOLD_BRIGHT + "Предмет не найден!" + RESET);
                        return;
                    }

                    int price = item.getValue();

                    // Проверка наличия золота у игрока
                    if (player.getGold() < price) {
                        System.out.println(RED_BOLD_BRIGHT + "Недостаточно золота!" + RESET);
                        return;
                    }

                    // Уменьшаем золото игрока и добавляем предмет в инвентарь
                    player.setGold(player.getGold() - price);

                    if (item instanceof Amulet) {
                        player.getInventory().addAmulet((Amulet) item);
                    } else {
                        player.getInventory().addItem(item);
                    }

                    System.out.println(GREEN_BOLD_BRIGHT + "Вы купили " + WHITE_BOLD_BRIGHT + item.getName() + GREEN_BOLD_BRIGHT + "!" + RESET);
                    return;
                }
                index++;
            }
        } catch (InputMismatchException e) {
            System.out.println(RED_BOLD_BRIGHT + "Ошибка: введите корректное числовое значение!" + RESET);
            scanner.nextLine(); // Очистка буфера
        } catch (Exception e) {
            System.out.println(RED_BOLD_BRIGHT + "Произошла ошибка: " + e.getMessage() + RESET);
        }
    }
    private void sellItem(Scanner scanner) {
        try {
            // Проверяем, есть ли предметы в инвентаре
            if (player.getInventory().getItems().isEmpty()) {
                System.out.println(RED_BOLD_BRIGHT + "Ваш инвентарь пуст!" + RESET);
                return;
            }

            System.out.println(WHITE_BOLD_BRIGHT + "Ваш инвентарь:" + RESET);
            player.getInventory().displayInventory();

            System.out.println(WHITE_BOLD_BRIGHT + "Введите индекс товара, который вы хотите продать:" + RESET);

            // Проверка на корректный ввод числа
            if (!scanner.hasNextInt()) {
                System.out.println(RED_BOLD_BRIGHT + "Пожалуйста, введите числовое значение!" + RESET);
                scanner.nextLine(); // Очищаем буфер
                return;
            }

            int itemIndex = scanner.nextInt();
            scanner.nextLine(); // Очищаем буфер

            List<Item> inventory = player.getInventory().getItems();

            // Проверка валидности индекса
            if (itemIndex < 1 || itemIndex > inventory.size()) {
                System.out.println(RED_BOLD_BRIGHT + "Неверный индекс! Доступный диапазон: 1-" + inventory.size() + RESET);
                return;
            }

            // Получаем предмет из инвентаря
            Item item = inventory.get(itemIndex - 1);

            // Проверка на null
            if (item == null) {
                System.out.println(RED_BOLD_BRIGHT + "Товар не найден в инвентаре!" + RESET);
                return;
            }

            try {
                // Вычисляем цену продажи
                int sellPrice = (int) Math.round(item.getValue() * 0.33);

                // Проверка на переполнение при добавлении золота
                if (player.getGold() + sellPrice < 0) {
                    System.out.println(RED_BOLD_BRIGHT + "Ошибка: превышен максимальный лимит золота!" + RESET);
                    return;
                }

                // Удаляем предмет и добавляем золото
                if (player.getInventory().removeItem(item)) {
                    player.setGold(player.getGold() + sellPrice);
                    System.out.println(GREEN_BOLD_BRIGHT + "Вы продали " + WHITE_BOLD_BRIGHT + item.getName() +
                            GREEN_BOLD_BRIGHT + " за " + YELLOW_BOLD_BRIGHT + sellPrice + " золота!" + RESET);
                } else {
                    System.out.println(RED_BOLD_BRIGHT + "Не удалось удалить предмет из инвентаря!" + RESET);
                }
            } catch (ArithmeticException e) {
                System.out.println(RED_BOLD_BRIGHT + "Ошибка при расчете цены продажи!" + RESET);
            }

        } catch (InputMismatchException e) {
            System.out.println(RED_BOLD_BRIGHT + "Ошибка: введите корректное числовое значение!" + RESET);
            scanner.nextLine(); // Очищаем буфер
        } catch (IndexOutOfBoundsException e) {
            System.out.println(RED_BOLD_BRIGHT + "Ошибка: указанный индекс находится вне допустимого диапазона!" + RESET);
        } catch (Exception e) {
            System.out.println(RED_BOLD_BRIGHT + "Произошла непредвиденная ошибка: " + e.getMessage() + RESET);
        }
    }
}
