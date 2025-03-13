package GameV2;

import java.util.List;

public class MageHealer extends Mage {

    public MageHealer(String name, int level) {
        super(name, level);
    }

    @Override
    public void performAction(Game game, Room room) {
        List<Entity> enemies = room.getEnemies(); // Assuming Room has a method getEnemies() that returns a list of enemies

        for (Entity enemy : enemies) {
            enemy.heal(125); // Heal each enemy by 125 health
            System.out.println(getName() + " healed " + enemy.getName() + " by 125 health.");
        }
    }
}