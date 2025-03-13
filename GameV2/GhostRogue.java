package GameV2;

import java.util.List;
import java.util.Random;

public class GhostRogue extends Entity {
    private Random random = new Random();
    private boolean isConcealed = false;

    public GhostRogue(String name, int level) {
        super(name, level, 180, 5, 20, 50);
        setEvasion(0.25); // 25% chance to evade attacks
    }

    @Override
    public void performAction(Game game, Room room) {
        List<Entity> targets = getTargets(game, room);
        Entity target = targets.get(random.nextInt(targets.size()));

        if (!isConcealed && random.nextDouble() < 0.3) {
            System.out.println(getName() + " скрывается в тенях!");
            isConcealed = true;
            setEvasion(0.75); // Increased evasion while concealed
        } else if (isConcealed) {
            System.out.println(getName() + " наносит внезапный удар из теней!");
            double originalDamage = calculateDamage(getWeapon());
            double buffedDamage = originalDamage * 2; // 70% of original damage
            dealDamage(target, buffedDamage);
            if (random.nextDouble() < 0.4) {
                target.setBleeding(true);
                target.setTurnsBleeding(3);
            }
            isConcealed = false;
            setEvasion(0.25); // Reset evasion
        } else {
            attack(target, getWeapon());
        }
    }

    @Override
    public boolean takeDamage(double damage) {
        if (isConcealed) {
            isConcealed = false;
            setEvasion(0.25);
            System.out.println(getName() + " теряет маскировку!");
        }
        return super.takeDamage(damage);
    }

    @Override
    public void dropLoot(Game game) {
    }
}