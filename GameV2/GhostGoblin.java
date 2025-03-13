package GameV2;

import java.util.List;
import java.util.Random;

public class GhostGoblin extends Entity {
    private Random random = new Random();

    public GhostGoblin(String name, int level) {
        super(name, level, 80, 5, 15, 70);
    }

    @Override
    public void performAction(Game game, Room room) {
        if (random.nextDouble() < 0.4) {
            System.out.println(getName() + " создает призрачную копию!");
            room.addEnemy(new GhostGoblin(getName() + " (Копия)", getLevel()));
            setHealth(getHealth() * 0.6);
        } else {
            List<Entity> targets = getTargets(game, room);
            Entity target = targets.get(random.nextInt(targets.size()));
            attack(target, getWeapon());
        }
    }

    @Override
    public void dropLoot(Game game) {
    }
}