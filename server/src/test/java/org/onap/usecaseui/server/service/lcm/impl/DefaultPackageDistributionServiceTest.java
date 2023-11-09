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
import org.onap.usecaseui.server.bean.lcm.VfNsPackageInfo;
import org.onap.usecaseui.server.service.lcm.PackageDistributionService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.VimInfo;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.VimInfoRsp;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.nsServiceRsp;
import org.onap.usecaseui.server.service.lcm.domain.sdc.SDCCatalogService;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.SDCServiceTemplate;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.Vnf;
import org.onap.usecaseui.server.service.lcm.domain.sdc.exceptions.SDCCatalogException;
import org.onap.usecaseui.server.service.lcm.domain.vfc.VfcService;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.Csar;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.DistributionResult;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.Job;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.JobStatus;
import org.onap.usecaseui.server.service.lcm.domain.vfc.exceptions.VfcException;

import okhttp3.ResponseBody;
import retrofit2.Call;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;

import static org.mockito.Matchers.anyObject;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.onap.usecaseui.server.service.lcm.domain.sdc.consts.SDCConsts.*;
import static org.onap.usecaseui.server.util.CallStub.emptyBodyCall;
import static org.onap.usecaseui.server.util.CallStub.failedCall;
import static org.onap.usecaseui.server.util.CallStub.successfulCall;

