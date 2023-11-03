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
package org.onap.usecaseui.server.controller.lcm;

import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.service.lcm.SotnServiceTemplateService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;

import static org.mockito.Mockito.*;

public class SotnServiceLcmControllerTest {

    private SotnServiceTemplateService service;
    private SotnServiceLcmController controller = new SotnServiceLcmController();



    @Before
    public void setUp() {
        service = mock(SotnServiceTemplateService.class);
        controller.setServiceLcmService(service);
    }

    @Test
    public void testinstantiateService_sotnunni() throws Exception {
        HashMap<String, Object> mp = new HashMap();
        HttpServletRequest request =mock(HttpServletRequest.class);
        controller.instantiateService_sotnunni(request, mp);

        verify(service, times(1)).instantiate_CCVPN_Service(mp);

    }

    @Test
    public void testgetSiteInformationTopology() throws  Exception
    {
        String servicetype = "ISAAC";
        String serviceInstanceId = "OS001";

        controller.getSiteInformationTopology(servicetype,serviceInstanceId);

        verify(service, times(1)).getSOTNSiteInformationTopology(servicetype,serviceInstanceId);
    }

    @Test
    public void testgetSotnService() throws Exception{

        String subscriptiontype = "SOTN";
        String instanceid = "123";

        controller.getSotnService(subscriptiontype, instanceid);

        verify(service, times(1)).getService(subscriptiontype, instanceid);
    }


//    @Test
//    public void testgetSotnServicestatus() throws Exception
//    {
//        String instanceid = "123";
//        controller.getSotnServicestatus(instanceid);
//
//        verify(service, times(1)).getSOTNInstantiationstatus(instanceid);
//    }


}