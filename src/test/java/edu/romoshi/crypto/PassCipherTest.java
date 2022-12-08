package edu.romoshi.crypto;

import org.junit.jupiter.api.Test;

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
        assertEquals("12334", pass.decrypt(passEncrypt));
    }
}