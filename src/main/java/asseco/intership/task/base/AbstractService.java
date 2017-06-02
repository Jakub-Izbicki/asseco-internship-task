package asseco.intership.task.base;

import asseco.intership.task.error.RuntimeErrorController;
import javafx.application.Platform;
import retrofit2.Response;

public abstract class AbstractService {

    protected static final String STATUS_OK = "OK";
    protected final RuntimeErrorController runtimeErrorController;

    protected AbstractService(RuntimeErrorController runtimeErrorController) {
        this.runtimeErrorController = runtimeErrorController;
    }

    protected void showErrorPopup(RuntimeErrorController runtimeErrorController, AbstractController parent, String messageKey) {
        Platform.runLater(() -> {
            runtimeErrorController.showErrorPopup(parent, messageKey);
        });
    }

    protected boolean statusEqualsOk(Response<ApiResponse> response) {
        return STATUS_OK.equals(response.body().getStatus());
    }
}
