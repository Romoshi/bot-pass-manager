package edu.romoshi.bot.commands;

import edu.romoshi.bot.PassManagerBot;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

public class Handler {
    private final ConcurrentMap<String, Command> commands;
    public boolean flag = false;

    public Handler(ConcurrentMap <String, Command> commands) {
        this.commands = commands;
    }

    public void addCommand(String cmd, Command command) {
        commands.put(cmd, command);
    }

    public void runCommand(Message message) {
        String[] messageArray = message.getText().split(" ");
        for(var entry : commands.entrySet()) {
            if(Objects.equals(entry.getKey(), messageArray[0])) {
                entry.getValue().execute(message);
                flag = true;
            }
        }
    }
}
