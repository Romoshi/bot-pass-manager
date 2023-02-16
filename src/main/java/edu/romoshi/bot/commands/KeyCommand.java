package edu.romoshi.bot.commands;

import at.favre.lib.crypto.bcrypt.BCrypt;
import edu.romoshi.bot.BotStrings;
import edu.romoshi.database.SQLUtils;
import org.telegram.telegrambots.meta.api.objects.Message;
import static edu.romoshi.Main.bot;

public class KeyCommand implements Command {
    private final boolean verifyMK;

    public KeyCommand(boolean verifyMK) {
        this.verifyMK = verifyMK;
    }

    @Override
    public void execute(Message message) {
        try {
            String[] messageArray = message.getText().split(" ");
            if(!verifyMK && SQLUtils.userExist(message)) {
                if(messageArray.length == 2) {
                    String bcryptHashString = BCrypt.withDefaults().hashToString(12, messageArray[1].toCharArray());
                    SQLUtils.createUser(message, bcryptHashString);
                    bot.sendMsg(message, BotStrings.KEY_STRING);
                    bot.sendMsg(message, BotStrings.AFTER_KEY_STRING);
                } else {
                    bot.sendMsg(message, BotStrings.MISTAKE_MESSAGE);
                }
            } else if (!messageArray[1].isEmpty()){
                bot.sendMsg(message, BotStrings.KEY_EXIST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
