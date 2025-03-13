package GameV2;

import java.util.Random;

public class LightningStaff extends Staff {
    public LightningStaff() {
        super("Lightning Staff", "A staff crackling with electrical energy.", 180, 11, 30, 1);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        // Chance to stun the target
        Random random = new Random();
        if (random.nextInt(100) < 40) { // 40% chance to stun
            System.out.println(target.getName() + " is stunned by a jolt of lightning!");
            target.addEffect(new StunnedEffect(1, "Stunned"));
        }
    }
}