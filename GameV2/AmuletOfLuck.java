package GameV2;

public class AmuletOfLuck extends Amulet {
    private static final double LUCK_VALUE = 0.15; // Фиксированное значение удачи
    private static final String NAME = "Amulet of Luck"; // Фиксированное имя
    private static final String DESCRIPTION = "Increases luck by 15%."; // Фиксированное описание

    public AmuletOfLuck() {
        super(NAME, DESCRIPTION); // Передаем фиксированные имя и описание
    }

    @Override
    public void applyPassiveEffect(Player player) {
        player.setLuck(LUCK_VALUE); // Устанавливаем удачу на фиксированное значение
        System.out.println("Luck set to " + LUCK_VALUE + " for " + player.getName() + ".");
    }

    @Override
    public void applyEffect(Entity target) {
        // Реализуйте любые дополнительные эффекты, если это необходимо
    }
}