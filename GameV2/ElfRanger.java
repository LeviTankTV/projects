package GameV2;

import java.util.List;
import java.util.Random;

// ElfRanger class
public class ElfRanger extends Ally {
    private static final int HEAL_AMOUNT = 80;

    public ElfRanger(String name, int level) {
        super(name, level, 150, 10, 14, 5, "Ranger");
    }

    @Override
    public void performAllyAction(Room room, Player player) {
        List<Entity> enemies = room.getEnemies();
        Random random = new Random();
        int action = random.nextInt(2);

        switch (action) {
            case 0: // Single target ranged attack
                if (!enemies.isEmpty()) {
                    Entity target = enemies.get(random.nextInt(enemies.size()));
                    attack(target, getWeapon());
                    System.out.println(getName() + " fires an arrow at " + target.getName() + " with their " + getWeapon().getName() + "!");
                }
                break;

            case 1: // Heal an ally
                Ally allyToHeal = player.getAllies().get(random.nextInt(player.getAllies().size()));
                allyToHeal.heal(HEAL_AMOUNT);
                System.out.println(getName() + " heals " + allyToHeal.getName() + " for " + HEAL_AMOUNT + " health.");
                break;
        }
    }
}