package GameV2;

class IceScroll extends OneTargetHitItems {
    private static final int DAMAGE = 60; // Урон, наносимый свитком льда

    public IceScroll() {
        super("Ice Scroll", "A scroll that freezes the target.", 240);
    }

    @Override
    protected int getDamage() {
        return DAMAGE; // Урон для Ice Scroll
    }

    @Override
    public void applyEffect(Room room) {
        Entity target = getRandomEnemy(room);
        if (target != null) {
            System.out.println(getName() + " hits " + target.getName() + " for " + getDamage() + " ice damage!");
            target.takeDamage(getDamage()); // Наносим урон
            target.addEffect(new FrozenEffect(2, "Frozen")); // Применяем эффект заморозки
        } else {
            System.out.println("No enemies to freeze with " + getName() + ".");
        }
    }
}