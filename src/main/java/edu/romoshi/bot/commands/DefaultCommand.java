package edu.romoshi.bot.commands;


import edu.romoshi.bot.BotStrings;
import org.telegram.telegrambots.meta.api.objects.Message;
import static edu.romoshi.Main.bot;

public class DefaultCommand implements Command {
    @Override
    public void execute(Message message) {
        try {
            bot.sendMsg(message, BotStrings.DEFAULT_STRING);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
