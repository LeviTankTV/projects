package GameV2;

class MinorHealingPotion extends Consumable {
    private static final int HEAL_AMOUNT = 50;

    public MinorHealingPotion() {
        super("Minor Healing Potion", "Restores a small amount of health.", 150);
    }

    @Override
    public void applyEffect(Player player) {
        player.heal(HEAL_AMOUNT);
        System.out.println(player.getName() + " restores " + HEAL_AMOUNT + " health!");
    }
}