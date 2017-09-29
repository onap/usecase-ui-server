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
package org.onap.usecaseui.server.service.lcm.impl;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.onap.usecaseui.server.bean.lcm.VfNsPackageInfo;
import org.onap.usecaseui.server.service.lcm.PackageDistributionService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.VimInfo;
import org.onap.usecaseui.server.service.lcm.domain.sdc.SDCCatalogService;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.SDCServiceTemplate;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.Vnf;
import org.onap.usecaseui.server.service.lcm.domain.sdc.exceptions.SDCCatalogException;
import org.onap.usecaseui.server.service.lcm.domain.vfc.VfcService;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.Csar;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.DistributionResult;
import org.onap.usecaseui.server.service.lcm.domain.vfc.exceptions.VfcException;
import org.onap.usecaseui.server.util.CallStub;
import retrofit2.Call;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.onap.usecaseui.server.service.lcm.domain.sdc.consts.SDCConsts.*;

public class DefaultPackageDistributionServiceTest {

    @Test
    public void itCanRetrievePackageFromSDCAndAAI() {
        List<SDCServiceTemplate> serviceTemplate = Collections.singletonList(new SDCServiceTemplate("1", "1", "service", "", ""));
        List<Vnf> vnf = Collections.singletonList(new Vnf("2","2","vnf"));
        SDCCatalogService sdcService = newSDCService(serviceTemplate, vnf);

        List<VimInfo> vim = Collections.singletonList(new VimInfo("owner", "regionId"));
        AAIService aaiService = newAAIService(vim);

        PackageDistributionService service = new DefaultPackageDistributionService(sdcService,aaiService, null);

        Assert.assertThat(service.retrievePackageInfo(), equalTo(new VfNsPackageInfo(serviceTemplate, vnf, vim)));
    }

    private AAIService newAAIService(List<VimInfo> vim) {
        AAIService aaiService = Mockito.mock(AAIService.class);

        Call<List<VimInfo>> vimCall = CallStub.successfulCall(vim);
        Mockito.when(aaiService.listVimInfo()).thenReturn(vimCall);
        return aaiService;
    }

    private SDCCatalogService newSDCService(List<SDCServiceTemplate> serviceTemplate, List<Vnf> vnf) {
        SDCCatalogService sdcService = Mockito.mock(SDCCatalogService.class);

        Call<List<SDCServiceTemplate>> serviceCall = CallStub.successfulCall(serviceTemplate);
        Mockito.when(sdcService.listServices(CATEGORY_NS, DISTRIBUTION_STATUS_DISTRIBUTED)).thenReturn(serviceCall);

        Call<List<Vnf>> vnfCall = CallStub.successfulCall(vnf);
        Mockito.when(sdcService.listResources(RESOURCETYPE_VF, DISTRIBUTION_STATUS_DISTRIBUTED)).thenReturn(vnfCall);
        return sdcService;
    }

    @Test(expected = SDCCatalogException.class)
    public void retrievePackageWillThrowExceptionWhenSDCIsNotAvailable() {
        SDCCatalogService sdcService = Mockito.mock(SDCCatalogService.class);
        Call<List<SDCServiceTemplate>> serviceCall = CallStub.failedCall("SDC is not available!");
        Mockito.when(sdcService.listServices(CATEGORY_NS, DISTRIBUTION_STATUS_DISTRIBUTED)).thenReturn(serviceCall);

        List<VimInfo> vim = Collections.singletonList(new VimInfo("owner", "regionId"));
        AAIService aaiService = newAAIService(vim);

        PackageDistributionService service = new DefaultPackageDistributionService(sdcService,aaiService, null);
        service.retrievePackageInfo();
    }

    @Test
    public void itCanPostNsPackageToVFC() {
        VfcService vfcService = Mockito.mock(VfcService.class);
        Csar csar = new Csar();
        DistributionResult result = new DistributionResult("status", "description", "errorcode");
        Mockito.when(vfcService.distributeNsPackage(csar)).thenReturn(CallStub.successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, null, vfcService);

        Assert.assertSame(result, service.postNsPackage(csar));
    }

    @Test(expected = VfcException.class)
    public void postNsPackageWillThrowExceptionWhenVFCIsNotAvailable() {
        VfcService vfcService = Mockito.mock(VfcService.class);
        Csar csar = new Csar();
        Mockito.when(vfcService.distributeNsPackage(csar)).thenReturn(CallStub.failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, null, vfcService);
        service.postNsPackage(csar);
    }
}