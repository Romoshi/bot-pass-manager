package edu.romoshi.bot;

public class BotStrings {
    public static final String MASTER_KEY_COMMAND = "/key";
    public static final String START_COMMAND = "/start";
    public static final String SHOW_COMMAND = "/show";
    public static final String SAVE_COMMAND = "/save";
    public static final String DELETE_COMMAND = "/delete";
    public static final String HELP_COMMAND = "/help";
    public static final String START_STRING_ONE = """
                                        Введите пароль.
                                        
                                        Если у Вас его нет, давайте его создадим.
                                        Введите команду:
                                        /key пароль
                                        
                                        Например: _/key 12345_""";
    public static final String START_STRING_TWO = """
                                        Приветствую!
                                        Бот включен и готов к использованию.
                                        Для помощи введите команду:
                                        /help""";
    public static final String HELP_STRING = """
                                        1. Команда для начала работы бота:
                                           /start

                                        2. Команда для показа сохраненных паролей:
                                           /show

                                        3. Команда для сохранения паролей:
                                           /save сервис логин пароль
                                           Например: _/save Яндекс ivanivanov@yandex.ru qwerty_

                                        4. Команда для удаления пароля:
                                           /delete сервис
                                           Например: _/delete Яндекс_""";
}
