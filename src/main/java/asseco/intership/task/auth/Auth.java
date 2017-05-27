package asseco.intership.task.auth;

import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Singleton
public class Auth {

    private String token;

    public boolean isExpired() { //TODO: implement
        return false;
    }
}
