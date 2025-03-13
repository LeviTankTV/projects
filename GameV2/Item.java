package GameV2;

import java.io.Serializable;
import java.util.Random;

public abstract class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String description;
    private int value;

    // Constructor
    public Item(String name, String description, int value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

}