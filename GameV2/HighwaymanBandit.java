package GameV2;

public class HighwaymanBandit extends Bandit {
    public HighwaymanBandit(String name, int level) {
        super(name, level);
    }

    @Override
    public void dropLoot(Game game) {
        super.dropLoot(game); // Call the base class dropLoot method
    }

    @Override
    public void performRobbery(Entity target) {
        super.performRobbery(target); // Call the base class performRobbery method
    }
}