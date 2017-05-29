package asseco.intership.task.util.validation;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringValidatorTest {

    private static final String EMPTY_STRING = "";
    private static final String NULL_STRING = null;
    private static final String VALID_STRING = "string";

    @Test
    public void shouldReturnTrueWhenStringsNullOrEmpty() {
        assertThat(StringValidator.isAnyNullOrEmpty(EMPTY_STRING)).isTrue();
        assertThat(StringValidator.isAnyNullOrEmpty(NULL_STRING)).isTrue();
    }

    @Test
    public void shouldReturnFalseWhenStringValid() {
        assertThat(StringValidator.isAnyNullOrEmpty(VALID_STRING)).isFalse();
    }
}
