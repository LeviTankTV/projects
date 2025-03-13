package GameV2;

import java.util.List;
import java.util.Random;

public class WiseMage extends Ally {
    private static final double CRITICAL_HIT_CHANCE = 0.15;

    public WiseMage(String name, int level) {
        super(name, level, 180, 12, 10, 4, "Spellcaster");
    }

    @Override
    public void performAllyAction(Room room, Player player) {
        List<Entity> enemies = room.getEnemies();
        Random random = new Random();
        int action = random.nextInt(3);

        System.out.println(CYAN_BOLD + "╔═══════════ ДЕЙСТВИЕ СОЮЗНИКА ═══════════╗" + RESET);

        switch (action) {
            case 0: // Одиночная атака заклинанием
                if (!enemies.isEmpty()) {
                    Entity target = enemies.get(random.nextInt(enemies.size()));
                    attack(target, getWeapon());
                    System.out.println(CYAN_BOLD + "║ " + YELLOW_BOLD + "🔮 " + getName() + " кастует мощное заклинание" + RESET);
                    System.out.println(CYAN_BOLD + "║ " + YELLOW_BOLD + "   используя " + getWeapon().getName() + RESET);
                    System.out.println(CYAN_BOLD + "║ " + YELLOW_BOLD + "   по " + target.getName() + "!" + RESET);
                }
                break;

            case 1: // Массовая атака заклинанием
                System.out.println(CYAN_BOLD + "║ " + RED_BOLD + "🌊 " + getName() + " выпускает волну магии!" + RESET);
                for (Entity enemy : enemies) {
                    double originalDamage = calculateDamage(getWeapon());
                    double reducedDamage = originalDamage * 0.7; // 70% от изначального урона
                    dealDamage(enemy, reducedDamage);
                    System.out.println(CYAN_BOLD + "║ " + RED_BOLD + "   " + enemy.getName() + " получает " +
                            String.format("%.2f", reducedDamage) + " урона!" + RESET);
                }
                break;

            case 2: // Баф союзника
                if (!player.getAllies().isEmpty()) {
                    Ally allyToBuff = player.getAllies().get(random.nextInt(player.getAllies().size()));
                    int buffAmount = 15;
                    int duration = 3;
                    IncreasedStrengthEffect strengthEffect = new IncreasedStrengthEffect(duration,
                            "Увеличенная Сила", buffAmount);
                    strengthEffect.apply(allyToBuff);
                    System.out.println(CYAN_BOLD + "║ " + GREEN_BOLD + "💪 " + getName() + " накладывает Увеличение Силы" + RESET);
                    System.out.println(CYAN_BOLD + "║ " + GREEN_BOLD + "   на " + allyToBuff.getName() + RESET);
                    System.out.println(CYAN_BOLD + "║ " + GREEN_BOLD + "   на " + duration + " ходов!" + RESET);
                }
                break;
        }

        System.out.println(CYAN_BOLD + "╚════════════════════════════════════════╝" + RESET);
    }

    private static final String CYAN_BOLD = "\033[1;36m";
    private static final String YELLOW_BOLD = "\033[1;33m";
    private static final String GREEN_BOLD = "\033[1;32m";
    private static final String RED_BOLD = "\033[1;31m";
    private static final String RESET = "\033[0m";
}