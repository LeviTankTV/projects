package GameV2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameSavedState implements Serializable {
    private static final long serialVersionUID = 1L;

    private String playerName;
    private double playerHealth;
    private int playerMana;
    private int playerLevel;
    private double playerAttack;
    private double playerDefense;
    private int playerGold;
    private int playerExperience;
    private WeaponState playerWeapon;
    private List<AllyState> alliesState;

    public GameSavedState(Player player) {
        // Save player state
        this.playerName = player.getName();
        this.playerHealth = player.getHealth();
        this.playerMana = player.getMana();
        this.playerLevel = player.getLevel();
        this.playerAttack = player.getAttack();
        this.playerDefense = player.getDefense();
        this.playerGold = player.getGold();
        this.playerExperience = player.getExperience();

        // Save player's weapon state
        if (player.getWeapon() != null) {
            this.playerWeapon = new WeaponState(player.getWeapon());
        }

        // Save allies' state
        this.alliesState = new ArrayList<>();
        for (Ally ally : player.getAllies()) {
            alliesState.add(new AllyState(ally));
        }
    }

    public void restore(Player player) {
        // Restore player state
        player.setName(playerName);
        player.setHealth(playerHealth);
        player.setMana(playerMana);
        player.setLevel(playerLevel);
        player.setAttack(playerAttack);
        player.setDefense(playerDefense);
        player.setGold(playerGold);
        player.setExperience(playerExperience);

        // Restore player's weapon
        if (playerWeapon != null) {
            player.setWeapon(playerWeapon.createWeapon());
        }

        // Restore allies
        player.getAllies().clear();
        for (AllyState allyState : alliesState) {
            Ally ally = allyState.createAlly();
            player.getAllies().add(ally);
        }
    }

    private static class WeaponState implements Serializable {
        private static final long serialVersionUID = 1L;

        private String weaponClassName;
        private String name;
        private String description;
        private int value;
        private int attackPower;
        private int critChance;
        private int critMultiplier;

        public WeaponState(Weapon weapon) {
            this.weaponClassName = weapon.getClass().getSimpleName();
            this.name = weapon.getName();
            this.description = weapon.getDescription();
            this.value = weapon.getValue();
            this.attackPower = weapon.getAttackPower();
            this.critChance = weapon.getCritChance();
            this.critMultiplier = weapon.getCritMultiplier();
        }

        public Weapon createWeapon() {
            try {
                Class<?> weaponClass = Class.forName("GameV2." + weaponClassName);
                return (Weapon) weaponClass.getDeclaredConstructor(String.class, String.class, int.class, int.class, int.class, int.class)
                        .newInstance(name, description, value, attackPower, critChance, critMultiplier);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private static class AllyState implements Serializable {
        private static final long serialVersionUID = 1L;

        private String allyClassName;
        private String name;
        private int level;
        private double health;
        private double baseHealth;
        private double healthBonus;
        private double attack;
        private double defense;
        private String role;
        private WeaponState weapon;

        public AllyState(Ally ally) {
            this.allyClassName = ally.getClass().getSimpleName();
            this.name = ally.getName();
            this.level = ally.getLevel();
            this.health = ally.getHealth();
            this.attack = ally.getAttack();
            this.defense = ally.getDefense();
            this.role = ally.getRole();
            if (ally.getWeapon() != null) {
                this.weapon = new WeaponState(ally.getWeapon());
            }
        }

        public Ally createAlly() {
            try {
                Class<?> allyClass = Class.forName("GameV2." + allyClassName);
                Ally ally = (Ally) allyClass.getDeclaredConstructor(String.class, int.class, double.class, double.class, double.class, double.class, String.class)
                        .newInstance(name, level, baseHealth, healthBonus, attack, defense, role);
                ally.setHealth(health);
                if (weapon != null) {
                    ally.setWeapon(weapon.createWeapon());
                }
                return ally;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}