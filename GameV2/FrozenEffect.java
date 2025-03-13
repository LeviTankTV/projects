package GameV2;

class FrozenEffect extends Effect {
    public FrozenEffect(int duration, String description) {
        super(duration, description);
    }

    @Override
    public void apply(Entity target) {
        target.setFrozen(true);
        target.setTurnsFrozen(duration);
    }

    @Override
    public void remove(Entity target) {
        target.setFrozen(false);
        target.setTurnsFrozen(0);
    }

    @Override
    public void update(Entity target) {
        duration--;

        if (target.getTurnsFrozen() > 0) {
            target.setTurnsFrozen(target.getTurnsFrozen() - 1);
        }

        if (duration <= 0) {
            remove(target);
        }
    }
}
