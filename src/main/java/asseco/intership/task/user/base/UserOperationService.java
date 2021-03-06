package asseco.intership.task.user.base;

import asseco.intership.task.base.AbstractService;
import asseco.intership.task.error.RuntimeErrorController;
import asseco.intership.task.user.model.User;
import com.google.inject.Provider;
import javafx.scene.text.Text;

import static asseco.intership.task.util.validation.UserValidator.validateUser;

public abstract class UserOperationService extends AbstractService {

    protected UserOperationService(RuntimeErrorController runtimeErrorController) {
        super(runtimeErrorController);
    }

    protected boolean isUserValid(User user, Provider<? extends UserOperationController> provider, Text errorText) {
        switch (validateUser(user)) {
            case EMPTY_FIELDS:
                provider.get().onEmptyFields(errorText);
                return false;
            case NO_CAPITAL_LETTERS:
                provider.get().onNoCapitalLetters(errorText);
                return false;
            case AGE_NOT_NUMERAL:
                provider.get().onAgeNotNumeral(errorText);
                return false;
            case USERNAME_BAD_FORMAT:
                provider.get().onPasswordFormatNotOk(errorText);
                return false;
        }
        return true;
    }
}
