package GameV2;

import java.util.Random;

public class SecondZombieFactory {

    private Random random = new Random();
    private SwordFactory SwordFactory; // Assuming you have a SwordFactory class

    public SecondZombieFactory() {
        this.SwordFactory = new SwordFactory(); // Initialize the SwordFactory
    }

    public Zombie createZombie(Player player) {
        int playerLevel = player.getLevel();
        int zombieLevel = playerLevel + random.nextInt(6) - 2; // Level range: playerLevel - 2 to playerLevel + 3

        // Ensure that the zombie level is at least 1
        zombieLevel = Math.max(1, zombieLevel);

        // Randomly choose one of the five new zombie types
        int zombieType = random.nextInt(5); // 0-4 for 5 types of zombies
        Zombie zombie;
        switch (zombieType) {
            case 0:
                zombie = new StrengthZombie("Strength Zombie", zombieLevel);
                break;
            case 1:
                zombie = new RegenerationZombie("Regeneration Zombie", zombieLevel);
                break;
            case 2:
                zombie = new BerserkerZombie("Berserker Zombie", zombieLevel);
                break;
            case 3:
                zombie = new SummonerZombie("Summoner Zombie", zombieLevel);
                break;
            case 4:
            default:
                zombie = new CursedZombie("Cursed Zombie", zombieLevel);
                break;
        }

        // Create a sword from the SwordFactory and equip it to the zombie
        Weapon sword = SwordFactory.createRandomSword(); // Assuming createSword() returns a Weapon object
        zombie.equipWeapon(sword); // Equip the sword to the zombie

        return zombie;
    }
}
