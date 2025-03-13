package GameV2;

import java.util.Random;

public class AlmightyWand extends Wand {
    public AlmightyWand() {
        super("Almighty Wand", "A wand that channels the power of all elements.", 240, 30, 20, 4);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        int effectChoice = random.nextInt(3);
        switch (effectChoice) {
            case 0:
                target.addEffect(new FrozenEffect(1, "Frozen"));
                System.out.println(target.getName() + " is frozen!");
                break;
            case 1:
                target.addEffect(new BurningEffect(3, "Burning"));
                System.out.println(target.getName() + " is burning!");
                break;
            case 2:
                target.addEffect(new MarkedEffect(2, "Marked", 2));
                System.out.println(target.getName() + " is Marked!");
                break;
        }
    }
}
