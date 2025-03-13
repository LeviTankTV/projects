package GameV2;

import java.util.Random;

public class BowFactory {
    private static final Class<? extends Bow>[] BOW_TYPES = new Class[] {
            ShortBow.class,
            WeakenBow.class,
            PoisonBow.class,
            MagicBow.class,
            IceBow.class,
            CrossBow.class,
            BleedingBow.class,
            LongBow.class,
            FireBow.class
    };

    private static final Random RANDOM = new Random();

    public static Bow createRandomBow() {
        int index = RANDOM.nextInt(BOW_TYPES.length);
        try {
            return BOW_TYPES[index].newInstance(); // Create a new instance of the selected bow type
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null; // Handle the exception as needed
        }
    }
}