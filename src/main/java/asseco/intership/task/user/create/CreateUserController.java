package asseco.intership.task.user.create;

import asseco.intership.task.user.UserController;
import asseco.intership.task.user.base.UserOperationController;
import asseco.intership.task.user.model.User;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

@Singleton
public class CreateUserController extends UserOperationController {

    private static final String NUMERAL = "\\d*";
    private static final String NON_NUMERAL = "[^\\d]";

    @FXML
    private TextField addUsernameTextField;
    @FXML
    private TextField addFirstnameTextField;
    @FXML
    private TextField addLastnameTextField;
    @FXML
    private TextField addAgeTextField;
    @FXML
    private TextField addPasswordTextField;
    @FXML
    private Text addUserName;
    @FXML
    Text addUserErrorInfo;

    private final CreateUserService createUserService;
    private final Provider<UserController> usersControllerProvider;

    @Inject
    CreateUserController(CreateUserService createUserService, Provider<UserController> usersControllerProvider) {
        this.createUserService = createUserService;
        this.usersControllerProvider = usersControllerProvider;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void onSaveAddButtonPress() {
        User newUser = User.builder()
                .username(addUsernameTextField.getText())
                .firstName(addFirstnameTextField.getText())
                .lastName(addLastnameTextField.getText())
                .age(addAgeTextField.getText())
                .password(addPasswordTextField.getText())
                .build();
        createUserService.createUser(newUser);
    }

    @FXML
    private void onCancelAddPButtonPress() {
        close();
    }

    void onSuccessfulUserCreate() {
        usersControllerProvider.get().initialize();
        closeLater();
    }

    void onUsernameFormatNotOk() {
        setErrorMessage(getMessage("usernamePasswordFormatNotOk"), addUserErrorInfo);
    }

    void onUserAlreadyExists() {
        setErrorMessage(getMessage("errorUserAlreadyExists"), addUserErrorInfo);
    }
}
