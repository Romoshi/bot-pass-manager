package edu.romoshi.bot;

import edu.romoshi.crypto.Decryption;
import edu.romoshi.crypto.Encryption;
import edu.romoshi.database.SQLUtils;
import edu.romoshi.user.AccWhichSave;
import edu.romoshi.user.MasterKey;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    private  static final String NAME_SERVICE_STRING = "Введите название сервиса.";
    private  static final String LOGIN_STRING = "Введите логин.";
    private  static final String PASSWORD_STRING = "Введите пароль.";
    MasterKey masterKey = new MasterKey("123");

    @Override
    public void onUpdateReceived(Update update) {
        try{
            if(update.hasMessage() && update.getMessage().hasText())
            {
                Message inMess = update.getMessage();
                parseMessage(inMess);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseMessage(Message message) throws Exception {
        String textMessage = message.getText();

        switch (textMessage) {
            case "/start" -> {
                SQLUtils.createTable();
                sendMsg(message, "Бот запущен");
            }
            case "Показать пароли" -> {
                List<AccWhichSave> accounts = SQLUtils.getAccounts();

                for (var account : accounts) {
                    Decryption de = new Decryption();
                    String answer = "Название сервиса: " + account.getNameService() + "\n" +
                            "Логин: " + account.getLogin() + "\n" +
                            "Пароль: " + de.decrypt(account.getPassword(), masterKey.getPassword());
                    sendMsg(message, answer);
                }
            }
            case "Добавить пароль" -> {
                sendMsg(message, NAME_SERVICE_STRING);
                String nameService = "Zxc";
                sendMsg(message, LOGIN_STRING);
                String login = "Asd";
                sendMsg(message, PASSWORD_STRING);
                String password = "123";

                Encryption en = new Encryption();
                AccWhichSave acc = new AccWhichSave(nameService, login, en.encrypt(password, masterKey.getPassword()));
                SQLUtils.saveAccount(acc);
                sendMsg(message, "Аккаунт добавлен!");
            }
            case "Удалить пароль" -> {
                SQLUtils.deleteAccount("riki");
                sendMsg(message, "Аккаунт удалён!");
            }
            case "Помощь" -> sendMsg(message, "Аккаунт удалён!");
            default -> sendMsg(message, "Извините, но такой команды нет.");
        }
    }

    private void sendMsg(Message message, String s) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(s);
        initKeyboard(sendMessage);
        execute(sendMessage);
    }
    private void initKeyboard(SendMessage sendMessage)
    {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        ArrayList<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Показать пароли"));
        keyboardFirstRow.add(new KeyboardButton("Добавить пароль"));

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("Удалить пароль"));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }
    @Override
    public String getBotUsername() {
        return "pass_manager_tlgbot";
    }

    @Override
    public String getBotToken() {
        return "5987488924:AAH1WfMQ2kFIJy0lg8WWlIE1l6BsDHtNhTE";
    }
}
