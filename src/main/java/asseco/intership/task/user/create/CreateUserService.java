package asseco.intership.task.user.create;

import asseco.intership.task.auth.Auth;
import asseco.intership.task.base.ApiResponse;
import asseco.intership.task.error.RuntimeErrorController;
import asseco.intership.task.user.UserClient;
import asseco.intership.task.user.base.UserOperationService;
import asseco.intership.task.user.model.User;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

import static asseco.intership.task.util.validation.UsernameValidator.isUsernameOk;

@Singleton
class CreateUserService extends UserOperationService {

    private final Provider<CreateUserController> createUserControllerProvider;
    private final UserClient userClient;
    private final Auth auth;

    @Inject
    CreateUserService(Provider<CreateUserController> createUserControllerProvider,
                      Auth auth,
                      RuntimeErrorController runtimeErrorController,
                      UserClient userClient) {
        super(runtimeErrorController);
        this.createUserControllerProvider = createUserControllerProvider;
        this.userClient = userClient;
        this.auth = auth;
    }

    void createUser(User newUser) {
        if (!isUserValid(
                newUser,
                createUserControllerProvider,
                createUserControllerProvider.get().addUserErrorInfo)) {
            return;
        }
        if (!isUsernameOk(newUser.getUsername())) {
            createUserControllerProvider.get().onUsernameFormatNotOk();
            return;
        }
        if (userExists(newUser)) {
            createUserControllerProvider.get().onUserAlreadyExists();
            return;
        }
        userClient.createUser(auth.getToken(), newUser).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (!isStatusOk(response)) {
                    onFailure(null, null);
                }
                createUserControllerProvider.get().onSuccessfulUserCreate();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                showErrorPopup(runtimeErrorController,
                        createUserControllerProvider.get(),
                        "runtimeErrorCreateUser");
            }
        });
    }

    private boolean userExists(User user) {
        Response<User> foundUser = null;
        try {
            foundUser = userClient.getUser(auth.getToken(), user.getUsername()).execute();
        } catch (IOException e) {
            showErrorPopup(runtimeErrorController,
                    createUserControllerProvider.get(),
                    "runtimeErrorCreateUser");
        }
        //because server returns OK when user not found
        return !(foundUser == null || (foundUser.body().getUsername() == null));
    }
}
