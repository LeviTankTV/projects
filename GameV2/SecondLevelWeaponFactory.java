package GameV2;

import java.util.Random;

public class SecondLevelWeaponFactory {
    private static final Class<?>[] WEAPON_FACTORIES = new Class[] {
            SwordFactory.class,
            GlovesFactory.class,
            BowFactory.class,
            StaffFactory.class,
            WandFactory.class,
            AxeFactory.class
    };

    private static final Random RANDOM = new Random();

    public static Item createRandomItem() {
        int index = RANDOM.nextInt(WEAPON_FACTORIES.length);
        try {
            // Use reflection to call the createRandomX method of the selected factory
            return (Item) WEAPON_FACTORIES[index].getMethod("createRandom" + WEAPON_FACTORIES[index].getSimpleName().replace("Factory", "")).invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Handle the exception as needed
        }
    }
}