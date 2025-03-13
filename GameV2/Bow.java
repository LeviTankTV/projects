package GameV2;

import java.util.Random;

public abstract class Bow extends Weapon {

    public Bow(String name, String description, int value, int attackPower, int critChance, int critMultiplier) {
        super(name, description, value, attackPower, critChance, critMultiplier);
    }

    @Override
    public int calculateDamage(Entity attacker, Entity target) {
        Random random = new Random();
        int damage = getAttackPower();

        // Bows always deal full damage, regardless of range
        if (random.nextInt(100) < getCritChance()) {
            damage *= getCritMultiplier();
            System.out.println(attacker.getName() + " landed a critical hit with " + getName() + "!");
        }
        return damage;
    }

    @Override
    public void applySpecialEffect(Entity target) {
        // Bows don't have a special effect, but you could override this in specific subclasses
    }
}