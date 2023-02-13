package edu.romoshi.bot.commands;

import edu.romoshi.bot.BotStrings;
import edu.romoshi.bot.PassManagerBot;
import org.telegram.telegrambots.meta.api.objects.Message;

public class StartCommand implements Command {

    @Override
    public void execute(PassManagerBot bot, Message message) {
        try {
            bot.sendMsg(message, BotStrings.START_STRING);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
