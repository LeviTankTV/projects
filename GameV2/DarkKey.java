package GameV2;

public class DarkKey extends Key {
    public DarkKey() {
        super("Dark Key", "A mysterious key that unlocks dark secrets.", 3000);
    }

    @Override
    public void applyEffect(Entity target) {
        // Implement the effect of using the Dark Key on the target
        System.out.println("Using Dark Key on " + target.getName() + ".");
        // Example: unlock a hidden passage or secret room
    }
}