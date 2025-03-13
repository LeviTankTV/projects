package GameV2;

import java.util.List;
import java.util.Random;

public class ShadowSkeleton extends Skeleton {
    private Random random = new Random();

    public ShadowSkeleton(String name, int level) {
        super(name, level, 35, 160);
    }

    @Override
    public void performAction(Game game, Room room) {
        attackRandomTarget(game, room);
    }

    private void attackRandomTarget(Game game, Room room) {
        List<Entity> targets = getTargets(game, room);
        if (!targets.isEmpty()) {
            Entity target = targets.get(random.nextInt(targets.size()));
            System.out.println(getName() + " strikes from the shadows at " +
                    (target instanceof Player ? "you" : target.getName()) + "!");
            attack(target, getWeapon());
        } else {
            System.out.println(getName() + " finds no targets to attack from the shadows!");
        }
    }
}