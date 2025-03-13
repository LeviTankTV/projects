package GameV2;

import java.util.Random;
import java.util.Scanner;

public class BossRoom extends Room {
    private BossFactory bossFactory;
    private Boss boss;
    private Scanner scanner;

    public BossRoom(Player player) {
        super();
        bossFactory = new BossFactory();
        generateBoss(player);
        scanner = new Scanner(System.in);
    }

    private static final String BOSS = """
            \033[0;36m
⠀⠀⠀⠀⠀⠀⢻⣿⣿⣿⣿⣿⣷⣦⠀⠀⣠⣶⣿⣿⣿⣿⣶⣄⠀⢀⣴⣾⡿⠿⣿⣷⣦⠀⢀⣴⣿⡿⢿⣿⣷⣦⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⢸⣿⣿⣀⣀⣿⣿⠿⠀⢸⣿⣿⠋⠀⠀⢹⣿⣿⡆⠘⣿⣿⣶⣤⣌⣉⠉⠉⠸⣿⣿⣦⣤⣍⣉⠉⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⢸⣿⣿⠛⠛⢿⣿⣶⡀⢸⣿⣿⠀⠀⠀⢸⣿⣿⡇⢀⡈⣙⠛⠿⢿⣿⣷⡆⢀⡈⣙⠻⠿⣿⣿⣷⡄⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⢸⣿⣿⣤⣤⣾⣿⣿⠃⠈⢿⣿⣷⣦⣴⣿⣿⡿⠀⠸⣿⣿⣦⣤⣼⣿⣿⠁⠹⣿⣿⣦⣤⣼⣿⡿⠁⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠉⠉⠉⠉⠉⠉⠉⠀⠀⠀⠀⠉⠉⠉⡉⠉⠁⠀⠀⠀⠈⣉⠉⠉⡉⠉⠁⠀⠀⠈⡉⠉⠉⠉⠉⠀⠀⠀⠀⠀⠀⠀
            \033[0m
            """;

    private void generateBoss(Player player) {
        String[] bossTypes = {"ZombieBoss", "SkeletonBoss", "GolemBoss", "GoblinBoss", "BanditBoss"};
        Random random = new Random();
        String bossType = bossTypes[random.nextInt(bossTypes.length)];
        boss = bossFactory.createBoss(bossType, player);
        addEnemy(boss);
    }

    @Override
    public void playerTurn(Game game, Room room) {
        if (hasEnemies()) {
            System.out.println(BOSS);
            System.out.println("Вы вошли в комнату босса! Перед вами стоит " + boss.getName() + "!");
            bossInteraction(game);
        } else {
            handleEmptyRoom(game, room);
        }
    }

    private void bossInteraction(Game game) {
        boolean combatInitiated = false;

        while (!combatInitiated) {
            System.out.println("\nЧто вы хотите сделать?");
            System.out.println("1. Изучить босса");
            System.out.println("2. Начать бой");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    examineBoss();
                    break;
                case 2:
                    System.out.println("Вы решаете, что пришло время сразиться с " + boss.getName() + "!");
                    combatInitiated = true;
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте еще раз.");
            }
        }

        // После взаимодействия переходим к бою
        handleCombat(game, this);
    }

    private void examineBoss() {
        System.out.println("\nВы внимательно изучаете " + boss.getName() + "...");
        System.out.println("Уровень босса: " + boss.getLevel());
        System.out.println("Здоровье босса: " + boss.getHealth());
        System.out.println("Оружие босса: " + boss.getWeapon().getName());
    }
}