package GameV2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Item> items;
    private Item equippedLeftHand;
    private Item equippedRightHand;
    private Item equippedAmulet;
    private List<Amulet> amulets;

    public Inventory() {
        items = new ArrayList<>();
        equippedLeftHand = null;
        equippedRightHand = null;
        equippedAmulet = null;
        this.amulets = new ArrayList<>();
    }

    public List<Item> getInventory() {
        return items;
    }

    public void addAmulet(Amulet amulet) {
        amulets.add(amulet);
    }

    public int getLockpickCount() {
        int count = 0;
        for (Item item : items) {
            if (item instanceof LockPick) { // Проверяем, является ли предмет отмычкой
                count++;
            }
        }
        return count; // Возвращаем общее количество отмычек
    }


    public boolean hasItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    public Item removeItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                items.remove(item);
                System.out.println(item.getName() + " удален из инвентаря.");
                return item;
            }
        }
        return null; // Item not found
    }


    public void equipAmulet(Item amulet, Player player) {
        if (amulet instanceof Amulet) {
            unequipAmulet(player); // Remove effects of the old amulet
            equippedAmulet = amulet;
            removeItem(amulet); // Remove from inventory
            System.out.println("You equipped the " + amulet.getName() + ".");
            ((Amulet) amulet).applyPassiveEffect(player); // Apply effects of the new amulet
        } else {
            System.out.println("That item cannot be equipped as an amulet.");
        }
    }

    public void unequipAmulet(Player player) {
        if (equippedAmulet != null) {
            // Call the removePassiveEffect method on the player instance
            player.removePassiveEffect((Amulet) equippedAmulet); // Remove effects
            addItem(equippedAmulet); // Add back to inventory
            System.out.println("You unequipped the amulet.");
            equippedAmulet = null;
        } else {
            System.out.println("You don't have an amulet equipped.");
        }
    }

    // Getter for the equipped amulet
    public Item getEquippedAmulet() {
        return equippedAmulet;
    }

    // Methods for adding, removing, and retrieving items
    public void addItem(Item item) {
        items.add(item);
    }

    public boolean removeItem(Item item) {
        boolean removed = items.remove(item);
        if (removed) {
            System.out.println(item.getName() + " удален из инвентаря.");
        }
        return removed; // Return whether the item was successfully removed
    }

    public void removeLockpick() {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item instanceof LockPick) {
                iterator.remove();

                return; // Удаляем только одну отмычку и выходим из метода
            }
        }
    }

    public Item getItemByName(String itemName) {
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null; // Item not found
    }

    public List<Item> getItems() {
        return items;
    }

    // Methods for equipping weapons
    public void equipWeapon(Item weapon, String hand) {
        if (weapon instanceof Weapon) {
            if (hand.equalsIgnoreCase("left")) {
                unequipWeapon("left"); // Unequip any weapon in the left hand
                equippedLeftHand = weapon;
            } else if (hand.equalsIgnoreCase("right")) {
                unequipWeapon("right"); // Unequip any weapon in the right hand
                equippedRightHand = weapon;
            }
            removeItem(weapon); // Remove the weapon from the inventory
            System.out.println("You equipped the " + weapon.getName() + " in your " + hand + " hand.");
        } else {
            System.out.println("That item cannot be equipped as a weapon.");
        }
    }

    public void unequipWeapon(String hand) {
        if (hand.equalsIgnoreCase("left")) {
            if (equippedLeftHand != null) {
                addItem(equippedLeftHand); // Add the unequipped weapon back to the inventory
                equippedLeftHand = null;
                System.out.println("You unequipped the weapon from your left hand.");
            } else {
                System.out.println("You don't have a weapon equipped in your left hand.");
            }
        } else if (hand.equalsIgnoreCase("right")) {
            if (equippedRightHand != null) {
                addItem(equippedRightHand); // Add the unequipped weapon back to the inventory
                equippedRightHand = null;
                System.out.println("You unequipped the weapon from your right hand.");
            } else {System.out.println("You don't have a weapon equipped in your right hand.");
            }
        }
    }

    // Getters for equipped weapons
    public Item getEquippedLeftHand() {
        return equippedLeftHand;
    }

    public Item getEquippedRightHand() {
        return equippedRightHand;
    }

    // Method to display the inventory
    public void displayInventory() {
        System.out.println(CYAN_BOLD + "╔═════════════════════════════════════════════╗");
        System.out.println("║              ИНВЕНТАРЬ ИГРОКА               ║");
        System.out.println("╠═════════════════════════════════════════════╣" + RESET);

        // Предметы
        System.out.println(YELLOW_BOLD + "║ Предметы:" + RESET);
        if (items.isEmpty()) {
            System.out.println("║  " + ITALIC + "Инвентарь пуст" + RESET);
        } else {
            for (int i = 0; i < items.size(); i++) {
                System.out.printf("║  %d. %-37s \n", (i + 1), items.get(i).getName());
            }
        }

        System.out.println(CYAN_BOLD + "╠═════════════════════════════════════════════╣" + RESET);

        // Экипировка
        System.out.println(YELLOW_BOLD + "║ Экипировка:" + RESET);
        System.out.printf("║  Левая рука:  %-31s \n", (equippedLeftHand != null ? equippedLeftHand.getName() : ITALIC + "Пусто" + RESET));
        System.out.printf("║  Правая рука: %-31s \n", (equippedRightHand != null ? equippedRightHand.getName() : ITALIC + "Пусто" + RESET));
        System.out.printf("║  Амулет:      %-31s \n", (equippedAmulet != null ? equippedAmulet.getName() : ITALIC + "Пусто" + RESET));

        System.out.println(CYAN_BOLD + "╠═════════════════════════════════════════════╣" + RESET);

        // Амулеты
        System.out.println(YELLOW_BOLD + "║ Амулеты:" + RESET);
        if (amulets.isEmpty()) {
            System.out.println("║  " + ITALIC + "Нет амулетов" + RESET);
        } else {
            for (int i = 0; i < amulets.size(); i++) {
                System.out.printf("║  %d. %-37s \n", (i + 1), amulets.get(i).getName());
            }
        }

        System.out.println(CYAN_BOLD + "╚═════════════════════════════════════════════╝" + RESET);
    }
    public List<Amulet> getAmulets() {
        return amulets; // Return the list of amulets
    }

    private static final String RESET = "\u001B[0m";
    private static final String CYAN_BOLD = "\u001B[1;36m";
    private static final String YELLOW_BOLD = "\u001B[1;33m";
    private static final String ITALIC = "\u001B[3m";

    public boolean hasKeyOfType(Class<? extends Key> keyType) {
        for (Item item : items) {
            if (keyType.isInstance(item)) {
                return true; // Found a key of the specified type
            }
        }
        return false; // No key of the specified type found
    }

    public void displayAmulets() {
        if (amulets.isEmpty()) {

        } else {
            System.out.println("Your Amulets:");
            for (int i = 0; i < amulets.size(); i++) {
                System.out.println((i + 1) + ". " + amulets.get(i).getName());
            }
        }
    }

    public boolean hasAllBossWeapons() {
        boolean hasZBSword = false;
        boolean hasSkeletonBossUltraBow = false;
        boolean hasGolemKingWand = false;
        boolean hasGoblinBossDarkAxe = false;
        boolean hasBanditBossMegaStaff = false;
        boolean hasRuby = false;
        boolean hasDiamond = false;
        boolean hasSapphire = false;
        boolean hasEmerald = false;

        for (Item item : items) {
            if (item instanceof ZBSword) hasZBSword = true;
            else if (item instanceof SkeletonBossUltraBow) hasSkeletonBossUltraBow = true;
            else if (item instanceof GolemKingWand) hasGolemKingWand = true;
            else if (item instanceof GoblinBossDarkAxe) hasGoblinBossDarkAxe = true;
            else if (item instanceof BanditBossMegaStaff) hasBanditBossMegaStaff = true;
            else if (item instanceof Ruby) hasRuby = true;
            else if (item instanceof Diamond) hasDiamond = true;
            else if (item instanceof Sapphire) hasSapphire = true;
            else if (item instanceof Emerald) hasEmerald = true;
        }

        return hasZBSword && hasSkeletonBossUltraBow && hasGolemKingWand &&
                hasGoblinBossDarkAxe && hasBanditBossMegaStaff &&
                hasRuby && hasDiamond && hasSapphire && hasEmerald;
    }

    public void removeAllBossWeapons() {
        items.removeIf(item ->
                item instanceof ZBSword ||
                        item instanceof SkeletonBossUltraBow ||
                        item instanceof GolemKingWand ||
                        item instanceof GoblinBossDarkAxe ||
                        item instanceof BanditBossMegaStaff ||
                        item instanceof Ruby ||
                        item instanceof Diamond ||
                        item instanceof Sapphire ||
                        item instanceof Emerald
        );
    }
}