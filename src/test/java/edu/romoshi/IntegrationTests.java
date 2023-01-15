package edu.romoshi;

import edu.romoshi.DBTools.CRUDUtils;
import edu.romoshi.crypto.PassCipher;
import edu.romoshi.userTools.AccWhichSave;
import java.util.List;

public class IntegrationTests {
    public static void main(String[] args) throws Exception {
        CRUDUtils.createTable();

        PassCipher passCipher = new PassCipher();
        passCipher.init();

        String nameService = "oasis1";
        String login = "iam@gmail.com";

        String password = "qwerty";
        String passEncrypt = passCipher.encrypt(password);
        AccWhichSave acc = new AccWhichSave(nameService, login, passEncrypt);
        CRUDUtils.saveAccount(acc);
        List<AccWhichSave> accounts = CRUDUtils.getAccounts();
        accounts.forEach(account -> System.out.println(account.getPassword()));

        String password1 = "qwerty1";
        String passEncrypt1 = passCipher.encrypt(password1);
        AccWhichSave acc1 = new AccWhichSave(nameService, login, passEncrypt1);
        CRUDUtils.saveAccount(acc1);
        List<AccWhichSave> accounts1 = CRUDUtils.getAccounts();
        accounts1.forEach(account1 -> System.out.println(account1.getPassword()));

    }
}
