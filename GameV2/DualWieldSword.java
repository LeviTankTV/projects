package GameV2;

import java.util.Random;

public class DualWieldSword extends Sword {
    public DualWieldSword() {
        super("Dual-Wield Sword", "A sword designed for swift double strikes.", 150, 35, 60, 2);
    }

    @Override
    public int calculateDamage(Entity attacker, Entity target) {
        Random random = new Random();
        int damage = super.calculateDamage(attacker, target);
        if (random.nextInt(100) < 60) {
            damage += super.calculateDamage(attacker, target);
            System.out.println(attacker.getName() + " strikes again with a swift motion!");
        }
        return damage;
    }
}