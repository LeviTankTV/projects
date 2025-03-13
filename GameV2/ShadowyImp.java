package GameV2;

import java.util.List;
import java.util.Random;

public class ShadowyImp extends Ally {
    private static final String PURPLE_BOLD = "\u001B[1;35m";
    private static final String RED_BOLD = "\u001B[1;31m";
    private static final String YELLOW_BOLD = "\u001B[1;33m";
    private static final String CYAN_BOLD = "\u001B[1;36m";
    private static final String RESET = "\u001B[0m";

    public ShadowyImp(String name, int level) {
        super(name, level, 60, 6, 27, 2, "Imp");
    }

    @Override
    public void performAllyAction(Room room, Player player) {
        List<Entity> enemies = room.getEnemies();
        Random random = new Random();
        int action = random.nextInt(2);

        switch (action) {
            case 0: // Perform a melee attack on a random enemy
                if (!enemies.isEmpty()) {
                    Entity target = enemies.get(random.nextInt(enemies.size()));
                    attack(target, null);
                }
                break;

            case 1: // Apply a random debuff to a random enemy
                if (!enemies.isEmpty()) {
                    Entity target = enemies.get(random.nextInt(enemies.size()));
                    int debuffType = random.nextInt(3);
                    switch (debuffType) {
                        case 0:
                            target.addEffect(new WeakenedEffect(2, "Weakened"));
                            System.out.println(YELLOW_BOLD + "💀 " + getName() + " ослабляет " + target.getName() + "!" + RESET);
                            break;
                        case 1:
                            target.addEffect(new MarkedEffect(2, "Marked", 0.75));
                            System.out.println(CYAN_BOLD + "🎯 " + getName() + " помечает " + target.getName() + "!" + RESET);
                            break;
                        case 2:
                            target.addEffect(new StunnedEffect(2, "Stunned"));
                            System.out.println(YELLOW_BOLD + "⚡ " + getName() + " оглушает " + target.getName() + "!" + RESET);
                            break;
                    }
                }
                break;

            default:
                break;
        }
    }
}