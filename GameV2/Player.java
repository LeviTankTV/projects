package GameV2;

import java.io.*;
import java.util.Scanner;
import java.util.Random;
import java.util.*;

public class Player extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;
    private int mana; // Add mana for player-specific actions
    private int maxMana;
    private int manaRegenLevel;
    private int gold;
    private double luck;
    private double evasion;
    private List<Ally> allies;
    private Inventory inventory;
    private boolean learnedHeal;
    private boolean learnedFireball;
    private boolean learnedIceSpike;
    private boolean learnedEarthQuake;
    private boolean learnedShadowBolt;
    private boolean learnedArcaneMissile;
    private boolean learnedBloodMagic;
    private boolean hasAttackedRightHand = false;
    private boolean hasAttackedLeftHand = false;
    private static final int BASE_EXP_FOR_LEVEL_UP = 100; // Base experience needed for level up
    private static final int EXP_MULTIPLIER = 50; // Additional multiplier for each level
    private static final String RESET = "\033[0m";
    private static final String WHITE_BOLD_BRIGHT = "\033[1;97m";
    private static final String RED_BOLD_BRIGHT = "\033[1;91m";
    private static final String CYAN_BOLD_BRIGHT = "\033[1;96m";
    private static final String GREEN_BOLD_BRIGHT = "\033[1;92m";
    private static final String BLUE_BOLD_BRIGHT = "\033[1;94m";
    private static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";


    public Player() {
        super("Default", 1, 275, 75, 45, 10);
        initializeDefaultValues();
    }

    public Player(String name, int level) {
        super(name, level, 275, 75, 45, 10);
        initializeDefaultValues();
    }



    private void initializeDefaultValues() {
        this.maxMana = 100;
        this.mana = maxMana;
        this.manaRegenLevel = 2;
        this.gold = 200;
        this.allies = new ArrayList<>();
        this.inventory = new Inventory();
        this.luck = 0.0;
        this.evasion = 0;
        this.learnedHeal = false;
        this.learnedFireball = false;
        this.learnedIceSpike = false;
        this.learnedEarthQuake = false;
        this.learnedShadowBolt = false;
        this.learnedArcaneMissile = false;
        this.learnedBloodMagic = false;
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

    public double getLuck() {
        return luck;
    }

    public void gainExperience(int amount) {
        super.gainExperience(amount);
        checkLevelUp();
    }

    public void increaseMaxHealth(int amount) {
        setMaxHealth(getMaxHealth() + amount);
        setHealth(getMaxHealth()); // Heal to full health
    }

    private void checkLevelUp() {
        while (getExperience() >= getExperienceToLevelUp()) {
            levelUp();
        }
    }

    private int getExperienceToLevelUp() {
        return BASE_EXP_FOR_LEVEL_UP + (EXP_MULTIPLIER * (getLevel() - 1));
    }

    private void levelUp() {
        setLevel(getLevel() + 1);
        int remainingExp = getExperience() - getExperienceToLevelUp();
        setExperience(Math.max(0, remainingExp));

        increaseMaxHealth(45);
        increaseMaxMana(5);
        increaseAttack(2);

        setHealth(getMaxHealth());

// Заменяем точку на запятую в числах
        String healthStr = String.format("%.1f", getMaxHealth()).replace('.', ',');
        String attackStr = String.format("%.1f", getAttack()).replace('.', ',');

        System.out.println(CYAN_BOLD_BRIGHT + "╔══════════════ НОВЫЙ УРОВЕНЬ ═════════════════╗" + RESET);
        System.out.println(CYAN_BOLD_BRIGHT + String.format("║ %-45s║", getName() + " достиг уровня " + getLevel() + "!") + RESET);
        System.out.println(GREEN_BOLD_BRIGHT + String.format("║ %-45s║", "Максимальное здоровье увеличено до: " + healthStr) + RESET);
        System.out.println(BLUE_BOLD_BRIGHT + String.format("║ %-45s║", "Максимальная мана увеличена до: " + getMaxMana()) + RESET);
        System.out.println(RED_BOLD_BRIGHT + String.format("║ %-45s║", "Сила атаки увеличена до: " + attackStr) + RESET);
        System.out.println(CYAN_BOLD_BRIGHT + "╚══════════════════════════════════════════════╝" + RESET);
    }
    public void increaseEvasion(double amount) {
        setEvasion(getEvasion() + amount); // Increase evasion
    }

    private void increaseMaxMana(int amount) {
        // Implement logic to increase max mana
        maxMana += amount; // Assuming maxMana is a class variable
    }

    public void clearAllies() {
        allies.clear();
    }

    public void setLuck(double luck) {
        this.luck = luck;
    }

    private void increaseAttack(double amount) {
        // Implement logic to increase attack
        setAttack(getAttack() + amount); // Assuming setAttack is a method in Entity
    }

    public boolean isLearnedArcaneMissile() {
        return learnedArcaneMissile;
    }

    public boolean isLearnedEarthQuake() {
        return learnedEarthQuake;
    }

    public void setLearnedEarthQuake(boolean learnedEarthQuake) {
        this.learnedEarthQuake = learnedEarthQuake;
    }

    public boolean isLearnedBloodMagic() {
        return learnedBloodMagic;
    }

    public void setLearnedBloodMagic(boolean learnedBloodMagic) {
        this.learnedBloodMagic = learnedBloodMagic;
    }

    public boolean isLearnedIceSpike() {
        return learnedIceSpike;
    }

    public void setLearnedIceSpike(boolean learnedIceSpike) {
        this.learnedIceSpike = learnedIceSpike;
    }

    public boolean isLearnedFireball() {
        return learnedFireball;
    }

    public void setLearnedFireball(boolean learnedFireball) {
        this.learnedFireball = learnedFireball;
    }

    public boolean isLearnedHeal() {
        return learnedHeal;
    }

    public void setLearnedHeal(boolean learnedHeal) {
        this.learnedHeal = learnedHeal;
    }

    public void setLearnedArcaneMissile(boolean learnedArcaneMissile) {
        this.learnedArcaneMissile = learnedArcaneMissile;
    }

    public boolean isLearnedShadowBolt() {
        return learnedShadowBolt;
    }

    public void setLearnedShadowBolt(boolean learnedShadowBolt) {
        this.learnedShadowBolt = learnedShadowBolt;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void addAlly(Ally ally) {
        allies.add(ally);
        ally.setIsAlly(true); // Set the ally flag in the entity
    }

    public void increaseManaRegenLevel() {
        if (manaRegenLevel < 10) {
            manaRegenLevel++;
            System.out.println(GREEN_BOLD_BRIGHT + "Вы повысили уровень регенерации маны с " + (manaRegenLevel-1) + " до " + manaRegenLevel + "!" + RESET);
        } else if (manaRegenLevel == 10){
            System.out.println(YELLOW_BOLD_BRIGHT + "У вас максимальный уровень регенерации маны!" + RESET);
        }
    }

    public void removePassiveEffect(Amulet amulet) {
        if (amulet instanceof AmuletOfEvasion) {
            setEvasion(0); // Сброс уклонения на значение по умолчанию
            System.out.println(YELLOW_BOLD_BRIGHT + "Уклонение сброшено для " + getName() + "." + RESET);
        } else if (amulet instanceof AmuletOfLuck) {
            this.luck = 0.0; // Сброс удачи на значение по умолчанию
            System.out.println(YELLOW_BOLD_BRIGHT + "Удача сброшена для " + getName() + "." + RESET);
        } else if (amulet instanceof AmuletOfIncreasedManaRegeneration) {
            this.manaRegenLevel -= 1; // Уменьшение уровня регенерации маны
            System.out.println(YELLOW_BOLD_BRIGHT + "Уровень регенерации маны уменьшен для " + getName() + "." + RESET);
        }
        // Обработка других типов амулетов по мере необходимости
    }


    // Getters and setters for mana
    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = Math.min(mana, maxMana); // Ensure mana doesn't exceed maxMana
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getManaRegenLevel() {
        return manaRegenLevel;
    }

    public void setManaRegenLevel(int manaRegenLevel) {
        this.manaRegenLevel = manaRegenLevel;
    }

    // Method to regenerate mana
    public void regenerateMana() {
        if (mana < maxMana) {
            mana += 5 * manaRegenLevel;
            mana = Math.min(mana, maxMana);
        }
    }

    public void increaseVampirismLevel() {
        if (getVampirismLevel() < 10) {
            setVampirismLevel(getVampirismLevel() + 1);
            System.out.println(GREEN_BOLD_BRIGHT + "Вы повысили уровень вампиризма до " + getVampirismLevel() + "!" + RESET);
        } else if(getVampirismLevel() == 10) {
            System.out.println(YELLOW_BOLD_BRIGHT + "У вас уже максимальный уровень вампиризма!" + RESET);
        }
    }

    // Method for spending mana
    public void spendMana(int amount) {
        if (mana >= amount) {
            mana -= amount;
        } else {
            System.out.println(RED_BOLD_BRIGHT + "Недостаточно маны!" + RESET);
        }
    }

    public void castHeal() {
        Scanner scanner = new Scanner(System.in);

        System.out.println(WHITE_BOLD_BRIGHT + "Выберите цель для исцеления:" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "1. Исцелить себя" + RESET);

        for (int i = 0; i < allies.size(); i++) {
            System.out.println(WHITE_BOLD_BRIGHT + (i + 2) + ". Исцелить " + allies.get(i).getName() + RESET);
        }

        System.out.print(WHITE_BOLD_BRIGHT + "Введите ваш выбор (1-" + (allies.size() + 1) + "): " + RESET);
        int choice = scanner.nextInt();
        scanner.nextLine();

        Entity target;

        if (choice == 1) {
            target = this;
        } else if (choice >= 2 && choice <= allies.size() + 1) {
            target = allies.get(choice - 2);
        } else {
            System.out.println(RED_BOLD_BRIGHT + "Неверный выбор! Исцеление отменено." + RESET);
            return;
        }

        double levelMultiplier = 1 + (getLevel() * 0.35);
        int healAmount = (int)((25 + (5 * (getLevel() - 1))) * levelMultiplier);

        if (mana >= 50) {
            spendMana(50);
            target.heal(healAmount);
            System.out.println(GREEN_BOLD_BRIGHT + getName() + " использует Исцеление, восстанавливая " + healAmount + " здоровья " + target.getName() + RESET);
        } else {
            System.out.println(RED_BOLD_BRIGHT + "Недостаточно маны для использования Исцеления!" + RESET);
        }
    }

    public void castFireball(Entity target) {
        if (mana >= 75) {
            spendMana(75);
            double levelMultiplier = 1 + (getLevel() * 0.19);
            double damage = (getAttack() * 2) * levelMultiplier;
            target.takeDamage(damage);
            System.out.println(RED_BOLD_BRIGHT + getName() + " использует Огненный шар, нанося " + damage + " урона " + target.getName() + RESET);
            target.addEffect(new BurningEffect(2, "Горение"));
        } else {
            System.out.println(RED_BOLD_BRIGHT + "Недостаточно маны для использования Огненного шара!" + RESET);
        }
    }

    // Method to set the player's allies
    public void setAllies(List<Ally> allies) {
        this.allies = allies;
    }

    // Method to get the player's allies
    public List<Ally> getAllies() {
        return allies;
    }

    // Method for showing entity information
    @Override
    public void showInfo() {
        super.showInfo(); // Show basic info from Entity
        System.out.println(WHITE_BOLD_BRIGHT + "║" + RESET + " " + WHITE_BOLD_BRIGHT + "Золото: " + YELLOW_BOLD_BRIGHT + getGold() + String.format("%" + (35 - String.valueOf(getGold()).length() - 8) + "s", "") + WHITE_BOLD_BRIGHT + "║" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "║" + RESET + " " + WHITE_BOLD_BRIGHT + "Опыт: " + GREEN_BOLD_BRIGHT + getExperience() + String.format("%" + (35 - String.valueOf(getExperience()).length() - 6) + "s", "") + WHITE_BOLD_BRIGHT + "║" + RESET);
    }


    public boolean performAction(Player player, Room room) {
        Scanner scanner = new Scanner(System.in);
        List<Entity> enemies = room.getEnemies();
        List<Ally> allies = this.allies;
        boolean turnEnded = false; // Flag for checking if the player's turn should end

        if (isNightmarish()|| isStunned() || isFrozen()) {
            System.out.println(WHITE_BOLD_BRIGHT + "Вы не можете действовать из-за состояния!" + RESET);
            return true; // End the player's turn without performing an action
        }

        System.out.println(WHITE_BOLD_BRIGHT + "╔═══════════════ ДЕЙСТВИЯ ИГРОКА ═══════════════╗" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "║                                               ║" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "║  " + GREEN_BOLD_BRIGHT + "1." + WHITE_BOLD_BRIGHT + " Атаковать правой рукой                    ║" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "║  " + GREEN_BOLD_BRIGHT + "2." + WHITE_BOLD_BRIGHT + " Атаковать левой рукой                     ║" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "║  " + GREEN_BOLD_BRIGHT + "3." + WHITE_BOLD_BRIGHT + " Попробовать сбежать                       ║" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "║  " + GREEN_BOLD_BRIGHT + "4." + WHITE_BOLD_BRIGHT + " Использовать инвентарь                    ║" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "║  " + GREEN_BOLD_BRIGHT + "5." + WHITE_BOLD_BRIGHT + " Использовать магию                        ║" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "║  " + GREEN_BOLD_BRIGHT + "6." + WHITE_BOLD_BRIGHT + " Показать информацию о врагах              ║" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "║  " + GREEN_BOLD_BRIGHT + "7." + WHITE_BOLD_BRIGHT + " Показать информацию о союзниках           ║" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "║  " + GREEN_BOLD_BRIGHT + "8." + WHITE_BOLD_BRIGHT + " Показать информацию о игроке              ║" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "║                                               ║" + RESET);
        System.out.println(WHITE_BOLD_BRIGHT + "╚═══════════════════════════════════════════════╝" + RESET);
        int choice = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print(CYAN_BOLD_BRIGHT + "Введите ваш выбор (1-8): " + RESET);
            try {
                String input = scanner.nextLine().trim();

                // Проверка на пустой ввод
                if (input.isEmpty()) {
                    System.out.println(RED_BOLD_BRIGHT + "Ошибка: Пустой ввод. Пожалуйста, введите число от 1 до 8." + RESET);
                    continue;
                }

                // Попытка преобразовать ввод в число
                choice = Integer.parseInt(input);

                // Проверка диапазона
                if (choice >= 1 && choice <= 8) {
                    validInput = true;
                } else {
                    System.out.println(RED_BOLD_BRIGHT + "Ошибка: Введите число от 1 до 8." + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED_BOLD_BRIGHT + "Ошибка: Введите корректное число от 1 до 8." + RESET);
            } catch (Exception e) {
                System.out.println(RED_BOLD_BRIGHT + "Произошла ошибка при вводе. Попробуйте снова." + RESET);
                scanner.nextLine(); // Очистка буфера сканера
            }
        }

        switch (choice) {
            case 1:
                if (hasAttackedRightHand) {
                    System.out.println(RED_BOLD_BRIGHT + "Вы уже атаковали правой рукой в этом ходу." + RESET);
                    return false; // Возврат, так как ход не закончился
                }
                Entity targetRight = chooseTarget(enemies, "right");
                if (targetRight != null) {
                    attack(targetRight, inventory.getEquippedRightHand() != null ? (Weapon) inventory.getEquippedRightHand() : null);
                    hasAttackedRightHand = true; // Player attacked with right hand
                } else {
                    System.out.println("Нет доступных целей для атаки правой рукой.");
                }
                break;
            case 2:
                if (hasAttackedLeftHand) {
                    System.out.println(RED_BOLD_BRIGHT + "Вы уже атаковали левой рукой в этом ходу." + RESET);
                    return false; // Возврат, так как ход не закончился
                }
                Entity targetLeft = chooseTarget(enemies, "left");
                if (targetLeft != null) {
                    attack(targetLeft, inventory.getEquippedLeftHand() != null ? (Weapon) inventory.getEquippedLeftHand() : null);
                    hasAttackedLeftHand = true; // Player attacked with left hand
                } else {
                    System.out.println("Нет доступных целей для атаки левой рукой.");
                }
                break;
            case 3:
                boolean escaped = tryEscape();
                if (escaped) {
                    turnEnded = true; // End turn if escaped
                    room.setEscaped(true);
                }
                turnEnded = true;
                break;
            case 4:
                useInventory(player, room); // This does not end the turn
                return false; // Return to not check end of turn
            case 5:
                useMagic(room); // This does not end the turn
                return false; // Return to not check end of turn
            case 6:
                showEnemiesInfo(room); // This does not end the turn
                return false; // Return to not check end of turn
            case 7:
                showAlliesInfo(allies); // This does not end the turn
                return false; // Return to not check end of turn
            case 8:
                showInfo();
                return false;
            default:
                System.out.println("Неверный выбор!");
                return false; // Return to not check end of turn
        }

        // Check if both hands were used to attack
        if (hasAttackedRightHand && hasAttackedLeftHand) {
            turnEnded = true; // End turn if both hands were used
        }

        // Reset attack flags for the next turn
        if (turnEnded) {
            hasAttackedRightHand = false;
            hasAttackedLeftHand = false;
        }

        return turnEnded; // Return whether the turn has ended
    }

    public void performAlliesActions(Room room) {
        if (allies.isEmpty()) {
            return;
        }

        List<Entity> enemies = room.getEnemies();
        List<Ally> deadAllies = new ArrayList<>();

        for (Ally ally : allies) {
            // Проверяем, жив ли союзник
            if (!ally.isAlive()) {
                deadAllies.add(ally);
                continue;
            }

            // Проверяем, есть ли живые враги
            Entity target = chooseRandomEnemy(enemies);
            if (target != null) {
                ally.updateEffects(); // Обновляем эффекты перед действием
                ally.performAllyAction(room, this);
            }
        }

        // Удаляем мертвых союзников
        for (Ally deadAlly : deadAllies) {
            allies.remove(deadAlly);
            System.out.println(RED_BOLD_BRIGHT + deadAlly.getName() + " пал в бою!" + RESET);
        }
    }

    private Entity chooseRandomEnemy(List<Entity> enemies) {
        if (enemies.isEmpty()) return null;
        Random random = new Random();
        return enemies.get(random.nextInt(enemies.size()));
    }


    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";

    public void showEnemiesInfo(Room room) {
        List<Entity> enemies = room.getEnemies();
        System.out.println(WHITE_BOLD_BRIGHT + "\n╔══════════ ВРАГИ В КОМНАТЕ ═════════╗" + RESET);

        for (Entity enemy : enemies) {
            enemy.showInfo();
            System.out.println(WHITE_BOLD_BRIGHT + "╠════════════════════════════════════╣" + RESET);
        }
        System.out.println(WHITE_BOLD_BRIGHT + "╚════════════════════════════════════╝\n" + RESET);
    }


    private Entity chooseTarget(List<Entity> enemies, String attacker) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(WHITE_BOLD_BRIGHT + "Враги:" + RESET);
        for (int i = 0; i < enemies.size(); i++) {
            System.out.println(WHITE_BOLD_BRIGHT + (i + 1) + ". " + enemies.get(i).getName() + RESET);
        }

        System.out.print(WHITE_BOLD_BRIGHT + "Выберите цель (1-" + enemies.size() + "): " + RESET);
        int targetIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Потребляем символ новой строки

        if (targetIndex < 0 || targetIndex >= enemies.size()) {
            System.out.println(RED_BOLD_BRIGHT + "Неверный выбор цели!" + RESET);
            return null;
        }

        Entity target = enemies.get(targetIndex);
        return target;
    }

    public void showAlliesInfo(List<Ally> allies) {
        if (allies.isEmpty()){
            System.out.println(YELLOW_BOLD_BRIGHT + "У вас нет союзников на данный момент!" + RESET);
        } else {
            System.out.println(WHITE_BOLD_BRIGHT + "Ваши союзники:" + RESET);
            for (Entity ally : allies) {
                ally.showInfo();
            }
        }
    }

    public void useMagic(Room room) {
        Scanner scanner = new Scanner(System.in);

        try {
            List<Entity> enemies = room.getEnemies();

            if (enemies.isEmpty()) {
                System.out.println(RED_BOLD_BRIGHT + "В комнате нет врагов!" + RESET);
                return;
            }

            System.out.println(WHITE_BOLD_BRIGHT + "Враги:" + RESET);
            for (int i = 0; i < enemies.size(); i++) {
                System.out.println(WHITE_BOLD_BRIGHT + (i + 1) + ". " + enemies.get(i).getName() + RESET);
            }

            System.out.print(WHITE_BOLD_BRIGHT + "Выберите цель (1-" + enemies.size() + "): " + RESET);

            if (!scanner.hasNextInt()) {
                System.out.println(RED_BOLD_BRIGHT + "Пожалуйста, введите числовое значение!" + RESET);
                scanner.nextLine(); // Очищаем буфер
                return;
            }

            int targetIndex = scanner.nextInt() - 1;
            scanner.nextLine(); // Потребляем символ новой строки

            if (targetIndex < 0 || targetIndex >= enemies.size()) {
                System.out.println(RED_BOLD_BRIGHT + "Неверный выбор цели!" + RESET);
                return;
            }

            Entity target = enemies.get(targetIndex);
            System.out.println(WHITE_BOLD_BRIGHT + "Выберите заклинание:" + RESET);
            displayAvailableSpells();

            System.out.print(WHITE_BOLD_BRIGHT + "Введите ваш выбор (или 0 для возврата): " + RESET);

            if (!scanner.hasNextInt()) {
                System.out.println(RED_BOLD_BRIGHT + "Пожалуйста, введите числовое значение!" + RESET);
                scanner.nextLine(); // Очищаем буфер
                return;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Потребляем символ новой строки

            if (choice == 0) {
                return; // Вернуться в главное меню действий
            }

            castSpell(choice, target, room);

        } catch (IndexOutOfBoundsException e) {
            System.out.println(RED_BOLD_BRIGHT + "Ошибка: неверный индекс цели!" + RESET);
        } catch (InputMismatchException e) {
            System.out.println(RED_BOLD_BRIGHT + "Ошибка: введите корректное числовое значение!" + RESET);
            scanner.nextLine(); // Очищаем буфер
        } catch (Exception e) {
            System.out.println(RED_BOLD_BRIGHT + "Произошла непредвиденная ошибка: " + e.getMessage() + RESET);
        }
    }

    private void displayAvailableSpells() {
        if (learnedHeal) System.out.println(WHITE_BOLD_BRIGHT + "1. Исцеление" + RESET);
        if (learnedFireball) System.out.println(WHITE_BOLD_BRIGHT + "2. Огненный шар" + RESET);
        if (learnedIceSpike) System.out.println(WHITE_BOLD_BRIGHT + "3. Ледяной шип" + RESET);
        if (learnedEarthQuake) System.out.println(WHITE_BOLD_BRIGHT + "4. Землетрясение" + RESET);
        if (learnedShadowBolt) System.out.println(WHITE_BOLD_BRIGHT + "5. Теневая стрела" + RESET);
        if (learnedArcaneMissile) System.out.println(WHITE_BOLD_BRIGHT + "6. Чародейская стрела" + RESET);
        if (learnedBloodMagic) System.out.println(WHITE_BOLD_BRIGHT + "7. Кровавая магия" + RESET);
    }

    private void castSpell(int choice, Entity target, Room room) {
        switch (choice) {
            case 1:
                if (learnedHeal) castHeal();
                else System.out.println(RED_BOLD_BRIGHT + "Вы еще не изучили Исцеление!" + RESET);
                break;
            case 2:
                if (learnedFireball) castFireball(target);
                else System.out.println(RED_BOLD_BRIGHT + "Вы еще не изучили Огненный шар!" + RESET);
                break;
            case 3:
                if (learnedIceSpike) castIceSpike(target);
                else System.out.println(RED_BOLD_BRIGHT + "Вы еще не изучили Ледяной шип!" + RESET);
                break;
            case 4:
                if (learnedEarthQuake) castEarthQuake(room);
                else System.out.println(RED_BOLD_BRIGHT + "Вы еще не изучили Землетрясение!" + RESET);
                break;
            case 5:
                if (learnedShadowBolt) castShadowBolt(room);
                else System.out.println(RED_BOLD_BRIGHT + "Вы еще не изучили Теневую стрелу!" + RESET);
                break;
            case 6:
                if (learnedArcaneMissile) castArcaneMissile(target);
                else System.out.println(RED_BOLD_BRIGHT + "Вы еще не изучили Чародейскую стрелу!" + RESET);
                break;
            case 7:
                if (learnedBloodMagic) castBloodMagic(room);
                else System.out.println(RED_BOLD_BRIGHT + "Вы еще не изучили Кровавую магию!" + RESET);
                break;
            default:
                System.out.println(RED_BOLD_BRIGHT + "Неверный выбор!" + RESET);
        }
    }

    public boolean tryEscape() {
        Random random = new Random();
        int chance = random.nextInt(100) + 1; // 1-100
        if (chance <= 33) {
            System.out.println(GREEN_BOLD_BRIGHT + "Вам удалось успешно сбежать!" + RESET);
            return true;
        } else {
            System.out.println(RED_BOLD_BRIGHT + "Вам не удалось сбежать!" + RESET);
            return false;
        }
    }

    public void castBloodMagic(Room room) {
        if (mana >= 100) {
            if (getHealth() >= (getMaxHealth() * 0.4)) {
                spendMana(100);
                List<Entity> enemies = room.getEnemies();
                double totalHealthDrained = 0;
                double levelMultiplier = 1 + (getLevel() * 0.05);

                for (Entity target : enemies) {
                    if (Math.random() < 0.65) {
                        double healthToDrain = (target.getHealth() * 0.25) * levelMultiplier;
                        target.takeDamage((int) healthToDrain);
                        totalHealthDrained += healthToDrain;
                        System.out.println(RED_BOLD_BRIGHT + getName() + " высасывает " + healthToDrain + " здоровья у " + target.getName() + RESET);
                    } else {
                        System.out.println(YELLOW_BOLD_BRIGHT + getName() + " не удалось высосать здоровье у " + target.getName() + "!" + RESET);
                    }
                }

                double healthLoss = getHealth() * 0.4;
                setHealth(getHealth() - healthLoss);
                System.out.println(RED_BOLD_BRIGHT + getName() + " жертвует " + healthLoss + " здоровья для заклинания Кровавой Магии." + RESET);
                setHealth(getHealth() + totalHealthDrained);
                System.out.println(GREEN_BOLD_BRIGHT + getName() + " высасывает всего " + totalHealthDrained + " здоровья у всех врагов." + RESET);
            } else {
                System.out.println(RED_BOLD_BRIGHT + "Недостаточно здоровья для применения Кровавой Магии!" + RESET);
            }
        } else {
            System.out.println(BLUE_BOLD_BRIGHT + "Недостаточно маны для применения Кровавой Магии!" + RESET);
        }
    }

    public void castIceSpike(Entity target) {
        if (mana >= 50) {
            spendMana(50);
            double levelMultiplier = 1 + (getLevel() * 0.12);
            double damage = (getAttack() * 2) * levelMultiplier;
            target.takeDamage(damage);
            System.out.println(CYAN_BOLD_BRIGHT + getName() + " применяет Ледяной Шип, нанося " + damage + " урона " + target.getName() + RESET);
            target.addEffect(new FrozenEffect(2, "Заморожен"));
        } else {
            System.out.println(BLUE_BOLD_BRIGHT + "Недостаточно маны для применения Ледяного Шипа!" + RESET);
        }
    }

    public void castEarthQuake(Room room) {
        List<Entity> enemies = room.getEnemies();
        if (mana >= 100) {
            spendMana(100);
            double levelMultiplier = 1 + (getLevel() * 0.13);
            for (Entity enemy : enemies) {
                if (enemy.isAlive()) {
                    double damage = (getAttack() * 1.25) * levelMultiplier;
                    enemy.takeDamage(damage);
                    System.out.println(YELLOW_BOLD_BRIGHT + getName() + " вызывает Землетрясение, нанося " + damage + " урона " + enemy.getName() + RESET);
                }
            }
        } else {
            System.out.println(BLUE_BOLD_BRIGHT + "Недостаточно маны для вызова Землетрясения!" + RESET);
        }
    }

    public void castShadowBolt(Room room) {
        List<Entity> enemies = room.getEnemies();
        if (enemies.size() < 2) {
            System.out.println(YELLOW_BOLD_BRIGHT + "Недостаточно врагов для применения Теневого Разряда!" + RESET);
            return;
        }

        if (mana >= 80) {
            spendMana(80);
            double levelMultiplier = 1 + (getLevel() * 0.14);
            Collections.shuffle(enemies);
            Entity target1 = enemies.get(0);
            Entity target2 = enemies.get(1);

            double damage1 = (getAttack() * 3) * levelMultiplier;
            target1.takeDamage(damage1);
            System.out.println(PURPLE_BOLD_BRIGHT + getName() + " применяет Теневой Разряд, нанося " + damage1 + " урона " + target1.getName() + RESET);
            target1.addEffect(new WeakenedEffect(2, "Ослаблен"));

            double damage2 = (getAttack() * 3) * levelMultiplier;
            target2.takeDamage(damage2);
            System.out.println(PURPLE_BOLD_BRIGHT + getName() + " применяет Теневой Разряд, нанося " + damage2 + " урона " + target2.getName() + RESET);
            target2.addEffect(new WeakenedEffect(2, "Ослаблен"));
        } else {
            System.out.println(BLUE_BOLD_BRIGHT + "Недостаточно маны для применения Теневого Разряда!" + RESET);
        }
    }

    public void castArcaneMissile(Entity target) {
        if (mana >= 50) {
            spendMana(50);
            double levelMultiplier = 1 + (getLevel() * 0.11);
            double damage = (getAttack() * 3) * levelMultiplier;
            target.takeDamage(damage);
            System.out.println(CYAN_BOLD_BRIGHT + getName() + " запускает Чародейскую Стрелу, нанося " + damage + " урона " + target.getName() + RESET);

            if (Math.random() < 0.2) {
                double critDamage = damage * 3.5 * levelMultiplier;
                target.takeDamage(critDamage);
                System.out.println(RED_BOLD_BRIGHT + getName() + " наносит критический удар Чародейской Стрелой, нанося дополнительные " + critDamage + " урона " + target.getName() + RESET);
            }
        } else {
            System.out.println(BLUE_BOLD_BRIGHT + "Недостаточно маны для запуска Чародейской Стрелы!" + RESET);
        }
    }
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    public void useInventory(Player player, Room room) {
        Scanner scanner = new Scanner(System.in);
        List<Entity> enemies = room.getEnemies();

        // Красивый заголовок
        System.out.println("\n" + PURPLE_BOLD_BRIGHT + "╔════════════════════════════════╗");
        System.out.println("║         INVENTORY MENU         ║");
        System.out.println("╚════════════════════════════════╝" + RESET);

        // Показ врагов
        if (!enemies.isEmpty()) {
            System.out.println(RED_BOLD_BRIGHT + "\n◆ Current Enemies ◆" + RESET);
            for (int i = 0; i < enemies.size(); i++) {
                System.out.println(RED + "  " + (i + 1) + ". " + enemies.get(i).getName() +
                        " [HP: " + enemies.get(i).getHealth() + "]" + RESET);
            }
        }

        // Показ инвентаря
        System.out.println(BLUE_BOLD_BRIGHT + "\n◆ Your Inventory ◆" + RESET);
        player.getInventory().displayInventory();

        System.out.println(YELLOW_BOLD_BRIGHT + "\n◆ Your Amulets ◆" + RESET);
        player.getInventory().displayAmulets();

        // Меню действий
        System.out.println(GREEN_BOLD_BRIGHT + "\n◆ Available Actions ◆" + RESET);
        System.out.println(GREEN + "  1. ⚔ Equip Weapon");
        System.out.println("  2. ⚔ Unequip Weapon");
        System.out.println("  3. 🎒 Use Item" + RESET);

        if (!player.getInventory().getAmulets().isEmpty()) {
            System.out.println(YELLOW + "  4. 📿 Equip Amulet");
            System.out.println("  5. 📿 Unequip Amulet" + RESET);
        }

        System.out.print(CYAN_BOLD_BRIGHT + "\n➤ Enter your choice (1-" +
                (player.getInventory().getAmulets().isEmpty() ? "3" : "5") + "): " + RESET);

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println(BLUE_BOLD_BRIGHT + "\n◆ Choose a weapon to equip ◆" + RESET);
                for (int i = 0; i < player.getInventory().getItems().size(); i++) {
                    System.out.println(BLUE + "  " + (i + 1) + ". " +
                            player.getInventory().getItems().get(i).getName() + RESET);
                }
                System.out.print(CYAN_BOLD_BRIGHT + "\n➤ Select weapon number: " + RESET);
                int weaponIndex = scanner.nextInt() - 1;
                scanner.nextLine();

                if (weaponIndex >= 0 && weaponIndex < player.getInventory().getItems().size()) {
                    Item weapon = player.getInventory().getItems().get(weaponIndex);
                    System.out.println(BLUE_BOLD_BRIGHT + "\n◆ Choose hand to equip ◆" + RESET);
                    System.out.println(BLUE + "  1. ← Left Hand");
                    System.out.println("  2. → Right Hand" + RESET);
                    System.out.print(CYAN_BOLD_BRIGHT + "\n➤ Select hand: " + RESET);
                    int handChoice = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (handChoice == 0) {
                        player.getInventory().equipWeapon(weapon, "left");
                        System.out.println(GREEN_BOLD_BRIGHT + "✔ Weapon equipped to left hand!" + RESET);
                    } else if (handChoice == 1) {
                        player.getInventory().equipWeapon(weapon, "right");
                        System.out.println(GREEN_BOLD_BRIGHT + "✔ Weapon equipped to right hand!" + RESET);
                    } else {
                        System.out.println(RED_BOLD_BRIGHT + "✘ Invalid hand choice!" + RESET);
                    }
                } else {
                    System.out.println(RED_BOLD_BRIGHT + "✘ Invalid weapon choice!" + RESET);
                }
                break;

            case 2:
                System.out.println(BLUE_BOLD_BRIGHT + "\n◆ Choose hand to unequip ◆" + RESET);
                System.out.println(BLUE + "  1. ← Left Hand");
                System.out.println("  2. → Right Hand" + RESET);
                System.out.print(CYAN_BOLD_BRIGHT + "\n➤ Select hand: " + RESET);
                int handChoicee = scanner.nextInt() - 1;
                scanner.nextLine();

                if (handChoicee == 0) {
                    player.getInventory().unequipWeapon("left");
                    System.out.println(GREEN_BOLD_BRIGHT + "✔ Weapon unequipped from left hand!" + RESET);
                } else if (handChoicee == 1) {
                    player.getInventory().unequipWeapon("right");
                    System.out.println(GREEN_BOLD_BRIGHT + "✔ Weapon unequipped from right hand!" + RESET);
                } else {
                    System.out.println(RED_BOLD_BRIGHT + "✘ Invalid hand choice!" + RESET);
                }
                break;

            case 3:
                System.out.println(BLUE_BOLD_BRIGHT + "\n◆ Choose an item to use ◆" + RESET);
                for (int i = 0; i < player.getInventory().getItems().size(); i++) {
                    System.out.println(BLUE + "  " + (i + 1) + ". " +
                            player.getInventory().getItems().get(i).getName() + RESET);
                }
                System.out.print(CYAN_BOLD_BRIGHT + "\n➤ Select item number: " + RESET);
                int itemIndex = scanner.nextInt() - 1;
                scanner.nextLine();

                if (itemIndex >= 0 && itemIndex < player.getInventory().getItems().size()) {
                    Item item = player.getInventory().getItems().get(itemIndex);

                    if (item instanceof Consumable || item instanceof MagicalFruit) {
                        if (item instanceof Consumable) {
                            ((Consumable) item).applyEffect(player);
                        } else {
                            ((MagicalFruit) item).applyEffect(player);
                        }
                        player.getInventory().removeItem(item);
                        System.out.println(GREEN_BOLD_BRIGHT + "✔ Item used successfully!" + RESET);
                    } else if (item instanceof AllHitWeapons) {
                        // Добавляем обработку AllHitWeapons
                        ((AllHitWeapons) item).applyEffect(room);
                        player.getInventory().removeItem(item);
                        System.out.println(GREEN_BOLD_BRIGHT + "✔ Item used on all enemies successfully!" + RESET);
                    } else if (item instanceof OneTargetHitItems) {
                        System.out.println(RED_BOLD_BRIGHT + "\n◆ Choose your target ◆" + RESET);
                        for (int i = 0; i < enemies.size(); i++) {
                            System.out.println(RED + "  " + (i + 1) + ". " +
                                    enemies.get(i).getName() + RESET);
                        }
                        System.out.print(CYAN_BOLD_BRIGHT + "\n➤ Select target (1-" +
                                enemies.size() + "): " + RESET);
                        int targetIndex = scanner.nextInt() - 1;
                        scanner.nextLine();
                        if (targetIndex >= 0 && targetIndex < enemies.size()) {
                            Entity target = enemies.get(targetIndex);
                            ((OneTargetHitItems) item).applyEffect(room, target);
                            player.getInventory().removeItem(item);
                            System.out.println(GREEN_BOLD_BRIGHT + "✔ Item used on target successfully!" + RESET);
                        } else {
                            System.out.println(RED_BOLD_BRIGHT + "✘ Invalid target choice!" + RESET);
                        }
                    } else {
                        System.out.println(RED_BOLD_BRIGHT + "✘ This item cannot be used!" + RESET);
                    }
                } else {
                    System.out.println(RED_BOLD_BRIGHT + "✘ Invalid item choice!" + RESET);
                }
                break;

            case 4:
                if (!player.getInventory().getAmulets().isEmpty()) {
                    System.out.println(YELLOW_BOLD_BRIGHT + "\n◆ Choose an amulet to equip ◆" + RESET);
                    for (int i = 0; i < player.getInventory().getAmulets().size(); i++) {
                        System.out.println(YELLOW + "  " + (i + 1) + ". " +
                                player.getInventory().getAmulets().get(i).getName() + RESET);
                    }
                    System.out.print(CYAN_BOLD_BRIGHT + "\n➤ Select amulet number: " + RESET);
                    int amuletIndex = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (amuletIndex >= 0 && amuletIndex < player.getInventory().getAmulets().size()) {
                        Amulet amulet = player.getInventory().getAmulets().get(amuletIndex);
                        player.getInventory().equipAmulet(amulet, player);
                        System.out.println(GREEN_BOLD_BRIGHT + "✔ Amulet equipped successfully!" + RESET);
                    } else {
                        System.out.println(RED_BOLD_BRIGHT + "✘ Invalid amulet choice!" + RESET);
                    }
                } else {
                    System.out.println(RED_BOLD_BRIGHT + "✘ No amulets available!" + RESET);
                }
                break;

            case 5:
                if (!player.getInventory().getAmulets().isEmpty()) {
                    player.getInventory().unequipAmulet(player);
                    System.out.println(GREEN_BOLD_BRIGHT + "✔ Amulet unequipped successfully!" + RESET);
                } else {
                    System.out.println(RED_BOLD_BRIGHT + "✘ No amulet equipped!" + RESET);
                }
                break;

            default:
                System.out.println(RED_BOLD_BRIGHT + "✘ Invalid choice!" + RESET);
                break;
        }
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении игры: " + e.getMessage());
        }
    }

    public static Player loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Player) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при загрузке игры: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void dropLoot(Game game) {
    }




}