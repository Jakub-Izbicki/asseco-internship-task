package asseco.intership.task.certificate;

import asseco.intership.task.base.Response;
import asseco.intership.task.certificate.model.PemCertificateRaw;
import asseco.intership.task.error.ApiResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface CertificateClient {

    @GET("/cert")
    Call<List<PemCertificateRaw>> getCertificates(@Header("Authorization") String token);

    @POST("/cert")
    Call<ApiResponse> createCertificate(@Header("Authorization") String token, @Body PemCertificateRaw pemCertificateRaw);

    @DELETE("/cert/{certId}")
    Call<Response> deleteCertificate(@Header("Authorization") String token, @Path("certId") String certId);
}
