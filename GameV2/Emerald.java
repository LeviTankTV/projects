package GameV2;

public class Emerald extends UniqueItem {
    public Emerald() {
        super("Emerald", "A stunning green gemstone, valuable for trading.", 6300); // Example sell price
    }

    @Override
    public void applyEffect(Entity target) {
        // No special effect, just for selling
        System.out.println("Oops, you dropped Emerald!");
    }
}