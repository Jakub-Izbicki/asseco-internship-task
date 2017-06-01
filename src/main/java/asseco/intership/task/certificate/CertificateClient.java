package asseco.intership.task.certificate;

import asseco.intership.task.base.Response;
import asseco.intership.task.certificate.model.PemCertificateRaw;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

import java.util.List;

public interface CertificateClient {

    @GET("/cert")
    Call<List<PemCertificateRaw>> getCertificates(@Header("Authorization") String token);

    @DELETE("/cert/{certId}")
    Call<Response> deleteCertificate(@Header("Authorization") String token, @Path("certId") String certId);
}
