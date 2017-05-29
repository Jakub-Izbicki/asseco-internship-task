package asseco.intership.task.util.validation;

public final class StringValidator {

    private StringValidator() {
    }

    public static boolean isAnyNullOrEmpty(String... strings) {
        for (String string : strings) {
            if (string == null || string.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
