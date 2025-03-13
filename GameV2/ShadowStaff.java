package GameV2;

import java.util.Random;

public class ShadowStaff extends Staff {
    public ShadowStaff() {
        super("Shadow Staff", "A staff that seems to absorb light.", 1500, 10, 20, 1);
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