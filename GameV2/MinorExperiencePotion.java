package GameV2;

class MinorExperiencePotion extends Consumable {
    private static final int EXPERIENCE_AMOUNT = 125;

    public MinorExperiencePotion() {
        super("Minor Experience Potion", "Provides a small amount of experience.", 150);
    }

    @Override
    public void applyEffect(Player player) {
        player.gainExperience(EXPERIENCE_AMOUNT);
        System.out.println(player.getName() + " gains " + EXPERIENCE_AMOUNT + " experience!");
    }
}