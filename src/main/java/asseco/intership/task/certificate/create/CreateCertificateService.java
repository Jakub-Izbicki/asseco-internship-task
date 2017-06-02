package asseco.intership.task.certificate.create;

import asseco.intership.task.auth.Auth;
import asseco.intership.task.base.ApiResponse;
import asseco.intership.task.certificate.CertificateClient;
import asseco.intership.task.certificate.CertificateController;
import asseco.intership.task.certificate.model.PemCertificateRaw;
import com.google.common.io.Files;
import com.google.inject.Inject;
import com.google.inject.Provider;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import static asseco.intership.task.base.AbstractController.DEFAULT_BUNDLE;
import static java.nio.charset.Charset.defaultCharset;

public class CreateCertificateService {

    private static final String FILE_CHOOSER_DESC =
            ResourceBundle.getBundle(DEFAULT_BUNDLE).getString("createCertificateFileChooserInfo");
    private static final String PEM_EXTENSION = "*.pem";
    private static final String CER_EXTENSION = "*.cer";
    private static final ExtensionFilter CERTIFICATE_EXTENSIONS =
            new ExtensionFilter(FILE_CHOOSER_DESC, PEM_EXTENSION, CER_EXTENSION);

    private final Provider<CertificateController> certificateControllerProvider;
    private final CertificateClient certificateClient;
    private final Auth auth;

    @Inject
    public CreateCertificateService(CertificateClient certificateClient,
                                    Provider<CertificateController> certificateControllerProvider,
                                    Auth auth) {
        this.certificateClient = certificateClient;
        this.certificateControllerProvider = certificateControllerProvider;
        this.auth = auth;
    }

    public void createCertificate() {
        File certificateFile = loadCertificateFile();
        PemCertificateRaw pemCertificate = new PemCertificateRaw();
        try {
            pemCertificate.setRaw_bytes(Files.toString(certificateFile, defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace(); //TODO: add error handling
            return;
        }
        certificateClient.createCertificate(auth.getToken(), pemCertificate)
                .enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        certificateControllerProvider.get().initialize();
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                        System.out.println("CREATE CERT FAILED"); //TODO: implement error handling
                    }
                });
    }

    private File loadCertificateFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(CERTIFICATE_EXTENSIONS);
        return fileChooser.showOpenDialog(certificateControllerProvider.get().getStage());
    }
}
