package asseco.intership.task.certificate;

import asseco.intership.task.base.ApiResponse;
import asseco.intership.task.certificate.model.PemCertificateRaw;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface CertificateClient {

    @GET("/cert")
    Call<List<PemCertificateRaw>> getCertificates(@Header("Authorization") String token);

    @POST("/cert")
    Call<ApiResponse> createCertificate(@Header("Authorization") String token, @Body PemCertificateRaw pemCertificateRaw);

    @DELETE("/cert/{certId}")
    Call<ApiResponse> deleteCertificate(@Header("Authorization") String token, @Path("certId") String certId);
}
