package GameV2;

import java.util.Scanner;

public class RitualRoom extends Room {
    private boolean visited; // Track if the player has visited the ritual room

    public RitualRoom() {
        super();
        this.visited = false; // Player has not visited yet
    }
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String RED_BOLD_BRIGHT = "\u001B[1;91m";
    private static final String GREEN_BOLD_BRIGHT = "\u001B[1;92m";
    private static final String YELLOW_BOLD_BRIGHT = "\u001B[1;93m";
    private static final String PURPLE_BOLD_BRIGHT = "\u001B[1;95m";
    private static final String CYAN_BOLD_BRIGHT = "\u001B[1;96m";
    private static final String RITUAL_ROOM_ART = """
            \033[0;33m
            (\\\s
            \\'\\\s
             \\'\\     __________ \s
             / '|   ()_________)
             \\ '/    \\ ~~~~~~~~ \\
               \\       \\ ~~~~~~   \\
               ==).      \\__________\\
              (__)       ()__________)
            \033[0m
    """;


    // Override playerTurn method to handle player interaction with the ritual room
    @Override
    public void playerTurn(Game game, Room room) {
        Player player = game.getPlayer();

        if (!visited) {
            if (player.getVampirismLevel() == 10) {
                System.out.println(RED_BOLD_BRIGHT + "╔══════════════════════════════════════════╗" + RESET);
                System.out.println("║         ТЫ УЖЕ ВАМПИР!                   ║");
                System.out.println("╚══════════════════════════════════════════╝" + RESET);
                System.out.println(RED + "Вы стоите перед алтарем, но ваше сердце больше не бьется.");
                System.out.println("Вы уже стали частью темной стороны. Ваша жажда крови неутолима..." + RESET);
                System.out.println(PURPLE + "Свет вокруг вас тускнеет, и вы понимаете, что никогда не сможете вернуться." + RESET);
                System.out.println(PURPLE_BOLD_BRIGHT + "\n╔════════════════════════════════╗");
                System.out.println("║     Ритуал окончен...          ║");
                System.out.println("╚════════════════════════════════╝" + RESET);
                visited = true; // Помечаем комнату как посещённую
                return; // Прерываем выполнение метода
            }

            // Мрачное оформление входа в комнату
            System.out.println("\n" + RED_BOLD_BRIGHT + "╔══════════════════════════════════════════╗");
            System.out.println("║           RITUAL CHAMBER OF SACRIFICE    ║");
            System.out.println("╚══════════════════════════════════════════╝" + RESET);

            System.out.println(PURPLE_BOLD_BRIGHT + RITUAL_ROOM_ART + RESET);

            // Атмосферное описание
            System.out.println(PURPLE + "\nВы входите в древнюю ритуальную комнату. Воздух здесь густой от запаха благовоний.");
            System.out.println("На стенах пляшут тени от множества свечей, а в центре комнаты находится зловещий алтарь," );
            System.out.println("покрытый таинственными символами..." + RESET);

            // Драматическое предложение
            System.out.println(RED_BOLD_BRIGHT + "\n⚠ ВНИМАНИЕ ⚠" + RESET);
            System.out.println(RED + "Древний алтарь жаждет жертвы... Он требует 99% вашей жизненной силы.");
            System.out.println("Взамен вы получите могущественный артефакт - Книгу Ритуалов." + RESET);

            // Текущее здоровье
            System.out.println(YELLOW_BOLD_BRIGHT + "\n➤ Ваше текущее здоровье: " + player.getHealth() + RESET);
            int healthToLose = (int) (player.getHealth() * 0.99);
            System.out.println(RED_BOLD_BRIGHT + "➤ Вы потеряете: " + healthToLose + " здоровья" + RESET);
            System.out.println(GREEN_BOLD_BRIGHT + "➤ У вас останется: " + (player.getHealth() - healthToLose) + " здоровья" + RESET);

            // Выбор
            System.out.print(CYAN_BOLD_BRIGHT + "\n▶ Принять жертву? (да/нет): " + RESET);

            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("да")) {
                if (player.getHealth() > 0) {
                    // Анимация жертвоприношения
                    System.out.println(RED_BOLD_BRIGHT + "\n⚡ РИТУАЛ НАЧИНАЕТСЯ ⚡" + RESET);
                    System.out.print(RED);
                    for (int i = 0; i < 3; i++) {
                        System.out.print(".");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println(RESET);

                    player.setHealth(player.getHealth() - healthToLose);
                    player.getInventory().addItem(new RitualBook());

                    System.out.println(PURPLE_BOLD_BRIGHT + "\n✧ Древняя магия пронизывает вашу душу..." + RESET);
                    System.out.println(RED_BOLD_BRIGHT + "➤ Вы пожертвовали " + healthToLose + " единиц жизненной силы" + RESET);
                    System.out.println(GREEN_BOLD_BRIGHT + "✧ Книга Ритуалов теперь ваша!" + RESET);
                } else {
                    System.out.println(RED_BOLD_BRIGHT + "\n✘ Ваша жизненная сила слишком слаба для ритуала!" + RESET);
                }
            } else {
                System.out.println(YELLOW_BOLD_BRIGHT + "\n➤ Вы отвергаете зов древнего алтаря..." + RESET);
                System.out.println(PURPLE + "Возможно, это было мудрое решение..." + RESET);
            }

            visited = true;

            System.out.println(PURPLE_BOLD_BRIGHT + "\n╔════════════════════════════════╗");
            System.out.println("║    Ритуал окончен...           ║");
            System.out.println("╚════════════════════════════════╝" + RESET);
        } else {
            System.out.println(PURPLE + "\nПустая ритуальная комната хранит отголоски прошлого ритуала..." + RESET);
        }

        handleEmptyRoom(game, this);
    }
}