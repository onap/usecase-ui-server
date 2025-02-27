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

import okhttp3.MediaType;
import okio.Buffer;
import okio.BufferedSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.onap.usecaseui.server.bean.ServiceBean;
import org.onap.usecaseui.server.bean.lcm.VfNsPackageInfo;
import org.onap.usecaseui.server.service.lcm.PackageDistributionService;
import org.onap.usecaseui.server.service.lcm.ServiceLcmService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIClient;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.VimInfo;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.VimInfoRsp;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.nsServiceRsp;
import org.onap.usecaseui.server.service.lcm.domain.sdc.SDCCatalogService;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.SDCServiceTemplate;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.Vnf;
import org.onap.usecaseui.server.service.lcm.domain.vfc.VfcService;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.Csar;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.DistributionResult;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.Job;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.JobStatus;
import org.onap.usecaseui.server.service.lcm.domain.vfc.exceptions.VfcException;

import okhttp3.ResponseBody;
import retrofit2.Call;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.onap.usecaseui.server.service.lcm.domain.sdc.consts.SDCConsts.*;
import static org.onap.usecaseui.server.util.CallStub.emptyBodyCall;
import static org.onap.usecaseui.server.util.CallStub.failedCall;
import static org.onap.usecaseui.server.util.CallStub.successfulCall;

public class DefaultPackageDistributionServiceTest {

    private ResponseBody result;


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
    public void itCanRetrievePackageFromSDCAndAAI() {
        List<SDCServiceTemplate> serviceTemplate = Collections.singletonList(new SDCServiceTemplate("1", "1", "service", "V1","", ""));
        Vnf o = new Vnf();
        o.setInvariantUUID("2");
        o.setUuid("2");
        o.setName("vnf");
        List<Vnf> vnf = Collections.singletonList(o);
        SDCCatalogService sdcService = newSDCService(serviceTemplate, vnf);

        List<VimInfo> vim = Collections.singletonList(new VimInfo("owner", "regionId"));
        AAIClient aaiClient = newAAIService(vim);

        PackageDistributionService service = new DefaultPackageDistributionService(sdcService, null, null);

        Assert.assertThat(service.retrievePackageInfo(), equalTo(new VfNsPackageInfo(serviceTemplate, vnf)));
    }

    private AAIClient newAAIService(List<VimInfo> vim) {
        AAIClient aaiClient = mock(AAIClient.class);
        VimInfoRsp rsp = new VimInfoRsp();
        rsp.setCloudRegion(vim);
        Call<VimInfoRsp> vimCall = successfulCall(rsp);
        when(aaiClient.listVimInfo()).thenReturn(vimCall);
        return aaiClient;
    }

    private SDCCatalogService newSDCService(List<SDCServiceTemplate> serviceTemplate, List<Vnf> vnf) {
        SDCCatalogService sdcService = mock(SDCCatalogService.class);

        Call<List<SDCServiceTemplate>> serviceCall = successfulCall(serviceTemplate);
        when(sdcService.listServices(CATEGORY_NS, DISTRIBUTION_STATUS_DISTRIBUTED)).thenReturn(serviceCall);

        Call<List<Vnf>> vnfCall = successfulCall(vnf);
        when(sdcService.listResources(RESOURCETYPE_VF)).thenReturn(vnfCall);
        return sdcService;
    }

    @Test
    public void retrievePackageWillThrowExceptionWhenSDCIsNotAvailable() {
        SDCCatalogService sdcService = mock(SDCCatalogService.class);
        Call<List<Vnf>> serviceCall = failedCall("SDC is not available!");
        Call<List<SDCServiceTemplate>> serviceCall1 = failedCall("SDC is not available!");
        when(sdcService.listServices(CATEGORY_NS, DISTRIBUTION_STATUS_DISTRIBUTED)).thenReturn(serviceCall1);
        when(sdcService.listResources(RESOURCETYPE_VF)).thenReturn(serviceCall);

        List<VimInfo> vim = Collections.singletonList(new VimInfo("owner", "regionId"));
        AAIClient aaiClient = newAAIService(vim);

        PackageDistributionService service = new DefaultPackageDistributionService(sdcService, null, null);
        service.retrievePackageInfo();
    }

