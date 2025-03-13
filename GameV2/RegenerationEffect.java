package GameV2;

class RegenerationEffect extends Effect {
    private double healAmount; // Amount of health restored each turn

    public RegenerationEffect(int duration, String description, double healAmount) {
        super(duration, description);
        this.healAmount = healAmount;
    }

    @Override
    public void apply(Entity target) {
        target.setRegenerating(true); // Assuming there's a method to set regeneration status
        target.setRegenerationAmount(healAmount); // Set the amount to heal each turn
        target.setTurnsRegenerating(duration); // Set the duration of the effect
    }

    @Override
    public void remove(Entity target) {
        target.setRegenerating(false); // Remove regeneration status
        target.setRegenerationAmount(0); // Reset healing amount
        target.setTurnsRegenerating(0); // Reset turns regenerating
    }

    @Override
    public void update(Entity target) {
        duration--;

        if (duration > 0) {
            target.heal((int) healAmount); // Heal the target
        }

        if (duration <= 0) {
            remove(target); // Remove effect when duration expires
        }
    }
}