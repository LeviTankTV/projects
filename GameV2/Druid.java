package GameV2;

import java.util.List;
import java.util.Random;

public class Druid extends Ally {
    private static final double CRITICAL_HIT_CHANCE = 0.2;

    public Druid(String name, int level) {
        super(name, level, 220, 10, 15, 5, "Healer");
    }

    @Override
    public void performAllyAction(Room room, Player player) {
        List<Entity> enemies = room.getEnemies();
        Random random = new Random();
        int action = random.nextInt(3);

        switch (action) {
            case 0: // Heal all allies
                for (Ally ally : player.getAllies()) {
                    int healAmount = 20;
                    ally.heal(healAmount);
                    System.out.println(getName() + " heals " + ally.getName() + " for " + healAmount + " health.");
                }
                break;

            case 1: // Single target attack
                if (!enemies.isEmpty()) {
                    Entity target = enemies.get(random.nextInt(enemies.size()));
                    attack(target, getWeapon());
                    System.out.println(getName() + " attacks " + target.getName() + " with their " +
                            getWeapon().getName() + "!");
                }
                break;

            case 2: // Area attack
                for (Entity enemy : enemies) {
                    double originalDamage = calculateDamage(getWeapon());
                    double reducedDamage = originalDamage * 0.7; // 70% of original damage
                    dealDamage(enemy, reducedDamage);
                    System.out.println(getName() + " unleashes nature's fury, hitting " + enemy.getName() + " for " +
                            String.format("%.2f", reducedDamage) + " damage!");
                }
                break;
        }
    }
}