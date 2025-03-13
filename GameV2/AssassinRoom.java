package GameV2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AssassinRoom extends Room {
    private AssassinFactory assassinFactory; // Factory for creating assassins
    private List<ShadowAssassin> assassins; // List of created assassins
    public AssassinRoom(Player player) {
        super();
        assassinFactory = new AssassinFactory();
        assassins = new ArrayList<>();
        generateAssassins(player); // Generate assassins when the room is created
    }

    private static final String Assassin = """
            \033[0;36m
⠀⢠⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡄⠀
⠀⠀⠹⣷⣦⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣴⣾⠏⠀⠀
⠀⠀⠀⠙⣿⣿⣿⣦⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣴⣿⣿⣿⠋⠀⠀⠀
⠀⠀⠀⠀⠈⢿⣿⣿⣿⣷⣄⡀⠀⠀⠀⠀⠀⠀⢀⣤⣾⣿⣿⣿⡿⠁⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠹⣿⣿⡉⠻⣷⣤⡀⠀⠀⢀⣴⣾⠟⢉⣿⣿⠏⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠈⠻⣿⣦⡈⠻⣿⣦⣀⠻⠟⢁⣴⣿⠟⠁⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⢿⣿⣦⡈⠻⣿⣷⣄⠙⠿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⣠⡀⠀⠀⠀⠀⣀⠙⢿⣿⣦⡈⠻⣿⣷⣄⠀⠀⠀⠀⢀⣄⠀⠀⠀⠀
⠀⠀⠀⠘⢿⣷⡄⠀⣠⣾⣿⠗⠀⠙⢿⣿⣦⡈⠻⣿⣷⣄⠀⢠⣾⡿⠃⠀⠀⠀
⠀⠀⠀⠀⠀⠙⢿⣾⣿⠟⢁⣴⣿⡷⠀⠙⢿⣿⣦⡈⠻⣿⣷⡿⠋⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⢀⣿⣿⣶⣿⡿⠋⠀⠀⠀⠀⠙⢿⣿⣶⣿⣿⡀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⣰⣿⠟⠉⠙⢿⣷⣄⡀⠀⠀⢀⣠⣾⡿⠋⠉⠻⣿⣆⠀⠀⠀⠀⠀
⠀⠀⢀⣴⣾⠋⠀⠀⠀⠀⠀⠙⢿⡿⠂⠐⢿⡿⠋⠀⠀⠀⠀⠀⠙⣷⣦⡀⠀⠀
⠀⠸⣿⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢻⣿⠇⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            \033[0m
            """;

    // Generate a random number of assassins (from 1 to 5)
    private void generateAssassins(Player player) {
        Random random = new Random();
        int numZombies = random.nextInt(3) + 1;
        for (int i = 0; i < numZombies; i++) {
            addEnemy(assassinFactory.createAssassin(player));
        }
    }
    // Override the playerTurn method to interact with the assassins
    public void playerTurn(Game game, Room room) {
        if (hasEnemies()) {
            handleCombat(game, this); // For example, call the combat method
        } else {
            handleEmptyRoom(game, room);
        }
    }
}