package edu.romoshi.puller.bot.commands;

import edu.romoshi.grps.AccountOuterClass;
import edu.romoshi.puller.bot.MessageStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import static edu.romoshi.puller.Puller.stubAccount;
import static edu.romoshi.puller.bot.Bot.bot;


public class DeleteCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(DeleteCommand.class);
    private final boolean verifyKey;

    public DeleteCommand(boolean verifyKey) {
        this.verifyKey = verifyKey;
    }
    @Override
    public void execute(Message message) {
        String[] messageArray = message.getText().split(" ");
        if (verifyKey) {
            if(messageArray.length == 2) {
                AccountOuterClass.DeleteRequest request = AccountOuterClass.DeleteRequest
                        .newBuilder().setId(message.getChatId().intValue()).setNameService(messageArray[1]).build();
                try {
                    stubAccount.deleteAccount(request);
                } catch (Exception ex) {
                    logger.info("bot delete password");
                }
                bot.sendMsg(message, MessageStrings.DELETE_STRING);
            } else {
                bot.sendMsg(message, MessageStrings.MISTAKE_MESSAGE);
            }
        } else {
            bot.sendMsg(message, MessageStrings.START_STRING);
        }
    }
}
