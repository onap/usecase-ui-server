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

import com.alibaba.fastjson2.JSONObject;
import netscape.javascript.JSObject;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.service.lcm.PackageDistributionService;
import org.onap.usecaseui.server.service.lcm.ServiceLcmService;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.Csar;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.text.ParseException;

public class PackageDistributionControllerTest {

    private PackageDistributionService service;

    private ServiceLcmService serviceLcmService;

    private PackageDistributionController controller = new PackageDistributionController();

    @Before
    public void setUp() {
        service = mock(PackageDistributionService.class);
        serviceLcmService = mock(ServiceLcmService.class);
        controller.setPackageDistributionService(service);
        controller.setServiceLcmService(serviceLcmService);
    }
    
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
        String responseId = "1";
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("responseId")).thenReturn(responseId);
        controller.getJobStatus(jobId, request);

        verify(service, times(1)).getJobStatus(jobId, responseId);
    }

    @Test
    public void testDeleteNsPackage() {
        String csarId = "1";
        controller.deleteNsPackage(csarId);

        verify(service, times(1)).deleteNsPackage(csarId);
    }

    @Test
    public void testDeleteVfPackage() {
        String csarId = "1";
        controller.deleteVfPackage(csarId);

        verify(service, times(1)).deleteVfPackage(csarId);
    }
    
    @Test
    public void testGetNsLcmJobStatus() throws IOException {
        String jobId="1";
        HttpServletRequest request = mockRequest();
        controller.getNsLcmJobStatus(jobId,request);
        verify(service, times(1)).getNsLcmJobStatus(any(),any(),any(),any());
    }
    
    @Test
    public void testFetchNsTemplateData() throws IOException {
        HttpServletRequest request = mockRequest();
        controller.fetchNsTemplateData(request);

        verify(service, times(1)).fetchNsTemplateData(request);
    }
    
    @Test
    public void testListNsTemplates(){
        controller.listNsTemplates();

        verify(service, times(1)).listNsTemplates();
    }
    
    @Test
    public void testGetNsPackages(){
        controller.getNsPackages();

        verify(service, times(1)).getNetworkServicePackages();
    }
    
    @Test
    public void testGetVnfPackages(){
        controller.getVnfPackages();

        verify(service, times(1)).getVnfPackages();
    }
    
    @Test
    public void testGetPnfPackages(){
        controller.getPnfPackages();

        verify(service, times(1)).getPnfPackages();
    }
    
    @Test
    public void testCreateNetworkServiceData() throws IOException {
        HttpServletRequest request = mockRequest();
        controller.createNetworkServiceData(request);

        verify(service, times(1)).createNetworkServiceData(request);
    }
    
    @Test
    public void testCreateVnfData() throws IOException {
        HttpServletRequest request = mockRequest();
        controller.createVnfData(request);

        verify(service, times(1)).createVnfData(request);
    }
    
    @Test
    public void testCreatePnfData() throws IOException {
        HttpServletRequest request = mockRequest();
        controller.createPnfData(request);

        verify(service, times(1)).createPnfData(request);
    }
    
    @Test
    public void testGetNsdInfo(){
    	String nsdInfoId ="1";
    	
        controller.getNsdInfo(nsdInfoId);

        verify(service, times(1)).getNsdInfo(nsdInfoId);
    }
    
    @Test
    public void testGetVnfInfo(){
    	String nsdInfoId ="1";
    	
        controller.getVnfInfo(nsdInfoId);

        verify(service, times(1)).getVnfInfo(nsdInfoId);
    }
    
    @Test
    public void testGetPnfInfo(){
    	String nsdInfoId ="1";
    	
        controller.getPnfInfo(nsdInfoId);

        verify(service, times(1)).getPnfInfo(nsdInfoId);
    }
    
    @Test
    public void testDownLoadNsPackage(){
    	String nsdInfoId ="1";
    	
        controller.downLoadNsPackage(nsdInfoId);

        verify(service, times(1)).downLoadNsPackage(nsdInfoId);
    }
    
    @Test
    public void testDownLoadPnfPackage(){
    	String nsdInfoId ="1";
    	
        controller.downLoadPnfPackage(nsdInfoId);

        verify(service, times(1)).downLoadPnfPackage(nsdInfoId);
    }
    
    @Test
    public void testDownLoadVnfPackage(){
    	String nsdInfoId ="1";
    	
        controller.downLoadVnfPackage(nsdInfoId);

        verify(service, times(1)).downLoadVnfPackage(nsdInfoId);
    }
    
    @Test
    public void testDeleteNsdPackage(){
    	String nsdInfoId ="1";
    	
        controller.deleteNsdPackage(nsdInfoId);

        verify(service, times(1)).deleteNsdPackage(nsdInfoId);
    }
    
    @Test
    public void testDeleteVnfPackage(){
    	String nsdInfoId ="1";
    	
        controller.deleteVnfPackage(nsdInfoId);

        verify(service, times(1)).deleteVnfPackage(nsdInfoId);
    }
    
    @Test
    public void testDeletePnfPackage(){
    	String nsdInfoId ="1";
    	
        controller.deletePnfPackage(nsdInfoId);

        verify(service, times(1)).deletePnfPackage(nsdInfoId);
    }
    
    @Test
    public void testDeleteNetworkServiceInstance(){
    	String nsdInfoId ="1";
    	
        controller.deleteNetworkServiceInstance(nsdInfoId);

        verify(service, times(1)).deleteNetworkServiceInstance(nsdInfoId);
    }
    
    @Test
    public void testGetNetworkServiceInfo(){
    	
        controller.getNetworkServiceInfo();

        verify(service, times(1)).getNetworkServiceInfo();
    }
    
    @Test
    public void testCreateNetworkServiceInstance() throws IOException {
        HttpServletRequest request = mockRequest();
        controller.createNetworkServiceInstance(request);

        verify(service, times(1)).createNetworkServiceInstance(request);
    }
    
    @Test
    public void testInstantiateNetworkServiceInstance() throws IOException, ParseException {
    	String ns_instance_id="1";
        HttpServletRequest request = mockRequest();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jobId","jobId");
        when(service.instantiateNetworkServiceInstance(any(),any())).thenReturn(jsonObject.toString());
        controller.instantiateNetworkServiceInstance(request);

        verify(service, times(1)).instantiateNetworkServiceInstance(eq(request),any());
    }
    
    @Test
    public void testTerminateNetworkServiceInstance() throws Exception {
    	String ns_instance_id="1";
        HttpServletRequest request = mockRequest();
        doNothing().when( serviceLcmService).saveOrUpdateServiceInstanceOperation(any());
        controller.terminateNetworkServiceInstance(request,ns_instance_id);

        verify(service, times(1)).terminateNetworkServiceInstance(request,ns_instance_id);
    }
    
    @Test
    public void testHealNetworkServiceInstance() throws IOException, ParseException{
    	String ns_instance_id="1";
        HttpServletRequest request = mockRequest();
        controller.healNetworkServiceInstance(request,ns_instance_id);

        verify(service, times(1)).healNetworkServiceInstance(request,ns_instance_id);
    }
    
    @Test
    public void testScaleNetworkServiceInstance() throws IOException {
    	String ns_instance_id="1";
        HttpServletRequest request = mockRequest();
        controller.scaleNetworkServiceInstance(request,ns_instance_id);

        verify(service, times(1)).scaleNetworkServiceInstance(request,ns_instance_id);
    }
    	
    @Test
    public void testGetVnfInfoById() throws IOException {
    	String ns_instance_id="1";
        controller.getVnfInfoById(ns_instance_id);

        verify(service, times(1)).getVnfInfoById(ns_instance_id);
    }
}