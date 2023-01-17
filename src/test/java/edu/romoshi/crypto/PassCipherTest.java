package edu.romoshi.crypto;

import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

class PassCipherTest {
    @Test
    void encrypt() throws Exception {
        Encryption en = new Encryption();
        String actual = "Test";
        String unexpected = en.encrypt("Test");
        assertNotSame(unexpected, actual);
    }

    @Test
    void decrypt() throws Exception {
        Encryption en = new Encryption();
        String encryptedWord = en.encrypt("Test");

        Decryption de = new Decryption();
        String actual = de.decrypt(encryptedWord);

        assertEquals("Test", actual);
    }

    @Test
    void end2end() throws Exception {

        var message = "Hello, World!";

        Encryption en = new Encryption();
        var encrypted = en.encrypt(message);
        assertNotSame(message, encrypted);

        Decryption de = new Decryption();
        var decrypted = de.decrypt(encrypted);
        assertEquals(message, decrypted);
    }
}