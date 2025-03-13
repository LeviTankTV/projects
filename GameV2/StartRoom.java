package GameV2;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StartRoom extends Room {
    private Map<String, Item> shopInventory;
    private Player player;

    // ANSI color codes
    public static final String RESET = "\033[0m";
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m";
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m";
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";
    public static final String CYAN_BOLD = "\033[1;36m";
    public static final String PURPLE_BOLD = "\033[1;35m";

    // ASCII art for the dungeon shop
    private static final String SHOP_ART = PURPLE_BOLD + """
                    
             /\\                                                        /\\
            /  \\    _____                                      _____  /  \\
           /    \\  |     |  ___   ___   ___   ___   ___   ___ |     |/    \\
          /      \\ |     | |___|_|___| |___| |___| |___| |___||     /      \\
         /   /\\   \\|     |  ___ @ ___ @ ___ @ ___ @ ___ @ ___ |    /   /\\   \\
        /   /  \\   \\     | |   | |   | |   | |   | |   | |   ||   /   /  \\   \\
       /   /    \\   \\    | |___| |___| |___| |___| |___| |___||  /   /    \\   \\
      /   /      \\   \\   |    ┌─────────────────────────────┐ | /   /      \\   \\
     /   /   /\\   \\   \\  |    │                             │ |/  /   /\\   \\   \\
    /   /   /  \\   \\   \\ |    │     МАГАЗИН ПОДЗЕМЕЛЬЯ      │/   /   /  \\   \\   \\
   /   /   /    \\   \\   \\|    │                             |   /   /    \\   \\   \\
  /___/___/      \\___\\___\\    └─────────────────────────────┘  /___/      \\___\\___\\
    """ + RESET;

    public StartRoom(Player player) {
        super();
        this.player = player;
        shopInventory = new HashMap<>();
        initializeShopItems();
    }

    private void initializeShopItems() {
        // Add items to the shop (values are already set in the item constructors)
        shopInventory.put("Minor Healing Potion", new MinorHealingPotion());
        shopInventory.put("Minor Experience Potion", new MinorExperiencePotion());
        shopInventory.put("Minor Mana Potion", new MinorManaPotion());
        shopInventory.put("Short Bow", new ShortBow());
        shopInventory.put("Hand Axe", new HandAxe());
        shopInventory.put("Short Sword", new ShortSword());
        shopInventory.put("Wooden Staff", new WoodenStaff());
        shopInventory.put("Flame Gloves", new FlameGloves());
        shopInventory.put("Dagger", new Dagger());
        shopInventory.put("Lock Pick", new LockPick());
    }

    @Override
    public void playerTurn(Game game, Room room) {
        System.out.println(SHOP_ART);
        System.out.println(YELLOW_BOLD_BRIGHT + "Добро пожаловать в Магазин Подземелья! Здесь вы можете приобрести снаряжение для своих приключений." + RESET);
        showShopMenu(game);
    }

    private void showShopMenu(Game game) {
        boolean continueShopping = true;
        Scanner scanner = new Scanner(System.in);

        while (continueShopping) {
            System.out.println(YELLOW_BOLD_BRIGHT + "У вас " + player.getGold() + " золота." + RESET);
            System.out.println(WHITE_BOLD_BRIGHT + "Выберите действие:" + RESET);
            System.out.println(WHITE_BOLD_BRIGHT + "1. Посмотреть товары" + RESET);
            System.out.println(WHITE_BOLD_BRIGHT + "2. Купить предмет" + RESET);
            System.out.println(WHITE_BOLD_BRIGHT + "3. Продать предмет" + RESET);
            System.out.println(WHITE_BOLD_BRIGHT + "4. Пойти в следующую комнату" + RESET);
            System.out.println(WHITE_BOLD_BRIGHT + "5. Использовать инвентарь" + RESET);

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

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
                    continueShopping = false;
                    game.moveForward();
                    break;
                case 5:
                    useInventory(player, this);
                    break;
                default:
                    System.out.println(RED_BOLD_BRIGHT + "Неверный выбор! Попробуйте еще раз." + RESET);
            }
        }
    }

    private void displayItems() {
        System.out.println(WHITE_BOLD_BRIGHT + "Доступные товары:" + RESET);
        int index = 1;
        for (Map.Entry<String, Item> entry : shopInventory.entrySet()) {
            System.out.println(WHITE_BOLD_BRIGHT + index + ". " + entry.getKey() + " - " + YELLOW_BOLD_BRIGHT + entry.getValue().getValue() + " золота" + RESET);
            index++;
        }
    }

    private void buyItem(Scanner scanner) {
        displayItems();
        System.out.println(WHITE_BOLD_BRIGHT + "Введите индекс товара, который хотите купить:" + RESET);

        int itemIndex = scanner.nextInt();
        scanner.nextLine();

        if (itemIndex >= 1 && itemIndex <= shopInventory.size()) {
            int index = 1;
            for (Map.Entry<String, Item> entry : shopInventory.entrySet()) {
                if (index == itemIndex) {
                    Item item = entry.getValue();
                    int price = item.getValue();

                    if (player.getGold() >= price) {
                        player.setGold(player.getGold() - price);
                        player.getInventory().addItem(item);
                        System.out.println(GREEN_BOLD_BRIGHT + "Вы купили " + item.getName() + "!" + RESET);
                        return;
                    } else {
                        System.out.println(RED_BOLD_BRIGHT + "Недостаточно золота!" + RESET);
                        return;
                    }
                }
                index++;
            }
        } else {
            System.out.println(RED_BOLD_BRIGHT + "Неверный индекс!" + RESET);
        }
    }

    private void sellItem(Scanner scanner) {
        System.out.println(WHITE_BOLD_BRIGHT + "Ваш инвентарь:" + RESET);
        player.getInventory().displayInventory();

        System.out.println(WHITE_BOLD_BRIGHT + "Введите индекс товара, который вы хотите продать:" + RESET);
        int itemIndex = scanner.nextInt();
        scanner.nextLine();

        if (itemIndex >= 1 && itemIndex <= player.getInventory().getItems().size()) {
            Item item = player.getInventory().getItems().get(itemIndex - 1);

            if (item != null) {
                int sellPrice = (int) Math.round(item.getValue() * 0.33);
                player.getInventory().removeItem(item);
                player.setGold(player.getGold() + sellPrice);
                System.out.println(GREEN_BOLD_BRIGHT + "Вы продали " + item.getName() + " за " + sellPrice + " золота!" + RESET);
            } else {
                System.out.println(RED_BOLD_BRIGHT + "Товар не найден в инвентаре!" + RESET);
            }
        } else {
            System.out.println(RED_BOLD_BRIGHT + "Неверный индекс!" + RESET);
        }
    }
}