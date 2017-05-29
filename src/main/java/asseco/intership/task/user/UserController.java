package asseco.intership.task.user;

import asseco.intership.task.base.AbstractController;
import asseco.intership.task.user.create.CreateUserController;
import asseco.intership.task.user.edit.EditUserController;
import asseco.intership.task.user.model.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static asseco.intership.task.user.model.User.*;

@Singleton
public class UserController extends AbstractController {

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
    @FXML
    private Button editUserButton;
    @FXML
    private TextField searchByUsernameTextField;

    private final UserService userService;
    private final EditUserController editUserController;
    private final CreateUserController createUserController;

    @Inject
    public UserController(UserService userService, EditUserController editUserController, CreateUserController createUserController) {
        this.userService = userService;
        this.editUserController = editUserController;
        this.createUserController = createUserController;
    }

    public void initialize() {
        initialize(null, null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpEditButton();
        setUpTableViewWithColumns();
        userService.getUsers();
    }

    public User getSelectedUser() {
        return usersTableView.getSelectionModel().getSelectedItem();
    }

    @FXML
    void onAddUserButtonPressed(ActionEvent actionEvent) {
        createUserController.createStageAsPopup(this).show();
        createUserController.initialize();
    }

    @FXML
    void onEditUserButtonPressed(ActionEvent actionEvent) {
        editUserController.createStageAsPopup(this).show();
        editUserController.initialize();
    }

    void showUsers(List<User> users) {
        setUpSearchFilterAndShowUsers(users);
    }

    private void setUpEditButton() {
        editUserButton.setDisable(true);
        usersTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                editUserButton.setDisable(false);
            } else {
                editUserButton.setDisable(true);
            }
        });
    }

    private void setUpTableViewWithColumns() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>(USERNAME));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>(FIRSTNAME));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>(LASTNAME));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>(AGE));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>(OWNER));
    }

    private void setUpSearchFilterAndShowUsers(List<User> users) {
        FilteredList<User> filteredUsers = new FilteredList<>(FXCollections.observableList(users), p -> true);
        searchByUsernameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredUsers.setPredicate(user ->
                    isNullOrEmptyOrIsIn(newValue, user.getUsername()));
        });
        SortedList<User> sortedData = new SortedList<>(filteredUsers);
        sortedData.comparatorProperty().bind(usersTableView.comparatorProperty());
        usersTableView.setItems(sortedData);
    }

    private boolean isNullOrEmptyOrIsIn(String value, String compareTo) {
        return value == null || value.isEmpty() || compareTo.toLowerCase().contains(value.toLowerCase());
    }
}
