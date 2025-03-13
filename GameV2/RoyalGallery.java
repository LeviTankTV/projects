package GameV2;

import java.util.Random;
import java.util.Scanner;

public class RoyalGallery extends Room {
    private Random random;

    public RoyalGallery() {
        super();
        this.random = new Random();
    }

    @Override
    public void playerTurn(Game game, Room room) {
        Player player = game.getPlayer();
        Scanner scanner = new Scanner(System.in);

        describeGallery();

        System.out.println("\nЧто вы хотите сделать в Королевской Галерее?");
        System.out.println("1. Осмотреть картины");
        System.out.println("2. Искать ценности");
        System.out.println("3. Исследовать тайный проход");
        System.out.println("4. Покинуть Галерею");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        switch (choice) {
            case 1:
                inspectPaintings(player);
                System.out.println("Вы покидаете Королевскую Галерею.");
                handleEmptyRoom(game, this);
                break;
            case 2:
                searchForTreasures(player);
                System.out.println("Вы покидаете Королевскую Галерею.");
                handleEmptyRoom(game, this);
                break;
            case 3:
                exploreSecretPassage(player);
                System.out.println("Вы покидаете Королевскую Галерею.");
                handleEmptyRoom(game, this);
                break;
            case 4:
                System.out.println("Вы покидаете Королевскую Галерею.");
                handleEmptyRoom(game, this);
                break;
            default:
                System.out.println("Неверный выбор. Вы покидаете Королевскую Галерею.");
                handleEmptyRoom(game, this);
        }
    }

    private void describeGallery() {
        System.out.println("Вы входите в Королевскую Галерею. Стены увешаны величественными портретами и пейзажами.");
        System.out.println("Золотые рамы блестят в свете хрустальных люстр.");
    }

    private void inspectPaintings(Player player) {
        if (random.nextDouble() < 0.5) {
            int expGain = random.nextInt(400) + 100;
            player.gainExperience(expGain);
            System.out.println("Изучая картины, вы получаете новые знания! +" + expGain + " опыта.");
        } else {
            System.out.println("Вы рассматриваете картины. Очень познавательно, но ничего особенного не находите.");
        }
    }

    private void searchForTreasures(Player player) {
        if (random.nextDouble() < 0.5) {
            int goldFound = random.nextInt(200) + 50;
            player.setGold(player.getGold() + goldFound);
            System.out.println("Вы нашли тайник с золотом! +" + goldFound + " золота.");
        } else {
            System.out.println("Вы ищете ценности, но ничего не находите.");
        }
    }

    private void exploreSecretPassage(Player player) {
        if (random.nextDouble() < 0.5) {
            int manaGain = random.nextInt(100) + 25;
            player.setMana(player.getMana() + manaGain);
            System.out.println("В тайном проходе вы находите древний источник магии! +" + manaGain + " маны.");
        } else {
            System.out.println("Вы исследуете проход, но ничего интересного не обнаруживаете.");
        }
    }
}