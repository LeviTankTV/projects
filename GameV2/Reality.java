package GameV2;

public class Reality extends Weapon {
    private static final int BASE_ATTACK_POWER = 100000;
    private static final int CRIT_CHANCE = 100; // 100% шанс крита
    private static final int CRIT_MULTIPLIER = 999999;

    public Reality() {
        super(
                "Reality",
                "Оружие самого Верховного Мага, способное искажать реальность. Содержит в себе частицу первородной тьмы.",
                999999, // value
                BASE_ATTACK_POWER,
                CRIT_CHANCE,
                CRIT_MULTIPLIER
        );
    }

    @Override
    public int calculateDamage(Entity attacker, Entity target) {
        return 0;
    }

    @Override
    public void applySpecialEffect(Entity target) {

    }
}