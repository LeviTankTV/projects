package GameV2;

import java.util.List;
import java.util.Random;

public class GhostArcher extends Entity {
    private Random random = new Random();
    private int arrowCount = 3;

    public GhostArcher(String name, int level) {
        super(name, level, 180, 6, 45, 60);
        setEvasion(0.15); // 15% chance to evade attacks
    }

    @Override
    public void performAction(Game game, Room room) {
        List<Entity> targets = getTargets(game, room);
        Entity target = targets.get(random.nextInt(targets.size()));

        if (arrowCount > 0 && random.nextDouble() < 0.4) {
            System.out.println(getName() + " выпускает залп призрачных стрел!");
            for (Entity t : targets) {
                double originalDamage = calculateDamage(getWeapon());
                double reducedDamage = originalDamage * 0.7; // 70% of original damage
                dealDamage(target, reducedDamage);
                System.out.println(t.getName() + " получает " + reducedDamage + " урона от призрачной стрелы!");
            }
            arrowCount--;
        } else {
            attack(target, getWeapon());
        }
    }

    @Override
    public void dropLoot(Game game) {
    }
}
