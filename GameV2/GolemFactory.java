package GameV2;

import java.util.Random;

public class GolemFactory {
    private Random random = new Random();

    public Golem createGolem(Player player) {
        int playerLevel = player.getLevel();
        int golemLevel = playerLevel + random.nextInt(6) - 2; // Golem level from playerLevel - 2 to playerLevel + 3

        // Ensure golem level is within reasonable bounds (e.g., not negative)
        golemLevel = Math.max(1, golemLevel); // Minimum level 1

        // Randomly choose the type of golem
        int golemType = random.nextInt(4); // 0-3 for 4 types of golems
        Golem golem;
        switch (golemType) {
            case 0:
                golem = new StoneGolem("Stone Golem", golemLevel);
                break;
            case 1:
                golem = new IronGolem("Iron Golem", golemLevel);
                break;
            case 2:
                golem = new ClayGolem("Clay Golem", golemLevel);
                break;
            case 3:
            default:
                golem = new GoldGolem("Gold Golem", golemLevel);
                break;
        }

        return golem;
    }
}