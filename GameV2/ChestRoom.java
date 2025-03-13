package GameV2;

import java.util.Random;
import java.util.Scanner;

public class ChestRoom extends Room {
    private boolean visited;

    public ChestRoom() {
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

    @Override
    public void playerTurn(Game game, Room room) {
        if (!visited) {
            System.out.println("Вы вошли в комнату с сундуком!");
            System.out.println(CHEST);
            // Открываем сундук
            openChest(game);
            visited = true;
        } else {
            System.out.println("Вы вернулись в комнату, где раньше был сундук. Теперь она пуста.");
        }

        // После открытия сундука или при повторном посещении, обрабатываем пустую комнату
        handleEmptyRoom(game, room);
    }
}