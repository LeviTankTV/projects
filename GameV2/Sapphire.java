package GameV2;

public class Sapphire extends UniqueItem {
    public Sapphire() {
        super("Sapphire", "A beautiful blue gemstone, valuable for trading.", 10500); // Example sell price
    }

    @Override
    public void applyEffect(Entity target) {
        // No special effect, just for selling
        System.out.println("Oops, you dropped Sapphire!");
    }
}
