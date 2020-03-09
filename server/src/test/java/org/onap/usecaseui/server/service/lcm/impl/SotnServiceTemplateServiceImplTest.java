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
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.E2EServiceInstanceRequest;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.ModelConfig;
import org.onap.usecaseui.server.service.lcm.SotnServiceTemplateService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;
import org.onap.usecaseui.server.service.lcm.domain.so.SOService;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.ServiceOperation;
import org.onap.usecaseui.server.service.sotn.impl.SOTNServiceImpl;

import java.util.HashMap;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.onap.usecaseui.server.util.CallStub.failedCall;
import static org.onap.usecaseui.server.util.CallStub.successfulCall;

public class SotnServiceTemplateServiceImplTest {

    AAIService aaiService;
    SOService soService;
    ServiceOperation serviceOperation;
    SotnServiceTemplateServiceImpl sotnServiceTemplateService;
    @Before
    public void before() throws Exception {
        aaiService = mock(AAIService.class);
        soService = mock(SOService.class);
        sotnServiceTemplateService = new SotnServiceTemplateServiceImpl();
    }

    @Test
    public void instantiate_CCVPN_ServiceTest() {
        when(soService.instantiateSOTNService(new E2EServiceInstanceRequest())).thenReturn(successfulCall(serviceOperation));
        sotnServiceTemplateService.instantiate_CCVPN_Service(new HashMap<String, Object>());
    }

    @Test
    public void instantiate_CCVPN_ServiceWithThrowException() {
        when(soService.instantiateSOTNService(new E2EServiceInstanceRequest())).thenReturn(failedCall("failed to create Service"));
        sotnServiceTemplateService.instantiate_CCVPN_Service(new HashMap<String, Object>());
    }

    @Test
    public void createSotnServiceTest() {
        when(soService.instantiateSOTNService(new E2EServiceInstanceRequest())).thenReturn(successfulCall(serviceOperation));
        sotnServiceTemplateService.createSotnService(new E2EServiceInstanceRequest());
    }

    @Test
    public void createSotnServiceWithThrowException() {
        when(soService.instantiateSOTNService(new E2EServiceInstanceRequest())).thenReturn(failedCall("failed to create Service"));
        sotnServiceTemplateService.createSotnService(new E2EServiceInstanceRequest());
    }

    @Test
    public void getServiceInstancesInfoTest() throws Exception {
        ResponseBody result=null;
        when(aaiService.getServiceInstancesForEdge("ISAAC","SOTN","ISAAC")).thenReturn(successfulCall(result));
        sotnServiceTemplateService.getServiceInstancesInfo("ISAAC","SOTN","ISAAC");
    }

    @Test
    public void getServiceInstancesInfoWithThrowException() throws Exception {
        when(aaiService.getServiceInstancesForEdge("ISAAC","SOTN","ISAAC")).thenReturn(failedCall("Failed to get Service Instance"));
        sotnServiceTemplateService.getServiceInstancesInfo("ISAAC","SOTN","ISAAC");
    }

    @Test
    public void getTerminationPointTest() throws Exception {
        ResponseBody result = null;
        when(aaiService.getTerminationPoint("SOTN","123")).thenReturn(successfulCall(result));
        sotnServiceTemplateService.getTerminationPoint("SOTN", "123");
    }

    @Test
    public void getTerminationPointWithThrowException() throws Exception {
        when(aaiService.getTerminationPoint("SOTN","123")).thenReturn(failedCall("Failed to get connectivity information."));
        sotnServiceTemplateService.getTerminationPoint("SOTN", "123");
    }

    @Test
    public void getSOTNPinterfaceByVpnIdTest() throws Exception {
        ResponseBody result = null;
        when(aaiService.getPinterfaceByVpnId("1")).thenReturn(successfulCall(result));
        sotnServiceTemplateService.getSOTNPinterfaceByVpnId("1");
    }

    @Test
    public void getSOTNPinterfaceByVpnIdWithThrowException() throws Exception {
        when(aaiService.getPinterfaceByVpnId("1")).thenReturn(failedCall("failed to get VPN ID"));
        sotnServiceTemplateService.getSOTNPinterfaceByVpnId("1");
    }

    @Test
    public void getSOTNPnfTest() throws Exception {
        ResponseBody result = null;
        when(aaiService.getPnfInfo("test")).thenReturn(successfulCall(result));
        sotnServiceTemplateService.getSOTNPnf("test");
    }

    @Test
    public void getSOTNPnfWithThrowException() throws Exception {
        when(aaiService.getPnfInfo("test")).thenReturn(failedCall("Failed to get PNF info."));
        sotnServiceTemplateService.getSOTNPnf("test");
    }

    @Test
    public void getSOTNLinkbyNameTest() throws Exception {
        ResponseBody result = null;
        when(aaiService.getSpecificLogicalLink("link")).thenReturn(successfulCall(result));
        sotnServiceTemplateService.getSOTNLinkbyName("link");
    }

    @Test
    public void getSOTNLinkbyNameWithThrowException() throws Exception {
        when(aaiService.getSpecificLogicalLink("link")).thenReturn(failedCall("Failed to get link info."));
        sotnServiceTemplateService.getSOTNLinkbyName("link");
    }
}
