package asseco.intership.task.user.base;

import asseco.intership.task.base.AbstractController;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class UserOperationController extends AbstractController {

    private static final String NUMERAL = "\\d*";
    private static final String NON_NUMERAL = "[^\\d]";

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

    private void setErrorMessage(String message, Text errorText) {
        errorText.setText(message);
    }
}
