
import edu.romoshi.core.crypto.Decryption;
import edu.romoshi.core.crypto.Encryption;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncryptDecryptTest {
    @Test
    void encrypt() throws Exception {
        String pass = "123";
        Encryption en = new Encryption();
        String actual = "Test";
        String unexpected = en.encrypt("Test", pass);
        assertNotSame(unexpected, actual);
    }

    @Test
    void decrypt() throws Exception {
        String pass = "123";
        Encryption en = new Encryption();
        String encryptedWord = en.encrypt("Test", pass);

        Decryption de = new Decryption();
        String actual = de.decrypt(encryptedWord, pass);

        assertEquals("Test", actual);
    }

    @Test
    void end2end() throws Exception {
        String pass = "123";
        var message = "Hello, World!";

        Encryption en = new Encryption();
        var encrypted = en.encrypt(message, pass);
        assertNotSame(message, encrypted);

        Decryption de = new Decryption();
        var decrypted = de.decrypt(encrypted, pass);
        assertEquals(message, decrypted);
    }
}