package GameV2;

import java.util.Random;

public class DoubleBladedAxe extends Axe {
    public DoubleBladedAxe() {
        super("Double Bladed Axe", "A deadly axe with two blades.", 150, 35, 40, 2);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 40) { // 10% chance
            target.addEffect(new StunnedEffect(2, "Stunned")); // Applies a 1-turn Disarm effect.
            System.out.println(target.getName() + " is stunned!");
        }
    }
}