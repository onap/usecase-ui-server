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
package org.onap.usecaseui.server.controller.lcm;

import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.service.lcm.ServiceTemplateService;

import static org.mockito.Mockito.*;

public class ServiceTemplateControllerTest {

    private ServiceTemplateService service;
    private ServiceTemplateController controller = new ServiceTemplateController();

    @Before
    public void setUp() {
        service = mock(ServiceTemplateService.class);
        controller.setServiceTemplateService(service);
    }

    @Test
    public void testGetServiceTemplates() throws Exception {
        controller.getServiceTemplates();

        verify(service, times(1)).listDistributedServiceTemplate();
    }

    @Test
    public void testGetServiceTemplateInput() throws Exception {
        String uuid = "1";
        String modelPath = "modelPath";
        controller.getServiceTemplateInput(uuid, modelPath);

        verify(service, times(1)).fetchServiceTemplateInput(uuid, "/api"+modelPath);
    }

    @Test
    public void testGetLocations() throws Exception {
        controller.getLocations();

        verify(service, times(1)).listVim();
    }

    @Test
    public void testGetSDNCControllers() throws Exception {
        controller.getSDNCControllers();

        verify(service, times(1)).listSDNCControllers();
    }
}