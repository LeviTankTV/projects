package GameV2;

import java.util.List;
import java.util.Random;

public class AncientPirate extends Ally {
    private static final int TREASURE_AMOUNT = 75;

    private static final String BLUE_BOLD = "\u001B[1;34m";
    private static final String YELLOW_BOLD = "\u001B[1;33m";
    private static final String GREEN_BOLD = "\u001B[1;32m";
    private static final String RESET = "\u001B[0m";

    public AncientPirate(String name, int level) {
        super(name, level, 180, 10, 15, 4, "Pirate");
    }

    @Override
    public void performAllyAction(Room room, Player player) {
        List<Entity> enemies = room.getEnemies();
        Random random = new Random();
        int action = random.nextInt(2);


        switch (action) {
            case 0: // Perform a swashbuckling attack on a random enemy
                if (!enemies.isEmpty()) {
                    Entity target = enemies.get(random.nextInt(enemies.size()));
                    attack(target, getWeapon());
                } else {
                    System.out.println(YELLOW_BOLD + "❌ " + getName() + " не может найти врагов для атаки!" + RESET);
                }
                break;

            case 1: // Use treasure ability
                player.setGold(player.getGold() + TREASURE_AMOUNT);
                System.out.println(YELLOW_BOLD + "💰 " + getName() + " находит зарытое сокровище! Команда получает " +
                        TREASURE_AMOUNT + " золота." + RESET);
                break;
        }
    }
}