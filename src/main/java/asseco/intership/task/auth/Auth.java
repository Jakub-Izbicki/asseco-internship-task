package asseco.intership.task.auth;

import com.google.inject.Singleton;
import lombok.Getter;

@Getter
@Singleton
public class Auth {

    private static final String TOKEN_PREFIX = "Token ";

    private String token;

    public void setToken(String token) {
        this.token = String.format("%s%s", TOKEN_PREFIX, token);
    }
}
