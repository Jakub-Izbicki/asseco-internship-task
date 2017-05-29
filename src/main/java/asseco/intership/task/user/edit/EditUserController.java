package asseco.intership.task.user.edit;

import asseco.intership.task.base.AbstractController;
import asseco.intership.task.user.UserController;
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
public class EditUserController extends AbstractController {

    private static final String NUMERAL = "\\d*";
    private static final String NON_NUMERAL = "[^\\d]";

    @FXML
    private TextField editFirstnameTextField;
    @FXML
    private TextField editLastnameTextField;
    @FXML
    private TextField editAgeTextField;
    @FXML
    private Text editUserName;
    @FXML
    private Text editUserErrorInfo;

    private final EditUserService editUserService;
    private final Provider<UserController> usersControllerProvider;
    private User editedUser;

    @Inject
    EditUserController(EditUserService editUserService, Provider<UserController> usersControllerProvider) {
        this.editUserService = editUserService;
        this.usersControllerProvider = usersControllerProvider;
    }

    public void initialize(URL location, ResourceBundle resources) {
        editedUser = usersControllerProvider.get().getSelectedUser();
        setEditFields();
    }

    void onSuccessfulUserUpdate() {
        usersControllerProvider.get().initialize();
        closeLater();
    }

    void onEmptyFields() {
        setErrorMessage(getMessage("editErrorEmptyFields"));
    }

    void onNoCapitalLetters() {
        setErrorMessage(getMessage("editErrorNameCapitalLetters"));
    }

    private void setErrorMessage(String message) {
        editUserErrorInfo.setText(message);
    }

    @FXML
    private void onSaveEditButtonPress() {
        editedUser.setFirstName(editFirstnameTextField.getText());
        editedUser.setLastName(editLastnameTextField.getText());
        editedUser.setAge(Integer.parseInt(editAgeTextField.getText()));
        editUserService.updateUser(editedUser);
    }

    @FXML
    private void onCancelEditPButtonPress() {
        close();
    }

    private void setEditFields() {
        editFirstnameTextField.setText(editedUser.getFirstName());
        editLastnameTextField.setText(editedUser.getLastName());
        editAgeTextField.setText(editedUser.getAge().toString());
        editUserName.setText(editedUser.getUsername());
        setAgeFieldAcceptNumeralsOnly();
    }

    private void setAgeFieldAcceptNumeralsOnly() {
        editAgeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(NUMERAL)) {
                handleAgeBadInput(newValue);
            }
        });
    }

    private void handleAgeBadInput(String input) {
        editAgeTextField.setText(input.replaceAll(NON_NUMERAL, ""));
        setErrorMessage(getMessage("errorAgeNotNumeral"));
    }
}
