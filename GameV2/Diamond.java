package GameV2;

public class Diamond extends UniqueItem {
    public Diamond() {
        super("Diamond", "A rare and valuable gemstone, highly sought after.", 13500); // Example sell price
    }

    @Override
    public void applyEffect(Entity target) {
        // No special effect, just for selling
        System.out.println("Oops, you dropped Diamond!");
    }
}