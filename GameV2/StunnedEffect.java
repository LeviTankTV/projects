package GameV2;

class StunnedEffect extends Effect {
    public StunnedEffect(int duration, String description) {
        super(duration, description);
    }

    @Override
    public void apply(Entity target) {
        target.setStunned(true);
        target.setTurnsStunned(duration);
    }

    @Override
    public void remove(Entity target) {
        target.setStunned(false);
        target.setTurnsStunned(0);
    }

    @Override
    public void update(Entity target) {
        duration--;

        if (target.getTurnsStunned() > 0) {
            target.setTurnsStunned(target.getTurnsStunned() - 1);
        }

        if (duration <= 0) {
            remove(target);
        }
    }
}
