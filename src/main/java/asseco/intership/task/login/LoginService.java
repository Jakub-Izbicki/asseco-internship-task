package asseco.intership.task.login;

import asseco.intership.task.auth.Auth;
import asseco.intership.task.base.ClientFactory;
import asseco.intership.task.login.model.Token;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Base64;

@Singleton
class LoginService {

    private final static String BASIC_PREFIX = "Basic ";
    private final static String AUTH_HEADER_FORMAT = "%s:%s";

    private final Provider<LoginController> loginControllerProvider;
    private final LoginClient loginClient;
    private final Auth auth;

    @Inject
    public LoginService(Provider<LoginController> loginControllerProvider, Auth auth) {
        this.loginControllerProvider = loginControllerProvider;
        this.auth = auth;
        loginClient = ClientFactory.of(LoginClient.class);
    }

    void login(String username, String password) {
        if (isAnyNullOrEmpty(username, password)) {
            loginControllerProvider.get().signInInfo
                    .setText(loginControllerProvider.get().getMessage("signInInfoEmptyInput"));
            loginControllerProvider.get().enableSignInButton();
            return;
        }
        String authHeader = BASIC_PREFIX +
                Base64.getEncoder().encodeToString(String.format(AUTH_HEADER_FORMAT, username, password).getBytes());
        getToken(authHeader);
    }

    private void getToken(String authHeader) {
        loginClient.getToken(authHeader).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Token token = response.body();
                // because server returns 200 OK when credentials are invalid...
                if (!token.isValid()) {
                    onFailure(call, new IllegalStateException());
                }
                auth.setToken(token.getToken());
                loginControllerProvider.get().enableSignInButton();
            }

            @Override
            public void onFailure(Call<Token> call, Throwable throwable) {
                loginControllerProvider.get().signInInfo
                        .setText(loginControllerProvider.get().getMessage("signInInfoWrongCredentials"));
                loginControllerProvider.get().enableSignInButton();
            }
        });
    }

    private boolean isAnyNullOrEmpty(String... strings) {
        for (String string : strings) {
            if (string == null || string.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
