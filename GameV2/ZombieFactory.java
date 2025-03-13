package GameV2;

import java.util.Random;

public class ZombieFactory {

    private Random random = new Random();
    private SwordFactory swordFactory = new SwordFactory(); // Создание экземпляра SwordFactory
    private AxeFactory axeFactory = new AxeFactory(); // Создание экземпляра AxeFactory

    public Zombie createZombie(Player player) {
        int playerLevel = player.getLevel();
        int zombieLevel = playerLevel + random.nextInt(6) - 2; // Уровень зомби от playerLevel - 2 до playerLevel + 3

        // Убедитесь, что уровень зомби находится в разумных пределах (например, не отрицательный)
        zombieLevel = Math.max(1, zombieLevel); // Минимальный уровень 1

        // Случайный выбор типа зомби
        int zombieType = random.nextInt(4); // 0-3 для 4 типов зомби
        Zombie zombie;
        switch (zombieType) {
            case 0:
                zombie = new FastZombie("Fast Zombie", zombieLevel);
                break;
            case 1:
                zombie = new StrongZombie("Strong Zombie", zombieLevel);
                break;
            case 2:
                zombie = new ArmoredZombie("Armored Zombie", zombieLevel);
                break;
            case 3:
            default:
                zombie = new WeakZombie("Weak Zombie", zombieLevel);
                break;
        }

        // Определяем, будет ли зомби с оружием (40% шанс)
        if (random.nextInt(100) < 40) { // 40% шанс на появление с оружием
            if (random.nextBoolean()) { // 50% шанс на выбор меча или топора
                Sword sword = SwordFactory.createRandomSword(); // Создаем случайный меч
                zombie.equipWeapon(sword); // Экипируем меч
            } else {
                Axe axe = AxeFactory.createRandomAxe(); // Создаем топор (предполагается, что у вас есть метод без параметров)
                zombie.equipWeapon(axe); // Экипируем топор
            }
        }

        return zombie;
    }
}