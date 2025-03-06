/**
 * Copyright 2025 Deutsche Telekom.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onap.usecaseui.server.config;

import java.io.IOException;

import org.onap.usecaseui.server.service.intent.IntentSoClient;
import org.onap.usecaseui.server.service.lcm.domain.so.SOClient;
import org.onap.usecaseui.server.service.slicingdomain.kpi.KpiSliceClient;
import org.onap.usecaseui.server.service.slicingdomain.so.SOSliceClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.micrometer.core.instrument.binder.okhttp3.OkHttpObservationInterceptor;
import io.micrometer.observation.ObservationRegistry;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class SOClientConfig {

    @Value("${uui-server.client.so.baseUrl}")
    String baseUrl;
    @Value("${uui-server.client.so.username}")
    String username;
    @Value("${uui-server.client.so.password}")
    String password;

    @Bean("okHttpClientSO")
    OkHttpClient okHttpClient(ObservationRegistry observationRegistry) {
        return new OkHttpClient().newBuilder()
            .addInterceptor(
                    OkHttpObservationInterceptor.builder(observationRegistry, "http.client.requests").build())
            .addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Request.Builder builder = originalRequest.newBuilder()
                        .header("Authorization", Credentials.basic(username, password))
                        .header(HttpHeaders.ACCEPT, "application/json")
                        .header("X-TransactionId", "9999")
                        .header("X-FromAppId", "onap-cli");
                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                }
            }).build();
    }

    @Bean("retrofitSO")
    Retrofit retrofitSo(@Qualifier("okHttpClientSO") OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(okHttpClient)
            .build();
    }

    @Bean
    SOClient soClient(@Qualifier("retrofitSO") Retrofit retrofit) {
        return retrofit.create(SOClient.class);
    }

    @Bean
    SOSliceClient soSliceClient(@Qualifier("retrofitSO") Retrofit retrofit) {
        return retrofit.create(SOSliceClient.class);
    }

    @Bean
    IntentSoClient intentSoClient(@Qualifier("retrofitSO") Retrofit retrofit) {
        return retrofit.create(IntentSoClient.class);
    }

    @Bean
    // not at all clear whether this service should interface with SO
    KpiSliceClient kpiSliceClient(@Qualifier("retrofitSO") Retrofit retrofit) {
        return retrofit.create(KpiSliceClient.class);
    }
}
