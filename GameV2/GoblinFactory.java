package GameV2;

import java.util.Random;

public class GoblinFactory {
    private Random random = new Random();
    private StaffFactory staffFactory = new StaffFactory(); // Create an instance of StaffFactory

    public Goblin createGoblin(Player player) {
        int playerLevel = player.getLevel();
        int goblinLevel = playerLevel + random.nextInt(6) - 2; // Goblin level from playerLevel - 2 to playerLevel + 3

        // Ensure goblin level is within reasonable bounds (e.g., not negative)
        goblinLevel = Math.max(1, goblinLevel); // Minimum level 1

        // Randomly choose the type of goblin
        int goblinType = random.nextInt(4); // 0-3 for 4 types of goblins
        Goblin goblin;
        switch (goblinType) {
            case 0:
                goblin = new ShamanGoblin("Shaman Goblin", goblinLevel);
                break;
            case 1:
                goblin = new SneakyGoblin("Sneaky Goblin", goblinLevel);
                break;
            case 2:
                goblin = new StandardGoblin("Standard Goblin", goblinLevel);
                break;
            case 3:
            default:
                goblin = new BerserkerGoblin("Berserker Goblin", goblinLevel);
                break;
        }

        // Determine if the goblin will have a staff (40% chance)
        if (random.nextInt(100) < 40) { // 40% chance to have a staff
            Staff staff = staffFactory.createRandomStaff(); // Create a random staff
            goblin.equipWeapon(staff); // Equip the staff
        }

        return goblin;
    }
}