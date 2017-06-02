package asseco.intership.task.certificate;

import asseco.intership.task.auth.Auth;
import asseco.intership.task.certificate.model.Certificate;
import asseco.intership.task.certificate.model.PemCertificateRaw;
import asseco.intership.task.error.RuntimeErrorController;
import asseco.intership.task.util.CertFactory;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.security.cert.CertificateException;
import java.util.List;

@Singleton
class CertificateService {

    private final CertificateClient certificateClient;
    private final Provider<CertificateController> certificateControllerProvider;
    private final Auth auth;
    private final RuntimeErrorController runtimeErrorController;
    private List<Certificate> certificates;

    @Inject
    public CertificateService(Provider<CertificateController> certificateControllerProvider,
                              Auth auth,
                              CertificateClient certificateClient,
                              RuntimeErrorController runtimeErrorController) {
        this.certificateControllerProvider = certificateControllerProvider;
        this.auth = auth;
        this.certificateClient = certificateClient;
        this.runtimeErrorController = runtimeErrorController;
    }

    void getCertificates() {
        certificateClient.getCertificates(auth.getToken()).enqueue(new Callback<List<PemCertificateRaw>>() {
            @Override
            public void onResponse(Call<List<PemCertificateRaw>> call, Response<List<PemCertificateRaw>> response) {
                try {
                    certificates = CertFactory.of(response.body());
                } catch (CertificateException | Base64DecodingException e) {
                    onFailure(null, null);
                }
                certificateControllerProvider.get().showCertificates(certificates);
            }

            @Override
            public void onFailure(Call<List<PemCertificateRaw>> call, Throwable throwable) {
                runtimeErrorController.showErrorPopup(
                        certificateControllerProvider.get(),
                        "runtimeErrorGetCertificates");
            }
        });
    }
}
