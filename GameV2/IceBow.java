package GameV2;

import java.util.Random;

public class IceBow extends Bow {
    public IceBow() {
        super("Ice Bow", "A bow that shoots ice arrows.", 180, 20, 25, 3);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 25) { // 25% chance
            target.addEffect(new FrozenEffect(2, "Frozen")); // Applies a 2-turn Frozen effect.
            System.out.println(target.getName() + " is frozen!");
        }
    }
}
