import java.util.*;

public final class PasswordGenerator {
    private final int MIN_LENGTH = 6;
    private final Random randomGenerator = new Random();
    private final static char[] letter = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private final static char[] numericChar = "123456789".toCharArray();
    private final static char[] specialChar = "@&()§:=+-_%$*!?,".toCharArray();
    private int numberOfLetter;
    private int numberOfNumericChar;
    private PasswordProtection levelOfProtection;
    private int numberOfSpecialChar;
    public enum PasswordProtection {
        LOW, // MINIMUM OF LENGTH
        MEDIUM, // MINIMUM TWO TYPE OF CHARACTER
        STRONG, // MINIMUM ALL CHARACTER + LENGTH OF 12
        CRAZY // WOW MEN, MINIMUM ALL CHARACTER + LENGTH OF 20
    }

    public PasswordGenerator() {
        this.numberOfLetter = MIN_LENGTH;
        this.numberOfNumericChar = 0;
        this.numberOfSpecialChar = 0;
        this.levelOfProtection = PasswordProtection.LOW;
    }

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

    public PasswordGenerator(int numberOfLetter) {
        this.numberOfLetter = numberOfLetter;
        this.numberOfNumericChar = Math.max((MIN_LENGTH - numberOfLetter), 0);
        this.numberOfSpecialChar = 0;
        this.setLevelOfProtection();
    }

    public PasswordGenerator(int numberOfLetter, int numberOfNumericChar) {
        this.numberOfLetter = numberOfLetter;
        this.numberOfNumericChar = numberOfNumericChar;
        this.numberOfSpecialChar = Math.max((MIN_LENGTH - (numberOfLetter + numberOfNumericChar)), 0);
        this.setLevelOfProtection();
    }

    public PasswordGenerator(int numberOfLetter, int numberOfNumericChar, int numberOfSpecialChar) {
        this.numberOfLetter = numberOfLetter;
        this.numberOfNumericChar = numberOfNumericChar;
        this.numberOfSpecialChar = numberOfSpecialChar;
        this.setLevelOfProtection();
    }

    private int getLength() {
        return numberOfLetter + numberOfNumericChar + numberOfSpecialChar;
    }

    public String getLevelOfProtection() {
        return this.levelOfProtection.toString();
    }

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

    public void setNumberOfLowerChar(int number) throws Exception {
        if (number + numberOfNumericChar + numberOfSpecialChar < MIN_LENGTH) {
            throw new Exception(
                    "The sum of numberOfSpecialChar + numberOfUpperChar + " +
                            "numberOfUpperChar would be equal to length of password"
            );
        }

        numberOfLetter = number;
        this.setLevelOfProtection();
    }

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
