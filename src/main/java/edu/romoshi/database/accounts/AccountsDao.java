package edu.romoshi.database.accounts;

import edu.romoshi.user.Accounts;
import java.util.Collections;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public interface AccountsDao {

    static void addAccount(Accounts account, Message message) {}
    static List<Accounts> getAccounts(Message message) {
        return Collections.emptyList();
    }
    static void deleteAccount(String nameService, Message message) {}
}
