package GameV2;

import java.util.Random;
import java.util.Scanner;

public class BigLifeFountain extends Room {
    private boolean visited; // Track if the player has visited the fountain
    private Random random;

    public BigLifeFountain() {
        super();
        this.visited = false; // Player has not visited yet
        this.random = new Random(); // Initialize Random instance
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
            System.out.println(FOUNTAIN_ART);
            // First time visiting the fountain
            System.out.println("Вы наткнулись на большой фонтан жизни! Хотите искупаться и восстановить свои жизни?");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите 'да' для искупания или 'нет' для игнорирования: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("да")) {
                int healedAmount = healPlayer(player);
                System.out.println("Вы восстановили " + healedAmount + " жизней!");

                // Chance to find a healing potion (e.g., 30% chance)
                if (random.nextInt(100) < 30) {
                    Item potion = getRandomPotion();
                    System.out.println("Вы нашли " + potion.getName() + "!");
                    // You can add logic to give the potion to the player here
                }

                visited = true; // Mark the fountain as visited
            } else {
                System.out.println("Вы решили игнорировать большой фонтан жизни.");
                visited = true; // Mark the fountain as visited
            }
        }

        // After the first interaction, handle empty room
        handleEmptyRoom(game, this);
    }

    // Heal the player with a random amount between 0 and 100
    private int healPlayer(Player player) {
        int healAmount = random.nextInt(202); // Random number between 0 and 201
        player.setHealth(player.getHealth() + healAmount); // Assuming Player has a method to restore health
        return healAmount;
    }

    // Randomly select a potion
    private Item getRandomPotion() {
        if (random.nextBoolean()) { // 50% chance for each potion type
            return new MinorHealingPotion();
        } else {
            return new GreaterHealingPotion();
        }
    }
}
