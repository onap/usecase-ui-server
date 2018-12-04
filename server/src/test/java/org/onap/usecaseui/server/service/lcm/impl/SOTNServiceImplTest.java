package org.onap.usecaseui.server.service.lcm.impl;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.onap.usecaseui.server.util.CallStub.emptyBodyCall;
import static org.onap.usecaseui.server.util.CallStub.failedCall;
import static org.onap.usecaseui.server.util.CallStub.successfulCall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.sotn.Pinterface;
import org.onap.usecaseui.server.bean.sotn.PinterfaceRsp;
import org.onap.usecaseui.server.service.lcm.PackageDistributionService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;
import org.onap.usecaseui.server.service.lcm.domain.aai.exceptions.AAIException;
import org.onap.usecaseui.server.service.lcm.domain.vfc.VfcService;
import org.onap.usecaseui.server.service.lcm.domain.vfc.exceptions.VfcException;
import org.onap.usecaseui.server.service.sotn.impl.SOTNServiceImpl;

import okhttp3.ResponseBody;

public class SOTNServiceImplTest {
	
	SOTNServiceImpl dsts = null;
	@Before
	public void before() throws Exception {
		dsts = new SOTNServiceImpl();
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
    	ResponseBody result=null;
    	AAIService aaiService = mock(AAIService.class);
    	when(aaiService.listNetWorkResources()).thenReturn(successfulCall(result));
    	dsts.getNetWorkResources();
    }
    
    @Test
    public void getNetWorkResourcesWithThrowsEexception(){
    	AAIService aaiService = mock(AAIService.class);
    	when(aaiService.listNetWorkResources()).thenReturn(failedCall("aai is not exist!"));
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
    	AAIService aaiService = mock(AAIService.class);
    	when(aaiService.getPinterfaceByPnfName(pnfName)).thenReturn(successfulCall(rsp));
    	dsts.getPinterfaceByPnfName(pnfName);
    }
    
    @Test
    public void getPinterfaceByPnfNameWithThrowsEexception(){
    	AAIService aaiService = mock(AAIService.class);
    	String pnfName="test";
    	when(aaiService.getPinterfaceByPnfName(pnfName)).thenReturn(failedCall("aai is not exist!"));
    	dsts.getPinterfaceByPnfName(pnfName);
    }
    
    @Test
    public void itCanGetLogicalLinks(){
    	ResponseBody result=null;
    	AAIService aaiService = mock(AAIService.class);
    	when(aaiService.getLogicalLinks()).thenReturn(successfulCall(result));
    	dsts.getLogicalLinks();
    }
    
    @Test
    public void getLogicalLinksWithThrowsEexception(){
    	AAIService aaiService = mock(AAIService.class);
    	when(aaiService.getLogicalLinks()).thenReturn(failedCall("aai is not exist!"));
    	dsts.getLogicalLinks();
    }
    
    @Test
    public void itCanGetSpecificLogicalLink(){
    	ResponseBody result=null;
    	String linkName="linkName";
    	AAIService aaiService = mock(AAIService.class);
    	when(aaiService.getSpecificLogicalLink(linkName)).thenReturn(successfulCall(result));
    	dsts.getSpecificLogicalLink(linkName);
    }
    
    @Test
    public void getSpecificLogicalLinkWithThrowsEexception(){
    	AAIService aaiService = mock(AAIService.class);
    	String linkName="linkName";
    	when(aaiService.getSpecificLogicalLink(linkName)).thenReturn(failedCall("aai is not exist!"));
    	dsts.getSpecificLogicalLink(linkName);
    }
    
    @Test
    public void itCanGetHostUrl(){
    	ResponseBody result=null;
    	String linkName="linkName";
    	AAIService aaiService = mock(AAIService.class);
    	when(aaiService.getHostUrl(linkName)).thenReturn(successfulCall(result));
    	dsts.getHostUrl(linkName);
    }
    
