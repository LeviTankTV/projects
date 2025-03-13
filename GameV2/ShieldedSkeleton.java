package GameV2;

import java.util.List;
import java.util.Random;

public class ShieldedSkeleton extends Skeleton {
    public ShieldedSkeleton(String name, int level) {
        super(name, level, 0, 200); // Higher health for tanking
        setDefense(getDefense() + 12); // Increased defense due to shield
        setHealth(getMaxHealth());

    }
}