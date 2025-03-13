package GameV2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MagesRoom extends Room {
    private MagesFactory magesFactory; // Фабрика для создания магов
    private List<Mage> mages; // Список созданных магов

    public MagesRoom(Player player) {
        super();
        magesFactory = new MagesFactory();
        generateMages(player); // Генерация магов при создании комнаты
    }

    private static final String MAGESROOM = """
            \033[0;33m
                              .
            
                               .
                     /^\\     .
                /\\   "V"
               /__\\   I      O  o
              //..\\\\  I     .
              \\].`[/  I
              /l\\/j\\  (]    .  O
             /. ~~ ,\\/I          .
             \\\\L__j^\\/I       o
              \\/--v}  I     o   .
              |    |  I   _________
              |    |  I c(`       ')o
              |    l  I   \\.     ,/
            _/j  L l\\_!  _//^---^\\\\_    -Row
            """;

    // Генерация случайного количества магов (от 2 до 4)
    private void generateMages(Player player) {
        Random random = new Random();
        int numMages = random.nextInt(3) + 2; // Случайное число от 2 до 4
        for (int i = 0; i < numMages; i++) {
            addEnemy(magesFactory.createMage(player));
        }
    }

    // Переопределение метода playerTurn для взаимодействия с магами
    public void playerTurn(Game game, Room room) {
        if (hasEnemies()) {
            System.out.println(MAGESROOM);
            System.out.println("Вы столкнулись с магами!");
            handleCombat(game, this); // Например, вызвать метод боя
        } else {
            handleEmptyRoom(game, room);
        }
    }
}