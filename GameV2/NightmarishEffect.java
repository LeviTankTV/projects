package GameV2;

class NightmarishEffect extends Effect {
    private int damagePerTurn; // Damage inflicted each turn
    private boolean causingFear; // Indicates if the target is afraid

    public NightmarishEffect(int duration, String description) {
        super(duration, description);
        this.damagePerTurn = 15; // Base damage per turn
        this.causingFear = true; // This effect causes fear
    }

    @Override
    public void apply(Entity target) {
        target.setNightmarish(true); // Set the nightmarish state
        target.setTurnsNightmarish(duration); // Set the duration of the effect
        System.out.println(target.getName() + " is trapped in a nightmare!");
    }

    @Override
    public void remove(Entity target) {
        target.setNightmarish(false); // Remove the nightmarish state
        target.setTurnsNightmarish(0); // Reset duration
        System.out.println(target.getName() + " has escaped the nightmare!");
    }

    @Override
    public void update(Entity target) {
        duration--;

        // Inflict damage every turn
        target.takeDamage(damagePerTurn);
        System.out.println(target.getName() + " suffers " + damagePerTurn + " damage from the nightmare!");

        if (target.getTurnsNightmarish() > 0) {
            target.setTurnsNightmarish(target.getTurnsNightmarish() - 1);
        }

        if (duration <= 0) {
            remove(target);
        }
    }
}
