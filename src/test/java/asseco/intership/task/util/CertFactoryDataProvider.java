package asseco.intership.task.util;

import asseco.intership.task.certificate.model.Certificate;
import com.tngtech.java.junit.dataprovider.DataProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class CertFactoryDataProvider {

    static final String ID = "1";
    static final String OWNER = "John";
    private static final String COMMON_NAME = "ubuntu";
    private static final String VALID_FROM = "24-10-2016";
    private static final String VALID_TO = "24-10-2017";
    private static final String RAW_DATA_FILE = "testCertificate.pem";

    @DataProvider
    public static Object[][] getValidPemRawAndCertificate() throws IOException {
        Certificate certificate = Certificate.builder()
                .id(ID)
                .commonName(COMMON_NAME)
                .validFrom(VALID_FROM)
                .validTo(VALID_TO)
                .owner(OWNER)
                .build();

        return new Object[][]{{getRawData(), certificate}};
    }

    private static String getRawData() throws IOException {
        File file = new File(ClassLoader.getSystemClassLoader()
                .getResource(RAW_DATA_FILE).getFile());
        return new String(Files.readAllBytes(file.toPath()));
    }
}
