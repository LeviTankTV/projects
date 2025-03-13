package GameV2;

import java.util.Random;

public class LightningSword extends Sword {
    public LightningSword() {
        super("Lightning Sword", "A sword crackling with electric energy.", 180, 28, 40, 2);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 40) { // 40% chance
            target.addEffect(new StunnedEffect(2, "Stunned"));
            System.out.println(target.getName() + " is shocked by lightning!");
        }
    }
}
