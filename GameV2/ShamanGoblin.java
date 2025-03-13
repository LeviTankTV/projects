package GameV2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShamanGoblin extends Goblin {
    public ShamanGoblin(String name, int level) {
        super(name, level, 5, 76);
        setHealth(getMaxHealth());
    }

    @Override
    public void performAction(Game game, Room room) {
        if (Math.random() < 0.75) {
            healAlly(room); // 75% chance to heal an ally
        } else {
            basicAttack(game, room); // Otherwise, perform a basic attack
        }
    }

    private void healAlly(Room room) {
        System.out.println(getName() + " performs a healing ritual!");
        Entity ally = findAlly(room);
        if (ally != null) {
            ally.heal(75);
            System.out.println(ally.getName() + " is healed for 75 health!");
        } else {
            System.out.println("But there are no allies to heal!");
        }
    }

    private Entity findAlly(Room room) {
        List<Entity> entities = room.getEnemies();
        List<Entity> allies = new ArrayList<>();

        for (Entity entity : entities) {
            if (entity instanceof Entity && entity != this) {
                allies.add(entity);
            }
        }

        if (!allies.isEmpty()) {
            Random random = new Random();
            return allies.get(random.nextInt(allies.size()));
        }
        return null;
    }

    private void basicAttack(Game game, Room room) {
        List<Entity> targets = getTargets(game, room);
        if (!targets.isEmpty()) {
            Random random = new Random();
            Entity target = targets.get(random.nextInt(targets.size()));
            System.out.println(getName() + " attacks with their staff!");
            attack(target, getWeapon());
        }
    }
}