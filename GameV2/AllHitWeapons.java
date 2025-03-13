package GameV2;

import java.util.List;

abstract class AllHitWeapons extends ThrowableWeapon {
    public AllHitWeapons(String name, String description, int value) {
        super(name, description, value);
    }

    @Override
    public void applyEffect(Room room) {
        List<Entity> enemies = room.getEnemies(); // Получаем всех врагов в комнате
        if (!enemies.isEmpty()) {
            for (Entity enemy : enemies) {
                if (enemy.isAlive()) { // Проверяем, жив ли враг
                    System.out.println(getName() + " hits " + enemy.getName() + " for " + getDamage() + " damage!");
                    enemy.takeDamage(getDamage()); // Наносим урон
                }
            }
        } else {
            System.out.println("No enemies to hit with " + getName() + ".");
        }
    }

    protected abstract int getDamage();
}