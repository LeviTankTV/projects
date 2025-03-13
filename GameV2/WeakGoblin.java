package GameV2;

import java.util.List;
import java.util.Random;

public class WeakGoblin extends Ally {
    public WeakGoblin(String name, int level) {
        super(name, level, 75, 3, 8, 1, "Goblin");
    }

    private static final String YELLOW_BOLD = "\u001B[1;33m";
    private static final String RESET = "\u001B[0m";
    @Override
    public void performAllyAction(Room room, Player player) {
        List<Entity> enemies = room.getEnemies();
        Random random = new Random();
        int action = random.nextInt(2);

        switch (action) {
            case 0: // Perform a melee attack on a random enemy
                if (!enemies.isEmpty()) {
                    Entity target = enemies.get(random.nextInt(enemies.size()));
                    attack(target, getWeapon());
                }
                break;

            case 1: // Apply WeakEffect to a random enemy
                if (!enemies.isEmpty()) {
                    Entity target = enemies.get(random.nextInt(enemies.size()));
                    target.addEffect(new WeakenedEffect(2, "Weakened"));
                    System.out.println(YELLOW_BOLD + "⚔️ " + getName() + " ослабляет " + target.getName() + "!" + RESET);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void dropLoot(Game game) {
        Player player = game.getPlayer();
    }
}