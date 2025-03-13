package GameV2;

import java.util.Random;

public class BleedingBow extends Bow {
    public BleedingBow() {
        super("Bleeding Bow", "A bow that inflicts bleeding on the target.", 180, 22, 28, 3);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 40) {
            target.addEffect(new BleedingEffect(3, "Bleeding")); // Applies a 3-turn Bleeding effect.
            System.out.println(target.getName() + " is bleeding!");
        }
    }
}