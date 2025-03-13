package GameV2;

class ExplosionDagger extends AllHitWeapons {
    private static final int DAMAGE = 125; // Урон, наносимый взрывным кинжалом

    public ExplosionDagger() {
        super("Explosion Dagger", "A dagger that explodes on impact.", 300);
    }

    @Override
    protected int getDamage() {
        return DAMAGE; // Урон для Explosion Dagger
    }
}