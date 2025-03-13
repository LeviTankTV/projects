package GameV2;

import java.util.Random;

public class GhostFactory {
    private Random random = new Random();

    public Entity createGhost(Player player) {
        int playerLevel = player.getLevel();
        int entityLevel = playerLevel + random.nextInt(6) - 2;
        entityLevel = Math.max(1, entityLevel);

        int entityType = random.nextInt(10); // 0-9 for 10 types of entities
        Entity entity;

        switch (entityType) {
            case 0:
                entity = new GhostGoblin("Ghost Goblin", entityLevel);
                break;
            case 1:
                entity = new GhostBandit("Ghost Bandit", entityLevel);
                break;
            case 2:
                entity = new GhostAssassin("Ghost Assassin", entityLevel);
                break;
            case 3:
                entity = new GhostKnight("Ghost Knight", entityLevel);
                break;
            case 4:
                entity = new GhostArcher("Ghost Archer", entityLevel);
                break;
            case 5:
                entity = new GhostGolem("Ghost Golem", entityLevel);
                break;
            case 6:
                entity = new GhostRogue("Ghost Rogue", entityLevel);
                break;
            case 7:
                entity = new UltimateZombie("Ultimate Zombie", entityLevel);
                break;
            case 8:
                entity = new DeadSkeleton("Dead Skeleton", entityLevel);
                break;
            default:
                entity = new GhostGoblin("Ghost Goblin", entityLevel);
                break;
        }

        Item weapon = GhostWeaponFactory.createRandomWeapon();
        if (weapon instanceof Weapon) {
            entity.equipWeapon((Weapon) weapon);
        }

        return entity;
    }
}