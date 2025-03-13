package GameV2;

import java.util.Random;

public class IceWand extends Wand {
    public IceWand() {
        super("Ice Wand", "A wand that channels the power of ice.", 180, 15, 10, 2);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 40) { // 40% chance
            target.addEffect(new FrozenEffect(2, "Frozen")); // Applies a 1-turn Freeze effect
            System.out.println(target.getName() + " is frozen!");
        }
    }
}