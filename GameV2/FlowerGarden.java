package GameV2;

import java.util.Random;
import java.util.Scanner;

public class FlowerGarden extends Room {
    private Random random;

    public FlowerGarden() {
        super();
        this.random = new Random();
    }

    private static final String FLOWER = """
            \033[0;36m
                                _
                              _(_)_                          wWWWw   _
                  @@@@       (_)@(_)   vVVVv     _     @@@@  (___) _(_)_
                 @@()@@ wWWWw  (_)\\    (___)   _(_)_  @@()@@   Y  (_)@(_)
                  @@@@  (___)     `|/    Y    (_)@(_)  @@@@   \\|/   (_)\\
                   /      Y       \\|    \\|/    /(_)    \\|      |/      |
                \\ |     \\ |/       | / \\ | /  \\|/       |/    \\|      \\|/
            jgs \\\\|//   \\\\|///  \\\\\\|//\\\\\\|/// \\|///  \\\\\\|//  \\\\|//  \\\\\\|//\s
            ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
            \033[0m
            """;

    @Override
    public void playerTurn(Game game, Room room) {
        Player player = game.getPlayer();
        Scanner scanner = new Scanner(System.in);
        System.out.println(FLOWER);
        describeGarden();

        System.out.print("Хотите сорвать цветок? (да/нет): ");
        String choice = scanner.nextLine().toLowerCase().trim();

        if (choice.equals("да")) {
            UniqueItem flower = generateRandomFlower();
            if (flower != null) {
                player.getInventory().addItem(flower);
                System.out.println("Вы нашли " + flower.getName() + "!");
                System.out.println(flower.getDescription());
            }
        } else {
            System.out.println("Вы решили оставить сад нетронутым.");
        }

        handleEmptyRoom(game, this);
    }

    private UniqueItem generateRandomFlower() {
        int chance = random.nextInt(100); // 0-99

        if (chance < 25) {
            return new Rose();
        } else if (chance < 50) {
            return new Tulip();
        } else if (chance < 75) {
            return new Daisy();
        } else {
            return new Orchid();
        }
    }

    private void describeGarden() {
        System.out.println("Вы входите в прекрасный цветочный сад.");
        System.out.println("Здесь растут волшебные цветы четырех видов:");
        System.out.println("- Розы с целебными свойствами");
        System.out.println("- Тюльпаны, наполненные магической энергией");
        System.out.println("- Ромашки с защитной аурой");
        System.out.println("- Орхидеи, дарующие силу");
    }
}