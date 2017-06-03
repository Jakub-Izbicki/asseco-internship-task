package asseco.intership.task.user;

import asseco.intership.task.AbstractServiceTest;
import asseco.intership.task.auth.Auth;
import asseco.intership.task.user.model.User;
import com.google.inject.Provider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import retrofit2.Call;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest extends AbstractServiceTest {

    private static final List<User> SAMPLE_USERS = new ArrayList<>();

    @Mock
    private Provider<UserController> usersControllerProvider;
    @Mock
    private Auth auth;
    @Mock
    private UserClient userClient;
    @InjectMocks
    private UserService userService;
    @Mock
    private Call<List<User>> usersCall;
    @Mock
    private UserController userController;

    @Before
    public void setUp() {
        initMocks(this);
        when(usersControllerProvider.get()).thenReturn(userController);
        when(userClient.getUsers(anyString())).thenReturn(usersCall);
        when(auth.getToken()).thenReturn(TOKEN);
        doNothing().when(userController).showUsers(SAMPLE_USERS);
        doAnswer(invocationOnMock -> {
            userController.showUsers(SAMPLE_USERS);
            return null;
        }).when(usersCall).enqueue(any());
    }

    @Test
    public void shouldGetUsers() {
        userService.getUsers();

        verify(userController).showUsers(SAMPLE_USERS);
        verify(usersCall).enqueue(any());
        verify(userClient).getUsers(anyString());
        verify(auth).getToken();
        verifyNoMoreInteractions(userController, userClient, auth);
    }
}
