package GameV2;

import java.util.Random;

public class SkeletonBossUltraBow extends Bow {
    public SkeletonBossUltraBow() {
        super("Skeleton Boss Ultra Bow", "A powerful bow wielded by the Skeleton Boss.", 3000, 80, 45, 4);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 25) { // 25% chance
            target.addEffect(new DeathMarkEffect(3, "Death Mark")); // Applies a 3-turn Death Mark effect.
            System.out.println(target.getName() + " is marked for death!");
        }
    }
}