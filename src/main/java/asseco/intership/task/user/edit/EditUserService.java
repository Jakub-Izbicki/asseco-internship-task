package asseco.intership.task.user.edit;

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

@Singleton
class EditUserService extends UserOperationService {

    private final Provider<EditUserController> editUserControllerProvider;
    private final UserClient userClient;
    private final Auth auth;

    @Inject
    EditUserService(Provider<EditUserController> editUserControllerProvider,
                    Auth auth,
                    RuntimeErrorController runtimeErrorController,
                    UserClient userClient) {
        super(runtimeErrorController);
        this.editUserControllerProvider = editUserControllerProvider;
        this.userClient = userClient;
        this.auth = auth;
    }

    void updateUser(User updatedUser) {
        if (!isUserValid(
                updatedUser,
                editUserControllerProvider,
                editUserControllerProvider.get().editUserErrorInfo)) {
            return;
        }
        userClient.updateUser(auth.getToken(), updatedUser.getUsername(), updatedUser).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (!isStatusOk(response)) {
                    onFailure(null, null);
                }
                editUserControllerProvider.get().onSuccessfulUserUpdate();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                showErrorPopup(runtimeErrorController,
                        editUserControllerProvider.get(),
                        "runtimeErrorEditUser",
                        throwable);
            }
        });
    }
}
