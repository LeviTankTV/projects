package GameV2;

import java.util.Random;

public class Zombie extends Entity {

    // Constructor for Zombie
    public Zombie(String name, int level, double baseAttack, double baseHealth) {
        super(name, level, baseHealth, 10, baseAttack, 8); // Call the Entity constructor with base values
        this.setHealth(baseHealth); // Start with full health
        setDefense(getDefense() - 1); // Zombies have slightly lower defense
    }

    // Override setHealth to ensure health management
    @Override
    public void setHealth(double health) {
        super.setHealth(health); // Call the parent method to set health
        // Additional logic can be added here if needed
    }


    @Override
    public void dropLoot(Game game) {
        Player player = game.getPlayer();
        Random random = new Random();

        // Проверка выпадения золота
        int lootChance = random.nextInt(100) + 1; // 1-100
        if (lootChance <= 30) { // 30% шанс на выпадение лута
            int goldAmount = random.nextInt(10) + 10;
            player.setGold(player.getGold() + goldAmount);

            String goldMessage = "    💰 " + getName() + " роняет " + goldAmount + " золота!";
            // Используем метод для правильного подсчета видимой длины строки
            int visibleLength = getVisibleLength(goldMessage);
            int padding = Math.max(0, 44 - visibleLength); // Добавляем Math.max для предотвращения отрицательных значений
            String paddedGoldMessage = goldMessage + " ".repeat(padding);

            System.out.println(WHITE_BOLD + "╔══════════════════ ДОБЫЧА ══════════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + GOLD_COLOR + paddedGoldMessage + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚════════════════════════════════════════════╝" + RESET);
        }

        // Проверка выпадения ключа
        int keyChance = random.nextInt(100) + 1; // 1-100
        if (keyChance <= 4) { // 4% шанс на выпадение золотого ключа
            player.getInventory().addItem(new GoldenKey());

            String keyMessage = "    🗝️ " + getName() + " роняет Золотой Ключ!";
            int visibleLength = getVisibleLength(keyMessage);
            int keyPadding = Math.max(0, 44 - visibleLength); // Добавляем Math.max для предотвращения отрицательных значений
            String paddedKeyMessage = keyMessage + " ".repeat(keyPadding);

            System.out.println(WHITE_BOLD + "╔═════════════ РЕДКАЯ ДОБЫЧА ════════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + GOLD_COLOR + paddedKeyMessage + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "║" + GREEN_BOLD + "        ✨ ЛЕГЕНДАРНЫЙ ПРЕДМЕТ! ✨          " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚════════════════════════════════════════════╝" + RESET);
        }
    }

    // Вспомогательный метод для подсчета видимой длины строки
    private int getVisibleLength(String str) {
        return str.codePointCount(0, str.length());
        // Альтернативный вариант:
        // return str.replaceAll("[^\x00-\xff]", "  ").length();
    }

    private static final String GOLD_COLOR = "\033[1;33m"; // Золотой
    private static final String WHITE_BOLD = "\033[1;37m"; // Белый жирный
    private static final String GREEN_BOLD = "\033[1;32m"; // Зеленый жирный
    private static final String RESET = "\033[0m"; // Сброс цвета

}