package GameV2;

public class TrueCastleEntranceRoom extends Room {

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

    private static final String RUINED_CASTLE_ART = ANSI_YELLOW + """
                                         T~~
                                         |
                                        /^\\
                                       /   \\
                      _____           /  _  \\            _____
                     |  _  \\        /  (_)  \\          /  _  |
                     | | | |        /  (o o)  \\         | | | |
                     | | | |       /  /     \\  \\       | | | |
                     | | | |      /  /       \\  \\      | | | |
                     | |_| |     /  /         \\  \\     | |_| |
                     |_____|    /__/           \\__\\    |_____|
                    (________)  |_|           |_|     (________)
                               (_____)       (_____)
    """ + ANSI_RESET;

    public TrueCastleEntranceRoom() {
        super();
        addEnemy(new GhostSupremeMage()); // Add the Ghost Supreme Mage to this room
    }

    @Override
    public void playerTurn(Game game, Room room) {
        if (hasEnemies()) {
            System.out.println(RUINED_CASTLE_ART);
            System.out.println(ANSI_CYAN + BOLD + "╔═══════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                    Вход в Истинный Замок Верховного Мага                    ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════╝" + ANSI_RESET);

            System.out.println(ANSI_BLUE + ITALIC + "\nВы входите в зал, который кажется странно знакомым..." + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "Это тот же вход в замок, который вы видели ранее, но теперь он полностью разрушен." + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "Стены покрыты трещинами, а некогда величественные колонны превратились в руины." + ANSI_RESET);
            System.out.println(ANSI_GREEN + "Воздух здесь застыл, и кажется, что жизнь покинула это место много веков назад." + ANSI_RESET);
            System.out.println(ANSI_CYAN + BOLD + "В центре зала вы замечаете полупрозрачную фигуру - это Призрак Верховного Мага!" + ANSI_RESET);
            System.out.println(ANSI_RED + "Его пустые глаза устремлены на вас, и вы чувствуете, как холод пронизывает ваше тело...\n" + ANSI_RESET);

            handleCombat(game, this);
        } else {
            System.out.println(ANSI_WHITE + BOLD + "Разрушенный зал пуст. Призрак Верховного Мага исчез..." + ANSI_RESET);
            handleEmptyRoom(game, room);
        }
    }
}