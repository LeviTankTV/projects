package GameV2;

import java.util.Random;

public class WeakenBow extends Bow {
    public WeakenBow() {
        super("Weaken Bow", "A bow that reduces the target's strength.", 180, 27, 40, 3);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 75) {
            target.addEffect(new WeakenedEffect(3, "Weakened")); // Applies a 3-turn Weaken effect.
            System.out.println(target.getName() + " is weakened!");
        }
    }
}