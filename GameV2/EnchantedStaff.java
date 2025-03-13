package GameV2;

import java.util.Random;

public class EnchantedStaff extends Staff {
    public EnchantedStaff() {
        super("Enchanted Staff", "A staff infused with magical power.", 180, 1, 1, 1);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 65) {
            target.addEffect(new MarkedEffect(3, "Marked", 5));
            System.out.println(target.getName() + " is marked!");
        }
    }
}