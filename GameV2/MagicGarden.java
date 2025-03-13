package GameV2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MagicGarden extends Room {
    private BerryFactory berryFactory; // Factory to create berries

    public MagicGarden() {
        super();
        this.berryFactory = new BerryFactory(); // Initialize the berry factory
    }

    private static final String MAGICGARDEN = """
            \033[0;33m
            (\\ /)   \\/                                               \\/   (\\ /)
              (X)   o00-     _                                  _     -00o   (X)
             (/|\\)          (')<                              >(')          (/|\\)
               |           (@ )                                ( @)           |
              \\|/|||||||||| ^^ |||||||||||||||||||||||||||||||| ^^ ||||wsm|||\\|/
                           ~~~~                                ~~~~
            
            """;


    // Override playerTurn method to handle player interaction with the magic garden
    @Override
    public void playerTurn(Game game, Room room) {
        Player player = game.getPlayer();
        System.out.println(MAGICGARDEN);

        // Visual description of the magic garden
        describeGarden();

        List<MagicalFruit> foundBerries = new ArrayList<>();
        Random random = new Random();

        // Determine how many berries to find based on the specified probabilities
        int chance = random.nextInt(100); // Random number between 0 and 99

        if (chance < 10) { // 10% chance to find 3 berries
            for (int i = 0; i < 3; i++) {
                foundBerries.add(berryFactory.createRandomBerry());
            }
        } else if (chance < 50) { // 40% chance to find 2 berries (10 to 49)
            for (int i = 0; i < 2; i++) {
                foundBerries.add(berryFactory.createRandomBerry());
            }
        } else { // 80% chance to find 1 berry (50 to 99)
            foundBerries.add(berryFactory.createRandomBerry());
        }

        // Display the found berries
        if (foundBerries.isEmpty()) {
            System.out.println("К сожалению, вы ничего не нашли в волшебном саду.");
        } else {
            System.out.println("Вы нашли следующие волшебные ягоды:");
            for (MagicalFruit berry : foundBerries) {
                System.out.println("- " + berry.getClass().getSimpleName());
                player.getInventory().addItem(berry);
            }

        }

        // After the interaction, handle empty room
        handleEmptyRoom(game, this);
    }

    // Method to describe the magic garden
    private void describeGarden() {
        System.out.println("Вы входите в волшебный сад, где яркие цветы и зелёные кусты переплетаются в гармонии.");
        System.out.println("В воздухе витает сладкий аромат ягод, и вы видите, как они блестят на солнечном свете.");
    }
}