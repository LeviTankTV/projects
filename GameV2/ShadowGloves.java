package GameV2;

import java.util.Random;

public class ShadowGloves extends Gloves {
    public ShadowGloves() {
        super("Shadow Gloves", "Gloves that enhance stealth and agility.", 1500, 1, 1, 1);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 15) { // 15% chance
            target.addEffect(new NightmarishEffect(7, "Nightmarish")); // Applies a 2-turn Nightmarish effect.
            System.out.println(target.getName() + " is trapped in a nightmare!");
        }
    }
}