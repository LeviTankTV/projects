package GameV2;

class CelestialBerry extends MagicalFruit {
    private static final int HEAL_AMOUNT = 40;
    private static final int MANA_AMOUNT = 40;

    public CelestialBerry() {
        super("Celestial Berry", "Restores some health and some mana.", 120);
    }

    @Override
    public void applyEffect(Player player) {
        player.heal(HEAL_AMOUNT);
        player.setMana(player.getMana() + MANA_AMOUNT);
        System.out.println(player.getName() + " restores " + HEAL_AMOUNT + " health and " + MANA_AMOUNT + " mana!");
    }
}
