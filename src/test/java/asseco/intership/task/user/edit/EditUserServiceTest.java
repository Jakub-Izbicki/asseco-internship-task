package asseco.intership.task.user.edit;

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
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(DataProviderRunner.class)
public class EditUserServiceTest extends AbstractServiceTest {

    @Mock
    private Provider<EditUserController> editUserControllerProvider;
    @Mock
    private Auth auth;
    @Mock
    private UserClient userClient;
    @InjectMocks
    private EditUserService editUserService;
    @Mock
    private EditUserController editUserController;
    @Mock
    private Call<ApiResponse> updateUserCall;

    @Before
    public void setUp() throws IOException {
        initMocks(this);
        when(editUserControllerProvider.get()).thenReturn(editUserController);
        when(auth.getToken()).thenReturn(TOKEN);
        when(userClient.updateUser(anyString(), anyString(), any())).thenReturn(updateUserCall);
        doNothing().when(editUserController).onSuccessfulUserUpdate();
        doAnswer(invocationOnMock -> {
            editUserController.onSuccessfulUserUpdate();
            return null;
        }).when(updateUserCall).enqueue(any());
    }

    @Test
    @UseDataProvider(value = "getValidUser", location = UserDataProvider.class)
    public void shouldCreateUser(User validUser) throws IOException {
        editUserService.updateUser(validUser);

        verify(updateUserCall).enqueue(any());
        verify(auth).getToken();
        verify(editUserController).onSuccessfulUserUpdate();
        verifyNoMoreInteractions(updateUserCall, auth, editUserController);
    }
}
