package GameV2;

import java.util.List;
import java.util.Random;

public abstract class Goblin extends Entity {

    private static final String GOLD_COLOR = "\033[1;33m"; // Золотой
    private static final String WHITE_BOLD = "\033[1;37m"; // Белый жирный
    private static final String GREEN_BOLD = "\033[1;32m"; // Зеленый жирный
    private static final String RESET = "\033[0m"; // Сброс цвета

    public Goblin(String name, int level, double baseAttack, double baseHealth) {
        super(name, level, baseHealth, 10, baseAttack, 8); // Call the Entity constructor with base values
        this.setHealth(baseHealth); // Start with full health
    }

    @Override
    public abstract void performAction(Game game, Room room);

    @Override
    public void dropLoot(Game game) {
        Player player = game.getPlayer();
        Random random = new Random();

        // Проверка выпадения золота
        int lootChance = random.nextInt(100) + 1;
        if (lootChance <= 30) { // 30% шанс на выпадение лута
            int goldAmount = random.nextInt(30) + 15;
            player.setGold(player.getGold() + goldAmount);

            System.out.println(WHITE_BOLD + "╔══════════════════ ДОБЫЧА ══════════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + GOLD_COLOR + "    💰 " + getName() + " роняет " + goldAmount + " золота!    " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚════════════════════════════════════════════╝" + RESET);
        }

        // Проверка выпадения ключа
        int keyChance = random.nextInt(100) + 1; // 1-100
        if (keyChance <= 4) { // 4% шанс на выпадение золотого ключа
            player.getInventory().addItem(new GoldenKey());

            System.out.println(WHITE_BOLD + "╔═════════════ РЕДКАЯ ДОБЫЧА ════════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + GOLD_COLOR + "    🗝️ " + getName() + " роняет Золотой Ключ!  " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "║" + GREEN_BOLD + "        ✨ ЛЕГЕНДАРНЫЙ ПРЕДМЕТ! ✨         " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚════════════════════════════════════════════╝" + RESET);
        }
    }


}