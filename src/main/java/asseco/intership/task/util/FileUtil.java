package asseco.intership.task.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public final class FileUtil {

    private static final String PATH_FORMAT = "%s/%s%s";

    private FileUtil() {
    }

    public static void saveToFile(String absoluteDirectoryPath,
                                  String filename,
                                  byte[] rawBytes,
                                  String extension)
            throws IOException {
        String finalPath = String.format(PATH_FORMAT, absoluteDirectoryPath, filename, extension);
        Files.write(Paths.get(finalPath), rawBytes, StandardOpenOption.CREATE);
    }
}
