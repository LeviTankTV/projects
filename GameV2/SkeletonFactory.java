package GameV2;

import java.util.Random;

public class SkeletonFactory {

    private Random random = new Random();
    private BowFactory bowFactory = new BowFactory(); // Create an instance of BowFactory

    public Skeleton createSkeleton(Player player) {
        int playerLevel = player.getLevel();
        int skeletonLevel = playerLevel + random.nextInt(6) - 2; // Skeleton level from playerLevel - 2 to playerLevel + 3

        // Ensure skeleton level is within reasonable bounds (e.g., not negative)
        skeletonLevel = Math.max(1, skeletonLevel); // Minimum level 1

        // Randomly choose the type of skeleton
        int skeletonType = random.nextInt(4); // 0-3 for 4 types of skeletons
        Skeleton skeleton;
        switch (skeletonType) {
            case 0:
                skeleton = new MageSkeleton("Mage Skeleton", skeletonLevel);
                break;
            case 1:
                skeleton = new ArcherSkeleton("Archer Skeleton", skeletonLevel);
                break;
            case 2:
                skeleton = new ShieldedSkeleton("Shielded Skeleton", skeletonLevel);
                break;
            case 3:
            default:
                skeleton = new PoisonSkeleton("Poison Skeleton", skeletonLevel);
                break;
        }

        // Determine if the skeleton will have a weapon (40% chance)
        if (random.nextInt(100) < 40) { // 40% chance to have a weapon
            Bow bow = bowFactory.createRandomBow(); // Create a random bow
            skeleton.equipWeapon(bow); // Equip the bow
        }

        return skeleton;
    }
}