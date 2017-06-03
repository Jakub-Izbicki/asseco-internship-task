package asseco.intership.task.certificate.download;

import asseco.intership.task.base.AbstractController;
import asseco.intership.task.certificate.CertificateController;
import asseco.intership.task.certificate.model.Certificate;
import com.google.inject.Inject;
import com.google.inject.Provider;
import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class DownloadCertificateController extends AbstractController {

    private final Provider<CertificateController> certificateControllerProvider;
    private final DownloadCertificateService downloadCertificateService;

    @Inject
    public DownloadCertificateController(Provider<CertificateController> certificateControllerProvider,
                                         DownloadCertificateService downloadCertificateService) {
        this.certificateControllerProvider = certificateControllerProvider;
        this.downloadCertificateService = downloadCertificateService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void onDownloadPem() {
        Certificate certificate = certificateControllerProvider.get().getSelectedCertificate();
        downloadCertificateService.downloadPem(certificate, selectDirectory());
        close();
    }

    @FXML
    private void onDownloadCer() {
        Certificate certificate = certificateControllerProvider.get().getSelectedCertificate();
        downloadCertificateService.downloadCer(certificate, selectDirectory());
        close();
    }

    @FXML
    private void onCancel() {
        close();
    }

    private File selectDirectory() {
        return new DirectoryChooser().showDialog(getStage());
    }
}
