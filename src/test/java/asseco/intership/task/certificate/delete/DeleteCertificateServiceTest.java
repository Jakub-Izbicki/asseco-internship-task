package asseco.intership.task.certificate.delete;

import asseco.intership.task.AbstractServiceTest;
import asseco.intership.task.auth.Auth;
import asseco.intership.task.base.ApiResponse;
import asseco.intership.task.certificate.CertificateClient;
import com.google.inject.Provider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import retrofit2.Call;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

public class DeleteCertificateServiceTest extends AbstractServiceTest {

    @Mock
    private CertificateClient certificateClient;
    @Mock
    private Provider<DeleteCertificateController> deleteCertificateControllerProvider;
    @Mock
    private Auth auth;
    @InjectMocks
    private DeleteCertificateService deleteCertificateService;
    @Mock
    private DeleteCertificateController deleteCertificateController;
    @Mock
    private Call<ApiResponse> deleteCertificateCall;

    @Before
    public void setUp() {
        initMocks(this);
        when(deleteCertificateControllerProvider.get()).thenReturn(deleteCertificateController);
        when(auth.getToken()).thenReturn(TOKEN);
        when(certificateClient.deleteCertificate(anyString(), any())).thenReturn(deleteCertificateCall);
        doNothing().when(deleteCertificateController).onCertificateDelete();
        doAnswer(invocationOnMock -> {
            deleteCertificateController.onCertificateDelete();
            return null;
        }).when(deleteCertificateCall).enqueue(any());
    }

    @Test
    public void shouldCreateCertificate() {
        deleteCertificateService.deleteCertificate("1");

        verify(deleteCertificateController).onCertificateDelete();
        verify(auth).getToken();
        verify(deleteCertificateCall).enqueue(any());
        verifyNoMoreInteractions(deleteCertificateController, auth, deleteCertificateCall);
    }
}
