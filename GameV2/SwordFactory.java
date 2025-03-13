package GameV2;

import java.util.Random;

public class SwordFactory {
    private static final Class<? extends Sword>[] SWORD_TYPES = new Class[] {
            GreatSword.class,
            LongSword.class,
            MagicSword.class,
            ShortSword.class,
            LightningSword.class,
            HolySword.class,
            KnightsSword.class,
            DualWieldSword.class,
            BladeOfTheAncients.class,
            ShadowSword.class
    };

    private static final Random RANDOM = new Random();

    public static Sword createRandomSword() {
        int index = RANDOM.nextInt(SWORD_TYPES.length);
        try {
            return SWORD_TYPES[index].newInstance(); // Create a new instance of the selected sword type
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null; // Handle the exception as needed
        }
    }
}