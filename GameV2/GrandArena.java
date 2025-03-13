package GameV2;

import java.util.Random;

public class GrandArena extends Room {
    private SecondZombieFactory secondZombieFactory;
    private SecondSkeletonFactory secondSkeletonFactory;
    private GoblinFactory goblinFactory;
    private GolemFactory golemFactory;

    public GrandArena(Player player) {
        super();
        secondZombieFactory = new SecondZombieFactory();
        secondSkeletonFactory = new SecondSkeletonFactory();
        goblinFactory = new GoblinFactory();
        golemFactory = new GolemFactory();
        generateEnemies(player);
    }

    private static final String Sword = """
            \033[0;36m
            
                                            ,-.
                                           ("O_)
                                          / `-/
                                         /-. /
                                        /   )
                                       /   / \s
                          _           /-. /
                         (_)"-._     /   )
                           "-._ "-'""( )/   \s
                               "-/"-._" `.\s
                                /     "-.'._
                               /\\       /-._"-._
                _,---...__    /  ) _,-"/    "-(_)
            ___<__(|) _   ""-/  / /   /
             '  `----' ""-.   \\/ /   /
                           )  ] /   /
                   ____..-'   //   /                       )
               ,-""      __.,'/   /   ___                 /,
              /    ,--""/  / /   /,-""   ""\"-.          ,'/
             [    (    /  / /   /  ,.---,_   `._   _,-','
              \\    `-./  / /   /  /       `-._  ""\" ,-'
               `-._  /  / /   /_,'            ""--"
                   "/  / /   /"        \s
                   /  / /   /
                  /  / /   /  o!O
                 /  |,'   / \s
                :   /    /
                [  /   ,'   
                | /  ,'
                |/,-'
                P'
            
            \033[0m
            """;

    private void generateEnemies(Player player) {
        Random random = new Random();
        int numEnemies = random.nextInt(5) + 2; // Генерация от 2 до 6 врагов
        for (int i = 0; i < numEnemies; i++) {
            int enemyType = random.nextInt(4); // Случайный выбор типа врага
            switch (enemyType) {
                case 0:
                    addEnemy(secondZombieFactory.createZombie(player)); // Добавление зомби второго уровня
                    break;
                case 1:
                    addEnemy(secondSkeletonFactory.createSkeleton(player)); // Добавление скелета второго уровня
                    break;
                case 2:
                    addEnemy(goblinFactory.createGoblin(player)); // Добавление гоблина
                    break;
                case 3:
                default:
                    addEnemy(golemFactory.createGolem(player)); // Добавление голема
                    break;
            }
        }
    }

    @Override
    public void playerTurn(Game game, Room room) {
        if (hasEnemies()) {
            System.out.println(Sword);
            System.out.println("Вы вошли в Великую арену! Приготовьтесь к эпическому сражению!");
            handleCombat(game, this);
        } else {
            handleEmptyRoom(game, room);
        }
    }
}