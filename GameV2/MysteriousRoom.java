package GameV2;

import java.util.Scanner;

public class MysteriousRoom extends Room {
    public static final String RESET = "\033[0m";  // Text Reset
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String RED_BRIGHT = "\033[0;91m";   // RED
    public static final String RED_BOLD_BRIGHT = "\033[1;91m"; // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String BLUE_BRIGHT = "\033[0;94m";  // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE
    private static final String UNLOCKED_DOOR = """
            \033[0;33m
               /|
              / |
             /__|______
            |  __  __  |
            | |  ||  | |
            | |  ||  | |
            | |__||__| |__\\
            |  __  __()|   \\
            | |  ||  | |    \\
            | |  ||  | |     \\
            | |  ||  | |     (\\)
            | |__||__| |     )((
            |__________| ejm ))))
            \033[0m
            """;

    private static final String LOCKED_DOOR = """
            \033[0;36m
             ______________
            |\\ ___________ /|
            | |  _ _ _ _  | |
            | | | | | | | | |
            | | |-+-+-+-| | |
            | | |-+-+-+-| | |
            | | |_|_|_|_| | |
            | |     ___   | |
            | |    /__/   | |
            | |   [%==] ()| |
            | |         ||| |
            | |         ()| |
            | |           | |
            | |           | |
            | |           | |
            |_|___________|_|
            \033[0m
            """;
    public MysteriousRoom(Player player) {
        super();
    }

    @Override
    public void handleEmptyRoom(Game game, Room room) {
        Player player = game.getPlayer();

        if (!(player.isLearnedHeal() && player.isLearnedFireball())) {
            System.out.println(PURPLE_BOLD + "Вы чувствуете, что в этой комнате скрыта какая-то тайна," +
                    " но пока не можете понять какая..." + RESET);
            System.out.println(LOCKED_DOOR);
            super.handleEmptyRoom(game, room);
            return;
        }

        System.out.println(CYAN_BOLD + "\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║              ТАИНСТВЕННАЯ КОМНАТА ПЕРЕХОДА               ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n" + RESET);

        System.out.println(LOCKED_DOOR);

        System.out.println(PURPLE_BRIGHT + "Воздух в комнате наэлектризован древней магией." + RESET);
        System.out.println(PURPLE_BRIGHT + "Вы ощущаете, как заклинания Огня и Исцеления резонируют с дверью." + RESET);
        System.out.println(YELLOW_BOLD + "\nДревние руны на двери начинают светиться в ответ на вашу магию..." + RESET);

        System.out.println(WHITE_BOLD + "\nЖелаете ли вы использовать силу обоих заклинаний, чтобы разрушить дверь? (да/нет)" + RESET);

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().trim().toLowerCase();

        if (choice.equals("да")) {
            try {
                System.out.println(RED_BOLD + "\n* * * Энергия заклинаний концентрируется вокруг вас * * *" + RESET);
                System.out.println(RED_BRIGHT + "Огонь и Исцеление сливаются в единый поток силы!");
                Thread.sleep(1000);
                System.out.println(RED_BOLD_BRIGHT + "ДВЕРЬ СОДРОГАЕТСЯ ПОД НАПОРОМ ВАШЕЙ МАГИИ!" + RESET);
                Thread.sleep(1000);
                System.out.println(YELLOW_BOLD_BRIGHT + "\n╔═══════════════════════════════════════════════╗");
                System.out.println("║         ДВЕРЬ ОТКРЫТА! ПУТЬ СВОБОДЕН!         ║");
                System.out.println("╚═══════════════════════════════════════════════╝\n" + RESET);

                System.out.println(UNLOCKED_DOOR);

                game.transitionToSecondPart();

                System.out.println(GREEN_BOLD + "\nВы чувствуете, как реальность вокруг вас меняется...");
                System.out.println("Впереди вас ждёт новая глава вашего приключения!" + RESET);
            } catch (InterruptedException e) {
                System.out.println("Произошла ошибка при создании эффекта задержки.");
                Thread.currentThread().interrupt();
            }
        } else {
            System.out.println(BLUE_BRIGHT + "\nВы решаете, что ещё не время раскрывать эту тайну.");
            System.out.println("Возможно, стоит вернуться сюда позже..." + RESET);
            super.handleEmptyRoom(game, room);
        }
    }
}
