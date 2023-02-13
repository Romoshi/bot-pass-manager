package edu.romoshi.bot.commands;

import edu.romoshi.bot.PassManagerBot;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface Command {
    void execute(Message message);
}
