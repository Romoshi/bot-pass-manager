package edu.romoshi.puller.bot.commands;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

public class Receiver {
    private final ConcurrentMap<String, Command> commands;
    private boolean flag = false;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Receiver(ConcurrentMap <String, Command> commands) {
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
