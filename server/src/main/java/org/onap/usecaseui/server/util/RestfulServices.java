/**
 * Copyright 2016-2017 ZTE Corporation.
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
package org.onap.usecaseui.server.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RestfulServices {

    private static final Logger logger = LoggerFactory.getLogger(RestfulServices.class);

    public static <T> T create(String baseUrl, Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
        return retrofit.create(clazz);
    }

    public static <T> T create(Class<T> clazz) {
        //Set the interface response time

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(300, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .build();

        String msbUrl = getMsbAddress();
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://" + msbUrl + "/")
            .client(okHttpClient)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

        return retrofit.create(clazz);
    }

    public static String getMsbAddress() {
        String msbAddress = System.getenv("MSB_ADDR");
        if (msbAddress == null) {
            return "";
        }
        return msbAddress;
    }

    public static RequestBody extractBody(HttpServletRequest request) throws IOException {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
            logger.info("The request body content is: " + sb.toString());
            return RequestBody.create(MediaType.parse("application/json"), sb.toString());
        } catch (Exception e) {
            logger.info("RestfulServices occur exection,this content is: " + e.getMessage());
            return RequestBody.create(MediaType.parse("application/json"), sb.toString());
        } finally {
            if (null != br) {
                br.close();
            }
        }
    }

    public static SSLSocketFactory getSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new CustomTrustManager()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }

    public static HostnameVerifier getHostnameVerifier() {
        HostnameVerifier   hostnameVerifier= new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        return hostnameVerifier;
    }
}

