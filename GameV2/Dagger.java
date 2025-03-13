package GameV2;

class Dagger extends OneTargetHitItems {
    private static final int DAMAGE = 125; // Урон, наносимый кинжалом

    public Dagger() {
        super("Dagger", "A common dagger that can be thrown at enemies.", 75);
    }

    @Override
    protected int getDamage() {
        return DAMAGE; // Урон для Dagger
    }
}