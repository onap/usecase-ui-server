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

import org.onap.usecaseui.server.service.lcm.domain.sdc.SDCCatalogClient;
import org.onap.usecaseui.server.service.lcm.domain.vfc.VfcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import lombok.RequiredArgsConstructor;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
@RequiredArgsConstructor
public class SDCClientConfig {

    private final SDCClientProperties clientProperties;

    OkHttpClient okHttpClient() {
        return new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder builder = originalRequest.newBuilder()
                    .header("Authorization", Credentials.basic(clientProperties.getUsername(), clientProperties.getPassword()))
                    .header(HttpHeaders.ACCEPT, "application/json")
                    .header("X-ECOMP-InstanceID", "777");
                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        }).build();
    }

    Retrofit retrofit() {
        return new Retrofit.Builder()
            .baseUrl(clientProperties.getBaseUrl())
            .addConverterFactory(JacksonConverterFactory.create())
            .client(okHttpClient())
            .build();
    }

    @Bean
    SDCCatalogClient sdcCatalogClient() {
        return retrofit().create(SDCCatalogClient.class);
    }

    @Bean
    VfcClient vfcClient() {
        return retrofit().create(VfcClient.class);
    }
}
