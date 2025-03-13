package GameV2;

import java.util.Random;

public class ThunderGloves extends Gloves {
    public ThunderGloves() {
        super("Thunder Gloves", "Gloves that crackle with electric energy, shocking enemies.", 108, 7, 18, 2);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        // Chance to stun the target
        Random random = new Random();
        if (random.nextInt(100) < 20) { // 20% chance to stun
            System.out.println(target.getName() + " is stunned by a shock from " + getName() + "!");
            target.addEffect(new StunnedEffect(1, "Stunned")); // Assuming the Entity class has a method to stun
        }
    }
}