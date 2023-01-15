package edu.romoshi.crypto;

import org.junit.jupiter.api.Test;
import se.simbio.encryption.Encryption;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

class PassCipherTest {

    @Test
    void encrypt() throws Exception {
        PassCipher pass = new PassCipher();
        pass.init();
        String passEncrypt = pass.encrypt("12334");
        assertNotSame(passEncrypt, "12334" );
    }

    @Test
    void decrypt() throws Exception {
        PassCipher pass = new PassCipher();
        pass.init();
        String passEncrypt = pass.encrypt("12334");
        assertEquals("12334", PassCipher.decrypt(passEncrypt));
    }

    @Test
    void end2end() throws Exception {
        PassCipher cypher = new PassCipher();
        cypher.init();
        var message = "Hello, World!";
        var encrypted = cypher.encrypt(message);
        assertNotSame(message, encrypted);

        var decrypted = PassCipher.decrypt(encrypted);
        assertEquals(message, decrypted);
    }
}