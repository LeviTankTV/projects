package GameV2;

import java.util.Random;
import java.util.*;

public abstract class Entity {
    Random random = new Random();

    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE
    private String name;
    private int level;
    private double health;
    private double maxHealth;
    private double attack;
    private double defense;
    private int experience;
    private boolean isDead = false;
    private double evasion;
    private boolean strengthIncreased;
    private double regenerationAmount;
    private boolean regenerating;
    private int turnsStrengthIncreased;
    private int turnsRegenerating;

    private List<Effect> effects = new ArrayList<>();

    private boolean burning = false;
    private int turnsBurning = 0;

    private boolean isNecrotic = false;
    private int turnsNecrotic = 0;

    private boolean bleeding = false;
    private int turnsBleeding = 0;

    private boolean stunned = false;
    private int turnsStunned = 0;

    private boolean weakened = false;
    private int turnsWeakened = 0;

    private boolean frozen = false;
    private int turnsFrozen = 0;

    private boolean poisoned = false;
    private int turnsPoisoned = 0;

    private boolean marked = false;
    private double damageMultiplier = 1.0;
    private int turnsMarked = 0;

    private boolean isNightmarish; // Indicates if the entity is under the nightmarish effect
    private int turnsNightmarish;

    // For IncreasedStrengthEffect
    public boolean isStrengthIncreased() {
        return strengthIncreased;
    }

    public void setStrengthIncreased(boolean strengthIncreased) {
        this.strengthIncreased = strengthIncreased;
    }

    public int getTurnsStrengthIncreased() {
        return turnsStrengthIncreased;
    }

    public void setTurnsStrengthIncreased(int turnsStrengthIncreased) {
        this.turnsStrengthIncreased = turnsStrengthIncreased;
    }

