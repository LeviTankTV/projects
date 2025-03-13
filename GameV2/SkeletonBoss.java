package GameV2;

import java.util.List;
import java.util.Random;

public class SkeletonBoss extends Boss {
    private boolean isSummoning; // Indicates if the SkeletonBoss is currently summoning
    private int turnsRemaining; // Tracks the number of turns remaining for the summoning timer

    public SkeletonBoss(String name, int level) {
        super(name, level, 2250, 75, 45, 0);
        this.isSummoning = false;
        this.turnsRemaining = 0;
    }

    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String RESET = "\u001B[0m";


    @Override
    public void performAction(Game game, Room room) {
        Random random = new Random();
        List<Entity> targets = getTargets(game, room);
        List<Entity> skeletons = room.getEnemies(); // Assuming Room class has a method to get skeletons

        // Check if currently summoning
        if (isSummoning) {
            handleSummoning(game, room);
        } else {
            // Randomly choose an action
            int actionChoice = random.nextInt(5); // Now we have 5 actions

            switch (actionChoice) {
                case 0: // Arrow Rain
                    arrowRain(game, room);
                    break;
                case 1: // Heal all skeletons
                    healSkeletons(skeletons);
                    break;
                case 2: // Summon one skeleton
                    summonSkeleton(game, room);
                    break;
                case 3: // Start summoning timer
                    startSummoningTimer();
                    break;
                case 4: // Regeneration effect
                    applyRegenerationEffect(room);
                    break;
            }
        }
    }

    private void arrowRain(Game game, Room room) {
        List<Entity> targets = getTargets(game, room);
        System.out.println(CYAN + "🏹 " + getName() + " вызывает мощный шторм стрел!" + RESET);
        for (Entity target : targets) {
            if (target.isAlive()) {
                attack(target, getWeapon());
            }
        }
    }

    private void healSkeletons(List<Entity> skeletons) {
        System.out.println(GREEN + "✨ " + getName() + " начинает древний ритуал исцеления!" + RESET);
        for (Entity skeleton : skeletons) {
            if (skeleton.isAlive()) {
                skeleton.heal(125);
                System.out.println(GREEN + "💚 Тёмная энергия восстанавливает " + skeleton.getName() + " на 125 HP!" + RESET);
            }
        }
    }

    private void summonSkeleton(Game game, Room room) {
        System.out.println(PURPLE + "🌀 " + getName() + " взывает к силам тьмы, чтобы призвать нового скелета!" + RESET);
        SecondSkeletonFactory secondSkeletonFactory = new SecondSkeletonFactory();
        Skeleton newSkeleton = secondSkeletonFactory.createSkeleton(game.getPlayer());
        room.addEnemy(newSkeleton);
        System.out.println(YELLOW + "💀 " + newSkeleton.getName() + " восстаёт из праха и присоединяется к битве!" + RESET);
    }

    private void startSummoningTimer() {
        System.out.println(BLUE + "🕰️ " + getName() + " начинает сложный ритуал призыва... Потребуется 2 хода!" + RESET);
        isSummoning = true;
        turnsRemaining = 2;
    }

    private void handleSummoning(Game game, Room room) {
        turnsRemaining--;
        System.out.println(BLUE + "⏳ " + getName() + " продолжает ритуал призыва! Осталось ходов: " + turnsRemaining + RESET);

        if (turnsRemaining <= 0) {
            System.out.println(PURPLE + "🌟 Ритуал завершен! " + getName() + " призывает армию тьмы!" + RESET);
            for (int i = 0; i < 3; i++) {
                summonSkeleton(game, room);
            }
            isSummoning = false;
            System.out.println(YELLOW + "👑 " + getName() + " успешно призвал 3 могучих скелета!" + RESET);
        }
    }

    private void applyRegenerationEffect(Room room) {
        List<Entity> enemies = room.getEnemies(); // Assuming Room has a method to get all enemies
        for (Entity enemy : enemies) {
            enemy.addEffect(new RegenerationEffect(2, "regen", 50));
        }
    }

    @Override
    public void dropLoot(Game game) {
        super.dropLoot(game);
        Random random = new Random();
        if (random.nextDouble() < 0.5) {
            game.getPlayer().getInventory().addItem(new SkeletonBossUltraBow());

            System.out.println(WHITE_BOLD + "╔═════════════ МИФИЧЕСКАЯ ДОБЫЧА ══════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + PURPLE_BOLD + "    🏹 " + getName() + " роняет Ультра-лук Скелета!    " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "║" + RED_BOLD + "        ⚜️ МИФИЧЕСКИЙ ПРЕДМЕТ! ⚜️         " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚══════════════════════════════════════════════╝" + RESET);
        }
    }
    private static final String WHITE_BOLD = "\u001B[1;37m";
    private static final String PURPLE_BOLD = "\u001B[1;35m";
    private static final String RED_BOLD = "\u001B[1;31m";
}