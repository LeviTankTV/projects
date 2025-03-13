package GameV2;

import java.util.Scanner;

public class TrueThroneRoom extends Room {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";

    private static final String BOLD = "\033[0;1m";
    private static final String ITALIC = "\033[3m";

    private static final String CROWN_ART = ANSI_YELLOW + """
              .
                                            .       |         .    .
                                      .  *         -*-          *
                                           \\        |         /   .
                          .    .            .      /^\\     .              .    .
                             *    |\\   /\\    /\\  / / \\ \\  /\\    /\\   /|    *
                           .   .  |  \\ \\/ /\\ \\ / /     \\ \\ / /\\ \\/ /  | .     .
                                   \\ | _ _\\/_ _ \\_\\_ _ /_/_ _\\/_ _ \\_/
                                     \\  *  *  *   \\ \\/ /  *  *  *  /
                                      ` ~ ~ ~ ~ ~  ~\\/~ ~ ~ ~ ~ ~ '
    """ + ANSI_RESET;

    private static final String TrueEnd = """
            \033[0;36m
                            _  _         _    _         _      _ _ _
                           |_)|_  /\\ /` |_   / \\|\\ |   |_  /\\ |_) | |_|
                           |  |_ /""\\\\_,|_   \\_/| \\|   |_ /""\\| \\ | | |
                 .-------------------------------------------------------------.
                 |    _     .-.                                                |
                 |   | `.  /  | ,--.                                .          |
                __   \\  |  |_/ /   /                             _.'|          |
             .'   `'. \\ /,-'';,'-'`         '.                ,'`   ',    ,    |
              \\      \\.;'   / |`\\,.--.        \\             ,'      '|   /|    |
               `-._.-;/    | ,'/\\ `.  \\        |    .,._ .'          |_,' |,   |
            ,'```'-.,'     \\/ /  \\  `-'        .-'`     `'.     _,---'     |   |
            `.._____/         ;  |             |`.  o      \\ .-'           |   |
                ,-';   /`.    |  /             | |         ,'              |   |
              ,'   /  /  |    '.'             .' ;        /                |   |
             /   ,'| ;   |.-.            _    /  /       .'                '   |
            '--'`| | |  /   /.'`.       | `. ;__ .       |                /    |
                 | | | /   / |  | .-.   \\  | /\\_)|       \\               '     |
                _|._\\|/--'`  |  ' |  \\   `.|'    |        `.           ,'\\     |
              .'    /`;      | /   \\__'_,;-.     :          '-..__.,.-'   `.   |
             /    ,' /\\\\     |/    _,-|\\  \\ `.   '                          `'-..__
             '--'`   | \\`'-._;_.,-'   | \\  '-'    '                                )
                 |   |  ;       |\\    `._)         \\                  ,-'`.       /
                 |   ;  |       | \\                 `.              ,'    |     ,'
                 |    \\_/       |  ;                  `-.         ,'      |   ,'
                 |              \\  |                     `''--''`          `'` |
                 |               `-'                                        mx |
                 '-------------------------------------------------------------'
            \033[0m
            """;
    public TrueThroneRoom() {
        super();
    }

    @Override
    public void playerTurn(Game game, Room room) {
        if (hasEnemies()) {
            System.out.println(ANSI_CYAN + BOLD + "\nВы стоите в Истинном Тронном Зале, месте, где решится судьба всего мира." + ANSI_RESET);
            System.out.println(ANSI_BLUE + "Огромный зал освещен мерцающим светом магических кристаллов." + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "В центре комнаты возвышается древний трон, окруженный пульсирующей энергией." + ANSI_RESET);
            System.out.println(ANSI_RED + BOLD + "Перед вами стоит Верховный Маг во всем своем могуществе, готовый к финальной битве." + ANSI_RESET);
            System.out.println(ANSI_YELLOW + ITALIC + "Воздух искрится от напряжения магии, и вы чувствуете, что это ваш последний шанс...\n" + ANSI_RESET);

            handleCombat(game, this);
        } else {
            endGame(game);
        }
    }

    private void endGame(Game game) {
        System.out.println(ANSI_GREEN + BOLD + "Истинный Тронный Зал затих. Верховный Маг повержен, и его магия рассеивается..." + ANSI_RESET);

        if (allFlowersPutOnGraves(game)) {
            goodEnding(game);
        } else {
            badEnding(game);
        }
        System.exit(0);
    }

    private boolean allFlowersPutOnGraves(Game game) {
        return game.isPutFlowerAtKingGrave() &&
                game.isPutFlowerAtQueenGrave() &&
                game.isPutFlowerAtPrinceGrave() &&
                game.isPutFlowerAtPrincessGrave();
    }

