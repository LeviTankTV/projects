package GameV2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KnightRoom extends Room {
    private RoyaltyFactory royaltyFactory; // Фабрика для создания персонажей
    private List<Royalty> royalties; // Список созданных персонажей

    public KnightRoom(Game game) {
        super();
        royaltyFactory = new RoyaltyFactory();
        generateRoyalty(game); // Генерация персонажей при создании комнаты
    }

    private static final String Knight = """
            \033[0;36m
                __      _
                 /__\\__  //
                //_____\\///
               _| /-_-\\)|/_
              (___\\ _ //___\\
              (  |\\\\_/// * \\\\
               \\_| \\_((*   *))
               ( |__|_\\\\  *//
               (o/  _  \\_*_/
               //\\__|__/\\
              // |  | |  |
             //  _\\ | |___)
            //  (___|
            \033[0m
            """;

    // Генерация случайного количества персонажей (от 2 до 4)
    private void generateRoyalty(Game game) {
        Player player = game.getPlayer();
        Random random = new Random();
        int numRoyalty = random.nextInt(3) + 2; // Случайное число от 2 до 4
        for (int i = 0; i < numRoyalty; i++) {
            String name = "Рыцарь " + (i + 1); // Присваиваем имена персонажам
            addEnemy(royaltyFactory.createRoyalty(player));
        }
    }

    // Переопределение метода playerTurn для взаимодействия с рыцарями
    public void playerTurn(Game game, Room room) {
        if (hasEnemies()) {
            System.out.println(Knight);
            System.out.println("Вы столкнулись с рыцарями!");
            handleCombat(game,room);
        } else {
            handleEmptyRoom(game, room);
        }
    }
}