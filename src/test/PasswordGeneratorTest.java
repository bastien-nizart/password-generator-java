import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordGeneratorTest {
    private PasswordGenerator passwordGenerator;

    @BeforeEach
    public void initPasswordGenerator() {
        this.passwordGenerator = new PasswordGenerator(PasswordGenerator.PasswordProtection.STRONG);
    }

    @AfterEach
    public void destroyPasswordGenerator() {
        this.passwordGenerator = null;
    }

    @Test
    void showPassword() {
        System.out.println(this.passwordGenerator.generatePassword());
    }

    @Test
    void passwordProtection() {
        assertEquals(PasswordGenerator.PasswordProtection.STRONG.toString(), this.passwordGenerator.getLevelOfProtection());

        this.passwordGenerator = new PasswordGenerator();
        assertEquals(PasswordGenerator.PasswordProtection.LOW.toString(), this.passwordGenerator.getLevelOfProtection());
        assertEquals(6, this.passwordGenerator.generatePassword().length());

        this.passwordGenerator = new PasswordGenerator(6);
        assertEquals(PasswordGenerator.PasswordProtection.LOW.toString(), this.passwordGenerator.getLevelOfProtection());
        assertEquals(6, this.passwordGenerator.generatePassword().length());

        this.passwordGenerator = new PasswordGenerator(6, 3);
        assertEquals(PasswordGenerator.PasswordProtection.MEDIUM.toString(), this.passwordGenerator.getLevelOfProtection());
        assertEquals(9, this.passwordGenerator.generatePassword().length());

        this.passwordGenerator = new PasswordGenerator(6, 3, 6);
        assertEquals(PasswordGenerator.PasswordProtection.STRONG.toString(), this.passwordGenerator.getLevelOfProtection());
        assertEquals(15, this.passwordGenerator.generatePassword().length());
    }
}