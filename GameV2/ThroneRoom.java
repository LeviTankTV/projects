package GameV2;

import java.util.Scanner;

public class ThroneRoom extends Room {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String BOLD = "\033[0;1m";
    private static final String ITALIC = "\033[3m";

    private static final String THRONE_ROOM_ART = ANSI_YELLOW + """
            ╔═══════════════════════════════════════════════════════════════════════════╗
            ║                   _____                                                   ║
            ║                  |     |                   /\\                             ║
            ║                  | [ ] |                  /  \\                            ║
            ║         _________|     |_________________/    \\____                       ║
            ║        |                                           |                      ║
            ║        |    ___     ___     ___     ___     ___    |                      ║
            ║        |   |   |   |   |   |   |   |   |   |   |   |                      ║
            ║        |   |___|   |___|   |___|   |___|   |___|   |                      ║
            ║        |     |       |       |       |       |     |                      ║
            ║        |     |       |       |       |       |     |                      ║
            ║        |_____|_______|_______|_______|_______|_____|                      ║
            ╚═══════════════════════════════════════════════════════════════════════════╝
    """ + ANSI_RESET;

    private static final String BAD_ENDING_ART = ANSI_RED + """
            ╔═══════════════════════════════════════════════════════════════════════════╗
            ║                       _____                                               ║
            ║                   ___/     \\___                                           ║
            ║                  /  (\\     /)  \\                                          ║
            ║                 /    (\\   /)    \\                                         ║
            ║                /      (\\_/)      \\                                        ║
            ║               /   /\\   )|(   /\\   \\                                       ║
            ║              /   /  \\  |||  /  \\   \\                                      ║
            ║             /___/    \\_|||_/    \\___\\                                     ║
            ║                       |||                                                 ║
            ║                       |||                                                 ║
            ║                       |||                                                 ║
            ║                    ___|||___                                              ║
            ║                   |         |                                             ║
            ║                   |  R.I.P  |                                             ║
            ║                   |         |                                             ║
            ╚═══════════════════════════════════════════════════════════════════════════╝
    """ + ANSI_RESET;

    private boolean magicDoorUnlocked = false;
    private int interactionTurn = 0;

    public ThroneRoom() {
        super();
    }

