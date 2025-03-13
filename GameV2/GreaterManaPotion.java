package GameV2;

class GreaterManaPotion extends Consumable {
    private static final int MANA_AMOUNT = 60;

    public GreaterManaPotion() {
        super("Greater Mana Potion", "Restores a significant amount of mana.", 270);
    }

    @Override
    public void applyEffect(Player player) {
        player.setMana(player.getMana() + MANA_AMOUNT);
        System.out.println(player.getName() + " restores " + MANA_AMOUNT + " mana!");
    }
}