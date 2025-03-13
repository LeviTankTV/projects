package GameV2;

import java.util.Random;

public class AssassinFactory {
    private Random random = new Random();
    private SwordFactory swordFactory = new SwordFactory(); // Instance of SwordFactory

    public ShadowAssassin createAssassin(Player player) {
        int playerLevel = player.getLevel();
        int assassinLevel = playerLevel + random.nextInt(6) - 2; // Assassin level from playerLevel - 2 to playerLevel + 3

        // Ensure the assassin level is within reasonable bounds (e.g., not negative)
        assassinLevel = Math.max(1, assassinLevel); // Minimum level 1

        // Create a ShadowAssassin with a random name and level
        ShadowAssassin assassin = new ShadowAssassin(generateAssassinName(), assassinLevel);

        // Equip the assassin with a random sword from SwordFactory
        Sword sword = swordFactory.createRandomSword(); // Get a random sword
        assassin.equipWeapon(sword); // Equip the sword to the assassin

        return assassin;
    }

    private String generateAssassinName() {
        String[] names = {"Nightblade", "Silent Shadow", "Whisper", "Dark Phantom", "Shadowstrike"};
        return names[random.nextInt(names.length)];
    }
}