package asseco.intership.task.util;

import asseco.intership.task.base.AbstractController;

public final class FxmlGetter {

    private static final String CONTROLLER_KEYWORD = "Controller";
    private static final String FXML_FORMAT = "/%s.fxml";

    private FxmlGetter() {
    }

    public static String getFxmlPath(Class<? extends AbstractController> controller) {
        final int filenameOffset = 1;
        String name = controller.getName().replaceFirst(CONTROLLER_KEYWORD, "");
        name = name.substring(name.lastIndexOf(".") + filenameOffset);
        return String.format(FXML_FORMAT, name);
    }
}
