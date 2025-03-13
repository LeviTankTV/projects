package GameV2;

class MagicApple extends MagicalFruit {
    private static final int HEAL_AMOUNT = 150;

    public MagicApple() {
        super("Magic Apple", "Restores a large amount of health.", 150);
    }

    @Override
    public void applyEffect(Player player) {
        player.heal(HEAL_AMOUNT);
        System.out.println(player.getName() + " restores " + HEAL_AMOUNT + " health!");
    }
}