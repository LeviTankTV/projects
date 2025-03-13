package GameV2;

import java.util.Random;
import java.util.Scanner;

public class AllyRoom extends Room {
    private Ally ally; // The ally that the player can recruit

    public AllyRoom(Game game) {
        super();
        generateAlly(game); // Generate an ally upon room creation
    }

    private static final String RESET = "\u001B[0m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";

    private static final String Allies = """
            \033[0;36m
            
            |    ~@~      ~@~                                            ~@~      ~@~    |
            |\\__/\\__/\\__/\\__/\\__/\\__/\\__/\\__/\\__/\\__/\\__/\\__/\\__/\\__/\\__/\\__/\\__/\\__/\\__/\\__/\s
            |. . . . . . . . . . .[___|___|___|___|___|__]. . . . . . . . . . . . . . . .|
            | . . . . . . . . . . [_|___|___|___|___|___|] . . . . . . . . . . . . . . . |
            |. . . . . . . . . . .[___|___|___|___|___|__]. . . . . . . . . . . . . . . .|
            | . . . .  _  . . . . [_|___|___|___|___|___|] . .  _  . . . . . . .  _  . . |
            |. . . .  /_\\  . . . .[__|___|___|___|___|___]. .  /_\\  . . . . . .  /_\\  . |
            | . . . . =|= . . . . [_|___/          \\___|_] . . =|= . . . . . . . =|= . . |
            |. . . . . . . . . . .[|___| ~  Allies ~ |___|]. . . . . . . . . . . . . . . .|
            |=====================[__|__\\__________/_|___]================____===========|
            |                     [___|___|___|___|___|__]               | |  \\          |
            |           ,         [_|___|___|___|___|___|]               | |   \\_______  |
            |          ,I,    ,;,/________________________\\,;,          _|_|___________) |
            |/|   ____;(;);__;(;);                        ;(;); /|     /   | ,.________) |
            |||__ !!!!!;;;!!!!=;============================;=  ||__  /____|/ .________| |
            ||/_/|!!!!!!;!!!!!![_|_|_]================[_|_|_]___|/_/|_|______/_______)(__lc
            /|' |'  |'     '|  [__|__]       `(       [__|__]   |' |'[|)(            ()   \\
             '  '   '       '  [_|_|_] o     ) (    o [_|_|_]   '  '   ()                  \s
                               [__|__] |    ( ) )   | [__|__]                      ,
                               [_|_|_] |---@@@@@@---| [_|_|_]           /|        ,I,       |\\  \s
                               [__|__]/!\\ @@@@@@@@ /!\\[__|__]           ||   ____;(;);____  ||
                              /______________________________\\          ||__ !!!!!;;;!!!!!__||
               ,             |________________________________|         |/_/|!!!!!!;!!!!!!\\_\\|
              ,I,       |\\   `================================`         || ||  ||     || || ||
            _;(;);____  ||  `==================================`        |  |   |       |  |  |
            !!;;;!!!!!__|| `====================================`
            !!!;!!!!!!\\_\\|
            \033[0m
            """;

    // Generate a random ally based on the player's level
    private void generateAlly(Game game) {
        Player player = game.getPlayer();
        AllyFactory allyFactory = new AllyFactory(); // Create an AllyFactory instance
        this.ally = allyFactory.createRandomAlly(player, game); // Create a random ally
    }

    private static final String[] ALLY_PHRASES = {
            "Я могу помочь тебе, если ты решишь меня нанять.",
            "Я готов сразиться на твоей стороне!",
            "Если ты меня примешь, я буду твоим верным союзником.",
            "Я обладаю мощными способностями, которые могут пригодиться.",
            "Согласись на мою помощь, и мы одолеем врагов вместе!",
            "Я здесь, чтобы поддержать тебя в бою.",
            "С тобой я чувствую себя сильнее!",
            "Дай мне шанс, и я докажу свою преданность.",
            "Я могу быть твоими глазами и ушами в этом мире.",
            "С помощью моей магии мы сможем победить любого врага.",
            "Я вижу в тебе великого лидера, и хочу следовать за тобой.",
            "Прими меня в свою команду, и я сделаю все возможное для победы.",
            "Я могу дать тебе советы и помочь в бою.",
            "Давай объединим наши силы для достижения цели!",
            "Я готов сражаться за тебя, если ты мне доверишься."
    };

    private static final String[] WEAK_GOBLIN_PHRASES = {
            "Я не очень силен, но могу помочь!",
            "Я могу отвлечь врагов, если ты мне позволишь.",
            "Я не так силен, как другие, но я постараюсь!",
            "Я могу быть полезен, если ты меня примешь.",
            "Я могу помочь тебе, но только если ты не будешь слишком строгим."
    };

