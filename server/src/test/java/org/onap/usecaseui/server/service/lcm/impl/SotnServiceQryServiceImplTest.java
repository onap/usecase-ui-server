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

import okhttp3.ResponseBody;
import org.junit.Test;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.onap.usecaseui.server.util.CallStub.failedCall;
import static org.onap.usecaseui.server.util.CallStub.successfulCall;

public class SotnServiceQryServiceImplTest {

    AAIService aaiService = mock(AAIService.class);

    SotnServiceQryServiceImpl sotnServiceQryService = new SotnServiceQryServiceImpl(aaiService);

    @Test
    public void getServiceInstancesTest() {
        ResponseBody result=mock(ResponseBody.class);
        when(aaiService.listServiceInstances(eq("SOTN-CUST"),eq("SOTN"))).thenReturn(successfulCall(result));
        sotnServiceQryService.getServiceInstances("SOTN");
    }

    @Test
    public void getServiceInstancesWithThrowException() {
        ResponseBody result=null;
        when(aaiService.listServiceInstances(eq("SOTN-CUST"),eq("SOTN"))).thenReturn(failedCall("aai is not exist!"));
        sotnServiceQryService.getServiceInstances("SOTN");
    }

}
