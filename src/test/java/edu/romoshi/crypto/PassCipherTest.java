package edu.romoshi.crypto;

import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

class PassCipherTest {

    @Test
    void encrypt() throws Exception {
        PassCipher cipher = new PassCipher();
        PassCipher.init();
        String passEncrypt = cipher.encrypt("12334");
        assertNotSame(passEncrypt, "12334" );
    }

    @Test
    void decrypt() throws Exception {
        PassCipher cipher = new PassCipher();
        PassCipher.init();
        String passEncrypt = cipher.encrypt("12334");
        assertEquals("12334", cipher.decrypt(passEncrypt));
    }

    @Test
    void end2end() throws Exception {
        PassCipher cipher = new PassCipher();
        PassCipher.init();
        var message = "Hello, World!";
        var encrypted = cipher.encrypt(message);
        assertNotSame(message, encrypted);

        var decrypted = cipher.decrypt(encrypted);
        assertEquals(message, decrypted);
    }
}