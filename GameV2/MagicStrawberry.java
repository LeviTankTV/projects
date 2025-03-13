package GameV2;

class MagicStrawberry extends MagicalFruit {
    private static final int MANA_AMOUNT = 70;

    public MagicStrawberry() {
        super("Magic Strawberry", "Restores a large amount of mana.", 150);
    }

    @Override
    public void applyEffect(Player player) {
        player.setMana(player.getMana() + MANA_AMOUNT);
        System.out.println(player.getName() + " restores " + MANA_AMOUNT + " mana!");
    }
}