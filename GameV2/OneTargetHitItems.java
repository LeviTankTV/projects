package GameV2;

import java.util.List;

abstract class OneTargetHitItems extends ThrowableWeapon {
    public OneTargetHitItems(String name, String description, int value) {
        super(name, description, value);
    }

    @Override
    public void applyEffect(Room room) {
        Entity target = getRandomEnemy(room); // Получаем случайного врага
        if (target != null) {
            System.out.println(getName() + " hits " + target.getName() + " for " + getDamage() + " damage!");
            target.takeDamage(getDamage()); // Наносим урон
        } else {
            System.out.println("No enemies to hit with " + getName() + ".");
        }
    }

    public void applyEffect(Room room, Entity target) {
        if (target != null) {
            System.out.println(getName() + " hits " + target.getName() + " for " + getDamage() + " damage!");
            target.takeDamage(getDamage());
        } else {
            System.out.println("No valid target for " + getName() + ".");
        }
    }

    protected abstract int getDamage();
}