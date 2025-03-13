package GameV2;

class UltraScroll extends AllHitWeapons {
    private static final int DAMAGE = 375; // Урон, наносимый свитком ультра

    public UltraScroll() {
        super("Ultra Scroll", "A powerful scroll that deals massive area damage.", 1250);
    }

    @Override
    protected int getDamage() {
        return DAMAGE; // Урон для Ultra Scroll
    }
}