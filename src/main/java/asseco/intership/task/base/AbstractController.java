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

import static javafx.stage.Modality.WINDOW_MODAL;

public abstract class AbstractController implements Initializable {

    private static final String STAGE_TITLE = "Asseco admin";
    private static final String DEFAULT_BUNDLE = "bundles.messages";
    private static final String DEFAULT_CSS_FILE_PATH = "/General.css";
    private static final String WINDOW_WIDTH_SMALL_KEY = "window.small.width";
    private static final String WINDOW_HEIGHT_SMALL_KEY = "window.small.height";
    private static final String WINDOW_WIDTH_VERY_SMALL_KEY = "window.very.small.width";
    private static final String WINDOW_HEIGHT_VERY_SMALL_KEY = "window.very.small.height";
    private static final String COULD_NOT_LOAD_FXML = "Could not load FXML file from location: ";

    private Parent fxmlRoot;
    private Stage stage;

    public Stage createStage() {
        PropertiesGetter prop = new PropertiesGetter();
        return createStage(
                Double.parseDouble(prop.getProperty(WINDOW_WIDTH_SMALL_KEY)),
                Double.parseDouble(prop.getProperty(WINDOW_HEIGHT_SMALL_KEY)));
    }

    public void initialize() {
        initialize(null, null);
    }

    public Stage getStage() {
        return stage;
    }

    public Stage createStageAsSmallPopup(AbstractController parentController) {
        PropertiesGetter prop = new PropertiesGetter();
        return createStageAsPopup(
                parentController,
                Double.parseDouble(prop.getProperty(WINDOW_WIDTH_VERY_SMALL_KEY)),
                Double.parseDouble(prop.getProperty(WINDOW_HEIGHT_VERY_SMALL_KEY)));
    }

    public Stage createStageAsPopup(AbstractController parentController) {
        PropertiesGetter prop = new PropertiesGetter();
        return createStageAsPopup(
                parentController,
                Double.parseDouble(prop.getProperty(WINDOW_WIDTH_SMALL_KEY)),
                Double.parseDouble(prop.getProperty(WINDOW_HEIGHT_SMALL_KEY)));
    }

    private Stage createStageAsPopup(AbstractController parentController, double windowWidth, double windowHeight) {
        Stage stage = createStage(windowWidth, windowHeight);
        stage.initModality(WINDOW_MODAL);
        stage.initOwner(parentController.stage.getScene().getWindow());
        return stage;
    }

    public Parent getRootParent() {
        return loadFxml();
    }

    private Stage createStage(double windowWidth, double windowHeight) {
        final Parent root = loadFxml();
        final Scene scene;
        if (isRootAlreadyRegisteredInStage(root)) {
            scene = root.getScene();
        } else {
            scene = new Scene(root, windowWidth, windowHeight);
        }
        scene.getStylesheets().add(App.class.getResource(DEFAULT_CSS_FILE_PATH).toExternalForm());

        final Stage stage = new Stage();
        stage.setTitle(STAGE_TITLE);
        stage.setScene(scene);
        stage.sizeToScene();
        this.stage = stage;
        return stage;
    }

    public void showLater(double windowWidth, double windowHeight) {
        Platform.runLater(() -> {
            createStage(windowWidth, windowHeight).show();
        });
    }

    protected void closeLater() {
        Platform.runLater(() -> {
            stage.close();
        });
    }

    protected void close() {
        stage.close();
    }

    protected String getMessage(String key) {
        return getFxmlResourceBundle().getString(key);
    }

    private boolean isRootAlreadyRegisteredInStage(Parent root) {
        return root.getScene() != null;
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
