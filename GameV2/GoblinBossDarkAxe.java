package GameV2;

import java.util.Random;

public class GoblinBossDarkAxe extends Axe {
    public GoblinBossDarkAxe() {
        super("Goblin Boss Dark Axe", "A cursed axe wielded by the Goblin Boss.", 3000, 75, 40, 3);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 30) { // 30% chance
            int healthDrain = (int) (target.getHealth() * 10 / 100); // Drains 10% of target's health.
            target.takeDamage(healthDrain);
            System.out.println(target.getName() + " lost " + healthDrain + " health due to the sword's power!");
        }
        if (random.nextInt(100) < 15) { // 15% chance
            target.addEffect(new FrozenEffect(3, "Frozen")); // Applies a 3-turn Frozen effect.
            System.out.println(target.getName() + " is frozen!");
        }
        if (random.nextInt(100) < 15) { // 15% chance
            target.addEffect(new StunnedEffect(3, "Stunned")); // Applies a 3-turn Frozen effect.
            System.out.println(target.getName() + " is stunned!");
        }
        if (random.nextInt(100) < 15) { // 15% chance
            target.addEffect(new PoisonEffect(3, "Poisoned")); // Applies a 3-turn Frozen effect.
            System.out.println(target.getName() + " is poisoned!");
        }
        if (random.nextInt(100) < 15) { // 15% chance
            target.addEffect(new BurningEffect(3, "Burning")); // Applies a 3-turn Frozen effect.
            System.out.println(target.getName() + " is burning!");
        }
        if (random.nextInt(100) < 15) { // 15% chance
            target.addEffect(new BleedingEffect(3, "Bleeding")); // Applies a 3-turn Frozen effect.
            System.out.println(target.getName() + " is bleeding!");
        }
    }
}