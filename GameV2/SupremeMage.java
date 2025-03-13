package GameV2;

import java.util.*;

public class SupremeMage extends Entity {
    private GhostFactory GhostFactory = new GhostFactory();
    private Map<Integer, String> healthMessages;
    public SupremeMage() {
        super("Верховный Маг", 1000, 100000, 0, 56, 25);
        this.healthMessages = initializeHealthMessages();
        this.setDefense(75);
        this.setAttack(75000);
        this.setWeapon(new Reality());
    }
    private boolean ritualCompleted;
    private int phasesCount;
    private int currentPhase;
    public void executeSpecialEvent1(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ВЕРХОВНОЕ ЗАМОРАЖИВАНИЕ ===");
        sleep(2000);
        System.out.println("Верховный Маг поднимает свой посох с жестокой улыбкой...");
        sleep(1500);
        System.out.println("\"Посмотрим, как тебе понравится быть ледяной статуей некоторое время!\"");
        sleep(1500);

        player.addEffect(new FrozenEffect(5, "Заморожен"));

        for (int i = 1; i <= 5; i++) {
            System.out.println("\nХод " + i + " заморозки:");
            sleep(1000);
            System.out.println("Верховный Маг: \"ХАХАХАХА! *вытирает слезу* О, это бесценно!\"");
            sleep(1000);
            System.out.println("Вы остаетесь замороженным, не в состоянии двигаться...");
            sleep(1000);

            player.setAttack(0);
            System.out.println("Ваша сила атаки снижена до 0, пока вы заморожены!");
            sleep(1000);
        }

        System.out.println("\nЭффект заморозки проходит, но вы чувствуете себя глубоко униженным...");
        sleep(2000);
    }

    public void executeSpecialEvent2(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ВЕРХОВНАЯ ИЛЛЮЗИЯ ===");
        sleep(2000);
        System.out.println("Глаза Верховного Мага сверкают злорадным весельем...");
        sleep(1500);
        System.out.println("\"О, ты думаешь, что ОДИН Верховный Маг - это сложно? А как насчет...\"");
        sleep(1500);

        for (int i = 0; i < 99; i++) {
            room.addEnemy(new SupremeMage());
        }

        System.out.println("\nВерховный Маг: \"Почему бы тебе не проверить информацию о комнате сейчас? (͡° ͜ʖ ͡°)\"");
        sleep(2000);
        System.out.println("Тронный зал заполнен 100 идентичными Верховными Магами!");
        sleep(2000);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Нажмите Enter, чтобы проверить информацию о комнате...");
        scanner.nextLine();

        room.showEnemiesInfo(room);
        sleep(2000);

        System.out.println("\nВерховный Маг: \"ПСИХЕ! Просто шучу!\"");
        sleep(1500);

        List<Entity> enemies = new ArrayList<>(room.getEnemies());
        for (Entity enemy : enemies) {
            if (enemy != this) {
                room.removeEnemy(enemy);
            }
        }

        sleep(1000);
    }

    public void executeSpecialEvent3(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ВЕРХОВНОЕ ВОРОВСТВО ===");
        sleep(2000);
        System.out.println("Ухмылка Верховного Мага становится еще шире...");
        sleep(1500);

        int stolenGold = player.getGold();
        player.setGold(0);
        System.out.println("Верховный Маг: \"Йоу! Эти " + stolenGold + " золотых монет выглядят лучше в МОЕМ кармане!\"");
        sleep(1500);
        System.out.println("*Верховный Маг танцует с вашим кошельком*");
        sleep(2000);

        int stolenMana = player.getMana();
        player.setMana(0);
        System.out.println("\nВерховный Маг: \"О, и эту ману я тоже заберу!\"");
        sleep(1500);

        while (player.getLevel() > 1) {
            player.setLevel(player.getLevel() - 1);
        }

        System.out.println("\nВерховный Маг: \"Возвращайся на первый уровень, абсолютный ГЛУПЕЦ!\"");
        sleep(1500);
        System.out.println("\"Ты ДЕЙСТВИТЕЛЬНО думал, что можешь бросить мне вызов? МНЕ? ВЕРХОВНОМУ МАГУ?\"");
        sleep(1500);
        System.out.println("*Верховный Маг вытирает слезы смеха*");
        sleep(2000);
    }

    public void executeSpecialEvent4(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ВЕРХОВНОЕ ИЗДЕВАТЕЛЬСТВО ===");
        sleep(2000);
        System.out.println("Верховный Маг задумчиво поглаживает подбородок...");
        sleep(1500);

        int originalLevel = player.getLevel();
        while (player.getLevel() < originalLevel) {
            player.setLevel(player.getLevel() + 1);
        }

        System.out.println("Верховный Маг: \"Знаешь что? Вот тебе твой уровень обратно. Не то чтобы это тебе помогло! ХАХАХАХА!\"");
        sleep(2000);

        player.addEffect(new StunnedEffect(3, "Оглушен"));

        for (int i = 1; i <= 3; i++) {
            System.out.println("\nХод оглушения " + i + ":");
            sleep(1000);
            System.out.println("Верховный Маг: \"" + getRandomLaugh() + "\"");
            sleep(1000);
            System.out.println("Вы остаетесь оглушенным, чувствуя все большее смущение...");
            sleep(1000);
        }

        sleep(2000);
    }

    public void executeSpecialEvent5(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ВЕРХОВНАЯ ЖЕСТОКОСТЬ ===");
        sleep(2000);
        System.out.println("Выражение лица Верховного Мага становится мрачным...");
        sleep(1500);

        List<Ally> allies = player.getAllies();
        if (!allies.isEmpty()) {
            System.out.println("Верховный Маг: \"О, ты привел друзей? Как... неудачно для них.\"");
            sleep(1500);

            for (Entity ally : allies) {
                System.out.println("\nВерховный Маг обращает свой взор на " + ally.getName() + "...");
                sleep(1000);
                System.out.println("Верховный Маг: \"Прощай, ничтожество!\"");
                sleep(1000);
                ally.takeDamage(ally.getHealth());
                System.out.println(ally.getName() + " погибает от невыносимой магической силы!");
                sleep(1000);
            }

            player.clearAllies();
            System.out.println("\nВерховный Маг: \"Теперь ты один. Совсем один.\"");
            sleep(1500);
            System.out.println("*Злорадный смех эхом разносится по залу*");
            sleep(2000);
        } else {
            System.out.println("У вас нет союзников, которых можно было бы убить.");
            sleep(1000);
        }
    }

    public void executeSpecialEvent6(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ВЕРХОВНЫЙ ДИАЛОГ ===");
        sleep(2000);
        System.out.println("Верховный Маг принимает театральную позу...");
        sleep(1500);

        String[] dialogueOptions = {
                "Зачем ты это делаешь?",
                "Я победю тебя!",
                "Прошу, помилуй!",
                "*Молчать*"
        };

        System.out.println("\nВыберите ваш ответ:");
        for (int i = 0; i < dialogueOptions.length; i++) {
            System.out.println((i + 1) + ") " + dialogueOptions[i]);
        }

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        System.out.println("\nВы: \"" + dialogueOptions[choice - 1] + "\"");
        sleep(1000);
        System.out.println("\nВерховный Маг: \"*Зевает* Как предсказуемо...\"");
        sleep(1500);
        System.out.println("\"Знаешь, сколько героев сказали то же самое перед смертью?\"");
        sleep(2000);
    }

    public void executeSpecialEvent7(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ВЕРХОВНОЕ ОТРАЖЕНИЕ ===");
        sleep(2000);
        System.out.println("Верховный Маг создает магическое зеркало...");
        sleep(1500);

        System.out.println("Верховный Маг: \"Позволь показать тебе, как ты ДЕЙСТВИТЕЛЬНО выглядишь!\"");
        sleep(1500);
        System.out.println("\n*В зеркале вы видите себя, но почему-то выглядите жалко и слабо*");
        sleep(2000);

        player.addEffect(new WeakenedEffect(3, "Ослаблен"));
        System.out.println("\nВаша уверенность пошатнулась, атака временно снижена!");
        sleep(2000);
    }

