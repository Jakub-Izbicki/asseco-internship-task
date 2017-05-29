package asseco.intership.task.user.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    public static final String USERNAME = "username";
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    public static final String AGE = "age";
    public static final String OWNER = "owner";

    private String username;
    private String firstName;
    private String lastName;
    private String age;
    private String owner;
    private String password;
}
