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
import org.onap.usecaseui.server.service.lcm.PackageDistributionService;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.Csar;

import static org.mockito.Mockito.*;

public class PackageDistributionControllerTest {

    private PackageDistributionService service;
    private PackageDistributionController controller = new PackageDistributionController();

    @Before
    public void setUp() {
        service = mock(PackageDistributionService.class);
        controller.setPackageDistributionService(service);
    }

    @Test
    public void retrievePackageInfo() throws Exception {
        controller.retrievePackageInfo();

        verify(service, times(1)).retrievePackageInfo();
    }

    @Test
    public void testDistributeNsPackage() throws Exception {
        Csar csar = new Csar();
        controller.distributeNsPackage(csar);

        verify(service, times(1)).postNsPackage(csar);
    }

    @Test
    public void distributeVfPackage() throws Exception {
        Csar csar = new Csar();
        controller.distributeVfPackage(csar);

        verify(service, times(1)).postVfPackage(csar);
    }

    @Test
    public void testGetJobStatus() throws Exception {
        String jobId = "1";
        controller.getJobStatus(jobId);

        verify(service, times(1)).getJobStatus(jobId);
    }
}