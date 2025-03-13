package GameV2;

import java.util.Random;

public class AllyFactory {
    private static final Ally[] FIRST_LEVEL_ALLIES = {
            new WeakGoblin("Weak Goblin", 1),
            new DelicateFairy("Delicate Fairy", 1),
            new ShadowyImp("Shadowy Imp", 1)
    };

    private static final Ally[] SECOND_LEVEL_ALLIES = {
            new AncientPirate("Ancient Pirate", 1),
            new ManaBeast("Mana Beast", 1),
            new WiseMage("Wise Mage", 1),
            new Druid("Druid", 1),
            new LightBard("Light Bard", 1),
            new NimbleRogue("Nimble Rogue", 1),
            new ElfRanger("Elf Ranger", 1),
            new DwarfWarrior("Dwarf Warrior", 1),
            new GnomeWizard("Gnome Wizard", 1),
            new VampireAlly("Vampire Ally", 1)
    };

    public static Ally createRandomAlly(Player player, Game game) {
        Random random = new Random();

        // Determine player's level and set ally level
        int playerLevel = player.getLevel(); // Assuming you have a method to get the player's level
        int allyLevel = playerLevel + random.nextInt(11) - 5; // ±5 levels

        allyLevel = Math.max(1, allyLevel);

        Ally selectedAlly;

        if (!game.isSecondGamePart()) {
            // Pick a random ally from the first level allies
            selectedAlly = FIRST_LEVEL_ALLIES[random.nextInt(FIRST_LEVEL_ALLIES.length)];
        } else {
            // Pick a random ally from the second level allies
            selectedAlly = SECOND_LEVEL_ALLIES[random.nextInt(SECOND_LEVEL_ALLIES.length)];
        }

        // Create a new instance of the selected ally with the determined level
        Ally newAlly = createAllyWithLevel(selectedAlly, allyLevel);

        // Equip the ally with the appropriate weapon
        equipWeaponToAlly(newAlly);

        return newAlly;
    }

    private static Ally createAllyWithLevel(Ally allyTemplate, int level) {
        // Create a new ally instance based on the type of the allyTemplate
        Ally newAlly = null;

        if (allyTemplate instanceof WeakGoblin) {
            newAlly = new WeakGoblin(allyTemplate.getName(), level);
        } else if (allyTemplate instanceof DelicateFairy) {
            newAlly = new DelicateFairy(allyTemplate.getName(), level);
        } else if (allyTemplate instanceof ShadowyImp) {
            newAlly = new ShadowyImp(allyTemplate.getName(), level);
        } else if (allyTemplate instanceof AncientPirate) {
            newAlly = new AncientPirate(allyTemplate.getName(), level);
        } else if (allyTemplate instanceof ManaBeast) {
            newAlly = new ManaBeast(allyTemplate.getName(), level);
        } else if (allyTemplate instanceof WiseMage) {
            newAlly = new WiseMage(allyTemplate.getName(), level);
        } else if (allyTemplate instanceof Druid) {
            newAlly = new Druid(allyTemplate.getName(), level);
        } else if (allyTemplate instanceof LightBard) {
            newAlly = new LightBard(allyTemplate.getName(), level);
        } else if (allyTemplate instanceof NimbleRogue) {
            newAlly = new NimbleRogue(allyTemplate.getName(), level);
        } else if (allyTemplate instanceof ElfRanger) {
            newAlly = new ElfRanger(allyTemplate.getName(), level);
        } else if (allyTemplate instanceof DwarfWarrior) {
            newAlly = new DwarfWarrior(allyTemplate.getName(), level);
        } else if (allyTemplate instanceof GnomeWizard) {
            newAlly = new GnomeWizard(allyTemplate.getName(), level);
        } else if (allyTemplate instanceof VampireAlly) {
            newAlly = new VampireAlly(allyTemplate.getName(), level);
        }

        return newAlly;
    }

    private static void equipWeaponToAlly(Ally ally) {
        if (ally instanceof AncientPirate) {
            // Assign a random sword from SwordFactory
            Sword sword = SwordFactory.createRandomSword(); // Assuming SwordFactory exists
            ally.equipWeapon(sword);
        } else if (ally instanceof ManaBeast) {
            // Mana Beast is weaponless, do nothing
        } else if (ally instanceof WiseMage) {
            // Assign a random staff from StaffFactory
            Staff staff = StaffFactory.createRandomStaff(); // Assuming StaffFactory exists
            ally.equipWeapon(staff);
        } else if (ally instanceof LightBard) {
            // Assign a random staff from StaffFactory
            Staff staff = StaffFactory.createRandomStaff(); // Assuming StaffFactory exists
            ally.equipWeapon(staff);
        } else if (ally instanceof NimbleRogue) {
            // Assign a random sword from SwordFactory
            Sword sword = SwordFactory.createRandomSword(); // Assuming SwordFactory exists
            ally.equipWeapon(sword);
        } else if (ally instanceof ElfRanger) {
            // Assign a random bow from BowFactory
            Bow bow = BowFactory.createRandomBow(); // Assuming BowFactory exists
            ally.equipWeapon(bow);
        } else if (ally instanceof DwarfWarrior) {
            // Assign a random axe from AxeFactory
            Axe axe = AxeFactory.createRandomAxe(); // Assuming AxeFactory exists
            ally.equipWeapon(axe);
        } else if (ally instanceof GnomeWizard) {
            // Assign a random staff from StaffFactory
            Staff staff = StaffFactory.createRandomStaff(); // Assuming StaffFactory exists
            ally.equipWeapon(staff);
        } else if (ally instanceof VampireAlly) {
            // Assign a random bow from BowFactory
            Bow bow = BowFactory.createRandomBow(); // Assuming BowFactory exists
            ally.equipWeapon(bow);
        } else if (ally instanceof Druid) {
            Staff staff = StaffFactory.createRandomStaff();
            ally.equipWeapon(staff);
        }
    }
}