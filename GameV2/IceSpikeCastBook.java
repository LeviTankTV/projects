package GameV2;

public class IceSpikeCastBook extends UniqueItem {
    // Color constants
    private static final String RESET = "\u001B[0m";
    private static final String ICE_BLUE = "\u001B[38;5;51m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE_BOLD = "\u001B[1;37m";
    private static final String BLUE_BOLD = "\u001B[1;34m";
    private static final String CYAN_BOLD = "\u001B[1;36m";
    private static final String BLUE_ITALIC = "\u001B[3;34m";

    public IceSpikeCastBook() {
        super("Ice Spike Cast Book", "A frosty tome radiating an aura of biting cold.", 1250);
    }

    @Override
    public void applyEffect(Entity target) {
        if (target instanceof Player) {
            Player player = (Player) target;
            player.setLearnedIceSpike(true);

            System.out.println("\n" + ICE_BLUE + "❄️🌟 Frost Magic Unleashed! 🌟❄️" + RESET);
            System.out.println(CYAN + "The Ice Spike Cast Book shimmers with a glacial gleam!" + RESET);
            System.out.println(WHITE_BOLD + "A chill breeze swirls around " + player.getName() + " as they absorb the frigid knowledge." + RESET);
            System.out.println(BLUE_BOLD + "Crystalline patterns of ice magic form in the air." + RESET);
            System.out.println(CYAN_BOLD + "🏔️ Astounding! " + player.getName() + " has harnessed the power of winter itself! 🏔️" + RESET);
            System.out.println(BLUE_ITALIC + "Icy energies now course through their veins, ready to freeze foes in their tracks." + RESET);
            System.out.println(CYAN_BOLD + "⚔️ " + player.getName() + " has mastered the devastating Ice Spike spell! ⚔️" + RESET);
        }
    }
}