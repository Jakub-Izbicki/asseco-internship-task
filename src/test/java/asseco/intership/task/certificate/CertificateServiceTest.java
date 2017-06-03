package asseco.intership.task.certificate;

import asseco.intership.task.AbstractServiceTest;
import asseco.intership.task.auth.Auth;
import asseco.intership.task.certificate.model.Certificate;
import asseco.intership.task.certificate.model.PemCertificateRaw;
import asseco.intership.task.error.RuntimeErrorController;
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

public class CertificateServiceTest extends AbstractServiceTest {

    private static final List<Certificate> SAMPLE_CERTIFICATES = new ArrayList<>();


    @Mock
    private Provider<CertificateController> certificateControllerProvider;
    @Mock
    private Auth auth;
    @Mock
    private CertificateClient certificateClient;
    @Mock
    private RuntimeErrorController runtimeErrorController;
    @InjectMocks
    private CertificateService certificateService;
    @Mock
    private CertificateController certificateController;
    @Mock
    private Call<List<PemCertificateRaw>> certificatesCall;

    @Before
    public void setUp() {
        initMocks(this);
        when(certificateControllerProvider.get()).thenReturn(certificateController);
        when(auth.getToken()).thenReturn(TOKEN);
        doNothing().when(certificateController).showCertificates(SAMPLE_CERTIFICATES);
        doAnswer(invocationOnMock -> {
            certificateController.showCertificates(SAMPLE_CERTIFICATES);
            return null;
        }).when(certificatesCall).enqueue(any());
    }

    @Test
    public void shouldGetCertificates() {
        when(certificateClient.getCertificates(anyString())).thenReturn(certificatesCall);
        doAnswer(invocationOnMock -> {
            certificateController.showCertificates(SAMPLE_CERTIFICATES);
            return null;
        }).when(certificatesCall).enqueue(any());
        certificateService.getCertificates();

        verify(certificateClient).getCertificates(anyString());
        verify(auth).getToken();
        verify(certificateController).showCertificates(SAMPLE_CERTIFICATES);
        verify(certificatesCall).enqueue(any());
        verifyNoMoreInteractions(certificateClient, auth, certificateController, certificatesCall);
    }
}
