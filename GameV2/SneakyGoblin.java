package GameV2;

import java.util.List;
import java.util.Random;

public class SneakyGoblin extends Goblin {
    public SneakyGoblin(String name, int level) {
        super(name, level, 8, 80); // Lower attack but more stealthy
        setHealth(getMaxHealth());

    }

    @Override
    public void performAction(Game game, Room room) {
        List<Entity> targets = getTargets(game, room);
        Random random = new Random();

        if (random.nextBoolean()) { // 50% chance to run away
            runAway(game, room);
        } else {
            if (!targets.isEmpty()) {
                int targetIndex = random.nextInt(targets.size());
                Entity target = targets.get(targetIndex);
                sneakAttack(target); // Sneaky goblin attacks a random target
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
            room.removeEnemy(this); // Remove the goblin from the room's enemy list
        } else {
            System.out.println(getName() + " fails to escape!");
            // Optionally, the goblin can take some action if it fails to escape
        }
    }

    public void sneakAttack(Entity target) {
        System.out.println(getName() + " sneaks up and attacks " + target.getName() + "!");
        attack(target, getWeapon());
    }
}