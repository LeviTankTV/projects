package GameV2;

import java.util.Random;

public class GlovesFactory {
    private static final Class<? extends Gloves>[] GLOVES_TYPES = new Class[] {
            FlameGloves.class,
            ShadowGloves.class,
            ThunderGloves.class,
            TitansGloves.class,
            AssassinsGloves.class,
            GlovesOfTheBerserker.class
    };

    private static final Random RANDOM = new Random();

    public static Gloves createRandomGloves() {
        int index = RANDOM.nextInt(GLOVES_TYPES.length);
        try {
            return GLOVES_TYPES[index].newInstance(); // Create a new instance of the selected glove type
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null; // Handle the exception as needed
        }
    }
}