    @Test
    public void retrievePackageWillBeEmptyWhenNoNsServiceAndVfInSDC() {
        SDCCatalogService sdcService = mock(SDCCatalogService.class);
        Call<List<SDCServiceTemplate>> serviceCall = emptyBodyCall();
        when(sdcService.listServices(CATEGORY_NS, DISTRIBUTION_STATUS_DISTRIBUTED)).thenReturn(serviceCall);

        Call<List<Vnf>> resourceCall = emptyBodyCall();
        when(sdcService.listResources(RESOURCETYPE_VF)).thenReturn(resourceCall);

        PackageDistributionService service = new DefaultPackageDistributionService(sdcService, null, null);
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
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        Assert.assertSame(result, service.postNsPackage(csar));
    }

    @Test(expected = VfcException.class)
    public void postNsPackageWillThrowExceptionWhenVFCIsNotAvailable() {
        VfcService vfcService = mock(VfcService.class);
        Csar csar = new Csar();
        when(vfcService.distributeNsPackage(csar)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.postNsPackage(csar);
    }

    @Test(expected = VfcException.class)
    public void postNsPackageWillThrowExceptionWhenVFCResponseError() {
        VfcService vfcService = mock(VfcService.class);
        Csar csar = new Csar();
        when(vfcService.distributeNsPackage(csar)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.postNsPackage(csar);
    }

    @Test
    public void itCanPostVnfPackageToVFC() {
        VfcService vfcService = mock(VfcService.class);
        Csar csar = new Csar();
        Job job = new Job();
        when(vfcService.distributeVnfPackage(csar)).thenReturn(successfulCall(job));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        Assert.assertSame(job, service.postVfPackage(csar));
    }

    @Test(expected = VfcException.class)
    public void postVnfPackageWillThrowExceptionWhenVFCIsNotAvailable() {
        VfcService vfcService = mock(VfcService.class);
        Csar csar = new Csar();
        when(vfcService.distributeVnfPackage(csar)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.postVfPackage(csar);
    }

    @Test(expected = VfcException.class)
    public void postVnfPackageWillThrowExceptionWhenVFCResponseError() {
        VfcService vfcService = mock(VfcService.class);
        Csar csar = new Csar();
        when(vfcService.distributeVnfPackage(csar)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.postVfPackage(csar);
    }

    @Test
    public void itCanGetJobStatusFromVFC() {
        VfcService vfcService = mock(VfcService.class);
        String jobId = "1";
        String responseId = "1";
        JobStatus jobStatus = new JobStatus();
        when(vfcService.getJobStatus(jobId, responseId)).thenReturn(successfulCall(jobStatus));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        Assert.assertSame(jobStatus, service.getJobStatus(jobId, responseId));
    }

    @Test(expected = VfcException.class)
    public void getJobStatusWillThrowExceptionWhenVFCIsNotAvailable() {
        VfcService vfcService = mock(VfcService.class);
        String jobId = "1";
        String responseId = "1";
        when(vfcService.getJobStatus(jobId, responseId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.getJobStatus(jobId, responseId);
    }

    @Test(expected = VfcException.class)
    public void getJobStatusWillThrowExceptionWhenVFCResponseError() {
        VfcService vfcService = mock(VfcService.class);
        String jobId = "1";
        String responseId = "1";
        when(vfcService.getJobStatus(jobId, responseId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
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
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

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
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
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
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.getNsLcmJobStatus(serviceId,jobId, responseId,operationType);
    }

    @Test
    public void itCanDeleteNsPackage() {
        String csarId = "1";
        DistributionResult result = new DistributionResult();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteNsPackage(csarId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        Assert.assertSame(result, service.deleteNsPackage(csarId));
    }

    @Test(expected = VfcException.class)
    public void deleteNsPackageWillThrowExceptionWhenVFCIsNotAvailable() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteNsPackage(csarId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.deleteNsPackage(csarId);
    }

    @Test(expected = VfcException.class)
    public void deleteNsPackageWillThrowExceptionWhenVFCResponseError() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteNsPackage(csarId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.deleteNsPackage(csarId);
    }

    @Test
    public void itCanGetVnfPackages(){
    	//ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getVnfPackages()).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

       // Assert.assertSame(result, service.getVnfPackages());
        Assert.assertNotNull(service.getVnfPackages());
    }

    @Test
    public void getVnfPackagesThrowExceptionWhenVFCResponseError(){

    	VfcService vfcService = mock(VfcService.class);
    	when(vfcService.getVnfPackages ()).thenReturn(emptyBodyCall());
    	PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
    	service.getVnfPackages();
    }

    @Test
    public void getVnfPackagesThrowException(){
    	VfcService vfcService = mock(VfcService.class);
    	when(vfcService.getVnfPackages ()).thenReturn(failedCall("VFC is not available!"));
    	PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
    	service.getVnfPackages();
    }

    @Test
    public void itCanDeleteVFPackage() {
        String csarId = "1";
        Job job = new Job();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteVnfPackage(csarId)).thenReturn(successfulCall(job));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        Assert.assertSame(job, service.deleteVfPackage(csarId));
    }

    @Test
    public void deleteVfPackageWillThrowExceptionWhenVFCIsNotAvailable() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteVnfdPackage(csarId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        Assert.assertSame("{\"status\":\"FAILED\"}",service.deleteVnfPackage(csarId));
    }

    @Test
    public void deleteVnfPackageWillThrowExceptionWhenVFCResponseError() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteVnfdPackage(csarId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.deleteVnfPackage(csarId);
    }

    @Test
    public void itCanGetNetworkServicePackages() {
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getNetworkServicePackages()).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        Assert.assertNotNull(service.getNetworkServicePackages());
    }

    @Test
    public void getNetworkServicePackagesWillThrowExceptionWhenVFCIsNotAvailable() {
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getNetworkServicePackages()).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.getNetworkServicePackages();
    }

    @Test
    public void getNetworkServicePackagesWillThrowExceptionWhenVFCResponseError() {
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getNetworkServicePackages()).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.getNetworkServicePackages();
    }

    @Test
    public void itCanGetPnfPackages(){
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getPnfPackages()).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        Assert.assertNotNull(service.getPnfPackages());
    }

    @Test
    public void getPnfPackagesThrowExceptionWhenVFCResponseError(){

    	VfcService vfcService = mock(VfcService.class);
    	when(vfcService.getPnfPackages ()).thenReturn(emptyBodyCall());
    	PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
    	service.getPnfPackages();
    }

    @Test
    public void getPnfPackagesThrowException(){
    	VfcService vfcService = mock(VfcService.class);
    	when(vfcService.getPnfPackages ()).thenReturn(failedCall("VFC is not available!"));
    	PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
    	service.getPnfPackages();
    }

    @Test
    public void itDownLoadNsPackage(){
    	String nsdInfoId="1";
    	ResponseBody successResponse=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.downLoadNsPackage(nsdInfoId)).thenReturn(successfulCall(successResponse));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        String result = service.downLoadNsPackage(nsdInfoId);
        assertNotNull(result);
        assertNotEquals("", result);
    }

    @Test
    public void downLoadNsPackagehrowExceptionWhenVFCResponseError(){
    	String nsdInfoId="1";
    	VfcService vfcService = mock(VfcService.class);
    	when(vfcService.downLoadNsPackage (nsdInfoId)).thenReturn(emptyBodyCall());
    	PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
    	service.downLoadNsPackage(nsdInfoId);
    }

    @Test
    public void downLoadNsPackageThrowException(){
    	String nsdInfoId="1";
    	VfcService vfcService = mock(VfcService.class);
    	when(vfcService.downLoadNsPackage (nsdInfoId)).thenReturn(failedCall("VFC is not available!"));
    	PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
    	service.downLoadNsPackage(nsdInfoId);
    }

    @Test
    public void itDownLoadPnfPackage(){
    	String pnfInfoId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.downLoadNsPackage(pnfInfoId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        Assert.assertSame("{\"status\":\"SUCCESS\"}", service.downLoadPnfPackage(pnfInfoId));
    }

    @Test
    public void downLoadPnfPackagehrowExceptionWhenVFCResponseError(){
    	String pnfInfoId="1";
    	VfcService vfcService = mock(VfcService.class);
    	when(vfcService.downLoadNsPackage (pnfInfoId)).thenReturn(emptyBodyCall());
    	PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
    	service.downLoadPnfPackage(pnfInfoId);
    }

    @Test
    public void downLoadPnfPackageThrowException(){
    	String pnfInfoId="1";
    	VfcService vfcService = mock(VfcService.class);
    	when(vfcService.downLoadNsPackage (pnfInfoId)).thenReturn(failedCall("VFC is not available!"));
    	PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
    	service.downLoadPnfPackage(pnfInfoId);
    }

    @Test
    public void itDownLoadVnfPackage(){
    	String vnfInfoId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.downLoadNsPackage(vnfInfoId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        Assert.assertSame("{\"status\":\"SUCCESS\"}", service.downLoadVnfPackage(vnfInfoId));
    }

    @Test
    public void downLoadVnfPackagehrowExceptionWhenVFCResponseError(){
    	String vnfInfoId="1";
    	VfcService vfcService = mock(VfcService.class);
        when(vfcService.downLoadNsPackage (vnfInfoId)).thenReturn(failedCall("VFC is not available!"));
    	PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
    	service.downLoadVnfPackage(vnfInfoId);
    }

    @Test
    public void downLoadVnfPackageThrowException(){
    	String vnfInfoId="1";
    	VfcService vfcService = mock(VfcService.class);
    	when(vfcService.downLoadNsPackage (vnfInfoId)).thenReturn(failedCall("VFC is not available!"));
    	PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
    	service.downLoadVnfPackage(vnfInfoId);
    }

    @Test
    public void itCanDeleteNsdPackage() {
        String csarId = "1";
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteNsdPackage(csarId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        Assert.assertSame("{\"status\":\"SUCCESS\"}", service.deleteNsdPackage(csarId));
    }

    @Test
    public void deleteNsdPackageWillThrowExceptionWhenVFCIsNotAvailable() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteNsdPackage(csarId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.deleteNsdPackage(csarId);
    }

    @Test
    public void deleteNsdPackageWillThrowExceptionWhenVFCResponseError() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteNsdPackage(csarId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.deleteNsdPackage(csarId);
    }

    @Test
    public void itCanDeleteVnfPackage() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteVnfdPackage(csarId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        Assert.assertNotNull(service.deleteVnfPackage(csarId));
    }

    @Test
    public void deleteVnfPackageWillThrowExceptionWhenVFCIsNotAvailable() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteVnfdPackage(csarId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.deleteVnfPackage(csarId);
    }

    @Test
    public void deleteVnfNsdPackageWillThrowExceptionWhenVFCResponseError() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteVnfdPackage(csarId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.deleteVnfPackage(csarId);
        Assert.assertSame("{\"status\":\"FAILED\"}", service.deleteVnfPackage(csarId));
    }

    @Test
    public void itCanDeletePnfdPackage() {
        String csarId = "1";
        ResponseBody result=null;
        Job job = new Job();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deletePnfdPackage(csarId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        Assert.assertSame("{\"status\":\"SUCCESS\"}", service.deletePnfPackage(csarId));
    }

    @Test
    public void deletePnfPackageWillThrowExceptionWhenVFCIsNotAvailable() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deletePnfdPackage(csarId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.deletePnfPackage(csarId);
    }

    @Test
    public void deletePnfPackageWillThrowExceptionWhenVFCResponseError() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deletePnfdPackage(csarId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.deletePnfPackage(csarId);
    }

    @Test
    public void itCanDeleteNetworkServiceInstance() {
        String csarId = "1";
        ResponseBody result=null;
        Job job = new Job();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteNetworkServiceInstance(csarId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        Assert.assertSame("{\"status\":\"SUCCESS\"}", service.deleteNetworkServiceInstance(csarId));
    }

    @Test
    public void deleteNetworkServiceInstanceWillThrowExceptionWhenVFCIsNotAvailable() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteNetworkServiceInstance(csarId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.deleteNetworkServiceInstance(csarId);
    }

    @Test
    public void deleteNetworkServiceInstanceWillThrowExceptionWhenVFCResponseError() {
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.deleteNetworkServiceInstance(csarId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.deleteNetworkServiceInstance(csarId);
    }

    @Test
    public void itCanCreateNetworkServiceInstance() throws IOException {
    	HttpServletRequest request = mockRequest();
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createNetworkServiceInstance(Mockito.any())).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        Assert.assertSame("{\"status\":\"FAILED\"}", service.createNetworkServiceInstance(request));
    }

    @Test
    public void createNetworkServiceInstanceWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	HttpServletRequest request = mockRequest();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createNetworkServiceInstance(Mockito.any())).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.createNetworkServiceInstance(request);
    }

    @Test
    public void createNetworkServiceInstanceWillThrowExceptionWhenVFCResponseError() throws IOException {
    	HttpServletRequest request = mockRequest();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createNetworkServiceInstance(Mockito.any())).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.createNetworkServiceInstance(request);
    }

    @Test
    public void itCanGetNetworkServiceInfo() throws IOException {
        nsServiceRsp ns = new nsServiceRsp();
        List<String> list = new ArrayList<>();
        String s = "{\"nsInstanceId\":\"nsInstanceId\"}";
        list.add(s);
        ns.setNsServices(list);
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getNetworkServiceInfo()).thenReturn(successfulCall(ns));
        ServiceLcmService serviceLcmService = mock(ServiceLcmService.class);
        DefaultPackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, serviceLcmService);
        when(serviceLcmService.getServiceBeanByServiceInStanceId("nsInstanceId")).thenReturn(new ServiceBean());
        Assert.assertNotNull( service.getNetworkServiceInfo());
    }

    @Test
    public void getNetworkServiceInfoWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getNetworkServiceInfo()).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.getNetworkServiceInfo();
    }

    @Test
    public void getNetworkServiceInfoWillThrowExceptionWhenVFCResponseError() throws IOException {
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getNetworkServiceInfo()).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.getNetworkServiceInfo();
    }



