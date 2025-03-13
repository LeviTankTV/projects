package GameV2;

public abstract class Amulet extends Item {
    private static final int FIXED_VALUE = 750;
    public Amulet(String name, String description) {
        super(name, description, FIXED_VALUE);
    }

    // This method is used to apply the passive effect of the item
    // This method will be called from Player class
    public abstract void applyPassiveEffect(Player player);

    public abstract void applyEffect(Entity target); // Now abstract
}