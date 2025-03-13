package GameV2;

public abstract class Royalty extends Entity {

    public Royalty(String name, int level, double baseAttack, double baseHealth) {
        super(name, level, baseHealth, 35, baseAttack, 8); // Adjust health bonus and defense as needed
    }

    // Abstract method for performAction to be implemented by subclasses
    public abstract void performAction(Game game, Room room);
}