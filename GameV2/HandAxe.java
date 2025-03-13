package GameV2;

import java.util.Random;

public class HandAxe extends Axe {
    public HandAxe() {
        super("Hand Axe", "A small, balanced axe.", 75, 18, 20, 3);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 45) {
            target.addEffect(new BleedingEffect(2, "Bleeding")); // Applies a 2-turn Bleeding effect.
            System.out.println(target.getName() + " is bleeding!");
        }
    }
}