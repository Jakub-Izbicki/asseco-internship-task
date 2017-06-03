package asseco.intership.task.error;

import asseco.intership.task.base.AbstractController;
import com.google.inject.Singleton;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

@Singleton
public class RuntimeErrorController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(RuntimeErrorController.class.getName());

    @FXML
    private Text runtimeErrorInfoText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void showErrorPopup(AbstractController parent, String messageKey, Throwable e) {
        LOGGER.error(ExceptionUtils.getStackTrace(e));
        createStageAsSmallPopup(parent).show();
        runtimeErrorInfoText.setText(getMessage(messageKey));
    }

    @FXML
    private void onOkButtonPressed() {
        close();
    }
}