    private static final String[] DELICATE_FAIRY_PHRASES = {
            "Я могу использовать магию, чтобы помочь тебе.",
            "Моя сила не в бою, но я могу исцелить тебя.",
            "Я маленькая, но могу быть полезной в трудную минуту.",
            "Дай мне шанс, и я покажу, на что способна!",
            "Я могу создать иллюзии, чтобы запутать врагов."
    };

    private static final String[] SHADOWY_IMP_PHRASES = {
            "Я могу быть незаметным, если ты меня возьмешь.",
            "Я не самый сильный, но могу быть хитрым.",
            "Я могу помочь тебе, если ты не против немного поиграть.",
            "Я могу создать небольшие проблемы для врагов.",
            "Я не так силен, но могу быть полезен в хитрости."
    };

    private static final String[] ANCIENT_PIRATE_PHRASES = {
            "Arrr! Я могу помочь тебе найти сокровища!",
            "Я был на многих морях, и знаю, как сразиться!",
            "Собирай команду, и мы захватим этот мир!",
            "Я могу научить тебя искусству пиратства.",
            "Давай сделаем этот бой эпичным, как морское сражение!"
    };

    private static final String[] MANA_BEAST_PHRASES = {
            "Я могу использовать магию природы, чтобы помочь!",
            "Сила маны течет во мне, и я готов сразиться!",
            "Я могу призвать силы природы на твою сторону.",
            "Я могу стать твоим защитником, если ты мне доверишься.",
            "Моя магия может изменить ход битвы!"
    };

    private static final String[] WISE_MAGE_PHRASES = {
            "Знание — это сила. Я могу поделиться с тобой мудростью.",
            "Я могу научить тебя заклинаниям, если ты готов учиться.",
            "Моя магия поможет нам одолеть врагов.",
            "Слушай, и я открою тебе секреты древних.",
            "С помощью магии мы можем изменить судьбу!"
    };

    private static final String[] VAMPIRE_ALLY_PHRASES = {
            "Я могу дать тебе силу ночи, если ты будешь со мной.",
            "Моя жизнь вечна, и я готов сражаться за тебя.",
            "Я могу помочь тебе, но помни, я жажду крови.",
            "Сила темноты — это мой дар, и я готов поделиться ею.",
            "Я могу стать твоим самым верным союзником, если ты мне доверишься."
    };

    private static final String[] DWARF_WARRIOR_PHRASES = {
            "Я сражался с величайшими врагами, и готов помочь тебе!",
            "Сильный удар — это моя специальность!",
            "Я могу защитить тебя в бою, если ты станешь моим командиром.",
            "Давай сразимся вместе, как настоящие воины!",
            "Я не боюсь ни одного врага, если ты рядом."
    };

    private static final String[] NIMBLE_ROGUE_PHRASES = {
            "Я могу незаметно подойти к врагам и нанести удар.",
            "Скорость и ловкость — это мой стиль!",
            "Я могу помочь тебе в хитрости и обмане.",
            "Давай сделаем это быстро и тихо!",
            "Я могу быть твоими глазами в тени."
    };

    private static final String[] ELF_RANGER_PHRASES = {
            "Я могу вести тебя через леса и горы.",
            "Моя стрельба точна, как никогда!",
            "Я могу помочь тебе найти путь в этом мире.",
            "Прислушайся к природе, и она подскажет нам.",
            "Я могу быть твоим защитником в дикой природе."
    };

    private static final String[] LIGHT_BARD_PHRASES = {
            "Моя музыка вдохновляет и поднимает дух!",
            "Я могу сыграть мелодию, которая поможет нам в бою.",
            "Слушай мой голос, и ты получишь силу!",
            "Я могу рассказать истории, которые вдохновят нас на победу.",
            "Музыка — это моя магия, и я готов поделиться ею."
    };

    // Override playerTurn method to handle ally room interactions
    @Override
    public void playerTurn(Game game, Room room) {
        if (ally != null) {
            System.out.println("\n" + CYAN + "╔════════════════════════════════════════╗" + RESET);
            System.out.println(CYAN + "║         ВСТРЕЧА С СОЮЗНИКОМ            ║" + RESET);
            System.out.println(CYAN + "╚════════════════════════════════════════╝" + RESET);

            System.out.println(YELLOW + "➤ Вы встретили: " + ally.getName() +
                    " (Уровень: " + ally.getLevel() + ")" + RESET);

            String randomPhrase = selectRandomPhrase();
            displayAnimatedPhrase(randomPhrase);

            handleAllyRecruitment(game);
        } else {
            handleEmptyRoom(game, room);
        }
    }

