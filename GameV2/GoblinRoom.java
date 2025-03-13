package GameV2;

import java.util.Random;

public class GoblinRoom extends Room {
    private GoblinFactory goblinFactory; // Inject goblin factory for generating goblins

    public GoblinRoom(Player player) {
        super();
        goblinFactory = new GoblinFactory(); // Initialize the goblin factory
        generateGoblins(player); // Generate goblins upon room creation
    }

    // Generate a number of goblins based on the player's level
    private void generateGoblins(Player player) {
        Random random = new Random();
        int numGoblins = random.nextInt(3) + 1; // Generate 1 to 3 goblins
        for (int i = 0; i < numGoblins; i++) {
            addEnemy(goblinFactory.createGoblin(player)); // Create and add goblin to the room
        }
    }

    private static final String GOBLIN_ART = """
            \033[0;36m
                       ,      ,
                      /(.-""-.)\\
                  |\\  \\/      \\/  /|
                  | \\ / =.  .= \\ / |
                  \\( \\   o\\/o   / )/
                   \\_, '-/  \\-' ,_/
                     /   \\__/   \\
                     \\ \\__/\\__/ /
                   ___\\ \\|--|/ /___
                 /`    \\      /    `\\
            jgs /       '----'       \\
            \033[0m
            """;

    // Override playerTurn method to handle goblin room interactions
    public void playerTurn(Game game, Room room) {
        if (hasEnemies()) {
            System.out.println(GOBLIN_ART);
            System.out.println("You encountered a horde of goblins!");
            handleCombat(game, this); // Call handleCombat
        } else {
            handleEmptyRoom(game, room);
        }
    }
}