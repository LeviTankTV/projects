package GameV2;

import java.util.List;
import java.util.Random;

public class MageSkeleton extends Skeleton {
    public MageSkeleton(String name, int level) {
        super(name, level, 15, 120); // Higher attack for a mage
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
                // Mage skeleton casts a spell instead of a regular attack
                castSpell(target);
            }
        }
    }

    public void castSpell(Entity target) {
        System.out.println(getName() + " casts a spell on " + target.getName() + "!");
        target.addEffect(new MarkedEffect(2, "Marked", 2.5));
    }
}