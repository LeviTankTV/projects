package GameV2;

import java.util.Random;

public class BanditBossMegaStaff extends Staff {
    public BanditBossMegaStaff() {
        super("Bandit Boss Mega Staff", "The ultimate weapon of the Bandit Boss.", 3000, 80, 20, 3);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        // This staff applies both Poison and Bleeding effects
        target.addEffect(new PoisonEffect(3, "Poisoned"));
        target.addEffect(new BleedingEffect(3, "Bleeding"));
        target.addEffect(new WeakenedEffect(3, "Weakened"));
        target.addEffect(new BurningEffect(3, "Burning"));
        Random random = new Random();
        int rel = random.nextInt(101);
        if (rel <= 25) {
            target.addEffect(new MarkedEffect(3, "Marked", 3));
            System.out.println(target.getName() + " got Marked!");
        }
        System.out.println(target.getName() + " is poisoned, burning, weakened, bleeding!");
    }
}
