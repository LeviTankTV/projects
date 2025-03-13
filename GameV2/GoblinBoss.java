package GameV2;

import java.util.List;
import java.util.Random;

public class GoblinBoss extends Boss {
    private boolean isSummoning; // Indicates if the GoblinBoss is currently summoning
    private int turnsRemaining; // Tracks the number of turns remaining for the summoning timer

    public GoblinBoss(String name, int level) {
        super(name, level, 2750, 50, 20, 20);
        this.isSummoning = false;
        this.turnsRemaining = 0;
    }

    @Override
    public void performAction(Game game, Room room) {
        Random random = new Random();
        List<Entity> allies = room.getEnemies(); // Get the GoblinBoss's allies (goblins)

        // Check if currently summoning
        if (isSummoning) {
            handleSummoning(game, room);
        } else {
            // Randomly choose an action
            int actionChoice = random.nextInt(4); // Now we have 4 actions

            switch (actionChoice) {
                case 0: // Increase strength of allies
                    increaseStrengthOfAllies(allies);
                    break;
                case 1: // Start summoning timer
                    startSummoningTimer();
                    break;
                case 2: // Summon one goblin
                    summonGoblin(game, room);
                    break;
                case 3: // Rob the player
                    robPlayer(game);
                    break;
            }
        }
    }

    private void increaseStrengthOfAllies(List<Entity> allies) {
        for (Entity ally : allies) {
            if (ally.isAlive() && ally instanceof Goblin) {
                ally.addEffect(new IncreasedStrengthEffect(3, "Increased strength from Goblin Boss", 1.5));
                System.out.println(GREEN + "💪 " + getName() + " усиливает " + ally.getName() + "! Сила гоблина возросла!" + RESET);
            }
        }
    }

    private void startSummoningTimer() {
        System.out.println(PURPLE + "🌀 " + getName() + " начинает призыв гоблинов... Это займет 2 хода!" + RESET);
        isSummoning = true;
        turnsRemaining = 2; // Установить таймер на 2 хода
    }

    private void handleSummoning(Game game, Room room) {
        turnsRemaining--;
        System.out.println(BLUE + "⏳ " + getName() + " призывает гоблинов! Осталось ходов: " + turnsRemaining + RESET);

        if (turnsRemaining <= 0) {
            for (int i = 0; i < 3; i++) {
                summonGoblin(game, room); // Призвать 3 новых гоблина
            }
            isSummoning = false; // Сбросить состояние призыва
            System.out.println(YELLOW + "✨ " + getName() + " успешно призвал гоблинов!" + RESET);
        }
    }

    private void summonGoblin(Game game, Room room) {
        System.out.println(PURPLE + "🌀 " + getName() + " призывает нового гоблина!" + RESET);
        GoblinFactory goblinFactory = new GoblinFactory(); // Предполагается, что у вас есть GoblinFactory
        Goblin newGoblin = goblinFactory.createGoblin(game.getPlayer()); // Создать нового гоблина
        room.addEnemy(newGoblin); // Предполагается, что у Room есть метод для добавления врагов
        System.out.println(GREEN + "👺 Гоблин " + newGoblin.getName() + " присоединился к битве!" + RESET);
    }

    private void robPlayer(Game game) {
        Player player = game.getPlayer(); // Получить игрока
        Random random = new Random();
        int stolenGold = 500 + random.nextInt(1500); // Случайно украсть от 500 до 2000 золота
        player.setGold(player.getGold() - stolenGold); // Предполагается, что есть метод для уменьшения золота
        System.out.println(RED + "💰 " + getName() + " крадет у игрока " + stolenGold + " золота!" + RESET);
    }
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    @Override
    public void dropLoot(Game game) {
        super.dropLoot(game);
        Random random = new Random();
        if (random.nextDouble() < 0.5) {
            game.getPlayer().getInventory().addItem(new GoblinBossDarkAxe());

            System.out.println(WHITE_BOLD + "╔═════════════ МИФИЧЕСКАЯ ДОБЫЧА ══════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + PURPLE_BOLD + "    🪓 " + getName() + " роняет Тёмный Топор Гоблина!    " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "║" + RED_BOLD + "        ⚜️ МИФИЧЕСКИЙ ПРЕДМЕТ! ⚜️         " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚══════════════════════════════════════════════╝" + RESET);
        }
    }
    private static final String RESET = "\u001B[0m";
    private static final String WHITE_BOLD = "\u001B[1;37m";
    private static final String PURPLE_BOLD = "\u001B[1;35m";
    private static final String RED_BOLD = "\u001B[1;31m";
}
