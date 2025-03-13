package GameV2;

import java.util.Random;

public class WoodenStaff extends Staff {
    public WoodenStaff() {
        super("Wooden Staff", "A simple wooden staff.", 75, 8, 15, 20);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        // Wooden Staff doesn't have a special effect
    }
}