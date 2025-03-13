package GameV2;

class GreaterHealingPotion extends Consumable {
    private static final int HEAL_AMOUNT = 100;

    public GreaterHealingPotion() {
        super("Greater Healing Potion", "Restores a significant amount of health.", 270);
    }

    @Override
    public void applyEffect(Player player) {
        player.heal(HEAL_AMOUNT);
        System.out.println(player.getName() + " restores " + HEAL_AMOUNT + " health!");
    }
}