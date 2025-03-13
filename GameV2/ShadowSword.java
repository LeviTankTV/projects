package GameV2;

import java.util.Random;

public class ShadowSword extends Sword {
    public ShadowSword() {
        super("Sword Of Nightmares", "A sword that can manipulate dreams and nightmares.", 1500, 15, 25, 2);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 15) { // 15% chance
            target.addEffect(new NightmarishEffect(5, "Nightmarish")); // Applies a 2-turn Nightmarish effect.
        }
    }
}
