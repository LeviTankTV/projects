package GameV2;

import java.util.Random;

public class BerryFactory {
    private Random random = new Random();

    public MagicalFruit createRandomBerry() {
        int berryType = random.nextInt(4); // Random number between 0 and 3
        switch (berryType) {
            case 0:
                return new MagicStrawberry();
            case 1:
                return new MagicBerry();
            case 2:
                return new CelestialBerry();
            case 3:
            default:
                return new AetherBerry();
        }
    }
}