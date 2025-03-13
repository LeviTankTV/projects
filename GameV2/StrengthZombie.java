package GameV2;

import java.util.List;
import java.util.Random;

public class StrengthZombie extends Zombie {
    public StrengthZombie(String name, int level) {
        super(name, level, 12, 325); // Adjust base attack and health as needed
    }

    @Override
    public void performAction(Game game, Room room) {
        increaseAllyStrength(room);
    }

    private void increaseAllyStrength(Room room) {
        System.out.println(getName() + " inspires its allies, increasing their strength!");
        List<Entity> allies = room.getEnemies(); // Assuming this gets allies in the room
        for (Entity ally : allies) {
            if (ally != null) {
                ally.addEffect(new IncreasedStrengthEffect(2, "Strength", 1.5));
            }
        }
    }
}