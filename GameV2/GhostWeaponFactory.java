package GameV2;

import java.util.Random;

public class GhostWeaponFactory {
    private static final Random RANDOM = new Random();

    public static Item createRandomWeapon() {
        int choice = RANDOM.nextInt(6); // 6 типов оружия

        return switch (choice) {
            case 0 -> SwordFactory.createRandomSword();
            case 1 -> GlovesFactory.createRandomGloves();
            case 2 -> BowFactory.createRandomBow();
            case 3 -> StaffFactory.createRandomStaff();
            case 4 -> WandFactory.createRandomWand();
            case 5 -> AxeFactory.createRandomAxe();
            default -> null;
        };
    }
}