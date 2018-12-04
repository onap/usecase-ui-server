package org.onap.usecaseui.server.controller.sotn;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.sotn.NetWorkResource;
import org.onap.usecaseui.server.bean.sotn.Pinterface;
import org.onap.usecaseui.server.service.sotn.SOTNService;

public class SotnControllerTest {
	
	public SOTNService sotnService;
	
	private SotnController sotnController = new SotnController();
	
	@Before
    public void setUp() {
		sotnService = mock(SOTNService.class);
		sotnController.setSotnService(sotnService);
    }
	
	@Test
	public void TestGetNetWorkResources(){
		sotnController.getNetWorkResources();
		verify(sotnService, times(1)).getNetWorkResources();
	}
	
	@Test
	public void TestGetPinterfaceByPnfName(){
		String pnfName="pnfName";
		sotnController.getPinterfaceByPnfName(pnfName);
		verify(sotnService, times(1)).getPinterfaceByPnfName(pnfName);
	}
	
	@Test
	public void TestGetLogicalLinks(){
		sotnController.getLogicalLinks();
		verify(sotnService, times(1)).getLogicalLinks();
	}
	
	@Test
	public void TestGetSpecificLogicalLink(){
		String linkName="pnfName";
		sotnController.getSpecificLogicalLink(linkName);
		verify(sotnService, times(1)).getSpecificLogicalLink(linkName);
	}
	
	@Test
	public void TestGetHostUrl(){
		String linkName="pnfName";
		sotnController.getHostUrl(linkName);
		verify(sotnService, times(1)).getHostUrl(linkName);
	}
	
	@Test
	public void TestGetExtAaiId(){
		String linkName="pnfName";
		sotnController.getExtAaiId(linkName);
		verify(sotnService, times(1)).getExtAaiId(linkName);
	}
	
	@Test
	public void TestCreateHostUrl(){
		String linkName="pnfName";
		HttpServletRequest request = mock(HttpServletRequest.class);
		sotnController.createHostUrl(request,linkName);
		verify(sotnService, times(1)).createHostUrl(request,linkName);
	}
	
	@Test
	public void TestCreateTopoNetwork(){
		String linkName="pnfName";
		HttpServletRequest request = mock(HttpServletRequest.class);
		sotnController.createTopoNetwork(request,linkName);
		verify(sotnService, times(1)).createTopoNetwork(request,linkName);
	}
	
	@Test
	public void TestCreateTerminationPoint(){
		String linkName="pnfName";
		String tpid="tpid";
		HttpServletRequest request = mock(HttpServletRequest.class);
		sotnController.createTerminationPoint(request,linkName,tpid);
		verify(sotnService, times(1)).createTerminationPoint(request,linkName,tpid);
	}
	
	@Test
	public void TestCreateLink(){
		String linkName="pnfName";
		HttpServletRequest request = mock(HttpServletRequest.class);
		sotnController.createLink(request,linkName);
		verify(sotnService, times(1)).createLink(request,linkName);
	}
	
	@Test
	public void TestCreatePnf(){
		String linkName="pnfName";
		HttpServletRequest request = mock(HttpServletRequest.class);
		sotnController.createPnf(request,linkName);
		verify(sotnService, times(1)).createPnf(request,linkName);
	}
	
	@Test
	public void TestDeleteLink(){
		String linkName="pnfName";
		String resourceVersion="resourceVersion";
		sotnController.deleteLink(linkName,resourceVersion);
		verify(sotnService, times(1)).deleteLink(linkName,resourceVersion);
	}
	
	@Test
	public void TestGetServiceInstanceInfo(){
		String linkName="pnfName";
		String resourceVersion="resourceVersion";
		String serviceType="serviceType";
		HttpServletRequest request = mock(HttpServletRequest.class);
		sotnController.getServiceInstanceInfo(request);
		verify(sotnService, times(1)).serviceInstanceInfo(linkName,resourceVersion,serviceType);
	}
	
	@Test
	public void TestGetPnfInfo(){
		String linkName="pnfName";
		sotnController.getPnfInfo(linkName);
		verify(sotnService, times(1)).getPnfInfo(linkName);
	}
	
	@Test
	public void TestGetAllottedResources(){
		String linkName="pnfName";
		String resourceVersion="resourceVersion";
		String serviceType="serviceType";
		HttpServletRequest request = mock(HttpServletRequest.class);
		sotnController.getAllottedResources(request);
		verify(sotnService, times(1)).getAllottedResources(linkName,resourceVersion,serviceType);
	}
	
	@Test
	public void TestGetConnectivityInfo(){
		String linkName="pnfName";
		sotnController.getConnectivityInfo(linkName);
		verify(sotnService, times(1)).getConnectivityInfo(linkName);
	}
	
	@Test
	public void TestGetPinterfaceByVpnId(){
		String linkName="pnfName";
		sotnController.getPinterfaceByVpnId(linkName);
		verify(sotnService, times(1)).getPinterfaceByVpnId(linkName);
	}
	
	@Test
	public void TestGetServiceInstanceList(){
		String linkName="pnfName";
		String resourceVersion="resourceVersion";
		HttpServletRequest request = mock(HttpServletRequest.class);
		sotnController.getServiceInstanceList(request);
		verify(sotnService, times(1)).getServiceInstances(linkName,resourceVersion);
	}
	
	@Test
	public void TestGetData(){
		sotnController.getData();
	}
	
	@Test
	public void TestDeleteExtNetwork(){
		String linkName="pnfName";
		String resourceVersion="resourceVersion";
		sotnController.deleteExtNetwork(linkName,resourceVersion);
		verify(sotnService, times(1)).deleteExtNetwork(linkName,resourceVersion);
	}
	
	@Test
	public void testNetWorkResource(){
		NetWorkResource netWorkResource = new NetWorkResource ();
		netWorkResource = new NetWorkResource("networkId",new ArrayList(),new ArrayList(),"aaiId");
		netWorkResource.getAaiId();
		netWorkResource.getNetworkId();
		netWorkResource.getPnfs();
		netWorkResource.getTps();
		netWorkResource.setAaiId("testaaai");
		netWorkResource.setNetworkId("testnet");
		netWorkResource.setPnfs(new ArrayList());
		netWorkResource.setTps(new ArrayList());
	}
}
