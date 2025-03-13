package GameV2;

public class CursedZombie extends Zombie {
    public CursedZombie(String name, int level) {
        super(name, level, 10, 50); // Adjust base attack and health as needed
    }

    @Override
    public void performAction(Game game, Room room) {
        cursePlayer(game.getPlayer());
    }

    private void cursePlayer(Player player) {
        System.out.println(getName() + " casts a curse on you!");
        player.addEffect(new MarkedEffect(3, "Marked", 3));
    }
}
