package GameV2;

public class ArcaneMissileCastBook extends UniqueItem {
    // Color constants
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[38;5;51m";
    private static final String PURPLE = "\u001B[38;5;141m";
    private static final String BLUE = "\u001B[38;5;27m";
    private static final String MAGENTA_BOLD = "\u001B[1;35m";
    private static final String CYAN_BOLD = "\u001B[1;36m";
    private static final String BLUE_ITALIC = "\u001B[3;34m";

    public ArcaneMissileCastBook() {
        super("Arcane Missile Cast Book", "A mystical tome crackling with pure arcane energy.", 1250);
    }

    @Override
    public void applyEffect(Entity target) {
        if (target instanceof Player) {
            Player player = (Player) target;
            player.setLearnedArcaneMissile(true);

            System.out.println("\n" + CYAN + "✨🌟 Arcane Wisdom Manifests! 🌟✨" + RESET);
            System.out.println(PURPLE + "The Arcane Missile Cast Book resonates with mystical energy!" + RESET);
            System.out.println(BLUE + "Ethereal wisps dance around " + player.getName() + " as arcane knowledge flows." + RESET);
            System.out.println(MAGENTA_BOLD + "Pure magical essence courses through their mind." + RESET);
            System.out.println(CYAN_BOLD + "⚡ Magnificent! " + player.getName() + " channels the raw power of magic! ⚡" + RESET);
            System.out.println(BLUE_ITALIC + "The very fabric of reality bends to their will." + RESET);
            System.out.println(PURPLE + "🔮 " + player.getName() + " has mastered the Arcane Missile spell! 🔮" + RESET);
            System.out.println(CYAN + "Let their enemies taste the pure essence of magic!" + RESET);
        }
    }
}