    private void deathEnding(Game game) {
        try {
            System.out.println(ANSI_RED + BOLD + "\nВаше тело падает на холодный мраморный пол тронного зала." + ANSI_RESET);
            Thread.sleep(3000);
            System.out.println(ANSI_PURPLE + "Верховный Маг медленно подходит к вашему поверженному телу." + ANSI_RESET);
            Thread.sleep(3000);
            System.out.println(ANSI_CYAN + ITALIC + "'Ещё один глупец, думавший, что может изменить судьбу...' - произносит он с презрением." + ANSI_RESET);
            Thread.sleep(3000);
            System.out.println(ANSI_YELLOW + "Последнее, что вы видите - его торжествующую улыбку и вспышку темной магии." + ANSI_RESET);
            Thread.sleep(3000);
            System.out.println(ANSI_BLUE + "Тьма поглощает вас, и ваша душа присоединяется к легиону призраков, служащих Верховному Магу." + ANSI_RESET);
            Thread.sleep(3000);
            System.out.println(ANSI_RED + BOLD + "\nИгра окончена. Вы пали в битве с Верховным Магом. Концовка 2/4" + ANSI_RESET);
            System.out.println(CROWN_ART);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void goodEnding(Game game) {
        try {
            System.out.println(ANSI_CYAN + "\nВнезапно, воздух в зале начинает мерцать, и перед вами появляются призрачные фигуры." + ANSI_RESET);
            Thread.sleep(3000);
            System.out.println(ANSI_BLUE + "Вы узнаете в них членов королевской семьи, чьи могилы вы почтили цветами." + ANSI_RESET);
            Thread.sleep(3000);
            System.out.println(ANSI_PURPLE + "Они благодарно кивают вам, а затем обращают свой взор на корону Верховного Мага." + ANSI_RESET);
            Thread.sleep(3000);
            System.out.println(ANSI_YELLOW + "С единым жестом они направляют свою энергию на корону, которая начинает трескаться и рассыпаться." + ANSI_RESET);
            Thread.sleep(3000);
            System.out.println(ANSI_GREEN + "Когда последний осколок короны исчезает, призраки улыбаются вам и растворяются в воздухе." + ANSI_RESET);
            Thread.sleep(3000);
            System.out.println(ANSI_BLUE + "Вы чувствуете, как тяжесть спадает с ваших плеч. Мир спасен, а древнее проклятие разрушено." + ANSI_RESET);
            Thread.sleep(3000);
            System.out.println(ANSI_CYAN + "Но Верховный Маг... он исчезает, растворяясь в дыму, оставляя после себя лишь тишину." + ANSI_RESET);
            Thread.sleep(3000);
            System.out.println(ANSI_RED + "Вы знаете, что это не конец. Он слишком могуществен, чтобы быть побежденным. И вы готовы встретиться с ним снова." + ANSI_RESET);
            Thread.sleep(3000);
            System.out.println(ANSI_CYAN + "Вы медленно покидаете разрушающийся замок, готовые начать новую главу в своей жизни, полную неизвестности..." + ANSI_RESET);
            Thread.sleep(3000);
            System.out.println(ANSI_GREEN + BOLD + "\nПоздравляем! Вы достигли истинной концовки 4/4, но история продолжается..." + ANSI_RESET);
            System.out.println(TrueEnd);
            handlePlayerDeath(game);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    private void badEnding(Game game) {
        try {
            System.out.println(ANSI_YELLOW + "\nКогда последний удар повергает Верховного Мага, его корона слетает с головы." + ANSI_RESET);
            Thread.sleep(3000);
            System.out.println(ANSI_PURPLE + "Но вместо того, чтобы упасть на пол, она зависает в воздухе, пульсируя темной энергией." + ANSI_RESET);
            Thread.sleep(3000);
            System.out.println(ANSI_RED + "Внезапно, корона устремляется к вам и опускается на вашу голову." + ANSI_RESET);
            Thread.sleep(3000);
            System.out.println(ANSI_BLUE + "Вы чувствуете, как невероятная сила наполняет ваше тело, но вместе с ней приходит и тьма." + ANSI_RESET);
            Thread.sleep(3000);
            System.out.println(ANSI_CYAN + "Ваш разум затуманивается, и вы понимаете, что теперь вы - новый Верховный Маг." + ANSI_RESET);
            Thread.sleep(3000);
            System.out.println(ANSI_GREEN + "Мир спасен от предыдущего тирана, но какова будет ваша судьба?" + ANSI_RESET);
            Thread.sleep(3000);
            System.out.println(ANSI_RED + BOLD + "\nВы достигли плохой концовки 3/4." + ANSI_RESET);
            System.out.println(CROWN_ART);
            handlePlayerDeath(game);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleCombat(Game game, Room room) {
        Player player = game.getPlayer();
        Scanner scanner = new Scanner(System.in);
        boolean turnEnded = false;

        while (player.isAlive() && hasEnemies() && !turnEnded) {
            // Player's turn
            player.updateEffects();
            boolean playerTurnEnded = player.performAction(player, room);

            if (!player.isAlive()) {
                deathEnding(game);
                handlePlayerDeath(game);
            }

            if (playerTurnEnded) {
                player.regenerateMana();
                player.performAlliesActions(room);

                for (int i = 0; i < getEnemies().size(); i++) {
                    Entity enemy = getEnemies().get(i);
                    if (enemy.isAlive()) {
                        enemy.updateEffects();
                        if (enemy instanceof SupremeMage) {
                            SupremeMage supremeMage = (SupremeMage) enemy;
                            if (!supremeMage.isRitualCompleted()) {
                                supremeMage.performAction(game, room);
                                break;
                            }
                        }
                        enemy.performAction(game, room);

                        if (!player.isAlive()) {
                            deathEnding(game);
                            handlePlayerDeath(game);
                        }
                    } else {
                        enemy.rip(game);
                        removeEnemy(enemy);
                        i--;
                    }
                }

                if (!hasEnemies()) {
                    System.out.println(ANSI_GREEN + BOLD + "Вы победили Верховного Мага!" + ANSI_RESET);
                    clearEnemies();
                    endGame(game);
                    return;
                }

                System.out.println(ANSI_YELLOW + "Нажмите Enter, чтобы продолжить ход." + ANSI_RESET);
                scanner.nextLine();
            }
        }
    }
}