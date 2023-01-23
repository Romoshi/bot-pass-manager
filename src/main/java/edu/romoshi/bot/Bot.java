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
    private String response = "";
    ReplyKeyboardMarkup replyKeyboardMarkup;
    MasterKey masterKey = new MasterKey("123");
    @Override
    public void onUpdateReceived(Update update) {
        try{
            if(update.hasMessage() && update.getMessage().hasText())
            {
                Message inMess = update.getMessage();
                String chatId = inMess.getChatId().toString();
                response = parseMessage(inMess.getText());
                SendMessage outMess = new SendMessage();

                outMess.setReplyMarkup(replyKeyboardMarkup);
                outMess.setChatId(chatId);
                outMess.setText(response);

                execute(outMess);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void initKeyboard()
    {
        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardRows.add(keyboardFirstRow);
        keyboardRows.add(keyboardSecondRow);
        keyboardRows.add(keyboardThirdRow);

        keyboardFirstRow.add(new KeyboardButton("Показать пароли"));
        keyboardFirstRow.add(new KeyboardButton("Добавить пароль"));
        keyboardSecondRow.add(new KeyboardButton("Удалить пароль"));
        keyboardThirdRow.add(new KeyboardButton("Информация"));

        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

    public String parseMessage(String textMsg) {
        if (textMsg.equals("/start")) {
            initKeyboard();
            SQLUtils.createTable();
        }

        return response;
    }

    public void useSQLCommands(String command) throws Exception {
        switch (command) {
            case "Показать пароли" -> {
                List<AccWhichSave> accounts = SQLUtils.getAccounts();

                for (var account : accounts) {
                    Decryption de = new Decryption();
                    response = "Название сервиса: " + account.getNameService() + "\n" +
                            "Логин: " + account.getLogin() + "\n" +
                            "Пароль: " + de.decrypt(account.getPassword(), masterKey.getPassword());
                }
            }
            case "Добавить пароль" -> {

                String nameService = "riki";
                String login = "riki@gmail.com";
                String password = "qwerty";

                Encryption en = new Encryption();
                AccWhichSave acc = new AccWhichSave(nameService, login, en.encrypt(password, masterKey.getPassword()));
                SQLUtils.saveAccount(acc);
                response = "Аккаунт добавлен!";
            }
            case "Удалить пароль" -> {
                SQLUtils.deleteAccount("riki");
                response = "Аккаунт удалён!";
            }
            default -> response = "Извините, но такой команды нет.";
        }
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
