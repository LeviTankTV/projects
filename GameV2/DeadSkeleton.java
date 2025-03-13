package GameV2;

import java.util.List;
import java.util.Random;

public class DeadSkeleton extends Entity {
    private Random random = new Random();
    private int resurrectCount = 2;

    public DeadSkeleton(String name, int level) {
        super(name, level, 500, 8, 45, 75);
    }

    @Override
    public boolean takeDamage(double damage) {
        boolean evaded = super.takeDamage(damage);
        if (!evaded && getHealth() <= 0 && resurrectCount > 0) {
            System.out.println(getName() + " восстает из мертвых с еще большей силой!");
            setHealth(getMaxHealth() * 0.7);
            setAttack(getAttack() * 1.2);
            resurrectCount--;
            return false;
        }
        return evaded;
    }

    @Override
    public void performAction(Game game, Room room) {
        List<Entity> targets = getTargets(game, room);
        Entity target = targets.get(random.nextInt(targets.size()));
        attack(target, getWeapon());
    }

    @Override
    public void dropLoot(Game game) {

    }
}