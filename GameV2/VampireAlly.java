package GameV2;

import java.util.List;
import java.util.Random;

public class VampireAlly extends Ally {
    private static final int HEAL_AMOUNT = 50;
    private static final int MAX_HEALTH = 150;

    public VampireAlly(String name, int level) {
        super(name, level, MAX_HEALTH, 5, 10, 3, "Vampire");
    }

    @Override
    public void performAllyAction(Room room, Player player) {
        List<Entity> enemies = room.getEnemies();
        Random random = new Random();

        if (enemies.isEmpty()) {
            System.out.println(getName() + " finds no enemies to attack.");
            return;
        }

        int action = random.nextInt(2);

        switch (action) {
            case 0: // Attack a random enemy
                Entity target = enemies.get(random.nextInt(enemies.size()));
                attack(target, getWeapon());
                healSelf(HEAL_AMOUNT);
                System.out.println(getName() + " attacks " + target.getName() + " with their " + getWeapon().getName() + " and heals for " + HEAL_AMOUNT + " health.");
                break;

            case 1: // Self-heal
                healSelf(HEAL_AMOUNT);
                System.out.println(getName() + " uses dark magic to heal for " + HEAL_AMOUNT + " health.");
                break;
        }
    }

    private void healSelf(double amount) {
        double newHealth = Math.min(getHealth() + amount, MAX_HEALTH);
        setHealth(newHealth);
    }

    @Override
    public void dropLoot(Game game) {
    }
}