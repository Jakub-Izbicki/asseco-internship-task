package asseco.intership.task.error;

import asseco.intership.task.base.AbstractController;
import com.google.inject.Singleton;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

@Singleton
public class RuntimeErrorController extends AbstractController {

    @FXML
    private Text runtimeErrorInfoText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void showErrorPopup(AbstractController parent, String messageKey) {
        createStageAsSmallPopup(parent).show();
        runtimeErrorInfoText.setText(getMessage(messageKey));
    }

    @FXML
    private void onOkButtonPressed() {
        close();
    }
}
