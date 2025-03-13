package GameV2;

import java.util.List;
import java.util.Random;

public class LightBard extends Ally {
    private static final int REGENERATION_AMOUNT = 20;
    private static final int REGENERATION_DURATION = 2;

    public LightBard(String name, int level) {
        super(name, level, 160, 8, 12, 5, "Bard");
    }

    @Override
    public void performAllyAction(Room room, Player player) {
        List<Entity> enemies = room.getEnemies();
        Random random = new Random();
        int action = random.nextInt(2);

        switch (action) {
            case 0: // Apply regeneration to all allies and player
                RegenerationEffect playerRegenerationEffect = new RegenerationEffect(REGENERATION_DURATION, "Regeneration", REGENERATION_AMOUNT);
                playerRegenerationEffect.apply(player);
                System.out.println(getName() + " applies Regeneration to " + player.getName() + " for " + REGENERATION_DURATION + " turns, restoring " + REGENERATION_AMOUNT + " health each turn.");

                for (Ally ally : player.getAllies()) {
                    RegenerationEffect allyRegenerationEffect = new RegenerationEffect(REGENERATION_DURATION, "Regeneration", REGENERATION_AMOUNT);
                    allyRegenerationEffect.apply(ally);
                    System.out.println(getName() + " applies Regeneration to " + ally.getName() + " for " + REGENERATION_DURATION + " turns, restoring " + REGENERATION_AMOUNT + " health each turn.");
                }
                break;

            case 1: // Area music attack
                int numEnemiesToHit = random.nextInt(3) + 2; // Hit between 2 to 4 enemies
                for (int i = 0; i < numEnemiesToHit && i < enemies.size(); i++) {
                    Entity target = enemies.get(random.nextInt(enemies.size()));
                    double originalDamage = calculateDamage(getWeapon());
                    double reducedDamage = originalDamage * 0.6;
                    dealDamage(target, reducedDamage);
                    System.out.println(getName() + " hits " + target.getName() + " with music for " +
                            String.format("%.2f", reducedDamage) + " damage!");
                }
                break;
        }
    }
}