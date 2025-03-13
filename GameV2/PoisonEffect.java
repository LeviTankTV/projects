package GameV2;

class PoisonEffect extends Effect {

    public PoisonEffect(int duration, String description) {
        super(duration, description);
    }

    @Override
    public void apply(Entity target) {
        target.setPoisoned(true); // Set the poisoned flag
        target.setTurnsPoisoned(duration); // Set the turns poisoned counter
    }

    @Override
    public void remove(Entity target) {
        target.setPoisoned(false); // Remove the poisoned flag
        target.setTurnsPoisoned(0); // Reset the turns poisoned counter
    }

    @Override
    public void update(Entity target) {
        duration--;

        if (target.getTurnsPoisoned() > 0) {
            target.setTurnsPoisoned(target.getTurnsPoisoned() - 1);
        }

        if (duration <= 0) {
            remove(target);
        }
            // The damage is already handled in the takeDamage() method
            // of the Entity class, so we don't need to call it again here.

    }
}