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
class UsersService {

    private static final String TOKEN_PREFIX = "Token ";
    private final Provider<UsersController> usersControllerProvider;
    private final UsersClient usersClient;
    private final Auth auth;
    private List<User> users;

    @Inject
    public UsersService(Provider<UsersController> usersControllerProvider, Auth auth) {
        this.usersControllerProvider = usersControllerProvider;
        this.auth = auth;
        usersClient = ClientFactory.of(UsersClient.class);
    }

    void getUsers() {
        usersClient.getUsers(String.format("%s%s", TOKEN_PREFIX, auth.getToken()))
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        users = response.body();
                        usersControllerProvider.get().showUsers(users);
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable throwable) {
                        System.out.println("FAILED"); //TODO: implement error handling
                    }
                });
    }
}
