package asseco.intership.task.util;

import asseco.intership.task.certificate.model.Certificate;
import asseco.intership.task.certificate.model.PemCertificateRaw;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import sun.security.provider.X509Factory;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CertFactory {

    private static final String EMPTY_STRING = "";
    private static final String CERTIFICATE_TYPE = "X.509";
    private static final Pattern commonNamePattern = Pattern.compile("CN=([^,]*)");
    private static final String COMMON_NAME_NOT_FOUND = "Common name not found.";
    private static final int COMMON_NAME_GROUP = 1;
    private static final Format DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    private CertFactory() {
    }

    public static List<Certificate> of(List<PemCertificateRaw> rawPemCertificates) {
        List<Certificate> certificates = new ArrayList<>();
        for (PemCertificateRaw rawCertificate : rawPemCertificates) {
            try {
                certificates.add(of(rawCertificate));
            } catch (CertificateException e) {
                e.printStackTrace();
            }
        }
        return certificates;
    }

    static Certificate of(PemCertificateRaw pemCertificateRaw)
            throws CertificateException {
        String certificateNoHeaders = pemCertificateRaw.getRaw_bytes()
                .replaceAll(X509Factory.BEGIN_CERT, EMPTY_STRING)
                .replaceAll(X509Factory.END_CERT, EMPTY_STRING);
        X509Certificate x509Certificate = null;
        try {
            x509Certificate = (X509Certificate) CertificateFactory.getInstance(CERTIFICATE_TYPE)
                    .generateCertificate(new ByteArrayInputStream(Base64.decode(certificateNoHeaders)));
        } catch (Base64DecodingException e) {
            e.printStackTrace();
        }
        Certificate certificate = of(x509Certificate);
        certificate.setId(pemCertificateRaw.getId());
        certificate.setOwner(pemCertificateRaw.getOwner());
        return certificate;
    }

    private static Certificate of(X509Certificate x509Certificate) {
        return Certificate.builder()
                .commonName(getCommonNameOrThrow(x509Certificate))
                .validFrom(getValidFrom(x509Certificate))
                .validTo(getValidTo(x509Certificate))
                .build();
    }

    private static String getCommonNameOrThrow(X509Certificate x509Certificate) {
        Matcher matcher =
                commonNamePattern.matcher(x509Certificate.getSubjectX500Principal().getName());
        if (matcher.find()) {
            return matcher.group(COMMON_NAME_GROUP);
        }
        throw new IllegalStateException(COMMON_NAME_NOT_FOUND);
    }

    private static String getValidFrom(X509Certificate x509Certificate) {
        return DATE_FORMAT.format(x509Certificate.getNotBefore());
    }

    private static String getValidTo(X509Certificate x509Certificate) {
        return DATE_FORMAT.format(x509Certificate.getNotAfter());
    }
}
