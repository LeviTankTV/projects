package GameV2;

import java.util.Random;

public class SecondSkeletonRoom extends Room {
    private SecondSkeletonFactory secondSkeletonFactory; // Use SecondSkeletonFactory for generating skeletons

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

    public SecondSkeletonRoom(Player player) {
        super();
        secondSkeletonFactory = new SecondSkeletonFactory(); // Initialize the SecondSkeletonFactory
        generateSkeletons(player); // Generate skeletons upon room creation
    }

    // Generate a number of skeletons based on the player's level (2 to 4 skeletons)
    private void generateSkeletons(Player player) {
        Random random = new Random();
        int numSkeletons = random.nextInt(3) + 2; // Generates either 2, 3, or 4 skeletons
        for (int i = 0; i < numSkeletons; i++) {
            addEnemy(secondSkeletonFactory.createSkeleton(player)); // Create and add the skeleton to the room
        }
    }

    // Override playerTurn method to handle interactions in the SecondSkeletonRoom
    @Override
    public void playerTurn(Game game, Room room) {
        if (hasEnemies()) {
            System.out.println(SKELETON_ROOM_ART);
            System.out.println("Вы столкнулись с ордой скелетов!");
            handleCombat(game, this); // Call handleCombat
        } else {
            handleEmptyRoom(game, room);
        }
    }
}