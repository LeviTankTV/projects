package GameV2;

public class NecroEffect extends Effect {
    private double damagePerTurn;

    public NecroEffect(int duration, String description) {
        super(duration, description);
        this.damagePerTurn = 15; // Base damage per turn
    }

    @Override
    public void apply(Entity target) {
        target.setNecrotic(true);
        target.setTurnsNecrotic(duration);

        // Initial stat reduction
        target.setAttack(target.getAttack() * 0.9);
        target.setDefense(target.getDefense() * 0.9);

        System.out.println(target.getName() + " is afflicted with necrotic energy!");
    }

    @Override
    public void remove(Entity target) {
        target.setNecrotic(false);
        target.setTurnsNecrotic(0);

        // Restore stats
        target.setAttack(target.getAttack() / 0.9);
        target.setDefense(target.getDefense() / 0.9);

        System.out.println(target.getName() + " recovers from necrotic affliction!");
    }

    @Override
    public void update(Entity target) {
        duration--;

        if (target.getTurnsNecrotic() > 0) {
            // Deal damage
            target.takeDamage(damagePerTurn);
            System.out.println(target.getName() + " takes " + damagePerTurn + " necrotic damage!");

            target.setTurnsNecrotic(target.getTurnsNecrotic() - 1);
        }

        if (duration <= 0) {
            remove(target);
        }
    }
}