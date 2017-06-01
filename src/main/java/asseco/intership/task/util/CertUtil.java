package asseco.intership.task.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public final class CertUtil {

    private static final String PEM_EXTENSION = ".pem";
    private static final String PATH_FORMAT = "%s/%s%s";

    private CertUtil() {
    }

    public static void saveAsPem(String absoluteDirectoryPath, String filename, String pemRawData) {
        String finalPath = String.format(PATH_FORMAT, absoluteDirectoryPath, filename, PEM_EXTENSION);
        try {
            Files.write(Paths.get(finalPath), pemRawData.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
