package GameV2;

import java.util.Random;
import java.util.Scanner;

public class ManaFountain extends Room {
    private boolean visited; // Track if the player has visited the fountain

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

    public ManaFountain() {
        super();
        this.visited = false; // Player has not visited yet
    }

    // Override playerTurn method to handle player interaction with the mana fountain
    @Override
    public void playerTurn(Game game, Room room) {
        Player player = game.getPlayer();

        if (!visited) {
            System.out.println(FOUNTAIN_ART);
            System.out.println("\033[1;35mВы наткнулись на фонтан маны! Хотите искупаться и восстановить свою ману?\033[0m");

            Scanner scanner = new Scanner(System.in);
            System.out.print("\033[1;37mВведите 'да' для искупания или 'нет' для игнорирования: \033[0m");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("да")) {
                int restoredAmount = restoreMana(player);
                System.out.println("\033[1;32mВы восстановили " + restoredAmount + " маны!\033[0m");
                visited = true; // Mark the fountain as visited
            } else {
                System.out.println("\033[1;33mВы решили игнорировать фонтан маны.\033[0m");
                visited = true; // Mark the fountain as visited
            }
        } else {
            System.out.println("\033[1;34mВы уже были у фонтана маны.\033[0m");
        }

        // After the first interaction, handle empty room
        handleEmptyRoom(game, this);
    }

    // Restore the player's mana with a random amount between 0 and 100
    private int restoreMana(Player player) {
        Random random = new Random();
        int restoreAmount = random.nextInt(101); // Random number between 0 and 100
        player.setMana(player.getMana() + restoreAmount); // Assuming Player has a method to restore mana
        return restoreAmount;
    }
}