package GameV2;

import java.util.Random;

public class FireBow extends Bow {
    public FireBow() {
        super("Fire Bow", "A bow that ignites its arrows with flames.", 180, 25, 15, 2);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 75) {
            target.addEffect(new BurningEffect(3, "Burning")); // Applies a 3-turn Burning effect.
            System.out.println(target.getName() + " is engulfed in flames!");
        }
    }
}
