package edu.romoshi.puller.bot.commands;

import at.favre.lib.crypto.bcrypt.BCrypt;
import edu.romoshi.grpc.User;
import edu.romoshi.puller.bot.MessageStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import static edu.romoshi.puller.Puller.stubUser;
import static edu.romoshi.puller.bot.Bot.bot;



public class KeyCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(KeyCommand.class);
    private final boolean verifyKey;

    public KeyCommand(boolean verifyKey) {
        this.verifyKey = verifyKey;
    }

    @Override
    public void execute(Message message) {
        String[] messageArray = message.getText().split(" ");
        User.IdRequest requestExist = User.IdRequest
                .newBuilder()
                .setId(message.getChatId().intValue())
                .build();
        User.ExistResponse response = stubUser.userExist(requestExist);

        if(!verifyKey && response.getResponse()) {
            if(messageArray.length == 2) {
                String bcryptHashString = BCrypt.withDefaults().hashToString(12, messageArray[1].toCharArray());
                   User.AddRequest request = User.AddRequest
                           .newBuilder()
                           .setId(message.getChatId().intValue())
                           .setKey(bcryptHashString)
                           .build();
                try {
                    stubUser.addUser(request);
                } catch (Exception ex) {
                    logger.info("bot add password");
                }
                bot.sendMsg(message, MessageStrings.KEY_STRING);
                bot.sendMsg(message, MessageStrings.AFTER_KEY_STRING);
            } else {
                bot.sendMsg(message, MessageStrings.MISTAKE_MESSAGE);
            }
        } else if (!messageArray[1].isEmpty()){
            bot.sendMsg(message, MessageStrings.KEY_EXIST);
        }
    }
}
