package GameV2;

import java.util.Random;

public class RoyalArena extends Room {
    private MagesFactory magesFactory;
    private BanditFactory banditFactory;
    private AssassinFactory assassinFactory;
    private RoyaltyFactory royaltyFactory;

    public RoyalArena(Player player) {
        super();
        magesFactory = new MagesFactory();
        banditFactory = new BanditFactory();
        assassinFactory = new AssassinFactory();
        royaltyFactory = new RoyaltyFactory();
        generateEnemies(player);
    }

    private static final String ROYAL_ARENA_ART = """
            \033[0;33m
                                     .                                              \s
                                         /   ))     |\\         )               ).          \s
                                   c--. (\\  ( `.    / )  (\\   ( `.     ).     ( (          \s
                                   | |   ))  ) )   ( (   `.`.  ) )    ( (      ) )         \s
                                   | |  ( ( / _..----.._  ) | ( ( _..----.._  ( (          \s
                     ,-.           | |---) V.'-------.. `-. )-/.-' ..------ `--) \\._       \s
                     | /===========| |  (   |      ) ( ``-.`\\/'.-''           (   ) ``-._  \s
                     | | / / / / / | |--------------------->  <-------------------------_>=-
                     | \\===========| |                 ..-'./\\.`-..                _,,-'   \s
                     `-'           | |-------._------''_.-'----`-._``------_.-----'        \s
                                   | |         ``----''            ``----''                 \s
                                   | |                                                      \s
                                   c--`                                                  \s
            
            \033[0m
    """;


    private void generateEnemies(Player player) {
        Random random = new Random();
        int numEnemies = random.nextInt(5) + 2; // Генерация от 2 до 6 врагов
        for (int i = 0; i < numEnemies; i++) {
            int enemyType = random.nextInt(4); // Случайный выбор типа врага
            switch (enemyType) {
                case 0:
                    addEnemy(magesFactory.createMage(player)); // Добавление мага
                    break;
                case 1:
                    addEnemy(banditFactory.createBandit(player)); // Добавление бандита
                    break;
                case 2:
                    addEnemy(assassinFactory.createAssassin(player)); // Добавление ассасина
                    break;
                case 3:
                default:
                    addEnemy(royaltyFactory.createRoyalty(player)); // Добавление представителя королевской семьи
                    break;
            }
        }
    }

    @Override
    public void playerTurn(Game game, Room room) {
        if (hasEnemies()) {
            System.out.println(ROYAL_ARENA_ART);
            System.out.println("Вы вошли в Королевскую арену! Здесь вас ждут самые изощренные противники!");
            handleCombat(game, this);
        } else {
            handleEmptyRoom(game, room);
        }
    }
}