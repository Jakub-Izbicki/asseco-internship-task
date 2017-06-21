package asseco.intership.task.base.client;

import asseco.intership.task.certificate.CertificateClient;
import asseco.intership.task.login.LoginClient;
import asseco.intership.task.user.UserClient;
import com.google.inject.AbstractModule;

public class ClientModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(LoginClient.class).toInstance(ClientFactory.of(LoginClient.class));
        bind(UserClient.class).toInstance(ClientFactory.of(UserClient.class));
        bind(CertificateClient.class).toInstance(ClientFactory.of(CertificateClient.class));
    }
}
