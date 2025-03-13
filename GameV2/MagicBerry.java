package GameV2;

class MagicBerry extends MagicalFruit {
    private static final int HEAL_AMOUNT = 30;
    private static final int MANA_AMOUNT = 30;

    public MagicBerry() {
        super("Magic Berry", "Restores some health and some mana.", 150);
    }

    @Override
    public void applyEffect(Player player) {
        player.heal(HEAL_AMOUNT);
        player.setMana(player.getMana() + MANA_AMOUNT);
        System.out.println(player.getName() + " restores " + HEAL_AMOUNT + " health and " + MANA_AMOUNT + " mana!");
    }
}