public class DefaultPackageDistributionServiceTest {
	
	
    private HttpServletRequest mockRequest() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContentLength()).thenReturn(0);
        ServletInputStream inStream = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return 0;
            }
        };
        when(request.getInputStream()).thenReturn(inStream);
        return request;
    }

    @Test
    public void itCanRetrievePackageFromSDCAndAAI() {
        List<SDCServiceTemplate> serviceTemplate = Collections.singletonList(new SDCServiceTemplate("1", "1", "service", "V1","", ""));
        Vnf o = new Vnf();
        o.setInvariantUUID("2");
        o.setUuid("2");
        o.setName("vnf");
        List<Vnf> vnf = Collections.singletonList(o);
        SDCCatalogService sdcService = newSDCService(serviceTemplate, vnf);

        List<VimInfo> vim = Collections.singletonList(new VimInfo("owner", "regionId"));
        AAIService aaiService = newAAIService(vim);

        PackageDistributionService service = new DefaultPackageDistributionService(sdcService, null);

        Assert.assertThat(service.retrievePackageInfo(), equalTo(new VfNsPackageInfo(serviceTemplate, vnf)));
    }

    private AAIService newAAIService(List<VimInfo> vim) {
        AAIService aaiService = mock(AAIService.class);
        VimInfoRsp rsp = new VimInfoRsp();
        rsp.setCloudRegion(vim);
        Call<VimInfoRsp> vimCall = successfulCall(rsp);
        when(aaiService.listVimInfo()).thenReturn(vimCall);
        return aaiService;
    }

    private SDCCatalogService newSDCService(List<SDCServiceTemplate> serviceTemplate, List<Vnf> vnf) {
        SDCCatalogService sdcService = mock(SDCCatalogService.class);

        Call<List<SDCServiceTemplate>> serviceCall = successfulCall(serviceTemplate);
        when(sdcService.listServices(CATEGORY_NS, DISTRIBUTION_STATUS_DISTRIBUTED)).thenReturn(serviceCall);

        Call<List<Vnf>> vnfCall = successfulCall(vnf);
        when(sdcService.listResources(RESOURCETYPE_VF)).thenReturn(vnfCall);
        return sdcService;
    }

    @Test(expected = SDCCatalogException.class)
    public void retrievePackageWillThrowExceptionWhenSDCIsNotAvailable() {
        SDCCatalogService sdcService = mock(SDCCatalogService.class);
        Call<List<SDCServiceTemplate>> serviceCall = failedCall("SDC is not available!");
        when(sdcService.listServices(CATEGORY_NS, DISTRIBUTION_STATUS_DISTRIBUTED)).thenReturn(serviceCall);

        List<VimInfo> vim = Collections.singletonList(new VimInfo("owner", "regionId"));
        AAIService aaiService = newAAIService(vim);

        PackageDistributionService service = new DefaultPackageDistributionService(sdcService, null);
        service.retrievePackageInfo();
    }

    @Test
    public void retrievePackageWillBeEmptyWhenNoNsServiceAndVfInSDC() {
        SDCCatalogService sdcService = mock(SDCCatalogService.class);
        Call<List<SDCServiceTemplate>> serviceCall = emptyBodyCall();
        when(sdcService.listServices(CATEGORY_NS, DISTRIBUTION_STATUS_DISTRIBUTED)).thenReturn(serviceCall);

        Call<List<Vnf>> resourceCall = emptyBodyCall();
        when(sdcService.listResources(RESOURCETYPE_VF)).thenReturn(resourceCall);

        PackageDistributionService service = new DefaultPackageDistributionService(sdcService, null);
        VfNsPackageInfo vfNsPackageInfo = service.retrievePackageInfo();

        Assert.assertTrue("ns should be empty!", vfNsPackageInfo.getNsPackage().isEmpty());
        Assert.assertTrue("vf should be empty!", vfNsPackageInfo.getVnfPackages().isEmpty());
    }

    @Test
    public void itCanPostNsPackageToVFC() {
        VfcService vfcService = mock(VfcService.class);
        Csar csar = new Csar();
        DistributionResult result = new DistributionResult();
        result.setStatus("status");
        result.setStatusDescription("description");
        result.setErrorCode("errorcode");
        when(vfcService.distributeNsPackage(csar)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(result, service.postNsPackage(csar));
    }

    @Test(expected = VfcException.class)
    public void postNsPackageWillThrowExceptionWhenVFCIsNotAvailable() {
        VfcService vfcService = mock(VfcService.class);
        Csar csar = new Csar();
        when(vfcService.distributeNsPackage(csar)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.postNsPackage(csar);
    }

    @Test(expected = VfcException.class)
    public void postNsPackageWillThrowExceptionWhenVFCResponseError() {
        VfcService vfcService = mock(VfcService.class);
        Csar csar = new Csar();
        when(vfcService.distributeNsPackage(csar)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.postNsPackage(csar);
    }

    @Test
    public void itCanPostVnfPackageToVFC() {
        VfcService vfcService = mock(VfcService.class);
        Csar csar = new Csar();
        Job job = new Job();
        when(vfcService.distributeVnfPackage(csar)).thenReturn(successfulCall(job));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(job, service.postVfPackage(csar));
    }

    @Test(expected = VfcException.class)
    public void postVnfPackageWillThrowExceptionWhenVFCIsNotAvailable() {
        VfcService vfcService = mock(VfcService.class);
        Csar csar = new Csar();
        when(vfcService.distributeVnfPackage(csar)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.postVfPackage(csar);
    }

    @Test(expected = VfcException.class)
    public void postVnfPackageWillThrowExceptionWhenVFCResponseError() {
        VfcService vfcService = mock(VfcService.class);
        Csar csar = new Csar();
        when(vfcService.distributeVnfPackage(csar)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.postVfPackage(csar);
    }

    @Test
    public void itCanGetJobStatusFromVFC() {
        VfcService vfcService = mock(VfcService.class);
        String jobId = "1";
        String responseId = "1";
        JobStatus jobStatus = new JobStatus();
        when(vfcService.getJobStatus(jobId, responseId)).thenReturn(successfulCall(jobStatus));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(jobStatus, service.getJobStatus(jobId, responseId));
    }

    @Test(expected = VfcException.class)
    public void getJobStatusWillThrowExceptionWhenVFCIsNotAvailable() {
        VfcService vfcService = mock(VfcService.class);
        String jobId = "1";
        String responseId = "1";
        when(vfcService.getJobStatus(jobId, responseId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.getJobStatus(jobId, responseId);
    }

    @Test(expected = VfcException.class)
    public void getJobStatusWillThrowExceptionWhenVFCResponseError() {
        VfcService vfcService = mock(VfcService.class);
        String jobId = "1";
        String responseId = "1";
        when(vfcService.getJobStatus(jobId, responseId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.getJobStatus(jobId, responseId);
    }

    @Test
    public void itCanGetNsLcmJobStatusFromVFC() {
        VfcService vfcService = mock(VfcService.class);
        String jobId = "1";
        String responseId = "1";
        String serviceId= "1";
        String operationType= "1";
        JobStatus jobStatus = new JobStatus();
        when(vfcService.getNsLcmJobStatus(jobId, responseId)).thenReturn(successfulCall(jobStatus));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(jobStatus, service.getNsLcmJobStatus(serviceId,jobId, responseId,operationType));
    }

    @Test(expected = VfcException.class)
    public void getNsLcmJobStatusWillThrowExceptionWhenVFCIsNotAvailable() {
        VfcService vfcService = mock(VfcService.class);
        String jobId = "1";
        String responseId = "1";
        String serviceId= "1";
        String operationType= "1";
        when(vfcService.getNsLcmJobStatus(jobId, responseId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.getNsLcmJobStatus(serviceId,jobId, responseId,operationType);
    }

    @Test(expected = VfcException.class)
    public void getNsLcmJobStatusWillThrowExceptionWhenVFCResponseError() {
        VfcService vfcService = mock(VfcService.class);
        String jobId = "1";
        String responseId = "1";
        String serviceId= "1";
        String operationType= "1";
        when(vfcService.getNsLcmJobStatus(jobId, responseId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.getNsLcmJobStatus(serviceId,jobId, responseId,operationType);
    }
    
    @Test
    public void itCanDeleteNsPackage() {
        String csarId = "1";
        DistributionResult result = new DistributionResult();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteNsPackage(csarId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(result, service.deleteNsPackage(csarId));
    }

    @Test(expected = VfcException.class)
    public void deleteNsPackageWillThrowExceptionWhenVFCIsNotAvailable() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteNsPackage(csarId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.deleteNsPackage(csarId);
    }

    @Test(expected = VfcException.class)
    public void deleteNsPackageWillThrowExceptionWhenVFCResponseError() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteNsPackage(csarId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.deleteNsPackage(csarId);
    }
    
    @Test
    public void itCanGetVnfPackages(){
    	ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getVnfPackages()).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(result, service.getVnfPackages());
    }
    
    @Test
    public void getVnfPackagesThrowExceptionWhenVFCResponseError(){
    	
    	VfcService vfcService = mock(VfcService.class);
    	when(vfcService.getVnfPackages ()).thenReturn(emptyBodyCall());
    	PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
    	service.getVnfPackages();
    }
    
    @Test
    public void getVnfPackagesThrowException(){
    	VfcService vfcService = mock(VfcService.class);
    	when(vfcService.getVnfPackages ()).thenReturn(failedCall("VFC is not available!"));
    	PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
    	service.getVnfPackages();
    }
    
    @Test
    public void itCanDeleteVFPackage() {
        String csarId = "1";
        Job job = new Job();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteVnfPackage(csarId)).thenReturn(successfulCall(job));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(job, service.deleteVfPackage(csarId));
    }

    @Test(expected = VfcException.class)
    public void deleteVfPackageWillThrowExceptionWhenVFCIsNotAvailable() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteVnfPackage(csarId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.deleteVfPackage(csarId);
    }

    @Test(expected = VfcException.class)
    public void deleteVnfPackageWillThrowExceptionWhenVFCResponseError() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteVnfPackage(csarId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.deleteVnfPackage(csarId);
    }
    
    @Test
    public void itCanGetNetworkServicePackages() {
    	ResponseBody responseBody = null;
        VfcService vfcService = mock(VfcService.class);
        JobStatus jobStatus = new JobStatus();
        when(vfcService.getNetworkServicePackages()).thenReturn(successfulCall(responseBody));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(jobStatus, service.getNetworkServicePackages());
    }

    @Test(expected = VfcException.class)
    public void getNetworkServicePackagesWillThrowExceptionWhenVFCIsNotAvailable() {
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getNetworkServicePackages()).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.getNetworkServicePackages();
    }

    @Test(expected = VfcException.class)
    public void getNetworkServicePackagesWillThrowExceptionWhenVFCResponseError() {
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getNetworkServicePackages()).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.getNetworkServicePackages();
    }
    
    @Test
    public void itCanGetPnfPackages(){
    	ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getPnfPackages()).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(result, service.getPnfPackages());
    }
    
    @Test
    public void getPnfPackagesThrowExceptionWhenVFCResponseError(){
    	
    	VfcService vfcService = mock(VfcService.class);
    	when(vfcService.getPnfPackages ()).thenReturn(emptyBodyCall());
    	PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
    	service.getPnfPackages();
    }
    
    @Test
    public void getPnfPackagesThrowException(){
    	VfcService vfcService = mock(VfcService.class);
    	when(vfcService.getPnfPackages ()).thenReturn(failedCall("VFC is not available!"));
    	PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
    	service.getPnfPackages();
    }
    
    @Test
    public void itDownLoadNsPackage(){
    	String nsdInfoId="1";
    	ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.downLoadNsPackage(nsdInfoId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(result, service.downLoadNsPackage(nsdInfoId));
    }
    
    @Test
    public void downLoadNsPackagehrowExceptionWhenVFCResponseError(){
    	String nsdInfoId="1";
    	VfcService vfcService = mock(VfcService.class);
    	when(vfcService.downLoadNsPackage (nsdInfoId)).thenReturn(emptyBodyCall());
    	PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
    	service.downLoadNsPackage(nsdInfoId);
    }
    
    @Test
    public void downLoadNsPackageThrowException(){
    	String nsdInfoId="1";
    	VfcService vfcService = mock(VfcService.class);
    	when(vfcService.downLoadNsPackage (nsdInfoId)).thenReturn(failedCall("VFC is not available!"));
    	PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
    	service.downLoadNsPackage(nsdInfoId);
    }
    
    @Test
    public void itDownLoadPnfPackage(){
    	String pnfInfoId="1";
    	ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.downLoadPnfPackage(pnfInfoId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(result, service.downLoadPnfPackage(pnfInfoId));
    }
    
    @Test
    public void downLoadPnfPackagehrowExceptionWhenVFCResponseError(){
    	String pnfInfoId="1";
    	VfcService vfcService = mock(VfcService.class);
    	when(vfcService.downLoadPnfPackage (pnfInfoId)).thenReturn(emptyBodyCall());
    	PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
    	service.downLoadPnfPackage(pnfInfoId);
    }
    
    @Test
    public void downLoadPnfPackageThrowException(){
    	String pnfInfoId="1";
    	VfcService vfcService = mock(VfcService.class);
    	when(vfcService.downLoadPnfPackage (pnfInfoId)).thenReturn(failedCall("VFC is not available!"));
    	PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
    	service.downLoadPnfPackage(pnfInfoId);
    }
    
    @Test
    public void itDownLoadVnfPackage(){
    	String vnfInfoId="1";
    	ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.downLoadVnfPackage(vnfInfoId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(result, service.downLoadVnfPackage(vnfInfoId));
    }
    
    @Test
    public void downLoadVnfPackagehrowExceptionWhenVFCResponseError(){
    	String vnfInfoId="1";
    	VfcService vfcService = mock(VfcService.class);
    	when(vfcService.downLoadVnfPackage (vnfInfoId)).thenReturn(emptyBodyCall());
    	PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
    	service.downLoadVnfPackage(vnfInfoId);
    }
    
    @Test
    public void downLoadVnfPackageThrowException(){
    	String vnfInfoId="1";
    	VfcService vfcService = mock(VfcService.class);
    	when(vfcService.downLoadVnfPackage (vnfInfoId)).thenReturn(failedCall("VFC is not available!"));
    	PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
    	service.downLoadVnfPackage(vnfInfoId);
    }
    
    @Test
    public void itCanDeleteNsdPackage() {
        String csarId = "1";
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteNsdPackage(csarId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(result, service.deleteNsdPackage(csarId));
    }

    @Test(expected = VfcException.class)
    public void deleteNsdPackageWillThrowExceptionWhenVFCIsNotAvailable() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteNsdPackage(csarId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.deleteNsdPackage(csarId);
    }

    @Test(expected = VfcException.class)
    public void deleteNsdPackageWillThrowExceptionWhenVFCResponseError() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteNsdPackage(csarId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.deleteNsdPackage(csarId);
    }
    
    @Test
    public void itCanDeleteVnfPackage() {
        String csarId = "1";
        ResponseBody result=null;
        Job job = new Job();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteVnfPackage(csarId)).thenReturn(successfulCall(job));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(job, service.deleteVnfPackage(csarId));
    }

    @Test(expected = VfcException.class)
    public void deleteVnfPackageWillThrowExceptionWhenVFCIsNotAvailable() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteVnfPackage(csarId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.deleteVnfPackage(csarId);
    }

    @Test(expected = VfcException.class)
    public void deleteVnfNsdPackageWillThrowExceptionWhenVFCResponseError() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteVnfPackage(csarId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.deleteVnfPackage(csarId);
    }
    
    @Test
    public void itCanDeletePnfdPackage() {
        String csarId = "1";
        ResponseBody result=null;
        Job job = new Job();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deletePnfdPackage(csarId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(result, service.deletePnfPackage(csarId));
    }

    @Test(expected = VfcException.class)
    public void deletePnfPackageWillThrowExceptionWhenVFCIsNotAvailable() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deletePnfdPackage(csarId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.deletePnfPackage(csarId);
    }

    @Test(expected = VfcException.class)
    public void deletePnfPackageWillThrowExceptionWhenVFCResponseError() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deletePnfdPackage(csarId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.deletePnfPackage(csarId);
    }
    
    @Test
    public void itCanDeleteNetworkServiceInstance() {
        String csarId = "1";
        ResponseBody result=null;
        Job job = new Job();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteNetworkServiceInstance(csarId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(result, service.deleteNetworkServiceInstance(csarId));
    }

    @Test(expected = VfcException.class)
    public void deleteNetworkServiceInstanceWillThrowExceptionWhenVFCIsNotAvailable() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteNetworkServiceInstance(csarId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.deleteNetworkServiceInstance(csarId);
    }

    @Test(expected = VfcException.class)
    public void deleteNetworkServiceInstanceWillThrowExceptionWhenVFCResponseError() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteNetworkServiceInstance(csarId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.deleteNetworkServiceInstance(csarId);
    }
    
    @Test
    public void itCanCreateNetworkServiceInstance() throws IOException {
    	HttpServletRequest request = mockRequest();
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createNetworkServiceInstance(anyObject())).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(result, service.createNetworkServiceInstance(request));
    }

    @Test(expected = VfcException.class)
    public void createNetworkServiceInstanceWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	HttpServletRequest request = mockRequest();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createNetworkServiceInstance(anyObject())).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.createNetworkServiceInstance(request);
    }

    @Test(expected = VfcException.class)
    public void createNetworkServiceInstanceWillThrowExceptionWhenVFCResponseError() throws IOException {
    	HttpServletRequest request = mockRequest();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createNetworkServiceInstance(anyObject())).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.createNetworkServiceInstance(request);
    }
    
    @Test
    public void itCanGetNetworkServiceInfo() throws IOException {
        ResponseBody result=null;
        nsServiceRsp ns = new nsServiceRsp();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getNetworkServiceInfo()).thenReturn(successfulCall(ns));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(result, service.getNetworkServiceInfo());
    }

    @Test(expected = VfcException.class)
    public void getNetworkServiceInfoWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getNetworkServiceInfo()).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.getNetworkServiceInfo();
    }

    @Test(expected = VfcException.class)
    public void getNetworkServiceInfoWillThrowExceptionWhenVFCResponseError() throws IOException {
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getNetworkServiceInfo()).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.getNetworkServiceInfo();
    }
    

    
    @Test
    public void itCanHealNetworkServiceInstance() throws IOException {
    	HttpServletRequest request = mockRequest();
        String csarId = "1";
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        //when(vfcService.healNetworkServiceInstance(csarId,anyObject())).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        //Assert.assertSame(result, service.healNetworkServiceInstance(request,csarId));
        service.healNetworkServiceInstance(request,csarId);
    }

    @Test(expected = VfcException.class)
    public void healNetworkServiceInstanceWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	HttpServletRequest request = mockRequest();
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.healNetworkServiceInstance(csarId,anyObject())).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.healNetworkServiceInstance(request,csarId);
    }

    @Test(expected = VfcException.class)
    public void healNetworkServiceInstanceWillThrowExceptionWhenVFCResponseError() throws IOException {
    	HttpServletRequest request = mockRequest();
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.healNetworkServiceInstance(csarId,anyObject())).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.healNetworkServiceInstance(request,csarId);
    }
    
    @Test
    public void itCanScaleNetworkServiceInstance() throws IOException {
    	HttpServletRequest request = mockRequest();
        String csarId = "1";
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        //when(vfcService.scaleNetworkServiceInstance(csarId,anyObject())).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(result, service.scaleNetworkServiceInstance(request,csarId));
    }

    @Test(expected = VfcException.class)
    public void scaleNetworkServiceInstanceWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	HttpServletRequest request = mockRequest();
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.scaleNetworkServiceInstance(csarId,anyObject())).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.scaleNetworkServiceInstance(request,csarId);
    }

    @Test(expected = VfcException.class)
    public void scaleNetworkServiceInstanceWillThrowExceptionWhenVFCResponseError() throws IOException {
    	HttpServletRequest request = mockRequest();
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.scaleNetworkServiceInstance(csarId,anyObject())).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.scaleNetworkServiceInstance(request,csarId);
    }
    
    
    @Test
    public void itCaninstantiateNetworkServiceInstance() throws IOException {
    	HttpServletRequest request = mockRequest();
    	String serviceInstanceId="1";
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        //when(vfcService.instantiateNetworkServiceInstance(anyObject(),serviceInstanceId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        service.instantiateNetworkServiceInstance(request,serviceInstanceId);
    }

    @Test(expected = VfcException.class)
    public void instantiateNetworkServiceInstanceWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	HttpServletRequest request = mockRequest();
    	String serviceInstanceId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.instantiateNetworkServiceInstance(anyObject(),serviceInstanceId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.instantiateNetworkServiceInstance(request,serviceInstanceId);
    }

    @Test(expected = VfcException.class)
    public void instantiateNetworkServiceInstanceWillThrowExceptionWhenVFCResponseError() throws IOException {
    	HttpServletRequest request = mockRequest();
    	String serviceInstanceId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.instantiateNetworkServiceInstance(anyObject(),serviceInstanceId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.instantiateNetworkServiceInstance(request,serviceInstanceId);
    }
    
    
    @Test
    public void itCanTerminateNetworkServiceInstance() throws IOException {
    	HttpServletRequest request = mockRequest();
        String csarId = "1";
        ResponseBody result=null;
        Job job = new Job();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.terminateNetworkServiceInstance(csarId,anyObject())).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        service.terminateNetworkServiceInstance(request,csarId);
    }

    @Test(expected = VfcException.class)
    public void terminateNetworkServiceInstanceWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	HttpServletRequest request = mockRequest();
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        //when(vfcService.terminateNetworkServiceInstance(csarId,anyObject())).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.terminateNetworkServiceInstance(request,csarId);
    }

    @Test(expected = VfcException.class)
    public void terminateNetworkServiceInstanceWillThrowExceptionWhenVFCResponseError() throws IOException {
    	HttpServletRequest request = mockRequest();
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.terminateNetworkServiceInstance(csarId,anyObject())).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.terminateNetworkServiceInstance(request,csarId);
    }
    
    @Test
    public void itCreateNetworkServiceData() throws IOException {
    	HttpServletRequest request = mockRequest();
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createNetworkServiceData(anyObject())).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(result, service.createNetworkServiceData(request));
    }

    @Test(expected = VfcException.class)
    public void createNetworkServiceDataWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	HttpServletRequest request = mockRequest();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createNetworkServiceData(anyObject())).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.createNetworkServiceData(request);
    }

    @Test(expected = VfcException.class)
    public void createNetworkServiceDataWillThrowExceptionWhenVFCResponseError() throws IOException {
    	HttpServletRequest request = mockRequest();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createNetworkServiceData(anyObject())).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.createNetworkServiceData(request);
    }
    
    @Test
    public void itCreateVnfData() throws IOException {
    	HttpServletRequest request = mockRequest();
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createVnfData(anyObject())).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(result, service.createVnfData(request));
    }

    @Test(expected = VfcException.class)
    public void createVnfDataWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	HttpServletRequest request = mockRequest();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createVnfData(anyObject())).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.createVnfData(request);
    }

    @Test(expected = VfcException.class)
    public void createVnfDataWillThrowExceptionWhenVFCResponseError() throws IOException {
    	HttpServletRequest request = mockRequest();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createVnfData(anyObject())).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.createVnfData(request);
    }
    
    @Test
    public void itCreatePnfData() throws IOException {
    	HttpServletRequest request = mockRequest();
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createPnfData(anyObject())).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(result, service.createPnfData(request));
    }

    @Test(expected = VfcException.class)
    public void createPnfDataWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	HttpServletRequest request = mockRequest();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createPnfData(anyObject())).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.createPnfData(request);
    }

    @Test(expected = VfcException.class)
    public void createPnfDataWillThrowExceptionWhenVFCResponseError() throws IOException {
    	HttpServletRequest request = mockRequest();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createPnfData(anyObject())).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.createPnfData(request);
    }
    
    @Test
    public void itGetNsdInfo() throws IOException {
    	String nsdId="1";
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getNsdInfo(nsdId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(result, service.getNsdInfo(nsdId));
    }

    @Test(expected = VfcException.class)
    public void getNsdInfoWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	String nsdId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getNsdInfo(nsdId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.getNsdInfo(nsdId);
    }

    @Test(expected = VfcException.class)
    public void getNsdInfoWillThrowExceptionWhenVFCResponseError() throws IOException {
    	String nsdId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getNsdInfo(nsdId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.getNsdInfo(nsdId);
    }
    
    @Test
    public void itGetVnfInfo() throws IOException {
    	String nsdId="1";
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getVnfInfo(nsdId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(result, service.getVnfInfo(nsdId));
    }

    @Test(expected = VfcException.class)
    public void getVnfInfoWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	String nsdId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getVnfInfo(nsdId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.getVnfInfo(nsdId);
    }

    @Test(expected = VfcException.class)
    public void getVnfInfoWillThrowExceptionWhenVFCResponseError() throws IOException {
    	String nsdId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getVnfInfo(nsdId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.getVnfInfo(nsdId);
    }
    
    @Test
    public void itGetPnfInfo() throws IOException {
    	String nsdId="1";
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getPnfInfo(nsdId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(result, service.getPnfInfo(nsdId));
    }

    @Test(expected = VfcException.class)
    public void getPnfInfoWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	String nsdId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getPnfInfo(nsdId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.getPnfInfo(nsdId);
    }

    @Test(expected = VfcException.class)
    public void getPnfInfoWillThrowExceptionWhenVFCResponseError() throws IOException {
    	String nsdId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getPnfInfo(nsdId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.getPnfInfo(nsdId);
    }
    
    @Test
    public void itCanListNsTemplates() throws IOException {
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.listNsTemplates()).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(result, service.listNsTemplates());
    }

    @Test(expected = VfcException.class)
    public void listNsTemplatesWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.listNsTemplates()).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.listNsTemplates();
    }

    @Test(expected = VfcException.class)
    public void listNsTemplatesWillThrowExceptionWhenVFCResponseError() throws IOException {
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.listNsTemplates()).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.listNsTemplates();
    }
    
    @Test
    public void itCanGetVnfInfoById() throws IOException {
    	String nsdId="1";
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getVnfInfoById(nsdId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(result, service.getVnfInfoById(nsdId));
    }

    @Test(expected = VfcException.class)
    public void getVnfInfoByIdWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	String nsdId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getVnfInfoById(nsdId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.getVnfInfoById(nsdId);
    }

    @Test(expected = VfcException.class)
    public void getVnfInfoByIdWillThrowExceptionWhenVFCResponseError() throws IOException {
    	String nsdId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getVnfInfoById(nsdId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.getVnfInfoById(nsdId);
    }
    
    @Test
    public void itCanFetchNsTemplateData() throws IOException {
    	HttpServletRequest request = mockRequest();
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.fetchNsTemplateData(anyObject())).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);

        Assert.assertSame(result, service.fetchNsTemplateData(request));
    }

    @Test(expected = VfcException.class)
    public void fetchNsTemplateDataWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	HttpServletRequest request = mockRequest();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.fetchNsTemplateData(anyObject())).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.fetchNsTemplateData(request);
    }

    @Test(expected = VfcException.class)
    public void fetchNsTemplateDataWillThrowExceptionWhenVFCResponseError() throws IOException {
    	HttpServletRequest request = mockRequest();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.fetchNsTemplateData(anyObject())).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService);
        service.fetchNsTemplateData(request);
    }

  }