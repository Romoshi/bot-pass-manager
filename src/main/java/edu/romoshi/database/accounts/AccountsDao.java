package edu.romoshi.database.accounts;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface AccountsDao {

    void addAccount(Message message);
}
