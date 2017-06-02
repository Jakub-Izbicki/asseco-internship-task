package asseco.intership.task.certificate;

import asseco.intership.task.base.AbstractController;
import asseco.intership.task.certificate.create.CreateCertificateService;
import asseco.intership.task.certificate.delete.DeleteCertificateController;
import asseco.intership.task.certificate.download.DownloadCertificateController;
import asseco.intership.task.certificate.model.Certificate;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.collections.FXCollections;
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
    private final CreateCertificateService createCertificateService;
    private final DeleteCertificateController deleteCertificateController;
    private final DownloadCertificateController downloadCertificateController;

    @Inject
    public CertificateController(CertificateService certificateService,
                                 CreateCertificateService createCertificateService,
                                 DeleteCertificateController deleteCertificateController,
                                 DownloadCertificateController downloadCertificateController) {
        this.certificateService = certificateService;
        this.createCertificateService = createCertificateService;
        this.deleteCertificateController = deleteCertificateController;
        this.downloadCertificateController = downloadCertificateController;
    }

    public void initialize() {
        initialize(null, null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpDisabledButtons();
        setUpTableViewWithColumns();
        certificateService.getCertificates();
    }

    public Certificate getSelectedCertificate() {
        return certTableView.getSelectionModel().getSelectedItem();
    }

    @FXML
    void onAddCertificateButtonPressed() {
        createCertificateService.createCertificate();
    }

    @FXML
    void onDownloadCertificateButtonPressed() {
        downloadCertificateController.createStageAsSmallPopup(this).show();
        downloadCertificateController.initialize();
    }

    @FXML
    void onRemoveCertificateButtonPressed() {
        deleteCertificateController.createStageAsSmallPopup(this).show();
        deleteCertificateController.initialize();
    }

    void showCertificates(List<Certificate> certificates) {
        certTableView.setItems(FXCollections.observableList(certificates));
    }

    private void setUpDisabledButtons() {
        setUpDisabledButtonListener(downloadCertificateButton);
        setUpDisabledButtonListener(removeCertificateButton);
    }

    private void setUpDisabledButtonListener(Button button) {
        button.setDisable(true);
        certTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                button.setDisable(false);
            } else {
                button.setDisable(true);
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
