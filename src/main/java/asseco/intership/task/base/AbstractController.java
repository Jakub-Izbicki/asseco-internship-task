package asseco.intership.task.base;

import asseco.intership.task.App;
import asseco.intership.task.util.FxmlGetter;
import asseco.intership.task.util.PropertiesGetter;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class AbstractController implements Initializable {

    private static final String STAGE_TITLE = "Asseco internship client application";
    private static final String DEFAULT_BUNDLE = "bundles.messages";
    private static final String DEFAULT_CSS_FILE_PATH = "/General.css";
    private static final String WINDOW_WIDTH_KEY = "window.small.width";
    private static final String WINDOW_HEIGHT_KEY = "window.small.height";
    private static final String COULD_NOT_LOAD_FXML = "Could not load FXML file from location: ";

    private Parent fxmlRoot;
    private Stage stage;

    public Stage createStage() {
        final Parent root = loadFxml();
        PropertiesGetter prop = new PropertiesGetter();
        final Scene scene = new Scene(
                root,
                Double.parseDouble(prop.getProperty(WINDOW_WIDTH_KEY)),
                Double.parseDouble(prop.getProperty(WINDOW_HEIGHT_KEY)));
        scene.getStylesheets().add(App.class.getResource(DEFAULT_CSS_FILE_PATH).toExternalForm());

        final Stage stage = new Stage();
        stage.setTitle(STAGE_TITLE);
        stage.setScene(scene);
        stage.sizeToScene();
        this.stage = stage;
        return stage;
    }

    public void showLater() {
        Platform.runLater(() -> {
            createStage().show();
        });
    }

    public void closeLater() {
        Platform.runLater(() -> {
            stage.close();
        });
    }

    public String getMessage(String key) {
        return getFxmlResourceBundle().getString(key);
    }

    private URL getFxmlResourceUrl() {
        return getClass().getResource(FxmlGetter.getFxmlPath(this.getClass()));
    }

    private FXMLLoader createFxmlLoader() {
        final URL fxmlUrl = getFxmlResourceUrl();
        final ResourceBundle rb = getFxmlResourceBundle();
        final FXMLLoader loader = new FXMLLoader(fxmlUrl, rb);
        loader.setController(this);
        return loader;
    }

    private synchronized Parent loadFxml() {
        if (fxmlRoot == null) {
            final FXMLLoader loader = createFxmlLoader();
            try {
                fxmlRoot = loader.load();
            } catch (IOException e) {
                throw new IllegalStateException(COULD_NOT_LOAD_FXML + loader.getLocation(), e);
            }
        }
        return fxmlRoot;
    }

    private ResourceBundle getFxmlResourceBundle() {
        return ResourceBundle.getBundle(DEFAULT_BUNDLE);
    }
}
