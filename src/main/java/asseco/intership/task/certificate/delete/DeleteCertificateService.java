package asseco.intership.task.certificate.delete;

import asseco.intership.task.auth.Auth;
import asseco.intership.task.base.AbstractService;
import asseco.intership.task.base.Response;
import asseco.intership.task.certificate.CertificateClient;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import retrofit2.Call;
import retrofit2.Callback;

@Singleton
class DeleteCertificateService extends AbstractService {

    private final CertificateClient certificateClient;
    private final Provider<DeleteCertificateController> deleteCertificateControllerProvider;
    private final Auth auth;

    @Inject
    public DeleteCertificateService(CertificateClient certificateClient,
                                    Provider<DeleteCertificateController> deleteCertificateControllerProvider,
                                    Auth auth) {
        this.certificateClient = certificateClient;
        this.deleteCertificateControllerProvider = deleteCertificateControllerProvider;
        this.auth = auth;
    }

    void deleteCertificate(String id) {
        certificateClient.deleteCertificate(auth.getToken(), id).enqueue(
                new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        // because server returns 200 OK when delete failed
                        if (response.body().getStatus() != STATUS_OK){
                            System.out.println("DELETE CERT FAILED"); // TODO: implement error handling
                        }
                        deleteCertificateControllerProvider.get().onCertificateDelete();
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable throwable) {
                        System.out.println("DELETE CERT FAILED"); // TODO: implement error handling
                    }
                }
        );
    }
}
