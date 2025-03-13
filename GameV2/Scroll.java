package GameV2;

import java.util.List;
import java.util.Random;

public abstract class Scroll extends Item {
    private static final Random random = new Random();

    public Scroll(String name, String description, int value) {
        super(name, description, value);
    }

    // Abstract method to apply effect to a single target
    public abstract void applyEffect(Room room);

    // Method to get a random enemy from the room
    protected Entity getRandomEnemy(Room room) {
        List<Entity> enemies = room.getEnemies();
        if (enemies.isEmpty()) {
            return null; // No enemies to hit
        }
        int index = random.nextInt(enemies.size()); // Get a random index
        return enemies.get(index); // Return the random enemy
    }
}