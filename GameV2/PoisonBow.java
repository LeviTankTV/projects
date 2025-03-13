package GameV2;

import java.util.Random;

public class PoisonBow extends Bow {
    public PoisonBow() {
        super("Potion Bow", "A bow that shoots poison potions.", 180, 25, 15, 2); // Note: Lower attack power due to healing effect
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 65) { // 65% chance
            target.addEffect(new PoisonEffect(3, "Poisoned")); // Applies a 3-turn Frozen effect.
            System.out.println(target.getName() + " is poisoned!");
        }
    }
}