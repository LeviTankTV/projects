package GameV2;

import java.util.Random;

public class GolemRoom extends Room {
    private GolemFactory golemFactory; // Inject golem factory for generating golems

    public GolemRoom(Player player) {
        super();
        golemFactory = new GolemFactory(); // Initialize the golem factory
        generateGolems(player); // Generate golems upon room creation
    }

    private static final String Golem = """
            \033[0;36m
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣤⣤⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⣠⣾⣷⡄⢠⣾⣿⣿⣿⣿⣷⡄⢀⣾⣷⣄⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⣠⣾⣿⣿⣿⡇⢀⣤⣤⡄⢠⣤⣤⡀⢸⣿⣿⣿⣷⣄⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠉⠻⢿⣿⣿⣿⡀⠻⢿⡇⢸⡿⠟⢀⣿⣿⣿⣿⠟⠋⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⣠⡦⠀⠉⠉⠉⣠⣶⣤⣤⣤⣤⣶⣄⠉⠉⠉⠀⢴⣤⡀⠀⠀⠀⠀
⠀⠀⠀⣠⣄⠉⠀⠀⠀⠀⠀⠙⢿⣿⣿⣿⣿⡿⠟⠀⠀⠀⠀⠀⠉⣀⣀⡀⠀⠀
⠀⠀⣾⣿⣿⣆⠀⠀⠀⠀⠀⠀⠀⣀⣀⣀⣠⠀⠀⠀⠀⠀⠀⠀⢰⣿⣿⣷⠀⠀
⠀⢰⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⢿⣿⣿⡿⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⡇⠀
⠀⣸⣿⣿⣿⣿⡇⠀⠀⢀⣴⠟⠃⠀⠉⠉⠀⠘⠻⣦⡀⠀⠀⢰⣿⣿⣿⣿⣷⠀
⠀⠉⢉⣉⣉⡉⠁⢀⣀⠘⠃⠀⠀⠀⠀⠀⠀⠀⠀⠘⠃⣀⡀⠈⠉⣉⣉⡉⠉⠀
⠀⠀⠘⠿⠟⠁⣴⣿⣿⣷⡀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣾⣿⣿⣦⠈⠻⠿⠃⠀⠀
⠀⠀⠀⠀⠀⢰⣿⣿⣿⣿⣷⠀⠀⠀⠀⠀⠀⠀⠀⣼⣿⣿⣿⣿⣇⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⢿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⡿⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⣠⣶⣦⡌⠻⣿⠀⠀⠀⠀⠀⠀⠀⠀⣿⠟⢁⣴⣶⣤⡀⠀⠀⠀⠀
⠀⠀⠀⠀⠘⠛⠛⠛⠋⠀⠙⠁⠀⠀⠀⠀⠀⠀⠀⠛⠀⠙⠛⠛⠛⠃⠀⠀⠀⠀
            \033[0m
            """;

    // Generate a number of golems based on the player's level
    private void generateGolems(Player player) {
        Random random = new Random();
        int numGolems = random.nextInt(3) + 1; // Generate 1 to 3 golems
        for (int i = 0; i < numGolems; i++) {
            addEnemy(golemFactory.createGolem(player)); // Create and add golem to the room
        }
    }

    // Override playerTurn method to handle golem room interactions
    public void playerTurn(Game game, Room room) {
        if (hasEnemies()) {
            System.out.println(Golem);
            System.out.println("You encountered a horde of golems!");
            handleCombat(game, this); // Call handleCombat
        } else {
            handleEmptyRoom(game, room);
        }
    }
}