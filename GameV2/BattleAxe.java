package GameV2;

import java.util.Random;
public class BattleAxe extends Axe {
    public BattleAxe() {
        super("Battle Axe", "A heavy, two-handed axe.", 240, 25, 15, 2);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        Random random = new Random();
        if (random.nextInt(100) < 75) {
            target.addEffect(new MarkedEffect(2, "Marked", 2));
            System.out.println(target.getName() + " is marked!");
        }
    }
}