    // For RegenerationEffect
    public boolean isRegenerating() {
        return regenerating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegenerating(boolean regenerating) {
        this.regenerating = regenerating;
    }

    public double getRegenerationAmount() {
        return regenerationAmount;
    }

    public void setRegenerationAmount(double regenerationAmount) {
        this.regenerationAmount = regenerationAmount;
    }

    public int getTurnsRegenerating() {
        return turnsRegenerating;
    }

    public void setTurnsRegenerating(int turnsRegenerating) {
        this.turnsRegenerating = turnsRegenerating;
    }


    private boolean vampirism = false; // Track vampirism state
    private int vampirismTurns = 0; // Track how many turns vampirism lasts

    private int vampirismLevel = 0;

    private int gold;

    private boolean isAlly;

    private Weapon weapon;

    public boolean isDead() {
        return isDead;
    }

    public void equipWeapon(Weapon weapon) {
        this.weapon = weapon; // Предполагается, что у вас есть поле weapon в классе Entity
    }

    // Setter for isDead
    public void setDead(boolean dead) {
        this.isDead = dead;
    }

    public boolean isAlly() {
        return isAlly;
    }

    public void setIsAlly(boolean isAlly) {
        this.isAlly = isAlly;
    }

    // isAlive method (derived from isDead)
    public boolean isAlive() {
        return !isDead;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
        if (this.gold < 0) {
            this.gold = 0;
        }
    }

    // Constructor with customizable health and max health
    public Entity(String name, int level, double baseHealth, double healthBonus, double baseAttack, double baseDefense) {
        this.name = name;
        this.level = level;
        this.maxHealth = baseHealth + (healthBonus * (level - 1)); // Calculate max health based on level
        this.health = maxHealth; // Start with full health
        this.attack = baseAttack + (level * 2); // Base attack + level bonus
        this.defense = baseDefense + level; // Base defense + level bonus
        this.isDead = false;
        this.evasion = 0.0;
        this.vampirismLevel = 0;
    }

    public Entity() {
    }

    public boolean isNecrotic() {
        return isNecrotic;
    }

    public void setNecrotic(boolean necrotic) {
        isNecrotic = necrotic;
    }

    public int getTurnsNecrotic() {
        return turnsNecrotic;
    }

    public void setTurnsNecrotic(int turns) {
        this.turnsNecrotic = turns;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getLevel() {
        return level;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
        // Optional: Ensure health doesn't exceed maxHealth or drop below 0
        if (this.health > maxHealth) {
            this.health = maxHealth;
        } else if (this.health < 0) {
            this.health = 0;
        }
    }
    public double getMaxHealth() {
        return maxHealth;
    }


    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setTurnsPoisoned(int turnsPoisoned) {
        this.turnsPoisoned = turnsPoisoned;
    }

    public boolean isPoisoned() {
        return poisoned;
    }

    public void setPoisoned(boolean poisoned) {
        this.poisoned = poisoned;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public void setDamageMultiplier(double multiplier) {
        this.damageMultiplier = multiplier;
    }

    public void setTurnsMarked(int turns) {
        this.turnsMarked = turns;
    }

    public boolean isMarked() {
        return marked;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    public int getTurnsMarked() {
        return turnsMarked;
    }

    // Burning
    public boolean isBurning() {
        return burning;
    }

    public void setBurning(boolean burning) {
        this.burning = burning;
    }

    public int getTurnsBurning() {
        return turnsBurning;
    }

    public void setTurnsBurning(int turnsBurning) {
        this.turnsBurning = turnsBurning;
    }
    public void reduceTurnsBurning() {
        turnsBurning--;
    }

    // Bleeding
    public boolean isBleeding() {
        return bleeding;
    }

    public void setBleeding(boolean bleeding) {
        this.bleeding = bleeding;
    }

    public int getTurnsBleeding() {
        return turnsBleeding;
    }

    public void setTurnsBleeding(int turnsBleeding) {
        this.turnsBleeding = turnsBleeding;
    }

    public void reduceTurnsBleeding() {
        turnsBleeding--;
    }

    // Stunned
    public boolean isStunned() {
        return stunned;
    }

    public void setStunned(boolean stunned) {
        this.stunned = stunned;
    }

    public int getTurnsStunned() {
        return turnsStunned;
    }

    public void setTurnsStunned(int turnsStunned) {
        this.turnsStunned = turnsStunned;
    }

    public void reduceTurnsStunned() {
        turnsStunned--;
    }

    // Weakened
    public boolean isWeakened() {
        return weakened;
    }

    public boolean isNightmarish() {
        return isNightmarish;
    }

    public void setNightmarish(boolean nightmarish) {
        isNightmarish = nightmarish;
    }

    public int getTurnsNightmarish() {
        return turnsNightmarish;
    }

    public void setTurnsNightmarish(int turnsNightmarish) {
        this.turnsNightmarish = turnsNightmarish;
    }

    public void setWeakened(boolean weakened) {
        this.weakened = weakened;
    }

    public int getTurnsWeakened() {
        return turnsWeakened;
    }

    public void setTurnsWeakened(int turnsWeakened) {
        this.turnsWeakened = turnsWeakened;
    }

    public void reduceTurnsWeakened() {
        turnsWeakened--;
    }

    // Frozen
    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public int getTurnsFrozen() {
        return turnsFrozen;
    }

    public void setTurnsFrozen(int turnsFrozen) {
        this.turnsFrozen = turnsFrozen;
    }



    // Vampirism
    public boolean hasVampirism() {
        return vampirism;
    }

    public void setVampirism(boolean vampirism) {
        this.vampirism = vampirism;
    }


    public int getVampirismLevel() {
        return vampirismLevel;
    }

    public void setVampirismLevel(int vampirismLevel) {
        this.vampirismLevel = vampirismLevel;
    }

    public void gainExperience(int amount) {
        experience += amount;
        System.out.println(GREEN_BOLD_BRIGHT + name + " получил " + BLUE_BOLD_BRIGHT + amount + RESET + GREEN_BOLD_BRIGHT +" опыта! 🎉" + RESET);
    }

    public int getTurnsPoisoned() {
        return turnsPoisoned;
    }



    public void rip(Game game) {
        Player player = game.getPlayer();
        int experienceGained = level * 10; // Example: 10 experience per level
        player.gainExperience(experienceGained);
        dropLoot(game); // Call the loot drop method
    }

    public boolean isEvasive() {
        return this.evasion > 0; // Returns true if evasion is greater than 0
    }

    public double getEvasion() {
        return evasion;
    }

    public void setEvasion(double evasion) {
        this.evasion = evasion;
    }

    public double calculateDamage(Weapon weapon) {
        double baseDamage;
        if (weapon != null) {
            baseDamage = this.getAttack() + weapon.calculateDamage(this, null);
        } else {
            baseDamage = calculateUnarmedDamage();
        }

        // Apply status effect modifiers
        if (isWeakened()) {
            baseDamage *= 0.7;
        }

        // Use the specific strength increase value from the effect
        if (isStrengthIncreased()) {
            // The strength increase is already applied to attack stat
            baseDamage = this.getAttack();
        }

        double randomFactor = 0.9 + (random.nextDouble() * 0.2);
        baseDamage *= randomFactor;

        return Math.max(0, baseDamage);
    }

    public boolean takeDamage(double damage) {
        if (isEvasive()) {
            double dodgeChance = this.evasion * 100;
            if (random.nextDouble() * 100 < dodgeChance) {
                System.out.println(CYAN_BOLD_BRIGHT + "🛡️ " + getName() + " ловко уклоняется от атаки!" + RESET);
                return true;
            }
        }

        double totalDamage = damage;

        if (isMarked()) {
            totalDamage *= damageMultiplier;
        }

        health = Math.max(0, health - totalDamage);

        if (health <= 0) {
            isDead = true;
            System.out.println(RED_BOLD_BRIGHT + "💀 " + getName() + " пал в бою!" + RESET);
        } else {
            System.out.println(YELLOW_BOLD_BRIGHT + "💥 " + getName() + " получает " + String.format("%.2f", totalDamage) + " урона!" + RESET);
        }

        return false;
    }

    public void attack(Entity target, Weapon weapon) {
        System.out.println(WHITE_BOLD_BRIGHT + "════════════════════════════════════════" + RESET);

        if (isStunned() || isNightmarish() || isFrozen()) {
            String reason = isStunned() ? "оглушен" :
                    isNightmarish() ? "в плену кошмаров" : "заморожен";
            System.out.println(PURPLE_BOLD_BRIGHT + "😵 " + getName() + " " + reason + " и не может атаковать!" + RESET);
            return;
        }

        double damage = calculateDamage(weapon);
        boolean dodged = target.takeDamage(damage);

        if (!dodged) {
            System.out.println(GREEN_BOLD_BRIGHT + "⚔️ " + getName() + " атакует " + target.getName() +
                    " используя " + (weapon != null ? weapon.getName() : "голые руки") +
                    " и наносит " + String.format("%.2f", damage) + " урона!" + RESET);

            if (vampirismLevel > 0) {
                double healAmount = damage * (vampirismLevel * 0.1);
                heal(healAmount);
                System.out.println(RED_BOLD_BRIGHT + "🩸 " + getName() + " восстанавливает " + String.format("%.2f", healAmount) +
                        " здоровья благодаря вампиризму!" + RESET);
            }

            if (weapon != null) {
                weapon.applySpecialEffect(target);
            }
        }

        System.out.println(WHITE_BOLD_BRIGHT + "════════════════════════════════════════" + RESET);
    }

    public void heal(double amount) {
        double oldHealth = health;
        health = Math.min(health + amount, maxHealth);
        double actualHeal = health - oldHealth;
        if (actualHeal > 0) {
            System.out.println(GREEN_BOLD_BRIGHT + "❤️ " + getName() + " восстанавливает " + String.format("%.2f", actualHeal) + " здоровья." + RESET);
        }
    }

    public void regenerateHealth() {
        if (health < maxHealth) {
            heal(1);
        }
    }

    public void dealDamage(Entity target, double damage) {
        if (target == null || !target.isAlive()) {
            return;
        }
        boolean dodged = target.takeDamage(damage);

        System.out.println(WHITE_BOLD_BRIGHT + "════════════════════════════════════════" + RESET);

        if (!dodged) {
            System.out.println(GREEN_BOLD_BRIGHT + "⚔️ " + getName() + " атакует " + target.getName() + " используя " +
                    (getWeapon() != null ? getWeapon().getName() : "голые руки") +
                    " и наносит " + String.format("%.2f", damage) + " урона!" + RESET);

            if (vampirismLevel > 0) {
                double healAmount = damage * (vampirismLevel * 0.1);
                heal(healAmount);
                System.out.println(RED_BOLD_BRIGHT + "🩸 " + getName() + " восстанавливает " + String.format("%.2f", healAmount) + " здоровья благодаря вампиризму!" + RESET);
            }

            if (getWeapon() != null) {
                getWeapon().applySpecialEffect(target);
            }
        } else {
            System.out.println(CYAN_BOLD_BRIGHT + "🛡️ " + target.getName() + " ловко уклоняется от атаки!" + RESET);
        }

        System.out.println(WHITE_BOLD_BRIGHT + "════════════════════════════════════════" + RESET);
    }
    public final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // ЗЕЛЕНЫЙ

    public void showInfo() {
        // Заголовок
        System.out.println(WHITE_BOLD_BRIGHT + "━━━ Информация о персонаже ━━━" + RESET);

        // Основные характеристики
        System.out.printf("%s%-12s%s%s%n",
                WHITE_BOLD_BRIGHT, "Имя:", RESET + RED_BOLD_BRIGHT, getName());

        System.out.printf("%s%-12s%s%d%n",
                WHITE_BOLD_BRIGHT, "Уровень:", BLUE_BOLD_BRIGHT, getLevel());

        System.out.printf("%s%-12s%s%.1f/%.1f%n",
                WHITE_BOLD_BRIGHT, "Здоровье:", RED_BOLD_BRIGHT, getHealth(), getMaxHealth());

        System.out.printf("%s%-12s%s%.1f%n",
                WHITE_BOLD_BRIGHT, "Атака:", RED_BOLD_BRIGHT, getAttack());

        System.out.printf("%s%-12s%s%.1f%n",
                WHITE_BOLD_BRIGHT, "Защита:", BLUE_BOLD_BRIGHT, getDefense());

        // Оружие
        String weaponName = (getWeapon() != null ? getWeapon().getName() : "Без оружия");
        System.out.printf("%s%-12s%s%s%n",
                WHITE_BOLD_BRIGHT, "Оружие:", PURPLE_BOLD_BRIGHT, weaponName);

        // Эффекты
        if (isBurning() || isBleeding() || isStunned() || isWeakened() || isFrozen() || isNightmarish() || isMarked()) {
            System.out.println("\n" + WHITE_BOLD_BRIGHT + "━━━ Активные эффекты ━━━" + RESET);

            if (isBurning()) {
                System.out.printf("  %s• Горение %s(%d ходов)%n",
                        RED_BOLD_BRIGHT, WHITE_BOLD_BRIGHT, turnsBurning);
            }
            if (isBleeding()) {
                System.out.printf("  %s• Кровотечение %s(%d ходов)%n",
                        RED_BOLD_BRIGHT, WHITE_BOLD_BRIGHT, turnsBleeding);
            }
            if (isStunned()) {
                System.out.printf("  %s• Оглушение %s(%d ходов)%n",
                        YELLOW_BOLD_BRIGHT, WHITE_BOLD_BRIGHT, turnsStunned);
            }
            if (isWeakened()) {
                System.out.printf("  %s• Ослабление %s(%d ходов)%n",
                        PURPLE_BOLD_BRIGHT, WHITE_BOLD_BRIGHT, turnsWeakened);
            }
            if (isFrozen()) {
                System.out.printf("  %s• Заморозка %s(%d ходов)%n",
                        CYAN_BOLD_BRIGHT, WHITE_BOLD_BRIGHT, turnsFrozen);
            }
            if (isNightmarish()) {
                System.out.printf("  %s• Кошмар %s(%d ходов)%n",
                        PURPLE_BOLD_BRIGHT, WHITE_BOLD_BRIGHT, turnsNightmarish);
            }
            if (isMarked()) {
                System.out.printf("  %s• Метка %s(%d ходов)%n",
                        YELLOW_BOLD_BRIGHT, WHITE_BOLD_BRIGHT, turnsMarked);
            }
        }
        System.out.println(WHITE_BOLD_BRIGHT + "━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
    }

    private static final String RESET = "\u001B[0m";
    public void addEffect(Effect effect) {
        effects.add(effect);
        effect.apply(this); // Apply the effect when it's added
        System.out.println(this.getName() + " is affected by " + effect.getDescription()); // Add this for clarity
    }

    public void updateEffects() {
        for (int i = 0; i < effects.size(); i++) {
            Effect effect = effects.get(i);
            effect.update(this);
            if (effect.duration <= 0) {
                effects.remove(i);
                i--; // Adjust index after removing
            }
        }
    }

    public void applyEffectDamage() {
        double totalEffectDamage = 0;
        StringBuilder damageMessage = new StringBuilder();

        // Проверяем каждый эффект и формируем сообщение об уроне
        if (isBurning()) {
            totalEffectDamage += 20;
            damageMessage.append("\n- 🔥 20 урона от Горения");
        }
        if (isBleeding()) {
            totalEffectDamage += 15;
            damageMessage.append("\n- 🩸 15 урона от Кровотечения");
        }
        if (isPoisoned()) {
            totalEffectDamage += 12;
            damageMessage.append("\n- ☠️ 12 урона от Яда");
        }
        if (isNightmarish()) {
            totalEffectDamage += 10;
            damageMessage.append("\n- 😱 10 урона от Кошмара");
        }

        if (totalEffectDamage > 0) {
            System.out.println(RED_BOLD_BRIGHT + getName() + " получает " + totalEffectDamage +
                    " суммарного урона от эффектов:" + RESET +
                    RED_BOLD_BRIGHT + damageMessage.toString() + RESET);
            takeDamage(totalEffectDamage);
        }
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void performAction(Game game, Room room) {
        Random random = new Random();
        List<Entity> targets = getTargets(game, room);

        // Ensure there are targets to select from
        if (!targets.isEmpty()) {
            int targetIndex = random.nextInt(targets.size());
            Entity target = targets.get(targetIndex);

            // Check if the target is not the same as the current entity
            if (target != this) {
                Weapon weapon = getWeapon(); // Get the entity's weapon
                attack(target, weapon); // Perform the attack with the weapon
            }
        }
    }


    public List<Entity> getTargets(Game game, Room room) {
        Player player = game.getPlayer();
        List<Entity> targets = new ArrayList<>(player.getAllies()); // Предполагается, что у вас есть метод, который возвращает союзников из комнаты
        if (targets.isEmpty()) {
            targets.add(player); // Если нет союзников, добавьте игрока
        } else {
            targets.add(player); // Включите игрока в список целей
        }
        return targets;
    }

    private double calculateUnarmedDamage() {
        // Base unarmed damage (you can adjust this value)
        double baseDamage = 1; // You can set this to a different value if needed

        // Calculate total damage based on the entity's attack attribute
        double totalDamage = baseDamage + this.attack; // Adding the entity's attack to the base damage

        // Ensure total damage is not negative
        return Math.max(totalDamage, 0);
    }

    public abstract void dropLoot(Game game);
}
