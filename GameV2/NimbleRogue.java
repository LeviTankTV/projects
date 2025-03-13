package GameV2;

import java.util.List;
import java.util.Random;

public class NimbleRogue extends Ally {
    public NimbleRogue(String name, int level) {
        super(name, level, 140, 12, 10, 6, "Rogue");
    }

    @Override
    public void performAllyAction(Room room, Player player) {
        List<Entity> enemies = room.getEnemies();
        Random random = new Random();
        int action = random.nextInt(2);

        switch (action) {
            case 0: // Single target sneak attack
                if (!enemies.isEmpty()) {
                    Entity target = enemies.get(random.nextInt(enemies.size()));
                    attack(target, getWeapon());
                    System.out.println(getName() + " performs a sneak attack on " + target.getName() + " with their " +
                            getWeapon().getName() + "!");
                }
                break;

            case 1: // Apply MarkedEffect to a random enemy
                if (!enemies.isEmpty()) {
                    Entity target = enemies.get(random.nextInt(enemies.size()));
                    MarkedEffect Mark = new MarkedEffect(3, "Marked", 1.8);
                    Mark.apply(target);
                    System.out.println(getName() + " marks " + target.getName() + " for increased damage!");
                }
                break;
        }
    }
}