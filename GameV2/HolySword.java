package GameV2;

import java.util.Random;

public class HolySword extends Sword {
    public HolySword() {
        super("Holy Sword", "A sword that radiates purity and light.", 700, 16, 10, 2);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 60) { // 60% chance
            target.takeDamage(125); // Deals additional damage to undead or evil entities
            System.out.println(target.getName() + " feels the wrath of holy light!");
        }
    }
}