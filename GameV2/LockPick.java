package GameV2;

public class LockPick extends Key {
    public LockPick() {
        super("Lock Pick", "A set of tools for picking locks.", 75);
    }

    @Override
    public void applyEffect(Entity target) {
        // Implement the effect of using the Lock Pick on the target
        System.out.println("Using Lock Pick on " + target.getName() + ".");
        // Example: attempt to unlock a door without a key
    }
}