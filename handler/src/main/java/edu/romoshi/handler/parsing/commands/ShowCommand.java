package edu.romoshi.handler.parsing.commands;

import edu.romoshi.polling.bot.BotStrings;
import edu.romoshi.handler.jdbc.accounts.Accounts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.util.List;

public class ShowCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ShowCommand.class);
    private final boolean verifyKey;

    public ShowCommand(boolean verifyKey) {
        this.verifyKey = verifyKey;
    }

    @Override
    public void execute(Message message) {
        String[] messageArray = message.getText().split(" ");
        try {
            if (verifyKey) {
                if(messageArray.length == 1) {
                    List<Accounts> accounts = Accounts.getAccounts(message.getChatId().intValue());
                    for (var account : accounts) {
                        Main.bot.sendMsg(message, account.getInfo(message.getChatId().toString()));
                    }
                } else {
                    Main.bot.sendMsg(message, BotStrings.MISTAKE_MESSAGE);
                }
            } else {
                Main.bot.sendMsg(message, BotStrings.START_STRING);
            }
        } catch (Exception ex) {
            logger.error("Show command", ex);
        }
    }
}
