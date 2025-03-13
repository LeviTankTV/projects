package GameV2;

import java.util.Random;
import java.util.Iterator;

public class DarkSilverChestRoom extends Room {
    private boolean visited;

    public DarkSilverChestRoom() {
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
            /______/______/______/______/______/______/______/______/______/______/_______/
            *******************************************************************************
            \033[0m
            """;

    @Override
    public void playerTurn(Game game, Room room) {
        if (!visited) {
            System.out.println(CHEST);
            if (hasTorch(game.getPlayer())) {
                System.out.println("Благодаря факелу, вы видите в темноте серебряный сундук!");
                System.out.println("Хотите открыть сундук? (да/нет)");
                String answer = game.getScanner().nextLine().toLowerCase();
                if (answer.equals("да")) {
                    openSilverChest(game);
                    removeTorch(game.getPlayer());
                } else {
                    System.out.println("Вы решили не открывать сундук.");
                }
            } else {
                System.out.println("В комнате слишком темно. Вам нужен факел, чтобы что-то разглядеть.");
            }
            visited = true;
        } else {
            System.out.println("Вы вернулись в темную комнату, где раньше был серебряный сундук. Теперь она пуста.");
        }
        handleEmptyRoom(game, room);
    }

    private boolean hasTorch(Player player) {
        return player.getInventory().getItems().stream().anyMatch(item -> item instanceof Torch);
    }

    private void removeTorch(Player player) {
        Iterator<Item> iterator = player.getInventory().getItems().iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item instanceof Torch) {
                iterator.remove();
                System.out.println("Факел погас и рассыпался в прах.");
                break;
            }
        }
    }

    private void openSilverChest(Game game) {
        System.out.println(SCHEST);
        Random random = new Random();
        Player player = game.getPlayer();
        double luckModifier = 1.0 + player.getLuck(); // Базовый шанс + модификатор удачи
        int chance = random.nextInt(100); // 0-99

        // Модифицируем шансы с учетом удачи
        if (chance < (70 * luckModifier)) { // Базовый шанс 70% увеличивается с удачей
            // Увеличиваем количество золота с учетом удачи
            int baseGold = random.nextInt(201) + 200; // 200-400 базового золота
            int bonusGold = (int)(baseGold * player.getLuck()); // Дополнительное золото от удачи
            int totalGold = baseGold + bonusGold;

            player.setGold(player.getGold() + totalGold);
            System.out.println("В сундуке вы находите " + totalGold + " золота!" +
                    (bonusGold > 0 ? " (+" + bonusGold + " от удачи)" : ""));
        } else {
            // Для амулетов тоже можно добавить бонус от удачи
            int amuletChance = random.nextInt(3); // 0-2

            // Шанс получить более редкий амулет увеличивается с удачей
            if (player.getLuck() > 0 && random.nextDouble() < player.getLuck()) {
                amuletChance = 2; // Гарантированно получаем лучший амулет
            }

            Item amulet;
            switch (amuletChance) {
                case 0:
                    amulet = new AmuletOfLuck();
                    break;
                case 1:
                    amulet = new AmuletOfEvasion();
                    break;
                default:
                    amulet = new AmuletOfIncreasedManaRegeneration();
                    break;
            }

            player.getInventory().addItem(amulet);
            System.out.println("В сундуке вы находите магический амулет: " + amulet.getName() + "!");
            if (player.getLuck() > 0) {
                System.out.println("Ваша удача помогла найти особенно ценный предмет!");
            }
        }
    }
}