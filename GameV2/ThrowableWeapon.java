package GameV2;

import java.util.List;
import java.util.Random;

public abstract class ThrowableWeapon extends Item {
    private static final Random random = new Random(); // Экземпляр Random для выбора случайных целей

    public ThrowableWeapon(String name, String description, int value) {
        super(name, description, value);
    }

    // Абстрактный метод для применения эффекта
    public abstract void applyEffect(Room room);

    // Метод для получения случайного врага из комнаты
    protected Entity getRandomEnemy(Room room) {
        List<Entity> enemies = room.getEnemies();
        if (enemies.isEmpty()) {
            return null; // Нет врагов для атаки
        }
        int index = random.nextInt(enemies.size()); // Получаем случайный индекс
        return enemies.get(index); // Возвращаем случайного врага
    }
}