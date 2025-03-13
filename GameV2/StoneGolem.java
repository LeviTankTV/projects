package GameV2;

import java.util.Random;

class StoneGolem extends Golem {
    public StoneGolem(String name, int level) {
        super(name, level, 8, 250);
        setHealth(getMaxHealth());
    }

    @Override
    public void dropLoot(Game game) {
        Player player = game.getPlayer();
        Random random = new Random();
        int lootChance = random.nextInt(100) + 1; // 1-100
        if (lootChance <= 30) { // 30% chance to drop loot
            int goldAmount = random.nextInt(15) + 15;
            player.setGold(player.getGold() + goldAmount);
            System.out.println(getName() + " dropped " + goldAmount + " gold!");
        }
    }
}
