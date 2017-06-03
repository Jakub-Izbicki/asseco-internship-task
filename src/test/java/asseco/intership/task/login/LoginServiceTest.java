package asseco.intership.task.login;

import asseco.intership.task.auth.Auth;
import asseco.intership.task.login.model.Token;
import com.google.inject.Provider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import retrofit2.Call;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class LoginServiceTest {

    private static final String USERNAME = "user1";
    private static final String PASSWORD = "pass";

    @Mock
    private Provider<LoginController> loginControllerProvider;
    @Mock
    private Auth auth;
    @Mock
    private LoginClient loginClient;
    @InjectMocks
    private LoginService loginService;
    @Mock
    private Call<Token> tokenCall;
    @Mock
    private LoginController loginController;

    @Before
    public void setUp() {
        initMocks(this);
        when(loginControllerProvider.get()).thenReturn(loginController);
        when(loginClient.getToken(anyString())).thenReturn(tokenCall);
    }

    @Test
    public void shouldLoginWhenValidCredentials() {
        doNothing().when(loginController).onValidCredentials();
        doAnswer(invocationOnMock -> {
            loginController.onValidCredentials();
            return null;
        }).when(tokenCall).enqueue(any());
        loginService.login(USERNAME, PASSWORD);

        verify(loginClient).getToken(anyString());
        verify(tokenCall).enqueue(any());
        verify(loginController).onValidCredentials();
        verifyNoMoreInteractions(loginClient, loginController, tokenCall);
    }

    @Test
    public void shouldErrorWhenEmptyCredentials() {
        doNothing().when(loginController).onEmptyCredentials();
        loginService.login(USERNAME, null);

        verify(loginController).onEmptyCredentials();
        verifyNoMoreInteractions(loginClient, loginController, tokenCall);
    }

    @Test
    public void shouldErrorWhenInvalidCredentials() {
        doNothing().when(loginController).onValidCredentials();
        doAnswer(invocationOnMock -> {
            loginController.onInvalidCredentials();
            return null;
        }).when(tokenCall).enqueue(any());
        loginService.login(USERNAME, PASSWORD);

        verify(loginClient).getToken(anyString());
        verify(tokenCall).enqueue(any());
        verify(loginController).onInvalidCredentials();
        verifyNoMoreInteractions(loginClient, loginController, tokenCall);
    }
}
