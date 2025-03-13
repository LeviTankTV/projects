package GameV2;

import java.util.List;
import java.util.Random;

public class GnomeWizard extends Ally {
    private static final int HEAL_AMOUNT = 125;

    public GnomeWizard(String name, int level) {
        super(name, level, 120, 10, 12, 8, "Wizard");
    }

    @Override
    public void performAllyAction(Room room, Player player) {
        List<Entity> enemies = room.getEnemies();
        Random random = new Random();
        int action = random.nextInt(5);

        switch (action) {
            case 0: // Attack 1-4 enemies for moderate damage
                int targets = random.nextInt(4) + 1;
                for (int i = 0; i < targets && !enemies.isEmpty(); i++) {
                    Entity target = enemies.remove(random.nextInt(enemies.size()));
                    attack(target, getWeapon());
                    System.out.println(getName() + " casts a spell and hits " + target.getName() + " with their " + getWeapon().getName() + "!");
                }
                break;

            case 1: // Area attack on all enemies
                for (Entity enemy : enemies) {
                    double originalDamage = calculateDamage(getWeapon());
                    double reducedDamage = originalDamage * 0.75; // 75% of original damage
                    dealDamage(enemy, reducedDamage);
                    System.out.println(getName() + " casts a powerful area spell, hitting " + enemy.getName() + " for " + String.format("%.2f", reducedDamage) + " damage!");
                }
                break;

            case 2: // High damage to 1-2 enemies
                int highDamageTargets = random.nextInt(2) + 1;
                for (int i = 0; i < highDamageTargets && !enemies.isEmpty(); i++) {
                    Entity target = enemies.remove(random.nextInt(enemies.size()));
                    double damage = calculateDamage(getWeapon()) * 2; // Double damage for high damage attack
                    dealDamage(target, damage);
                    System.out.println(getName() + " unleashes a devastating spell on " + target.getName() + " for " + String.format("%.2f", damage) + " damage!");
                }
                break;

            case 3: // Heal an ally
                Ally allyToHeal = player.getAllies().get(random.nextInt(player.getAllies().size()));
                allyToHeal.heal(HEAL_AMOUNT);
                System.out.println(getName() + " casts a healing spell on " + allyToHeal.getName() + " for " + HEAL_AMOUNT + " health.");
                break;

            case 4: // Apply regeneration effect to an ally
                Ally allyToRegenerate = player.getAllies().get(random.nextInt(player.getAllies().size()));
                allyToRegenerate.addEffect(new RegenerationEffect(3, "regen", 75));
                System.out.println(getName() + " grants regeneration to " + allyToRegenerate.getName() + "!");
                break;
        }
    }
}