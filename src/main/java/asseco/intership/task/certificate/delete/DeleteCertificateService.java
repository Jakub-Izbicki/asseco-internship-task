package asseco.intership.task.certificate.delete;

import asseco.intership.task.auth.Auth;
import asseco.intership.task.base.AbstractService;
import asseco.intership.task.base.ApiResponse;
import asseco.intership.task.certificate.CertificateClient;
import asseco.intership.task.error.RuntimeErrorController;
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
                                    Auth auth,
                                    RuntimeErrorController runtimeErrorController) {
        super(runtimeErrorController);
        this.certificateClient = certificateClient;
        this.deleteCertificateControllerProvider = deleteCertificateControllerProvider;
        this.auth = auth;
    }

    void deleteCertificate(String id) {
        certificateClient.deleteCertificate(auth.getToken(), id).enqueue(
                new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                        // because server returns 200 OK when delete failed
                        if (!STATUS_OK.equals(response.body().getStatus())) {
                            onFailure(null, null);
                        }
                        deleteCertificateControllerProvider.get().onCertificateDelete();
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                        showErrorPopup(runtimeErrorController,
                                deleteCertificateControllerProvider.get(),
                                "runtimeErrorDeleteCertificate");
                    }
                }
        );
    }
}
