package GameV2;

import java.util.Random;

public class FirstLevelWeaponFactory {
    private static final Class<? extends Item>[] WEAPON_TYPES = new Class[] {
            WoodenStaff.class,
            LongSword.class,
            GreatSword.class,
            DoubleBladedAxe.class,
            CrossBow.class,
            ShortBow.class,
            MinorHealingPotion.class,
            MinorExperiencePotion.class,
            MinorManaPotion.class,
            LockPick.class,
            Dagger.class
    };

    private static final Random RANDOM = new Random();

    public static Item createRandomItem() {
        int index = RANDOM.nextInt(WEAPON_TYPES.length);
        try {
            return WEAPON_TYPES[index].newInstance(); // Create a new instance of the selected weapon type
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null; // Handle the exception as needed
        }
    }
}