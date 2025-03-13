package GameV2;

public abstract class Consumable extends Item {

    // Constructor
    public Consumable(String name, String description, int value) {
        super(name, description, value); // Call the superclass constructor
    }

    // New applyEffect method for Player
    public abstract void applyEffect(Player player);

}