    private void handleAllyRecruitment(Game game) {
        Player player = game.getPlayer();
        Scanner scanner = new Scanner(System.in);
        boolean continueChoosing = true;

        while (continueChoosing) {
            System.out.println("\n" + PURPLE + "◈ Выберите действие:" + RESET);
            System.out.println(BLUE + "  1. ⚔ Нанять " + ally.getName() + RESET);
            System.out.println(BLUE + "  2. ↩ Игнорировать и уйти" + RESET);

            System.out.print(CYAN + "\n➤ Ваш выбор: " + RESET);
            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        System.out.println("\n" + GREEN + "✧ " + ally.getName() +
                                " присоединяется к вашей команде! ✧" + RESET);
                        player.addAlly(ally);
                        ally = null;
                        continueChoosing = false;
                        break;
                    case 2:
                        System.out.println("\n" + YELLOW + "← Вы уходите, оставляя " +
                                ally.getName() + " позади..." + RESET);
                        ally = null;
                        continueChoosing = false;
                        break;
                    default:
                        System.out.println(RED + "✘ Неверный выбор! Пожалуйста, введите 1 или 2." + RESET);
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "✘ Пожалуйста, введите числовое значение (1 или 2)." + RESET);
            }
        }

        handleEmptyRoom(game, this);
    }

    private void displayAnimatedPhrase(String phrase) {
        System.out.println("\n" + CYAN + "╭────────────────────────────────────────" + RESET);
        System.out.print(CYAN + "│ " + RESET);
        for (char c : phrase.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(30); // Задержка между символами
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(CYAN  + RESET);
        System.out.println(CYAN + "╰─────────────────────────────────────────" + RESET);
        System.out.println(); // Дополнительная пустая строка для читаемости
    }

    private String selectRandomPhrase() {
        Random random = new Random();
        String selectedPhrase;

        if (ally instanceof WeakGoblin) {
            selectedPhrase = GREEN + "🍄 " + WEAK_GOBLIN_PHRASES[random.nextInt(WEAK_GOBLIN_PHRASES.length)] + RESET;
        } else if (ally instanceof DelicateFairy) {
            selectedPhrase = PURPLE + "🧚 " + DELICATE_FAIRY_PHRASES[random.nextInt(DELICATE_FAIRY_PHRASES.length)] + RESET;
        } else if (ally instanceof ShadowyImp) {
            selectedPhrase = BLUE + "👿 " + SHADOWY_IMP_PHRASES[random.nextInt(SHADOWY_IMP_PHRASES.length)] + RESET;
        } else if (ally instanceof AncientPirate) {
            selectedPhrase = YELLOW + "🏴‍☠️ " + ANCIENT_PIRATE_PHRASES[random.nextInt(ANCIENT_PIRATE_PHRASES.length)] + RESET;
        } else if (ally instanceof ManaBeast) {
            selectedPhrase = CYAN + "🐉 " + MANA_BEAST_PHRASES[random.nextInt(MANA_BEAST_PHRASES.length)] + RESET;
        } else if (ally instanceof WiseMage || ally instanceof GnomeWizard) {
            selectedPhrase = PURPLE + "🧙 " + WISE_MAGE_PHRASES[random.nextInt(WISE_MAGE_PHRASES.length)] + RESET;
        } else if (ally instanceof VampireAlly) {
            selectedPhrase = BLUE + "🧛 " + VAMPIRE_ALLY_PHRASES[random.nextInt(VAMPIRE_ALLY_PHRASES.length)] + RESET;
        } else if (ally instanceof DwarfWarrior) {
            selectedPhrase = YELLOW + "⚒️ " + DWARF_WARRIOR_PHRASES[random.nextInt(DWARF_WARRIOR_PHRASES.length)] + RESET;
        } else if (ally instanceof NimbleRogue) {
            selectedPhrase = GREEN + "🗡️ " + NIMBLE_ROGUE_PHRASES[random.nextInt(NIMBLE_ROGUE_PHRASES.length)] + RESET;
        } else if (ally instanceof ElfRanger) {
            selectedPhrase = GREEN + "🏹 " + ELF_RANGER_PHRASES[random.nextInt(ELF_RANGER_PHRASES.length)] + RESET;
        } else if (ally instanceof LightBard) {
            selectedPhrase = YELLOW + "🎵 " + LIGHT_BARD_PHRASES[random.nextInt(LIGHT_BARD_PHRASES.length)] + RESET;
        } else {
            selectedPhrase = BLUE + "💬 " + ALLY_PHRASES[random.nextInt(ALLY_PHRASES.length)] + RESET;
        }

        return selectedPhrase;
    }
}
