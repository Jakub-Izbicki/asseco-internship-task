package asseco.intership.task.login;

import asseco.intership.task.base.AbstractController;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends AbstractController {

    @FXML
    private Text signInInfo;

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
        signInInfo.setText(getFxmlResourceBundle().getString("signInInfoWrongCredentials"));
    }
}
