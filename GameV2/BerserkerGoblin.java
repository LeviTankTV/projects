package GameV2;

import java.util.List;
import java.util.Random;

public class BerserkerGoblin extends Goblin {
    private Random random = new Random();

    public BerserkerGoblin(String name, int level) {
        super(name, level, 12, 180);
        setHealth(getMaxHealth());
    }

    @Override
    public void performAction(Game game, Room room) {
        List<Entity> targets = getTargets(game, room);
        if (!targets.isEmpty()) {
            if (random.nextDouble() < 0.3) { // 30% chance for area attack
                berserkAreaAttack(targets);
            } else {
                berserkSingleAttack(targets.get(random.nextInt(targets.size())));
            }
        }
    }

    private void berserkAreaAttack(List<Entity> targets) {
        double multiplier = getDamageMultiplier(random);
        System.out.println(getName() + " enters a berserker rage and swings wildly at everyone!");

        for (Entity target : targets) {
            double originalDamage = calculateDamage(getWeapon());
            double modifiedDamage = originalDamage * multiplier * 0.6; // 60% damage for area attack
            dealDamage(target, modifiedDamage);
            System.out.println(getName() + " hits " +
                    (target instanceof Player ? "you" : target.getName()) +
                    " for " + String.format("%.2f", modifiedDamage) + " damage!");
        }
    }

    private void berserkSingleAttack(Entity target) {
        double multiplier = getDamageMultiplier(random);
        double originalDamage = calculateDamage(getWeapon());
        double modifiedDamage = originalDamage * multiplier;

        System.out.println(getName() + " enters a berserker rage and attacks " +
                (target instanceof Player ? "you" : target.getName()) + "!");
        dealDamage(target, modifiedDamage);
        System.out.println(getName() + " hits for " + String.format("%.2f", modifiedDamage) + " damage!");
    }

    private double getDamageMultiplier(Random random) {
        int chance = random.nextInt(100) + 1;
        if (chance <= 1) return 16.0;      // 1% chance
        if (chance <= 5) return 8.0;       // 4% chance
        if (chance <= 15) return 4.0;      // 10% chance
        if (chance <= 30) return 2.0;      // 15% chance
        return 1.5;                        // 70% chance
    }
}