    public void executeSpecialEvent8(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ВЕРХОВНАЯ ПЕТЛЯ ВРЕМЕНИ ===");
        sleep(2000);
        System.out.println("Верховный Маг щелкает пальцами...");
        sleep(1500);

        for (int i = 1; i <= 3; i++) {
            System.out.println("\nПетля времени " + i + ":");
            sleep(1000);
            System.out.println("Верховный Маг: \"Снова и снова и снова!\"");
            sleep(1000);
            player.takeDamage(50);
            System.out.println("Вы переживаете одну и ту же атаку снова!");
            sleep(1000);
        }

        sleep(2000);
    }

    public void executeSpecialEvent9(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ВЕРХОВНАЯ АУДИТОРИЯ ===");
        sleep(2000);
        System.out.println("Верховный Маг хлопает в ладоши...");
        sleep(1500);

        System.out.println("*Внезапно появляется толпа призрачных зрителей*");
        sleep(2000);
        System.out.println("Верховный Маг: \"Дамы и господа! Приветствуйте нашего сегодняшнего неудачника!\"");
        sleep(2500);

        System.out.println("\n*Призрачная толпа смеется и освистывает вас*");
        sleep(2000);
    }

    public void executeSpecialEvent10(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ВЕРХОВНОЕ УНИЖЕНИЕ ===");
        sleep(2000);
        System.out.println("Верховный Маг готовится к финальному акту...");
        sleep(1500);

        System.out.println("Верховный Маг: \"Знаешь что? Я даже не буду тебя убивать.\"");
        sleep(2000);
        System.out.println("\"Жить с этим позором будет куда более мучительно! ХАХАХАХА!\"");
        sleep(2500);

        player.setHealth(1);
        System.out.println("\nВаше здоровье снижено до 1, но вы всё еще живы...");
        sleep(2000);
        player.heal(200);
        sleep(1000);
    }

