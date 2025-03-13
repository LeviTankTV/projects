package GameV2;

import java.util.Random;

public class BossFactory {

    public Boss createBoss(String type, Player player) {
        Random random = new Random();
        Boss boss = null;
        String name = generateBossName(type);

        // Determine the boss level based on the player's level
        int playerLevel = player.getLevel();
        int bossLevel = playerLevel + random.nextInt(3); // Boss level is player's level + a random value (0-2)

        switch (type.toLowerCase()) {
            case "zombieboss":
                boss = new ZombieBoss(name, bossLevel);
                boss.equipWeapon(new ZBSword()); // Equip with ZBSword
                break;
            case "skeletonboss":
                boss = new SkeletonBoss(name, bossLevel);
                boss.equipWeapon(new SkeletonBossUltraBow()); // Equip with SkeletonBossUltraBow
                break;
            case "golemboss":
                boss = new GolemBoss(name, bossLevel);
                boss.equipWeapon(new GolemKingWand()); // Equip with GolemKingWand
                break;
            case "goblinboss":
                boss = new GoblinBoss(name, bossLevel);
                boss.equipWeapon(new GoblinBossDarkAxe()); // Equip with GoblinBossDarkAxe
                break;
            case "banditboss":
                boss = new BanditBoss(name, bossLevel);
                boss.equipWeapon(new BanditBossMegaStaff()); // Equip with BanditBossMegaStaff
                break;
            default:
                System.out.println("Unknown boss type: " + type);
                break;
        }

        return boss; // Return the created boss
    }

    private String generateBossName(String type) {
        String[] names;

        switch (type.toLowerCase()) {
            case "zombieboss":
                names = new String[]{"Undead King", "Zombie Overlord", "Rotting Tyrant"};
                break;
            case "skeletonboss":
                names = new String[]{"Bone Collector", "Skeleton Warlord", "Grim Reaper"};
                break;
            case "golemboss":
                names = new String[]{"Golem King", "Stone Guardian", "Rock Titan"};
                break;
            case "goblinboss":
                names = new String[]{"Goblin Chief", "Warlord of Goblins", "Greedy Goblin"};
                break;
            case "banditboss":
                names = new String[]{"Bandit King", "Master Thief", "Rogue Lord"};
                break;
            default:
                names = new String[]{"Unknown Boss"};
                break;
        }

        Random random = new Random();
        return names[random.nextInt(names.length)]; // Select a random name from the array
    }
}