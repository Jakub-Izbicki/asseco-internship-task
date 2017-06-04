package asseco.intership.task.certificate.create;

import asseco.intership.task.AbstractServiceTest;
import asseco.intership.task.auth.Auth;
import asseco.intership.task.base.ApiResponse;
import asseco.intership.task.certificate.CertificateClient;
import asseco.intership.task.certificate.CertificateController;
import com.google.inject.Provider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import retrofit2.Call;

import java.io.File;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreateCertificateServiceTest extends AbstractServiceTest {

    private static final String RAW_DATA_FILE = "testCertificate.pem";
    private static final File CERTIFICATE_FILE = new File(ClassLoader.getSystemClassLoader()
            .getResource(RAW_DATA_FILE).getFile());

    @Mock
    private CertificateClient certificateClient;
    @Mock
    private Provider<CertificateController> certificateControllerProvider;
    @Mock
    private Auth auth;
    @InjectMocks
    private CreateCertificateService createCertificateService;
    @Mock
    private CertificateController certificateController;
    @Mock
    private Call<ApiResponse> createCertificateCall;

    @Before
    public void setUp() {
        initMocks(this);
        when(certificateControllerProvider.get()).thenReturn(certificateController);
        when(auth.getToken()).thenReturn(TOKEN);
        when(certificateClient.createCertificate(anyString(), any())).thenReturn(createCertificateCall);
        doNothing().when(certificateController).initialize();
        doAnswer(invocationOnMock -> {
            certificateController.initialize();
            return null;
        }).when(createCertificateCall).enqueue(any());
    }

    @Test
    public void shouldCreateCertificate() {
        createCertificateService.createCertificate(CERTIFICATE_FILE);

        verify(certificateController).initialize();
        verify(auth).getToken();
        verify(createCertificateCall).enqueue(any());
        verifyNoMoreInteractions(certificateController, auth, createCertificateCall);
    }
}
