package asseco.intership.task.certificate.download;

import asseco.intership.task.base.AbstractService;
import asseco.intership.task.certificate.model.Certificate;
import asseco.intership.task.error.RuntimeErrorController;
import asseco.intership.task.util.CertFactory;
import asseco.intership.task.util.FileUtil;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;

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

    void downloadPem(Certificate certificate) {
        try {
            download(certificate, certificate.getRawData().getBytes(), PEM_EXTENSION);
        } catch (IOException e) {
            runtimeErrorController.showErrorPopup(
                    downloadCertificateControllerProvider.get(), "runtimeErrorDownloadCertificate");
        }
    }

    void downloadCer(Certificate certificate) {
        try {
            byte[] certRawData = CertFactory.of(certificate.getRawData()).getEncoded();
            download(certificate, certificate.getRawData().getBytes(), CER_EXTENSION);
        } catch (CertificateException | Base64DecodingException | IOException e) {
            runtimeErrorController.showErrorPopup(
                    downloadCertificateControllerProvider.get(), "runtimeErrorDownloadCertificate");
        }
    }

    private void download(Certificate certificate, byte[] rawBytes, String extension)
            throws IOException {
        File selectedDirectory = new DirectoryChooser()
                .showDialog(downloadCertificateControllerProvider.get().getStage());
        String filename = certificate.getCommonName() + certificate.getId();
        FileUtil.saveToFile(
                selectedDirectory.getAbsolutePath(),
                filename,
                rawBytes,
                extension);
    }
}
