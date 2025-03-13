package GameV2;

import java.util.List;

public class UniqueMage extends Mage {
    public UniqueMage(String name, int level) {
        super(name, level);
    }

    @Override
    public void performAction(Game game, Room room) {
        List<Entity> targets = getTargets(game, room);
        if (!targets.isEmpty()) {
            Entity target = targets.get(random.nextInt(targets.size()));
            if (target != this) {
                System.out.println(getName() + " casts a unique spell on " + target.getName() + "!");
                attack(target, getWeapon());
                target.addEffect(new NightmarishEffect(2, "Nightmarish for 2 turns!"));
            }
        }
    }

    @Override
    public void dropLoot(Game game) {
        // Drop gold between 30 and 150
        int gold = 30 + random.nextInt(121); // 30 to 150
        game.getPlayer().setGold(game.getPlayer().getGold() + gold);
        System.out.println(getName() + " drops " + gold + " gold!");

        // Check for WeakenScroll drop
        if (random.nextInt(100) < 5) { // 5% chance
            game.getPlayer().getInventory().addItem(new WeakenScroll());
            System.out.println(getName() + " drops a WeakenScroll!");
        }

        // Check for UltraScroll drop
        if (random.nextInt(100) < 2) { // 2% chance
            game.getPlayer().getInventory().addItem(new UltraScroll());
            System.out.println(getName() + " drops an UltraScroll!");
        }
    }
}