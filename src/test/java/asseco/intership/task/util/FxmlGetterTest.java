package asseco.intership.task.util;

import asseco.intership.task.base.AbstractController;
import asseco.intership.task.login.LoginController;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FxmlGetterTest {

    private static final Class<? extends AbstractController> EXAMPLE_CONTROLLER_CLASS = LoginController.class;
    private static final String EXPECTED_PATH = "/Login.fxml";

    @Test
    public void shouldGetCorrectFxmlFilename() {
        String filename = FxmlGetter.getFxmlPath(EXAMPLE_CONTROLLER_CLASS);
        assertThat(filename).isEqualTo(EXPECTED_PATH);
    }
}
