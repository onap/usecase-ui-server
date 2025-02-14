/**
 * Copyright (C) 2020 Huawei, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.service.lcm.impl;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIClient;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.onap.usecaseui.server.util.CallStub.failedCall;
import static org.onap.usecaseui.server.util.CallStub.successfulCall;

public class SotnServiceQryServiceImplTest {

    AAIClient aaiClient = mock(AAIClient.class);

    SotnServiceQryServiceImpl sotnServiceQryService = new SotnServiceQryServiceImpl(aaiClient);
    ResponseBody result;

    @Before
    public void before() throws Exception {
        result= new ResponseBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return MediaType.parse("application/json; charset=utf-8");
            }

            @Override
            public long contentLength() {
                return 0;
            }

            @NotNull
            @Override
            public BufferedSource source() {

                return new Buffer();
            }
        };
    }
    @Test
    public void getServiceInstancesTest() {
        when(aaiClient.listServiceInstances(eq("SOTN-CUST"),eq("SOTN"))).thenReturn(successfulCall(result));
        sotnServiceQryService.getServiceInstances("SOTN");
    }

    @Test
    public void getServiceInstancesWithThrowException() {
        when(aaiClient.listServiceInstances(eq("SOTN-CUST"),eq("SOTN"))).thenReturn(failedCall("aai is not exist!"));
        sotnServiceQryService.getServiceInstances("SOTN");
    }

}
