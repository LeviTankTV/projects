package GameV2;

import java.util.Random;

public class SummonerZombie extends Zombie {
    private ZombieFactory zombieFactory;

    public SummonerZombie(String name, int level) {
        super(name, level, 26, 125); // Adjust base attack and health as needed
        this.zombieFactory = new ZombieFactory(); // Initialize the ZombieFactory
    }

    @Override
    public void performAction(Game game, Room room) {
        summonZombie(game, room);
    }

    private void summonZombie(Game game, Room room) {
        System.out.println(getName() + " summons a new zombie to join the fight!");
        Zombie newZombie = zombieFactory.createZombie(game.getPlayer());
        room.addEnemy(newZombie); // Assuming Room has a method to add enemies
        System.out.println("A " + newZombie.getName() + " has joined the battle!");
    }
}
