package GameV2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DungeonArena extends Room {
    private GolemFactory golemFactory; // Фабрика для создания големов
    private GoblinFactory goblinFactory; // Фабрика для создания гоблинов
    private SkeletonFactory skeletonFactory; // Фабрика для создания скелетов
    private ZombieFactory zombieFactory; // Фабрика для создания зомби

    public DungeonArena(Player player) {
        super();
        golemFactory = new GolemFactory(); // Инициализация фабрики големов
        goblinFactory = new GoblinFactory(); // Инициализация фабрики гоблинов
        skeletonFactory = new SkeletonFactory(); // Инициализация фабрики скелетов
        zombieFactory = new ZombieFactory(); // Инициализация фабрики зомби
        generateEnemies(player); // Генерация врагов при создании арены
    }

    // Генерация врагов на основе уровня игрока
    private void generateEnemies(Player player) {
        Random random = new Random();
        int numEnemies = random.nextInt(3) + 3; // Генерация от 3 до 5 врагов
        for (int i = 0; i < numEnemies; i++) {
            int enemyType = random.nextInt(4); // Случайный выбор типа врага
            switch (enemyType) {
                case 0:
                    addEnemy(golemFactory.createGolem(player)); // Добавление голема
                    break;
                case 1:
                    addEnemy(goblinFactory.createGoblin(player)); // Добавление гоблина
                    break;
                case 2:
                    addEnemy(skeletonFactory.createSkeleton(player)); // Добавление скелета
                    break;
                case 3:
                default:
                    addEnemy(zombieFactory.createZombie(player)); // Добавление зомби
                    break;
            }
        }
    }

    private static final String Dun = """
            \033[0;36m
                                       .-.
                                      {{#}}
                      {}               8@8
                    .::::.             888
                @\\\\/W\\/\\/W\\//@         8@8
                 \\\\/^\\/\\/^\\//     _    )8(    _
                  \\_O_{}_O_/     (@)__/8@8\\__(@)
             ____________________ `~"-=):(=-"~`
            |<><><>  |  |  <><><>|     |.|
            |<>      |  |      <>|     |S|
            |<>      |  |      <>|     |'|
            |<>   .--------.   <>|     |.|
            |     |   ()   |     |     |P|
            |_____| (O\\/O) |_____|     |'|
            |     \\   /\\   /     |     |.|
            |------\\  \\/  /------|     |U|
            |       '.__.'       |     |'|
            |        |  |        |     |.|
            :        |  |        :     |N|
             \\       |  |       /      |'|
              \\<>    |  |    <>/       |.|
               \\<>   |  |   <>/        |K|
                `\\<> |  | <>/'         |'|
            jgs   `-.|__|.-`           \\ /
                                        ^
            \033[0m
            """;

    // Переопределение метода playerTurn для обработки взаимодействий в арене
    @Override
    public void playerTurn(Game game, Room room) {
        if (hasEnemies()) {
            System.out.println(Dun);
            System.out.println("Вы вошли в Подземную арену! Подготовьтесь к бою с врагами!");
            handleCombat(game, this); // Вызов метода handleCombat
        } else {
            handleEmptyRoom(game, room); // Обработка сценария пустой комнаты
        }
    }
}
