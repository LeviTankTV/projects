package GameV2;

import java.util.Random;

public class WandFactory {
    private static final Class<? extends Wand>[] WAND_TYPES = new Class[] {
            IceWand.class,
            FireWand.class,
            LightningWand.class
    };

    private static final Random RANDOM = new Random();

    public static Wand createRandomWand() {
        int index = RANDOM.nextInt(WAND_TYPES.length);
        try {
            return WAND_TYPES[index].newInstance(); // Create a new instance of the selected wand type
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null; // Handle the exception as needed
        }
    }
}