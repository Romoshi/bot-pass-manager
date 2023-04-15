package edu.romoshi.puller.bot.commands;

import edu.romoshi.grps.AccountOuterClass;
import edu.romoshi.puller.bot.MessageStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

import static edu.romoshi.puller.Main.stubAccount;
import static edu.romoshi.puller.bot.Bot.bot;

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
                    AccountOuterClass.IdRequest request = AccountOuterClass.IdRequest
                            .newBuilder().setId(message.getChatId().intValue()).build();
                    AccountOuterClass.GetResponse response = stubAccount.getAccounts(request);

                    List<AccountOuterClass.Account> accounts = response.getAccountsList();
                    for (var account : accounts) {
                        String info = new StringBuilder()
                                .append("Название сервиса: " + account.getNameService() + "\n")
                                .append("Логин: " + account.getLogin() + "\n")
                                .append("Пароль: " + account.getPassword() + "\n")
                                .toString();
                       bot.sendMsg(message, info);
                    }
                } else {
                    bot.sendMsg(message, MessageStrings.MISTAKE_MESSAGE);
                }
            } else {
                bot.sendMsg(message, MessageStrings.START_STRING);
            }
        } catch (Exception ex) {
            logger.error("DB is EMPTY", ex);
        }
    }
}