    @Test
    public void getHostUrlWithThrowsEexception(){
    	AAIService aaiService = mock(AAIService.class);
    	String linkName="linkName";
    	when(aaiService.getHostUrl(linkName)).thenReturn(failedCall("aai is not exist!"));
    	dsts.getHostUrl(linkName);
    }
    
    @Test
    public void itCanGetExtAaiId(){
    	ResponseBody result=null;
    	String linkName="linkName";
    	AAIService aaiService = mock(AAIService.class);
    	when(aaiService.getExtAaiId(linkName)).thenReturn(successfulCall(result));
    	dsts.getExtAaiId(linkName);
    }
    
    @Test
    public void getExtAaiIdWithThrowsEexception(){
    	AAIService aaiService = mock(AAIService.class);
    	String linkName="linkName";
    	when(aaiService.getExtAaiId(linkName)).thenReturn(failedCall("aai is not exist!"));
    	dsts.getExtAaiId(linkName);
    }
    
    @Test
    public void itCanCreateHostUrl() throws IOException{
    	ResponseBody result=null;
    	String linkName="linkName";
    	HttpServletRequest request = mockRequest();
    	AAIService aaiService = mock(AAIService.class);
    	when(aaiService.createHostUrl(anyObject(),eq(linkName))).thenReturn(successfulCall(result));
    	Assert.assertSame(result,dsts.createHostUrl(request,linkName));
    }
    
    @Test
    public void createHostUrlWithThrowsEexception() throws IOException{
    	AAIService aaiService = mock(AAIService.class);
    	String linkName="linkName";
    	HttpServletRequest request = mockRequest();
    	when(aaiService.createHostUrl(anyObject(),eq(linkName))).thenReturn(failedCall("aai is not exist!"));
    	Assert.assertSame("",dsts.createHostUrl(request,linkName));
    }
    
    @Test
    public void itCanCreateTopoNetwork() throws IOException{
    	ResponseBody result=null;
    	String linkName="linkName";
    	HttpServletRequest request = mockRequest();
    	AAIService aaiService = mock(AAIService.class);
    	when(aaiService.createTopoNetwork(anyObject(),eq(linkName))).thenReturn(successfulCall(result));
    	Assert.assertSame(result,dsts.createTopoNetwork(request,linkName));
    }
    
    @Test
    public void createTopoNetworkWithThrowsEexception() throws IOException{
    	AAIService aaiService = mock(AAIService.class);
    	String linkName="linkName";
    	HttpServletRequest request = mockRequest();
    	when(aaiService.createTopoNetwork(anyObject(),eq(linkName))).thenReturn(failedCall("aai is not exist!"));
    	Assert.assertSame("",dsts.createTopoNetwork(request,linkName));
    }
    
    @Test
    public void itCanCreateTerminationPoint() throws IOException{
    	ResponseBody result=null;
    	String linkName="linkName";
    	String tpid="tpId";
    	HttpServletRequest request = mockRequest();
    	AAIService aaiService = mock(AAIService.class);
    	when(aaiService.createTerminationPoint(anyObject(),eq(linkName),eq(linkName))).thenReturn(successfulCall(result));
    	Assert.assertSame(result,dsts.createTerminationPoint(request,linkName,tpid));
    }
    
    @Test
    public void createTerminationPointWithThrowsEexception() throws IOException{
    	AAIService aaiService = mock(AAIService.class);
    	String linkName="linkName";
    	String tpid="tpId";
    	HttpServletRequest request = mockRequest();
    	when(aaiService.createTerminationPoint(anyObject(),eq(linkName),eq(linkName))).thenReturn(failedCall("aai is not exist!"));
    	Assert.assertSame("",dsts.createTerminationPoint(request,linkName,tpid));
    }
    
    @Test
    public void itCanCreateLink() throws IOException{
    	ResponseBody result=null;
    	String linkName="linkName";
    	HttpServletRequest request = mockRequest();
    	AAIService aaiService = mock(AAIService.class);
    	when(aaiService.createLink(anyObject(),eq(linkName))).thenReturn(successfulCall(result));
    	dsts.createLink(request,linkName);
    }
    
