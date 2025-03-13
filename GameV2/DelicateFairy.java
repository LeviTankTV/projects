package GameV2;

import java.util.List;
import java.util.Random;

public class DelicateFairy extends Ally {
    private static final int BASE_HEAL_AMOUNT = 25;
    private static final int HEAL_AMOUNT_VARIABILITY = 55;

    private static final String GREEN_BOLD = "\u001B[1;32m";
    private static final String CYAN_BOLD = "\u001B[1;36m";
    private static final String YELLOW_BOLD = "\u001B[1;33m";
    private static final String RESET = "\u001B[0m";

    public DelicateFairy(String name, int level) {
        super(name, level, 140, 1, 1, 3, "Fairy");
    }

    @Override
    public void performAllyAction(Room room, Player player) {
        List<Ally> allies = player.getAllies();
        Random random = new Random();
        int action = random.nextInt(3);

        switch (action) {
            case 0: // Heal a random ally
                if (!allies.isEmpty()) {
                    Entity target = allies.get(random.nextInt(allies.size()));
                    if (target.getHealth() < target.getMaxHealth()) {
                        int healAmount = calculateHealAmount();
                        target.heal(healAmount);
                        System.out.println(GREEN_BOLD + "🌿 " + getName() + " лечит " + target.getName() + " на " + healAmount + " здоровья." + RESET);
                    }
                }
                break;

            case 1: // Apply RegenEffect to a random ally
                if (!allies.isEmpty()) {
                    Entity target = allies.get(random.nextInt(allies.size()));
                    target.addEffect(new RegenerationEffect(2, "Regeneration", 18));
                    System.out.println(YELLOW_BOLD + "🌟 " + getName() + " накладывает эффект Регенирации на " + target.getName() + "!" + RESET);
                }
                break;

            case 2: // Heal the player
                int healAmount = calculateHealAmount();
                player.heal(healAmount);
                System.out.println(GREEN_BOLD + "🌸 " + getName() + " лечит игрока на " + healAmount + " HP!" + RESET);
                break;

            default:
                break;
        }
    }

    private int calculateHealAmount() {
        Random random = new Random();
        int variability = random.nextInt(HEAL_AMOUNT_VARIABILITY + 1);
        return BASE_HEAL_AMOUNT + variability;
    }
}