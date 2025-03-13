package GameV2;

import java.util.Random;
import java.util.Scanner;

public class GoldenChestRoom extends Room {
    private boolean visited;

    public GoldenChestRoom() {
        super();
        this.visited = false;
    }


    private static final String CHEST = """
            \033[0;36m
                      ____...------------...____
                 _.-"` /o/__ ____ __ __  __ \\o\\_`"-._
               .'     / /                    \\ \\     '.
               |=====/o/======================\\o\\=====|
               |____/_/________..____..________\\_\\____|
               /   _/ \\_     <_o#\\__/#o_>     _/ \\_   \\
               \\_________\\####/_________/
                |===\\!/========================\\!/===|
                |   |=|          .---.         |=|   |
                |===|o|=========/     \\========|o|===|
                |   | |         \\() ()/        | |   |
                |===|o|======{'-.) A (.-'}=====|o|===|
                | __/ \\__     '-.\\uuu/.-'    __/ \\__ |
                |==== .'.'^'.'.====|
            jgs |  _\\o/   __  {.' __  '.} _   _\\o/  _|
                `""\""-""\"""\"""\"""\"""\"""\"""\"""\"""-""\""`
            
            
            
            \033[0m
            """;
    private static final String SCHEST = """
            \033[0;36m
            *******************************************************************************
                      |                   |                  |                     |
             _________|________________.=""_;=.______________|_____________________|_______
            |                   |  ,-"_,=""     `"=.|                  |
            |___________________|__"=._o`"-._        `"=.______________|___________________
                      |                `"=._o`"=._      _`"=._                     |
             _________|_____________________:=._o "=._."_.-="'"=.__________________|_______
            |                   |    __.--" , ; `"=._o." ,-""\"-._ ".   |
            |___________________|_._"  ,. .` ` `` ,  `"-._"-._   ". '__|___________________
                      |           |o`"=._` , "` `; .". ,  "-._"-._; ;              |
             _________|___________| ;`-.o`"=._; ." ` '`."\\` . "-._ /_______________|_______
            |                   | |o;    `"-.o`"=._``  '` " ,__.--o;   |
            |___________________|_| ;     (#) `-.o `"=.`_.--"_o.-; ;___|___________________
            ____/______/______/___|o;._    "      `".o|o_.--"    ;o;____/______/______/____
            /______/______/______/_"=._o--._        ; | ;        ; ;/______/______/______/_
            ____/______/______/______/__"=._o--._   ;o|o;     _._;o;____/______/______/____
            /______/______/______/______/____"=._o._; | ;_.--"o.--"_/______/______/______/_
            ____/______/______/______/______/_____"=.o|o_.--""___/______/______/______/____
            /______/______/______/______/______/______/______/______/______/______/________
            *******************************************************************************
            \033[0m
            """;

    @Override
    public void playerTurn(Game game, Room room) {
        if (!visited) {
            System.out.println(CHEST);
            System.out.println("Вы вошли в комнату с золотым сундуком! Он сверкает и манит своим блеском...");
            if (hasGoldenKey(game.getPlayer())) {
                System.out.println("У вас есть золотой ключ. Хотите открыть сундук? (да/нет)");
                String answer = game.getScanner().nextLine().toLowerCase();
                if (answer.equals("да")) {
                    openGoldenChest(game);
                    removeGoldenKey(game.getPlayer());
                } else {
                    System.out.println("Вы решили не открывать сундук.");
                }
            } else {
                System.out.println("Сундук заперт. Для его открытия нужен золотой ключ.");
            }
            visited = true;
        } else {
            System.out.println("Вы вернулись в комнату, где раньше был золотой сундук. Теперь она пуста.");
        }
        handleEmptyRoom(game, room);
    }


    private boolean hasGoldenKey(Player player) {
        return player.getInventory().getItems().stream().anyMatch(item -> item instanceof GoldenKey);
    }

    private void removeGoldenKey(Player player) {
        player.getInventory().getItems().removeIf(item -> item instanceof GoldenKey);
        System.out.println("Золотой ключ использован и исчез.");
    }

    private void openGoldenChest(Game game) {
        System.out.println(SCHEST);
        Random random = new Random();
        Player player = game.getPlayer();
        double luckModifier = 1.0 + player.getLuck(); // Базовый шанс + модификатор удачи
        int chance = random.nextInt(100); // 0-99

        if (chance < (70 * luckModifier)) { // Базовый шанс 70% увеличивается с удачей
            // Увеличиваем количество золота с учетом удачи
            int baseGold = random.nextInt(901) + 600; // 600-1500 базового золота
            int bonusGold = (int)(baseGold * player.getLuck()); // Дополнительное золото от удачи
            int totalGold = baseGold + bonusGold;

            player.setGold(player.getGold() + totalGold);
            System.out.println("В сундуке вы находите " + totalGold + " золота!" +
                    (bonusGold > 0 ? " (+" + bonusGold + " от удачи)" : ""));
        } else {
            // Для камней тоже добавляем бонус от удачи
            int gemChance = random.nextInt(4); // 0-3

            // Шанс получить более редкий камень увеличивается с удачей
            if (player.getLuck() > 0 && random.nextDouble() < player.getLuck()) {
                gemChance = 2;
            }

            Item gem;
            switch (gemChance) {
                case 0:
                    gem = new Ruby();
                    break;
                case 1:
                    gem = new Sapphire();
                    break;
                case 2:
                    gem = new Diamond();
                    break;
                default:
                    gem = new Emerald();
                    break;
            }

            player.getInventory().addItem(gem);
            System.out.println("В сундуке вы находите драгоценный камень: " + gem.getName() + "!");
            if (player.getLuck() > 0) {
                System.out.println("Ваша удача помогла найти особенно ценный камень!");
            }
            System.out.println("Ценность камня: " + gem.getValue() + " золота");
        }
    }
}