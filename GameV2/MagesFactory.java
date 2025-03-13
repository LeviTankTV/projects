package GameV2;

import java.util.Random;

public class MagesFactory {

    private Random random = new Random();
    private WandFactory wandFactory = new WandFactory(); // Create an instance of WandFactory

    public Mage createMage(Player player) {
        int playerLevel = player.getLevel();
        int mageLevel = playerLevel + 2 + random.nextInt(4); // Mage level from playerLevel + 2 to playerLevel + 5

        // Create a random type of mage
        int mageType = random.nextInt(5); // 0-4 for 5 types of mages (including MageHealer)
        Mage mage;
        switch (mageType) {
            case 0:
                mage = new FireMage("Fire Mage", mageLevel);
                break;
            case 1:
                mage = new IceMage("Ice Mage", mageLevel);
                break;
            case 2:
                mage = new UniqueMage("Unique Mage", mageLevel);
                break;
            case 3:
                mage = new EarthMage("Earth Mage", mageLevel);
                break;
            case 4:
            default:
                mage = new MageHealer("Healer Mage", mageLevel); // Create a MageHealer
                break;
        }

        // Equip the mage with a random wand
        Wand wand = WandFactory.createRandomWand();
        mage.equipWeapon(wand); // Equip the wand

        return mage;
    }
}