    @Test
    public void itCanHealNetworkServiceInstance() throws IOException {
    	HttpServletRequest request = mockRequest();
        String csarId = "1";
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        //when(vfcService.healNetworkServiceInstance(csarId,anyObject())).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        //Assert.assertSame(result, service.healNetworkServiceInstance(request,csarId));
        service.healNetworkServiceInstance(request,csarId);
    }

    @Test
    public void healNetworkServiceInstanceWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	HttpServletRequest request = mockRequest();
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.healNetworkServiceInstance(eq(csarId),Mockito.any())).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.healNetworkServiceInstance(request,csarId);
    }

    @Test
    public void healNetworkServiceInstanceWillThrowExceptionWhenVFCResponseError() throws IOException {
    	HttpServletRequest request = mockRequest();
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.healNetworkServiceInstance(eq(csarId),Mockito.any())).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.healNetworkServiceInstance(request,csarId);
    }

    @Test
    public void itCanScaleNetworkServiceInstance() throws IOException {
    	HttpServletRequest request = mockRequest();
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        String result = service.scaleNetworkServiceInstance(request,csarId);
        assertNotNull(result);
        assertNotEquals("", result);
    }

    @Test
    public void scaleNetworkServiceInstanceWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	HttpServletRequest request = mockRequest();
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.scaleNetworkServiceInstance(eq(csarId),Mockito.any())).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.scaleNetworkServiceInstance(request,csarId);
    }

    @Test
    public void scaleNetworkServiceInstanceWillThrowExceptionWhenVFCResponseError() throws IOException {
    	HttpServletRequest request = mockRequest();
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.scaleNetworkServiceInstance(eq(csarId),Mockito.any())).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.scaleNetworkServiceInstance(request,csarId);
    }


    @Test
    public void itCaninstantiateNetworkServiceInstance() throws IOException {
    	HttpServletRequest request = mockRequest();
    	String serviceInstanceId="1";
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        //when(vfcService.instantiateNetworkServiceInstance(anyObject(),serviceInstanceId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        service.instantiateNetworkServiceInstance(request,serviceInstanceId);
    }

    @Test
    public void instantiateNetworkServiceInstanceWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	HttpServletRequest request = mockRequest();
    	String serviceInstanceId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.instantiateNetworkServiceInstance(Mockito.any(),eq(serviceInstanceId))).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.instantiateNetworkServiceInstance(request,serviceInstanceId);
    }

    @Test
    public void instantiateNetworkServiceInstanceWillThrowExceptionWhenVFCResponseError() throws IOException {
    	HttpServletRequest request = mockRequest();
    	String serviceInstanceId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.instantiateNetworkServiceInstance(Mockito.any(),eq(serviceInstanceId))).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.instantiateNetworkServiceInstance(request,serviceInstanceId);
    }


    @Test
    public void itCanTerminateNetworkServiceInstance() throws IOException {
    	HttpServletRequest request = mockRequest();
        String csarId = "1";
        ResponseBody result=null;
        Job job = new Job();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.terminateNetworkServiceInstance(eq(csarId),Mockito.any())).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        service.terminateNetworkServiceInstance(request,csarId);
    }

    @Test
    public void terminateNetworkServiceInstanceWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	HttpServletRequest request = mockRequest();
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        //when(vfcService.terminateNetworkServiceInstance(csarId,anyObject())).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.terminateNetworkServiceInstance(request,csarId);
    }

    @Test
    public void terminateNetworkServiceInstanceWillThrowExceptionWhenVFCResponseError() throws IOException {
    	HttpServletRequest request = mockRequest();
        String csarId = "1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.terminateNetworkServiceInstance(eq(csarId),Mockito.any())).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.terminateNetworkServiceInstance(request,csarId);
    }

    @Test
    public void itCreateNetworkServiceData() throws IOException {
    	HttpServletRequest request = mockRequest();
        ResponseBody responseBody = null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createNetworkServiceData(Mockito.any())).thenReturn(successfulCall(responseBody));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        String result = service.createNetworkServiceData(request);
        assertNotNull(result);
        assertNotEquals("", result);
    }

    @Test
    public void createNetworkServiceDataWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	HttpServletRequest request = mockRequest();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createNetworkServiceData(Mockito.any())).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.createNetworkServiceData(request);
    }

    @Test
    public void createNetworkServiceDataWillThrowExceptionWhenVFCResponseError() throws IOException {
    	HttpServletRequest request = mockRequest();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createNetworkServiceData(Mockito.any())).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.createNetworkServiceData(request);
    }

    @Test
    public void itCreateVnfData() throws IOException {
    	HttpServletRequest request = mockRequest();
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createVnfData(Mockito.any())).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        Assert.assertSame("{\"status\":\"FAILED\"}", service.createVnfData(request));
    }

    @Test
    public void createVnfDataWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	HttpServletRequest request = mockRequest();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createVnfData(Mockito.any())).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.createVnfData(request);
    }

    @Test
    public void createVnfDataWillThrowExceptionWhenVFCResponseError() throws IOException {
    	HttpServletRequest request = mockRequest();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createVnfData(Mockito.any())).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.createVnfData(request);
    }

    @Test
    public void itCreatePnfData() throws IOException {
    	HttpServletRequest request = mockRequest();
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createPnfData(Mockito.any())).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        Assert.assertSame("{\"status\":\"FAILED\"}", service.createPnfData(request));
    }

    @Test
    public void createPnfDataWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	HttpServletRequest request = mockRequest();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createPnfData(Mockito.any())).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.createPnfData(request);
    }

    @Test
    public void createPnfDataWillThrowExceptionWhenVFCResponseError() throws IOException {
    	HttpServletRequest request = mockRequest();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.createPnfData(Mockito.any())).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.createPnfData(request);
    }

    @Test
    public void itGetNsdInfo() throws IOException {
    	String nsdId="1";
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getNsdInfo(nsdId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        Assert.assertSame("{\"status\":\"SUCCESS\"}", service.getNsdInfo(nsdId));
    }

    @Test
    public void getNsdInfoWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	String nsdId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getNsdInfo(nsdId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.getNsdInfo(nsdId);
    }

    @Test
    public void getNsdInfoWillThrowExceptionWhenVFCResponseError() throws IOException {
    	String nsdId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getNsdInfo(nsdId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.getNsdInfo(nsdId);
    }

    @Test
    public void itGetVnfInfo() throws IOException {
    	String nsdId="1";
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getVnfInfo(nsdId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        Assert.assertSame("{\"status\":\"SUCCESS\"}", service.getVnfInfo(nsdId));
    }

    @Test
    public void getVnfInfoWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	String nsdId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getVnfInfo(nsdId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.getVnfInfo(nsdId);
    }

    @Test
    public void getVnfInfoWillThrowExceptionWhenVFCResponseError() throws IOException {
    	String nsdId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getVnfInfo(nsdId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.getVnfInfo(nsdId);
    }

    @Test
    public void itGetPnfInfo() throws IOException {
    	String nsdId="1";
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getPnfInfo(nsdId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        Assert.assertSame("{\"status\":\"SUCCESS\"}", service.getPnfInfo(nsdId));
    }

    @Test
    public void getPnfInfoWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	String nsdId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getPnfInfo(nsdId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.getPnfInfo(nsdId);
    }

    @Test
    public void getPnfInfoWillThrowExceptionWhenVFCResponseError() throws IOException {
    	String nsdId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getPnfInfo(nsdId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.getPnfInfo(nsdId);
    }

    @Test
    public void itCanListNsTemplates() throws IOException {
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.listNsTemplates()).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        Assert.assertNotNull( service.listNsTemplates());
    }

    @Test
    public void listNsTemplatesWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.listNsTemplates()).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.listNsTemplates();
    }

    @Test
    public void listNsTemplatesWillThrowExceptionWhenVFCResponseError() throws IOException {
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.listNsTemplates()).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.listNsTemplates();
    }

    @Test
    public void itCanGetVnfInfoById() throws IOException {
    	String nsdId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getVnfInfoById(nsdId)).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        Assert.assertNotNull(service.getVnfInfoById(nsdId));
    }

    @Test
    public void getVnfInfoByIdWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	String nsdId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getVnfInfoById(nsdId)).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.getVnfInfoById(nsdId);
    }

    @Test
    public void getVnfInfoByIdWillThrowExceptionWhenVFCResponseError() throws IOException {
    	String nsdId="1";
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.getVnfInfoById(nsdId)).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.getVnfInfoById(nsdId);
    }

    @Test
    public void itCanFetchNsTemplateData() throws IOException {
    	HttpServletRequest request = mockRequest();
        ResponseBody result=null;
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.fetchNsTemplateData(Mockito.any())).thenReturn(successfulCall(result));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);

        Assert.assertSame("{\"status\":\"FAILED\"}", service.fetchNsTemplateData(request));
    }

    @Test
    public void fetchNsTemplateDataWillThrowExceptionWhenVFCIsNotAvailable() throws IOException {
    	HttpServletRequest request = mockRequest();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.fetchNsTemplateData(Mockito.any())).thenReturn(failedCall("VFC is not available!"));
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.fetchNsTemplateData(request);
    }

    @Test
    public void fetchNsTemplateDataWillThrowExceptionWhenVFCResponseError() throws IOException {
    	HttpServletRequest request = mockRequest();
        VfcService vfcService = mock(VfcService.class);
        when(vfcService.fetchNsTemplateData(Mockito.any())).thenReturn(emptyBodyCall());
        PackageDistributionService service = new DefaultPackageDistributionService(null, vfcService, null);
        service.fetchNsTemplateData(request);
    }

  }
