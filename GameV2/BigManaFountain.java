package GameV2;

import java.util.Random;
import java.util.Scanner;

public class BigManaFountain extends Room {
    private boolean visited; // Track if the player has visited the fountain
    private Random random;

    public BigManaFountain() {
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
            System.out.println("Вы наткнулись на большой фонтан маны! Хотите искупаться и восстановить свою ману полностью?");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите 'да' для искупания или 'нет' для игнорирования: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("да")) {
                int restoredAmount = restoreFullMana(player);
                System.out.println("Вы восстановили " + restoredAmount + " маны!");

                // Chance to find a mana potion (e.g., 30% chance)
                if (random.nextInt(100) < 30) {
                    Item potion = getRandomPotion();
                    System.out.println("Вы нашли " + potion.getName() + "!");
                    game.getPlayer().getInventory().addItem(potion);
                    }


                visited = true; // Mark the fountain as visited
            } else {
                System.out.println("Вы решили игнорировать большой фонтан маны.");
                visited = true; // Mark the fountain as visited
            }
        } else {
            System.out.println("Вы уже были у большого фонтана маны.");
        }

        // After the first interaction, handle empty room
        handleEmptyRoom(game, this);
    }

    // Restore the player's mana to full (assuming a maximum mana value)
    private int restoreFullMana(Player player) {
        int maxMana = player.getMaxMana(); // Assuming Player has a method to get max mana
        player.setMana(maxMana); // Restore mana to maximum
        return maxMana; // Return the amount restored
    }

    // Randomly select a mana potion
    private Item getRandomPotion() {
        if (random.nextBoolean()) { // 50% chance for each potion type
            return new MinorManaPotion();
        } else {
            return new GreaterManaPotion();
        }
    }
}