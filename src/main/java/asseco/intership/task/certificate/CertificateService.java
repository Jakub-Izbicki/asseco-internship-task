package asseco.intership.task.certificate;

import asseco.intership.task.auth.Auth;
import asseco.intership.task.base.ClientFactory;
import asseco.intership.task.certificate.model.Certificate;
import asseco.intership.task.certificate.model.PemCertificateRaw;
import asseco.intership.task.util.CertFactory;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

@Singleton
class CertificateService {

    private final CertificateClient certificateClient;
    private final Provider<CertificateController> certificateControllerProvider;
    private final Auth auth;
    private List<Certificate> certificates;

    @Inject
    public CertificateService(Provider<CertificateController> certificateControllerProvider,
                              Auth auth) {
        this.certificateControllerProvider = certificateControllerProvider;
        this.auth = auth;
        this.certificateClient = ClientFactory.of(CertificateClient.class);
    }

    void getCertificates() {
        certificateClient.getCertificates(auth.getToken()).enqueue(new Callback<List<PemCertificateRaw>>() {
            @Override
            public void onResponse(Call<List<PemCertificateRaw>> call, Response<List<PemCertificateRaw>> response) {
                certificates = CertFactory.of(response.body());
                certificateControllerProvider.get().showCertificates(certificates);
            }

            @Override
            public void onFailure(Call<List<PemCertificateRaw>> call, Throwable throwable) {
                System.out.println("GET CERTS FAILED"); //TODO: implement error handling
            }
        });
    }
}
