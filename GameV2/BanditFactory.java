package GameV2;

import java.util.Random;

public class BanditFactory {
    private Random random = new Random();
    private GlovesFactory gloveFactory = new GlovesFactory(); // Instance of GloveFactory

    public Bandit createBandit(Player player) {
        int playerLevel = player.getLevel();
        int banditLevel = playerLevel + random.nextInt(6) - 2; // Bandit level from playerLevel - 2 to playerLevel + 3

        // Ensure the bandit level is within reasonable bounds (e.g., not negative)
        banditLevel = Math.max(1, banditLevel); // Minimum level 1

        // Randomly choose bandit type
        int banditType = random.nextInt(3); // 0-2 for 3 types of bandits
        Bandit bandit;
        switch (banditType) {
            case 0:
                bandit = new CutthroatBandit(generateBanditName(), banditLevel);
                break;
            case 1:
                bandit = new HighwaymanBandit(generateBanditName(), banditLevel);
                break;
            case 2:
            default:
                bandit = new ThiefBandit(generateBanditName(), banditLevel);
                break;
        }

        // Equip random gloves from GloveFactory
        Weapon randomGlove = GlovesFactory.createRandomGloves(); // Get a random glove
        bandit.equipWeapon(randomGlove); // Assuming Bandit has a setGlove method

        return bandit;
    }

    private String generateBanditName() {
        String[] names = {"Blackjack", "Cutthroat", "Shadow", "Rogue", "Silvers", "Ravager"};
        return names[random.nextInt(names.length)];
    }
}