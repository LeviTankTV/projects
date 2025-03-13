package GameV2;

import java.util.Random;

public class SecondSkeletonFactory {

    private Random random = new Random();
    private BowFactory bowFactory; // Assuming you have a BowFactory class

    public SecondSkeletonFactory() {
        this.bowFactory = new BowFactory(); // Initialize the BowFactory
    }

    public Skeleton createSkeleton(Player player) {
        int playerLevel = player.getLevel();
        int skeletonLevel = playerLevel + random.nextInt(6) - 2; // Level range: playerLevel - 2 to playerLevel + 3

        // Ensure that the skeleton level is at least 1
        skeletonLevel = Math.max(1, skeletonLevel);

        // Randomly choose one of the five strong skeleton types
        int skeletonType = random.nextInt(5); // 0-5 for 6 types of skeletons
        Skeleton skeleton;
        switch (skeletonType) {
            case 0:
                skeleton = new EliteArcherSkeleton("Elite Archer Skeleton", skeletonLevel);
                break;
            case 1:
                skeleton = new FrostSkeleton("Frost Skeleton", skeletonLevel);
                break;
            case 2:
                skeleton = new FlameSkeleton("Flame Skeleton", skeletonLevel);
                break;
            case 3:
                skeleton = new ShadowSkeleton("Shadow Skeleton", skeletonLevel);
                break;
            case 4:
                skeleton = new NecroBone("Skeleton Necromancer", skeletonLevel);
                break;
            default:
                skeleton = new ArmoredSkeleton("Armored Skeleton", skeletonLevel);
                break;
        }

        // Create a bow from the BowFactory and equip it to the skeleton
        Weapon bow = BowFactory.createRandomBow(); // Assuming createBow() returns a Weapon object
        skeleton.equipWeapon(bow); // Equip the bow to the skeleton

        return skeleton;
    }
}