package asseco.intership.task.certificate.download;

import asseco.intership.task.base.AbstractService;
import asseco.intership.task.certificate.model.Certificate;
import asseco.intership.task.error.RuntimeErrorController;
import asseco.intership.task.util.FileUtil;
import com.google.inject.Inject;
import com.google.inject.Provider;

import java.io.File;
import java.io.IOException;

class DownloadCertificateService extends AbstractService {

    private static final String PEM_EXTENSION = ".pem";
    private static final String CER_EXTENSION = ".cer";

    private final Provider<DownloadCertificateController> downloadCertificateControllerProvider;

    @Inject
    public DownloadCertificateService(
            Provider<DownloadCertificateController> downloadCertificateControllerProvider,
            RuntimeErrorController runtimeErrorController) {
        super(runtimeErrorController);
        this.downloadCertificateControllerProvider = downloadCertificateControllerProvider;
    }

    void downloadPem(Certificate certificate, File selectedDirectory) {
        download(certificate,
                certificate.getRawData().getBytes(),
                PEM_EXTENSION,
                selectedDirectory);
    }

    void downloadCer(Certificate certificate, File selectedDirectory) {
        download(certificate,
                certificate.getRawData().getBytes(),
                CER_EXTENSION,
                selectedDirectory);
    }

    private void download(Certificate certificate,
                          byte[] rawBytes,
                          String extension,
                          File selectedDirectory) {
        String filename = certificate.getCommonName() + certificate.getId();
        try {
            FileUtil.saveToFile(
                    selectedDirectory.getAbsolutePath(),
                    filename,
                    rawBytes,
                    extension);
        } catch (IOException e) {
            runtimeErrorController.showErrorPopup(
                    downloadCertificateControllerProvider.get(),
                    "runtimeErrorDownloadCertificate",
                    e);
        }
    }
}
