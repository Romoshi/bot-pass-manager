package edu.romoshi.puller.bot;

import at.favre.lib.crypto.bcrypt.BCrypt;
import edu.romoshi.core.dto.users.Users;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

public class Cache {

    private final ConcurrentMap<Integer, List<String>> cacheMsg;
    private final List<String> messages;
    private final int DEFAULT_TIMEOUT = (int) (86.4 * Math.pow(10, 5)); //24 hours


    public Cache(ConcurrentMap <Integer, List<String>> c, List<String> mes) {
        this.cacheMsg = c;
        this.messages = mes;
    }


    public void add(Message message) {
        messages.add(message.getText());
        cacheMsg.put(message.getChatId().intValue(), messages);
    }
    public boolean findPassFromCache(Message message) {
        Users user = new Users(message.getChatId().intValue());
        if (user.getMk() == null) return false;
        for(Map.Entry<Integer, List<String>> entry : cacheMsg.entrySet()) {
            if(entry.getKey() == message.getChatId().intValue()) {
                for (var item : entry.getValue()) {
                    BCrypt.Result result = BCrypt.verifyer().verify(item.toCharArray(), user.getMk());
                    if(result.verified) return true;
                }
            }
        }
        return false;
    }

    public void autoDeleteCache(Message message) {
        final Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                for(Map.Entry<Integer, List<String>> entry : cacheMsg.entrySet()) {
                    if(entry.getKey() == message.getChatId().intValue()) {
                        entry.getValue().clear();
                    }
                }
                time.cancel();
            }
        }, DEFAULT_TIMEOUT);
    }
}
