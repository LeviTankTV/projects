package GameV2;

class MinorManaPotion extends Consumable {
    private static final int MANA_AMOUNT = 30;

    public MinorManaPotion() {
        super("Minor Mana Potion", "Restores a small amount of mana.", 80);
    }

    @Override
    public void applyEffect(Player player) {
        player.setMana(player.getMana() + MANA_AMOUNT);
        System.out.println(player.getName() + " restores " + MANA_AMOUNT + " mana!");
    }
}