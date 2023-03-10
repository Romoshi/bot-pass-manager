package edu.romoshi.bot.commands;

import edu.romoshi.Log;
import edu.romoshi.bot.BotStrings;
import edu.romoshi.jdbc.accounts.Accounts;
import org.telegram.telegrambots.meta.api.objects.Message;
import static edu.romoshi.Main.bot;

public class DeleteCommand implements Command {
    private final boolean verifyKey;

    public DeleteCommand(boolean verifyKey) {
        this.verifyKey = verifyKey;
    }
    @Override
    public void execute(Message message) {
        String[] messageArray = message.getText().split(" ");
        try {
            if (verifyKey) {
                if(messageArray.length == 2) {
                    Accounts.deleteAccount(messageArray[1], message);
                    bot.sendMsg(message, BotStrings.DELETE_STRING);
                } else {
                    bot.sendMsg(message, BotStrings.MISTAKE_MESSAGE);
                }
            }else {
                bot.sendMsg(message, BotStrings.START_STRING);
            }
        } catch (Exception ex) {
            Log.logger.error("Delete command", ex);
        }
    }
}
