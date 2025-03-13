package GameV2;

public class Ruby extends UniqueItem {
    public Ruby() {
        super("Ruby", "A precious red gemstone, valuable for trading.", 7500); // Example sell price
    }

    @Override
    public void applyEffect(Entity target) {
        // No special effect, just for selling
        System.out.println("Oops, you dropped Ruby!");
    }
}
