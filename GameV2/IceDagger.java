package GameV2;

import java.util.Random;

class IceDagger extends OneTargetHitItems {
    private static final int DAMAGE = 125; // Урон, наносимый кинжалом

    public IceDagger() {
        super("Ice Dagger", "A dagger that freezes the target on impact.", 180);
    }

    @Override
    protected int getDamage() {
        return DAMAGE; // Урон для Ice Dagger
    }

    public void applySpecialEffect(Entity target) {
        target.addEffect(new FrozenEffect(3, "Frozen"));
        System.out.println(target.getName() + " is frozen!");

    }
}
