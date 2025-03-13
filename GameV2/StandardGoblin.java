package GameV2;

import java.util.List;
import java.util.Random;

public class StandardGoblin extends Goblin {
    public StandardGoblin(String name, int level) {
        super(name, level, 10, 60); // Balanced attack and health
        setHealth(getMaxHealth());

    }

    @Override
    public void performAction(Game game, Room room) {
        List<Entity> targets = getTargets(game, room);

        if (!targets.isEmpty()) {
            stealGold(game.getPlayer());
        }
    }

    public void stealGold(Entity target) {
        Random random = new Random();
        int stolenAmount = Math.min(15, target.getGold()); // Steal up to 5 gold
        target.setGold(target.getGold() - stolenAmount);
        System.out.println(getName() + " steals " + stolenAmount + " gold from " + target.getName() + "!");
    }
}