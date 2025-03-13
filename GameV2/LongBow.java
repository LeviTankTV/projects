package GameV2;

import java.util.Random;

public class LongBow extends Bow {
    public LongBow() {
        super("Long Bow", "A bow designed for long-range attacks.", 180, 25, 40, 2);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 75) {
            target.addEffect(new MarkedEffect(3, "Marked", 1.4)); // Applies a 3-turn Marked effect.
            System.out.println(target.getName() + " is marked for increased damage!");
        }
    }
}