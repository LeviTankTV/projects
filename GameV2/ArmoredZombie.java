package GameV2;

public class ArmoredZombie extends Zombie {
    public ArmoredZombie(String name, int level) {
        super(name, level, 5, 200);
        setAttack(getAttack() - 1); // Уменьшение атаки
        setDefense(getDefense() + 5); // Увеличение защиты
        setHealth(getMaxHealth()); // Устанавливаем текущее здоровье равным максимальному
    }
}
