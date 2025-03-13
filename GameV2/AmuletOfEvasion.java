package GameV2;

public class AmuletOfEvasion extends Amulet {
    private static final double EVASION_VALUE = 0.15; // Fixed value for evasion
    private static final String NAME = "Amulet of Evasion"; // Фиксированное имя
    private static final String DESCRIPTION = "Increases evasion by 15%."; // Фиксированное описание

    public AmuletOfEvasion() {
        super(NAME, DESCRIPTION);
    }

    @Override
    public void applyPassiveEffect(Player player) {
        player.setEvasion(EVASION_VALUE); // Set evasion to a fixed value
        System.out.println("Evasion set to " + EVASION_VALUE + " for " + player.getName() + ".");
    }

    @Override
    public void applyEffect(Entity target) {
        // Implement any additional effect if needed
    }
}