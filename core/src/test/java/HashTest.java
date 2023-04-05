import at.favre.lib.crypto.bcrypt.BCrypt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HashTest {

    @Test
    void verifyMK() {
        String originPass = "123456789";
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, originPass.toCharArray());
        BCrypt.Result result = BCrypt.verifyer().verify(originPass.toCharArray(), bcryptHashString);
        assertTrue(result.verified);
    }
}
