package GameV2;

import java.util.Random;
import java.util.Scanner;

public class DreamFountain extends Room {
    private Random random = new Random();
    private BiggestItemFactory itemFactory = new BiggestItemFactory();

    public DreamFountain() {
        super();
    }

    private static final String FOUNTAIN_ART = """
            \033[0;36m
                          .      .       .       .
      .   .       .          .      . .      .         .          .    .
             .       .         .    .   .         .         .            .
        .   .    .       .         . . .        .        .     .    .
     .          .   .       .       . .      .        .  .              .
          .  .    .  .       .     . .    .       . .      .   .        .
     .   .       .    . .      .    . .   .      .     .          .     .
        .            .    .     .   . .  .     .   .               .
         .               .  .    .  . . .    .  .                 .
                            . .  .  . . .  . .
                                . . . . . .
                                  . . . .
                                   I . I
                     _______________III_______________
                    /    .       .       .       .    \\
                   ( ~~~ .  ~~~  .  ~~~  .  ~~~  . ~~~ )
                     \\SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS/
                        \\ ======================= /
                            \\SSSSSSSSSSSSSSSSS/
                         ________\\       /________
                        (=+=+=+=+=+=+=+=+=+=+=+=+=)
                         ~~~~~~~~~~~~~~~~~~~~~~~~~
            \033[0m
            """;

    @Override
    public void playerTurn(Game game, Room room) {
        Player player = game.getPlayer();
        System.out.println(FOUNTAIN_ART);
        boolean continueThrowing = true;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Вы находитесь у Фонтана Желаний.");

        while (continueThrowing) {
            System.out.println("У вас сейчас " + player.getGold() + " монет.");
            System.out.println("Хотите бросить 5 монет и загадать желание?");
            System.out.print("Введите 'да' для броска монет или 'нет' чтобы отказаться: ");

            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("да")) {
                if (player.getGold() >= 5) {
                    player.setGold(player.getGold() - 5);
                    System.out.println("Вы бросили 5 монет в фонтан...");

                    // 2% шанс получить предмет
                    if (random.nextInt(100) < 2) {
                        Item randomItem = itemFactory.createRandomItem();
                        player.getInventory().addItem(randomItem);
                        System.out.println("Фонтан засветился волшебным светом!");
                        System.out.println("Вы получили: " + randomItem.getName());
                    } else {
                        System.out.println("Ничего не произошло... Может быть, повезет в следующий раз?");
                    }
                } else {
                    System.out.println("У вас недостаточно монет! Необходимо 5 монет.");
                    System.out.println("Придется прийти в другой раз...");
                    continueThrowing = false;
                }
            } else {
                System.out.println("Вы отошли от фонтана.");
                continueThrowing = false;
            }
        }

        // После того как игрок закончил бросать монеты
        handleEmptyRoom(game, this);
    }
}