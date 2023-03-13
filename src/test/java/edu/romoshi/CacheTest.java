package edu.romoshi;

import edu.romoshi.jdbc.users.Users;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

    @Test
    void add() {
        final Cache cache = new Cache(new ConcurrentHashMap<>(), new ArrayList<>());
        Chat chat = new Chat();
        chat.setId(614213L);

        Message message = new Message();
        message.setChat(chat);
        message.setText("Hello world");

        assertEquals(0, cache.getSize());
        cache.add(message);
        assertEquals(1, cache.getSize());
    }

    @Test
    void findPassFromCache() {

    }
}