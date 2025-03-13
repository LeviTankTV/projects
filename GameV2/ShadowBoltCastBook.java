package GameV2;

public class ShadowBoltCastBook extends UniqueItem {
    // Color constants
    private static final String RESET = "\u001B[0m";
    private static final String PURPLE = "\u001B[38;5;129m";
    private static final String DARK_PURPLE = "\u001B[38;5;92m";
    private static final String BLACK_BOLD = "\u001B[1;30m";
    private static final String PURPLE_BOLD = "\u001B[1;35m";
    private static final String RED_BOLD = "\u001B[1;31m";
    private static final String PURPLE_ITALIC = "\u001B[3;35m";

    public ShadowBoltCastBook() {
        super("Shadow Bolt Cast Book", "A forbidden tome emanating dark, mysterious energies.", 1250);
    }

    @Override
    public void applyEffect(Entity target) {
        if (target instanceof Player) {
            Player player = (Player) target;
            player.setLearnedShadowBolt(true);

            System.out.println("\n" + PURPLE + "🌑⚡ Dark Power Awakens! ⚡🌑" + RESET);
            System.out.println(DARK_PURPLE + "The Shadow Bolt Cast Book trembles with forbidden knowledge!" + RESET);
            System.out.println(BLACK_BOLD + "Tendrils of darkness swirl around " + player.getName() + " as they read the ancient text." + RESET);
            System.out.println(PURPLE_BOLD + "Shadowy whispers reveal the secrets of dark magic." + RESET);
            System.out.println(RED_BOLD + "💀 Behold! " + player.getName() + " has mastered the art of shadow manipulation! 💀" + RESET);
            System.out.println(PURPLE_ITALIC + "Dark energies now bend to their will, ready to strike down enemies." + RESET);
            System.out.println(PURPLE_BOLD + "⚔️ " + player.getName() + " has embraced the power of the Shadow Bolt! ⚔️" + RESET);
        }
    }
}