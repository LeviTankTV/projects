package GameV2;

class BleedingEffect extends Effect {
    public BleedingEffect(int duration, String description) {
        super(duration, description);
    }

    @Override
    public void apply(Entity target) {
        target.setBleeding(true);
        target.setTurnsBleeding(duration);
    }

    @Override
    public void remove(Entity target) {
        target.setBleeding(false);
        target.setTurnsBleeding(0);
    }

    @Override
    public void update(Entity target) {
        duration--;

        if (target.getTurnsBleeding() > 0) {
            target.setTurnsBleeding(target.getTurnsBleeding() - 1);
        }

        if (duration <= 0) {
            remove(target);
        }
    }
}