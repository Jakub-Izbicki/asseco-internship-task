package asseco.intership.task.user;

import asseco.intership.task.base.AbstractController;
import asseco.intership.task.user.model.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static asseco.intership.task.user.model.User.*;

@Singleton
public class UsersController extends AbstractController {

    @FXML
    private TableView<User> usersTableView;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> firstnameColumn;
    @FXML
    private TableColumn<User, String> lastnameColumn;
    @FXML
    private TableColumn<User, Integer> ageColumn;
    @FXML
    private TableColumn<User, String> ownerColumn;

    private final UsersService usersService;

    @Inject
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpTableViewWithColumns();
        usersService.getUsers();
    }

    void showUsers(List<User> users) {
        ObservableList<User> userViewObservableList = FXCollections.observableList(users);
        usersTableView.setItems(userViewObservableList);
    }

    private void setUpTableViewWithColumns() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>(USERNAME));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>(FIRSTNAME));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>(LASTNAME));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>(AGE));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>(OWNER));
    }
}
