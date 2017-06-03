package asseco.intership.task.certificate.create;

import asseco.intership.task.auth.Auth;
import asseco.intership.task.base.AbstractService;
import asseco.intership.task.base.ApiResponse;
import asseco.intership.task.certificate.CertificateClient;
import asseco.intership.task.certificate.CertificateController;
import asseco.intership.task.certificate.model.PemCertificateRaw;
import asseco.intership.task.error.RuntimeErrorController;
import com.google.common.io.Files;
import com.google.inject.Inject;
import com.google.inject.Provider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;

public class CreateCertificateService extends AbstractService {

    private final Provider<CertificateController> certificateControllerProvider;
    private final CertificateClient certificateClient;
    private final Auth auth;

    @Inject
    public CreateCertificateService(CertificateClient certificateClient,
                                    Provider<CertificateController> certificateControllerProvider,
                                    Auth auth,
                                    RuntimeErrorController runtimeErrorController) {
        super(runtimeErrorController);
        this.certificateClient = certificateClient;
        this.certificateControllerProvider = certificateControllerProvider;
        this.auth = auth;
    }

    public void createCertificate(File certificateFile) {
        PemCertificateRaw pemCertificate = new PemCertificateRaw();
        try {
            pemCertificate.setRaw_bytes(Files.toString(certificateFile, defaultCharset()));
        } catch (IOException e) {
            showErrorPopup(runtimeErrorController,
                    certificateControllerProvider.get(),
                    "runtimeErrorReadCertificateFromFile",
                    e);
            return;
        }
        createCertificate(pemCertificate);
    }

    private void createCertificate(PemCertificateRaw pemCertificate) {
        certificateClient.createCertificate(auth.getToken(), pemCertificate)
                .enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (!isStatusOk(response)) {
                            onFailure(null, null);
                        }
                        certificateControllerProvider.get().initialize();
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                        showErrorPopup(runtimeErrorController,
                                certificateControllerProvider.get(),
                                "runtimeErrorCreateCertificate",
                                throwable);
                    }
                });
    }
}
