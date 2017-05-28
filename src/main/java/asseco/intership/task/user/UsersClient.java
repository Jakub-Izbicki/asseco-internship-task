package asseco.intership.task.user;

import asseco.intership.task.user.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

import java.util.List;

interface UsersClient {

    @GET("/user")
    Call<List<User>> getUsers(@Header("Authorization") String token);
}
