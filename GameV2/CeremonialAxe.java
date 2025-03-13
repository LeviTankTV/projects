package GameV2;

public class CeremonialAxe extends Axe {
    public CeremonialAxe() {
        super("Ceremonial Axe", "An ornate axe used in rituals, not for battle.", 4800, 10, 5, 1);
    }

    @Override
    public int calculateDamage(Entity attacker, Entity target) {
        // Lower damage but higher chance of critical hit
        int damage = super.calculateDamage(attacker, target);
        damage = Math.max(damage, 5); // Ensure a minimum damage output
        return damage;
    }
}