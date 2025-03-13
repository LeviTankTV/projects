package GameV2;

public class AmuletOfIncreasedManaRegeneration extends Amulet {
    private static final int MANA_REGENERATION_BONUS = 1; // Фиксированный бонус к восстановлению маны
    private static final String NAME = "Amulet of Increased Mana Regeneration"; // Фиксированное имя
    private static final String DESCRIPTION = "Increases mana regeneration by 1."; // Фиксированное описание

    public AmuletOfIncreasedManaRegeneration() {
        super(NAME, DESCRIPTION); // Передаем фиксированные имя и описание в суперкласс
    }

    @Override
    public void applyPassiveEffect(Player player) {
        player.setManaRegenLevel(player.getManaRegenLevel() + MANA_REGENERATION_BONUS); // Увеличиваем уровень восстановления маны
        System.out.println("Mana regeneration level increased by " + MANA_REGENERATION_BONUS + " for " + player.getName() + ".");
    }

    @Override
    public void applyEffect(Entity target) {
        // Реализуйте любые дополнительные эффекты, если это необходимо
    }
}