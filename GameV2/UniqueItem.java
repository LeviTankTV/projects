package GameV2;

public abstract class UniqueItem extends Item {
    public UniqueItem(String name, String description, int value) {
        super(name, description, value);
    }

    public abstract void applyEffect(Entity target); // Now abstract
}
