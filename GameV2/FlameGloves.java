package GameV2;

public class FlameGloves extends Gloves {
    public FlameGloves() {
        super("Flame Gloves", "Gloves that ignite with fiery energy, burning enemies on contact.", 75, 6, 20, 3);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        System.out.println(target.getName() + " is set ablaze by " + getName() + "!");
        target.addEffect(new BurningEffect(2, "Burning"));
    }
}