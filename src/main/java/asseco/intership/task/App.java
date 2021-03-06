package asseco.intership.task;

import asseco.intership.task.base.client.ClientModule;
import asseco.intership.task.login.LoginController;
import com.google.inject.Guice;
import com.google.inject.Inject;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Inject
    private LoginController loginController;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Guice.createInjector(new ClientModule()).injectMembers(this);
        loginController.createStage().show();
    }
}
