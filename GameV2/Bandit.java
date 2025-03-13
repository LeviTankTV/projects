package GameV2;

import java.util.List;
import java.util.Random;

public class Bandit extends Entity {
    protected Random random = new Random();

    public Bandit(String name, int level) {
        super(name, level, 100, 30, 10, 15); // Example stats: health, attack, defense, speed
    }

    @Override
    public void dropLoot(Game game) {
        Player player = game.getPlayer();
        Random random = new Random();

        // Выпадение золота
        int gold = 45 + random.nextInt(131); // 45 to 175
        player.setGold(player.getGold() + gold);

        System.out.println(WHITE_BOLD + "╔══════════════════ ДОБЫЧА ══════════════════╗" + RESET);
        System.out.println(WHITE_BOLD + "║" + GOLD_COLOR + "    💰 " + getName() + " роняет " + gold + " золота!      " + WHITE_BOLD + "║" + RESET);
        System.out.println(WHITE_BOLD + "╚════════════════════════════════════════════╝" + RESET);

        // Проверка выпадения фрукта (25% шанс)
        if (random.nextInt(100) < 25) {
            Item fruit = new FruitFactory().createRandomFruit();
            player.getInventory().addItem(fruit);
            System.out.println(WHITE_BOLD + "╔════════════════ ДОБЫЧА ═══════════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + GREEN_BOLD + "    🍎 " + getName() + " роняет " + fruit.getName() + "!    " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚═══════════════════════════════════════════╝" + RESET);
        }

        // Проверка выпадения драгоценных камней (5% шанс)
        if (random.nextInt(100) < 5) {
            UniqueItem gem = getRandomGem();
            player.getInventory().addItem(gem);
            String gemColor = getGemColor(gem);

            System.out.println(WHITE_BOLD + "╔═════════════ МИФИЧЕСКАЯ ДОБЫЧА ══════════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + gemColor + "    💎 " + getName() + " роняет " + gem.getName() + "!    " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "║" + PURPLE_BOLD + "           ✨ МИФИЧЕСКИЙ ПРЕДМЕТ! ✨           " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚══════════════════════════════════════════════════╝" + RESET);
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

    private String getGemColor(UniqueItem gem) {
        if (gem instanceof Sapphire) return BLUE_BOLD;
        if (gem instanceof Diamond) return WHITE_BOLD;
        if (gem instanceof Emerald) return GREEN_BOLD;
        if (gem instanceof Ruby) return RED_BOLD;
        return WHITE_BOLD;
    }

    private static final String RESET = "\u001B[0m";
    private static final String WHITE_BOLD = "\u001B[1;37m";
    private static final String GOLD_COLOR = "\u001B[1;33m";
    private static final String RED_BOLD = "\u001B[1;31m";
    private static final String GREEN_BOLD = "\u001B[1;32m";
    private static final String BLUE_BOLD = "\u001B[1;34m";
    private static final String PURPLE_BOLD = "\u001B[1;35m";

    private UniqueItem getRandomGem() {
        int gemType = random.nextInt(4); // 0 to 3 for Sapphire, Diamond, Emerald, Ruby
        switch (gemType) {
            case 0: return new Sapphire();
            case 1: return new Diamond();
            case 2: return new Emerald();
            case 3: return new Ruby();
            default: return null; // Should not reach here
        }
    }

    public void performRobbery(Entity target) {
        if (target instanceof Entity) {
            Player playerTarget = (Player) target;
            int targetGold = playerTarget.getGold();
            int stolenAmount = Math.min(50 + random.nextInt(151), targetGold); // Steal between 50 and 200, but not more than what the target has

            playerTarget.setGold(targetGold - stolenAmount);
            System.out.println(getName() + " successfully robs " + playerTarget.getName() + " and steals " + stolenAmount + " gold!");
        } else {
            System.out.println(getName() + " cannot rob " + target.getName() + " because they are not a player!");
        }
    }

    public void performAction(Game game, Room room) {
        this.performRobbery(game.getPlayer());
        }
    }
