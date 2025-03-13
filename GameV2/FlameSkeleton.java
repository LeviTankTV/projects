package GameV2;

import java.util.List;

public class FlameSkeleton extends Skeleton {
    public FlameSkeleton(String name, int level) {
        super(name, level, 19, 50);
    }

    @Override
    public void performAction(Game game, Room room) {
        attackWithFire(game, room);
    }

    private void attackWithFire(Game game, Room room) {
        System.out.println(getName() + " shoots a fiery attack!");
        List<Entity> targets = getTargets(game, room);
        for (Entity target : targets) {
            attack(target, getWeapon());
            if (target instanceof Player) {
                ((Player) target).addEffect(new BurningEffect(2, "Burning"));
                System.out.println("You are burned!");
            }
        }
    }
}