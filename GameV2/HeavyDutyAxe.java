package GameV2;

public class HeavyDutyAxe extends Axe {
    public HeavyDutyAxe() {
        super("Heavy Duty Axe", "A massive axe designed for maximum damage.", 180, 45, 60, 2);
    }

    @Override
    public int calculateDamage(Entity attacker, Entity target) {
        // Increased damage but lower critical chance
        int damage = super.calculateDamage(attacker, target);
        damage += 25; // Additional flat damage
        return damage;
    }
}
