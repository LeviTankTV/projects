package GameV2;

class FireballScroll extends OneTargetHitItems {
    private static final int DAMAGE = 100; // Урон, наносимый свитком огненного шара

    public FireballScroll() {
        super("Fireball Scroll", "A scroll that unleashes a fireball on impact.", 120);
    }

    @Override
    protected int getDamage() {
        return DAMAGE; // Урон для Fireball Scroll
    }

    @Override
    public void applyEffect(Room room) {
        Entity target = getRandomEnemy(room);
        if (target != null) {
            System.out.println(getName() + " hits " + target.getName() + " for " + getDamage() + " fire damage!");
            target.takeDamage(getDamage()); // Наносим урон
            target.addEffect(new BurningEffect(3, "Burning")); // Применяем эффект горения
        } else {
            System.out.println("No enemies to hit with " + getName() + ".");
        }
    }
}