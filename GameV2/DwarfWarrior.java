package GameV2;

import java.util.List;
import java.util.Random;

public class DwarfWarrior extends Ally {
    public DwarfWarrior(String name, int level) {
        super(name, level, 200, 8, 10, 8, "Warrior");
    }

    @Override
    public void performAllyAction(Room room, Player player) {
        List<Entity> enemies = room.getEnemies();
        Random random = new Random();
        int action = random.nextInt(2);

        switch (action) {
            case 0: // Single target melee attack
                if (!enemies.isEmpty()) {
                    Entity target = enemies.get(random.nextInt(enemies.size()));
                    attack(target, getWeapon());
                    System.out.println(getName() + " swings their " + getWeapon().getName() + " at " + target.getName() + "!");
                }
                break;

            case 1: // Apply IncreasedDamageEffect to all allies
                player.addEffect(new IncreasedStrengthEffect(2, "Increased Damage", 1.4));
                System.out.println(getName() + " boosts the party's damage output!");
                for (Ally ally : player.getAllies()) {
                    IncreasedStrengthEffect i = new IncreasedStrengthEffect(2, "Increased Damage", 1.4);
                    i.apply(ally);
                }
                break;
        }
    }
}