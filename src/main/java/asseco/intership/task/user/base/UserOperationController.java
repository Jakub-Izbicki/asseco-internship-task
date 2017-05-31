package asseco.intership.task.user.base;

import asseco.intership.task.base.AbstractController;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class UserOperationController extends AbstractController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    void onEmptyFields(Text errorText) {
        setErrorMessage(getMessage("editErrorEmptyFields"), errorText);
    }

    void onNoCapitalLetters(Text errorText) {
        setErrorMessage(getMessage("editErrorNameCapitalLetters"), errorText);
    }

    void onAgeNotNumeral(Text errorText) {
        setErrorMessage(getMessage("errorAgeNotNumeral"), errorText);
    }

    void onPasswordFormatNotOk(Text errorText) {
        setErrorMessage(getMessage("usernamePasswordFormatNotOk"), errorText);
    }

    protected void setErrorMessage(String message, Text errorText) {
        errorText.setText(message);
    }
}
