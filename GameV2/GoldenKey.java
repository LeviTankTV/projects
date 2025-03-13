package GameV2;

public class GoldenKey extends Key {
    public GoldenKey() {
        super("Golden Key", "A luxurious golden key that unlocks golden locks.", 600);
    }

    @Override
    public void applyEffect(Entity target) {
        // Implement the effect of using the Golden Key on the target
        System.out.println("Using Golden Key on " + target.getName() + ".");
        // Example: unlock a golden door or chest
    }
}