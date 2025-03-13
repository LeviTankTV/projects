package GameV2;

import java.util.Random;

public class FireWand extends Wand {
    public FireWand() {
        super("Fire Wand", "A wand that channels the power of fire.", 180, 15, 10, 2);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 25) { // 25% chance
            target.addEffect(new BurningEffect(3, "Burning")); // Applies a 3-turn Burn effect
            System.out.println(target.getName() + " is burning!");
        }
    }
}
