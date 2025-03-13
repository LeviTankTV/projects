package GameV2;

public class DeathMarkEffect extends Effect {

    public DeathMarkEffect(int duration, String description) {
        super(duration, description);
    }

    @Override
    public void apply(Entity target) {
        // Apply the effect (e.g., add a visual indicator to the target)
        System.out.println(target.getName() + " is marked for death! They have " + duration + " turns to survive.");
    }

    @Override
    public void remove(Entity target) {
        // Remove the effect (e.g., remove the visual indicator)
        System.out.println(target.getName() + "'s death mark has faded.");
    }

    @Override
    public void update(Entity target) {
        duration--;

        if (duration <= 0) {
            // If the Death Mark effect expires, the target dies
            target.setHealth(0);
            System.out.println(target.getName() + " has succumbed to the Death Mark and perished!");
            remove(target);
        } else {
            // You could add a visual effect here to indicate the remaining duration
            System.out.println(target.getName() + " has " + duration + " turns left to live.");
        }
    }
}
