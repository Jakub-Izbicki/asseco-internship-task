package asseco.intership.task.login;

import asseco.intership.task.base.AbstractController;
import asseco.intership.task.user.UsersController;
import asseco.intership.task.util.PropertiesGetter;
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

    private static final String WINDOW_WIDTH_KEY = "window.large.width";
    private static final String WINDOW_HEIGHT_KEY = "window.large.height";

    @FXML
    private Text signInInfo;
    @FXML
    private Button signInButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;

    private final LoginService loginService;
    private final UsersController usersController;

    @Inject
    public LoginController(LoginService loginService, UsersController usersController) {
        this.loginService = loginService;
        this.usersController = usersController;
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
        PropertiesGetter prop = new PropertiesGetter();
        usersController.showLater(
                Double.parseDouble(prop.getProperty(WINDOW_WIDTH_KEY)),
                Double.parseDouble(prop.getProperty(WINDOW_HEIGHT_KEY)));
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