    @Test
    public void createLinkWithThrowsEexception() throws IOException{
    	AAIService aaiService = mock(AAIService.class);
    	String linkName="linkName";
    	HttpServletRequest request = mockRequest();
    	when(aaiService.createLink(anyObject(),eq(linkName))).thenReturn(failedCall("aai is not exist!"));
    	dsts.createLink(request,linkName);
    }
    
    @Test
    public void itCanCreatePnf() throws IOException{
    	ResponseBody result=null;
    	String linkName="linkName";
    	HttpServletRequest request = mockRequest();
    	AAIService aaiService = mock(AAIService.class);
    	when(aaiService.createPnf(anyObject(),eq(linkName))).thenReturn(successfulCall(result));
    	dsts.createPnf(request,linkName);
    }
    
    @Test
    public void createPnfWithThrowsEexception() throws IOException{
    	AAIService aaiService = mock(AAIService.class);
    	String linkName="linkName";
    	HttpServletRequest request = mockRequest();
    	when(aaiService.createPnf(anyObject(),eq(linkName))).thenReturn(failedCall("aai is not exist!"));
    	dsts.createPnf(request,linkName);
    }
    
    @Test
    public void itCanDeleteLink() throws IOException{
    	ResponseBody result=null;
    	String linkName="linkName";
    	String resourceVersion="resourceVersion";
    	AAIService aaiService = mock(AAIService.class);
    	when(aaiService.deleteLink(linkName,resourceVersion)).thenReturn(successfulCall(result));
    	dsts.deleteLink(linkName,resourceVersion);
    }
    
    @Test
    public void deleteLinkWithThrowsEexception() throws IOException{
    	AAIService aaiService = mock(AAIService.class);
    	String linkName="linkName";
    	String resourceVersion="resourceVersion";
    	when(aaiService.deleteLink(linkName,resourceVersion)).thenReturn(failedCall("aai is not exist!"));
    	dsts.deleteLink(linkName,resourceVersion);
    }
    
    @Test
    public void itCanGetServiceInstances() throws IOException{
    	ResponseBody result=null;
    	String linkName="linkName";
    	String resourceVersion="resourceVersion";
    	AAIService aaiService = mock(AAIService.class);
    	when(aaiService.getServiceInstances(linkName,resourceVersion)).thenReturn(successfulCall(result));
    	dsts.getServiceInstances(linkName,resourceVersion);
    }
    
    @Test
    public void getServiceInstancesWithThrowsEexception() throws IOException{
    	AAIService aaiService = mock(AAIService.class);
    	String linkName="linkName";
    	String resourceVersion="resourceVersion";
    	when(aaiService.getServiceInstances(linkName,resourceVersion)).thenReturn(failedCall("aai is not exist!"));
    	dsts.getServiceInstances(linkName,resourceVersion);
    }
    
    @Test
    public void itCanGerviceInstanceInfo() throws IOException{
    	ResponseBody result=null;
    	String linkName="linkName";
    	String resourceVersion="resourceVersion";
    	String serviceId="serviceId";
    	AAIService aaiService = mock(AAIService.class);
    	when(aaiService.serviceInstaneInfo(linkName,resourceVersion,serviceId)).thenReturn(successfulCall(result));
    	dsts.serviceInstanceInfo(linkName,resourceVersion,serviceId);
    }
    
    @Test
    public void serviceInstanceInfoWithThrowsEexception() throws IOException{
    	AAIService aaiService = mock(AAIService.class);
    	String linkName="linkName";
    	String resourceVersion="resourceVersion";
    	String serviceId="serviceId";
    	when(aaiService.serviceInstaneInfo(linkName,resourceVersion,serviceId)).thenReturn(failedCall("aai is not exist!"));
    	dsts.serviceInstanceInfo(linkName,resourceVersion,serviceId);
    }
    
