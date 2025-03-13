package GameV2;

import java.util.Random;

public class AxeFactory {
    private static final Class<? extends Axe>[] AXE_TYPES = new Class[] {
            BattleAxe.class,
            DoubleBladedAxe.class,
            HandAxe.class,
            GuardianAxe.class,
            CeremonialAxe.class,
            DwarvenAxe.class,
            HeavyDutyAxe.class
    };

    private static final Random RANDOM = new Random();

    public static Axe createRandomAxe() {
        int index = RANDOM.nextInt(AXE_TYPES.length);
        try {
            return AXE_TYPES[index].newInstance(); // Create a new instance of the selected axe type
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null; // Handle the exception as needed
        }
    }
}