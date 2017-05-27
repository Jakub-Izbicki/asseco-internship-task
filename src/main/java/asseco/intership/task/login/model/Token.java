package asseco.intership.task.login.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {

    private String token;

    public boolean isValid(){
        return token != null;
    }
}
