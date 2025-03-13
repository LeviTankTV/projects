package GameV2;

import java.util.List;
import java.util.Random;

public class ArmoredSkeleton extends Skeleton {
    private Random random = new Random();

    public ArmoredSkeleton(String name, int level) {
        super(name, level, 16, 420); // High health for tank role
    }

    @Override
    public void performAction(Game game, Room room) {
        attackRandomTarget(game,room);
    }

    private void attackRandomTarget(Game game, Room room) {
        List<Entity> possibleTargets = getTargets(game, room);

        if (!possibleTargets.isEmpty()) {
            // Select random target from the list
            Entity target = possibleTargets.get(random.nextInt(possibleTargets.size()));

            System.out.println(getName() + " swings a heavy attack at " +
                    (target instanceof Player ? "you" : target.getName()) + "!");

            attack(target, getWeapon());
        } else {
            System.out.println(getName() + " finds no targets to attack!");
        }
    }
}