package GameV2;

class AetherBerry extends MagicalFruit {
    private static final int HEAL_AMOUNT = 150;
    private static final int MANA_AMOUNT = 150;

    public AetherBerry() {
        super("Aether Berry", "Restores many health and many mana.", 300);
    }

    @Override
    public void applyEffect(Player player) {
        player.heal(HEAL_AMOUNT);
        player.setMana(player.getMana() + MANA_AMOUNT);
        System.out.println(player.getName() + " restores " + HEAL_AMOUNT + " health and " + MANA_AMOUNT + " mana!");
    }
}