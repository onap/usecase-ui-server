/*
 * Copyright (C) 2018 CMCC, Inc. and others. All rights reserved.
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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.onap.usecaseui.server.util.CallStub.failedCall;
import static org.onap.usecaseui.server.util.CallStub.successfulCall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;

import okhttp3.MediaType;
import okio.Buffer;
import okio.BufferedSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.onap.usecaseui.server.bean.sotn.Pinterface;
import org.onap.usecaseui.server.bean.sotn.PinterfaceRsp;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIClient;
import org.onap.usecaseui.server.service.lcm.domain.aai.exceptions.AAIException;
import org.onap.usecaseui.server.service.lcm.domain.so.exceptions.SOException;
import org.onap.usecaseui.server.service.sotn.impl.SOTNServiceImpl;

import okhttp3.ResponseBody;

public class SOTNServiceImplTest {

	SOTNServiceImpl dsts = null;
	AAIClient aaiClient = null;

	ResponseBody result;
	@Before
	public void before() throws Exception {
		aaiClient= mock(AAIClient.class);
		dsts = new SOTNServiceImpl(aaiClient);
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
    public void itCanGetNetWorkResources(){
    	when(aaiClient.listNetWorkResources()).thenReturn(successfulCall(result));
    	dsts.getNetWorkResources();
    }

    @Test
    public void getNetWorkResourcesWithThrowsEexception(){
    	when(aaiClient.listNetWorkResources()).thenReturn(failedCall("aai is not exist!"));
    	dsts.getNetWorkResources();
    }

    @SuppressWarnings("unchecked")
	@Test
    public void itCanGetPinterfaceByPnfName(){
    	String pnfName="test";
    	PinterfaceRsp rsp = new PinterfaceRsp();
    	List pinterfaces = new ArrayList();
    	Pinterface pinterface = new Pinterface("interfaceName");
    	pinterfaces.add(pinterface);
    	rsp.setPinterfaces(pinterfaces);
    	when(aaiClient.getPinterfaceByPnfName(pnfName)).thenReturn(successfulCall(rsp));
    	dsts.getPinterfaceByPnfName(pnfName);
    }

    @Test(expected = AAIException.class)
    public void getPinterfaceByPnfNameWithThrowsEexception(){
    	String pnfName="test";
    	when(aaiClient.getPinterfaceByPnfName(pnfName)).thenReturn(failedCall("aai is not exist!"));
    	dsts.getPinterfaceByPnfName(pnfName);
    }

    @Test
    public void itCanGetLogicalLinks(){
    	when(aaiClient.getLogicalLinks()).thenReturn(successfulCall(result));
    	dsts.getLogicalLinks();
    }

    @Test
    public void getLogicalLinksWithThrowsEexception(){
    	when(aaiClient.getLogicalLinks()).thenReturn(failedCall("aai is not exist!"));
    	dsts.getLogicalLinks();
    }

    @Test
    public void itCanGetSpecificLogicalLink(){
    	String linkName="linkName";
    	when(aaiClient.getSpecificLogicalLink(linkName)).thenReturn(successfulCall(result));
    	dsts.getSpecificLogicalLink(linkName);
    }

    @Test
    public void getSpecificLogicalLinkWithThrowsEexception(){
    	String linkName="linkName";
    	when(aaiClient.getSpecificLogicalLink(linkName)).thenReturn(failedCall("aai is not exist!"));
    	dsts.getSpecificLogicalLink(linkName);
    }

    @Test
    public void itCanGetHostUrl(){
    	String linkName="linkName";
    	when(aaiClient.getHostUrl(linkName)).thenReturn(successfulCall(result));
    	dsts.getHostUrl(linkName);
    }

    @Test
    public void getHostUrlWithThrowsEexception(){
    	String linkName="linkName";
    	when(aaiClient.getHostUrl(linkName)).thenReturn(failedCall("aai is not exist!"));
    	dsts.getHostUrl(linkName);
    }

    @Test
    public void itCanGetExtAaiId(){
    	String linkName="linkName";
    	when(aaiClient.getExtAaiId(linkName)).thenReturn(successfulCall(result));
    	dsts.getExtAaiId(linkName);
    }

    @Test
    public void getExtAaiIdWithThrowsEexception(){
    	String linkName="linkName";
    	when(aaiClient.getExtAaiId(linkName)).thenReturn(failedCall("aai is not exist!"));
    	dsts.getExtAaiId(linkName);
    }

    @Test
    public void itCanCreateHostUrl() throws IOException{
    	String linkName="linkName";
    	HttpServletRequest request = mockRequest();
    	when(aaiClient.createHostUrl(Mockito.any(),eq(linkName))).thenReturn(successfulCall(result));
    	Assert.assertSame("{\"status\":\"SUCCESS\"}",dsts.createHostUrl(request,linkName));
    }

    @Test
    public void createHostUrlWithThrowsEexception() throws IOException{
    	String linkName="linkName";
    	HttpServletRequest request = mockRequest();
    	when(aaiClient.createHostUrl(Mockito.any(),eq(linkName))).thenReturn(failedCall("aai is not exist!"));
    	Assert.assertSame("{\"status\":\"FAILED\"}",dsts.createHostUrl(request,linkName));
    }

    @Test
    public void itCanCreateTopoNetwork() throws IOException{
    	String linkName="linkName";
    	HttpServletRequest request = mockRequest();
    	when(aaiClient.createTopoNetwork(Mockito.any(),eq(linkName))).thenReturn(successfulCall(result));
    	Assert.assertSame("{\"status\":\"SUCCESS\"}",dsts.createTopoNetwork(request,linkName));
    }

    @Test
    public void createTopoNetworkWithThrowsEexception() throws IOException{
    	String linkName="linkName";
    	HttpServletRequest request = mockRequest();
    	when(aaiClient.createTopoNetwork(Mockito.any(),eq(linkName))).thenReturn(failedCall("aai is not exist!"));
    	Assert.assertSame("{\"status\":\"FAILED\"}",dsts.createTopoNetwork(request,linkName));
    }

    @Test
    public void itCanCreateTerminationPoint() throws IOException{
    	String linkName="linkName";
    	String tpid="tpId";
    	HttpServletRequest request = mockRequest();
    	when(aaiClient.createTerminationPoint(Mockito.any(),eq(linkName),eq(linkName))).thenReturn(successfulCall(result));
    	Assert.assertSame("{\"status\":\"FAILED\"}",dsts.createTerminationPoint(request,linkName,tpid));
    }

    @Test
    public void createTerminationPointWithThrowsEexception() throws IOException{
    	String linkName="linkName";
    	String tpid="tpId";
    	HttpServletRequest request = mockRequest();
    	when(aaiClient.createTerminationPoint(Mockito.any(),eq(linkName),eq(linkName))).thenReturn(failedCall("aai is not exist!"));
    	Assert.assertSame("{\"status\":\"FAILED\"}",dsts.createTerminationPoint(request,linkName,tpid));
    }

    @Test
    public void itCanCreateLink() throws IOException{
    	String linkName="linkName";
    	HttpServletRequest request = mockRequest();
    	when(aaiClient.createLink(Mockito.any(),eq(linkName))).thenReturn(successfulCall(result));
    	dsts.createLink(request,linkName);
    }

    @Test
    public void createLinkWithThrowsEexception() throws IOException{
    	String linkName="linkName";
    	HttpServletRequest request = mockRequest();
    	when(aaiClient.createLink(Mockito.any(),eq(linkName))).thenReturn(failedCall("aai is not exist!"));
    	dsts.createLink(request,linkName);
    }

    @Test
    public void itCanCreatePnf() throws IOException{
    	String linkName="linkName";
    	HttpServletRequest request = mockRequest();
    	when(aaiClient.createPnf(Mockito.any(),eq(linkName))).thenReturn(successfulCall(result));
    	dsts.createPnf(request,linkName);
    }

    @Test
    public void createPnfWithThrowsEexception() throws IOException{
    	String linkName="linkName";
    	HttpServletRequest request = mockRequest();
    	when(aaiClient.createPnf(Mockito.any(),eq(linkName))).thenReturn(failedCall("aai is not exist!"));
    	dsts.createPnf(request,linkName);
    }

    @Test
    public void itCanDeleteLink() throws IOException{
    	String linkName="linkName";
    	String resourceVersion="resourceVersion";
    	when(aaiClient.deleteLink(linkName,resourceVersion)).thenReturn(successfulCall(result));
    	dsts.deleteLink(linkName,resourceVersion);
    }

    @Test
    public void deleteLinkWithThrowsEexception() throws IOException{
    	String linkName="linkName";
    	String resourceVersion="resourceVersion";
    	when(aaiClient.deleteLink(linkName,resourceVersion)).thenReturn(failedCall("aai is not exist!"));
    	dsts.deleteLink(linkName,resourceVersion);
    }

    @Test
    public void itCanGetServiceInstances() throws IOException{
    	String linkName="linkName";
    	String resourceVersion="resourceVersion";
    	when(aaiClient.getServiceInstances(linkName,resourceVersion)).thenReturn(successfulCall(result));
    	dsts.getServiceInstances(linkName,resourceVersion);
    }

    @Test
    public void getServiceInstancesWithThrowsEexception() throws IOException{
    	String linkName="linkName";
    	String resourceVersion="resourceVersion";
    	when(aaiClient.getServiceInstances(linkName,resourceVersion)).thenReturn(failedCall("aai is not exist!"));
    	dsts.getServiceInstances(linkName,resourceVersion);
    }

    @Test
    public void itCanGerviceInstanceInfo() throws IOException{
    	String linkName="linkName";
    	String resourceVersion="resourceVersion";
    	String serviceId="serviceId";
    	when(aaiClient.serviceInstaneInfo(linkName,resourceVersion,serviceId)).thenReturn(successfulCall(result));
    	dsts.serviceInstanceInfo(linkName,resourceVersion,serviceId);
    }

    @Test(expected = SOException.class)
    public void serviceInstanceInfoWithThrowsEexception() throws IOException{
    	String linkName="linkName";
    	String resourceVersion="resourceVersion";
    	String serviceId="serviceId";
    	when(aaiClient.serviceInstaneInfo(linkName,resourceVersion,serviceId)).thenReturn(failedCall("aai is not exist!"));
    	dsts.serviceInstanceInfo(linkName,resourceVersion,serviceId);
    }

    @Test
    public void itCanGetPnfInfo() throws IOException{
    	String linkName="linkName";
    	when(aaiClient.getPnfInfo(linkName)).thenReturn(successfulCall(result));
    	dsts.getPnfInfo(linkName);
    }

    @Test
    public void getPnfInfoWithThrowsEexception() throws IOException{
    	String linkName="linkName";
    	when(aaiClient.getPnfInfo(linkName)).thenReturn(failedCall("aai is not exist!"));
    	dsts.getPnfInfo(linkName);
    }

    @Test
    public void itCangetAllottedResources() throws IOException{
    	String linkName="linkName";
    	String resourceVersion="resourceVersion";
    	String serviceId="serviceId";
    	when(aaiClient.getAllottedResources(linkName,resourceVersion,serviceId)).thenReturn(successfulCall(result));
    	dsts.getAllottedResources(linkName,resourceVersion,serviceId);
    }

    @Test
    public void getAllottedResourcesWithThrowsEexception() throws IOException{
    	String linkName="linkName";
    	String resourceVersion="resourceVersion";
    	String serviceId="serviceId";
    	when(aaiClient.getAllottedResources(linkName,resourceVersion,serviceId)).thenReturn(failedCall("aai is not exist!"));
    	dsts.getAllottedResources(linkName,resourceVersion,serviceId);
    }

    @Test
    public void itCangetConnectivityInfo() throws IOException{
    	String linkName="linkName";
    	when(aaiClient.getConnectivityInfo(linkName)).thenReturn(successfulCall(result));
    	dsts.getConnectivityInfo(linkName);
    }

    @Test
    public void getConnectivityInfoWithThrowsEexception() throws IOException{
    	String linkName="linkName";
    	when(aaiClient.getConnectivityInfo(linkName)).thenReturn(failedCall("aai is not exist!"));
    	dsts.getConnectivityInfo(linkName);
    }

    @Test
    public void itCangetPinterfaceByVpnId() throws IOException{
    	String linkName="linkName";
    	when(aaiClient.getPinterfaceByVpnId(linkName)).thenReturn(successfulCall(result));
    	dsts.getPinterfaceByVpnId(linkName);
    }

    @Test
    public void getPinterfaceByVpnIdWithThrowsEexception() throws IOException{
    	String linkName="linkName";
    	when(aaiClient.getPinterfaceByVpnId(linkName)).thenReturn(failedCall("aai is not exist!"));
    	dsts.getPinterfaceByVpnId(linkName);
    }

    @Test
    public void itCandeleteExtNetwork() throws IOException{
    	ResponseBody result=null;
    	String linkName="linkName";
    	String resourceVersion="resourceVersion";
    	when(aaiClient.deleteExtNetwork(linkName,resourceVersion)).thenReturn(successfulCall(result));
    	dsts.deleteExtNetwork(linkName,resourceVersion);
    }

    @Test
    public void deleteExtNetworkWithThrowsEexception() throws IOException{
    	String linkName="linkName";
    	String resourceVersion="resourceVersion";
    	when(aaiClient.deleteExtNetwork(linkName,resourceVersion)).thenReturn(failedCall("aai is not exist!"));
    	dsts.deleteExtNetwork(linkName,resourceVersion);
    }
}
