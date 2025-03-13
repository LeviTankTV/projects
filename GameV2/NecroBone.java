package GameV2;

import java.util.Random;

public class NecroBone extends Skeleton {
    private boolean isSummoning; // Indicates if the summoner is currently in the process of summoning
    private int turnsRemaining; // Tracks the number of turns remaining for the summoning timer

    public NecroBone(String name, int level) {
        super(name, level, 15, 125); // Adjust base attack and health as needed
        this.isSummoning = false;
        this.turnsRemaining = 0;
    }

    @Override
    public void performAction(Game game, Room room) {
        if (isSummoning) {
            handleSummoning(game, room);
        } else {
            // Randomly decide to summon immediately or start the timer
            if (new Random().nextBoolean()) {
                summonSkeleton(game, room);
            } else {
                startSummoningTimer();
            }
        }
    }

    private void summonSkeleton(Game game, Room room) {
        System.out.println(getName() + " summons a new skeleton!");
        SkeletonFactory skeletonFactory = new SkeletonFactory(); // Assuming you have a SkeletonFactory
        Skeleton newSkeleton = skeletonFactory.createSkeleton(game.getPlayer());
        room.addEnemy(newSkeleton); // Assuming Room has a method to add enemies
        System.out.println("A " + newSkeleton.getName() + " has joined the battle!");
    }

    private void startSummoningTimer() {
        System.out.println(getName() + " begins to summon skeletons... It will take 2 turns!");
        isSummoning = true;
        turnsRemaining = 2; // Set the timer for 2 turns
    }

    private void handleSummoning(Game game, Room room) {
        turnsRemaining--;
        System.out.println(getName() + " is summoning skeletons! Turns remaining: " + turnsRemaining);

        if (turnsRemaining <= 0) {
            for (int i = 0; i < 3; i++) {
                summonSkeleton(game, room); // Summon 3 new skeletons
            }
            isSummoning = false; // Reset summoning state
        }
    }
}