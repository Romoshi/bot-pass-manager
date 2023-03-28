package edu.romoshi.bot;

public class BotStrings {

    public static final String MISTAKE_MESSAGE = "Введите команду правильно.";
    public static final String DEFAULT_STRING = "Помощь: /help";
    public static final String DELETE_STRING = "Аккаунт удалён!";
    public static final String SAVE_STRING = "Аккаунт добавлен!";
    public static final String KEY_STRING = "Отлично, пароль создан!\nТеперь нужно его ввести :)";
    public static final String AFTER_KEY_STRING = "Возможности поменять пароль нет.\nНо к счастью, это единственный пароль, который придётся запомнить.";
    public static final String KEY_EXIST = "Пароль уже существует.";
    public static final String START_STRING = "Для доступа введите пароль.\nЕсли у Вас его нет, Вы можете создать его с помощью команды:\n/key пароль\n\nНапример: /key 12345";
    public static final String HELP_STRING = "1. Команда для начала работы бота:\n/start\n\n" +
                                        "2. Команда для показа сохраненных паролей:\n/show\n\n" +
                                        "3. Команда для сохранения паролей:\n/save сервис логин пароль\nНапример: /save Яндекс ivanivanov@yandex.ru qwerty\n\n" +
                                        "4. Команда для удаления пароля:\n/delete сервис\nНапример: /delete Яндекс";
}
