package GameV2;

class MarkedEffect extends Effect {
    private double damageMultiplier; // Multiplier for increased damage

    public MarkedEffect(int duration, String description, double damageMultiplier) {
        super(duration, description);
        this.damageMultiplier = damageMultiplier;
    }

    @Override
    public void apply(Entity target) {
        target.setMarked(true); // Assuming there's a method to mark the entity
        target.setDamageMultiplier(damageMultiplier); // Set the damage multiplier
        target.setTurnsMarked(duration); // Set the duration of the effect
    }

    @Override
    public void remove(Entity target) {
        target.setMarked(false); // Remove the marked status
        target.setDamageMultiplier(1.0); // Reset damage multiplier to normal
        target.setTurnsMarked(0); // Reset turns marked
    }

    @Override
    public void update(Entity target) {
        duration--;

        if (target.getTurnsMarked() > 0) {
            target.setTurnsMarked(target.getTurnsMarked() - 1);
        }

        if (duration <= 0) {
            remove(target); // Remove effect when duration expires
        }
    }
}