package GameV2;

public class StrongZombie extends Zombie {
    public StrongZombie(String name, int level) {
        super(name, level, 12, 75); // Базовая атака 18, здоровье 120
        setAttack(getAttack() + 3); // Дополнительное увеличение атаки
        setDefense(getDefense() + 2); // Увеличение защиты
        setHealth(getMaxHealth()); // Устанавливаем текущее здоровье равным максимальному
    }
}