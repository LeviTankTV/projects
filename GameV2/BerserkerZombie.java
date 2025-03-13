package GameV2;

import java.util.List;

public class BerserkerZombie extends Zombie {
    public BerserkerZombie(String name, int level) {
        super(name, level, 15, 325); // Adjust base attack and health as needed
    }

    @Override
    public void performAction(Game game, Room room) {
        attackPlayerAndAllies(game, room);
    }

    private void attackPlayerAndAllies(Game game, Room room) {
        System.out.println(getName() + " goes berserk and attacks everyone!");
        Player player = game.getPlayer();

        // Attack player's allies
        for (Entity ally : player.getAllies()) {
            attack(ally, getWeapon());
            System.out.println("Your " + ally.getName() + " is attacked by the Berserker Zombie!");
        }

        // Attack player
        attack(player, getWeapon());
        System.out.println("You are attacked by the Berserker Zombie!");

        // Attack other enemies in the room
        List<Entity> roomEnemies = room.getEnemies();
        for (Entity enemy : roomEnemies) {
            if (enemy != this && enemy != null) {
                attack(enemy, getWeapon());
                System.out.println(enemy.getName() + " is attacked by the Berserker Zombie!");
            }
        }
    }
}