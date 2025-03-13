package GameV2;

import java.util.Random;

public class ElementalStaff extends Staff {
    public ElementalStaff() {
        super("Elemental Staff", "A staff imbued with the power of the elements.", 320, 12, 10, 2);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        int element = random.nextInt(3); // 0: Fire, 1: Ice, 2: Lightning

        if (element == 0) {
            target.addEffect(new BurningEffect(2, "Burning"));
            System.out.println(target.getName() + " is burning!");
        } else if (element == 1) {
            target.addEffect(new FrozenEffect(2, "Frozen"));
            System.out.println(target.getName() + " is frozen!");
        } else {
            target.addEffect(new StunnedEffect(2, "Stunned"));
            System.out.println(target.getName() + " is stunned!");
        }
    }
}