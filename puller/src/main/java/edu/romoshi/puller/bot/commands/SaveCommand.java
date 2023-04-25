package edu.romoshi.puller.bot.commands;

import edu.romoshi.grps.AccountOuterClass;
import edu.romoshi.puller.bot.MessageStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import static edu.romoshi.puller.Puller.stubAccount;
import static edu.romoshi.puller.bot.Bot.bot;

public class SaveCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(SaveCommand.class);
    private final boolean verifyKey;

    public SaveCommand(boolean verifyKey) {
        this.verifyKey = verifyKey;
    }
    @Override
    public void execute(Message message) {
        try {
            String[] messageArray = message.getText().split(" ");
            if (verifyKey) {
                if(messageArray.length == 4) {
                    AccountOuterClass.Account request = AccountOuterClass.Account
                            .newBuilder().setId(message.getChatId().intValue()).setNameService(messageArray[1]).setLogin(messageArray[2])
                            .setPassword(messageArray[3]).build();
                    stubAccount.addAccount(request);
                    bot.sendMsg(message, MessageStrings.SAVE_STRING);
                } else {
                    bot.sendMsg(message, MessageStrings.MISTAKE_MESSAGE);
                }
            } else {
                bot.sendMsg(message, MessageStrings.MISTAKE_MESSAGE);
            }
        } catch (Exception ex) {
            logger.error("Save command", ex);
        }
    }
}
