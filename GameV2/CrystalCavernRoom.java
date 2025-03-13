package GameV2;

import java.util.Random;
import java.util.Scanner;

public class CrystalCavernRoom extends Room {
    private Random random;

    public CrystalCavernRoom() {
        super();
        this.random = new Random();
    }

    private static final String GEMS = """
            \033[0;36m
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⠶⣿⡿⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⣄⡀⠀⠀⠀⠀⢀⣠⣴⠾⠋⠁⣰⡟⠀⠘⣷⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⣸⡏⠻⢶⣄⡀⠰⣿⣯⣀⠀⠀⣴⠟⠀⠀⠀⠘⣷⡀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⢠⣿⡶⠿⢻⡿⠀⠀⠘⣷⡉⠻⠾⣯⣤⣀⣀⣀⠀⠈⢿⡄⠠⡶⢿⠀⠀⠀⠀
⠀⠀⠙⠻⣦⣾⠃⠀⠀⠀⠘⣷⡀⠀⠀⢨⡿⠋⠛⣛⣿⡿⠟⠀⠀⢸⡄⠀⠀⠀
⠀⢠⣀⡀⠀⠉⠀⠀⠀⠀⠀⠘⢿⡄⣰⣟⣡⣴⠟⠋⠁⠀⣾⡶⠟⠛⠁⠀⠀⠀
⠀⢸⣿⠛⠻⠶⣦⣤⣄⣀⠀⠀⠈⢿⡿⠟⠉⠀⢀⣀⣤⣄⡀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⣿⠀⠀⠀⠀⠈⠉⣻⣿⠀⠀⠀⢀⣴⣾⠛⠛⠉⠻⣯⠛⠻⢶⣤⣄⣀⠀⠀
⠀⠀⢿⡄⠀⠀⠀⠀⣼⠏⢹⡆⠀⣠⡞⠉⣿⠀⠀⠀⠀⣙⣧⣤⣤⠴⣿⣿⠃⠀
⠀⠀⢸⡇⣀⣤⣴⠾⠋⠀⠈⢁⡼⢋⣀⣠⣿⣴⠶⠟⠛⠋⣿⠁⠀⢠⡾⠁⠀⠀
⠀⠀⠘⠿⣯⣭⣀⣀⠀⠀⠠⢿⣿⣛⠋⠉⠙⢷⣄⠀⠀⢠⣿⠀⣠⡿⠁⠀⠀⠀
⠀⠀⠀⠀⠀⠈⠉⠛⠛⠷⢶⣤⠈⠙⠿⣦⣄⡀⠹⣷⡀⢸⡇⣴⠏⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⠳⢾⣿⣿⡿⠃⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⠁⠀⠀⠀⠀⠀⠀⠀
            \033[0m
            """;

    @Override
    public void playerTurn(Game game, Room room) {
        Player player = game.getPlayer();
        Scanner scanner = new Scanner(System.in);
        System.out.println(GEMS);

        describeCavern();

        while (true) {
            System.out.println("\nЧто вы хотите сделать?");
            System.out.println("1. Попытаться добыть кристалл");
            System.out.println("2. Исследовать пещеру");
            System.out.println("3. Покинуть Кристальную Пещеру");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    mineCrystal(player);
                    System.out.println("Вы покидаете Кристальную Пещеру после попытки добычи кристалла.");
                    handleEmptyRoom(game, this);
                    return;
                case 2:
                    exploreCavern(player);
                    handleEmptyRoom(game, this);
                    return;
                case 3:
                    System.out.println("Вы покидаете Кристальную Пещеру.");
                    handleEmptyRoom(game, this);
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void describeCavern() {
        System.out.println("Вы входите в Кристальную Пещеру. Стены сверкают от света, отражающегося от кристаллов.");
    }

    private void mineCrystal(Player player) {
        if (random.nextDouble() < 0.25) { // 25% chance to mine a crystal
            UniqueItem crystal = getRandomCrystal();
            player.getInventory().addItem(crystal);
            System.out.println("Вам удалось добыть " + crystal.getName() + "!");
        } else {
            System.out.println("К сожалению, вам не удалось добыть кристалл в этот раз.");
        }
    }

    private UniqueItem getRandomCrystal() {
        int crystalType = random.nextInt(4);
        switch (crystalType) {
            case 0:
                return new Ruby();
            case 1:
                return new Diamond();
            case 2:
                return new Emerald();
            default:
                return new Sapphire();
        }
    }

    private void exploreCavern(Player player) {
        System.out.println("Вы исследуете пещеру и встречаете магическое существо.");

        int creatureType = random.nextInt(3);
        switch (creatureType) {
            case 0:
                int healthBoost = random.nextInt(60) + 40;
                player.heal(healthBoost);
                System.out.println("Существо излечивает вас. Ваше здоровье увеличивается на " + healthBoost + ".");
                break;
            case 1:
                int expGain = random.nextInt(250) + 100;
                player.gainExperience(expGain);
                System.out.println("Существо делится с вами мудростью. Вы получаете " + expGain + " опыта.");
                break;
            case 2:
                int manaGain = random.nextInt(55) + 25;
                player.setMana(player.getMana() + manaGain);
                System.out.println("Существо наполняет вас магической энергией. Вы получаете " + manaGain + " маны.");
                break;
        }
    }
}