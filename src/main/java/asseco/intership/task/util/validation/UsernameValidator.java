package asseco.intership.task.util.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UsernameValidator {

    private static final Pattern SMALL_LETTER = Pattern.compile("[a-z]");
    private static final Pattern DIGIT = Pattern.compile("[0-9]");
    private static final int SMALL_LETTERS_MIN_AMOUNT = 2;
    private static final int DIGITS_AMOUNT = 2;

    private UsernameValidator() {
    }

    public static boolean isUsernameOk(String username) {
        return hasAtLeastAmountOfSmallLetters(username) && hasExactAmountOfDigits(username);
    }

    private static boolean hasAtLeastAmountOfSmallLetters(String string) {

        return getAmountOfSmallLetters(string) >= SMALL_LETTERS_MIN_AMOUNT;
    }

    private static int getAmountOfSmallLetters(String string) {
        Matcher matcher = SMALL_LETTER.matcher(string);
        return countFound(matcher);
    }

    private static boolean hasExactAmountOfDigits(String string) {
        return getAmountOfDigits(string) == DIGITS_AMOUNT;
    }

    private static int getAmountOfDigits(String string) {
        Matcher matcher = DIGIT.matcher(string);
        return countFound(matcher);
    }

    private static int countFound(Matcher matcher) {
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }
}
