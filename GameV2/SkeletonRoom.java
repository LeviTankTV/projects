package GameV2;

import java.util.Random;

public class SkeletonRoom extends Room {
    private SkeletonFactory skeletonFactory; // Inject skeleton factory for generating skeletons


    private static final String SKELETON_ROOM_ART = """
            \033[0;33m
                                       ,--.
                                      {    }
                                      K,   }
                                     /  `Y`
                                _   /   /
                               {_'-K.__/
                                 `/-.__L._
                                 /  ' /`\\_}
                                /  ' /     
                        ____   /  ' /
                 ,-'~~~~    ~~/  ' /_
               ,'             ``~~~%%',
              (                     %  Y
             {                      %% I
            {      -                 %  `.
            |       ',                %  )
            |        |   ,..__      __. Y
            |    .,_./  Y ' / ^Y   J   )|
            \\           |' /   |   |   ||
             \\          L_/    . _ (_,.'(
              \\,   ,      ^^""' / |      )
                \\_  \\          /,L]     /
                  '-_`-,       ` `   ./`
                     `-(_            )
                         ^^\\..___,.--`
            \033[0m
    """;


    public SkeletonRoom(Player player) {
        super();
        skeletonFactory = new SkeletonFactory(); // Initialize the skeleton factory
        generateSkeletons(player); // Generate skeletons upon room creation
    }

    // Generate a number of skeletons based on the player's level
    private void generateSkeletons(Player player) {
        Random random = new Random();
        int numSkeletons = random.nextInt(3) + 1; // Generate 1 to 3 skeletons
        for (int i = 0; i < numSkeletons; i++) {
            addEnemy(skeletonFactory.createSkeleton(player));
        }
    }

    // Override playerTurn method to handle skeleton room interactions
    public void playerTurn(Game game, Room room) {
        if (hasEnemies()) {
            System.out.println(SKELETON_ROOM_ART);
            handleCombat(game, this); // Call handleCombat
        } else {
            handleEmptyRoom(game, room);
        }
    }
}