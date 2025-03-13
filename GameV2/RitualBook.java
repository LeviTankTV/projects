package GameV2;

public class RitualBook extends UniqueItem {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String PURPLE = "\u001B[35m";
    private static final String YELLOW = "\u001B[33m";

    private static final String BOOK_ART =
            "   _______________\n" +
                    "  /               \\\n" +
                    " /                 \\\n" +
                    "| ╔═══════════════╗ |\n" +
                    "| ║  RITUAL BOOK  ║ |\n" +
                    "| ╚═══════════════╝ |\n" +
                    "|   ___________     |\n" +
                    "|  /     V     \\    |\n" +
                    "| |   ┌─────┐   |   |\n" +
                    "| |   │  ♥  │   |   |\n" +
                    "| |   └─────┘   |   |\n" +
                    "|  \\_________/    | \n" +
                    " \\                 /\n" +
                    "  \\_________________/";

    public RitualBook() {
        super("Ritual Book", "A mystical tome that increases your Vampirism Level.", 750);
    }

    @Override
    public void applyEffect(Entity target) {
        if (target instanceof Player) {
            Player player = (Player) target;
            player.increaseVampirismLevel();

            System.out.println(PURPLE + BOOK_ART + RESET);
            System.out.println(RED + "╔══════════════════════════════════════════╗" + RESET);
            System.out.println(RED + "║        " + YELLOW + "Ritual Book Effect Applied" + RED + "        ║" + RESET);
            System.out.println(RED + "╠══════════════════════════════════════════╣" + RESET);
            System.out.println(RED + "║ " + PURPLE + player.getName() + " has delved into dark magic!" + RED + "      ║" + RESET);
            System.out.println(RED + "║ " + YELLOW + "Vampirism Level increased significantly." + RED + " ║" + RESET);
            System.out.println(RED + "╚══════════════════════════════════════════╝" + RESET);
        }
    }

    @Override
    public String toString() {
        return PURPLE + "📕 " + getName() + RESET + " - " + YELLOW + getDescription() + RESET +
                "\n   " + RED + "Value: " + getValue() + " gold" + RESET;
    }
}