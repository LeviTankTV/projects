package GameV2;

import java.util.List;
import java.util.Random;

public class GhostGolem extends Entity {
    private Random random = new Random();
    private boolean enraged = false;

    public GhostGolem(String name, int level) {
        super(name, level, 180, 12, 20, 90);
    }

    @Override
    public void performAction(Game game, Room room) {
        List<Entity> targets = getTargets(game, room);
        Entity target = targets.get(random.nextInt(targets.size()));

        if (getHealth() < getMaxHealth() * 0.3 && !enraged) {
            System.out.println(getName() + " впадает в призрачное безумие!");
            setAttack(getAttack() * 1.5);
            enraged = true;
        }

        if (random.nextDouble() < 0.25) {
            System.out.println(getName() + " создает призрачную волну!");
            for (Entity t : targets) {
                t.takeDamage(getAttack() * 0.8);
                if (random.nextDouble() < 0.3) {
                    t.setStunned(true);
                    t.setTurnsStunned(2);
                }
            }
        } else {
            attack(target, getWeapon());
        }
    }

    @Override
    public void dropLoot(Game game) {

    }
}
