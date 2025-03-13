package GameV2;

public abstract class Key extends Item {
    public Key(String name, String description, int value) {
        super(name, description, value);
    }

    public abstract void applyEffect(Entity target); // Now abstract
}