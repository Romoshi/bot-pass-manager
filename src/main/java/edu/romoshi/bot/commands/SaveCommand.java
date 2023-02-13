package edu.romoshi.bot.commands;

import edu.romoshi.bot.BotStrings;
import edu.romoshi.crypto.Encryption;
import edu.romoshi.database.SQLUtils;
import edu.romoshi.user.Accounts;
import org.telegram.telegrambots.meta.api.objects.Message;
import static edu.romoshi.Main.bot;

public class SaveCommand implements Command {
    private final boolean verifyMK;

    public SaveCommand(boolean verifyMK) {
        this.verifyMK = verifyMK;
    }
    @Override
    public void execute(Message message) {
        try {
            String[] messageArray = message.getText().split(" ");
            if (verifyMK) {
                if(messageArray.length == 4) {
                    Encryption en = new Encryption();
                    Accounts acc = new Accounts(messageArray[1], messageArray[2],
                            en.encrypt(messageArray[3], message.getChatId().toString()));
                    SQLUtils.saveAccount(acc, message);
                    bot.sendMsg(message, BotStrings.SAVE_STRING);
                } else {
                    bot.sendMsg(message, BotStrings.MISTAKE_MESSAGE);
                }
            } else {
                bot.sendMsg(message, BotStrings.START_STRING);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
