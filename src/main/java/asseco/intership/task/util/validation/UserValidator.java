package asseco.intership.task.util.validation;

import asseco.intership.task.user.model.User;
import org.apache.commons.lang.StringUtils;

import static asseco.intership.task.util.validation.UsernameValidator.isUsernameOk;
import static asseco.intership.task.util.validation.StringValidator.isAnyNullOrEmpty;
import static asseco.intership.task.util.validation.UserValidator.UserValidation.*;

public final class UserValidator {

    private static final int FIRST_CHARACTER = 0;

    private UserValidator() {
    }

    public static UserValidation validateUser(User user) {
        if (isAnyNullOrEmpty(user.getFirstName(), user.getLastName(), user.getPassword(), user.getAge())) {
            return EMPTY_FIELDS;
        }
        if (!startsWithCapitalLetter(user.getFirstName(), user.getLastName())) {
            return NO_CAPITAL_LETTERS;
        }
        if (!StringUtils.isNumeric(user.getAge())) {
            return AGE_NOT_NUMERAL;
        }
        return OK;
    }

    private static boolean startsWithCapitalLetter(String... strings) {
        for (String string : strings) {
            if (!Character.isUpperCase(string.codePointAt(FIRST_CHARACTER))) {
                return false;
            }
        }
        return true;
    }

    public enum UserValidation {
        OK, EMPTY_FIELDS, NO_CAPITAL_LETTERS, AGE_NOT_NUMERAL, USERNAME_BAD_FORMAT
    }
}
