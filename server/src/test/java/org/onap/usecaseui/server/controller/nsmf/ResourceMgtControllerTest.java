/*
 * Copyright (C) 2019 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.controller.nsmf;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.onap.usecaseui.server.service.nsmf.ResourceMgtService;

public class ResourceMgtControllerTest {

    @Test
    public void testQuerySlicingBusiness() {
        ResourceMgtService resourceMgtService = mock(ResourceMgtService.class);
        ResourceMgtController resourceMgtController = new ResourceMgtController();
        resourceMgtController.setResourceMgtService(resourceMgtService);

        resourceMgtController.querySlicingBusiness(1, 10);
        verify(resourceMgtService, times(1)).querySlicingBusiness(1, 10);
    }

    @Test
    public void testQuerySlicingBusinessByStatus() {
        ResourceMgtService resourceMgtService = mock(ResourceMgtService.class);
        ResourceMgtController resourceMgtController = new ResourceMgtController();
        resourceMgtController.setResourceMgtService(resourceMgtService);

        resourceMgtController.querySlicingBusinessByStatus("Planning", 1, 10);
        verify(resourceMgtService, times(1)).querySlicingBusinessByStatus("Planning", 1, 10);
    }

    @Test
    public void testQuerySlicingBusinessDetails() {
        ResourceMgtService resourceMgtService = mock(ResourceMgtService.class);
        ResourceMgtController resourceMgtController = new ResourceMgtController();
        resourceMgtController.setResourceMgtService(resourceMgtService);

        resourceMgtController.querySlicingBusinessDetails("oou9-876e-jhy7-uy78");
        verify(resourceMgtService, times(1)).querySlicingBusinessDetails("oou9-876e-jhy7-uy78");
    }

    @Test
    public void testQueryNsiInstances() {
        ResourceMgtService resourceMgtService = mock(ResourceMgtService.class);
        ResourceMgtController resourceMgtController = new ResourceMgtController();
        resourceMgtController.setResourceMgtService(resourceMgtService);

        resourceMgtController.queryNsiInstances(1, 10);
        verify(resourceMgtService, times(1)).queryNsiInstances(1, 10);
    }

    @Test
    public void testQueryNsiInstancesByStatus() {
        ResourceMgtService resourceMgtService = mock(ResourceMgtService.class);
        ResourceMgtController resourceMgtController = new ResourceMgtController();
        resourceMgtController.setResourceMgtService(resourceMgtService);

        resourceMgtController.queryNsiInstancesByStatus("activated", 1, 10);
        verify(resourceMgtService, times(1)).queryNsiInstancesByStatus("activated", 1, 10);
    }

    @Test
    public void testQueryNsiDetails() {
        ResourceMgtService resourceMgtService = mock(ResourceMgtService.class);
        ResourceMgtController resourceMgtController = new ResourceMgtController();
        resourceMgtController.setResourceMgtService(resourceMgtService);

        resourceMgtController.queryNsiDetails("oou9-876e-jhy7-uy78");
        verify(resourceMgtService, times(1)).queryNsiDetails("oou9-876e-jhy7-uy78");
    }

    @Test
    public void testQueryNsiRelatedNssiInfo() {
        ResourceMgtService resourceMgtService = mock(ResourceMgtService.class);
        ResourceMgtController resourceMgtController = new ResourceMgtController();
        resourceMgtController.setResourceMgtService(resourceMgtService);

        resourceMgtController.queryNsiRelatedNssiInfo("oou9-876e-jhy7-uy78");
        verify(resourceMgtService, times(1)).queryNsiRelatedNssiInfo("oou9-876e-jhy7-uy78");
    }

    @Test
    public void testQueryNssiInstances() {
        ResourceMgtService resourceMgtService = mock(ResourceMgtService.class);
        ResourceMgtController resourceMgtController = new ResourceMgtController();
        resourceMgtController.setResourceMgtService(resourceMgtService);

        resourceMgtController.queryNssiInstances(1, 10);
        verify(resourceMgtService, times(1)).queryNssiInstances(1, 10);
    }

    @Test
    public void testQueryNssiInstancesByStatus() {
        ResourceMgtService resourceMgtService = mock(ResourceMgtService.class);
        ResourceMgtController resourceMgtController = new ResourceMgtController();
        resourceMgtController.setResourceMgtService(resourceMgtService);

        resourceMgtController.queryNssiInstancesByStatus("activated", 1, 10);
        verify(resourceMgtService, times(1)).queryNssiInstancesByStatus("activated", 1, 10);
    }

    @Test
    public void testQueryNssiInstancesByEnvironment() {
        ResourceMgtService resourceMgtService = mock(ResourceMgtService.class);
        ResourceMgtController resourceMgtController = new ResourceMgtController();
        resourceMgtController.setResourceMgtService(resourceMgtService);

        resourceMgtController.queryNssiInstancesByEnvironment("activated", 1, 10);
        verify(resourceMgtService, times(1)).queryNssiInstancesByEnvironment("activated", 1, 10);
    }

    @Test
    public void testQueryNssiDetails() {
        ResourceMgtService resourceMgtService = mock(ResourceMgtService.class);
        ResourceMgtController resourceMgtController = new ResourceMgtController();
        resourceMgtController.setResourceMgtService(resourceMgtService);

        resourceMgtController.queryNssiDetails("oou9-876e-jhy7-uy78");
        verify(resourceMgtService, times(1)).queryNssiDetails("oou9-876e-jhy7-uy78");
    }

    @Test
    public void testActivateSlicingService() {
        ResourceMgtService resourceMgtService = mock(ResourceMgtService.class);
        ResourceMgtController resourceMgtController = new ResourceMgtController();
        resourceMgtController.setResourceMgtService(resourceMgtService);

        resourceMgtController.activateSlicingService("oou9-876e-jhy7-uy78");
        verify(resourceMgtService, times(1)).activateSlicingService("oou9-876e-jhy7-uy78");
    }

    @Test
    public void testDeactivateSlicingService() {
        ResourceMgtService resourceMgtService = mock(ResourceMgtService.class);
        ResourceMgtController resourceMgtController = new ResourceMgtController();
        resourceMgtController.setResourceMgtService(resourceMgtService);

        resourceMgtController.deactivateSlicingService("oou9-876e-jhy7-uy78");
        verify(resourceMgtService, times(1)).deactivateSlicingService("oou9-876e-jhy7-uy78");
    }

    @Test
    public void testTerminateSlicingService() {
        ResourceMgtService resourceMgtService = mock(ResourceMgtService.class);
        ResourceMgtController resourceMgtController = new ResourceMgtController();
        resourceMgtController.setResourceMgtService(resourceMgtService);

        resourceMgtController.terminateSlicingService("oou9-876e-jhy7-uy78");
        verify(resourceMgtService, times(1)).terminateSlicingService("oou9-876e-jhy7-uy78");
    }

    @Test
    public void testQueryOperationProgress() {
        ResourceMgtService resourceMgtService = mock(ResourceMgtService.class);
        ResourceMgtController resourceMgtController = new ResourceMgtController();
        resourceMgtController.setResourceMgtService(resourceMgtService);

        resourceMgtController.queryOperationProgress("oou9-876e-jhy7-uy78");
        verify(resourceMgtService, times(1)).queryOperationProgress("oou9-876e-jhy7-uy78");
    }
}
