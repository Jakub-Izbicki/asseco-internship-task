package asseco.intership.task.user;

import asseco.intership.task.auth.Auth;
import asseco.intership.task.base.ClientFactory;
import asseco.intership.task.user.model.User;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

@Singleton
class UserService {

    private final Provider<UserController> usersControllerProvider;
    private final UserClient userClient;
    private final Auth auth;
    private List<User> users;

    @Inject
    public UserService(Provider<UserController> usersControllerProvider, Auth auth) {
        this.usersControllerProvider = usersControllerProvider;
        this.auth = auth;
        userClient = ClientFactory.of(UserClient.class);
    }

    void getUsers() {
        userClient.getUsers(auth.getToken())
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        users = response.body();
                        usersControllerProvider.get().showUsers(users);
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable throwable) {
                        System.out.println("GET USERS FAILED"); //TODO: implement error handling
                    }
                });
    }
}
