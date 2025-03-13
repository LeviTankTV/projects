package GameV2;

import java.util.List;
import java.util.Random;

public class FrostSkeleton extends Skeleton {
    private Random random = new Random();

    public FrostSkeleton(String name, int level) {
        super(name, level, 18, 180);
    }

    @Override
    public void performAction(Game game, Room room) {
        attackWithIce(game, room);
    }

    private void attackWithIce(Game game, Room room) {
        System.out.println(getName() + " unleashes a chilling attack!");
        List<Entity> targets = getTargets(game, room);
        for (Entity target : targets) {
            attack(target, getWeapon());
            if (target instanceof Player && random.nextInt(100) < 40) {
                ((Player) target).addEffect(new FrozenEffect(1, "Frozen"));
                System.out.println("You are frozen!");
            }
        }
    }
}