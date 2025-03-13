package GameV2;

import java.util.List;
import java.util.Random;

public class GhostBandit extends Entity {
    private Random random = new Random();

    public GhostBandit(String name, int level) {
        super(name, level, 180, 7, 15, 65);
        setEvasion(0.5); // 50% chance to evade attacks
    }

    @Override
    public void performAction(Game game, Room room) {
        List<Entity> targets = getTargets(game, room);
        Entity target = targets.get(random.nextInt(targets.size()));

        if (random.nextDouble() < 0.3) {
            System.out.println(getName() + " использует призрачное ограбление!");
            int stolenGold = random.nextInt(50) + 25;
            game.getPlayer().setGold(game.getPlayer().getGold() - stolenGold);
            System.out.println(getName() + " украл " + stolenGold + " золота!");
        } else {
            attack(target, getWeapon());
        }
    }

    @Override
    public void dropLoot(Game game) {
    }
}