package GameV2;

import java.util.Random;

public class ShortBow extends Bow {
    public ShortBow() {
        super("Short Bow", "A compact bow for close-range combat.", 75, 22, 12, 1);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 25) { // 25% chance
            target.addEffect(new WeakenedEffect(2, "Slowed")); // Applies a 2-turn Slow effect.
            System.out.println(target.getName() + " is weakened!");
        }
    }
}