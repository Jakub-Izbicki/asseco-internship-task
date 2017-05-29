package asseco.intership.task.user;

import asseco.intership.task.error.ApiResponse;
import asseco.intership.task.user.model.User;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface UserClient {

    @GET("/user")
    Call<List<User>> getUsers(@Header("Authorization") String token);

    @POST("/user")
    Call<ApiResponse> createUser(@Header("Authorization") String token, @Body User newUser);

    @PUT("/user/{username}")
    Call<Void> updateUser(@Header("Authorization") String token, @Path("username") String username, @Body User updatedUser);
}
