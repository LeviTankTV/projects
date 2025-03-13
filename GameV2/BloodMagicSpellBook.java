package GameV2;

public class BloodMagicSpellBook extends UniqueItem {
    // Color constants
    private static final String RESET = "\u001B[0m";
    private static final String DARK_RED = "\u001B[38;5;88m";
    private static final String BLOOD_RED = "\u001B[38;5;124m";
    private static final String CRIMSON = "\u001B[38;5;160m";
    private static final String RED_BOLD = "\u001B[1;31m";
    private static final String PURPLE_BOLD = "\u001B[1;35m";
    private static final String RED_ITALIC = "\u001B[3;31m";

    public BloodMagicSpellBook() {
        super("Blood Magic Spell Book", "A forbidden tome dripping with dark essence.", 1250);
    }

    @Override
    public void applyEffect(Entity target) {
        if (target instanceof Player) {
            Player player = (Player) target;
            player.setLearnedBloodMagic(true);

            System.out.println("\n" + DARK_RED + "🩸💀 Dark Powers Awaken! 💀🩸" + RESET);
            System.out.println(BLOOD_RED + "The Blood Magic Spell Book pulses with forbidden knowledge!" + RESET);
            System.out.println(CRIMSON + "Crimson mist swirls around " + player.getName() + " as they embrace the dark arts." + RESET);
            System.out.println(RED_BOLD + "Ancient blood rituals surge through their veins." + RESET);
            System.out.println(PURPLE_BOLD + "⚔️ Sinister! " + player.getName() + " has mastered the arts of blood! ⚔️" + RESET);
            System.out.println(RED_ITALIC + "Life force itself becomes their weapon." + RESET);
            System.out.println(BLOOD_RED + "🌑 " + player.getName() + " has unlocked the forbidden Blood Magic spell! 🌑" + RESET);
            System.out.println(DARK_RED + "The price is steep, but the power... limitless." + RESET);
        }
    }
}