    @Override
    public void playerTurn(Game game, Room room) {
        Player player = game.getPlayer();
        Scanner scanner = new Scanner(System.in);

        describeThroneRoom();

        while (true) {
            System.out.println(ANSI_CYAN + BOLD + "\nЧто вы хотите сделать в Тронном Зале?" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "1. Осмотреть трон");
            System.out.println("2. Изучить гобелены");
            System.out.println("3. Подойти к Магической Двери");
            System.out.println("4. Покинуть Тронный Зал" + ANSI_RESET);

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    examineThroneInteraction(player);
                    return;
                case 2:
                    examineTapestriesInteraction(player);
                    return;
                case 3:
                    approachMagicDoorInteraction(player, game, scanner);
                    return;
                case 4:
                    System.out.println(ANSI_BLUE + "Вы покидаете Тронный Зал." + ANSI_RESET);
                    handleEmptyRoom(game, this);
                    return;
                default:
                    System.out.println(ANSI_RED + "Неверный выбор. Попробуйте снова." + ANSI_RESET);
            }
        }
    }

    private void describeThroneRoom() {
        System.out.println(THRONE_ROOM_ART);
        System.out.println(ANSI_PURPLE + BOLD + "Вы входите в величественный Тронный Зал." + ANSI_RESET);
        System.out.println(ANSI_BLUE + "В центре комнаты стоит массивный трон, украшенный драгоценными камнями.");
        System.out.println("Стены покрыты богатыми гобеленами, изображающими историю королевства.");
        System.out.println("В дальнем конце зала вы замечаете загадочную Магическую Дверь." + ANSI_RESET);
    }

    private void examineThroneInteraction(Player player) {
        System.out.println(ANSI_YELLOW + "Вы внимательно осматриваете трон.");
        System.out.println("Вы замечаете странный символ на подлокотнике." + ANSI_RESET);
        player.gainExperience(200);
        System.out.println(ANSI_GREEN + "Вы получили 200 опыта за наблюдательность." + ANSI_RESET);
    }

    private void examineTapestriesInteraction(Player player) {
        System.out.println(ANSI_YELLOW + "Вы изучаете гобелены на стенах.");
        System.out.println("Они рассказывают древнюю историю о могущественных магах." + ANSI_RESET);
        player.gainExperience(200);
        System.out.println(ANSI_GREEN + "Вы получили 200 опыта за изучение истории." + ANSI_RESET);
    }


    private void approachMagicDoorInteraction(Player player, Game game, Scanner scanner) {
        System.out.println("Вы подходите к Магической Двери.");

        if (canUnlockMagicDoor(player)) {
            System.out.println("Дверь начинает светиться, реагируя на ваши магические знания и Тёмный Ключ.");
            System.out.println("Вы чувствуете, как древняя магия пробуждается...");
            magicDoorUnlocked = true;
            player.gainExperience(5000);
            System.out.println("Вы получили 5000 опыта за разблокировку Магической Двери!");
            supremeMageInteraction(player, scanner, game);
        } else {
            System.out.println("Дверь остается неподвижной. Кажется, вам не хватает чего-то для её открытия.");
            if (!hasDarkKey(player)) {
                System.out.println("Вам не хватает Тёмного Ключа.");
            }
            if (!hasAllSpells(player)) {
                System.out.println("Вам нужно изучить больше магических навыков.");
            }
        }
    }

    private void supremeMageInteraction(Player player, Scanner scanner, Game game) {
        if (interactionTurn == 0) {
            System.out.println(ANSI_PURPLE + "\nКогда дверь открывается, вы видите величественную фигуру Верховного Мага.");
            System.out.println("\"Наконец-то ты добрался сюда,\" - произносит он с холодной улыбкой." + ANSI_RESET);
        }

        while (interactionTurn < 3) {
            System.out.println(ANSI_CYAN + "\nКак вы ответите Верховному Магу?" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "1. \"Зачем вы устроили все эти испытания?\"");
            System.out.println("2. \"Я пришел положить конец вашему правлению!\"");
            System.out.println("3. \"Что ждет меня дальше?\"");
            System.out.println("4. \"Научите меня своему могуществу.\"" + ANSI_RESET);

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            System.out.println(ANSI_PURPLE + BOLD);
            switch (choice) {
                case 1:
                    System.out.println("\nВерховный Маг усмехается:");
                    System.out.println("\"Мне нужен был достойный преемник. Или достойная жертва - это зависит от твоего выбора.\"");
                    break;
                case 2:
                    System.out.println("\nВерховный Маг грозно смотрит на вас:");
                    System.out.println("\"Храбрые слова для того, кто даже не представляет истинной силы древней магии.\"");
                    break;
                case 3:
                    System.out.println("\nВерховный Маг поднимает руку, создавая магический символ в воздухе:");
                    System.out.println("\"Впереди тебя ждет выбор - присоединиться ко мне или погибнуть.\"");
                    break;
                case 4:
                    System.out.println("\nВерховный Маг довольно кивает:");
                    System.out.println("\"Мудрый выбор. Но готов ли ты заплатить цену за такое могущество?\"");
                    break;
            }
            System.out.println(ANSI_RESET);

            interactionTurn++;
        }

        concludeInteraction(player, game);
    }

    private void concludeInteraction(Player player, Game game) {
        System.out.println(ANSI_RED + BOLD + "\nВерховный Маг поднимает руки, окутанные темной энергией:");
        System.out.println("\"Время пришло! Сделай свой выбор - власть или смерть!\"" + ANSI_RESET);

        Scanner scanner = new Scanner(System.in);
        System.out.println(ANSI_YELLOW + "\n1. Принять предложение Верховного Мага");
        System.out.println("2. Отвергнуть предложение и сразиться" + ANSI_RESET);

        int choice = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        if (choice == 1) {
            System.out.println(ANSI_RED + BOLD + "\nВы принимаете темную силу. Ваша душа изменяется навсегда...");
            System.out.println("Верховный Маг улыбается: \"Добро пожаловать в истинное могущество.\"" + ANSI_RESET);
            player.gainExperience(1);
            System.out.println(ANSI_GREEN + "Вы получили 1 опыта за принятие темной силы!" + ANSI_RESET);
            System.out.println(ANSI_RED + BOLD + "\nКонцовка 1/4: Плохая Концовка");
            System.out.println("Вы предали свои идеалы и присоединились к тьме..." + ANSI_RESET);
            System.out.println(BAD_ENDING_ART);
            this.handlePlayerDeath(game);
        } else {
            System.out.println(ANSI_BLUE + BOLD + "\nВы отвергаете предложение и готовитесь к битве.");
            System.out.println("\"Глупец! Ты упустил свой единственный шанс на величие!\" - гремит голос Верховного Мага.");
            System.out.println("Внезапно, пространство вокруг искажается, и вы оба переноситесь в другое место..." + ANSI_RESET);
            player.gainExperience(8000);
            System.out.println(ANSI_GREEN + "Вы получили 8000 опыта за проявленную силу воли!" + ANSI_RESET);

            game.transitionToThirdPart();
        }
    }

    private boolean canUnlockMagicDoor(Player player) {
        return hasAllSpells(player) && hasDarkKey(player);
    }

    private boolean hasAllSpells(Player player) {
        return player.isLearnedBloodMagic() &&
                player.isLearnedEarthQuake() &&
                player.isLearnedArcaneMissile() &&
                player.isLearnedFireball() &&
                player.isLearnedHeal() &&
                player.isLearnedIceSpike() &&
                player.isLearnedShadowBolt();
    }

    private boolean hasDarkKey(Player player) {
        for (Item item : player.getInventory().getItems()) {
            if (item instanceof DarkKey) {
                return true;
            }
        }
        return false;
    }
}