package GameV2;

public class HealCastBook extends UniqueItem {
    public HealCastBook() {
        super("Heal Cast Book", "An ancient tome radiating with mystical healing energy.", 450);
    }
    private static final String RESET = "\u001B[0m";
    private static final String GOLD = "\u001B[38;5;220m";
    private static final String CYAN_BOLD = "\u001B[1;36m";
    private static final String GREEN_BOLD = "\u001B[1;32m";
    private static final String PURPLE_BOLD = "\u001B[1;35m";
    private static final String YELLOW_BOLD = "\u001B[1;33m";
    private static final String GREEN_ITALIC = "\u001B[3;32m";
    private static final String BLUE_BOLD = "\u001B[1;34m";
    @Override
    public void applyEffect(Entity target) {
        if (target instanceof Player) {
            Player player = (Player) target;
            player.setLearnedHeal(true);

            System.out.println("\n" + GOLD + "✨🌟 Magical Revelation! 🌟✨" + RESET);
            System.out.println(CYAN_BOLD + "The Heal Cast Book glows with an otherworldly light!" + RESET);
            System.out.println(GREEN_BOLD + player.getName() + " opens the mystical tome, and ancient wisdom floods their mind." + RESET);
            System.out.println(PURPLE_BOLD + "Sacred incantations and gestures of healing magic unfold before their eyes." + RESET);
            System.out.println(YELLOW_BOLD + "🎉 Congratulations! " + player.getName() + " has mastered the art of magical healing! 🎉" + RESET);
            System.out.println(GREEN_ITALIC + "The power to mend wounds and restore life now flows through their fingertips." + RESET);
            System.out.println(BLUE_BOLD + "🌈 A new chapter in " + player.getName() + "'s journey has begun! 🌈" + RESET);
        }
    }
}