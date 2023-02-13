package edu.romoshi.bot;

public class BotStrings {

    public static final String MISTAKE_MESSAGE = "Введите команду правильно.";
    public static final String DEFAULT_STRING = "Помощь: /help";
    public static final String DELETE_STRING = "Аккаунт удалён!";
    public static final String SAVE_STRING = "Аккаунт добавлен!";
    public static final String KEY_STRING = """
                                        Отлично, пароль создан!
                                        Теперь нужно его ввести :)""";
    public static final String AFTER_KEY_STRING = """
                                        Возможности поменять пароль нет. 
                                        Но к счастью, это единственный пароль, который придётся запомнить.""";
    public static final String KEY_EXIST = "Пароль уже существует.";
    public static final String START_STRING = """
                                        Для доступа введите пароль.
                                        
                                        Если у Вас его нет, Вы можете создать его с помощью команды: 
                                        /key пароль
                                        
                                        Например: /key 12345""";
    public static final String HELP_STRING = """
                                        1. Команда для начала работы бота:
                                           /start

                                        2. Команда для показа сохраненных паролей:
                                           /show

                                        3. Команда для сохранения паролей:
                                           /save сервис логин пароль
                                           Например: /save Яндекс ivanivanov@yandex.ru qwerty

                                        4. Команда для удаления пароля:
                                           /delete сервис
                                           Например: /delete Яндекс""";
}
