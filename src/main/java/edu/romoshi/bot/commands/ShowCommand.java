package edu.romoshi.bot.commands;

import edu.romoshi.bot.BotStrings;
import edu.romoshi.database.SQLUtils;
import edu.romoshi.user.Accounts;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

import static edu.romoshi.Main.bot;

public class ShowCommand implements Command {
    private final boolean verifyMK;

    public ShowCommand(boolean verifyMK) {
        this.verifyMK = verifyMK;
    }

    @Override
    public void execute(Message message) {
        String[] messageArray = message.getText().split(" ");
        try {
            if (verifyMK) {
                if(messageArray.length == 1) {
                    List<Accounts> accounts = SQLUtils.getAccounts(message);
                    for (var account : accounts) {
                        bot.sendMsg(message, Accounts.getServices(account, message));
                    }
                } else {
                    bot.sendMsg(message, BotStrings.MISTAKE_MESSAGE);
                }
            }else {
                bot.sendMsg(message, BotStrings.START_STRING);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
