package GameV2;

import java.util.Random;

public class NecroStaff extends Staff {
    private Random random = new Random();

    public NecroStaff() {
        super("Necro Staff", "A dark staff pulsing with necrotic energy.", 200, 40, 35, 1);
    }

    @Override
    public void applySpecialEffect(Entity target) {
        int roll = random.nextInt(100);

        if (roll < 1) { // 1% chance for Death Mark
            System.out.println(target.getName() + " is marked for death by the Necro Staff!");
            target.addEffect(new DeathMarkEffect(2, "Death Mark"));

        } else if (roll < 5) { // 4% chance for Stunned, Weakened, or Nightmarish
            applyRareEffect(target);

        } else if (roll < 20) { // 15% chance for Frozen, Burning, or Bleeding
            applyCommonEffect(target);

        } else if (roll < 35) { // 15% chance for basic Necro Effect
            System.out.println(target.getName() + " is cursed by the Necro Staff's dark power!");
            target.addEffect(new NecroEffect(3, "Necrotic Curse"));
        }
    }

    private void applyRareEffect(Entity target) {
        int effectRoll = random.nextInt(3);
        switch (effectRoll) {
            case 0:
                System.out.println(target.getName() + " is stunned by the Necro Staff's power!");
                target.addEffect(new StunnedEffect(1, "Necro Stun"));
                break;
            case 1:
                System.out.println(target.getName() + " is weakened by the Necro Staff's dark energy!");
                target.addEffect(new WeakenedEffect(2, "Necro Weakness"));
                break;
            case 2:
                System.out.println(target.getName() + " is haunted by nightmares from the Necro Staff!");
                target.addEffect(new NightmarishEffect(2, "Necro Nightmares"));
                break;
        }
    }

    private void applyCommonEffect(Entity target) {
        int effectRoll = random.nextInt(3);
        switch (effectRoll) {
            case 0:
                System.out.println(target.getName() + " is frozen by the Necro Staff's cold touch!");
                target.addEffect(new FrozenEffect(2, "Necro Frost"));
                break;
            case 1:
                System.out.println(target.getName() + " is set ablaze by the Necro Staff's dark fire!");
                target.addEffect(new BurningEffect(2, "Necro Flames"));
                break;
            case 2:
                System.out.println(target.getName() + " is bleeding from the Necro Staff's spectral wounds!");
                target.addEffect(new BleedingEffect(2, "Necro Bleed"));
                break;
        }
    }
}