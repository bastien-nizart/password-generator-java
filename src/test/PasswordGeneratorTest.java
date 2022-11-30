import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordGeneratorTest {
    private PasswordGenerator passwordGenerator;
    private final PasswordProtection currentPasswordProtection = PasswordProtection.STRONG;

    @BeforeEach
    public void initPasswordGenerator() {
        this.passwordGenerator = new PasswordGenerator(this.currentPasswordProtection);
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
    void allSetters() throws Exception {
        this.passwordGenerator = new PasswordGenerator(PasswordProtection.LOW);
        Throwable exception = assertThrows(Exception.class, () -> this.passwordGenerator.setNumberOfLetter(4));
        assertEquals("Minimum password size is not reached", exception.getMessage());
        assertEquals(6, this.passwordGenerator.generatePassword().length());

        this.passwordGenerator = new PasswordGenerator(4, 4, 4);
        this.passwordGenerator.setNumberOfLetter(6);
        assertEquals(14, this.passwordGenerator.generatePassword().length());
        this.passwordGenerator.setNumberOfNumericChar(8);
        assertEquals(18, this.passwordGenerator.generatePassword().length());
        this.passwordGenerator.setNumberOfSpecialChar(7);
        assertEquals(21, this.passwordGenerator.generatePassword().length());
        assertEquals(PasswordProtection.CRAZY.toString(), this.passwordGenerator.getLevelOfProtection());
    }

    @Test
    void constructors() {
        assertEquals(this.currentPasswordProtection.toString(), this.passwordGenerator.getLevelOfProtection());

        this.passwordGenerator = new PasswordGenerator();
        assertEquals(PasswordProtection.LOW.toString(), this.passwordGenerator.getLevelOfProtection());
        assertEquals(6, this.passwordGenerator.generatePassword().length());

        this.passwordGenerator = new PasswordGenerator(6);
        assertEquals(PasswordProtection.LOW.toString(), this.passwordGenerator.getLevelOfProtection());
        assertEquals(6, this.passwordGenerator.generatePassword().length());

        this.passwordGenerator = new PasswordGenerator(6, 3);
        assertEquals(PasswordProtection.MEDIUM.toString(), this.passwordGenerator.getLevelOfProtection());
        assertEquals(9, this.passwordGenerator.generatePassword().length());

        this.passwordGenerator = new PasswordGenerator(6, 3, 6);
        assertEquals(PasswordProtection.STRONG.toString(), this.passwordGenerator.getLevelOfProtection());
        assertEquals(15, this.passwordGenerator.generatePassword().length());
    }
}