    public void executeSpecialEvent11(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: СТИХИЙНЫЙ ХАОС ===");
        sleep(2000);
        System.out.println("Верховный Маг поднимает руки, и вокруг вас начинают бушевать стихии...");
        sleep(1500);

        String[] elements = {"огонь", "лёд", "молния", "кислота"};
        for (String element : elements) {
            System.out.println("Вас атакует " + element + "!");
            sleep(1000);
            player.takeDamage(20);
        }

        System.out.println("\nВерховный Маг: \"Как тебе мой маленький парад стихий? " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent12(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ИГРЫ РАЗУМА ===");
        sleep(2000);
        System.out.println("Верховный Маг пристально смотрит вам в глаза...");
        sleep(1500);

        System.out.println("Верховный Маг: \"А теперь... ударь себя!\"");
        sleep(2000);
        System.out.println("*Вы чувствуете, как ваша рука поднимается против вашей воли*");
        sleep(1500);
        player.takeDamage(player.getAttack());
        System.out.println("Вы наносите себе урон своим же оружием!");
        sleep(2000);
    }

    public void executeSpecialEvent13(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ПРИЗРАКИ ПРОШЛОГО ===");
        sleep(2000);
        System.out.println("Верховный Маг взмахивает рукой, и вокруг вас появляются призрачные фигуры...");
        sleep(1500);

        System.out.println("Вы узнаёте лица тех, кого не смогли спасти в прошлом.");
        sleep(2000);
        System.out.println("Призраки: \"Почему ты нас бросил? Почему не спас?\"");
        sleep(2000);
        System.out.println("*Вы чувствуете, как ваша решимость ослабевает*");
        player.addEffect(new WeakenedEffect(3, "Подавлен воспоминаниями"));
        sleep(2000);
    }

    public void executeSpecialEvent14(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: УСКОРЕНИЕ ВРЕМЕНИ ===");
        sleep(2000);
        System.out.println("Верховный Маг щёлкает пальцами, и вы чувствуете, как время вокруг вас ускоряется...");
        sleep(1500);

        int damagePerTick = 10;
        for (int i = 1; i <= 5; i++) {
            System.out.println("Тик времени " + i + ": вы стареете на глазах!");
            player.takeDamage(damagePerTick);
            sleep(1000);
        }

        System.out.println("\nВерховный Маг: \"Годы проносятся мимо, не правда ли? " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent15(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ИСКАЖЕНИЕ РЕАЛЬНОСТИ ===");
        sleep(2000);
        System.out.println("Верховный Маг искривляет пространство вокруг вас...");
        sleep(1500);

        System.out.println("*Вы видите, как стены плавятся, а пол уходит из-под ног*");
        sleep(2000);
        System.out.println("Верховный Маг: \"Как тебе мой маленький мирок? Нравится?\"");
        sleep(2000);
        System.out.println("*От головокружения вы теряете равновесие и падаете*");
        sleep(2000);
    }

    public void executeSpecialEvent16(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ПОХИЩЕНИЕ ЭНЕРГИИ ===");
        sleep(2000);
        System.out.println("Верховный Маг простирает руку в вашу сторону...");
        sleep(1500);

        int drainedHealth = (int) (player.getHealth() / 2);
        player.takeDamage(drainedHealth);
        System.out.println("*Вы чувствуете, как ваша жизненная энергия утекает*");
        sleep(2000);
        System.out.println("Верховный Маг: \"Спасибо за подзарядку! " + getRandomLaugh() + "\"");
        sleep(2000);
        System.out.println("Верховный Маг восстанавливает " + drainedHealth + " очков здоровья.");
        sleep(1000);
    }

    public void executeSpecialEvent17(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ОБМЕН ДУШАМИ ===");
        sleep(2000);
        System.out.println("Верховный Маг проводит загадочный ритуал...");
        sleep(1500);

        double playerCurrentHealth = player.getHealth();
        double magCurrentHealth = this.getHealth();

        player.setHealth(Math.min(magCurrentHealth, player.getMaxHealth()));
        this.setHealth(Math.max(1, playerCurrentHealth)); // Маг не может умереть от этого события

        System.out.println("*Вы чувствуете, как ваша душа покидает тело*");
        sleep(2000);
        System.out.println("Верховный Маг: \"Теперь я знаю, каково быть тобой... И ты узнаешь, каково быть мной!\"");
        sleep(2000);
    }

    public void executeSpecialEvent18(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ПРОКЛЯТИЕ НЕМОЩИ ===");
        sleep(2000);
        System.out.println("Верховный Маг накладывает на вас древнее проклятие...");
        sleep(1500);

        int originalAttack = (int) player.getAttack();
        player.setAttack(Math.max(1, originalAttack / 2)); // Атака не может стать меньше 1
        System.out.println("*Ваша сила уменьшается вдвое!*");
        sleep(2000);

        System.out.println("Верховный Маг: \"Чувствуешь, как утекает твоя сила? " + getRandomLaugh() + "\"");
        player.addEffect(new WeakenedEffect(4, "Проклятие немощи"));
        sleep(2000);
    }

    public void executeSpecialEvent19(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ПРИЗЫВ КОШМАРОВ ===");
        sleep(2000);
        System.out.println("Верховный Маг погружает комнату во тьму...");
        sleep(1500);

        System.out.println("*Из теней появляются ваши худшие кошмары*");
        sleep(2000);

        int totalDamage = 0;
        for (int i = 0; i < 3; i++) {
            int damage = 15 + random.nextInt(10);
            totalDamage += damage;
            System.out.println("Кошмар наносит вам " + damage + " урона!");
            sleep(1000);
        }

        // Убедимся, что игрок не умрет от этого урона
        int safeDamage = (int) Math.min(totalDamage, player.getHealth() - 1);
        player.takeDamage(safeDamage);

        System.out.println("\nВерховный Маг: \"Сладких снов! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent20(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКОЕ ЗЕРКАЛО ===");
        sleep(2000);
        System.out.println("Верховный Маг создаёт магическое зеркало...");
        sleep(1500);

        System.out.println("*Любой урон, который вы наносите, отражается обратно на вас*");
        sleep(2000);

        int reflectedDamage = (int) Math.min(player.getAttack() * 2, player.getHealth() - 1);
        System.out.println("Ваша атака отражается и наносит вам " + reflectedDamage + " урона!");
        player.takeDamage(reflectedDamage);

        System.out.println("Верховный Маг: \"Как тебе твоя собственная сила? " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent21(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ФИНАЛЬНОЕ ЗАКЛИНАНИЕ ===");
        sleep(2000);
        System.out.println("Верховный Маг начинает читать древнее заклинание...");
        sleep(1500);

        System.out.println("*Воздух вокруг начинает искриться от магической энергии*");
        sleep(2000);

        int spellDamage = (int) Math.min(player.getMaxHealth() / 3, player.getHealth() - 1);
        System.out.println("Мощная волна магической энергии сбивает вас с ног!");
        player.takeDamage(spellDamage);

        System.out.println("Верховный Маг: \"Вот она - истинная сила архимага! " + getRandomLaugh() + "\"");
        sleep(2000);

        // Добавляем несколько негативных эффектов
        player.addEffect(new WeakenedEffect(2, "Магическое истощение"));
        sleep(1000);
    }

    public void executeSpecialEvent22(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ ВАКУУМ ===");
        sleep(2000);
        System.out.println("Верховный Маг создает зону магического вакуума...");
        sleep(1500);
        int manaDrain = player.getMana();
        player.setMana(0);
        System.out.println("Вы теряете " + manaDrain + " единиц маны!");
        System.out.println("Верховный Маг: \"Как тебе без магии, смертный? " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent23(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ПРОКЛЯТИЕ НЕУДАЧИ ===");
        sleep(2000);
        System.out.println("Верховный Маг накладывает на вас проклятие неудачи...");
        sleep(1500);
        int damageTaken = random.nextInt(15) + 5;
        player.takeDamage(damageTaken);
        System.out.println("Вы спотыкаетесь и получаете " + damageTaken + " урона!");
        System.out.println("Верховный Маг: \"Удача отвернулась от тебя! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent24(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МЕНТАЛЬНОЕ СМЯТЕНИЕ ===");
        sleep(2000);
        System.out.println("Верховный Маг атакует ваш разум...");
        sleep(1500);
        int manaDrain = player.getMana() / 2;
        player.setMana(player.getMana() - manaDrain);
        System.out.println("Вы теряете " + manaDrain + " единиц маны из-за ментального смятения!");
        System.out.println("Верховный Маг: \"Твой разум слаб передо мной! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent25(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: КОСМИЧЕСКОЕ ОХЛАЖДЕНИЕ ===");
        sleep(2000);
        System.out.println("Верховный Маг призывает силы космического холода...");
        sleep(1500);
        int damageTaken = random.nextInt(20) + 10;
        player.takeDamage(damageTaken);
        System.out.println("Космический холод пронизывает вас, нанося " + damageTaken + " урона!");
        System.out.println("Верховный Маг: \"Почувствуй холод вселенной! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent26(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ВРЕМЕННОЙ СДВИГ ===");
        sleep(2000);
        System.out.println("Верховный Маг манипулирует временем вокруг вас...");
        sleep(1500);
        int healAmount = random.nextInt(20) + 10;
        player.heal(healAmount);
        System.out.println("Ваши раны заживают, восстанавливая " + healAmount + " очков здоровья!");
        System.out.println("Верховный Маг: \"Время - всего лишь иллюзия в моих руках! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent27(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ГРАВИТАЦИОННЫЙ ВСПЛЕСК ===");
        sleep(2000);
        System.out.println("Верховный Маг искажает гравитацию в комнате...");
        sleep(1500);
        int damageTaken = random.nextInt(25) + 15;
        player.takeDamage(damageTaken);
        System.out.println("Вас прижимает к полу, нанося " + damageTaken + " урона!");
        System.out.println("Верховный Маг: \"Почувствуй тяжесть своего ничтожества! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent28(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ЭНЕРГЕТИЧЕСКИЙ ВЗРЫВ ===");
        sleep(2000);
        System.out.println("Верховный Маг концентрирует огромное количество энергии...");
        sleep(1500);
        int damageTaken = random.nextInt(30) + 20;
        player.takeDamage(damageTaken);
        System.out.println("Энергетический взрыв сотрясает комнату, нанося вам " + damageTaken + " урона!");
        System.out.println("Верховный Маг: \"Узри мою истинную мощь! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent29(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ ВАМПИРИЗМ ===");
        sleep(2000);
        System.out.println("Верховный Маг начинает высасывать вашу жизненную энергию...");
        sleep(1500);
        int drainAmount = (int) (player.getMaxHealth() / 6);
        player.takeDamage(drainAmount);
        System.out.println("Вы теряете " + drainAmount + " очков здоровья!");
        System.out.println("Верховный Маг восстанавливает " + (drainAmount * 2) + " очков здоровья!");
        // Здесь нужно добавить метод для восстановления здоровья Верховного Мага
        System.out.println("Верховный Маг: \"Твоя жизненная сила теперь принадлежит мне! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent30(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ИСКАЖЕНИЕ РЕАЛЬНОСТИ ===");
        sleep(2000);
        System.out.println("Верховный Маг искажает ткань реальности вокруг вас...");
        sleep(1500);
        System.out.println("*Мир вокруг начинает плавиться и изменяться*");
        sleep(2000);
        int realityDamage = random.nextInt(20) + 10;
        player.takeDamage(realityDamage);
        System.out.println("Искажение реальности наносит вам " + realityDamage + " урона!");
        System.out.println("Верховный Маг: \"Как тебе нравится моя версия реальности? " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent31(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ПРИЗЫВ АСТРАЛЬНЫХ ОСКОЛКОВ ===");
        sleep(2000);
        System.out.println("Верховный Маг призывает светящиеся астральные осколки...");
        sleep(1500);
        int damage = random.nextInt(10) + 5; // Небольшой урон
        player.takeDamage(damage);
        int manaGain = random.nextInt(15) + 5;
        player.setMana(player.getMana() + manaGain);
        System.out.println("Осколки наносят " + damage + " урона, но вы поглощаете их энергию и получаете " + manaGain + " маны!");
        System.out.println("Верховный Маг: \"Сила космоса непредсказуема! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent32(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ВРЕМЕННОЙ ПАРАДОКС ===");
        sleep(2000);
        System.out.println("Верховный Маг создает временной парадокс...");
        sleep(1500);
        int healing = random.nextInt(20) + 10;
        player.heal(healing);
        System.out.println("Ваши раны затягиваются, восстанавливая " + healing + " здоровья!");
        System.out.println("Верховный Маг: \"Время лечит... или калечит! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent33(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ЭНЕРГЕТИЧЕСКИЙ ОБМЕН ===");
        sleep(2000);
        System.out.println("Верховный Маг инициирует энергетический обмен...");
        sleep(1500);
        int healthLoss = random.nextInt(15) + 5;
        player.takeDamage(healthLoss);
        int manaGain = healthLoss * 2;
        player.setMana(player.getMana() + manaGain);
        System.out.println("Вы теряете " + healthLoss + " здоровья, но получаете " + manaGain + " маны!");
        System.out.println("Верховный Маг: \"Баланс должен соблюдаться! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent34(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ РЕЗОНАНС ===");
        sleep(2000);
        System.out.println("Верховный Маг создает магический резонанс...");
        sleep(1500);
        int manaBonus = random.nextInt(25) + 15;
        player.setMana(player.getMana() + manaBonus);
        System.out.println("Магический резонанс усиливает вашу ману на " + manaBonus + " единиц!");
        System.out.println("Верховный Маг: \"Почувствуй силу магии! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent35(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ПРОСТРАНСТВЕННАЯ АНОМАЛИЯ ===");
        sleep(2000);
        System.out.println("Верховный Маг искривляет пространство...");
        sleep(1500);
        int damage = random.nextInt(12) + 8;
        player.takeDamage(damage);
        int healing = random.nextInt(15) + 5;
        player.heal(healing);
        System.out.println("Аномалия наносит " + damage + " урона, но также лечит вас на " + healing + " здоровья!");
        System.out.println("Верховный Маг: \"Пространство полно сюрпризов! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent36(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ЭНЕРГЕТИЧЕСКИЙ ВИХРЬ ===");
        sleep(2000);
        System.out.println("Верховный Маг создает энергетический вихрь...");
        sleep(1500);
        int manaChange = random.nextInt(20) - 10; // От -10 до +10
        player.setMana(player.getMana() + manaChange);
        System.out.println(manaChange >= 0 ?
                "Вихрь наполняет вас энергией! +" + manaChange + " маны!" :
                "Вихрь поглощает часть вашей маны! " + manaChange + " маны!");
        System.out.println("Верховный Маг: \"Танцуй в моем вихре! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent37(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ВОЛНА ВОССТАНОВЛЕНИЯ ===");
        sleep(2000);
        System.out.println("Верховный Маг случайно создает волну восстановления...");
        sleep(1500);
        int healing = random.nextInt(25) + 15;
        player.heal(healing);
        System.out.println("Волна восстанавливает " + healing + " здоровья!");
        System.out.println("Верховный Маг: \"Проклятье! Это должно было навредить! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent38(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ ЩЕПОТ ===");
        sleep(2000);
        System.out.println("Верховный Маг шепчет древние заклинания...");
        sleep(1500);
        int manaBonus = random.nextInt(20) + 10;
        player.setMana(player.getMana() + manaBonus);
        System.out.println("Древняя магия наполняет вас силой! +" + manaBonus + " маны!");
        System.out.println("Верховный Маг: \"Древние силы непредсказуемы! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent39(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ВРЕМЕННОЙ ОТКАТ ===");
        sleep(2000);
        System.out.println("Верховный Маг пытается отмотать время...");
        sleep(1500);
        int healing = random.nextInt(18) + 12;
        player.heal(healing);
        System.out.println("Ваши недавние раны исчезают! +" + healing + " здоровья!");
        System.out.println("Верховный Маг: \"Время играет с нами в свои игры! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent40(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ВЕЛИКИЙ БАЛАНС ===");
        sleep(2000);
        System.out.println("Верховный Маг призывает силы вселенского баланса...");
        sleep(1500);

        int currentHealth = (int) player.getHealth();
        int currentMana = player.getMana();

        int averageValue = (currentHealth + currentMana) / 2;

        player.setHealth(averageValue);
        player.setMana(averageValue);

        System.out.println("Силы баланса уравновешивают ваше здоровье и ману до " + averageValue + "!");
        System.out.println("Верховный Маг: \"Идеальный баланс... как все и должно быть! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent41(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ПРИЗРАЧНОЕ ЭХО ===");
        sleep(2000);
        System.out.println("Верховный Маг вызывает призрачное эхо...");
        sleep(1500);
        int echoDamage = random.nextInt(10) + 5;
        player.takeDamage(echoDamage);
        System.out.println("Эхо наносит " + echoDamage + " урона, но вы чувствуете, что оно может повториться!");
        System.out.println("Верховный Маг: \"Эхо прошлого настигнет тебя! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent42(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ РЕЗОНАНС ===");
        sleep(2000);
        System.out.println("Верховный Маг создает магический резонанс...");
        sleep(1500);
        int manaBoost = random.nextInt(20) + 10;
        player.setMana(player.getMana() + manaBoost);
        System.out.println("Ваша мана усиливается на " + manaBoost + " единиц!");
        System.out.println("Верховный Маг: \"Почувствуй, как резонирует магия! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent43(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ВРЕМЕННАЯ ПЕТЛЯ ===");
        sleep(2000);
        System.out.println("Верховный Маг создает временную петлю...");
        sleep(1500);
        int healAmount = random.nextInt(15) + 10;
        player.heal(healAmount);
        System.out.println("Вы восстанавливаете " + healAmount + " здоровья!");
        System.out.println("Верховный Маг: \"Время повторяется, но изменится ли исход? " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent44(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ЭНЕРГЕТИЧЕСКИЙ ВИХРЬ ===");
        sleep(2000);
        System.out.println("Верховный Маг создает энергетический вихрь...");
        sleep(1500);
        int energyChange = random.nextInt(30) - 15; // От -15 до +15
        player.setMana(player.getMana() + energyChange);
        System.out.println(energyChange >= 0 ?
                "Вихрь наполняет вас энергией! +" + energyChange + " маны!" :
                "Вихрь поглощает часть вашей маны! " + energyChange + " маны!");
        System.out.println("Верховный Маг: \"Энергия течет, как ей вздумается! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent45(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: АСТРАЛЬНОЕ ЗЕРКАЛО ===");
        sleep(2000);
        System.out.println("Верховный Маг создает астральное зеркало...");
        sleep(1500);
        System.out.println("Вы чувствуете, что следующая атака может отразиться!");
        System.out.println("Верховный Маг: \"Посмотрим, кто кого атакует! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent46(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: КОСМИЧЕСКАЯ ГАРМОНИЯ ===");
        sleep(2000);
        System.out.println("Верховный Маг настраивает космическую гармонию...");
        sleep(1500);
        int harmonyBonus = random.nextInt(10) + 5;
        player.heal(harmonyBonus);
        player.setMana(player.getMana() + harmonyBonus);
        System.out.println("Гармония восстанавливает " + harmonyBonus + " здоровья и маны!");
        System.out.println("Верховный Маг: \"Вселенная в равновесии... пока что! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent47(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ПРОСТРАНСТВЕННЫЙ СДВИГ ===");
        sleep(2000);
        System.out.println("Верховный Маг искажает пространство вокруг вас...");
        sleep(1500);
        System.out.println("Вы чувствуете, что ваши движения могут быть непредсказуемыми!");
        System.out.println("Верховный Маг: \"Попробуй попасть по мне теперь! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent48(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ВРЕМЕННОЕ УСКОРЕНИЕ ===");
        sleep(2000);
        System.out.println("Верховный Маг ускоряет время вокруг вас...");
        sleep(1500);
        System.out.println("Вы чувствуете, что можете действовать быстрее!");
        System.out.println("Верховный Маг: \"Успеешь ли ты воспользоваться моментом? " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent49(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ЭНЕРГЕТИЧЕСКИЙ ОБМЕН ===");
        sleep(2000);
        System.out.println("Верховный Маг инициирует энергетический обмен...");
        sleep(1500);
        int healthToMana = random.nextInt(10) + 5;
        player.takeDamage(healthToMana);
        player.setMana(player.getMana() + healthToMana * 2);
        System.out.println("Вы теряете " + healthToMana + " здоровья, но получаете " + (healthToMana * 2) + " маны!");
        System.out.println("Верховный Маг: \"Жизнь за магию, неплохая сделка, не так ли? " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent50(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: КВАНТОВАЯ НЕОПРЕДЕЛЕННОСТЬ ===");
        sleep(2000);
        System.out.println("Верховный Маг создает поле квантовой неопределенности...");
        sleep(1500);
        System.out.println("Вы чувствуете, что ваши действия могут иметь непредсказуемые последствия!");
        System.out.println("Верховный Маг: \"Теперь каждое твое действие - это бросок кубика! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent51(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ЭЛЕМЕНТАЛЬНЫЙ РЕЗОНАНС ===");
        sleep(2000);
        System.out.println("Верховный Маг вызывает элементальный резонанс...");
        sleep(1500);
        String[] elements = {"Огонь", "Лёд", "Молния", "Земля"};
        String randomElement = elements[random.nextInt(elements.length)];
        System.out.println("Вы получаете силу " + randomElement + "!");
        System.out.println("Верховный Маг: \"Стихии благоволят тебе... временно! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent52(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ ОБМЕН ===");
        sleep(2000);
        System.out.println("Верховный Маг предлагает магический обмен...");
        sleep(1500);
        int currentMana = player.getMana();
        int currentHealth = (int) player.getHealth();
        player.setMana(currentHealth);
        player.setHealth(currentMana);
        System.out.println("Ваше здоровье и мана меняются местами!");
        System.out.println("Верховный Маг: \"Как тебе такой поворот? " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent53(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: КАРМАННОЕ ИЗМЕРЕНИЕ ===");
        sleep(2000);
        System.out.println("Верховный Маг открывает карманное измерение...");
        sleep(1500);
        System.out.println("Вы можете хранить дополнительные предметы временно!");
        System.out.println("Верховный Маг: \"Больше места для сокровищ... или проблем! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent54(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ ДВОЙНИК ===");
        sleep(2000);
        System.out.println("Верховный Маг создает вашу магическую копию...");
        sleep(1500);
        System.out.println("Ваши атаки могут повториться благодаря двойнику!");
        System.out.println("Верховный Маг: \"Двое против одного? Как интересно! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent55(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ВРЕМЕННОЙ ПАРАДОКС ===");
        sleep(2000);
        System.out.println("Верховный Маг создает временной парадокс...");
        sleep(1500);
        System.out.println("Ваши действия могут повториться в следующем ходу!");
        System.out.println("Верховный Маг: \"Прошлое догонит настоящее! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent56(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: РУНИЧЕСКАЯ ПЕЧАТЬ ===");
        sleep(2000);
        System.out.println("Верховный Маг накладывает руническую печать...");
        sleep(1500);
        int runicBonus = random.nextInt(15) + 10;
        System.out.println("Ваша защита усиливается на " + runicBonus + "!");
        System.out.println("Верховный Маг: \"Древние руны защищают тебя... пока что! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent57(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКАЯ РЕГЕНЕРАЦИЯ ===");
        sleep(2000);
        System.out.println("Верховный Маг активирует магическую регенерацию...");
        sleep(1500);
        System.out.println("Вы будете восстанавливать здоровье каждый ход!");
        System.out.println("Верховный Маг: \"Исцеляйся... пока можешь! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent58(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ ВАМПИРИЗМ ===");
        sleep(2000);
        System.out.println("Верховный Маг накладывает эффект магического вампиризма...");
        sleep(1500);
        System.out.println("Часть нанесенного урона будет возвращаться вам в виде здоровья!");
        System.out.println("Верховный Маг: \"Твоя сила станет твоим исцелением! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent59(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ИСКАЖЕНИЕ РЕАЛЬНОСТИ ===");
        sleep(2000);
        System.out.println("Верховный Маг искажает реальность вокруг вас...");
        sleep(1500);
        System.out.println("Ваши атаки могут иметь неожиданные дополнительные эффекты!");
        System.out.println("Верховный Маг: \"Реальность не так проста, как кажется! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent60(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКАЯ СИНЕРГИЯ ===");
        sleep(2000);
        System.out.println("Верховный Маг создает магическую синергию...");
        sleep(1500);
        System.out.println("Ваши способности усиливают друг друга!");
        System.out.println("Верховный Маг: \"Почувствуй, как сливаются воедино твои силы! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent61(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ ПОЛИМОРФИЗМ ===");
        sleep(2000);
        System.out.println("Верховный Маг превращает вас в случайное существо...");
        sleep(1500);
        String[] creatures = {"кролик", "медведь", "орёл", "рыба", "кот"};
        String randomCreature = creatures[random.nextInt(creatures.length)];
        System.out.println("Вы превращаетесь в " + randomCreature + "!");
        System.out.println("Верховный Маг: \"Как тебе новая шкура? " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent62(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКОЕ ЭХО ===");
        sleep(2000);
        System.out.println("Верховный Маг создает магическое эхо...");
        sleep(1500);
        System.out.println("Ваши заклинания могут сработать дважды!");
        System.out.println("Верховный Маг: \"Услышь эхо своей силы! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent63(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГНИТНОЕ ПОЛЕ ===");
        sleep(2000);
        System.out.println("Верховный Маг создает магнитное поле вокруг вас...");
        sleep(1500);
        System.out.println("Вы притягиваете к себе предметы с расстояния!");
        System.out.println("Верховный Маг: \"Почувствуй силу притяжения! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent64(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: АСТРАЛЬНАЯ ПРОЕКЦИЯ ===");
        sleep(2000);
        System.out.println("Верховный Маг отделяет вашу астральную форму...");
        sleep(1500);
        System.out.println("Вы можете исследовать соседние комнаты, не перемещаясь физически!");
        System.out.println("Верховный Маг: \"Путешествуй между мирами! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent65(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: КВАНТОВОЕ ЗАПУТЫВАНИЕ ===");
        sleep(2000);
        System.out.println("Верховный Маг запутывает вас на квантовом уровне...");
        sleep(1500);
        System.out.println("Ваши действия могут иметь неожиданные последствия в других частях подземелья!");
        System.out.println("Верховный Маг: \"Теперь ты связан со всем подземельем! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent66(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ РЕЗОНАНС ===");
        sleep(2000);
        System.out.println("Верховный Маг настраивает ваши магические частоты...");
        sleep(1500);
        System.out.println("Ваши заклинания усиливаются при повторном использовании!");
        System.out.println("Верховный Маг: \"Почувствуй, как растет твоя сила! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent67(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: ВРЕМЕННАЯ ПЕТЛЯ ===");
        sleep(2000);
        System.out.println("Верховный Маг помещает вас во временную петлю...");
        sleep(1500);
        System.out.println("Вы можете повторить свой последний ход!");
        System.out.println("Верховный Маг: \"Проживи этот момент снова! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent68(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКАЯ СИМФОНИЯ ===");
        sleep(2000);
        System.out.println("Верховный Маг начинает играть магическую симфонию...");
        sleep(1500);
        System.out.println("Ваши действия становятся более гармоничными и эффективными!");
        System.out.println("Верховный Маг: \"Танцуй под мою музыку! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent69(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ КАЛЕЙДОСКОП ===");
        sleep(2000);
        System.out.println("Верховный Маг создает магический калейдоскоп...");
        sleep(1500);
        System.out.println("Ваши заклинания могут случайным образом менять свои эффекты!");
        System.out.println("Верховный Маг: \"Наслаждайся разнообразием магии! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent70(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКОЕ ЗЕРКАЛО ===");
        sleep(2000);
        System.out.println("Верховный Маг создает магическое зеркало...");
        sleep(1500);
        System.out.println("Вы можете отражать заклинания обратно на противников!");
        System.out.println("Верховный Маг: \"Отрази свою судьбу! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent71(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ ТЕАТР ===");
        sleep(2000);
        System.out.println("Верховный Маг создает иллюзорный театр...");
        sleep(1500);
        System.out.println("Вы видите сцены из прошлого и возможного будущего.");
        System.out.println("Верховный Маг: \"Жизнь - это театр, а ты в нем актер! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent72(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ ОРКЕСТР ===");
        sleep(2000);
        System.out.println("Верховный Маг дирижирует невидимым оркестром...");
        sleep(1500);
        System.out.println("Воздух наполняется волшебной музыкой, поднимающей настроение.");
        System.out.println("Верховный Маг: \"Танцуй, пока играет музыка! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent73(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ ФЕЙЕРВЕРК ===");
        sleep(2000);
        System.out.println("Верховный Маг устраивает магический фейерверк...");
        sleep(1500);
        System.out.println("Яркие вспышки и красочные искры заполняют воздух.");
        System.out.println("Верховный Маг: \"Наслаждайся шоу, смертный! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent74(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ ЦИРК ===");
        sleep(2000);
        System.out.println("Верховный Маг превращает комнату в цирковую арену...");
        sleep(1500);
        System.out.println("Иллюзорные акробаты и жонглеры развлекают вас своими трюками.");
        System.out.println("Верховный Маг: \"Добро пожаловать на величайшее шоу! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent75(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ САД ===");
        sleep(2000);
        System.out.println("Верховный Маг создает вокруг вас волшебный сад...");
        sleep(1500);
        System.out.println("Экзотические цветы и светящиеся растения окружают вас.");
        System.out.println("Верховный Маг: \"Полюбуйся на мое зеленое творение! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent76(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ АКВАРИУМ ===");
        sleep(2000);
        System.out.println("Верховный Маг наполняет комнату водой, создавая гигантский аквариум...");
        sleep(1500);
        System.out.println("Вы плаваете среди экзотических рыб и светящихся кораллов.");
        System.out.println("Верховный Маг: \"Поплавай с рыбками, сухопутное существо! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent77(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКАЯ ГАЛЕРЕЯ ===");
        sleep(2000);
        System.out.println("Верховный Маг создает вокруг вас магическую художественную галерею...");
        sleep(1500);
        System.out.println("Живые картины и движущиеся скульптуры окружают вас.");
        System.out.println("Верховный Маг: \"Искусство вечно, а ты - лишь мимолетный зритель! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent78(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ КАРНАВАЛ ===");
        sleep(2000);
        System.out.println("Верховный Маг устраивает магический карнавал...");
        sleep(1500);
        System.out.println("Вокруг вас появляются маски, костюмы и праздничная атмосфера.");
        System.out.println("Верховный Маг: \"Надень маску и присоединяйся к веселью! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent79(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ ЛАБИРИНТ ===");
        sleep(2000);
        System.out.println("Верховный Маг создает иллюзорный лабиринт вокруг вас...");
        sleep(1500);
        System.out.println("Стены постоянно меняются, создавая запутанные коридоры.");
        System.out.println("Верховный Маг: \"Найди выход, если сможешь! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent80(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКОЕ КАЗИНО ===");
        sleep(2000);
        System.out.println("Верховный Маг превращает комнату в магическое казино...");
        sleep(1500);
        System.out.println("Вокруг вас появляются игровые столы и волшебные игральные автоматы.");
        System.out.println("Верховный Маг: \"Испытай свою удачу, смертный! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent81(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ ЗООПАРК ===");
        sleep(2000);
        System.out.println("Верховный Маг создает вокруг вас иллюзорный зоопарк...");
        sleep(1500);
        System.out.println("Вы видите клетки с фантастическими существами из разных миров.");
        System.out.println("Верховный Маг: \"Добро пожаловать в мою коллекцию редкостей! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent82(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКАЯ БИБЛИОТЕКА ===");
        sleep(2000);
        System.out.println("Верховный Маг окружает вас бесконечными книжными полками...");
        sleep(1500);
        System.out.println("Книги летают вокруг, их страницы шелестят, рассказывая древние истории.");
        System.out.println("Верховный Маг: \"Знание - сила, но хватит ли у тебя времени его обрести? " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent83(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ ПОДИУМ ===");
        sleep(2000);
        System.out.println("Верховный Маг устраивает магический показ мод...");
        sleep(1500);
        System.out.println("Иллюзорные модели демонстрируют невероятные наряды из чистой магии.");
        System.out.println("Верховный Маг: \"Полюбуйся на последние тренды магической моды! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent84(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ КОНЦЕРТ ===");
        sleep(2000);
        System.out.println("Верховный Маг создает иллюзию грандиозного рок-концерта...");
        sleep(1500);
        System.out.println("Вы оказываетесь в центре толпы фантомных фанатов, наслаждающихся музыкой.");
        System.out.println("Верховный Маг: \"Почувствуй ритм магического рока! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent85(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ СПОРТЗАЛ ===");
        sleep(2000);
        System.out.println("Верховный Маг превращает комнату в фантастический спортзал...");
        sleep(1500);
        System.out.println("Вокруг вас парят волшебные гантели и бегут дорожки, ведущие в никуда.");
        System.out.println("Верховный Маг: \"Время поработать над магической формой! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent86(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ РЕСТОРАН ===");
        sleep(2000);
        System.out.println("Верховный Маг создает иллюзию роскошного ресторана...");
        sleep(1500);
        System.out.println("Фантомные официанты подают невероятные блюда, исчезающие при прикосновении.");
        System.out.println("Верховный Маг: \"Наслаждайся ужином, который никогда не закончится! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent87(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ КОСМОПОРТ ===");
        sleep(2000);
        System.out.println("Верховный Маг трансформирует пространство в футуристический космопорт...");
        sleep(1500);
        System.out.println("Вокруг вас пролетают странные корабли и инопланетные существа.");
        System.out.println("Верховный Маг: \"Добро пожаловать на межгалактический перекресток! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent88(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ ПАРК АТТРАКЦИОНОВ ===");
        sleep(2000);
        System.out.println("Верховный Маг создает вокруг вас волшебный парк развлечений...");
        sleep(1500);
        System.out.println("Американские горки взмывают в небо, а карусели вращаются с невозможной скоростью.");
        System.out.println("Верховный Маг: \"Прокатись на самых опасных аттракционах в мультивселенной! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent89(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ ПОДВОДНЫЙ МИР ===");
        sleep(2000);
        System.out.println("Верховный Маг погружает вас в иллюзию глубоководного мира...");
        sleep(1500);
        System.out.println("Вокруг плавают светящиеся рыбы и извиваются гигантские водоросли.");
        System.out.println("Верховный Маг: \"Добро пожаловать в пучину моего воображения! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent90(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКАЯ ОБСЕРВАТОРИЯ ===");
        sleep(2000);
        System.out.println("Верховный Маг создает вокруг вас космическую обсерваторию...");
        sleep(1500);
        System.out.println("Вы видите далекие галактики и рождение новых звезд.");
        System.out.println("Верховный Маг: \"Познай свою ничтожность перед лицом вселенной! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent91(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ ЦИРК ===");
        sleep(2000);
        System.out.println("Верховный Маг превращает комнату в фантастический цирк...");
        sleep(1500);
        System.out.println("Акробаты из чистой энергии выполняют невозможные трюки под куполом из звёзд.");
        System.out.println("Верховный Маг: \"Добро пожаловать на величайшее шоу в мультивселенной! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent92(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ САД ===");
        sleep(2000);
        System.out.println("Верховный Маг создает вокруг вас волшебный сад...");
        sleep(1500);
        System.out.println("Цветы поют мелодичные песни, а деревья танцуют под их музыку.");
        System.out.println("Верховный Маг: \"Насладись симфонией природы... пока можешь! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent93(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ МУЗЕЙ ===");
        sleep(2000);
        System.out.println("Верховный Маг окружает вас экспонатами из разных эпох и миров...");
        sleep(1500);
        System.out.println("Вы видите артефакты, которых никогда не существовало, и картины невозможных пейзажей.");
        System.out.println("Верховный Маг: \"Полюбуйся на мою коллекцию невозможного! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent94(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ КАРНАВАЛ ===");
        sleep(2000);
        System.out.println("Верховный Маг устраивает вокруг вас грандиозный карнавал...");
        sleep(1500);
        System.out.println("Маски оживают, а костюмы меняют форму и цвет прямо на глазах.");
        System.out.println("Верховный Маг: \"Присоединяйся к безумному веселью! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent95(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ ЛАБИРИНТ ===");
        sleep(2000);
        System.out.println("Верховный Маг создает вокруг вас постоянно меняющийся лабиринт...");
        sleep(1500);
        System.out.println("Стены двигаются, проходы исчезают и появляются в самых неожиданных местах.");
        System.out.println("Верховный Маг: \"Попробуй найти выход из моего разума! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent96(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ АУКЦИОН ===");
        sleep(2000);
        System.out.println("Верховный Маг устраивает аукцион невероятных магических предметов...");
        sleep(1500);
        System.out.println("Фантомные покупатели торгуются за артефакты, существующие лишь в воображении.");
        System.out.println("Верховный Маг: \"Кто больше за частичку моего безумия? " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent97(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ ФЕСТИВАЛЬ ===");
        sleep(2000);
        System.out.println("Верховный Маг создает вокруг вас грандиозный фестиваль магии...");
        sleep(1500);
        System.out.println("Маги со всех уголков мультивселенной демонстрируют невероятные трюки и заклинания.");
        System.out.println("Верховный Маг: \"Добро пожаловать на съезд безумных волшебников! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent98(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКАЯ ОПЕРА ===");
        sleep(2000);
        System.out.println("Верховный Маг устраивает представление волшебной оперы...");
        sleep(1500);
        System.out.println("Призрачные певцы исполняют арии на неизвестных языках, вызывая невероятные видения.");
        System.out.println("Верховный Маг: \"Наслаждайся музыкой сфер! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    public void executeSpecialEvent99(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ: МАГИЧЕСКИЙ ПАРАД ===");
        sleep(2000);
        System.out.println("Верховный Маг организует грандиозный парад магических существ...");
        sleep(1500);
        System.out.println("Мимо вас проходят удивительные создания из легенд и мифов всех миров.");
        System.out.println("Верховный Маг: \"Встречай армию моего воображения! " + getRandomLaugh() + "\"");
        sleep(2000);
    }

    @Override
    public void performAction(Game game, Room room) {
        int healthPercentage = (int) ((getHealth() * 100.0) / getMaxHealth());

        if (healthMessages.containsKey(healthPercentage)) {
            System.out.println(getName() + ": " + healthMessages.get(healthPercentage));
        }

        if (!ritualCompleted) {
            performRitual(game, room);
        } else {
            Player player = game.getPlayer();
            Random random = new Random();
            int action = random.nextInt(100);

            if (getHealth() <= 0) {
                System.out.println(getName() + ": Как... Как смертный смог победить первородную тьму...");
                room.removeEnemy(this);
                return;
            }

            // Добавляем новые способности с малым шансом
            if (action < 5) { // 5% шанс на поглощение души
                absorbSingleSoul(room);
            } else if (action < 10) { // 2% шанс на поглощение всех душ
                absorbAllSouls(room);
            } else if (healthPercentage <= 30) {
                    summonMinion(room, player);
            } else if (healthPercentage <= 60) {
                if (action < 50) {
                    summonMinion(room, player);
                } else {
                    sayRandomPhrase();
                }
            } else {
                if (action < 25) {
                    summonMinion(room, player);
                } else {
                    sayRandomPhrase();
                }
            }
        }
    }

    private void absorbSingleSoul(Room room) {
        List<Entity> enemies = new ArrayList<>(room.getEnemies());
        enemies.remove(this); // Удаляем себя из списка возможных целей

        if (enemies.isEmpty()) {
            System.out.println(getName() + ": Нет душ для поглощения...");
            return;
        }

        Random random = new Random();
        Entity target = enemies.get(random.nextInt(enemies.size()));

        double absorbedHealth = target.getHealth();
        setHealth(getHealth() + absorbedHealth);

        System.out.println(getName() + ": Твоя душа принадлежит мне, " + target.getName() + "!");
        System.out.println(getName() + " поглощает душу " + target.getName() + " и восстанавливает "
                + absorbedHealth + " здоровья!");

        room.removeEnemy(target);
    }

    private void absorbAllSouls(Room room) {
        List<Entity> enemies = new ArrayList<>(room.getEnemies());
        enemies.remove(this); // Удаляем себя из списка целей

        if (enemies.isEmpty()) {
            System.out.println(getName() + ": Нет душ для поглощения...");
            return;
        }

        System.out.println(getName() + ": ВАШИ ДУШИ ПИТАЮТ МЕНЯ!");
        double totalAbsorbedHealth = 0;

        for (Entity enemy : enemies) {
            totalAbsorbedHealth += enemy.getHealth();
            System.out.println(getName() + " поглощает душу " + enemy.getName() + "!");
        }

        setHealth(getHealth() + totalAbsorbedHealth);
        System.out.println(getName() + " восстанавливает " + totalAbsorbedHealth + " здоровья!");

        // Удаляем всех поглощенных врагов
        enemies.forEach(room::removeEnemy);
    }

    private Map<Integer, String> initializeHealthMessages() {
        Map<Integer, String> messages = new HashMap<>();
        messages.put(100, "Наконец-то... Ты добрался до истинной тьмы.");
        messages.put(95, "Твоя сила... Всё та же жалкая искра света.");
        messages.put(90, "Десятая часть... Даже не царапина.");
        messages.put(85, "Тьма поглотит твой свет... Как и всегда.");
        messages.put(80, "Пятая часть... Ты действительно думаешь, что можешь победить?");
        messages.put(75, "Четверть моей силы... Как утомительно.");
        messages.put(70, "Твоё упорство... Раздражает.");
        messages.put(65, "Эта битва... Лишь подтверждает тщетность вашего существования.");
        messages.put(60, "Сорок процентов... Возможно, ты заслуживаешь толики моего внимания.");
        messages.put(55, "Я чувствую древнюю силу... Она не спасёт тебя.");
        messages.put(50, "Половина... Теперь ты познаешь истинную тьму.");
        messages.put(45, "Твой свет... Он начинает тускнеть.");
        messages.put(40, "Печати древних... Бесполезны против первородной тьмы.");
        messages.put(35, "Ты силён... Но я существовал до начала времён.");
        messages.put(30, "Треть моей силы... Пора показать тебе настоящий страх.");
        messages.put(25, "Твоя надежда угасает... Я чувствую это.");
        messages.put(20, "Пятая часть... Тьма жаждет твоей души.");
        messages.put(15, "Твой свет меркнет... Как и у всех до тебя.");
        messages.put(10, "Десятая часть... Сопротивление бесполезно.");
        messages.put(5, "Твоя сила иссякает... Прими свою судьбу.");
        messages.put(4, "Я чувствую твой страх... Он прекрасен.");
        messages.put(3, "Тьма поглотит всё... Это неизбежно.");
        messages.put(2, "Твоя душа... Она уже принадлежит мне.");
        messages.put(1, "Невозможно... НЕВОЗМОЖНО!");

        return messages;
    }


    private void sayRandomPhrase() {
        Random random = new Random();
        int phraseIndex = random.nextInt(phrases.size());
        String phrase = phrases.get(phraseIndex);
        System.out.println(getName() + " говорит: " + phrase);
    }

    private List<String> phrases = Arrays.asList(
            "Ваше существование... такое утомительное...",
            "Смертные... Всё те же бессмысленные попытки...",
            "Как долго я существую... и всё такая же скука...",
            "Ваша борьба забавляла меня... первые несколько тысячелетий.",
            "Тьма поглотит всё... рано или поздно...",
            "Я видел рождение и смерть вселенных... ты ничто.",
            "Твоя душа... такая же незначительная, как и миллионы до неё...",
            "Вечность... делает даже уничтожение миров... монотонным...",
            "Твоя храбрость... такая предсказуемая...",
            "Очередной герой... очередная пустая надежда...",
            "Даже само время склонится перед тьмой... зачем сопротивляться?",
            "Твой свет... такой тусклый... такой временный...",
            "Я существовал до начала времён... и буду существовать после их конца...",
            "Твоя жизнь... лишь мгновение в бесконечности тьмы...",
            "Сколько героев пало... все они были... такими же самонадеянными..."
    );

    private void summonMinion(Room room, Player player) {
        Entity minion = new EntityFactory().createRandomEntity(player);
        room.addEnemy(minion);
        System.out.println("Верховный Маг призывает " + minion.getName() + " себе на помощь!");
    }
    private void performRitual(Game game, Room room) {
        Player player = game.getPlayer();

        // Сохраняем текущее состояние игрока и его союзников
        SavedState savedState = new SavedState(player, this);

        System.out.println("\n=== ВЕРХОВНЫЙ МАГ НАЧИНАЕТ СВОЙ ВЕЛИКИЙ РИТУАЛ ===");
        sleep(2000);

        // Выполнение ритуала (события)
        executeSpecialEvent1(player, room);
        sleep(1000);
        executeSpecialEvent2(player, room);
        sleep(1000);
        executeSpecialEvent3(player, room);
        sleep(1000);
        executeSpecialEvent4(player, room);
        sleep(1000);
        executeSpecialEvent5(player, room);
        sleep(1000);
        executeSpecialEvent6(player, room);
        sleep(1000);
        executeSpecialEvent7(player, room);
        sleep(1000);
        executeSpecialEvent8(player, room);
        sleep(1000);
        executeSpecialEvent9(player, room);
        sleep(1000);
        executeSpecialEvent10(player, room);
        sleep(1000);
        executeSpecialEvent11(player, room);
        sleep(1000);
        executeSpecialEvent12(player, room);
        sleep(1000);
        executeSpecialEvent13(player, room);
        sleep(1000);
        executeSpecialEvent14(player, room);
        sleep(1000);
        executeSpecialEvent15(player, room);
        sleep(1000);
        executeSpecialEvent16(player, room);
        sleep(1000);
        executeSpecialEvent17(player, room);
        sleep(1000);
        executeSpecialEvent18(player, room);
        sleep(1000);
        executeSpecialEvent19(player, room);
        sleep(1000);
        executeSpecialEvent20(player, room);
        sleep(1000);
        executeSpecialEvent21(player, room);
        sleep(1000);
        executeSpecialEvent22(player, room);
        sleep(1000);
        executeSpecialEvent23(player, room);
        sleep(1000);
        executeSpecialEvent24(player, room);
        sleep(1000);
        executeSpecialEvent25(player, room);
        sleep(1000);
        executeSpecialEvent26(player, room);
        sleep(1000);
        executeSpecialEvent27(player, room);
        sleep(1000);
        executeSpecialEvent28(player, room);
        sleep(1000);
        executeSpecialEvent29(player, room);
        sleep(1000);
        executeSpecialEvent30(player, room);
        sleep(1000);
        executeSpecialEvent31(player, room);
        sleep(1000);
        executeSpecialEvent32(player, room);
        sleep(1000);
        executeSpecialEvent33(player, room);
        sleep(1000);
        executeSpecialEvent34(player, room);
        sleep(1000);
        executeSpecialEvent35(player, room);
        sleep(1000);
        executeSpecialEvent36(player, room);
        sleep(1000);
        executeSpecialEvent37(player, room);
        sleep(1000);
        executeSpecialEvent38(player, room);
        sleep(1000);
        executeSpecialEvent39(player, room);
        sleep(1000);
        executeSpecialEvent40(player, room);
        sleep(1000);
        executeSpecialEvent41(player, room);
        sleep(1000);
        executeSpecialEvent42(player, room);
        sleep(1000);
        executeSpecialEvent43(player, room);
        sleep(1000);
        executeSpecialEvent44(player, room);
        sleep(1000);
        executeSpecialEvent45(player, room);
        sleep(1000);
        executeSpecialEvent46(player, room);
        sleep(1000);
        executeSpecialEvent47(player, room);
        sleep(1000);
        executeSpecialEvent48(player, room);
        sleep(1000);
        executeSpecialEvent49(player, room);
        sleep(1000);
        executeSpecialEvent50(player, room);
        sleep(1000);
        executeSpecialEvent51(player, room);
        sleep(1000);
        executeSpecialEvent52(player, room);
        sleep(1000);
        executeSpecialEvent53(player, room);
        sleep(1000);
        executeSpecialEvent54(player, room);
        sleep(1000);
        executeSpecialEvent55(player, room);
        sleep(1000);
        executeSpecialEvent56(player, room);
        sleep(1000);
        executeSpecialEvent57(player, room);
        sleep(1000);
        executeSpecialEvent58(player, room);
        sleep(1000);
        executeSpecialEvent59(player, room);
        sleep(1000);
        executeSpecialEvent60(player, room);
        sleep(1000);
        executeSpecialEvent61(player, room);
        sleep(1000);
        executeSpecialEvent62(player, room);
        sleep(1000);
        executeSpecialEvent63(player, room);
        sleep(1000);
        executeSpecialEvent64(player, room);
        sleep(1000);
        executeSpecialEvent65(player, room);
        sleep(1000);
        executeSpecialEvent66(player, room);
        sleep(1000);
        executeSpecialEvent67(player, room);
        sleep(1000);
        executeSpecialEvent68(player, room);
        sleep(1000);
        executeSpecialEvent69(player, room);
        sleep(1000);
        executeSpecialEvent70(player, room);
        sleep(1000);
        executeSpecialEvent71(player, room);
        sleep(1000);
        executeSpecialEvent72(player, room);
        sleep(1000);
        executeSpecialEvent73(player, room);
        sleep(1000);
        executeSpecialEvent74(player, room);
        sleep(1000);
        executeSpecialEvent75(player, room);
        sleep(1000);
        executeSpecialEvent76(player, room);
        sleep(1000);
        executeSpecialEvent77(player, room);
        sleep(1000);
        executeSpecialEvent78(player, room);
        sleep(1000);
        executeSpecialEvent79(player, room);
        sleep(1000);
        executeSpecialEvent80(player, room);
        sleep(1000);
        executeSpecialEvent81(player, room);
        sleep(1000);
        executeSpecialEvent82(player, room);
        sleep(1000);
        executeSpecialEvent83(player, room);
        sleep(1000);
        executeSpecialEvent84(player, room);
        sleep(1000);
        executeSpecialEvent85(player, room);
        sleep(1000);
        executeSpecialEvent86(player, room);
        sleep(1000);
        executeSpecialEvent87(player, room);
        sleep(1000);
        executeSpecialEvent88(player, room);
        sleep(1000);
        executeSpecialEvent89(player, room);
        sleep(1000);
        executeSpecialEvent90(player, room);
        sleep(1000);
        executeSpecialEvent91(player, room);
        sleep(1000);
        executeSpecialEvent92(player, room);
        sleep(1000);
        executeSpecialEvent93(player, room);
        sleep(1000);
        executeSpecialEvent94(player, room);
        sleep(1000);
        executeSpecialEvent95(player, room);
        sleep(1000);
        executeSpecialEvent96(player, room);
        sleep(1000);
        executeSpecialEvent97(player, room);
        sleep(1000);
        executeSpecialEvent98(player, room);
        sleep(1000);
        executeSpecialEvent99(player, room);
        sleep(1000);

        System.out.println("\n=== ВЕЛИКИЙ РИТУАЛ ВЕРХОВНОГО МАГА ЗАВЕРШЕН ===");
        sleep(2000);
        System.out.println("Верховный Маг: \"Как тебе моя сила, смертный? " + getRandomLaugh() + "\"");

        // Восстанавливаем состояние игрока и его союзников
        savedState.restore(player, this);
        player.updateEffects();
        player.updateEffects();
        player.updateEffects();
        player.updateEffects();
        player.updateEffects();
        System.out.println("Ваши характеристики восстановлены, и вы готовы к бою!");

        // Запускаем событие 100
        executeSpecialEvent100(player, room);

        ritualCompleted = true;
    }



    public boolean isRitualCompleted() {
        return ritualCompleted;
    }


    public void executeSpecialEvent100(Player player, Room room) {
        System.out.println("\n=== ОСОБОЕ СОБЫТИЕ 100: ВОЗВРАЩЕНИЕ К РЕАЛЬНОСТИ ===");
        sleep(2000);
        System.out.println("Иллюзорный мир вокруг вас начинает рассыпаться...");
        sleep(1500);
        System.out.println("Краски блекнут, и реальность возвращается в свою разрушенную форму.");
        sleep(2000);
        System.out.println("Верховный Маг с презрением смотрит на вас и говорит:");
        sleep(1500);
        System.out.println("\"Все это было бесполезно, смертный! Ты не сможешь изменить свою судьбу!\"");
        sleep(2000);
        System.out.println("Он поднимает свой посох и готовится к битве.");
        sleep(1500);
        System.out.println("=== БОЙ С ВЕРХОВНЫМ МАГОМ НАЧИНАЕТСЯ ===");
        sleep(2000);
    }

    private String getRandomLaugh() {
        String[] laughs = {
                "ХАХАХАХА!",
                "МУХАХАХА!",
                "ХО-ХО-ХО!",
                "ХИ-ХИ-ХИ!",
                "ГА-ГА-ГА!",
                "*Злобный смех*",
                "*Маниакальный хохот*",
                "БВАХАХАХА!",
                "*Зловещее хихиканье*",
                "ХЕ-ХЕ-ХЕ!"
        };
        return laughs[random.nextInt(laughs.length)];
    }

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void dropLoot(Game game) {

    }
}
