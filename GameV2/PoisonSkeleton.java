package GameV2;

import java.util.List;
import java.util.Random;

public class PoisonSkeleton extends Skeleton {
    public PoisonSkeleton(String name, int level) {
        super(name, level, 10, 75); // Moderate attack and health
        setHealth(getMaxHealth());

    }

    @Override
    public void performAction(Game game, Room room) {
        List<Entity> targets = getTargets(game, room);
        Random random = new Random();

        if (!targets.isEmpty()) {
            int targetIndex = random.nextInt(targets.size());
            Entity target = targets.get(targetIndex);

            if (target != this) {
                // Poison skeleton uses a poison attack
                poisonAttack(target);
            }
        }
    }

    public void poisonAttack(Entity target) {
        System.out.println(getName() + " poisons " + target.getName() + "!");
        target.addEffect(new PoisonEffect(2, "Poisoned"));
    }
}