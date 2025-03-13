package GameV2;

public class WeakZombie extends Zombie {
    public WeakZombie(String name, int level) {
        super(name, level, 8, 35);
        setAttack(getAttack() - 3); // Уменьшение атаки
        setDefense(getDefense() - 1); // Уменьшение защиты
        setHealth(getMaxHealth()); // Устанавливаем текущее здоровье равным максимальному
    }
}