package GameV2;

class AetherFruit extends MagicalFruit {
    private static final int HEAL_AMOUNT = 200;
    private static final int MANA_AMOUNT = 100;

    public AetherFruit() {
        super("Aether Fruit", "Restores many health and many mana.", 300);
    }

    @Override
    public void applyEffect(Player player) {
        player.heal(HEAL_AMOUNT);
        player.setMana(player.getMana() + MANA_AMOUNT);
        System.out.println(player.getName() + " restores " + HEAL_AMOUNT + " health and " + MANA_AMOUNT + " mana!");
    }
}
