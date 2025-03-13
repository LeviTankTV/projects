package GameV2;

class BurningEffect extends Effect {
    public BurningEffect(int duration, String description) {
        super(duration, description);
    }

    @Override
    public void apply(Entity target) {
        target.setBurning(true);
        target.setTurnsBurning(duration);
    }

    @Override
    public void remove(Entity target) {
        target.setBurning(false);
        target.setTurnsBurning(0);
    }

    @Override
    public void update(Entity target) {
        duration--;

        if (target.getTurnsBurning() > 0) {
            target.setTurnsBurning(target.getTurnsBurning() - 1);
        }

        if (duration <= 0) {
            remove(target);
        }
    }
}
