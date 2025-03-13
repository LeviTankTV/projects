package GameV2;

import java.util.Random;

public abstract class Gloves extends Weapon {
    public Gloves(String name, String description, int value, int attackPower, int critChance, int critMultiplier) {
        super(name, description, value, attackPower, critChance, critMultiplier);
    }

    @Override
    public int calculateDamage(Entity attacker, Entity target) {
        Random random = new Random();
        int damage = getAttackPower();
        if (random.nextInt(100) < getCritChance()) {
            damage *= getCritMultiplier();
            System.out.println(attacker.getName() + " landed a critical hit with " + getName() + "!");
        }
        return damage;
    }

    @Override
    public void applySpecialEffect(Entity target) {
        // Swords generally don't have a special effect, but you could override this in specific subclasses
    }
}