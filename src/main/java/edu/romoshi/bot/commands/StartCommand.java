package edu.romoshi.bot.commands;

import edu.romoshi.Log;
import edu.romoshi.bot.BotStrings;
import org.telegram.telegrambots.meta.api.objects.Message;

import static edu.romoshi.Main.bot;

public class StartCommand implements Command {

    @Override
    public void execute(Message message) {
        try {
            bot.sendMsg(message, BotStrings.START_STRING);
        } catch (Exception ex) {
            Log.logger.error("Start command", ex);
        }
    }
}
