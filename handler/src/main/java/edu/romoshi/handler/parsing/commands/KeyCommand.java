package edu.romoshi.handler.parsing.commands;

import at.favre.lib.crypto.bcrypt.BCrypt;
import edu.romoshi.polling.bot.BotStrings;
import edu.romoshi.handler.jdbc.users.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;


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
                    Main.bot.sendMsg(message, BotStrings.KEY_STRING);
                    Main.bot.sendMsg(message, BotStrings.AFTER_KEY_STRING);
                } else {
                    Main.bot.sendMsg(message, BotStrings.MISTAKE_MESSAGE);
                }
            } else if (!messageArray[1].isEmpty()){
                Main.bot.sendMsg(message, BotStrings.KEY_EXIST);
            }
        } catch (Exception ex) {
            logger.error("Key command", ex);
        }
    }
}
