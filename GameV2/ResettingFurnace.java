package GameV2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ResettingFurnace extends Room {
    private BowFactory bowFactory;
    private AxeFactory axeFactory;
    private SwordFactory swordFactory;
    private GlovesFactory glovesFactory;
    private WandFactory wandFactory;
    private StaffFactory staffFactory;

    public ResettingFurnace() {
        super();
        this.bowFactory = new BowFactory();
        this.axeFactory = new AxeFactory();
        this.swordFactory = new SwordFactory();
        this.glovesFactory = new GlovesFactory();
        this.wandFactory = new WandFactory();
        this.staffFactory = new StaffFactory();
    }

    private static final String RESETTNG_FURNACE_ART = """
      \033[0;33m
      ⠀⠀⠀⠀⠀⠀⠀⠀⠀    ⣾⠟⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠻⣶
          ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀   ⣿
          ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠻⣷⡶⠶⠶⠶⠶⠶⠶⠶⠶⠶⠶⠶⠶⠶⠶⠶⠶⠶⠶⠶⠶⠶⢶⣾⠟
          ⠀⣴⡿⠿⠿⠿⢿⣦⠀⠀⣿⡃⠀⠀⠀⢀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣁⡀⠀⠀⠀⠸⣿⠀
          ⠀⢿⣇⣀⣀⣀⣨⡿⠀⠀⣿⡇⠀⠀⠀⣾⡏⠉⠉⠉⠉⠉⠉⠋⠉⠉⠉⠩⢹⣷⠀⠀⠀⢸⣿⠀
          ⠀⠈⣿⡏⠉⢹⣿⠁⠀⠀⣿⡆⠀⠀⠀⣿⡇⠀⠀⠀⠠⠀⠀⠀⠀⠀⠀⠀⢸⣿⠀⠀⠀ ⠰⣿⠀
          ⠀⠀⣿⣧⣤⣼⣿⠀⠀⠀⣿⡆⠀⠀⠀⢿⣇⣀⣀⣀⣀⣄⣀⣀⣀⣀⣀⣀⣸⡿⠐⠀⠀⢘⣿⠀
          ⣠⡾⠋⠁⣤⠈⠙⢷⡄⠀⣿⡃⠀⠀⠀⠀⠩⠉⠉⠉⠩⠉⠉⠉⠉⠉⠉⠉⠉⠀⠀⠀⠀⢸⣿⠀
          ⣿⠁⠀⠀⣿⣦⡤⠈⣿⠀⣿⡅⠀⠀⢀⣴⠶⠶⠶⠶⠶⠶⠷⠶⠾⠶⠶⢶⠶⣦⡀⠀⠸⣿
          ⢿⣆⠀⠀⠀⠀⠀⣰⡟⠀⣿⡆⠀⢠⡿⠁⠀⠀⠀⠄⠀⠀⣤⣀⠀⠀⠀⠀⠀⠈⢿⡄⠀⢸⣿⠀
          ⠀⠙⣿⣦⣤⣶⣾⠋⠀⠀⣿⠇⠀⢸⡇⠀⠀⠀⠀⠀⠀⣸⡟⠻⣷⣄⠄⠀⠀⠀⢸⡇⠀⢸⣿⠀
          ⠀⠀⣿⡇⠀⢸⣿⠀⠀⠀⣿⡇⠀⢸⡇⠀⠀⠀⠀⣠⣾⠟⠀⠠⠈⠻⣦⠀⠀⠀⢸⡇⠀⢸⣿⠀
          ⠀⠀⠹⣇⠀⠈⠻⠿⠿⠿⣿⡅⠀⢸⡇⠀⠀⣠⡾⠋⠀⠀⣤⣄⠀⠀⢹⣧⠀⠀⢸⡇⠀⠰⣿⠀
          ⠀⠀⠀⠹⢷⣄⣀⣀⣀⣀⣿⠆⠀⢸⡇⡀⠀⣿⠁⠀⣠⣾⠏⠹⣷⡀⠀⣿⡀⠀⢸⡇⠀⢘⣿⠀
          ⠀⠀⠀⠀⠀⠈⠉⠉⠉⠉⣿⡃⠀⢸⡇⠀⠈⣿⣀⣾⠏⠀⡀⠀⠘⣿⢀⣿⠀⠀⢸⡇⠀⢸⣿⠀
          ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣴⡿⠷⠶⠾⠷⠶⠶⠿⢿⠿⢶⠶⢷⠶⠶⠿⠾⠿⠷⠶⠾⠷⠶⠾⢿⣦
          ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠀⠀⠐⠀⠀⠀⠀⠀⠈⠀⠀⠀⠀⠈⠀⠀⠀⠀⠂⠀⠀⠀⠀⠀⠀  ⣿
          ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠿⣦⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣥⣤⣤⣤⣤⣴⠟
      \033[0m
  """;

    @Override
    public void playerTurn(Game game, Room room) {
        Player player = game.getPlayer();
        Scanner scanner = new Scanner(System.in);

        displayRoomInterface();

        if (player.getInventory().hasAllBossWeapons()) {
            handleWhisperCrafting(player, scanner);
        } else {
            handleRegularWeaponResetting(player, scanner);
        }

        handleEmptyRoom(game, this);
    }

    private void displayRoomInterface() {
        System.out.println(RESETTNG_FURNACE_ART);
        System.out.println(YELLOW + "\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║             " + RED + "КОМНАТА ПЕРЕКОВОЧНОЙ ПЕЧИ" + YELLOW + "                  ║");
        System.out.println("╠════════════════════════════════════════════════════════╣");
        System.out.println("║  " + WHITE + "Здесь вы можете переплавить своё оружие и получить    " + YELLOW + "║");
        System.out.println("║  " + WHITE + "новое того же типа. Стоимость переплавки: " + GREEN + "400 золота " + YELLOW + " ║");
        System.out.println("╚════════════════════════════════════════════════════════╝" + RESET + "\n");
    }

    private void handleWhisperCrafting(Player player, Scanner scanner) {
        System.out.println(PURPLE + "╔════════════════════════════════════════════════════════╗");
        System.out.println("║               " + CYAN + "СОЗДАНИЕ WHISPER" + PURPLE + "                        ║");
        System.out.println("╠════════════════════════════════════════════════════════╣");
        System.out.println("║  " + WHITE + "У вас есть все необходимые материалы для создания    " + PURPLE + "║");
        System.out.println("║  " + WHITE + "двух легендарных клинков - " + CYAN + "Whisper" + PURPLE + "!                  ║");
        System.out.println("╚════════════════════════════════════════════════════════╝" + RESET);

        System.out.print("\n" + WHITE + "Хотите создать два " + CYAN + "Whisper" + WHITE + "? (да/нет): " + RESET);
        String choice = scanner.nextLine().trim().toLowerCase();

        if (choice.equals("да")) {
            craftWhispers(player);
        } else {
            System.out.println("\n" + YELLOW + "Вы решили отложить создание Whisper." + RESET);
        }
    }

    private void craftWhispers(Player player) {
        System.out.println("\n" + YELLOW + "⚒️ Начинается процесс создания Whisper..." + RESET);
        System.out.println(RED + "🔥 Печь разогревается до невероятных температур..." + RESET);
        System.out.println(CYAN + "✨ Магическая энергия наполняет комнату..." + RESET);

        // Удаление всех необходимых предметов и создание двух Whisper
        player.getInventory().removeAllBossWeapons();
        player.getInventory().addItem(new Whisper());
        player.getInventory().addItem(new Whisper());

        System.out.println("\n" + PURPLE + "╔════════════════════════════════════════════════════════╗");
        System.out.println("║            " + CYAN + "ПОЗДРАВЛЯЕМ! ДВА WHISPER СОЗДАНЫ!" + PURPLE + "            ║");
        System.out.println("╚════════════════════════════════════════════════════════╝" + RESET);
    }

    private void handleRegularWeaponResetting(Player player, Scanner scanner) {
        while (true) {
            System.out.println("\n" + CYAN + "Ваш инвентарь:" + RESET);
            player.getInventory().displayInventory();
            System.out.print("\n" + WHITE + "Хотите переплавить оружие? (да/нет): " + RESET);

            String choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("нет")) break;
            if (!choice.equals("да")) {
                System.out.println(RED + "Неверный ввод. Пожалуйста, введите 'да' или 'нет'." + RESET);
                continue;
            }

            processWeaponResetting(player, scanner);
        }
    }

    private void processWeaponResetting(Player player, Scanner scanner) {
        System.out.print(WHITE + "Введите индекс оружия для переплавки: " + RESET);
        try {
            int index = scanner.nextInt();
            scanner.nextLine(); // очистка буфера

            if (index <= 0 || index > player.getInventory().getItems().size()) {
                System.out.println(RED + "❌ Неверный индекс!" + RESET);
                return;
            }

            Item itemToReset = player.getInventory().getItems().get(index - 1);
            if (!(itemToReset instanceof Weapon)) {
                System.out.println(RED + "❌ Выбранный предмет не является оружием!" + RESET);
                return;
            }

            if (player.getGold() < 400) {
                System.out.println(RED + "❌ Недостаточно золота! Требуется " + YELLOW + "400 золотых." + RESET);
                return;
            }

            performWeaponReset(player, (Weapon) itemToReset);

        } catch (InputMismatchException e) {
            System.out.println(RED + "❌ Пожалуйста, введите корректное число!" + RESET);
            scanner.nextLine(); // очистка буфера
        }
    }

    private void performWeaponReset(Player player, Weapon oldWeapon) {
        Weapon newWeapon = resetWeapon(oldWeapon);
        if (newWeapon != null) {
            player.getInventory().removeItem(oldWeapon);
            player.getInventory().addItem(newWeapon);
            player.setGold(player.getGold() - 400);

            System.out.println("\n" + YELLOW + "╔════════════════════════════════════════════════════════╗");
            System.out.println("║               " + GREEN + "ПЕРЕПЛАВКА УСПЕШНА!" + YELLOW + "                      ║");
            System.out.printf("║  " + WHITE + "%s → %s" + YELLOW + "  ║\n", oldWeapon.getName(), newWeapon.getName());
            System.out.println("╚════════════════════════════════════════════════════════╝" + RESET);
        }
    }

    private Weapon resetWeapon(Weapon oldWeapon) {
        if (oldWeapon instanceof Bow) {
            return BowFactory.createRandomBow();
        } else if (oldWeapon instanceof Axe) {
            return AxeFactory.createRandomAxe();
        } else if (oldWeapon instanceof Sword) {
            return SwordFactory.createRandomSword();
        } else if (oldWeapon instanceof Gloves) {
            return GlovesFactory.createRandomGloves();
        } else if (oldWeapon instanceof Wand) {
            return WandFactory.createRandomWand();
        } else if (oldWeapon instanceof Staff) {
            return StaffFactory.createRandomStaff();
        }
        return null; // Этого не должно произойти, если все типы оружия учтены
    }

    private static final String RESET = "\u001B[0m";
    private static final String BLACK = "\u001B[30m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE = "\u001B[37m";
}