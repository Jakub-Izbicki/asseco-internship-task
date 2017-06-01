package asseco.intership.task.certificate.download;

import asseco.intership.task.certificate.CertificateController;
import asseco.intership.task.certificate.model.Certificate;
import asseco.intership.task.util.CertUtil;
import com.google.inject.Inject;
import com.google.inject.Provider;
import javafx.stage.DirectoryChooser;

import java.io.File;

class DownloadCertificateService {

    private final Provider<DownloadCertificateController> downloadCertificateControllerProvider;

    @Inject
    public DownloadCertificateService(
            Provider<DownloadCertificateController> downloadCertificateControllerProvider,
            Provider<CertificateController> certificateControllerProvider) {
        this.downloadCertificateControllerProvider = downloadCertificateControllerProvider;
    }

    void downloadPem(Certificate certificate) {
        File selectedDirectory = new DirectoryChooser()
                .showDialog(downloadCertificateControllerProvider.get().getStage());
        if (selectedDirectory == null) {
            return;
        }
        String filename = certificate.getCommonName() + certificate.getId();
        CertUtil.saveAsPem(
                selectedDirectory.getAbsolutePath(),
                filename,
                certificate.getRawData());
    }
}
