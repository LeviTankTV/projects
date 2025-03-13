package GameV2;

import java.util.List;
import java.util.Random;

public class ZombieBoss extends Boss {
    private boolean isSummoning; // Indicates if the ZombieBoss is currently summoning
    private int turnsRemaining; // Tracks the number of turns remaining for the summoning timer


    public ZombieBoss(String name, int level) {
        super(name, level, 2750, 50, 35, 15);
        this.isSummoning = false;
        this.turnsRemaining = 0;
    }

    @Override
    public void performAction(Game game, Room room) {
        Random random = new Random();
        List<Entity> targets = getTargets(game, room);
        List<Entity> zombies = room.getEnemies(); // Assuming Room class has a method to get zombies

        // Check if currently summoning
        if (isSummoning) {
            handleSummoning(game, room);
        } else {
            // Randomly decide to summon immediately, start the timer, or perform another action
            int actionChoice = random.nextInt(4); // Now we have 4 actions: heal, wave of destruction, attack, or summon

            switch (actionChoice) {
                case 0: // Heal all zombies
                    healZombies(zombies);
                    break;
                case 1: // Wave of destruction
                    unleashWaveOfDestruction(game, room);
                    break;
                case 2: // Attack a random target
                    if (!targets.isEmpty()) {
                        int targetIndex = random.nextInt(targets.size());
                        Entity target = targets.get(targetIndex);
                        attack(target, getWeapon()); // attack with its weapon
                    }
                    break;
                case 3: // Start summoning
                    startSummoningTimer();
                    break;
            }
        }
    }

    private void healZombies(List<Entity> zombies) {
        for (Entity zombie : zombies) {
            if (zombie.isAlive()) {
                zombie.heal(125); // Heal each zombie by 125 HP
            }
        }
    }

    private static final String RED_BOLD = "\033[1;31m";
    private static final String GREEN_BOLD = "\033[1;32m";
    private static final String YELLOW_BOLD = "\033[1;33m";
    private static final String BLUE_BOLD = "\033[1;34m";
    private static final String RESET = "\033[0m";

    private void unleashWaveOfDestruction(Game game, Room room) {
        List<Entity> targets = getTargets(game, room);
        for (Entity target : targets) {
            if (target.isAlive()) {
                double destructionDamage = getAttack() * 2; // Пример: удвоенный урон атаки
                target.takeDamage(destructionDamage);
                System.out.println(RED_BOLD + "╔══════════════════════════════════════════════════════════════╗" + RESET);
                System.out.println(RED_BOLD + "║ " + getName() + " обрушивает волну разрушения на " + target.getName() + "!" + RESET);
                System.out.println(RED_BOLD + "║ Нанесено урона: " + destructionDamage + RESET);
                System.out.println(RED_BOLD + "╚══════════════════════════════════════════════════════════════╝" + RESET);
            }
        }
    }

    private void startSummoningTimer() {
        System.out.println(YELLOW_BOLD + "⚠ " + getName() + " начинает призыв зомби... Это займет 2 хода! ⚠" + RESET);
        isSummoning = true;
        turnsRemaining = 2; // Установка таймера на 2 хода
    }

    private void handleSummoning(Game game, Room room) {
        turnsRemaining--;
        System.out.println(YELLOW_BOLD + "⏳ " + getName() + " призывает зомби! Осталось ходов: " + turnsRemaining + RESET);

        if (turnsRemaining <= 0) {
            System.out.println(GREEN_BOLD + "╔═════════════════════════════════════╗" + RESET);
            System.out.println(GREEN_BOLD + "║      Призыв зомби завершен!         ║" + RESET);
            System.out.println(GREEN_BOLD + "╚═════════════════════════════════════╝" + RESET);
            for (int i = 0; i < 3; i++) {
                summonZombie(game, room); // Призыв 3 новых зомби
            }
            isSummoning = false; // Сброс состояния призыва
        }
    }

    private void summonZombie(Game game, Room room) {
        System.out.println(BLUE_BOLD + "🧟 " + getName() + " призывает нового зомби!" + RESET);
        SecondZombieFactory zombieFactory = new SecondZombieFactory(); // Предполагается, что у вас есть ZombieFactory
        Zombie newZombie = zombieFactory.createZombie(game.getPlayer()); // Создание нового зомби
        room.addEnemy(newZombie); // Предполагается, что у Room есть метод для добавления врагов
        System.out.println(BLUE_BOLD + "🧟 " + newZombie.getName() + " присоединяется к битве!" + RESET);
    }

    // Для ZombieBoss
    @Override
    public void dropLoot(Game game) {
        super.dropLoot(game);
        Random random = new Random();
        if (random.nextDouble() < 0.5) {
            game.getPlayer().getInventory().addItem(new ZBSword());

            System.out.println(WHITE_BOLD + "╔═════════════ МИФИЧЕСКАЯ ДОБЫЧА ══════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + PURPLE_BOLD + "    🗡️ " + getName() + " роняет Меч Зомбосса!    " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "║" + RED_BOLD + "        ⚜️ МИФИЧЕСКИЙ ПРЕДМЕТ! ⚜️         " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚══════════════════════════════════════════════╝" + RESET);
        }
    }
    private static final String WHITE_BOLD = "\u001B[1;37m";
    private static final String PURPLE_BOLD = "\u001B[1;35m";
}