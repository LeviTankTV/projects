package GameV2;

import java.util.List;
import java.util.Random;

public class Boss extends Entity {

    private static final String RESET = "\u001B[0m";
    private static final String WHITE_BOLD = "\u001B[1;37m";
    private static final String GOLD_COLOR = "\u001B[38;5;220m";
    private static final String PURPLE_BOLD = "\u001B[1;35m";
    private static final String GREEN_BOLD = "\u001B[1;32m";
    private static final String RED_BOLD = "\u001B[1;31m";
    private Random random = new Random();

    public Boss(String name, int level, double baseHealth, double healthBonus, double baseAttack, double baseDefense) {
        super(name, level, baseHealth, healthBonus, baseAttack, baseDefense);
    }

    @Override
    public void performAction(Game game, Room room) {
        Random random = new Random();
        List<Entity> targets = getTargets(game, room);

        // Boss can perform a special action or attack
        if (!targets.isEmpty()) {
            int targetIndex = random.nextInt(targets.size());
            Entity target = targets.get(targetIndex);

            // Check if the target is not the same as the current entity
            if (target != this) {
                // Boss can have a special attack or just a regular attack
                if (random.nextBoolean()) {
                    // Special attack logic (for example, a powerful attack)
                    System.out.println(getName() + " performs a powerful attack on " + target.getName() + "!");
                    double specialDamage = getAttack() * 1.5; // 50% more damage
                    target.takeDamage(specialDamage);
                } else {
                    // Regular attack
                    Weapon weapon = getWeapon(); // Get the boss's weapon
                    attack(target, weapon); // Perform the attack with the weapon
                }
            }
        }
    }

    @Override
    public void dropLoot(Game game) {
        Player player = game.getPlayer();
        Random random = new Random();

        // Выпадение золота
        int lootAmount = random.nextInt(201) + 300; // 300 to 500
        player.setGold(player.getGold() + lootAmount);

        System.out.println(WHITE_BOLD + "╔══════════════════ ДОБЫЧА БОССА ══════════════════╗" + RESET);
        System.out.println(WHITE_BOLD + "║" + GOLD_COLOR + "      💰 " + getName() + " роняет " + lootAmount + " золота!      " + WHITE_BOLD + "║" + RESET);
        System.out.println(WHITE_BOLD + "╚═══════════════════════════════════════════════════╝" + RESET);

        // Проверка выпадения Dark Key
        if (random.nextDouble() < 0.2) { // 20% шанс
            player.getInventory().addItem(new DarkKey());

            System.out.println(WHITE_BOLD + "╔═════════════ МИФИЧЕСКАЯ ДОБЫЧА ══════════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + PURPLE_BOLD + "      🗝️ " + getName() + " роняет Тёмный Ключ!      " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "║" + RED_BOLD + "           ✨ МИФИЧЕСКИЙ ПРЕДМЕТ! ✨           " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚═══════════════════════════════════════════════════╝" + RESET);
        }

        // Проверка выпадения Золотого Ключа
        int keyChance = random.nextInt(100) + 1; // 1-100
        if (keyChance <= 50) { // 50% шанс на выпадение золотого ключа
            player.getInventory().addItem(new GoldenKey());

            System.out.println(WHITE_BOLD + "╔══════════════ РЕДКАЯ ДОБЫЧА ═════════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + GOLD_COLOR + "     🗝️ " + getName() + " роняет Золотой Ключ!     " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "║" + GREEN_BOLD + "         ✨ ЛЕГЕНДАРНЫЙ ПРЕДМЕТ! ✨         " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚═══════════════════════════════════════════════╝" + RESET);
        }
    }
}