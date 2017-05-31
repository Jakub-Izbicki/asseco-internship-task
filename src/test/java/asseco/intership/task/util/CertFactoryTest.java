package asseco.intership.task.util;

import asseco.intership.task.certificate.model.Certificate;
import asseco.intership.task.certificate.model.PemCertificateRaw;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.cert.CertificateException;

import static asseco.intership.task.util.CertFactoryDataProvider.ID;
import static asseco.intership.task.util.CertFactoryDataProvider.OWNER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(DataProviderRunner.class)
public class CertFactoryTest {

    private static final String RANDOM_SYMBOL = "M";
    private static final String RANDOM_SYMBOL_2 = "z";

    @Test
    @UseDataProvider(value = "getValidPemRawAndCertificate",
            location = CertFactoryDataProvider.class)
    public void shouldProduceCertificate(String rawPemCertificate, Certificate validCertificate) throws CertificateException {
        PemCertificateRaw pemCertificateRaw =
                new PemCertificateRaw(ID, OWNER, rawPemCertificate);
        Certificate certificate = CertFactory.of(pemCertificateRaw);

        assertThat(certificate).isEqualToComparingFieldByField(validCertificate);
    }

    @Test
    @UseDataProvider(value = "getValidPemRawAndCertificate",
            location = CertFactoryDataProvider.class)
    public void shouldProduceErrorWhenInvalidRawPem(String rawPemCertificate, Certificate validCertificate) throws CertificateException {
        rawPemCertificate = rawPemCertificate.replaceAll(RANDOM_SYMBOL, RANDOM_SYMBOL_2);
        PemCertificateRaw pemCertificateRaw =
                new PemCertificateRaw(ID, OWNER, rawPemCertificate);
        assertThatThrownBy(() -> CertFactory.of(pemCertificateRaw))
                .isInstanceOf(CertificateException.class);
    }
}
