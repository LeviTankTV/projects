package GameV2;

class MarkScroll extends OneTargetHitItems {
    private static final int DAMAGE = 0; // Урон не наносится, но цель помечается

    public MarkScroll() {
        super("Mark Scroll", "A scroll that marks a target for increased damage.", 100);
    }

    @Override
    protected int getDamage() {
        return DAMAGE; // Урон для Mark Scroll
    }

    @Override
    public void applyEffect(Room room) {
        Entity target = getRandomEnemy(room);
        if (target != null) {
            System.out.println("Mark Scroll marks " + target.getName() + " for increased damage!");
            target.addEffect(new MarkedEffect(3, "Marked", 2.4)); // Применяем эффект пометки
        } else {
            System.out.println("No enemies to mark with Mark Scroll.");
        }
    }
}