package GameV2;

import java.util.Random;
import java.util.Scanner;

public class LifeFountain extends Room {
    private boolean visited; // Track if the player has visited the fountain

    public LifeFountain() {
        super();
        this.visited = false; // Player has not visited yet
    }

    private static final String FOUNTAIN_ART = """
            \033[0;36m
                          .      .       .       .
      .   .       .          .      . .      .         .          .    .
             .       .         .    .   .         .         .            .
        .   .    .       .         . . .        .        .     .    .
     .          .   .       .       . .      .        .  .              .
          .  .    .  .       .     . .    .       . .      .   .        .
     .   .       .    . .      .    . .   .      .     .          .     .
        .            .    .     .   . .  .     .   .               .
         .               .  .    .  . . .    .  .                 .
                            . .  .  . . .  . .
                                . . . . . .
                                  . . . .
                                   I . I
                     _______________III_______________
                    /    .       .       .       .    \\
                   ( ~~~ .  ~~~  .  ~~~  .  ~~~  . ~~~ )
                     \\SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS/
                        \\ ======================= /
                            \\SSSSSSSSSSSSSSSSS/
                         ________\\       /________
                        (=+=+=+=+=+=+=+=+=+=+=+=+=)
                         ~~~~~~~~~~~~~~~~~~~~~~~~~
            \033[0m
            """;

    // Override playerTurn method to handle player interaction with the fountain
    @Override
    public void playerTurn(Game game, Room room) {
        Player player = game.getPlayer();

        if (!visited) {
            System.out.println(CYAN_BOLD + "\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                    ФОНТАН ЖИЗНИ                           ║");
            System.out.println("╠══════════════════════════════════════════════════════════════╣" + RESET);
            System.out.println(BLUE_BRIGHT + FOUNTAIN_ART + RESET);
            System.out.println(CYAN_BOLD + "╠══════════════════════════════════════════════════════════════╣" + RESET);
            System.out.println(YELLOW_BOLD + "║  Вы наткнулись на легендарный Фонтан Жизни!                 ║");
            System.out.println("║  Его кристально чистые воды излучают целебную энергию.      ║" + RESET);
            System.out.println(CYAN_BOLD + "╠══════════════════════════════════════════════════════════════╣" + RESET);
            System.out.println(GREEN_BOLD + "║  Желаете ли вы окунуться в его живительные воды?            ║");
            System.out.println("║  Это может восстановить ваши силы и излечить раны.          ║" + RESET);
            System.out.println(CYAN_BOLD + "╠══════════════════════════════════════════════════════════════╣" + RESET);

            Scanner scanner = new Scanner(System.in);
            System.out.print(WHITE_BOLD + "║  Введите " + GREEN_BOLD + "'да'" + WHITE_BOLD + " для купания или " + RED_BOLD + "'нет'" + WHITE_BOLD + " чтобы отказаться: " + RESET);
            String choice = scanner.nextLine().trim().toLowerCase();

            System.out.println(CYAN_BOLD + "╠══════════════════════════════════════════════════════════════╣" + RESET);

            if (choice.equals("да")) {
                int healedAmount = healPlayer(player);
                System.out.println(GREEN_BOLD + "║  Вы погружаетесь в прохладные воды фонтана...              ║");
                System.out.println("║  Волшебная энергия наполняет ваше тело!                    ║");
                System.out.println("║                                                            ║");
                System.out.println("║  " + YELLOW_BOLD + "Вы восстановили " + healedAmount + " единиц здоровья!" + GREEN_BOLD + "                    ║" + RESET);
                visited = true;
            } else {
                System.out.println(YELLOW_BOLD + "║  Вы решаете не рисковать и обходите фонтан стороной.      ║");
                System.out.println("║  Возможно, это мудрое решение... или упущенная возможность?║" + RESET);
                visited = true;
            }

            System.out.println(CYAN_BOLD + "╚══════════════════════════════════════════════════════════════╝" + RESET);
        } else {
            System.out.println(CYAN_BOLD + "\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                    ФОНТАН ЖИЗНИ                           ║");
            System.out.println("╠══════════════════════════════════════════════════════════════╣" + RESET);
            System.out.println(YELLOW_BOLD + "║  Вы снова оказались у Фонтана Жизни.                         ║");
            System.out.println("║  Его воды все так же чисты, но магия уже не действует на вас.║" + RESET);
            System.out.println(CYAN_BOLD + "╚══════════════════════════════════════════════════════════════╝" + RESET);
        }

        // После взаимодействия с фонтаном, обрабатываем как пустую комнату
        handleEmptyRoom(game, this);
    }

    private static final String RESET = "\u001B[0m";
    private static final String CYAN_BOLD = "\u001B[1;36m";
    private static final String YELLOW_BOLD = "\u001B[1;33m";
    private static final String GREEN_BOLD = "\u001B[1;32m";
    private static final String RED_BOLD = "\u001B[1;31m";
    private static final String WHITE_BOLD = "\u001B[1;37m";
    private static final String BLUE_BRIGHT = "\u001B[0;94m";
    // Heal the player with a random amount between 0 and 100
    private int healPlayer(Player player) {
        Random random = new Random();
        int healAmount = random.nextInt(101); // Random number between 0 and 100
        player.setHealth(player.getHealth() + healAmount); // Assuming Player has a method to restore health
        return healAmount;
    }
}