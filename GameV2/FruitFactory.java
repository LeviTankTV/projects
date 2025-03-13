package GameV2;

import java.util.Random;

public class FruitFactory {
    private Random random = new Random();

    public MagicalFruit createRandomFruit() {
        int fruitType = random.nextInt(4); // Random number between 0 and 3
        switch (fruitType) {
            case 0:
                return new MagicApple();
            case 1:
                return new MagicBanana();
            case 2:
                return new MagicPear();
            case 3:
            default:
                return new AetherFruit();
        }
    }
}