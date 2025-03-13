package GameV2;

import java.util.Random;

public class CrossBow extends Bow {
    public CrossBow() {
        super("Crossbow", "A powerful bow with a high-powered shot.", 180, 25, 18, 2);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 45) { // 15% chance
            target.addEffect(new StunnedEffect(2, "Stunned")); // Applies a 1-turn Stun effect.
            System.out.println(target.getName() + " is stunned!");
        }
    }
}