package GameV2;

public class ManaLevelIncreaseBook extends UniqueItem {
    // Color constants
    private static final String RESET = "\u001B[0m";
    private static final String BLUE = "\u001B[38;5;39m";
    private static final String CYAN_BOLD = "\u001B[1;36m";
    private static final String PURPLE_BOLD = "\u001B[1;35m";
    private static final String YELLOW_BOLD = "\u001B[1;33m";
    private static final String BLUE_ITALIC = "\u001B[3;34m";
    private static final String GREEN_BOLD = "\u001B[1;32m";

    public ManaLevelIncreaseBook() {
        super("Mana Level Increase Book", "An arcane tome pulsing with ethereal energy.", 750);
    }

    @Override
    public void applyEffect(Entity target) {
        if (target instanceof Player) {
            Player player = (Player) target;
            player.increaseManaRegenLevel();

            System.out.println("\n" + BLUE + "🌠✨ Arcane Awakening! ✨🌠" + RESET);
            System.out.println(CYAN_BOLD + "The Mana Level Increase Book surges with mystical power!" + RESET);
            System.out.println(PURPLE_BOLD + player.getName() + " immerses themselves in the book's arcane knowledge." + RESET);
            System.out.println(YELLOW_BOLD + "Waves of ethereal energy course through their very being." + RESET);
            System.out.println(BLUE_ITALIC + "🎊 Extraordinary! " + player.getName() + "'s capacity for mana has expanded! 🎊" + RESET);
            System.out.println(GREEN_BOLD + "Magical energy now flows more freely, replenishing at an accelerated rate." + RESET);
            System.out.println(PURPLE_BOLD + "🔮 " + player.getName() + " has ascended to new heights of magical prowess! 🔮" + RESET);
        }
    }
}
