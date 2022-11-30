import java.util.*;

public final class PasswordGenerator {
    /** @var This is a minimum length of all password generate with this class. */
    private final int MIN_LENGTH = 6;
    /** @var Random generator for select randomly a char. */
    private final Random randomGenerator = new Random();
    /** @var Array of char with any character "letters". */
    private final static char[] letter = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    /** @var Array of char with any character "numeric". */
    private final static char[] numericChar = "123456789".toCharArray();
    /** @var Array of char with any special character. */
    private final static char[] specialChar = "@&()§:=+-_%$*!?,".toCharArray();
    /** @var Number of character "letters". */
    private int numberOfLetter;
    /** @var Number of character "numeric". */
    private int numberOfNumericChar;
    /** @var Number of special character. */
    private int numberOfSpecialChar;
    /** @var The level protection setting of current password. */
    private PasswordProtection levelOfProtection;

    /**
     * Enumeration of different types of protection.
     */
    public enum PasswordProtection {
        LOW, // MINIMUM OF LENGTH
        MEDIUM, // MINIMUM TWO TYPE OF CHARACTER
        STRONG, // MINIMUM ALL CHARACTER + LENGTH OF 12
        CRAZY // WOW MEN, MINIMUM ALL CHARACTER + LENGTH OF 20
    }

    /**
     * Constructor of PasswordGenerator without any parameter.
     */
    public PasswordGenerator() {
        this.numberOfLetter = MIN_LENGTH;
        this.numberOfNumericChar = 0;
        this.numberOfSpecialChar = 0;
        this.levelOfProtection = PasswordProtection.LOW;
    }

    /**
     * This constructor choose all parameters based on the
     * level protection of the password.
     * @param protection level protection of the password.
     */
    public PasswordGenerator(PasswordProtection protection) {
        switch (protection) {
            case LOW -> {
                this.numberOfLetter = MIN_LENGTH;
                this.numberOfNumericChar = 0;
                this.numberOfSpecialChar = 0;
                this.levelOfProtection = PasswordProtection.LOW;
            }
            case MEDIUM -> {
                this.numberOfLetter = MIN_LENGTH;
                this.numberOfNumericChar = MIN_LENGTH / 2;
                this.numberOfSpecialChar = 0;
                this.levelOfProtection = PasswordProtection.MEDIUM;
            }
            case STRONG -> {
                this.numberOfLetter = MIN_LENGTH;
                this.numberOfNumericChar = MIN_LENGTH;
                this.numberOfSpecialChar = MIN_LENGTH / 2;
                this.levelOfProtection = PasswordProtection.STRONG;
            }
            case CRAZY -> {
                this.numberOfLetter = MIN_LENGTH * 2;
                this.numberOfNumericChar = MIN_LENGTH;
                this.numberOfSpecialChar = MIN_LENGTH;
                this.levelOfProtection = PasswordProtection.CRAZY;
            }
        }
    }

    /**
     * This constructor choose all parameters ignored.
     * @param numberOfLetter the number of letters in the password.
     */
    public PasswordGenerator(int numberOfLetter) {
        this.numberOfLetter = numberOfLetter;
        this.numberOfNumericChar = Math.max((MIN_LENGTH - numberOfLetter), 0);
        this.numberOfSpecialChar = 0;
        this.setLevelOfProtection();
    }

    /**
     * This constructor choose all parameters ignored.
     * @param numberOfLetter the number of letters in the password.
     * @param numberOfNumericChar the number of numeric characters in the password.
     */
    public PasswordGenerator(int numberOfLetter, int numberOfNumericChar) {
        this.numberOfLetter = numberOfLetter;
        this.numberOfNumericChar = numberOfNumericChar;
        this.numberOfSpecialChar = Math.max((MIN_LENGTH - (numberOfLetter + numberOfNumericChar)), 0);
        this.setLevelOfProtection();
    }

    /**
     * This constructor choose all parameters ignored.
     * @param numberOfLetter the number of letters in the password.
     * @param numberOfNumericChar the number of numeric characters in the password.
     * @param numberOfSpecialChar the number of special characters in the password.
     */
    public PasswordGenerator(int numberOfLetter, int numberOfNumericChar, int numberOfSpecialChar) {
        this.numberOfLetter = numberOfLetter;
        this.numberOfNumericChar = numberOfNumericChar;
        this.numberOfSpecialChar = numberOfSpecialChar;
        this.setLevelOfProtection();
    }

