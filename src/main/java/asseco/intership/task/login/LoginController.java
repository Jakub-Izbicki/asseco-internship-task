package asseco.intership.task.login;

import asseco.intership.task.base.AbstractController;
import asseco.intership.task.mainpage.MainPageController;
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
    private Text signInInfo;
    @FXML
    private Button signInButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;

    private final LoginService loginService;
    private final MainPageController mainPageController;

    @Inject
    public LoginController(LoginService loginService, MainPageController mainPageController) {
        this.loginService = loginService;
        this.mainPageController = mainPageController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    protected void onSignInButtonPress(ActionEvent actionEvent) {
        disableSignInButton();
        loginService.login(usernameTextField.getText(), passwordTextField.getText());
    }

    void onValidCredentials() {
        closeLater();
        mainPageController.showLater();
    }

    void onEmptyCredentials() {
        signInInfo.setText(getMessage("signInInfoEmptyInput"));
        enableSignInButton();
    }

    void onInvalidCredentials() {
        signInInfo.setText(getMessage("signInInfoWrongCredentials"));
        enableSignInButton();
    }

    private void enableSignInButton() {
        signInButton.setDisable(false);
    }

    private void disableSignInButton() {
        signInButton.setDisable(true);
    }
}
