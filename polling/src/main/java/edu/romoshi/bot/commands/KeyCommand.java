package edu.romoshi.bot.commands;

import at.favre.lib.crypto.bcrypt.BCrypt;
import edu.romoshi.bot.BotStrings;
import edu.romoshi.jdbc.users.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import static edu.romoshi.Main.bot;


public class KeyCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(KeyCommand.class);
    private final boolean verifyKey;

    public KeyCommand(boolean verifyKey) {
        this.verifyKey = verifyKey;
    }

    @Override
    public void execute(Message message) {
        try {
            String[] messageArray = message.getText().split(" ");
            Users user = new Users(message.getChatId().intValue());
            if(!verifyKey && user.userExist()) {
                if(messageArray.length == 2) {
                    String bcryptHashString = BCrypt.withDefaults().hashToString(12, messageArray[1].toCharArray());
                    user.addUser(bcryptHashString);
                    bot.sendMsg(message, BotStrings.KEY_STRING);
                    bot.sendMsg(message, BotStrings.AFTER_KEY_STRING);
                } else {
                    bot.sendMsg(message, BotStrings.MISTAKE_MESSAGE);
                }
            } else if (!messageArray[1].isEmpty()){
                bot.sendMsg(message, BotStrings.KEY_EXIST);
            }
        } catch (Exception ex) {
            logger.error("Key command", ex);
        }
    }
}
