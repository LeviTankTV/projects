package GameV2;

public class FastZombie extends Zombie {
    public FastZombie(String name, int level) {
        super(name, level, 40, 15); // Базовая атака 20, здоровье 15
        setAttack(getAttack() + 5); // Дополнительное увеличение атаки
        setDefense(getDefense() - 2); // Уменьшение защиты
        setHealth(getMaxHealth()); // Устанавливаем текущее здоровье равным максимальному
    }
}
