package GameV2;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class GhostSupremeMage extends Entity {
    private static final int BASE_HEALTH = 22222;
    private static final int BASE_ATTACK = 0;  // No attack
    private static final int BASE_DEFENSE = 50;
    private final List<String> escapeMessages;
    private final Map<Integer, String> healthMessages;
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";

    // Смайлики
    private static final String EVIL_SMILE = "\uD83D\uDE08";
    private static final String FIRE = "\uD83D\uDD25";
    private static final String GHOST = "\uD83D\uDC7B";
    private static final String SKULL = "\uD83D\uDC80";
    private static final String LIGHTNING = "⚡";
    private static final String CRYSTAL_BALL = "\uD83D\uDD2E";

    public GhostSupremeMage() {
        super("Призрак Верховного Мага", 0, BASE_HEALTH, 0, BASE_ATTACK, BASE_DEFENSE);
        escapeMessages = initializeEscapeMessages();
        healthMessages = initializeHealthMessages();
    }

    private List<String> initializeEscapeMessages() {
        List<String> messages = new ArrayList<>();
        messages.add(RED + getName() + " растворяется в воздухе со зловещим смехом: 'Ты думаешь, что сможешь меня поймать? Наивный смертный!'" + EVIL_SMILE + RESET);
        messages.add(PURPLE + getName() + " исчезает в вихре темной энергии: 'Твоя погоня бессмысленна! Этот замок - мой лабиринт!'" + GHOST + RESET);
        messages.add(YELLOW + "'Ха-ха-ха! Беги за мной, глупец!' - " + getName() + " растворяется в тенях." + SKULL + RESET);
        messages.add(BLUE + getName() + " превращается в туман: 'Твоя настойчивость похвальна, но бесполезна!'" + CRYSTAL_BALL + RESET);
        messages.add(GREEN + "'Этот замок станет твоей могилой!' - кричит " + getName() + ", исчезая в темноте." + LIGHTNING + RESET);
        messages.add(CYAN + getName() + " рассыпается на тысячи светящихся частиц: 'Продолжай преследование, если осмелишься!'" + FIRE + RESET);
        messages.add(RED + "'Ты силен, но я - часть этого замка!' - " + getName() + " просачивается сквозь стены." + GHOST + RESET);
        messages.add(PURPLE + getName() + " создает портал и исчезает в нем: 'Поиграем в кошки-мышки, смертный!'" + EVIL_SMILE + RESET);
        messages.add(YELLOW + "'Твоя храбрость граничит с глупостью!' - смеется " + getName() + ", растворяясь в воздухе." + SKULL + RESET);
        messages.add(BLUE + getName() + " окутывается тьмой: 'Каждый шаг приближает тебя к твоей судьбе!'" + CRYSTAL_BALL + RESET);
        messages.add(GREEN + "'Ты думаешь, что загнал меня? Как наивно!' - " + getName() + " исчезает в вспышке призрачного света." + LIGHTNING + RESET);
        messages.add(CYAN + getName() + " превращается в стаю воронов: 'Твое упорство не спасет тебя от неизбежного!'" + FIRE + RESET);
        messages.add(RED + "'Этот замок хранит больше секретов, чем ты можешь представить!' - " + getName() + " растворяется в тенях." + GHOST + RESET);
        messages.add(PURPLE + getName() + " создает иллюзорные копии себя: 'Найди меня, если сможешь!' - и все они исчезают." + EVIL_SMILE + RESET);
        messages.add(YELLOW + "'Твоя сила растет... Но этого недостаточно!' - " + getName() + " распадается на сгустки темной энергии." + SKULL + RESET);
        messages.add(BLUE + getName() + " усмехается, ускользая: 'Ты думаешь, что знаешь меня? Я - тень, которой нет!'" + CRYSTAL_BALL + RESET);
        messages.add(GREEN + getName() + " испаряется, оставляя за собой лишь шепот: 'Неужели ты веришь, что сможешь меня поймать?'" + LIGHTNING + RESET);
        messages.add(CYAN + "'Лишь только ветер знает мой путь!' - восклицает " + getName() + ", исчезая в облаке мрака." + FIRE + RESET);
        messages.add(RED + getName() + " развлеченно парит в воздухе: 'Ты следишь за моей тенью, но я уже за пределами!'" + GHOST + RESET);
        messages.add(PURPLE + "'Ты настойчив, но утишение - не твоя цель!' - смеется " + getName() + ", исчезая в ярком вихре." + EVIL_SMILE + RESET);
        messages.add(YELLOW + getName() + " забирается в облако черного дыма: 'Игры только начинаются, смертный!'" + SKULL + RESET);
        messages.add(BLUE + "'Я веду с тобой танец, и это ты - лишь тень!' - говорит " + getName() + ", растворяясь в воздухе." + CRYSTAL_BALL + RESET);
        messages.add(GREEN + getName() + " растекается по земле: 'Каждый шаг приближает тебя к проклятию!'" + LIGHTNING + RESET);
        messages.add(CYAN + "'Здесь, в этих стенах, я - повелитель!' - насмехается " + getName() + ", исчезая в тени." + FIRE + RESET);
        messages.add(RED + getName() + " недоуменно смеется: 'Попробуй уловить мой след - но скорее всего, это тебе не удастся!'" + GHOST + RESET);
        messages.add(PURPLE + getName() + " превращается в клуб дыма: 'Как же ты скучен в своем упорстве! Оставь эту идею!'" + EVIL_SMILE + RESET);
        messages.add(YELLOW + "'Теплота твоих усилий лишь остывает!' - с явным удовольствием говорит " + getName() + ", растворяясь в ночи." + SKULL + RESET);
        messages.add(BLUE + getName() + " исчезает в вихре синих огней: 'Твое время истекает, смертный!'" + CRYSTAL_BALL + RESET);
        messages.add(GREEN + getName() + " заигрывает с тенями: 'Что ты собираешься делать? Мечтать о победе?'" + LIGHTNING + RESET);
        messages.add(CYAN + "'Смех – это моя правота!' - восклицает " + getName() + ", с шутливой дерзостью растворяясь в темноте." + FIRE + RESET);
        messages.add(RED + getName() + " распадается на пыль: 'У тебя не хватит смелости, чтобы стать моим соперником!'" + GHOST + RESET);
        messages.add(PURPLE + getName() + " волшебным движением заставляет стены дрожать: 'Каждое движение - шаг к твоему краху!'" + EVIL_SMILE + RESET);
        messages.add(YELLOW + "'Ты на грани откровения, но не осознаешь этого!' - говорит " + getName() + ", исчезая как сон." + SKULL + RESET);
        messages.add(BLUE + getName() + " поднимается в воздух: 'Попробуй дуновение ветра, но знай - это лишь иллюзия!'" + CRYSTAL_BALL + RESET);

        return messages;
    }

    private static final String LAUGHING = "\uD83D\uDE06";
    private static final String SMIRKING = "\uD83D\uDE0F";
    private static final String MOSQUITO = "\uD83E\uDD9F";
    private static final String THINKING = "\uD83E\uDD14";
    private static final String HOURGLASS = "⏳";
    private static final String CLAPPING = "\uD83D\uDC4F";
    private static final String EYES = "\uD83D\uDC40";
    private static final String YAWNING = "\uD83E\uDD71";
    private static final String FLEXED_BICEPS = "\uD83D\uDCAA";
    private static final String ANNOYED = "\uD83D\uDE10";
    private static final String RAISED_EYEBROW = "\uD83E\uDD28";
    private static final String MAGNIFYING_GLASS = "\uD83D\uDD0D";
    private static final String ANGRY = "\uD83D\uDE21";
    private static final String QUESTIONING = "\u2753";
    private static final String SPARKLES = "✨";
    private static final String SERIOUS = "\uD83D\uDE11";
    private static final String SURPRISED = "\uD83D\uDE2E";
    private static final String LIGHTBULB = "\uD83D\uDCA1";
    private static final String STAR = "⭐";
    private static final String TROPHY = "\uD83C\uDFC6";
    private static final String RAGE = "\uD83D\uDE21";
    private static final String NAUSEATED = "\uD83E\uDD22";
    private static final String BROKEN_CHAIN = "⛓️";
    private static final String WARNING = "⚠️";
    private static final String SHOCKED = "\uD83D\uDE31";
    private static final String CHART_INCREASING = "\uD83D\uDCC8";
    private static final String UNLOCKED = "\uD83D\uDD13";
    private static final String SLEEPING = "\uD83D\uDE34";
    private static final String MAGIC_WAND = "\uD83E\uDE84";
    private static final String DIZZY = "\uD83D\uDE35";
    private static final String CYCLONE = "\uD83C\uDF00";
    private static final String HOUSE_CRUMBLING = "\uD83C\uDFDA";
    private static final String ASTONISHED = "\uD83D\uDE32";
    private static final String DROPLET = "\uD83D\uDCA7";
    private static final String COLD_SWEAT = "\uD83D\uDE30";
    private static final String EXPLODING_HEAD = "\uD83E\uDD2F";
    private static final String PERSON_RAISING_HAND = "\uD83D\uDE4B";
    private static final String BOMB = "\uD83D\uDCA3";
    private static final String SCREAM = "\uD83D\uDE31";
    private static final String BROKEN_HEART = "\uD83D\uDC94";
    private static final String SUNRISE = "\uD83C\uDF05";
    private static final String CROSSED_SWORDS = "⚔️";


    private Map<Integer, String> initializeHealthMessages() {
        Map<Integer, String> messages = new HashMap<>();
        messages.put(99, RED + "Ты посмел поднять на меня руку? Как забавно!" + EVIL_SMILE + RESET);
        messages.put(98, PURPLE + "Это щекотно, продолжай, смертный!" + LAUGHING + RESET);
        messages.put(97, YELLOW + "О, ты действительно пытаешься меня ранить? Как мило!" + SMIRKING + RESET);
        messages.put(96, BLUE + "Твои атаки подобны укусам комара!" + MOSQUITO + RESET);
        messages.put(95, GREEN + "Неужели ты думаешь, что можешь причинить мне боль?" + THINKING + RESET);
        messages.put(94, CYAN + "Я веками накапливал силу, а ты пытаешься победить меня?" + HOURGLASS + RESET);
        messages.put(93, RED + "Твоя самонадеянность поистине впечатляет!" + CLAPPING + RESET);
        messages.put(92, PURPLE + "Продолжай, мне даже интересно на что ты способен." + EYES + RESET);
        messages.put(91, YELLOW + "Ха! Я почти чувствую твои удары. Почти..." + YAWNING + RESET);
        messages.put(90, BLUE + "Десятая часть моей силы? Неплохо, но недостаточно!" + FLEXED_BICEPS + RESET);
        messages.put(89, GREEN + "Твоё упорство начинает раздражать меня." + ANNOYED + RESET);
        messages.put(88, CYAN + "Возможно, ты не так слаб, как я думал изначально." + THINKING + RESET);
        messages.put(87, RED + "Интересно... Давно никто не был столь настойчив." + RAISED_EYEBROW + RESET);
        messages.put(86, PURPLE + "Твоя сила... В ней есть что-то знакомое." + MAGNIFYING_GLASS + RESET);
        messages.put(85, YELLOW + "Пятнадцать процентов... Ты начинаешь меня злить!" + ANGRY + RESET);
        messages.put(84, BLUE + "Твоя техника... Кто научил тебя этому?" + QUESTIONING + RESET);
        messages.put(83, GREEN + "Я чувствую древнюю магию в твоих атаках." + SPARKLES + RESET);
        messages.put(82, CYAN + "Возможно, мне стоит отнестись к тебе серьёзнее." + SERIOUS + RESET);
        messages.put(81, RED + "Ты заставляешь меня вспомнить былые времена..." + HOURGLASS + RESET);
        messages.put(80, PURPLE + "Пятая часть моей силы... Впечатляюще!" + SURPRISED + RESET);
        messages.put(79, YELLOW + "Твоя решимость... Она напоминает мне кое-кого." + THINKING + RESET);
        messages.put(78, BLUE + "Я начинаю понимать, почему ты добрался так далеко." + LIGHTBULB + RESET);
        messages.put(77, GREEN + "В тебе есть потенциал, этого нельзя отрицать." + STAR + RESET);
        messages.put(76, CYAN + "Ты сильнее многих, кто пытался меня победить." + TROPHY + RESET);
        messages.put(75, RED + "Четверть моей силы... Теперь я действительно зол!" + RAGE + RESET);
        messages.put(74, PURPLE + "Твоя магия... Она противна самой природе!" + NAUSEATED + RESET);
        messages.put(73, YELLOW + "Я чувствую, как древние печати слабеют..." + BROKEN_CHAIN + RESET);
        messages.put(72, BLUE + "Ты понимаешь, с какими силами играешь?" + WARNING + RESET);
        messages.put(71, GREEN + "Этот замок станет твоей могилой!" + SKULL + RESET);
        messages.put(70, CYAN + "Тридцать процентов... Невозможно!" + SHOCKED + RESET);
        messages.put(69, RED + "Твоя сила растёт... Как это возможно?" + CHART_INCREASING + RESET);
        messages.put(68, PURPLE + "Я чувствую, как печати древних магов слабеют..." + UNLOCKED + RESET);
        messages.put(67, YELLOW + "Ты пробуждаешь то, что должно было остаться спящим!" + SLEEPING + RESET);
        messages.put(66, BLUE + "Древняя магия... Она откликается на твой зов!" + MAGIC_WAND + RESET);
        messages.put(65, GREEN + "Больше трети моей силы... Это не должно было случиться!" + DIZZY + RESET);
        messages.put(64, CYAN + "Я чувствую, как реальность искажается вокруг нас!" + CYCLONE + RESET);
        messages.put(63, RED + "Стены замка дрожат от нашей битвы!" + HOUSE_CRUMBLING + RESET);
        messages.put(62, PURPLE + "Твоя сила превосходит всё, что я видел за века!" + ASTONISHED + RESET);
        messages.put(61, YELLOW + "Магия древних... Она течёт в твоих венах!" + DROPLET + RESET);
        messages.put(60, BLUE + "Сорок процентов... Я недооценил тебя!" + COLD_SWEAT + RESET);
        messages.put(59, GREEN + "Печати... Они рвутся! Что ты наделал?!" + EXPLODING_HEAD + RESET);
        messages.put(58, CYAN + "Сила предков... Она пробуждается в тебе!" + PERSON_RAISING_HAND + RESET);
        messages.put(57, RED + "Ты не понимаешь, что высвобождаешь!" + BOMB + RESET);
        messages.put(56, PURPLE + "Древнее пророчество... Неужели ты..." + CRYSTAL_BALL + RESET);
        messages.put(55, YELLOW + "Почти половина... Нет, это невозможно!" + SCREAM + RESET);
        messages.put(54, BLUE + "Я чувствую, как моя сущность распадается!" + BROKEN_HEART + RESET);
        messages.put(53, GREEN + "Печати древних магов... Они откликаются на твой зов!" + MAGIC_WAND + RESET);
        messages.put(52, CYAN + "Сила, запечатанная веками... Она пробуждается!" + SUNRISE + RESET);
        messages.put(51, RED + "Последний рубеж... Готовься к настоящей битве!" + CROSSED_SWORDS + RESET);

        return messages;
    }

    @Override
    public void performAction(Game game, Room room) {
        int healthPercentage = (int) ((getHealth() * 100.0) / getMaxHealth());

        if (healthMessages.containsKey(healthPercentage)) {
            System.out.println(getName() + ": " + healthMessages.get(healthPercentage));
        }
        if (getHealth() <= getMaxHealth() * 0.5) {
            System.out.println(getName() + " понимает что это тупик, и бежать некуда! Приготовься, игрок.");
            room.removeEnemy(this);
            game.setGhostSupremeMageBeaten(true);
            game.prepareFinalBattle(room);
        } else {
            escape(game, room);
        }
    }

    private void escape(Game game, Room room) {
        String escapeMessage = escapeMessages.get(random.nextInt(escapeMessages.size()));
        System.out.println(escapeMessage);
        room.removeEnemy(this);
        Room nextRoom = game.getCurrentRoom().getNextRoom();
        if (nextRoom != null) {
            nextRoom.addEnemy(this);
        }
    }

    @Override
    public void dropLoot(Game game) {
        // No loot drop
    }

    @Override
    public void attack(Entity target, Weapon weapon) {
        // Ghost doesn't attack
    }
}