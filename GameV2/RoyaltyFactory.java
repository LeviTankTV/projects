package GameV2;

import java.util.Random;

public class RoyaltyFactory {
    private Random random = new Random();
    private SwordFactory swordFactory = new SwordFactory();
    private BowFactory bowFactory = new BowFactory();
    private WandFactory wandFactory = new WandFactory();

    public Royalty createRoyalty(Player player) {
        int playerLevel = player.getLevel();
        int royaltyLevel = playerLevel + random.nextInt(5) - 2; // Уровень +-2 от уровня игрока

        // Убедимся, что уровень не меньше 1
        royaltyLevel = Math.max(1, royaltyLevel);

        int royaltyType = random.nextInt(3); // Случайный выбор типа (0-2)
        Royalty royalty;

        switch (royaltyType) {
            case 0: // RoyalKnight
                royalty = new RoyalKnight("Royal Knight", royaltyLevel);
                Sword sword = swordFactory.createRandomSword();
                royalty.equipWeapon(sword);
                break;

            case 1: // RoyalArcher
                royalty = new RoyalArcher("Royal Archer", royaltyLevel);
                Bow bow = bowFactory.createRandomBow();
                royalty.equipWeapon(bow);
                break;

            case 2: // RoyalAlchemist
                royalty = new RoyalAlchemist("Royal Alchemist", royaltyLevel);
                Wand wand = wandFactory.createRandomWand();
                royalty.equipWeapon(wand);
                break;

            default:
                throw new IllegalArgumentException("Invalid royalty type");
        }

        // Можно добавить дополнительную логику для экипировки брони или других предметов

        return royalty;
    }
}