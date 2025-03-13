package GameV2;

class LightningScroll extends AllHitWeapons {
    private static final int DAMAGE = 150; // Урон, наносимый свитком молнии

    public LightningScroll() {
        super("Lightning Scroll", "A scroll that strikes all enemies with lightning.", 340);
    }

    @Override
    protected int getDamage() {
        return DAMAGE; // Урон для Lightning Scroll
    }
}