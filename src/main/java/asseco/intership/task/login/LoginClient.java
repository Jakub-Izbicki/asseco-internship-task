package asseco.intership.task.login;

import asseco.intership.task.login.model.Token;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface LoginClient {

    @GET("/auth")
    Call<Token> getToken(@Header("Authorization") String authorization);
}
