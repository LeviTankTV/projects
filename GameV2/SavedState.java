package GameV2;

import java.util.ArrayList;
import java.util.List;

public class SavedState {
    private int playerHealth;
    private int playerMana;
    private int playerLevel;
    private List<AllyState> alliesState;
    private int supremeMageHealth;
    private int supremeMageMana;
    private int supremeMageLevel;
    private double playerAttack;
    private int playerGold;
    private int playerExperience;

    public SavedState(Player player, Entity supremeMage) {
        // Сохраняем состояние игрока
        this.playerHealth = (int) player.getHealth();
        this.playerMana = player.getMana();
        this.playerLevel = player.getLevel();
        this.playerAttack = player.getAttack();
        this.playerGold = player.getGold();
        this.playerExperience = player.getExperience();

        // Сохраняем состояние союзников
        this.alliesState = new ArrayList<>();
        for (Ally ally : player.getAllies()) {
            alliesState.add(new AllyState(ally));
        }

        // Сохраняем состояние Верховного Мага
        this.supremeMageHealth = (int) supremeMage.getHealth();
        this.supremeMageLevel = supremeMage.getLevel();
    }

    public void restore(Player player, Entity supremeMage) {
        // Восстанавливаем состояние игрока
        player.setHealth(playerHealth);
        player.setMana(playerMana);
        player.setLevel(playerLevel);
        player.setAttack(playerAttack);
        player.setGold(playerGold);
        player.setExperience(playerExperience);

        // Восстанавливаем состояние союзников
        for (int i = 0; i < alliesState.size(); i++) {
            if (i < player.getAllies().size()) {
                alliesState.get(i).restore(player.getAllies().get(i));
            }
        }

        // Восстанавливаем состояние Верховного Мага
        supremeMage.setHealth(supremeMageHealth);
        supremeMage.setLevel(supremeMageLevel);
    }

    private class AllyState {
        private String name;
        private int health;
        private int mana;
        private int level;

        public AllyState(Ally ally) {
            this.name = ally.getName();
            this.health = (int) ally.getHealth();
            this.level = ally.getLevel();
        }

        public void restore(Ally ally) {
            ally.setHealth(health);
            ally.setLevel(level);
        }
    }
}