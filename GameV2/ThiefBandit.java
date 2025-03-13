package GameV2;

import java.util.List;
import java.util.Random;

public class ThiefBandit extends Bandit {
    public ThiefBandit(String name, int level) {
        super(name, level);
    }

    @Override
    public void dropLoot(Game game) {
        super.dropLoot(game); // Call the base class dropLoot method
    }

    @Override
    public void performAction(Game game, Room room) {
        List<Entity> targets = getTargets(game, room); // Get potential targets
        Random random = new Random();

        if (random.nextInt(100) < 40) { // 40% chance to run away
            runAway(game, room);
        } else {
            if (!targets.isEmpty()) {
                performRobbery(game.getPlayer()); // Attempt to rob a random target
            } else {
                System.out.println(getName() + " finds no targets to rob!");
            }
        }
    }

    public void runAway(Game game, Room room) {
        System.out.println(getName() + " attempts to sneak away from the battle!");

        // Logic to determine if the run is successful
        Random random = new Random();
        int chanceToEscape = random.nextInt(100) + 1; // 1-100

        if (chanceToEscape <= 50) { // 50% chance to escape
            System.out.println(getName() + " successfully escapes!");
            room.removeEnemy(this); // Remove the bandit from the room's enemy list
        } else {
            System.out.println(getName() + " fails to escape!");
            // Optionally, the bandit can take some action if it fails to escape
        }
    }
}