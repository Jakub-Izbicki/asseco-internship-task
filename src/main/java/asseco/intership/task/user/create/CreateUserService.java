package asseco.intership.task.user.create;

import asseco.intership.task.auth.Auth;
import asseco.intership.task.base.ClientFactory;
import asseco.intership.task.error.ApiResponse;
import asseco.intership.task.user.UserClient;
import asseco.intership.task.user.base.UserOperationService;
import asseco.intership.task.user.model.User;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
class CreateUserService extends UserOperationService {

    private final Provider<CreateUserController> createUserControllerProvider;
    private final UserClient userClient;
    private final Auth auth;

    @Inject
    CreateUserService(Provider<CreateUserController> createUserControllerProvider, Auth auth) {
        this.createUserControllerProvider = createUserControllerProvider;
        this.userClient = ClientFactory.of(UserClient.class);
        this.auth = auth;
    }

    void createUser(User newUser) {
        if (!isUserValid(
                newUser,
                createUserControllerProvider,
                createUserControllerProvider.get().addUserErrorInfo)) {
            return;
        }
        userClient.createUser(auth.getToken(), newUser).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                createUserControllerProvider.get().onSuccessfulUserCreate();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                System.out.println("CREATE USER FAILED: " + throwable.getMessage());//TODO: error handling
            }
        });
    }
}
