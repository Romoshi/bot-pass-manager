package edu.romoshi.bot;

public class BotStrings {
    public static final String MASTER_KEY_COMMAND = "/key";
    public static final String START_COMMAND = "/start";
    public static final String SHOW_COMMAND = "/show";
    public static final String SAVE_COMMAND = "/save";
    public static final String DELETE_COMMAND = "/delete";
    public static final String HELP_COMMAND = "/help";
    public static final String MISTAKE_MESSAGE = "Введите команду правильно.";
    public static final String KEY_STRING = """
                                        Пароль создан.
                                            
                                        Возможности поменять пароль нет. 
                                        Но к счастью, это единственный пароль, который придётся запомнить.""";
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
