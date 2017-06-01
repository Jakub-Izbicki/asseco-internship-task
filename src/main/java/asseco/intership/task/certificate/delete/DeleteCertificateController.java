package asseco.intership.task.certificate.delete;

import asseco.intership.task.base.AbstractController;
import asseco.intership.task.certificate.CertificateController;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

@Singleton
public class DeleteCertificateController extends AbstractController {

    private static final String POPUP_MESSAGE_FORMAT = "%s %s";

    private final DeleteCertificateService deleteCertificateService;
    private final Provider<CertificateController> certificateControllerProvider;
    private String deletedCertificateId;

    @FXML
    private Text deleteCertificatePopup;

    @Inject
    public DeleteCertificateController(DeleteCertificateService deleteCertificateService,
                                       Provider<CertificateController> certificateControllerProvider) {
        this.deleteCertificateService = deleteCertificateService;
        this.certificateControllerProvider = certificateControllerProvider;
    }

    public void initialize(URL location, ResourceBundle resources) {
        deletedCertificateId = getDeletedCertificateId();
        deleteCertificatePopup.setText(String.format(
                POPUP_MESSAGE_FORMAT, getMessage("deleteCertificatePopup"), deletedCertificateId));
    }

    @FXML
    void onDelete(ActionEvent actionEvent) {
        deleteCertificateService.deleteCertificate(deletedCertificateId);
    }

    @FXML
    void onCancel(ActionEvent actionEvent) {
        close();
    }

    void onCertificateDelete() {
        certificateControllerProvider.get().initialize();
        closeLater();
    }

    private String getDeletedCertificateId() {
        return certificateControllerProvider.get().getSelectedCertificate().getId();
    }
}
