package GameV2;

import java.util.List;

public class EarthMage extends Mage {
    public EarthMage(String name, int level) {
        super(name, level);
    }

    @Override
    public void performAction(Game game, Room room) {
        List<Entity> targets = getTargets(game, room);
        if (!targets.isEmpty()) {
            double originalDamage = calculateDamage(getWeapon());
            double reducedDamage = originalDamage * 0.7; // 70% of original damage for AoE

            for (Entity target : targets) {
                if (target != this) {
                    dealDamage(target, reducedDamage);
                    target.addEffect(new WeakenedEffect(2, "Weakened for 2 turns!"));
                    System.out.println(getName() + " casts an earthquake on " + target.getName() + " for " + reducedDamage + " damage and weakens them!");
                }
            }
        }
    }

    @Override
    public void dropLoot(Game game) {
        // Drop gold between 30 and 150
        int gold = 30 + random.nextInt(121); // 30 to 150
        game.getPlayer().setGold(game.getPlayer().getGold() + gold);
        System.out.println(getName() + " drops " + gold + " gold!");

        // Check for MarkedScroll drop
        if (random.nextInt(100) < 5) { // 5% chance
            game.getPlayer().getInventory().addItem(new MarkScroll());
            System.out.println(getName() + " drops a MarkedScroll!");
        }

        // Check for UltraScroll drop
        if (random.nextInt(100) < 2) { // 2% chance
            game.getPlayer().getInventory().addItem(new UltraScroll());
            System.out.println(getName() + " drops an UltraScroll!");
        }
    }
}