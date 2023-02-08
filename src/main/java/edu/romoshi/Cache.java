package edu.romoshi;

import at.favre.lib.crypto.bcrypt.BCrypt;
import edu.romoshi.database.SQLUtils;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.*;

public class Cache {

    private final Map<Integer, List<String>> cacheMsg;
    private final List<String> messages;
    private final int AUTO_DELETE_CACHE_TIME = (int) (86.4 * Math.pow(10, 5)); //24 hours

    public Cache(Map<Integer, List<String>> c, List<String> mes) {
        this.cacheMsg = c;
        this.messages = mes;
    }

    public void createCache(Message message) {
        cacheMsg.put(message.getChatId().intValue(), messages);
        messages.add(message.getText());
    }
    public boolean findPassFromCache(Message message) {
        if (SQLUtils.mkExist(message)) return false;

        for(Map.Entry<Integer, List<String>> entry : cacheMsg.entrySet()) {
            if(entry.getKey() == message.getChatId().intValue()) {
                for (var item : entry.getValue()) {
                    BCrypt.Result result = BCrypt.verifyer().verify(item.toCharArray(), SQLUtils.getMk(message));
                    if(result.verified) return true;
                }
            }
        }

        return false;
    }

    public void autoDeleteCache() {
        final Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                cacheMsg.clear();
                time.cancel();
            }
        }, AUTO_DELETE_CACHE_TIME);
    }
}
