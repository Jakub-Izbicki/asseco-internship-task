package asseco.intership.task.user.edit;

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
public class EditUserController extends UserOperationController {

    @FXML
    private TextField editFirstnameTextField;
    @FXML
    private TextField editLastnameTextField;
    @FXML
    private TextField editAgeTextField;
    @FXML
    private Text editUserName;
    @FXML
    Text editUserErrorInfo;

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

    @FXML
    private void onSaveEditButtonPress() {
        editedUser.setFirstName(editFirstnameTextField.getText());
        editedUser.setLastName(editLastnameTextField.getText());
        editedUser.setAge(editAgeTextField.getText());
        editUserService.updateUser(editedUser);
    }

    @FXML
    private void onCancelEditPButtonPress() {
        close();
    }

    private void setEditFields() {
        editFirstnameTextField.setText(editedUser.getFirstName());
        editLastnameTextField.setText(editedUser.getLastName());
        editAgeTextField.setText(editedUser.getAge());
        editUserName.setText(editedUser.getUsername());
    }
}
