package GameV2;

import java.util.Random;

class GoldGolem extends Golem {
    public GoldGolem(String name, int level) {
        super(name, level, 4, 375);
        setHealth(getMaxHealth());
    }

    @Override
    public void dropLoot(Game game) {
        Player player = game.getPlayer();
        Random random = new Random();

        // Выпадение золота
        int goldAmount = 125;
        player.setGold(player.getGold() + goldAmount);

        System.out.println(WHITE_BOLD + "╔══════════════════ ДОБЫЧА ══════════════════╗" + RESET);
        System.out.println(WHITE_BOLD + "║" + GOLD_BOLD + "    💰 " + getName() + " роняет " + goldAmount + " золота!       " + WHITE_BOLD + "║" + RESET);
        System.out.println(WHITE_BOLD + "╚════════════════════════════════════════════╝" + RESET);

        // 5% шанс выпадения Золотого Ключа
        if (random.nextInt(100) < 5) {
            player.getInventory().addItem(new GoldenKey());

            System.out.println(WHITE_BOLD + "╔═════════════ РЕДКАЯ ДОБЫЧА ════════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + GOLD_BOLD + "    🗝️ Вы нашли Золотой Ключ!            " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚════════════════════════════════════════════╝" + RESET);
        }
    }
    private static final String WHITE_BOLD = "\033[1;37m";
    private static final String GOLD_BOLD = "\033[1;33m";
    private static final String RESET = "\033[0m";
}