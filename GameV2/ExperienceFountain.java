package GameV2;

import java.util.Random;
import java.util.Scanner;

public class ExperienceFountain extends Room {
    private boolean visited; // Track if the player has visited the fountain

    public ExperienceFountain() {
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
            System.out.println(FOUNTAIN_ART);
            // First time visiting the fountain
            System.out.println("Вы наткнулись на фонтан опыта! Хотите искупаться и получить опыт?");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите 'да' для искупания или 'нет' для игнорирования: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("да")) {
                int gainedExperience = gainExperience(player);
                System.out.println("Вы получили " + gainedExperience + " опыта!");
                visited = true; // Mark the fountain as visited
            } else {
                System.out.println("Вы решили игнорировать фонтан опыта.");
                visited = true; // Mark the fountain as visited
            }
        }

        // After the first interaction, handle empty room
        handleEmptyRoom(game, this);
    }

    // Gain experience for the player with a random amount between 0 and 100
    private int gainExperience(Player player) {
        Random random = new Random();
        int experienceAmount = random.nextInt(101); // Random number between 0 and 100
        player.setExperience(player.getExperience() + experienceAmount); // Assuming Player has a method to set experience
        return experienceAmount;
    }
}