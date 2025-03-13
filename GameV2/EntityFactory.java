package GameV2;

import java.util.Random;

public class EntityFactory {
    private Random random = new Random();

    // Фабрики для создания существ
    private ZombieFactory zombieFactory = new ZombieFactory();
    private SecondZombieFactory secondZombieFactory = new SecondZombieFactory();
    private SkeletonFactory skeletonFactory = new SkeletonFactory();
    private SecondSkeletonFactory secondSkeletonFactory = new SecondSkeletonFactory();
    private GoblinFactory goblinFactory = new GoblinFactory();
    private GolemFactory golemFactory = new GolemFactory();
    private RoyaltyFactory royaltyFactory = new RoyaltyFactory();
    private MagesFactory magesFactory = new MagesFactory();
    private BanditFactory banditFactory = new BanditFactory();
    private AssassinFactory assassinFactory = new AssassinFactory();
    private GhostFactory GhostFactory = new GhostFactory();

    public Entity createRandomEntity(Player player) {
        int entityType = random.nextInt(11); // 0-10 для всех типов существ

        switch (entityType) {
            case 0: return zombieFactory.createZombie(player);
            case 1: return secondZombieFactory.createZombie(player);
            case 2: return skeletonFactory.createSkeleton(player);
            case 3: return secondSkeletonFactory.createSkeleton(player);
            case 4: return goblinFactory.createGoblin(player);
            case 5: return golemFactory.createGolem(player);
            case 6: return royaltyFactory.createRoyalty(player);
            case 7: return magesFactory.createMage(player);
            case 8: return banditFactory.createBandit(player);
            case 9: return assassinFactory.createAssassin(player);
            case 10: return GhostFactory.createGhost(player);
            default: throw new IllegalStateException("Unexpected entity type");
        }
    }
}