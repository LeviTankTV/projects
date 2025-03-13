package GameV2;

import java.util.Random;

public class GolemKingWand extends Wand {
    public GolemKingWand() {
        super("Golem King Wand", "A powerful wand imbued with the strength of a Golem King.", 3000, 80, 25, 5);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 15) { // 15% chance
            target.addEffect(new StunnedEffect(6, "Stunned")); // Applies a 6-turn Stun effect
            System.out.println(target.getName() + " is stunned!");
        }
    }
}