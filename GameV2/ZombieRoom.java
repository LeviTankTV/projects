package GameV2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ZombieRoom extends Room {
    private ZombieFactory zombieFactory; // Inject zombie factory for generating zombies

    public ZombieRoom(Player player) {
        super();
        zombieFactory = new ZombieFactory(); // Initialize the zombie factory
        generateZombies(player); // Generate zombies upon room creation
    }

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

    // Generate a number of zombies based on the player's level
    private void generateZombies(Player player) {
        Random random = new Random();
        int numZombies = random.nextInt(3) + 1;
        for (int i = 0; i < numZombies; i++) {
            addEnemy(zombieFactory.createZombie(player));
        }
    }

    public static final String RED_BOLD_BRIGHT = "\033[1;91m";
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m";
    public static final String RESET = "\033[0m";

    // Override playerTurn method to handle zombie room interactions
    public void playerTurn(Game game, Room room) {
        System.out.println(ZOMBIE_ROOM_ART);
        if (hasEnemies()) {
            System.out.println(RED_BOLD_BRIGHT + "╔══════════════════════════════════════╗" + RESET);
            System.out.println(RED_BOLD_BRIGHT + "║  Вы столкнулись с ордой зомби! 🧟‍♂️    ║" + RESET);
            System.out.println(RED_BOLD_BRIGHT + "╚══════════════════════════════════════╝" + RESET);
            handleCombat(game, this); // Call handleCombat
        } else {
            System.out.println(GREEN_BOLD_BRIGHT + "Комната пуста. Вы можете осмотреться." + RESET);
            handleEmptyRoom(game, room);
        }
    }
}