    @Test
    public void itCanGetPnfInfo() throws IOException{
    	ResponseBody result=null;
    	String linkName="linkName";
    	AAIService aaiService = mock(AAIService.class);
    	when(aaiService.getPnfInfo(linkName)).thenReturn(successfulCall(result));
    	dsts.getPnfInfo(linkName);
    }
    
    @Test
    public void getPnfInfoWithThrowsEexception() throws IOException{
    	AAIService aaiService = mock(AAIService.class);
    	String linkName="linkName";
    	when(aaiService.getPnfInfo(linkName)).thenReturn(failedCall("aai is not exist!"));
    	dsts.getPnfInfo(linkName);
    }
    
    @Test
    public void itCangetAllottedResources() throws IOException{
    	ResponseBody result=null;
    	String linkName="linkName";
    	String resourceVersion="resourceVersion";
    	String serviceId="serviceId";
    	AAIService aaiService = mock(AAIService.class);
    	when(aaiService.getAllottedResources(linkName,resourceVersion,serviceId)).thenReturn(successfulCall(result));
    	dsts.getAllottedResources(linkName,resourceVersion,serviceId);
    }
    
    @Test
    public void getAllottedResourcesWithThrowsEexception() throws IOException{
    	AAIService aaiService = mock(AAIService.class);
    	String linkName="linkName";
    	String resourceVersion="resourceVersion";
    	String serviceId="serviceId";
    	when(aaiService.getAllottedResources(linkName,resourceVersion,serviceId)).thenReturn(failedCall("aai is not exist!"));
    	dsts.getAllottedResources(linkName,resourceVersion,serviceId);
    }
    
    @Test
    public void itCangetConnectivityInfo() throws IOException{
    	ResponseBody result=null;
    	String linkName="linkName";
    	AAIService aaiService = mock(AAIService.class);
    	when(aaiService.getConnectivityInfo(linkName)).thenReturn(successfulCall(result));
    	dsts.getConnectivityInfo(linkName);
    }
    
    @Test
    public void getConnectivityInfoWithThrowsEexception() throws IOException{
    	AAIService aaiService = mock(AAIService.class);
    	String linkName="linkName";
    	when(aaiService.getConnectivityInfo(linkName)).thenReturn(failedCall("aai is not exist!"));
    	dsts.getConnectivityInfo(linkName);
    }
    
    @Test
    public void itCangetPinterfaceByVpnId() throws IOException{
    	ResponseBody result=null;
    	String linkName="linkName";
    	AAIService aaiService = mock(AAIService.class);
    	when(aaiService.getPinterfaceByVpnId(linkName)).thenReturn(successfulCall(result));
    	dsts.getPinterfaceByVpnId(linkName);
    }
    
    @Test
    public void getPinterfaceByVpnIdWithThrowsEexception() throws IOException{
    	AAIService aaiService = mock(AAIService.class);
    	String linkName="linkName";
    	when(aaiService.getPinterfaceByVpnId(linkName)).thenReturn(failedCall("aai is not exist!"));
    	dsts.getPinterfaceByVpnId(linkName);
    }
    
    @Test
    public void itCandeleteExtNetwork() throws IOException{
    	ResponseBody result=null;
    	String linkName="linkName";
    	String resourceVersion="resourceVersion";
    	AAIService aaiService = mock(AAIService.class);
    	when(aaiService.deleteExtNetwork(linkName,resourceVersion)).thenReturn(successfulCall(result));
    	dsts.deleteExtNetwork(linkName,resourceVersion);
    }
    
    @Test
    public void deleteExtNetworkWithThrowsEexception() throws IOException{
    	AAIService aaiService = mock(AAIService.class);
    	String linkName="linkName";
    	String resourceVersion="resourceVersion";
    	when(aaiService.deleteExtNetwork(linkName,resourceVersion)).thenReturn(failedCall("aai is not exist!"));
    	dsts.deleteExtNetwork(linkName,resourceVersion);
    }
}
