package GameV2;

import java.util.Random;

public class SecondZombieRoom extends Room {
    private SecondZombieFactory secondZombieFactory; // Use SecondZombieFactory for generating zombies

    private static final String ZOMBIE_ROOM_ART = """
            \033[0;31m
                   _____________________
                  /                     \\
                 /   ____     ____       \\
                |   /    \\   /    \\       |
                |  | (o) |   | (o) |      |
                |   \\____/   \\____/       |
                |       /     \\           |
                |      |  ___  |          |
                |       \\_____/           |
                |   __________________    |
                |  |                  |   |
                |  |   ZOMBIE  ZONE   |   |
                |  |                  |   |
                |  |  ENTER AT YOUR   |   |
                |  |   OWN  RISK!     |   |
                |  |__________________|   |
                |                         |
                 \\     _____________     /
                  \\___/             \\___/
            \033[0m
    """;

    public SecondZombieRoom(Player player) {
        super();
        secondZombieFactory = new SecondZombieFactory(); // Initialize the SecondZombieFactory
        generateZombies(player); // Generate zombies upon room creation
    }

    // Generate a number of zombies based on the player's level (2 to 4 zombies)
    private void generateZombies(Player player) {
        Random random = new Random();
        int numZombies = random.nextInt(3) + 2; // Generates either 2, 3, or 4 zombies
        for (int i = 0; i < numZombies; i++) {
            addEnemy(secondZombieFactory.createZombie(player)); // Create and add the zombie to the room
        }
    }

    // Override playerTurn method to handle interactions in the SZombieRoom
    @Override
    public void playerTurn(Game game, Room room) {
        if (hasEnemies()) {
            System.out.println(ZOMBIE_ROOM_ART);
            System.out.println("Вы столкнулись с ордой зомби!");
            handleCombat(game, this); // Call handleCombat
        } else {
            handleEmptyRoom(game, room);
        }
    }
}