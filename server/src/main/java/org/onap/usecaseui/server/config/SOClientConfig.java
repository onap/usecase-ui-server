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

import org.onap.usecaseui.server.service.intent.IntentSoService;
import org.onap.usecaseui.server.service.lcm.domain.so.SOService;
import org.onap.usecaseui.server.service.slicingdomain.kpi.KpiSliceService;
import org.onap.usecaseui.server.service.slicingdomain.so.SOSliceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

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

    OkHttpClient okHttpClient() {
        return new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
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

    Retrofit retrofit() {
        return new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(okHttpClient())
            .build();
    }

    @Bean
    SOService soService() {
        return retrofit().create(SOService.class);
    }

    @Bean
    SOSliceService soSliceService() {
        return retrofit().create(SOSliceService.class);
    }

    @Bean
    IntentSoService intentSoService() {
        return retrofit().create(IntentSoService.class);
    }

    @Bean
    // not at all clear whether this service should interface with SO
    KpiSliceService kpiSliceService() {
        return retrofit().create(KpiSliceService.class);
    }
}
