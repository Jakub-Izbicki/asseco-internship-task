package asseco.intership.task.certificate.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Certificate {

    public static final String ID = "id";
    public static final String COMMON_NAME = "commonName";
    public static final String VALID_FROM = "validFrom";
    public static final String VALID_TO = "validTo";
    public static final String OWNER = "owner";

    private String id;
    private String commonName;
    private String validFrom;
    private String validTo;
    private String owner;
}
