package GameV2;

public class CutthroatBandit extends Bandit {
    public CutthroatBandit(String name, int level) {
        super(name, level);
    }

    @Override
    public void dropLoot(Game game) {
        super.dropLoot(game); // Call the base class dropLoot method
    }

    @Override
    public void performRobbery(Entity target) {
        if (random.nextInt(100) < 30) { // 30% chance for a sneak attack
            System.out.println(getName() + " performs a sneak attack on " + target.getName() + "!");
            super.performRobbery(target); // Call the base class performRobbery method
            // Increase amount stolen by 50%
            if (target instanceof Player) {
                Player playerTarget = (Player) target;
                int targetGold = playerTarget.getGold();
                int stolenAmount = Math.min((50 + random.nextInt(151)) * 3 / 2, targetGold); // 50% more
                playerTarget.setGold(targetGold - stolenAmount);
                System.out.println(getName() + " steals " + stolenAmount + " gold from " + playerTarget.getName() + "!");
            }
        } else {
            super.performRobbery(target); // Regular robbery attempt
        }
    }
}