package asseco.intership.task.certificate;

import asseco.intership.task.certificate.model.PemCertificateRaw;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

import java.util.List;

public interface CertificateClient {

    @GET("/cert")
    Call<List<PemCertificateRaw>> getCertificates(@Header("Authorization") String token);
}
