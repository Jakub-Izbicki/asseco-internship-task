package asseco.intership.task.certificate.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PemCertificateRaw {

    private String id;
    private String owner;
    private String raw_bytes;
}
