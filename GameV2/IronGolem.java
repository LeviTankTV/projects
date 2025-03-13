package GameV2;

import java.util.Random;

class IronGolem extends Golem {
    public IronGolem(String name, int level) {
        super(name, level, 9, 400);
        setHealth(getMaxHealth());
    }
    public static final String RESET = "\u001B[0m";

    // Bold White
    public static final String WHITE_BOLD = "\u001B[1;37m";

    // Gold (represented as yellow in most consoles)
    public static final String GOLD_COLOR = "\u001B[33m";
    @Override
    public void dropLoot(Game game) {
        Player player = game.getPlayer();
        Random random = new Random();
        int lootChance = random.nextInt(100) + 1; // 1-100
        if (lootChance <= 30) { // 30% шанс на выпадение лута
            int goldAmount = random.nextInt(10) + 10;
            player.setGold(player.getGold() + goldAmount);

            String goldMessage = "    💰 " + getName() + " роняет " + goldAmount + " золота!";
            int padding = 44 - goldMessage.length();
            String paddedGoldMessage = goldMessage + " ".repeat(padding);

            System.out.println(WHITE_BOLD + "╔══════════════════ ДОБЫЧА ══════════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + GOLD_COLOR + paddedGoldMessage + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚════════════════════════════════════════════╝" + RESET);
        }
}
}