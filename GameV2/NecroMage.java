package GameV2;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class NecroMage extends Entity {
    private Random random = new Random();
    private GhostFactory ghostFactory = new GhostFactory();

    public NecroMage(String name, int level) {
        super(name, level, 375, 8, 45, 70);
        setEvasion(0.25); // 25% base evasion
    }

    @Override
    public void performAction(Game game, Room room) {
        double actionRoll = random.nextDouble();

        if (actionRoll < 0.15) {
            healAllies(room);
        } else if (actionRoll < 0.30) {
            massAttack(game, room);
        } else if (actionRoll < 0.40) {
            berserkAttack(game, room);
        } else if (actionRoll < 0.55) {
            applyRegenerationToAllies(room);
        } else if (actionRoll < 0.65) {
            strengthenRandomAlly(room);
        } else if (actionRoll < 0.80) {
            applyRandomDebuff(game, room);
        } else if (actionRoll < 0.90) {
            summonGhost(game,room);
        } else {
            // Basic attack if no special action is performed
            List<Entity> targets = getTargets(game, room);
            Entity target = targets.get(random.nextInt(targets.size()));
            attack(target, new NecroStaff());
        }
    }

    private void healAllies(Room room) {
        System.out.println(getName() + " читает заклинание массового исцеления!");
        for (Entity enemy : room.getEnemies()) {
            double healAmount = enemy.getMaxHealth() * 0.3;
            enemy.heal(healAmount);
            System.out.println(enemy.getName() + " восстанавливает " + healAmount + " здоровья!");
        }
    }

    private void massAttack(Game game, Room room) {
        System.out.println(getName() + " призывает темную энергию!");
        List<Entity> targets = new ArrayList<>();
        targets.add(game.getPlayer());
        targets.addAll(game.getPlayer().getAllies());

        for (Entity target : targets) {
            double damage = getAttack() * 1.2;
            target.takeDamage(damage);
            System.out.println(target.getName() + " получает " + damage + " урона от темной энергии!");
        }
    }

    private void berserkAttack(Game game, Room room) {
        System.out.println(getName() + " впадает в безумие и атакует всех вокруг!");
        List<Entity> allTargets = new ArrayList<>();
        allTargets.add(game.getPlayer());
        allTargets.addAll(game.getPlayer().getAllies());
        allTargets.addAll(room.getEnemies());

        for (Entity target : allTargets) {
            if (target != this) {
                double damage = getAttack() * 1.5;
                target.takeDamage(damage);
                System.out.println(target.getName() + " получает " + damage + " урона от безумной атаки!");
            }
        }
    }

    private void applyRegenerationToAllies(Room room) {
        System.out.println(getName() + " накладывает эффект регенерации на союзников!");
        for (Entity enemy : room.getEnemies()) {
            enemy.addEffect(new RegenerationEffect(3, "Регенерация", 0.1));
        }
    }

    private void strengthenRandomAlly(Room room) {
        List<Entity> enemies = room.getEnemies();
        if (!enemies.isEmpty()) {
            Entity target = enemies.get(random.nextInt(enemies.size()));
            System.out.println(getName() + " усиливает " + target.getName() + "!");
            target.addEffect(new IncreasedStrengthEffect(3, "Усиление", 1.3));
        }
    }

    private void applyRandomDebuff(Game game, Room room) {
        List<Entity> targets = new ArrayList<>();
        targets.add(game.getPlayer());
        targets.addAll(game.getPlayer().getAllies());

        if (!targets.isEmpty()) {
            Entity target = targets.get(random.nextInt(targets.size()));
            int effectChoice = random.nextInt(6);

            System.out.println(getName() + " накладывает случайный дебафф на " + target.getName() + "!");

            switch (effectChoice) {
                case 0:
                    target.addEffect(new PoisonEffect(3, "Отравление"));
                    System.out.println(target.getName() + " отравлен!");
                    break;
                case 1:
                    target.addEffect(new WeakenedEffect(3, "Ослабление"));
                    System.out.println(target.getName() + " ослаблен!");
                    break;
                case 2:
                    target.addEffect(new BurningEffect(3, "Поджог"));
                    System.out.println(target.getName() + " горит!");
                    break;
                case 3:
                    target.addEffect(new FrozenEffect(3, "Заморозка"));
                    System.out.println(target.getName() + " заморожен!");
                    break;
                case 4:
                    target.addEffect(new MarkedEffect(3, "Марка", 1.8));
                    System.out.println(target.getName() + " помечен!");
                    break;
                case 5:
                    target.addEffect(new BleedingEffect(3, "Кровь"));
                    System.out.println(target.getName() + " истекает кровью!");
                    break;
            }
        }
    }

    private void summonGhost(Game game, Room room) {
        Player player = game.getPlayer();
        System.out.println(getName() + " призывает призрака!");
        Entity ghost = ghostFactory.createGhost(player);
        room.addEnemy(ghost);
        System.out.println(ghost.getName() + " появляется в комнате!");
    }

    @Override
    public void dropLoot(Game game) {
        Player player = game.getPlayer();
        int goldAmount = random.nextInt(300) + 200;
        player.setGold(player.getGold() + goldAmount);

        System.out.println(WHITE_BOLD + "╔════════════════ ДОБЫЧА ═══════════════════╗" + RESET);
        System.out.println(WHITE_BOLD + "║" + GOLD_COLOR + "    💰 " + getName() + " роняет " + goldAmount + " золота!       " + WHITE_BOLD + "║" + RESET);
        System.out.println(WHITE_BOLD + "╚═══════════════════════════════════════════╝" + RESET);

        if (random.nextDouble() < 0.5) {
            player.addEffect(new RegenerationEffect(4, "Благословение некроманта", 125));

            System.out.println(WHITE_BOLD + "╔═════════════ ОСОБЫЙ ЭФФЕКТ ════════════════╗" + RESET);
            System.out.println(WHITE_BOLD + "║" + PURPLE_BOLD + "    🧙 Вы получаете Благословение Некроманта! " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "║" + GREEN_BOLD + "    ❤️ +125 к регенерации на 4 хода        " + WHITE_BOLD + "║" + RESET);
            System.out.println(WHITE_BOLD + "╚════════════════════════════════════════════╝" + RESET);
        }
    }
    private static final String RESET = "\u001B[0m";
    private static final String WHITE_BOLD = "\u001B[1;37m";
    private static final String GOLD_COLOR = "\u001B[1;33m";
    private static final String GREEN_BOLD = "\u001B[1;32m";
    private static final String PURPLE_BOLD = "\u001B[1;35m";
}