    /**
     * A simple getter for the length of password.
     * @return length of password.
     */
    private int getLength() {
        return numberOfLetter + numberOfNumericChar + numberOfSpecialChar;
    }

    /**
     * Choose the level protection of password automatically thanks to
     * the length of password and its constitution.
     */
    private void setLevelOfProtection() {
        if (this.numberOfNumericChar > 0 && this.numberOfLetter > 0 && this.numberOfSpecialChar > 0) {
            if (getLength() >= 20) {
                this.levelOfProtection = PasswordProtection.CRAZY;
            } else {
                this.levelOfProtection = PasswordProtection.STRONG;
            }
        } else if (
                (this.numberOfNumericChar > 0 && this.numberOfLetter > 0) ||
                        (this.numberOfSpecialChar > 0 && this.numberOfLetter > 0) ||
                        (this.numberOfNumericChar > 0 && this.numberOfSpecialChar > 0)
        ) {
            this.levelOfProtection = PasswordProtection.MEDIUM;
        } else {
            this.levelOfProtection = PasswordProtection.LOW;
        }
    }

    /**
     * This getter return the current level protection. All password
     * generated with this generator will all have this level of
     * protection.
     * @return the current level protection as a String.
     */
    public String getLevelOfProtection() {
        return this.levelOfProtection.toString();
    }

    /**
     * Set the number of letter in the password.
     * @param numberOfLetter The new number of letters that
     * you want to set.
     * @throws Exception Thrown if the MINIMUM_LENGTH of password
     * not met with this change.
     */
    public void setNumberOfLetter(int numberOfLetter) throws Exception {
        if (numberOfLetter + this.numberOfNumericChar + this.numberOfSpecialChar < MIN_LENGTH) {
            throw new Exception("Minimum password size is not reached");
        }

        this.numberOfLetter = numberOfLetter;
        this.setLevelOfProtection();
    }

    /**
     * Set the number of numeric chars in the password.
     * @param numberOfNumericChar The new number of numeric chars that
     * you want to set.
     * @throws Exception Thrown if the MINIMUM_LENGTH of password
     * not met with this change.
     */
    public void setNumberOfNumericChar(int numberOfNumericChar) throws Exception {
        if (numberOfLetter + this.numberOfLetter + this.numberOfSpecialChar < MIN_LENGTH) {
            throw new Exception("Minimum password size is not reached");
        }

        this.numberOfNumericChar = numberOfNumericChar;
        this.setLevelOfProtection();
    }

    /**
     * Set the number of special chars in the password.
     * @param numberOfSpecialChar The new number of special chars that
     * you want to set.
     * @throws Exception Thrown if the MINIMUM_LENGTH of password
     * not met with this change.
     */
    public void setNumberOfSpecialChar(int numberOfSpecialChar) throws Exception {
        if (numberOfSpecialChar + this.numberOfNumericChar + this.numberOfLetter < MIN_LENGTH) {
            throw new Exception("Minimum password size is not reached");
        }

        this.numberOfSpecialChar = numberOfSpecialChar;
        this.setLevelOfProtection();
    }

    /**
     * Generate a random password with the different parameters
     * included in this class.
     * @return the password.
     */
    public String generatePassword() {
        ArrayList<Character> password = new ArrayList<>();
        StringBuilder passwordBuilder = new StringBuilder();

        for (int i = 0; i < getLength(); i++) {
            if (i <= numberOfLetter) {
                password.add(PasswordGenerator.letter[randomGenerator.nextInt(PasswordGenerator.letter.length)]);
            } else if (i <= numberOfNumericChar+numberOfLetter) {
                password.add(PasswordGenerator.numericChar[randomGenerator.nextInt(PasswordGenerator.numericChar.length)]);
            } else {
                password.add(PasswordGenerator.specialChar[randomGenerator.nextInt(PasswordGenerator.specialChar.length)]);
            }
        }

        Collections.shuffle(password);

        for (Character character : password) {
            passwordBuilder.append(character);
        }

        return passwordBuilder.toString();
    }
}
