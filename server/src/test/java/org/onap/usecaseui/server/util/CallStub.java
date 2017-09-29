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

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class CallStub<T> implements Call<T> {

    private boolean isSuccess;
    private T result;
    private String failMessage;

    private CallStub(boolean isSuccess, T result, String message) {
        this.isSuccess = isSuccess;
        this.result = result;
        this.failMessage = message;
    }

    @Override
    public Response<T> execute() throws IOException {
        if (isSuccess) {
            return Response.success(result);
        } else {
            throw new IOException(failMessage);
        }
    }

    @Override
    public void enqueue(Callback<T> callback) {

    }

    @Override
    public boolean isExecuted() {
        return false;
    }

    @Override
    public void cancel() {

    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public Call<T> clone() {
        return null;
    }

    @Override
    public Request request() {
        return null;
    }

    public static <T> CallStub<T> successfulCall(T result) {
        return new CallStub<>(true, result, "");
    }

    public static <T> CallStub<T> failedCall(String message) {
        return new CallStub<>(false, null, message);
    }
}
