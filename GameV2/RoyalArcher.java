package GameV2;

import java.util.List;
import java.util.Random;

public class RoyalArcher extends Royalty {
    public RoyalArcher(String name, int level) {
        super(name, level, 20, 75);
    }

    @Override
    public void performAction(Game game, Room room) {
        List<Entity> targets = getTargets(game, room);
        if (!targets.isEmpty()) {
            Random random = new Random();
            Entity target = targets.get(random.nextInt(targets.size()));

            System.out.println(getName() + " fires a precise arrow at " + target.getName() + "!");
            attack(target, getWeapon());
        }
    }

    @Override
    public void dropLoot(Game game) {
        Player player = game.getPlayer();
        Random random = new Random();

        // Проверка выпадения лука (25% шанс)
        if (random.nextInt(100) < 25) {
            Bow bow = new BowFactory().createRandomBow();
            player.getInventory().addItem(bow);

            System.out.println(WHITE_BOLD + "╔════════════════ ДОБЫЧА ═══════════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + GREEN_BOLD + "    🏹 " + getName() + " роняет " + bow.getName() + "!    " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚═══════════════════════════════════════════╝" + RESET);
        }

        // Проверка выпадения Золотого Ключа (4% шанс)
        if (random.nextInt(100) < 4) {
            player.getInventory().addItem(new GoldenKey());
            System.out.println(WHITE_BOLD + "╔═════════════ РЕДКАЯ ДОБЫЧА ════════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + GOLD_COLOR + "    🗝️ " + getName() + " роняет Золотой Ключ!    " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "║" + GREEN_BOLD + "        ✨ ЛЕГЕНДАРНЫЙ ПРЕДМЕТ! ✨        " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚════════════════════════════════════════════╝" + RESET);
        }
    }
    private static final String RESET = "\u001B[0m";
    private static final String WHITE_BOLD = "\u001B[1;37m";
    private static final String GOLD_COLOR = "\u001B[1;33m";
    private static final String GREEN_BOLD = "\u001B[1;32m";
    private static final String BLUE_BOLD = "\u001B[1;34m";
    private static final String PURPLE_BOLD = "\u001B[1;35m";
}