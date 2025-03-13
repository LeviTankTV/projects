package GameV2;

class WeakenedEffect extends Effect {
    public WeakenedEffect(int duration, String description) {
        super(duration, description);
    }

    @Override
    public void apply(Entity target) {
        target.setWeakened(true);
        target.setTurnsWeakened(duration);
    }

    @Override
    public void remove(Entity target) {
        target.setWeakened(false);
        target.setTurnsWeakened(0);
    }

    @Override
    public void update(Entity target) {
        duration--;

        if (target.getTurnsWeakened() > 0) {
            target.setTurnsWeakened(target.getTurnsWeakened() - 1);
        }

        if (duration <= 0) {
            remove(target);
        }
    }
}