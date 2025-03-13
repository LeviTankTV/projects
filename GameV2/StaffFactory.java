package GameV2;

import java.util.Random;

public class StaffFactory {
    private static final Class<? extends Staff>[] STAFF_TYPES = new Class[] {
            WoodenStaff.class,
            EnchantedStaff.class,
            ElementalStaff.class,
            LightningStaff.class,
            NatureStaff.class,
            ShadowStaff.class
    };

    private static final Random RANDOM = new Random();

    public static Staff createRandomStaff() {
        int index = RANDOM.nextInt(STAFF_TYPES.length);
        try {
            return STAFF_TYPES[index].newInstance(); // Create a new instance of the selected staff type
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null; // Handle the exception as needed
        }
    }
}
