package asseco.intership.task.panel;

import asseco.intership.task.base.AbstractController;
import asseco.intership.task.certificate.CertificateController;
import asseco.intership.task.user.UserController;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PanelController extends AbstractController {

    @FXML
    private AnchorPane panelAnchorPane;

    private final UserController userController;
    private final CertificateController certificateController;

    @Inject
    public PanelController(UserController userController,
                           CertificateController certificateController) {
        this.userController = userController;
        this.certificateController = certificateController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPanelSubstages();
        onCertificatesButtonPressed();
        onUsersButtonPressed();
    }

    @FXML
    private void onCertificatesButtonPressed() {
        panelAnchorPane.getChildren().clear();
        panelAnchorPane.getChildren().add(certificateController.getRootParent());
        certificateController.initialize();
    }

    @FXML
    private void onUsersButtonPressed() {
        panelAnchorPane.getChildren().clear();
        panelAnchorPane.getChildren().add(userController.getRootParent());
        userController.initialize();
    }

    private void initPanelSubstages() {
        userController.createStage();
        certificateController.createStage();
    }
}
