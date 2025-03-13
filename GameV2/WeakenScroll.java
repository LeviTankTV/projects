package GameV2;

class WeakenScroll extends OneTargetHitItems {
    private static final int DAMAGE = 0; // Урон не наносится, но эффект ослабления

    public WeakenScroll() {
        super("Weaken Scroll", "A scroll that weakens the target.", 180);
    }

    @Override
    protected int getDamage() {
        return DAMAGE; // Урон для Weaken Scroll
    }

    @Override
    public void applyEffect(Room room) {
        Entity target = getRandomEnemy(room);
        if (target != null) {
            System.out.println("Weaken Scroll weakens " + target.getName() + "!");
            target.addEffect(new WeakenedEffect(3, "Weakened")); // Применяем эффект ослабления
        } else {
            System.out.println("No enemies to weaken with Weaken Scroll.");
        }
    }
}