package GameV2;

import java.util.Random;

public class MagicSword extends Sword {
    public MagicSword() {
        super("Magic Sword", "A sword imbued with magical power.", 450, 67, 35, 2);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        // Implement a magical effect, like a chance to apply a "Frozen" effect.
        Random random = new Random();
        if (random.nextInt(100) < 60) { // 25% chance
            target.addEffect(new FrozenEffect(3, "Frozen")); // Applies a 3-turn Frozen effect.
            System.out.println(target.getName() + " is frozen!");
        }
        if (random.nextInt(100) < 60) { // 25% chance
            target.addEffect(new BurningEffect(3, "Burning")); // Applies a 3-turn Frozen effect.
            System.out.println(target.getName() + " is burning!");
        }
    }
}
