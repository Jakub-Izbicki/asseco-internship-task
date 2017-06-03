package asseco.intership.task.util.validation;

import asseco.intership.task.user.UserDataProvider;
import asseco.intership.task.user.model.User;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static asseco.intership.task.util.validation.UserValidator.UserValidation.OK;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(DataProviderRunner.class)
public class UserValidatorTest {

    @Test
    @UseDataProvider(value = "getValidUser", location = UserDataProvider.class)
    public void shouldReturnOkWhenUserValid(User validUser) {
        assertThat(UserValidator.validateUser(validUser)).isEqualTo(OK);
    }

    @Test
    @UseDataProvider(value = "getInvalidUser", location = UserDataProvider.class)
    public void shouldReturnErrorWhenUserInvalid(User invalidUser) {
        assertThat(UserValidator.validateUser(invalidUser)).isNotEqualTo(OK);
    }
}
