package asseco.intership.task.login;

import asseco.intership.task.base.AbstractController;
import com.google.inject.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

@Singleton
public class LoginController extends AbstractController {

    @FXML
    Text signInInfo;
    @FXML
    private Button signInButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;

    private LoginService loginService;

    @Inject
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    protected void onSignInButtonPress(ActionEvent actionEvent) {
        disableSignInButton();
        loginService.login(usernameTextField.getText(), passwordTextField.getText());
    }

    public void enableSignInButton() {
        signInButton.setDisable(false);
    }

    private void disableSignInButton() {
        signInButton.setDisable(true);
    }
}
