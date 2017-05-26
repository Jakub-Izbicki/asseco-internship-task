package asseco.intership.task.util;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesGetter {

    private static final String PROPERTIES_FILE = "/application.properties";
    private static final String COULD_NOT_LOAD_PROPERTIES = "Unable to load properties file.";

    private Properties properties;

    public PropertiesGetter() {
        properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
            throw new IllegalStateException(COULD_NOT_LOAD_PROPERTIES, e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
