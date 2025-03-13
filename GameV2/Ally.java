package GameV2;

import java.io.Serializable;

public abstract class Ally extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;
    // Additional attributes specific to allies can be added here
    private String role; // Optional: to define the role of the ally (e.g., healer, attacker)

    // Constructor for the Ally class
    public Ally(String name, int level, double baseHealth, double healthBonus, double baseAttack, double baseDefense, String role) {
        super(name, level, baseHealth, healthBonus, baseAttack, baseDefense); // Call the parent constructor
        this.role = role; // Set the role of the ally
    }

    // Abstract method that subclasses must implement
    public abstract void performAllyAction(Room room, Player player);

    // Optional: Getter for the role
    public String getRole() {
        return role;
    }

    @Override
    public void dropLoot(Game game){
    }
}