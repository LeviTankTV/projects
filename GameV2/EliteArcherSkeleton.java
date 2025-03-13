package GameV2;

import java.util.List;
import java.util.Random;

public class EliteArcherSkeleton extends Skeleton {
    private Random random = new Random();

    public EliteArcherSkeleton(String name, int level) {
        super(name, level, 20, 225);
    }

    @Override
    public void performAction(Game game, Room room) {
        attackFromDistance(game, room);
    }

    private void attackFromDistance(Game game, Room room) {
        System.out.println(getName() + " fires powerful arrows!");
        List<Entity> targets = getTargets(game, room);
        for (Entity target : targets) {
            if (target instanceof Player || random.nextInt(100) < 50) {
                attack(target, getWeapon());
            }
        }
    }
}