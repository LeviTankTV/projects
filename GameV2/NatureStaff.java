package GameV2;

import java.util.Random;

public class NatureStaff extends Staff {
    public NatureStaff() {
        super("Nature Staff", "A staff that channels the power of nature.", 140, 8, 20, 2);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 45) { // 45% chance
            target.addEffect(new WeakenedEffect(3, "Weakened")); // Applies a 3-turn Weaken effect.
            System.out.println(target.getName() + " is weakened!");
        }
    }
}
