package edu.romoshi.handler.parsing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import edu.romoshi.handler.jdbc.Tables;
import edu.romoshi.handler.parsing.commands.*;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Transmitter {
    private static final Logger logger = LoggerFactory.getLogger(Transmitter.class);
//    private final int AUTO_DELETE_MESSAGE_TIME = 3 * (int)Math.pow(10, 5); //5 minute
    private final Receiver receiver = new Receiver(new ConcurrentHashMap<>());
    private final Cache cache = new Cache(new ConcurrentHashMap<>(), new ArrayList<>());
    private void parseMessage(Message message) {
        if(!Tables.isFlag()) {
            Tables.initTables();
        }

        cache.autoDeleteCache(message);
        cache.add(message);

        initCommands(cache.findPassFromCache(message));
        receiver.runCommand(message);

        if(!receiver.isFlag()) {
            DefaultCommand defaultCommand = new DefaultCommand();
            defaultCommand.execute(message);
        } else {
            receiver.setFlag(false);
        }
    }

    private void initCommands(boolean verifyKey) {
        receiver.addCommand(CommandStrings.START_COMMAND, new StartCommand());
        receiver.addCommand(CommandStrings.KEY_COMMAND, new KeyCommand(verifyKey));
        receiver.addCommand(CommandStrings.SHOW_COMMAND, new ShowCommand(verifyKey));
        receiver.addCommand(CommandStrings.SAVE_COMMAND, new SaveCommand(verifyKey));
        receiver.addCommand(CommandStrings.DELETE_COMMAND, new DeleteCommand(verifyKey));
        receiver.addCommand(CommandStrings.HELP_COMMAND, new HelpCommand());
    }
}
