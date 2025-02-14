/**
 * Copyright (C) 2020 Huawei, Inc. and others. All rights reserved.
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
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Options;
import okio.Sink;
import okio.Timeout;
import okio.TypedOptions;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.E2EServiceInstanceRequest;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.ModelConfig;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIClient;
import org.onap.usecaseui.server.service.lcm.domain.so.SOService;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.DeleteOperationRsp;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.ServiceOperation;
import retrofit2.Call;
import retrofit2.Callback;

import jakarta.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.onap.usecaseui.server.util.CallStub.failedCall;

public class SotnServiceTemplateServiceImplTest {

    AAIClient aaiClient;
    SOService soService;
    ServiceOperation serviceOperation;
    SotnServiceTemplateServiceImpl sotnServiceTemplateService;

    @Before
    public void before() throws Exception {
        aaiClient = mock(AAIClient.class);
        soService = mock(SOService.class);
        sotnServiceTemplateService = new SotnServiceTemplateServiceImpl();
        sotnServiceTemplateService.setSoService(soService);
        sotnServiceTemplateService.setAaiService(aaiClient);
    }

    @Test
    public void instantiate_CCVPN_ServiceTest() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("name","12");
        map.put("description","23");
        map.put("l2vpn","34");
        map.put("sotnUni","45");
        Call<ServiceOperation> call = getSosCall();
        when(soService.instantiateSOTNService(any(E2EServiceInstanceRequest.class))).thenReturn(call);
        sotnServiceTemplateService.instantiate_CCVPN_Service(map);
    }

    @Test
    public void instantiate_CCVPN_ServiceWithThrowException() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","12");
        map.put("description","23");
        map.put("l2vpn","34");
        map.put("sotnUni","45");
        Call<ServiceOperation> call = getSosCall();
        when(soService.instantiateSOTNService(any(E2EServiceInstanceRequest.class))).thenReturn(call);
        sotnServiceTemplateService.instantiate_CCVPN_Service(map);
    }

    @Test
    public void createSotnServiceTest() {
        Call<ServiceOperation> call = getSosCall();
        when(soService.instantiateSOTNService(any(E2EServiceInstanceRequest.class))).thenReturn(call);
        sotnServiceTemplateService.createSotnService(new E2EServiceInstanceRequest());
    }

    @Test
    public void createSotnServiceWithThrowException() {
        Call<ServiceOperation> call = getSosCall();
        when(soService.instantiateSOTNService(any(E2EServiceInstanceRequest.class))).thenReturn(call);
        sotnServiceTemplateService.createSotnService(new E2EServiceInstanceRequest());
    }

    @Test
    public void getServiceInstancesInfoTest() throws Exception {
        Call<ResponseBody> call = getAaiCall("ServiceInstance");
        when(aaiClient.getServiceInstancesForEdge("ISAAC","SOTN","ISAAC")).thenReturn(call);
        sotnServiceTemplateService.getServiceInstancesInfo("ISAAC","SOTN","ISAAC");
    }

    @Test
    public void getServiceInstancesInfoWithThrowException() throws Exception {
        Call<ResponseBody> call = getAaiCall("ServiceInstance");
        when(aaiClient.getServiceInstancesForEdge("ISAAC","SOTN","ISAAC")).thenReturn(call);
        sotnServiceTemplateService.getServiceInstancesInfo("ISAAC","SOTN","ISAAC");
    }

    @Test
    public void getTerminationPointTest() throws Exception {
        Call<ResponseBody> call = getAaiCall("Pinterface");
        when(aaiClient.getTerminationPoint("SOTN","123")).thenReturn(call);
        sotnServiceTemplateService.getTerminationPoint("SOTN", "123");
    }

    @Test
    public void getTerminationPointWithThrowException() throws Exception {
        Call<ResponseBody> call = getAaiCall("Pinterface");
        when(aaiClient.getTerminationPoint("SOTN","123")).thenReturn(call);
        sotnServiceTemplateService.getTerminationPoint("SOTN", "123");
    }

    @Test
    public void getSOTNPinterfaceByVpnIdTest() throws Exception {
        Call<ResponseBody> call = getAaiCall("VpnBinding");

        when(aaiClient.getPinterfaceByVpnId("1")).thenReturn(call);
        sotnServiceTemplateService.getSOTNPinterfaceByVpnId("1");
    }

    @Test
    public void getSOTNPinterfaceByVpnIdWithThrowException() throws Exception {
        Call<ResponseBody> call = getAaiCall("VpnBinding");
        when(aaiClient.getPinterfaceByVpnId("1")).thenReturn(call);
        sotnServiceTemplateService.getSOTNPinterfaceByVpnId("1");
    }

    @Test
    public void getSOTNPnfTest() throws Exception {
        Call<ResponseBody> call = getAaiCall("Pnf");
        when(aaiClient.getPnfInfo("test")).thenReturn(call);
        sotnServiceTemplateService.getSOTNPnf("test");
    }

    @Test
    public void getSOTNPnfWithThrowException() throws Exception {
        Call<ResponseBody> call = getAaiCall("Pnf");
        when(aaiClient.getPnfInfo("test")).thenReturn(call);
        sotnServiceTemplateService.getSOTNPnf("test");
    }

    @Test
    public void getSOTNLinkbyNameTest() throws Exception {
        Call<ResponseBody> call = getAaiCall("LogicalLink");
        when(aaiClient.getSpecificLogicalLink("link")).thenReturn(call);
        sotnServiceTemplateService.getSOTNLinkbyName("link");
    }

    @Test
    public void getSOTNLinkbyNameWithThrowException() throws Exception {
        Call<ResponseBody> call = getAaiCall("LogicalLink");
        when(aaiClient.getSpecificLogicalLink("link")).thenReturn(call);
        sotnServiceTemplateService.getSOTNLinkbyName("link");
    }


    @Test
    public void getUNIInfoTest() throws Exception {
        Call<ResponseBody> call = getAaiCall("Uni");
        when(aaiClient.getUNIInfo("uni-id")).thenReturn(call);
        sotnServiceTemplateService.getUNIInfo("uni-id");
    }
    @Test
    public void getUNIInfoWithThrowException() throws Exception {
        Call<ResponseBody> call = getAaiCall("Uni");
        when(aaiClient.getUNIInfo("uni-id")).thenReturn(call);
        sotnServiceTemplateService.getUNIInfo("uni-id");
    }
    @Test
    public void getVnfsTest() throws Exception {
        Call<ResponseBody> call = getAaiCall("Vnfs");
        when(aaiClient.getVNFsDetail("vnf-id")).thenReturn(call);
        sotnServiceTemplateService.getVnfs("vnf-id");
    }
    @Test
    public void getVnfsWithThrowException() throws Exception {
        Call<ResponseBody> call = getAaiCall("Vnfs");
        when(aaiClient.getVNFsDetail("vnf-id")).thenReturn(call);
        sotnServiceTemplateService.getVnfs("vnf-id");
    }
    @Test
    public void getReadFile_unniTest() throws Exception {
        ModelConfig mdl = new ModelConfig();
        sotnServiceTemplateService.readFile_unni();
    }
    @Test
    public void getReadFileTest() throws Exception {
        ModelConfig mdl = new ModelConfig();
        sotnServiceTemplateService.readFile();
    }

    // TODO: 2021/1/22
    @Test
    public void getSOTNSiteInformationTopologyTest() throws Exception {
        Call<ResponseBody> call = getAaiCall("ServiceInstance");
        Call<ResponseBody> call1 = getAaiCall("AllottedResource");
        Call<ResponseBody> call2 = getAaiCall("SiteResource");
        Call<ResponseBody> call3 = getAaiCall("Connectivity");
        Call<ResponseBody> call4 = getAaiCall("ComplexObj");
        Call<ResponseBody> call5 = getAaiCall("Pinterface");
        when(aaiClient.getServiceInstancesForEdge(anyString(),anyString(),anyString())).thenReturn(call);
        when(aaiClient.getSiteResourceInfo(anyString())).thenReturn(call2);
        when(aaiClient.getConnectivityInformation(anyString())).thenReturn(call3);
        when(aaiClient.getAllotedResourceFor5G(anyString(),anyString(),anyString(),anyString())).thenReturn(call1);
        when(aaiClient.getComplexObject(anyString())).thenReturn(call4);
        when(aaiClient.getTerminationPoint(anyString(),anyString())).thenReturn(call5);
        sotnServiceTemplateService.getSOTNSiteInformationTopology("SOTN", "ISAAC");
    }
    @Test
    public void getSOTNSiteInformationTopologyWithThrowException() throws Exception {
        Call<ResponseBody> call = getAaiCall("ServiceInstance");
        Call<ResponseBody> call1 = getAaiCall("AllottedResource");
        Call<ResponseBody> call2 = getAaiCall("SiteResource");
        Call<ResponseBody> call3 = getAaiCall("Connectivity");
        Call<ResponseBody> call4 = getAaiCall("ComplexObj");
        Call<ResponseBody> call5 = getAaiCall("Pinterface");
        when(aaiClient.getServiceInstancesForEdge(anyString(),anyString(),anyString())).thenReturn(call);
        when(aaiClient.getSiteResourceInfo(anyString())).thenReturn(call2);
        when(aaiClient.getConnectivityInformation(anyString())).thenReturn(call3);
        when(aaiClient.getAllotedResourceFor5G(anyString(),anyString(),anyString(),anyString())).thenReturn(call1);
        when(aaiClient.getComplexObject(anyString())).thenReturn(call4);
        when(aaiClient.getTerminationPoint(anyString(),anyString())).thenReturn(call5);
        sotnServiceTemplateService.getSOTNSiteInformationTopology("SOTN", "ISAAC");
    }
    @Test
    public void getServiceTest() throws Exception {
        Call<ResponseBody> call = getAaiCall("ServiceInstance");
        Call<ResponseBody> call1 = getAaiCall("AllottedResource");
        Call<ResponseBody> call2 = getAaiCall("SiteResource");
        Call<ResponseBody> call3 = getAaiCall("Connectivity");
        Call<ResponseBody> call4 = getAaiCall("ComplexObj");
        when(aaiClient.getServiceInstancesForEdge(anyString(),anyString(),anyString())).thenReturn(call);
        when(aaiClient.getSiteResourceInfo(anyString())).thenReturn(call2);
        when(aaiClient.getConnectivityInformation(anyString())).thenReturn(call3);
        when(aaiClient.getAllotedResourceFor5G(anyString(),anyString(),anyString(),anyString())).thenReturn(call1);
        when(aaiClient.getComplexObject(anyString())).thenReturn(call4);
        sotnServiceTemplateService.getService("SOTN", "ISAAC");
    }
    @Test
    public void getServiceWithThrowException() throws Exception {
        Call<ResponseBody> call = getAaiCall("ServiceInstance");
        ResponseBody result = null;
        when(aaiClient.getServiceInstancesForEdge(anyString(),anyString(),anyString())).thenReturn(call);
        ResponseBody result1 = null;
        when(aaiClient.getConnectivityInformation("1")).thenReturn(failedCall("Failed to get connectivity"));
        ResponseBody result2 = null;
        when(aaiClient.getAllotedResourceFor5G("1", "SONT", "ISAAC", "2")).thenReturn(failedCall("failed to get allocated resource"));
        sotnServiceTemplateService.getService("SOTN", "ISAAC");
    }
    @Test
    public void getSOTNServiceInformationTopologyTest() throws Exception {
        Call<ResponseBody> call = getAaiCall("ServiceInstance");
        Call<ResponseBody> call1 = getAaiCall("Uni");
        Call<ResponseBody> call2 = getAaiCall("Vnfs");
        Call<ResponseBody> call3 = getAaiCall("Connectivity");
        Call<ResponseBody> call4 = getAaiCall("Pinterface");
        Call<ResponseBody> call5 = getAaiCall("VpnBinding");
        when(aaiClient.getServiceInstancesForEdge(anyString(),anyString(),anyString())).thenReturn(call);
        when(aaiClient.getUNIInfo(anyString())).thenReturn(call1);
        when(aaiClient.getVNFsDetail(anyString())).thenReturn(call2);
        when(aaiClient.getConnectivityInformation(anyString())).thenReturn(call3);
        when(aaiClient.getTerminationPoint(anyString(),anyString())).thenReturn(call4);
        when(aaiClient.getPinterfaceByVpnId(anyString())).thenReturn(call5);
        sotnServiceTemplateService.getServiceInformationTopology("example-service-type-val-52265", "NNI-001");
    }
    @Test
    public void getSOTNServiceInformationTopologyWithThrowException() throws Exception {
        Call<ResponseBody> call = getAaiCall("ServiceInstance");
        Call<ResponseBody> call1 = getAaiCall("Uni");
        Call<ResponseBody> call2 = getAaiCall("Vnfs");
        Call<ResponseBody> call3 = getAaiCall("Connectivity");
        Call<ResponseBody> call4 = getAaiCall("Pinterface");
        Call<ResponseBody> call5 = getAaiCall("VpnBinding");
        when(aaiClient.getServiceInstancesForEdge(anyString(),anyString(),anyString())).thenReturn(call);
        when(aaiClient.getUNIInfo(anyString())).thenReturn(call1);
        when(aaiClient.getVNFsDetail(anyString())).thenReturn(call2);
        when(aaiClient.getConnectivityInformation(anyString())).thenReturn(call3);
        when(aaiClient.getTerminationPoint(anyString(),anyString())).thenReturn(call4);
        when(aaiClient.getPinterfaceByVpnId(anyString())).thenReturn(call5);
        sotnServiceTemplateService.getServiceInformationTopology("SOTN", "ISAAC");
    }


    @Test
    public void getVPNBindingInformationTopologyTest() throws Exception {
        Call<ResponseBody> call = getAaiCall("VpnBinding");
        when(aaiClient.getPinterfaceByVpnId(anyString())).thenReturn(call);
        Call<ResponseBody> call1 = getAaiCall("Pinterface");
        when(aaiClient.getTerminationPoint(anyString(),anyString())).thenReturn(call1);
        sotnServiceTemplateService.getVPNBindingInformationTopology("example-service-type-val-52265", "NNI-001", "vpn-bind-1");
    }
    @Test
    public void getVPNBindingInformationTopologyWithThrowException() throws Exception {
        Call<ResponseBody> call = getAaiCall("VpnBinding");
        when(aaiClient.getPinterfaceByVpnId(anyString())).thenReturn(call);
        Call<ResponseBody> call1 = getAaiCall("Pinterface");
        when(aaiClient.getTerminationPoint(anyString(),anyString())).thenReturn(call1);
        sotnServiceTemplateService.getVPNBindingInformationTopology("example-service-type-val-52265", "NNI-001", "vpn-bind-1");
    }

    @Test
    public void deleteServiceTest() throws Exception {
        Call<ResponseBody> aaiCall = getAaiCall("ServiceInstance");
        Call<DeleteOperationRsp> sosCall = getDeleteSosCall();
        Response result = null;
        RequestBody requestBody = null;
        when(aaiClient.getServiceInstancesForEdge(anyString(),anyString(),anyString())).thenReturn(aaiCall);
        when(soService.terminateService(anyString(),any(RequestBody.class))).thenReturn(sosCall);
        sotnServiceTemplateService.deleteService("NNI-001", "vpn-bind-1");
    }
    @Test
    public void deleteServiceWithThrowException() throws Exception {
        Call<ResponseBody> aaiCall = getAaiCall("ServiceInstance");
        Call<ServiceOperation> sosCall = getSosCall();
        Response result = null;
        RequestBody requestBody = null;
        when(aaiClient.getServiceInstancesForEdge("ISAAC", "example-service-type-val-52265", "NNI-001")).thenReturn(aaiCall);
        when(soService.terminateService("serviceId",requestBody)).thenReturn(failedCall("failed to delete the server."));
        sotnServiceTemplateService.deleteService("NNI-001", "vpn-bind-1");
    }
    @Test
    public void getNodeTest() throws Exception {
        sotnServiceTemplateService.getNode("001", "vpn-bind-1","image.png");
    }
    @Test
    public void getEdgeTest() throws Exception {
        sotnServiceTemplateService.getEdge("fromid", "toId");
    }

    @Test
    public void getSOTNResourceInformationTopologyTest() throws Exception {
        Call<ResponseBody> aaiCall = getAaiCall("ServiceInstance");
        Call<ResponseBody> aaiCall1 = getAaiCall("Connectivity");
        Call<ResponseBody> aaiCall2 = getAaiCall("Pnf");
        Call<ResponseBody> aaiCall3 = getAaiCall("Pinterface");
        Call<ResponseBody> aaiCall4 = getAaiCall("VpnBinding");
        Call<ResponseBody> aaiCall5 = getAaiCall("LogicalLink");
        when(aaiClient.getServiceInstancesForEdge(anyString(),anyString(),anyString())).thenReturn(aaiCall);
        when(aaiClient.getConnectivityInformation( anyString())).thenReturn(aaiCall1);
        when(aaiClient.getPnfInfo(anyString())).thenReturn(aaiCall2);
        when(aaiClient.getTerminationPoint(anyString(),anyString())).thenReturn(aaiCall3);
        when(aaiClient.getPinterfaceByVpnId(anyString())).thenReturn(aaiCall4);
        when(aaiClient.getSpecificLogicalLink(anyString())).thenReturn(aaiCall5);
        sotnServiceTemplateService.getSOTNResourceInformationTopology("example-service-type-val-52265", "NNI-001");
    }
    @Test
    public void getSOTNResourceInformationTopologyWithThrowException() throws Exception {
        ResponseBody result = null;
        when(aaiClient.getServiceInstancesForEdge("ISAAC", "example-service-type-val-52265", "NNI-001")).thenReturn(failedCall("failed to get sotn resource topology."));
        sotnServiceTemplateService.getSOTNResourceInformationTopology("example-service-type-val-52265", "NNI-001");
    }


    private Call<ServiceOperation> getSosCall(){
        Call<ServiceOperation> call = new Call<ServiceOperation>() {

            @Override
            public retrofit2.Response<ServiceOperation> execute() throws IOException {
                ServiceOperation serviceOperation=new ServiceOperation();
                return retrofit2.Response.success(serviceOperation);
            }

            @Override
            public void enqueue(Callback<ServiceOperation> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<ServiceOperation> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        return call;
    }
    private Call<DeleteOperationRsp> getDeleteSosCall(){
        Call<DeleteOperationRsp> call = new Call<DeleteOperationRsp>() {
            @Override
            public retrofit2.Response<DeleteOperationRsp> execute() throws IOException {
                DeleteOperationRsp deleteOperationRsp = new DeleteOperationRsp();
                return retrofit2.Response.success(deleteOperationRsp);
            }

            @Override
            public void enqueue(Callback<DeleteOperationRsp> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<DeleteOperationRsp> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        return call;
    }
    private Call<ResponseBody> getAaiCall(String t){
        Call<ResponseBody> call = new Call<ResponseBody>() {
            @Override
            public retrofit2.Response<ResponseBody> execute() throws IOException {
                ResponseBody responseBody= new ResponseBody() {
                    @Nullable
                    @Override
                    public MediaType contentType() {
                        return null;
                    }

                    @Override
                    public long contentLength() {
                        long lenth = 0;
                        // TODO: 2021/1/21 长度
                        if(t.equals("ServiceInstance")){
                            lenth = 417;
                        }else if(t.equals("Connectivity")){
                            lenth = 163;
                        }else if(t.equals("Pinterface")){
                            lenth = 326;
                        }else if(t.equals("AllottedResource")){
                            lenth = 48;
                        }else if(t.equals("SiteResource")){
                            lenth = 154;
                        }else if(t.equals("ComplexObj")){
                            lenth = 111;
                        }else if(t.equals("VpnBinding")){
                            lenth = 254;
                        }else if(t.equals("Pnf")){
                            lenth = 46;
                        }else if(t.equals("LogicalLink")){
                            lenth = 281;
                        }else if(t.equals("Uni")){
                            lenth = 221;
                        }else if(t.equals("Vnfs")){
                            lenth = 190;
                        }
                        return lenth;
                    }

                    @Override
                    public BufferedSource source() {
                        BufferedSource bufferedSource = new BufferedSource() {

                            @org.jetbrains.annotations.Nullable
                            @Override
                            public <T> T select(@NotNull TypedOptions<T> typedOptions) throws IOException {
                                return null;
                            }

                            @NotNull
                            @Override
                            public BufferedSource peek() {
                                return null;
                            }

                            @NotNull
                            @Override
                            public Buffer getBuffer() {
                                return null;
                            }

                            @Override
                            public long read(Buffer buffer, long l) throws IOException {
                                return 0;
                            }

                            @Override
                            public Timeout timeout() {
                                return null;
                            }

                            @Override
                            public void close() throws IOException {

                            }

                            @Override
                            public boolean isOpen() {
                                return false;
                            }

                            @Override
                            public int read(ByteBuffer dst) throws IOException {
                                return 0;
                            }

                            @Override
                            public Buffer buffer() {
                                return null;
                            }

                            @Override
                            public boolean exhausted() throws IOException {
                                return false;
                            }

                            @Override
                            public void require(long l) throws IOException {

                            }

                            @Override
                            public boolean request(long l) throws IOException {
                                return false;
                            }

                            @Override
                            public byte readByte() throws IOException {
                                return 0;
                            }

                            @Override
                            public short readShort() throws IOException {
                                return 0;
                            }

                            @Override
                            public short readShortLe() throws IOException {
                                return 0;
                            }

                            @Override
                            public int readInt() throws IOException {
                                return 0;
                            }

                            @Override
                            public int readIntLe() throws IOException {
                                return 0;
                            }

                            @Override
                            public long readLong() throws IOException {
                                return 0;
                            }

                            @Override
                            public long readLongLe() throws IOException {
                                return 0;
                            }

                            @Override
                            public long readDecimalLong() throws IOException {
                                return 0;
                            }

                            @Override
                            public long readHexadecimalUnsignedLong() throws IOException {
                                return 0;
                            }

                            @Override
                            public void skip(long l) throws IOException {

                            }

                            @Override
                            public ByteString readByteString() throws IOException {
                                return null;
                            }

                            @Override
                            public ByteString readByteString(long l) throws IOException {
                                return null;
                            }

                            @Override
                            public int select(Options options) throws IOException {
                                return 0;
                            }

                            @Override
                            public byte[] readByteArray() throws IOException {
                                // TODO: 2021/1/21 字符串
                                String s = new String();
                                if(t.equals("ServiceInstance")){
                                    s = "{\"service-instance-id\":\"234\",\"service-instance-name\":18,\"input-parameters\":\"as\",\"service-type\":1591851786568,\"relationship-list\":{\"relationship\":[{\"related-to\":\"service-instance\",\"related-link\":\"12/0\"},{\"related-to\":\"generic-vnf\",\"related-link\":\"12/0\"},{\"related-to\":\"allotted-resource\",\"related-link\":\"12/0\"},{\"related-to\":\"connectivity\",\"related-link\":\"12/0\"},{\"related-to\":\"site-resource\",\"related-link\":\"12/0\"}]}}";
                                }else if(t.equals("Connectivity")){
                                    s = "{\"connectivity-id\":\"234\",\"bandwidth-profile-name\":18,\"cir\":1591851786568,\"relationship-list\":{\"relationship\":[{\"related-to\":\"vpn-binding\",\"related-link\":\"12/0\"}]}}";
                                }else if(t.equals("Pinterface")){
                                    s = "{\"interface-name\":\"234\",\"speed-units\":18,\"port-description\":1591851786568," +
                                            "\"speed-value\":\"234\",\"equipment-identifier\":18,\"resource-version\":1591851786568," +
                                            "\"in-maint\":\"true\",\"network-ref\":\"23\",\"transparent\":\"34\",\"operational-status\":\"34\",\"relationship-list\":{\"relationship\":[{\"related-to\":\"logical-link\",\"related-link\":\"12/0\"}]}}";
                                }else if(t.equals("AllottedResource")){
                                    s = "{\"id\":234,\"selflink\":18,\"model-invariant-id\":12}";
                                }else if(t.equals("SiteResource")){
                                    s = "{\"site-resource-id\":\"234\",\"site-resource-name\":18,\"description\":12,\"relationship-list\":{\"relationship\":[{\"related-to\":\"complex\",\"related-link\":\"123/0\"}]}}";
                                }else if(t.equals("ComplexObj")){
                                    s = "{\"physical-location-id\":\"234\",\"resource-version\":18,\"physical-location-type\":12,\"city\":\"sd\",\"postal-code\":\"ds\"}";
                                }else if(t.equals("VpnBinding")){
                                    s = "{\"vpn-binding\":[{\"vpn-id\":23,\"src-access-node-id\":18,\"dst-access-node-id\":18,\"relationship-list\":{\"relationship\":[{\"related-to\":\"p-interface\",\"relationship-data\":[{\"relationship-key\":\"pnf.pnf-name\",\"relationship-value\":\"23\"}],\"related-link\":\"123/2\"}]}}]}";
                                }else if(t.equals("Pnf")){
                                    s = "{\"pnf-name\":\"234\",\"pnf-id\":18,\"in-maint\":true}";
                                }else if(t.equals("LogicalLink")){
                                    s = "{\"link-name\":\"234\",\"in-maint\":18,\"link-type\":true,\"relationship-list\":{\"relationship\":[{\"related-to\":\"p-interface\",\"relationship-data\":[{\"relationship-key\":\"pnf.pnf-name\",\"relationship-value\":\"26\"}],\"related-link\":\"123/0\"},{\"related-to\":\"ext-aai-network\",\"related-link\":\"123/0\"}]}}";;
                                }else if(t.equals("Uni")){
                                    s = "{\"id\":\"234\",\"tpId\":18,\"resource-version\":true,\"relationship-list\":{\"relationship\":[{\"related-to\":\"p-interface\",\"relationship-data\":[{\"relationship-key\":\"pnf.pnf-name\",\"relationship-value\":\"26\"}],\"related-link\":\"123/0\"}]}}";
                                }else if(t.equals("Vnfs")){
                                    s = "{\"vnf-id\":\"234\",\"in-maint\":18,\"resource-version\":true,\"relationship-list\":{\"relationship\":[{\"related-to\":\"connectivity\",\"related-link\":\"123/0\"},{\"related-to\":\"uni\",\"related-link\":\"123/0\"}]}}";
                                }
                                byte[] bytes = s.getBytes();
                                return bytes;
                            }

                            @Override
                            public byte[] readByteArray(long l) throws IOException {
                                return new byte[0];
                            }

                            @Override
                            public int read(byte[] bytes) throws IOException {
                                return 0;
                            }

                            @Override
                            public void readFully(byte[] bytes) throws IOException {

                            }

                            @Override
                            public int read(byte[] bytes, int i, int i1) throws IOException {
                                return 0;
                            }

                            @Override
                            public void readFully(Buffer buffer, long l) throws IOException {

                            }

                            @Override
                            public long readAll(Sink sink) throws IOException {
                                return 0;
                            }

                            @Override
                            public String readUtf8() throws IOException {
                                return null;
                            }

                            @Override
                            public String readUtf8(long l) throws IOException {
                                return null;
                            }

                            @Nullable
                            @Override
                            public String readUtf8Line() throws IOException {
                                return null;
                            }

                            @Override
                            public String readUtf8LineStrict() throws IOException {
                                return null;
                            }

                            @Override
                            public String readUtf8LineStrict(long l) throws IOException {
                                return null;
                            }

                            @Override
                            public int readUtf8CodePoint() throws IOException {
                                return 0;
                            }

                            @Override
                            public String readString(Charset charset) throws IOException {
                                return null;
                            }

                            @Override
                            public String readString(long l, Charset charset) throws IOException {
                                return null;
                            }

                            @Override
                            public long indexOf(byte b) throws IOException {
                                return 0;
                            }

                            @Override
                            public long indexOf(byte b, long l) throws IOException {
                                return 0;
                            }

                            @Override
                            public long indexOf(byte b, long l, long l1) throws IOException {
                                return 0;
                            }

                            @Override
                            public long indexOf(ByteString byteString) throws IOException {
                                return 0;
                            }

                            @Override
                            public long indexOf(ByteString byteString, long l) throws IOException {
                                return 0;
                            }

                            @Override
                            public long indexOfElement(ByteString byteString) throws IOException {
                                return 0;
                            }

                            @Override
                            public long indexOfElement(ByteString byteString, long l) throws IOException {
                                return 0;
                            }

                            @Override
                            public boolean rangeEquals(long l, ByteString byteString) throws IOException {
                                return false;
                            }

                            @Override
                            public boolean rangeEquals(long l, ByteString byteString, int i, int i1) throws IOException {
                                return false;
                            }

                            @Override
                            public InputStream inputStream() {
                                return null;
                            }
                        };
                        return bufferedSource;
                    }
                };
                return retrofit2.Response.success(200,responseBody);
            }

            @Override
            public void enqueue(Callback<ResponseBody> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<ResponseBody> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        return call;
    }
}
