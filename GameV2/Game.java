package GameV2;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Game {

    private Room currentRoom;
    private Player player;
    private LinkedList<Room> visitedRooms;
    private boolean secondGamePart = false;
    private boolean thirdGamePart = false;
    private Scanner scanner;
    private boolean ghostSupremeMageBeaten = false;
    private boolean putFlowerAtKingGrave = false;
    private boolean putFlowerAtQueenGrave = false;
    private boolean putFlowerAtPrinceGrave = false;
    private boolean putFlowerAtPrincessGrave = false;
    private List<Achievement> achievements;
    private int monstersKilled = 0;
    private boolean isSecondGamePart = false;

    public List<Achievement> getAchievements() {
        return achievements;
    }

    // Метод для разблокировки достижения
    public void unlockAchievement(String achievementName) {
        for (Achievement achievement : achievements) {
            if (achievement.getName().equals(achievementName) && !achievement.isUnlocked()) {
                achievement.unlock();
                System.out.println("Достижение разблокировано: " + achievement.getName());
                break;
            }
        }
    }

    private void initializeAchievements() {
        achievements = new ArrayList<>();
        achievements.add(new Achievement("Первые шаги", "Начните новую игру"));
        achievements.add(new Achievement("Победитель", "Пройдите игру до конца"));
        achievements.add(new Achievement("Мастер магии", "Изучите все заклинания"));
        achievements.add(new Achievement("Богатый", "Накопите 10000+ монет"));
        achievements.add(new Achievement("Пир на весь мир", "Доберитесь до второго уровня подземелья"));
        achievements.add(new Achievement("Истребитель монстров", "Победите 1000 врагов"));
        achievements.add(new Achievement("Лучшие друзья", "Соберите 10 союзников"));
    }

    public void incrementMonstersKilled() {
        monstersKilled++;
        checkMonsterKillerAchievement();
    }

    public void checkRichAchievement() {
        if (player.getGold() >= 10000) {
            unlockAchievement("Богатый");
        }
    }

    public void checkSecondFloorAchievement() {
        if (isSecondGamePart) {
            unlockAchievement("Пир на весь мир");
        }
    }

    private void checkMonsterKillerAchievement() {
        if (monstersKilled >= 1000) {
            unlockAchievement("Истребитель монстров");
        }
    }

    public void checkBestFriendsAchievement() {
        if (player.getAllies().size() >= 10) {
            unlockAchievement("Лучшие друзья");
        }
    }


    public Game(Player player) {

        this.scanner = new Scanner(System.in);
        this.player = player;
        this.visitedRooms = new LinkedList<>();
        generateDungeon();
        currentRoom = visitedRooms.get(0);
        initializeAchievements();
    }

    public void saveGame(String filename) {
        try {
            File saveDir = new File("saves");
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }

            // Создаем полный путь к файлу
            File saveFile = new File(saveDir, filename + ".sav");

            // Если файл существует, удаляем его
            if (saveFile.exists()) {
                saveFile.delete();
            }

            // Создаем новый файл и сохраняем данные
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile))) {
                oos.writeObject(player);
                System.out.println("Игра успешно сохранена в файл: " + saveFile.getName());
            }
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении игры: " + e.getMessage());
        }
    }

    public void loadGame(String filename) {
        try {
            File saveFile = new File("saves", filename);
            if (!saveFile.exists()) {
                throw new FileNotFoundException("Файл сохранения не найден: " + filename);
            }

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile))) {
                Player loadedPlayer = (Player) ois.readObject();
                if (loadedPlayer != null) {
                    this.player = loadedPlayer;
                    System.out.println("Игра успешно загружена из файла: " + filename);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при загрузке игры: " + e.getMessage());
            throw new RuntimeException("Не удалось загрузить игру.");
        }
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isThirdGamePart() {
        return thirdGamePart;
    }

    public void setThirdGamePart(boolean thirdGamePart) {
        this.thirdGamePart = thirdGamePart;
    }

    public boolean isSecondGamePart() {
        return secondGamePart;
    }

    public void setSecondGamePart(boolean secondGamePart) {
        this.secondGamePart = secondGamePart;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public boolean isGhostSupremeMageBeaten() {
        return ghostSupremeMageBeaten;
    }

    public void setGhostSupremeMageBeaten(boolean ghostSupremeMageBeaten) {
        this.ghostSupremeMageBeaten = ghostSupremeMageBeaten;
    }

    public boolean isPutFlowerAtKingGrave() {
        return putFlowerAtKingGrave;
    }

    public void setPutFlowerAtKingGrave(boolean putFlowerAtKingGrave) {
        this.putFlowerAtKingGrave = putFlowerAtKingGrave;
    }

    public boolean isPutFlowerAtQueenGrave() {
        return putFlowerAtQueenGrave;
    }

    public void setPutFlowerAtQueenGrave(boolean putFlowerAtQueenGrave) {
        this.putFlowerAtQueenGrave = putFlowerAtQueenGrave;
    }

    public boolean isPutFlowerAtPrinceGrave() {
        return putFlowerAtPrinceGrave;
    }

    public void setPutFlowerAtPrinceGrave(boolean putFlowerAtPrinceGrave) {
        this.putFlowerAtPrinceGrave = putFlowerAtPrinceGrave;
    }

    public boolean isPutFlowerAtPrincessGrave() {
        return putFlowerAtPrincessGrave;
    }

    public void setPutFlowerAtPrincessGrave(boolean putFlowerAtPrincessGrave) {
        this.putFlowerAtPrincessGrave = putFlowerAtPrincessGrave;
    }

    public void prepareFinalBattle(Room room) {
        TrueThroneRoom finalRoom = new TrueThroneRoom();
        finalRoom.addEnemy(new SupremeMage());
        setCurrentRoom(finalRoom);
    }

    private void generateDungeon() {
        Room firstRoom;
        if (thirdGamePart) {
            firstRoom = new TrueCastleEntranceRoom();
        } else if (secondGamePart) {
            firstRoom = new CastleEntranceRoom(player);
        } else {
            firstRoom = new StartRoom(player);
        }

        visitedRooms.clear();
        visitedRooms.add(firstRoom);
        Room previousRoom = firstRoom;

        for (int i = 1; i < 600; i++) {
            Room newRoom;
            if (thirdGamePart) {
                newRoom = Room.generateThirdGamePartRandomRoom(this);
            } else if (secondGamePart) {
                newRoom = Room.generateSecondGamePartRandomRoom(this);
            } else {
                newRoom = Room.generateRandomRoom(this);
            }

            visitedRooms.add(newRoom);
            previousRoom.setNextRoom(newRoom);
            newRoom.setPreviousRoom(previousRoom);
            previousRoom = newRoom;
        }
        previousRoom.setNextRoom(null);
    }

    public void transitionToSecondPart() {
        secondGamePart = true;
        thirdGamePart = false;
        generateDungeon();
        currentRoom = visitedRooms.getFirst();
        System.out.println(GREEN_BOLD_BRIGHT + "Вы входите во вторую часть игры... 🚪" + RESET);
    }

    public void transitionToThirdPart() {
        thirdGamePart = true;
        secondGamePart = false;
        generateDungeon();
        currentRoom = visitedRooms.getFirst();
        System.out.println(GREEN_BOLD_BRIGHT + "Вы входите в третью часть игры... 🚪" + RESET);
    }

    public void start() {
        System.out.println(GREEN_BOLD_BRIGHT + "Вы выпрыгнули из портала и оказались на втором этаже подземелья. Осматриваясь, вы видите магазин." + RESET);

        while (player.isAlive()) {
            currentRoom.playerTurn(this, currentRoom);

            // Проверка перехода ко второй части
            if (currentRoom instanceof CastleEntranceRoom && !secondGamePart) {
                transitionToSecondPart();
            }

            // Проверка перехода к третьей части
            if (currentRoom instanceof TrueCastleEntranceRoom && !thirdGamePart) {
                transitionToThirdPart();
            }
            checkAchievements();
        }
    }

    public void checkAchievements() {
        if (player.getGold() >= 10000) {
            unlockAchievement(YELLOW_BOLD_BRIGHT + "Богатый" + RESET);
        }
        if (secondGamePart) {
            unlockAchievement(YELLOW_BOLD_BRIGHT + "Пир на весь мир" + RESET);
        }
        if (monstersKilled >= 1000) {
            unlockAchievement(YELLOW_BOLD_BRIGHT + "Истребитель монстров" + RESET);
        }
        if (player.getAllies().size() >= 10) {
            unlockAchievement(YELLOW_BOLD_BRIGHT + "Лучшие друзья" + RESET);
        }
        // Добавьте проверки для других достижений
    }

    // Методы перемещения
    public void moveForward() {
        Room nextRoom = currentRoom.getNextRoom();
        nextRoom.setEscaped(false); // Сбрасываем флаг escaped для новой комнаты
        if (nextRoom != null) {
            // Превращаем предыдущую комнату в NothingRoom
            int currentIndex = visitedRooms.indexOf(currentRoom);
            currentRoom = nextRoom;
            System.out.println(GREEN_BOLD_BRIGHT + "Вы находитесь в " + currentRoom.getClass().getSimpleName() + "." + RESET);
        } else {
            System.out.println(RED_BOLD_BRIGHT + "Следующая комната недоступна." + RESET);
        }
    }

    public void moveBackward() {
        Room previousRoom = currentRoom.getPreviousRoom();
        if (previousRoom != null) {
            currentRoom = previousRoom;
            System.out.println(GREEN_BOLD_BRIGHT + "Вы находитесь в " + currentRoom.getClass().getSimpleName() + "." + RESET);
        } else {
            System.out.println(RED_BOLD_BRIGHT + "Вы не можете вернуться назад." + RESET);
        }
    }

    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // Яркий зеленый
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // Яркий красный
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m"; // Яркий желтый
    public static final String RESET = "\033[0m";

    public Scanner getScanner() {
        return scanner;
    }

    public void goToRoom(int roomIndex) {
        if (roomIndex >= 0 && roomIndex < visitedRooms.size()) {
            currentRoom = visitedRooms.get(roomIndex);
        } else {
            System.out.println("Такой комнаты не существует.");
        }
    }

}

