package GameV2;

class IncreasedStrengthEffect extends Effect {
    private double strengthIncrease; // Amount to increase attack power

    public IncreasedStrengthEffect(int duration, String description, double strengthIncrease) {
        super(duration, description);
        this.strengthIncrease = strengthIncrease;
    }

    @Override
    public void apply(Entity target) {
        target.setStrengthIncreased(true); // Assuming there's a method to set strength increase status
        target.setAttack(target.getAttack() + strengthIncrease); // Increase attack power
        target.setTurnsStrengthIncreased(duration); // Set the duration of the effect
    }

    @Override
    public void remove(Entity target) {
        target.setStrengthIncreased(false); // Remove strength increase status
        target.setAttack(target.getAttack() - strengthIncrease); // Reset attack power
        target.setTurnsStrengthIncreased(0); // Reset turns increased
    }

    @Override
    public void update(Entity target) {
        duration--;

        if (duration <= 0) {
            remove(target); // Remove effect when duration expires
        }
    }
}