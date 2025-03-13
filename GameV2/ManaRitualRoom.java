package GameV2;

import java.util.Scanner;

public class ManaRitualRoom extends Room {
    private boolean visited;

    public ManaRitualRoom() {
        super();
        this.visited = false;
    }

    private static final String RESET = "\u001B[0m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    private static final String PURPLE = "\u001B[35m";
    private static final String BLUE_BOLD_BRIGHT = "\u001B[1;94m";
    private static final String CYAN_BOLD_BRIGHT = "\u001B[1;96m";
    private static final String PURPLE_BOLD_BRIGHT = "\u001B[1;95m";

    private static final String MANA_ROOM_ART = """
            \u001B[34m
               /\\
              /  \\
             /    \\
            /______\\
            \\      /
             \\    /
              \\  /
               \\/
            \u001B[0m
    """;

    @Override
    public void playerTurn(Game game, Room room) {
        Player player = game.getPlayer();

        if (!visited) {
            System.out.println("\n" + BLUE_BOLD_BRIGHT + "╔══════════════════════════════════════════╗");
            System.out.println("║           SANCTUARY OF ARCANE MANA       ║");
            System.out.println("╚══════════════════════════════════════════╝" + RESET);

            System.out.println(CYAN_BOLD_BRIGHT + MANA_ROOM_ART + RESET);

            System.out.println(CYAN + "\nВы входите в сияющее святилище маны. Воздух здесь наполнен мерцающими частицами магической энергии.");
            System.out.println("В центре комнаты парит светящийся кристалл, пульсирующий силой древних магов." + RESET);

            System.out.println(PURPLE_BOLD_BRIGHT + "\n⚠ ВНИМАНИЕ ⚠" + RESET);
            System.out.println(PURPLE + "Кристалл предлагает вам увеличить ваш уровень маны, но взамен потребует часть вашего опыта.");
            System.out.println("Вы можете получить Книгу Увеличения Маны, но потеряете 50% текущего опыта." + RESET);

            System.out.println(CYAN_BOLD_BRIGHT + "\n➤ Ваш текущий опыт: " + player.getExperience() + RESET);
            int xpToLose = player.getExperience() / 2;
            System.out.println(PURPLE_BOLD_BRIGHT + "➤ Вы потеряете: " + xpToLose + " опыта" + RESET);
            System.out.println(BLUE_BOLD_BRIGHT + "➤ У вас останется: " + (player.getExperience() - xpToLose) + " опыта" + RESET);

            System.out.print(CYAN_BOLD_BRIGHT + "\n▶ Принять предложение кристалла? (да/нет): " + RESET);

            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("да")) {
                if (player.getExperience() >= 100) {  // Минимальный порог опыта для обмена
                    System.out.println(BLUE_BOLD_BRIGHT + "\n⚡ РИТУАЛ МАНЫ НАЧИНАЕТСЯ ⚡" + RESET);
                    System.out.print(BLUE);
                    for (int i = 0; i < 3; i++) {
                        System.out.print(".");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println(RESET);

                    player.setExperience(player.getExperience() - xpToLose);
                    player.getInventory().addItem(new ManaLevelIncreaseBook());

                    System.out.println(CYAN_BOLD_BRIGHT + "\n✧ Магическая энергия наполняет ваше тело..." + RESET);
                    System.out.println(PURPLE_BOLD_BRIGHT + "➤ Вы пожертвовали " + xpToLose + " единиц опыта" + RESET);
                    System.out.println(BLUE_BOLD_BRIGHT + "✧ Книга Увеличения Маны теперь ваша!" + RESET);
                } else {
                    System.out.println(PURPLE_BOLD_BRIGHT + "\n✘ У вас недостаточно опыта для этого ритуала!" + RESET);
                }
            } else {
                System.out.println(CYAN_BOLD_BRIGHT + "\n➤ Вы отказываетесь от предложения кристалла..." + RESET);
                System.out.println(PURPLE + "Возможно, это было мудрое решение..." + RESET);
            }

            visited = true;

            System.out.println(BLUE_BOLD_BRIGHT + "\n╔════════════════════════════════╗");
            System.out.println("║    Ритуал маны завершен...     ║");
            System.out.println("╚════════════════════════════════╝" + RESET);
        } else {
            System.out.println(CYAN + "\nСвятилище маны спокойно. Кристалл тускло мерцает, храня свои тайны..." + RESET);
        }

        handleEmptyRoom(game, this);
    }
}