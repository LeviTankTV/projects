package GameV2;

class GrandExperiencePotion extends Consumable {
    private static final int EXPERIENCE_AMOUNT = 1250;

    public GrandExperiencePotion() {
        super("Grand Experience Potion", "Provides a large amount of experience.", 700);
    }

    @Override
    public void applyEffect(Player player) {
        player.gainExperience(EXPERIENCE_AMOUNT);
        System.out.println(player.getName() + " gains " + EXPERIENCE_AMOUNT + " experience!");
    }
}