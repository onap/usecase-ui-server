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

import okhttp3.RequestBody;
import okhttp3.MediaType;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RestfulServices {

    public static <T> T create(Class<T> clazz) {
        String msbUrl = getMsbAddress();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + msbUrl)
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
        int len = request.getContentLength();
        ServletInputStream inStream = null;
        try {
            inStream = request.getInputStream();
            byte[] buffer = new byte[len];
            inStream.read(buffer, 0, len);
            return RequestBody.create(MediaType.parse("application/json"), buffer);
        }finally {
            if (inStream != null) {
                inStream.close();
            }
        }
    }
}
