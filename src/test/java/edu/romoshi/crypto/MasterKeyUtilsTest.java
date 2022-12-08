package edu.romoshi.crypto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MasterKeyUtilsTest {

    @Test
    void verifyPassword() {
        String salt = MasterKeyUtils.generateSalt(512).get();
        String originPass = "12345";
        String key = MasterKeyUtils.hashPassword(originPass, salt).get();
        assertTrue(MasterKeyUtils.verifyPassword("12345", key, salt));
    }
}