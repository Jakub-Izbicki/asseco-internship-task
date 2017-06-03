package asseco.intership.task.user.create;

import asseco.intership.task.AbstractServiceTest;
import asseco.intership.task.auth.Auth;
import asseco.intership.task.base.ApiResponse;
import asseco.intership.task.user.UserClient;
import asseco.intership.task.user.UserDataProvider;
import asseco.intership.task.user.model.User;
import com.google.inject.Provider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import retrofit2.Call;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(DataProviderRunner.class)
public class CreateUserServiceTest extends AbstractServiceTest {

    @Mock
    private Provider<CreateUserController> createUserControllerProvider;
    @Mock
    private Auth auth;
    @Mock
    private UserClient userClient;
    @InjectMocks
    private CreateUserService createUserService;
    @Mock
    private CreateUserController createUserController;
    @Mock
    private Call<User> userCall;
    @Mock
    private Call<ApiResponse> apiResponseCall;

    @Before
    public void setUp() throws IOException {
        initMocks(this);
        when(createUserControllerProvider.get()).thenReturn(createUserController);
        when(auth.getToken()).thenReturn(TOKEN);
        when(userClient.getUser(anyString(), any())).thenReturn(userCall);
        when(userClient.createUser(anyString(), any())).thenReturn(apiResponseCall);
        doNothing().when(createUserController).onSuccessfulUserCreate();
        doAnswer(invocationOnMock -> null).when(userCall).execute();
        doAnswer(invocationOnMock -> {
            createUserController.onSuccessfulUserCreate();
            return null;
        }).when(apiResponseCall).enqueue(any());
    }

    @Test
    @UseDataProvider(value = "getValidUser", location = UserDataProvider.class)
    public void shouldCreateUser(User validUser) throws IOException {
        createUserService.createUser(validUser);

        verify(userCall).execute();
        verify(apiResponseCall).enqueue(any());
        verify(auth, times(2)).getToken();
        verify(createUserController).onSuccessfulUserCreate();
    }
}
