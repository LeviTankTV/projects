package GameV2;

import java.util.Random;

public abstract class Weapon extends Item {
    private int attackPower;
    private int critChance; // In percentage (e.g., 5 for 5%)
    private int critMultiplier;

    public Weapon(String name, String description, int value, int attackPower, int critChance, int critMultiplier) {
        super(name, description, value);
        this.attackPower = attackPower;
        this.critChance = critChance;
        this.critMultiplier = critMultiplier;
    }

    // Getters
    public int getAttackPower() {
        return attackPower;
    }

    public int getCritChance() {
        return critChance;
    }

    public int getCritMultiplier() {
        return critMultiplier;
    }

    // Abstract methods to be implemented by subclasses
    public abstract int calculateDamage(Entity attacker, Entity target); // Calculate damage based on specific weapon mechanics

    public abstract void applySpecialEffect(Entity target); // Apply specific effects (e.g., bleed, slow)
}