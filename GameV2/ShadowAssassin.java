package GameV2;

import java.util.Random;

public class ShadowAssassin extends Assassin {
    public ShadowAssassin(String name, int level) {
        super(name, level);
    }

    @Override
    public void dropLoot(Game game) {
        super.dropLoot(game); // Call the base class method for loot dropping
        }


    @Override
    public void performStealthAttack(Entity target) {
        System.out.println(getName() + " silently approaches and performs a stealth attack on " + target.getName());
        super.performStealthAttack(target); // Call the base class method for attack
    }
}