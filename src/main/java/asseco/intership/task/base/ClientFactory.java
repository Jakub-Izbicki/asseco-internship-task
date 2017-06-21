package asseco.intership.task.base;

import asseco.intership.task.util.PropertiesGetter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

final class ClientFactory {

    private static final Retrofit clientBuilder =
            new Retrofit.Builder().baseUrl(new PropertiesGetter().getProperty("api.address"))
                    .addConverterFactory(GsonConverterFactory.create()).build();

    private ClientFactory() {
    }

    static <T> T of(Class<T> tClass) {
        return clientBuilder.create(tClass);
    }
}
