package GameV2;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class HallOfFameRoom extends Room {
    private List<LegendaryHero> legendaryHeroes;
    private Random random;

    public HallOfFameRoom() {
        super();
        this.random = new Random();
        initializeLegendaryHeroes();
    }

    private void initializeLegendaryHeroes() {
        legendaryHeroes = Arrays.asList(
                new LegendaryHero("Алексей Храбрый", "Великий воин, известный своей отвагой в битвах.", "strength"),
                new LegendaryHero("Елена Мудрая", "Могущественная волшебница, прославившаяся своими знаниями.", "intelligence"),
                new LegendaryHero("Михаил Ловкий", "Непревзойденный лучник и мастер скрытности.", "agility"),
                new LegendaryHero("Ольга Целительница", "Легендарный клирик, спасшая тысячи жизней.", "vitality")
        );
    }

    private static final String H = """
            \033[0;36m
                                                                       |>>>
                               _                      _                |
                ____________ .' '.    _____/----/-\\ .' './========\\   / \\
               //// ////// /V_.-._\\  |.-.-.|===| _ |-----| u    u |  /___\\
              // /// // ///==\\ u |.  || | ||===||||| |T| |   ||   | .| u |_ _ _ _ _ _
             ///////-\\////====\\==|:::::::::::::::::::::::::::::::::::|u u| U U U U U
             |----/\\u |--|++++|..|'''''''''''::::::::::::::''''''''''|+++|+-+-+-+-+-+
             |u u|u | |u ||||||..|              '::::::::'           |===|>=== _ _ ==
             |===|  |u|==|++++|==|              .::::::::.           | T |....| V |..
             |u u|u | |u ||HH||         \\|/    .::::::::::.
             |===|_.|u|_.|+HH+|_              .::::::::::::.              _
                            __(_)___         .::::::::::::::.         ___(_)__
            ---------------/  / \\  /|       .:::::;;;:::;;:::.       |\\  / \\  \\-------
            ______________/_______/ |      .::::::;;:::::;;:::.      | \\_______\\________
            |       |     [===  =] /|     .:::::;;;::::::;;;:::.     |\\ [==  = ]   |
            |_______|_____[ = == ]/ |    .:::::;;;:::::::;;;::::.    | \\[ ===  ]___|____
                 |       |[  === ] /|   .:::::;;;::::::::;;;:::::.   |\\ [=  ===] |
            _____|_______|[== = =]/ |  .:::::;;;::::::::::;;;:::::.  | \\[ ==  =]_|______
             |       |    [ == = ] /| .::::::;;:::::::::::;;;::::::. |\\ [== == ]      |
            _|_______|____[=  == ]/ |.::::::;;:::::::::::::;;;::::::.| \\[  === ]______|_
               |       |  [ === =] /.::::::;;::::::::::::::;;;:::::::.\\ [===  =]   |
            ___|_______|__[ == ==]/.::::::;;;:::::::::::::::;;;:::::::.\\[=  == ]___|_____
            \033[0m
            """;

    @Override
    public void playerTurn(Game game, Room room) {
        Player player = game.getPlayer();
        Scanner scanner = new Scanner(System.in);

        describeHallOfFame();

        while (true) {
            System.out.println("\nЧто вы хотите сделать?");
            System.out.println("1. Осмотреть статую героя");
            System.out.println("2. Помедитировать перед статуей");
            System.out.println("3. Покинуть Зал Славы");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    examineStatue();
                    break;
                case 2:
                    meditateBeforeStatue(player);
                    break;
                case 3:
                    System.out.println(H);
                    System.out.println("Вы покидаете Зал Славы, чувствуя вдохновение от великих героев прошлого.");
                    handleEmptyRoom(game, this);
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void describeHallOfFame() {
        System.out.println("Вы входите в величественный Зал Славы.");
        System.out.println("Вокруг вас возвышаются статуи легендарных героев прошлого.");
        System.out.println("Атмосфера наполнена ощущением величия и вдохновения.");
    }

    private void examineStatue() {
        LegendaryHero hero = legendaryHeroes.get(random.nextInt(legendaryHeroes.size()));
        System.out.println("\nВы подходите к статуе " + hero.getName() + ".");
        System.out.println(hero.getDescription());
    }

    private void meditateBeforeStatue(Player player) {
        LegendaryHero hero = legendaryHeroes.get(random.nextInt(legendaryHeroes.size()));
        System.out.println("\nВы медитируете перед статуей " + hero.getName() + ".");

        if (random.nextDouble() < 0.5) { // 50% chance of receiving a bonus
            int bonus = random.nextInt(5) + 1; // Random bonus between 1 and 5
            switch (hero.getSpecialty()) {
                case "strength":
                    player.heal(75);
                    System.out.println("Вы чувствуете прилив силы!");
                    break;
                case "intelligence":
                    player.heal(75);
                    System.out.println("Ваш разум проясняется!");
                    break;
                case "agility":
                    player.heal(75);
                    System.out.println("Вы ощущаете небывалую легкость!");
                    break;
                case "vitality":
                    player.heal(75);
                    System.out.println("Вы чувствуете прилив жизненных сил!");
                    break;
            }
        } else {
            System.out.println("Вы чувствуете умиротворение, но ничего необычного не происходит.");
        }
    }

    private static class LegendaryHero {
        private String name;
        private String description;
        private String specialty;

        public LegendaryHero(String name, String description, String specialty) {
            this.name = name;
            this.description = description;
            this.specialty = specialty;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getSpecialty() {
            return specialty;
        }
    }
}