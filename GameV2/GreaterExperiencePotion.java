package GameV2;

public class GreaterExperiencePotion extends Consumable {
    private static final int EXPERIENCE_AMOUNT = 500;

    public GreaterExperiencePotion() {
        super("Greater Experience Potion", "Provides a significant amount of experience.", 300);
    }

    @Override
    public void applyEffect(Player player) {
        player.gainExperience(EXPERIENCE_AMOUNT);
        System.out.println(player.getName() + " gains " + EXPERIENCE_AMOUNT + " experience!");
    }
}
