package GameV2;

import java.util.Random;

public class ZBSword extends Sword {
    public ZBSword() {
        super("Zombie Boss Sword", "A powerful sword dropped by a fearsome zombie boss.", 3000, 15, 20, 2);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 30) { // 30% chance
            int healthDrain = (int) (target.getHealth() * 10 / 100); // Drains 10% of target's health.
            target.takeDamage(healthDrain);
        }
        if (random.nextInt(100) < 15) { // 15% chance
            target.addEffect(new FrozenEffect(3, "Frozen")); // Applies a 3-turn Frozen effect.
        }
        if (random.nextInt(100) < 15) { // 15% chance
            target.addEffect(new StunnedEffect(3, "Stunned")); // Applies a 3-turn Frozen effect.
        }
        if (random.nextInt(100) < 15) { // 15% chance
            target.addEffect(new PoisonEffect(3, "Poisoned")); // Applies a 3-turn Frozen effect.
        }
        if (random.nextInt(100) < 15) { // 15% chance
            target.addEffect(new BurningEffect(3, "Burning")); // Applies a 3-turn Frozen effect.
        }
        if (random.nextInt(100) < 15) { // 15% chance
            target.addEffect(new BleedingEffect(3, "Bleeding")); // Applies a 3-turn Frozen effect.
        }
    }
}