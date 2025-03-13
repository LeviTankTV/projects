package GameV2;

import java.util.Random;

public class WeaponFactory {
    private static final Random RANDOM = new Random();

    private static final Class<? extends Weapon>[] ALL_WEAPON_TYPES = new Class[]{
            // Swords
            GreatSword.class, LongSword.class, MagicSword.class, ShortSword.class,
            LightningSword.class, HolySword.class, KnightsSword.class, DualWieldSword.class,
            BladeOfTheAncients.class, ShadowSword.class,

            // Bows
            ShortBow.class, WeakenBow.class, PoisonBow.class, MagicBow.class,
            IceBow.class, CrossBow.class, BleedingBow.class, LongBow.class, FireBow.class,

            // Gloves
            FlameGloves.class, ShadowGloves.class, ThunderGloves.class,
            TitansGloves.class, AssassinsGloves.class, GlovesOfTheBerserker.class,

            // Axes
            BattleAxe.class, DoubleBladedAxe.class, HandAxe.class, GuardianAxe.class,
            CeremonialAxe.class, DwarvenAxe.class, HeavyDutyAxe.class,

            // Wands
            IceWand.class, FireWand.class, LightningWand.class,

            // Staffs
            WoodenStaff.class, EnchantedStaff.class, ElementalStaff.class,
            LightningStaff.class, NatureStaff.class, ShadowStaff.class
    };

    public static Weapon createRandomWeapon() {
        int index = RANDOM.nextInt(ALL_WEAPON_TYPES.length);
        try {
            return ALL_WEAPON_TYPES[index].getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Weapon createWeaponByClassName(String className) {
        try {
            Class<?> weaponClass = Class.forName("GameV2." + className);
            return createWeaponByClass(weaponClass.asSubclass(Weapon.class));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Weapon createWeaponByClass(Class<? extends Weapon> weaponClass) {
        try {
            return weaponClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}