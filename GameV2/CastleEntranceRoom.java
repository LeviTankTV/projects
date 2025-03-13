package GameV2;

public class CastleEntranceRoom extends Room {
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

    private static final String CASTLE_ART = """
                |>>>                                                      |>>>
                |                     |>>>          |>>>                  |
                *                     |             |                     *
               / \\                    *             *                    / \\
              /___\\                 _/ \\           / \\_                 /___\\
              [   ]                |/   \\_________/   \\|                [   ]
              [ I ]                /     \\       /     \\                [ I ]
              [   ]_ _ _          /       \\     /       \\          _ _ _[   ]
              [   ] U U |        {#########}   {#########}        | U U [   ]
              [   ]====/          \\=======/     \\=======/          \\====[   ]
              [   ]    |           |   I |_ _ _ _| I   |           |    [   ]
              [___]    |_ _ _ _ _ _|     | U U U |     |_ _ _ _ _ _|    [___]
              \\===/  I | U U U U U |     |=======|     | U U U U U | I  \\===/
               \\=/     |===========| I   | + W + |   I |===========|     \\=/
                |  I   |           |     |_______|     |           |   I  |
                |      |           |     |||||||||     |           |      |
                |      |           |   I ||vvvvv|| I   |           |      |
            _-_-|______|-----------|_____||     ||_____|-----------|______|-_-_
               /________\\         /______||     ||______\\         /________\\
              |__________|-------|________\\_____/________|-------|__________|
            """;

    public CastleEntranceRoom(Player player) {
        super();
    }

    @Override
    public void playerTurn(Game game, Room room) {
        System.out.println(CYAN_BOLD + CASTLE_ART + RESET);

        System.out.println(YELLOW_BOLD + "╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    ДОБРО ПОЖАЛОВАТЬ В ЗАМОК!                      ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝" + RESET);

        System.out.println(PURPLE_BRIGHT + "\nВы стоите в величественном зале входа в древний замок.");
        System.out.println("Высокие своды теряются в полумраке, а эхо ваших шагов разносится по пустынному помещению.");
        System.out.println("Пылинки танцуют в лучах света, проникающих через витражные окна.");
        System.out.println("Вы чувствуете, что здесь начинается новая глава вашего приключения." + RESET);

        System.out.println(GREEN_BOLD + "\nЧто вы хотите сделать дальше?" + RESET);

        handleEmptyRoom(game, room);
    }
}