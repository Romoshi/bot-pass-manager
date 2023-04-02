package edu.romoshi.handler.parsing.commands;

import at.favre.lib.crypto.bcrypt.BCrypt;

import edu.romoshi.handler.jdbc.users.Users;
import edu.romoshi.handler.parsing.Handler;
import edu.romoshi.handler.parsing.MessageStrings;
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
                    Handler.hadlerQueue.add(MessageStrings.KEY_STRING);
                    Handler.hadlerQueue.add(MessageStrings.AFTER_KEY_STRING);
                } else {
                    Handler.hadlerQueue.add(MessageStrings.MISTAKE_MESSAGE);
                }
            } else if (!messageArray[1].isEmpty()){
                Handler.hadlerQueue.add(MessageStrings.KEY_EXIST);
            }
        } catch (Exception ex) {
            logger.error("Key command", ex);
        }
    }
}
