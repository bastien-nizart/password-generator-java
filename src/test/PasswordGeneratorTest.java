import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordGeneratorTest {
    private PasswordGenerator passwordGenerator;

    @BeforeEach
    public void initCalculatrice() {
        this.passwordGenerator = new PasswordGenerator(PasswordGenerator.PasswordProtection.CRAZY);
    }

    @AfterEach
    public void invalideCalculatrice() {
        this.passwordGenerator = null;
    }

    @Test
    @DisplayName("Multiple test with generation of password")
    void generatePassword() {
        String passwordA = this.passwordGenerator.generatePassword();
        String passwordB = this.passwordGenerator.generatePassword();

        // Check if the password have good length
        assertEquals(10, passwordA.length());
        // Check two password are different
        assertNotEquals(passwordA, passwordB);
    }

    @Test
    @DisplayName("Show password")
    void showPassword() {
        System.out.println(this.passwordGenerator.generatePassword());
    }
}