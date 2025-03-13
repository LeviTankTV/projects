package GameV2;

class MagicBanana extends MagicalFruit {
    private static final int HEAL_AMOUNT = 100;
    private static final int MANA_AMOUNT = 50;

    public MagicBanana() {
        super("Magic Banana", "Restores health and mana.", 150);
    }

    @Override
    public void applyEffect(Player player) {
        player.heal(HEAL_AMOUNT);
        player.setMana(player.getMana() + MANA_AMOUNT);
        System.out.println(player.getName() + " restores " + HEAL_AMOUNT + " health and " + MANA_AMOUNT + " mana!");
    }
}
