package GameV2;

import java.util.List;
import java.util.Random;

public class ManaBeast extends Ally {
    private static final int MAX_HEALTH = 400;

    public ManaBeast(String name, int level) {
        super(name, level, MAX_HEALTH, 3, 4, 5, "Mana Beast");
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
                System.out.println(getName() + " unleashes magical energy at " + target.getName() + "!");
                break;

            case 1: // Restore mana to the player
                int mana = random.nextInt(100) + 8;
                player.setMana(player.getMana() + mana);
                System.out.println(getName() + " restores " + mana + " mana to " +
                        player.getName() + "!");
                break;
        }
    }
}