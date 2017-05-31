package asseco.intership.task.certificate;

import asseco.intership.task.base.AbstractController;
import asseco.intership.task.certificate.model.Certificate;
import asseco.intership.task.user.create.CreateUserController;
import asseco.intership.task.user.edit.EditUserController;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static asseco.intership.task.certificate.model.Certificate.*;

@Singleton
public class CertificateController extends AbstractController {

    @FXML
    private TableView<Certificate> certTableView;
    @FXML
    private TableColumn<Certificate, String> certIdColumn;
    @FXML
    private TableColumn<Certificate, String> certCommonNameColumn;
    @FXML
    private TableColumn<Certificate, String> certValidFromColumn;
    @FXML
    private TableColumn<Certificate, String> certValidToColumn;
    @FXML
    private TableColumn<Certificate, String> certOwnerColumn;
    @FXML
    private TableColumn<Certificate, String> certSerialNumberColumn;
    @FXML
    private Button addCertificateButton;
    @FXML
    private Button downloadCertificateButton;
    @FXML
    private Button removeCertificateButton;

    private final CertificateService certificateService;
//    private final EditUserController editUserController;
//    private final CreateUserController createUserController;

    @Inject
    public CertificateController(CertificateService certificateService,
                                 EditUserController editUserController,
                                 CreateUserController createUserController) {
        this.certificateService = certificateService;
//        this.editUserController = editUserController;
//        this.createUserController = createUserController;
    }

    public void initialize() {
        initialize(null, null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpRemoveCertificateButton();
        setUpTableViewWithColumns();
        certificateService.getCertificates();
    }

//    public User getSelectedUser() {
//        return usersTableView.getSelectionModel().getSelectedItem();
//    }

    @FXML
    void onAddCertificateButtonPressed(ActionEvent actionEvent) {
//        createUserController.createStageAsPopup(this).show();
//        createUserController.initialize();
    }

    @FXML
    void onDownloadCertificateButtonPressed(ActionEvent actionEvent) {
//        editUserController.createStageAsPopup(this).show();
//        editUserController.initialize();
    }

    @FXML
    void onRemoveCertificateButtonPressed(ActionEvent actionEvent) {
//        editUserController.createStageAsPopup(this).show();
//        editUserController.initialize();
    }

    void showCertificates(List<Certificate> certificates) {
        certTableView.setItems(FXCollections.observableList(certificates));
    }

    private void setUpRemoveCertificateButton() {
        removeCertificateButton.setDisable(true);
        certTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                removeCertificateButton.setDisable(false);
            } else {
                removeCertificateButton.setDisable(true);
            }
        });
    }

    private void setUpTableViewWithColumns() {
        certIdColumn.setCellValueFactory(new PropertyValueFactory<>(ID));
        certCommonNameColumn.setCellValueFactory(new PropertyValueFactory<>(COMMON_NAME));
        certValidFromColumn.setCellValueFactory(new PropertyValueFactory<>(VALID_FROM));
        certValidToColumn.setCellValueFactory(new PropertyValueFactory<>(VALID_TO));
        certOwnerColumn.setCellValueFactory(new PropertyValueFactory<>(OWNER));
        certSerialNumberColumn.setCellValueFactory(new PropertyValueFactory<>(SERIAL_NUMBER));
    }
}
