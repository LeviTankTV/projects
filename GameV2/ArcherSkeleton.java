package GameV2;

import java.util.List;
import java.util.Random;

public class ArcherSkeleton extends Skeleton {
    public ArcherSkeleton(String name, int level) {
        super(name, level, 12, 80); // Balanced attack and health
        setHealth(getMaxHealth());

    }

    @Override
    public void performAction(Game game, Room room) {
        // Get the list of all players and allies in the room
        List<Entity> targets = getTargets(game, room);
        Random random = new Random();

        // Attack each target (players and allies)
        for (Entity target : targets) {
            if (target != this) { // Ensure the target is not the archer itself
                shootArrow(target); // Attack each target
            }
        }
    }

    public void shootArrow(Entity target) {
        System.out.println(getName() + " shoots an arrow at " + target.getName() + "!");
        Random r = new Random();
        double rel = r.nextInt(20) + 15;
        target.takeDamage(rel);
    }
}