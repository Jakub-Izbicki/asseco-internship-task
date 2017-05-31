package asseco.intership.task.util.validation;

import asseco.intership.task.user.model.User;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static asseco.intership.task.util.validation.UserValidator.UserValidation.OK;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(DataProviderRunner.class)
public class UserValidatorTest {

    private static final String USERNAME = "JohnDoe20";
    private static final String USER_AGE = "20";
    private static final String USER_LASTNAME = "Doe";
    private static final String USER_FIRSTNAME = "John";
    private static final String USER_PASSWORD = "string";
    private static final String INVALID_USERNAME = "JohnDoe20";
    private static final String INVALID_USER_AGE = null;
    private static final String INVALID_USER_LASTNAME = "doe";
    private static final String INVALID_USER_FIRSTNAME = "john";
    private static final String INVALID_USER_PASSWORD = null;

    @DataProvider
    public static Object[] getValidUser() {
        final User user = User.builder()
                .username(USERNAME)
                .age(USER_AGE)
                .firstName(USER_FIRSTNAME)
                .lastName(USER_LASTNAME)
                .password(USER_PASSWORD)
                .build();
        return new Object[]{user};
    }

    @DataProvider
    public static Object[] getInvalidUser() {
        final User user = User.builder()
                .username(INVALID_USERNAME)
                .age(INVALID_USER_AGE)
                .firstName(INVALID_USER_FIRSTNAME)
                .lastName(INVALID_USER_LASTNAME)
                .password(INVALID_USER_PASSWORD)
                .build();
        return new Object[]{user};
    }

    @Test
    @UseDataProvider("getValidUser")
    public void shouldReturnOkWhenUserValid(User validUser) {
        assertThat(UserValidator.validateUser(validUser)).isEqualTo(OK);
    }

    @Test
    @UseDataProvider("getInvalidUser")
    public void shouldReturnErrorWhenUserInvalid(User invalidUser) {
        assertThat(UserValidator.validateUser(invalidUser)).isNotEqualTo(OK);
    }
}
