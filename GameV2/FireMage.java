package GameV2;

import java.util.List;


public class FireMage extends Mage {
    public FireMage(String name, int level) {
        super(name, level);
    }

    @Override
    public void performAction(Game game, Room room) {
        List<Entity> targets = getTargets(game, room);
        if (!targets.isEmpty()) {
            Entity target = targets.get(random.nextInt(targets.size()));
            if (target != this) {
                System.out.println(getName() + " casts a fireball on " + target.getName() + "!");
                attack(target, getWeapon());
                target.addEffect(new BurningEffect(3, "Burning for 3 turns!"));
            }
        }
    }

    @Override
    public void dropLoot(Game game) {
        // Drop gold between 30 and 150
        int gold = 30 + random.nextInt(121); // 30 to 150
        game.getPlayer().setGold(game.getPlayer().getGold() + gold);
        System.out.println(getName() + " drops " + gold + " gold!");

        // Check for FireScroll drop
        if (random.nextInt(100) < 5) { // 5% chance
            game.getPlayer().getInventory().addItem(new FireballScroll());
            System.out.println(getName() + " drops a FireScroll!");
        }

        // Check for UltraScroll drop
        if (random.nextInt(100) < 2) { // 2% chance
            game.getPlayer().getInventory().addItem(new UltraScroll());
            System.out.println(getName() + " drops an UltraScroll!");
        }
    }
}