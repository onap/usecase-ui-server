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

import org.onap.usecaseui.server.service.intent.IntentAaiClient;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIClient;
import org.onap.usecaseui.server.service.slicingdomain.aai.AAISliceClient;
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
public class AAIClientConfig {

    @Value("${uui-server.client.aai.baseUrl}")
    String baseUrl;
    @Value("${uui-server.client.aai.apiVersion}")
    String apiVersion;
    @Value("${uui-server.client.aai.username}")
    String username;
    @Value("${uui-server.client.aai.password}")
    String password;

    @Bean
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
                        .header("X-TransactionId", "7777")
                        .header("X-FromAppId", "uui");
                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                }
            }).build();
    }

    @Bean("retrofitAAI")
    Retrofit retrofitAAI(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
            .baseUrl(baseUrl + "/aai/" + apiVersion + "/")
            .addConverterFactory(JacksonConverterFactory.create())
            .client(okHttpClient)
            .build();
    }

    @Bean
    AAIClient aaiClient(@Qualifier("retrofitAAI") Retrofit retrofit) {
        return retrofit.create(AAIClient.class);
    }

    @Bean
    AAISliceClient aaiSliceClient(@Qualifier("retrofitAAI") Retrofit retrofit) {
        return retrofit.create(AAISliceClient.class);
    }

    @Bean
    IntentAaiClient intentAaiClient(@Qualifier("retrofitAAI") Retrofit retrofit) {
        return retrofit.create(IntentAaiClient.class);
    }
}
