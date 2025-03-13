package GameV2;

public class FireballCastBook extends UniqueItem {
    // Color constants
    private static final String RESET = "\u001B[0m";
    private static final String BRIGHT_RED = "\u001B[38;5;196m";
    private static final String ORANGE = "\u001B[38;5;208m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED_BOLD = "\u001B[1;31m";
    private static final String YELLOW_BOLD = "\u001B[1;33m";
    private static final String RED_ITALIC = "\u001B[3;31m";

    public FireballCastBook() {
        super("Fireball Cast Book", "A scorching tome that radiates intense heat.", 450);
    }

    @Override
    public void applyEffect(Entity target) {
        if (target instanceof Player) {
            Player player = (Player) target;
            player.setLearnedFireball(true);

            System.out.println("\n" + BRIGHT_RED + "🔥✨ Flames of Power Ignite! ✨🔥" + RESET);
            System.out.println(ORANGE + "The Fireball Cast Book burns with primal energy!" + RESET);
            System.out.println(YELLOW + "Flames dance around " + player.getName() + " as they absorb the burning knowledge." + RESET);
            System.out.println(RED_BOLD + "The air crackles with intense heat and magical power." + RESET);
            System.out.println(YELLOW_BOLD + "🌋 Magnificent! " + player.getName() + " has gained mastery over fire! 🌋" + RESET);
            System.out.println(RED_ITALIC + "Their hands now pulse with the raw power of flame." + RESET);
            System.out.println(ORANGE + "⚔️ " + player.getName() + " has mastered the devastating Fireball spell! ⚔️" + RESET);
        }
    }
}