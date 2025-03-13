package GameV2;

import java.util.Random;

public class LightningWand extends Wand {
    public LightningWand() {
        super("Lightning Wand", "A wand that channels the power of lightning.", 180, 20, 15, 3);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 40) { // 40% chance
            target.addEffect(new StunnedEffect(1, "Stunned")); // Applies a 1-turn Shock effect
            System.out.println(target.getName() + " is shocked!");
        }
    }
}
