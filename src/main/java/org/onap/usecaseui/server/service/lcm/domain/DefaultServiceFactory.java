package org.onap.usecaseui.server.service.lcm.domain;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class DefaultServiceFactory {

    public <T> T newService(Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }
}
