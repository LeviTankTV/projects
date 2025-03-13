package GameV2;

import java.util.Random;

public abstract class Mage extends Entity {
    protected Random random = new Random();

    private static final String GOLD_COLOR = "\033[1;33m"; // Золотой
    private static final String WHITE_BOLD = "\033[1;37m"; // Белый жирный
    private static final String GREEN_BOLD = "\033[1;32m"; // Зеленый жирный
    private static final String RESET = "\033[0m"; // Сброс цвета
    private static final String PURPLE_BOLD = "\u001B[1;35m";
    public Mage(String name, int level) {
        super(name, level, 125, 20, 15, 15); // Base health, attack, and defense
    }

    // The performAction method will handle the mage's actions
    public abstract void performAction(Game game, Room room);

    @Override
    public void dropLoot(Game game) {
        Player player = game.getPlayer();
        Random random = new Random();

        // Выпадение золота
        int gold = 30 + random.nextInt(121); // 30 to 150
        player.setGold(player.getGold() + gold);

        System.out.println(WHITE_BOLD + "╔══════════════════ ДОБЫЧА ══════════════════╗" + RESET);
        System.out.println(WHITE_BOLD + "║" + GOLD_COLOR + "    💰 " + getName() + " роняет " + gold + " золота!      " + WHITE_BOLD + "║" + RESET);
        System.out.println(WHITE_BOLD + "╚════════════════════════════════════════════╝" + RESET);

        // Проверка выпадения UltraScroll
        if (random.nextInt(100) < 2) { // 2% шанс
            player.getInventory().addItem(new UltraScroll());

            System.out.println(WHITE_BOLD + "╔═════════════ РЕДКАЯ ДОБЫЧА ════════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + PURPLE_BOLD + "   📜 " + getName() + " роняет UltraScroll!   " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "║" + GREEN_BOLD + "        ✨ ЛЕГЕНДАРНЫЙ ПРЕДМЕТ! ✨        " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚════════════════════════════════════════════╝" + RESET);
        }

        // Проверка выпадения Золотого Ключа
        int keyChance = random.nextInt(100) + 1; // 1-100
        if (keyChance <= 4) { // 4% шанс на выпадение золотого ключа
            player.getInventory().addItem(new GoldenKey());

            System.out.println(WHITE_BOLD + "╔═════════════ РЕДКАЯ ДОБЫЧА ════════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + GOLD_COLOR + "    🗝️ " + getName() + " роняет Золотой Ключ!    " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "║" + GREEN_BOLD + "        ✨ ЛЕГЕНДАРНЫЙ ПРЕДМЕТ! ✨        " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚════════════════════════════════════════════╝" + RESET);
        }
    }
}