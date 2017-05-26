package asseco.intership.task.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertiesGetterTest {

    private static final String EXAMPLE_PROPERTY = "window.small.width";

    @Test
    public void shouldGetProperties() {
        PropertiesGetter prop = new PropertiesGetter();
        assertThat(prop.getProperty(EXAMPLE_PROPERTY)).isNotEmpty();
    }
}
