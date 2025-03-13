package GameV2;

import java.util.List;

class RockBomb extends AllHitWeapons {
    private static final int DAMAGE = 9999; // Урон, наносимый бомбой

    public RockBomb() {
        super("Rock Bomb", "A bomb made of rocks that causes area damage.", 1);
    }

    @Override
    protected int getDamage() {
        return DAMAGE; // Урон для Rock Bomb
    }
}