package GameV2;

import java.util.Random;

public class BiggestItemFactory {
    private Random random = new Random();

    // Фабрики для создания специфических предметов
    private SwordFactory swordFactory = new SwordFactory();
    private BowFactory bowFactory = new BowFactory();
    private WandFactory wandFactory = new WandFactory();
    private StaffFactory staffFactory = new StaffFactory();
    private AxeFactory axeFactory = new AxeFactory();
    private GlovesFactory glovesFactory = new GlovesFactory();
    private FruitFactory fruitFactory = new FruitFactory();
    private BerryFactory berryFactory = new BerryFactory();

    public Item createRandomItem() {
        int itemType = random.nextInt(34); // 0-33 для всех типов предметов

        switch (itemType) {
            case 0: return new GoldenKey();
            case 1: return new LockPick();
            case 2: return new Torch();
            case 3: return new Ruby();
            case 4: return new Emerald();
            case 5: return new Diamond();
            case 6: return new Sapphire();
            case 7: return swordFactory.createRandomSword();
            case 8: return bowFactory.createRandomBow();
            case 9: return wandFactory.createRandomWand();
            case 10: return staffFactory.createRandomStaff();
            case 11: return axeFactory.createRandomAxe();
            case 12: return glovesFactory.createRandomGloves();
            case 13: return new AmuletOfEvasion();
            case 14: return new AmuletOfIncreasedManaRegeneration();
            case 15: return new AmuletOfLuck();
            case 16: return fruitFactory.createRandomFruit();
            case 17: return berryFactory.createRandomBerry();
            case 18: return new MinorHealingPotion();
            case 19: return new GreaterHealingPotion();
            case 20: return new MinorManaPotion();
            case 21: return new GreaterManaPotion();
            case 22: return new MinorExperiencePotion();
            case 23: return new GreaterExperiencePotion();
            case 24: return new GrandExperiencePotion();
            case 25: return new ExplosionDagger();
            case 26: return new LightningScroll();
            case 27: return new FireballScroll();
            case 28: return new UltraScroll();
            case 29: return new IceDagger();
            case 30: return new Dagger();
            case 31: return new IceScroll();
            case 32: return new WeakenScroll();
            case 33: return new MarkScroll();
            case 34: return new ManaLevelIncreaseBook();
            case 35: return new RitualBook();
            default: throw new IllegalStateException("Unexpected item type");
        }
    }
}