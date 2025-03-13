package GameV2;

import java.util.List;
import java.util.Random;

public class UltimateZombie extends Entity {
    private Random random = new Random();

    public UltimateZombie(String name, int level) {
        super(name, level, 180, 10, 50, 80);
    }

    @Override
    public void performAction(Game game, Room room) {
        System.out.println(getName() + " испускает мощную ауру разложения!");
        List<Entity> targets = getTargets(game, room);
        for (Entity target : targets) {
            target.addEffect(new WeakenedEffect(4, "Сильное Разложение"));
        }
        Entity target = targets.get(random.nextInt(targets.size()));
        attack(target, getWeapon());
    }

    @Override
    public void dropLoot(Game game) {
    }
}