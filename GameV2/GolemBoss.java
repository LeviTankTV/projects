package GameV2;

import java.util.List;
import java.util.Random;

public class GolemBoss extends Boss {
    private static final int HEAL_AMOUNT = 250; // Amount to heal

    public GolemBoss(String name, int level) {
        super(name, level, 7500, 0, 20, 0);
    }

    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";

    @Override
    public void performAction(Game game, Room room) {
        Random random = new Random();
        int actionChoice = random.nextInt(100); // Случайное число от 0 до 99

        // 50% шанс ничего не делать
        if (actionChoice < 50) {
            System.out.println(YELLOW + getName() + " ничего не делает на этом ходу..." + RESET);
            return; // Ничего не делать
        }

        // 25% шанс призвать голема
        if (actionChoice < 75) {
            summonGolem(game, room);
            return; // Призвать голема
        }

        // 25% шанс атаковать или исцелить
        if (random.nextBoolean()) {
            attackWithTargets(game, room);
        } else {
            healSelf();
        }
    }

    private void summonGolem(Game game, Room room) {
        System.out.println(PURPLE + getName() + " призывает могучего голема!" + RESET);
        GolemFactory golemFactory = new GolemFactory(); // Предполагается, что у вас есть GolemFactory
        Golem newGolem = golemFactory.createGolem(game.getPlayer()); // Создать нового голема
        room.addEnemy(newGolem); // Предполагается, что у Room есть метод для добавления врагов
        System.out.println(GREEN + newGolem.getName() + " восстает и присоединяется к битве!" + RESET);
    }

    private void attackWithTargets(Game game, Room room) {
        List<Entity> targets = getTargets(game, room);

        // Убедитесь, что есть цели для выбора
        if (!targets.isEmpty()) {
            Random random = new Random();
            int targetIndex = random.nextInt(targets.size());
            Entity target = targets.get(targetIndex);

            // Проверяем, не является ли цель тем же объектом
            if (target != this) {
                Weapon weapon = getWeapon();
                attack(target, weapon); // Выполнить атаку с помощью оружия
            }
        }
    }

    private void healSelf() {
        double newHealth = Math.min(getHealth() + HEAL_AMOUNT, getMaxHealth()); // Исцелить и убедиться, что максимальное здоровье не превышено
        setHealth(newHealth); // Предполагается, что есть метод для установки здоровья
        System.out.println(GREEN + getName() + " исцеляет себя на " + HEAL_AMOUNT + " HP! 🌟" + RESET);
    }


    @Override
    public void dropLoot(Game game) {
        super.dropLoot(game);
        Random random = new Random();
        if (random.nextDouble() < 0.5) {
            game.getPlayer().getInventory().addItem(new RockBomb());

            System.out.println(WHITE_BOLD + "╔═════════════ МИФИЧЕСКАЯ ДОБЫЧА ══════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + PURPLE_BOLD + "    💣 " + getName() + " роняет Каменную Бомбу!    " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "║" + RED_BOLD + "        ⚜️ МИФИЧЕСКИЙ ПРЕДМЕТ! ⚜️         " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚══════════════════════════════════════════════╝" + RESET);
        }

        if (random.nextDouble() < 0.5) {
            game.getPlayer().getInventory().addItem(new GolemKingWand());

            System.out.println(WHITE_BOLD + "╔═════════════ МИФИЧЕСКАЯ ДОБЫЧА ══════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + PURPLE_BOLD + "    🪄 " + getName() + " роняет Жезл Короля Големов!    " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "║" + RED_BOLD + "        ⚜️ МИФИЧЕСКИЙ ПРЕДМЕТ! ⚜️         " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚══════════════════════════════════════════════╝" + RESET);
        }
    }
    private static final String RESET = "\u001B[0m";
    private static final String WHITE_BOLD = "\u001B[1;37m";
    private static final String PURPLE_BOLD = "\u001B[1;35m";
    private static final String RED_BOLD = "\u001B[1;31m";
}