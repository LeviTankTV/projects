package GameV2;

public class EarthquakeCastBook extends UniqueItem {
    // Color constants
    private static final String RESET = "\u001B[0m";
    private static final String BROWN = "\u001B[38;5;130m";
    private static final String DARK_GREEN = "\u001B[38;5;22m";
    private static final String GRAY = "\u001B[38;5;244m";
    private static final String BROWN_BOLD = "\u001B[1;33m";
    private static final String GREEN_BOLD = "\u001B[1;32m";
    private static final String GRAY_ITALIC = "\u001B[3;37m";

    public EarthquakeCastBook() {
        super("Earthquake Cast Book", "An ancient tome pulsing with earthen might.", 1250);
    }

    @Override
    public void applyEffect(Entity target) {
        if (target instanceof Player) {
            Player player = (Player) target;
            player.setLearnedEarthQuake(true);

            System.out.println("\n" + BROWN + "🌋⚒️ Earth's Power Awakens! ⚒️🌋" + RESET);
            System.out.println(DARK_GREEN + "The Earthquake Cast Book resonates with primordial force!" + RESET);
            System.out.println(GRAY + "The ground trembles beneath " + player.getName() + " as they channel nature's might." + RESET);
            System.out.println(BROWN_BOLD + "Ancient stones hum with tectonic energy." + RESET);
            System.out.println(GREEN_BOLD + "🗿 Tremendous! " + player.getName() + " has become one with the earth itself! 🗿" + RESET);
            System.out.println(GRAY_ITALIC + "The very foundation of the world now answers their call." + RESET);
            System.out.println(BROWN + "⚔️ " + player.getName() + " has mastered the devastating Earthquake spell! ⚔️" + RESET);
            System.out.println(DARK_GREEN + "Let the ground shatter before their unstoppable might!" + RESET);
        }
    }
}