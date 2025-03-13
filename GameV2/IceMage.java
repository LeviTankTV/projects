package GameV2;

import java.util.List;


public class IceMage extends Mage {
    public IceMage(String name, int level) {
        super(name, level);
    }

    @Override
    public void performAction(Game game, Room room) {
        List<Entity> targets = getTargets(game, room);
        if (!targets.isEmpty()) {
            Entity target = targets.get(random.nextInt(targets.size()));
            if (target != this) {
                System.out.println(getName() + " casts an ice shard on " + target.getName() + "!");
                attack(target, getWeapon());
                target.addEffect(new FrozenEffect(2, "Frozen for 2 turns!"));
            }
        }
    }
}