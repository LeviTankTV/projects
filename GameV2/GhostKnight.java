package GameV2;

import java.util.List;
import java.util.Random;

public class GhostKnight extends Entity {
    private Random random = new Random();
    private boolean shieldActive = false;

    public GhostKnight(String name, int level) {
        super(name, level, 550, 9, 20, 85);
    }

    @Override
    public void performAction(Game game, Room room) {
        List<Entity> targets = getTargets(game, room);
        Entity target = targets.get(random.nextInt(targets.size()));

        if (!shieldActive && random.nextDouble() < 0.3) {
            System.out.println(getName() + " активирует призрачный щит!");
            shieldActive = true;
            setDefense(getDefense() * 1.5);
        } else {
            attack(target, getWeapon());
        }
    }

    @Override
    public boolean takeDamage(double damage) {
        if (shieldActive) {
            damage *= 0.5; // Reduce damage by 50% when shield is active
            System.out.println(getName() + " блокирует часть урона призрачным щитом!");
            shieldActive = false;
            setDefense(getDefense() / 1.5);
        }
        return super.takeDamage(damage);
    }

    @Override
    public void dropLoot(Game game) {
    }
}