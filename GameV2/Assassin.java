package GameV2;

import java.util.List;
import java.util.Random;

// Assassin subclass of Entity
public class Assassin extends Entity {
    public Assassin(String name, int level) {
        super(name, level, 120, 20, 15, 10); // Example stats
    }

    @Override
    public void dropLoot(Game game) {
        Player player = game.getPlayer();
        Random random = new Random();

        // Выпадение золота
        int gold = 30 + random.nextInt(121); // 30 to 150
        player.setGold(player.getGold() + gold);

        System.out.println(WHITE_BOLD + "╔══════════════════ ДОБЫЧА ══════════════════╗" + RESET);
        System.out.println(WHITE_BOLD + "║" + GOLD_BOLD + "    💰 " + getName() + " роняет " + gold + " золота!      " + WHITE_BOLD + "║" + RESET);
        System.out.println(WHITE_BOLD + "╚════════════════════════════════════════════╝" + RESET);

        // Проверка выпадения кинжалов (25% шанс для каждого)
        if (random.nextInt(100) < 25) {
            player.getInventory().addItem(new Dagger());
            System.out.println(WHITE_BOLD + "╔════════════════ ДОБЫЧА ═══════════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + BLUE_BOLD + "    🗡️ " + getName() + " роняет Кинжал!    " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚════════════════════════════════════════════╝" + RESET);
        }

        if (random.nextInt(100) < 25) {
            player.getInventory().addItem(new ExplosionDagger());
            System.out.println(WHITE_BOLD + "╔════════════ РЕДКАЯ ДОБЫЧА ═════════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + RED_BOLD + "    🗡️ " + getName() + " роняет Взрывной Кинжал!    " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "║" + GREEN_BOLD + "         ✨ РЕДКИЙ ПРЕДМЕТ! ✨          " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚════════════════════════════════════════════╝" + RESET);
        }

        if (random.nextInt(100) < 25) {
            player.getInventory().addItem(new IceDagger());
            System.out.println(WHITE_BOLD + "╔════════════ РЕДКАЯ ДОБЫЧА ═════════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + CYAN_BOLD + "    🗡️ " + getName() + " роняет Ледяной Кинжал!    " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "║" + GREEN_BOLD + "         ✨ РЕДКИЙ ПРЕДМЕТ! ✨          " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚════════════════════════════════════════════╝" + RESET);
        }

        // Проверка выпадения Золотого Ключа (4% шанс)
        if (random.nextInt(100) < 4) {
            player.getInventory().addItem(new GoldenKey());
            System.out.println(WHITE_BOLD + "╔═════════════ РЕДКАЯ ДОБЫЧА ════════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + GOLD_BOLD + "    🗝️ " + getName() + " роняет Золотой Ключ!    " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "║" + GREEN_BOLD + "        ✨ ЛЕГЕНДАРНЫЙ ПРЕДМЕТ! ✨        " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚════════════════════════════════════════════╝" + RESET);
        }

        // Проверка выпадения драгоценных камней (5% шанс)
        if (random.nextInt(100) < 5) {
            int gemType = random.nextInt(4);
            UniqueItem gem = null;
            String gemColor = "";

            switch (gemType) {
                case 0:
                    gem = new Sapphire();
                    gemColor = BLUE_BOLD;
                    break;
                case 1:
                    gem = new Diamond();
                    gemColor = WHITE_BOLD;
                    break;
                case 2:
                    gem = new Emerald();
                    gemColor = GREEN_BOLD;
                    break;
                case 3:
                    gem = new Ruby();
                    gemColor = RED_BOLD;
                    break;
            }
            player.getInventory().addItem(gem);

            System.out.println(WHITE_BOLD + "╔═════════════ МИФИЧЕСКАЯ ДОБЫЧА ══════════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + gemColor + "    💎 " + getName() + " роняет " + gem.getName() + "!    " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "║" + PURPLE_BOLD + "           ✨ МИФИЧЕСКИЙ ПРЕДМЕТ! ✨           " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚═══════════════════════════════════════════════════╝" + RESET);
        }
    }

    private static final String RESET = "\u001B[0m";
    private static final String WHITE_BOLD = "\u001B[1;37m";
    private static final String GOLD_BOLD = "\u001B[1;33m";
    private static final String RED_BOLD = "\u001B[1;31m";
    private static final String GREEN_BOLD = "\u001B[1;32m";
    private static final String BLUE_BOLD = "\u001B[1;34m";
    private static final String PURPLE_BOLD = "\u001B[1;35m";
    private static final String CYAN_BOLD = "\u001B[1;36m";


    @Override
    public void performAction(Game game, Room room) {
        Random random = new Random();
        List<Entity> targets = getTargets(game, room);

        // Ensure there are targets to select from
        if (!targets.isEmpty()) {
            int targetIndex = random.nextInt(targets.size());
            Entity target = targets.get(targetIndex);

            // Check if the target is not the same as the current entity
            if (target != this) {
                // Perform a stealth attack
                performStealthAttack(target);
            }
        }
    }

    public void performStealthAttack(Entity target) {
        System.out.println(getName() + " performs a stealth attack on " + target.getName());
        attack(target, getWeapon());
    }
}