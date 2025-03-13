package GameV2;

import java.util.List;

public class RegenerationZombie extends Zombie {
    public RegenerationZombie(String name, int level) {
        super(name, level, 10, 325); // Adjust base attack and health as needed
    }

    @Override
    public void performAction(Game game, Room room) {
        regenerateAllies(room);
    }

    private void regenerateAllies(Room room) {
        System.out.println(getName() + " channels dark magic to heal its allies!");
        List<Entity> allies = room.getEnemies(); // Assuming this gets allies in the room
        for (Entity ally : allies) {
            if (ally != null) {
                ally.heal(60);
            }
        }
    }
}
