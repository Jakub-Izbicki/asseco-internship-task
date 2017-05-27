package asseco.intership.task.base;

import asseco.intership.task.util.PropertiesGetter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ClientFactory {

    private static final Retrofit clientBuilder =
            new Retrofit.Builder().baseUrl(new PropertiesGetter().getProperty("api.address"))
                    .addConverterFactory(GsonConverterFactory.create()).build();

    private ClientFactory() {
    }

    public static <T> T of(Class<T> tClass) {
        return clientBuilder.create(tClass);
    }
}
