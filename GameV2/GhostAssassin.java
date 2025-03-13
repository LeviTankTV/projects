package GameV2;

import java.util.List;
import java.util.Random;

public class GhostAssassin extends Entity {
    private Random random = new Random();

    public GhostAssassin(String name, int level) {
        super(name, level, 400, 6, 20, 55);
        setEvasion(0.3); // 30% chance to evade attacks
    }

    @Override
    public void performAction(Game game, Room room) {
        List<Entity> targets = getTargets(game, room);
        Entity target = targets.get(random.nextInt(targets.size()));

        if (random.nextDouble() < 0.4) {
            System.out.println(getName() + " наносит критический удар из тени!");
            double originalDamage = calculateDamage(getWeapon());
            double criticalDamage = originalDamage * 2.5; // 70% of original damage
            dealDamage(target, criticalDamage);
            System.out.println(target.getName() + " получает " + criticalDamage + " урона!");
        } else {
            attack(target, getWeapon());
        }
    }

    @Override
    public void dropLoot(Game game) {

    }
}