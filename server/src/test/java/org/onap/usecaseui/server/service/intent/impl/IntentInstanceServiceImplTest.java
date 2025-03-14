/*
 * Copyright (C) 2021 CTC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.service.intent.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.onap.usecaseui.server.bean.csmf.ServiceCreateResult;
import org.onap.usecaseui.server.bean.csmf.SlicingOrder;
import org.onap.usecaseui.server.bean.csmf.SlicingOrderDetail;
import org.onap.usecaseui.server.bean.intent.CCVPNInstance;
import org.onap.usecaseui.server.bean.intent.InstancePerformance;
import org.onap.usecaseui.server.bean.intent.IntentInstance;
import org.onap.usecaseui.server.bean.intent.IntentModel;
import org.onap.usecaseui.server.bean.nsmf.common.ServiceResult;
import org.onap.usecaseui.server.constant.IntentConstant;
import org.onap.usecaseui.server.service.csmf.SlicingService;
import org.onap.usecaseui.server.service.intent.IntentAaiClient;
import org.onap.usecaseui.server.service.intent.IntentSoClient;
import org.onap.usecaseui.server.service.intent.config.IntentProperties;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.OperationProgress;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.OperationProgressInformation;
import org.onap.usecaseui.server.service.nsmf.ResourceMgtService;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.*;

import retrofit2.Call;
import retrofit2.Response;

import jakarta.annotation.Nullable;
import jakarta.annotation.Resource;

@RunWith(MockitoJUnitRunner.class)
public class IntentInstanceServiceImplTest {

    public IntentInstanceServiceImplTest() {
    }

    @InjectMocks
    private IntentInstanceServiceImpl intentInstanceService;

    @Mock
    private IntentAaiClient intentAaiClient;

    @Mock
    private IntentSoClient intentSoClient;

    @Mock
    private IntentProperties intentProperties;

    @Mock
    @Resource(name = "ResourceMgtService")
    private ResourceMgtService resourceMgtService;

    @Mock
    @Resource(name = "SlicingService")
    private SlicingService slicingService;

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Before
    public void before() throws Exception {
        MemberModifier.field(IntentInstanceServiceImpl.class, "sessionFactory").set(intentInstanceService , sessionFactory);
        MemberModifier.field(IntentInstanceServiceImpl.class, "resourceMgtService").set(intentInstanceService , resourceMgtService);
        MemberModifier.field(IntentInstanceServiceImpl.class, "slicingService").set(intentInstanceService , slicingService);
        MemberModifier.field(IntentInstanceServiceImpl.class, "intentAaiClient").set(intentInstanceService , intentAaiClient);
        MemberModifier.field(IntentInstanceServiceImpl.class, "intentSoClient").set(intentInstanceService , intentSoClient);
        when(sessionFactory.openSession()).thenReturn(session);

        when(intentProperties.getGlobalCustomerId()).thenReturn("someCustomer");
        when(intentProperties.getSubscriberName()).thenReturn("someSubscriber");
        when(intentProperties.getSubscriberType()).thenReturn("someSubscriberType");

    }

    @Test
    public void queryIntentInstanceTest() {
        CCVPNInstance instance = new CCVPNInstance();
        instance.setInstanceId("1");
        instance.setJobId("1");
        instance.setStatus("1");

        Query query = Mockito.mock(Query.class);
        when(session.createQuery(anyString())).thenReturn(query);
        List<IntentModel> list = new ArrayList<>();
        when(query.list()).thenReturn(list);
        when(query.uniqueResult()).thenReturn(10L);
        assertTrue(intentInstanceService.queryIntentInstance(instance,1,2).getList().isEmpty());
    }

    @Test
    public void queryIntentInstanceGetCountErrorTest() {
        CCVPNInstance instance = new CCVPNInstance();
        instance.setInstanceId("1");
        instance.setJobId("1");
        instance.setStatus("1");

        Query query = Mockito.mock(Query.class);
        when(session.createQuery(anyString())).thenReturn(query);
        List<IntentModel> list = new ArrayList<>();
        when(query.list()).thenReturn(list);
        when(query.uniqueResult()).thenReturn(10);
        assertTrue(intentInstanceService.queryIntentInstance(instance,1,2).getList().isEmpty());
    }

    @Test
    public void queryIntentInstanceThrowErrorTest() {
        CCVPNInstance instance = new CCVPNInstance();
        instance.setInstanceId("1");
        instance.setJobId("1");
        instance.setStatus("1");

        when(session.createQuery(anyString())).thenThrow(new RuntimeException());

        assertEquals(intentInstanceService.queryIntentInstance(instance,1,2), null);
    }
    @Test
    public void createCCVPNInstanceTest() throws IOException {
        CCVPNInstance instance = new CCVPNInstance();
        instance.setInstanceId("1");
        instance.setJobId("1");
        instance.setStatus("1");

        Call mockCall = PowerMockito.mock(Call.class);
        JSONObject body = JSONObject.parseObject("{\"jobId\":\"123\"}");
        Response<JSONObject> response = Response.success(body);
        Mockito.when(intentSoClient.createIntentInstance(any())).thenReturn(mockCall);
        Mockito.when(mockCall.execute()).thenReturn(response);

        IntentInstanceServiceImpl spy = PowerMockito.spy(intentInstanceService);
        doNothing().when(spy).saveIntentInstanceToAAI(isNull(),any(CCVPNInstance.class));

        Transaction tx = Mockito.mock(Transaction.class);
        Mockito.when(session.beginTransaction()).thenReturn(tx);
        Serializable save = Mockito.mock(Serializable.class);
        Mockito.when(session.save(any())).thenReturn(save);
        Mockito.doNothing().when(tx).commit();

        assertEquals(spy.createCCVPNInstance(instance), 1);
    }

    @Test
    public void createCCVPNInstanceThrowErrorTest() throws IOException {
        CCVPNInstance instance = new CCVPNInstance();
        instance.setInstanceId("1");
        instance.setJobId("1");
        instance.setStatus("1");

        Call mockCall = PowerMockito.mock(Call.class);
        JSONObject body = JSONObject.parseObject("{\"jobId\":\"123\"}");
        Response<JSONObject> response = Response.success(body);
        Mockito.when(intentSoClient.createIntentInstance(any())).thenReturn(mockCall);
        Mockito.when(mockCall.execute()).thenReturn(response);

        IntentInstanceServiceImpl spy = PowerMockito.spy(intentInstanceService);
        doThrow(new RuntimeException()).when(spy).saveIntentInstanceToAAI(isNull(),any(CCVPNInstance.class));

        Transaction tx = Mockito.mock(Transaction.class);
        Serializable save = Mockito.mock(Serializable.class);
        assertEquals(spy.createCCVPNInstance(instance), 0);
    }

    @Test
    public void createCCVPNInstanceInstanceIsNullTest() throws IOException {
        assertEquals(intentInstanceService.createCCVPNInstance(null), 0);
    }
    @Test
    public void createCCVPNInstanceInstanceJobIdIsNullTest() throws IOException {
        CCVPNInstance instance = new CCVPNInstance();
        instance.setInstanceId("1");
        instance.setStatus("1");
        assertEquals(intentInstanceService.createCCVPNInstance(instance), 0);
    }

    @Test
    public void getIntentInstanceProgressTest() throws IOException {

        Query query1 = Mockito.mock(Query.class);
        when(session.createQuery("from CCVPNInstance where deleteState = 0 and status = '0'")).thenReturn(query1);
        List<CCVPNInstance> q = new ArrayList<>();
        CCVPNInstance instance = new CCVPNInstance();
        instance.setInstanceId("1");
        instance.setResourceInstanceId("1");
        instance.setJobId("1");
        q.add(instance);
        when(query1.list()).thenReturn(q);

        OperationProgressInformation operationProgressInformation = new OperationProgressInformation();
        OperationProgress operationProgress = new OperationProgress();
        operationProgress.setProgress(100);
        operationProgressInformation.setOperationStatus(operationProgress);

        JSONObject jsonObject = new JSONObject();
        JSONObject operation = new JSONObject();
        operation.put("progress", 100);
        jsonObject.put("operation", operation);
        Call mockCall = PowerMockito.mock(Call.class);
        Response<JSONObject> response = Response.success(jsonObject);
        Mockito.when(intentSoClient.queryOperationProgress(anyString(),anyString())).thenReturn(mockCall);
        Mockito.when(mockCall.execute()).thenReturn(response);

        IntentInstanceServiceImpl spy = PowerMockito.spy(intentInstanceService);
        doNothing().when(spy).saveIntentInstanceToAAI(anyString(),any(CCVPNInstance.class));

        Transaction tx = Mockito.mock(Transaction.class);
        Mockito.when(session.beginTransaction()).thenReturn(tx);
        Serializable save = Mockito.mock(Serializable.class);
        Mockito.doNothing().when(tx).commit();

        spy.getIntentInstanceProgress();
        assertEquals(operation.getString("progress"),"100");
    }
    @Test
    public void getIntentInstanceCreateStatusTest() throws IOException {

        Query query1 = Mockito.mock(Query.class);
        when(session.createQuery("from CCVPNInstance where deleteState = 0 and status = '0'")).thenReturn(query1);
        List<CCVPNInstance> q = new ArrayList<>();
        CCVPNInstance instance = new CCVPNInstance();
        instance.setInstanceId("1");
        instance.setResourceInstanceId("1");
        instance.setJobId("1");
        q.add(instance);
        when(query1.list()).thenReturn(q);

        OperationProgressInformation operationProgressInformation = new OperationProgressInformation();
        OperationProgress operationProgress = new OperationProgress();
        operationProgress.setProgress(100);
        operationProgressInformation.setOperationStatus(operationProgress);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orchestration-status", "created");
        Call mockCall = PowerMockito.mock(Call.class);
        Response<JSONObject> response = Response.success(jsonObject);
        Mockito.when(intentAaiClient.getInstanceInfo(anyString())).thenReturn(mockCall);
        Mockito.when(mockCall.execute()).thenReturn(response);

        IntentInstanceServiceImpl spy = PowerMockito.spy(intentInstanceService);
        doNothing().when(spy).saveIntentInstanceToAAI(anyString(),any(CCVPNInstance.class));

        Transaction tx = Mockito.mock(Transaction.class);
        Mockito.when(session.beginTransaction()).thenReturn(tx);
        Serializable save = Mockito.mock(Serializable.class);
        Mockito.doNothing().when(tx).commit();

        spy.getIntentInstanceCreateStatus();
        assertEquals(jsonObject.getString("orchestration-status"),"created");
    }

    @Test
    public void getFinishedInstanceInfo() {
        Query query = Mockito.mock(Query.class);
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList());
        assertTrue(intentInstanceService.getFinishedInstanceInfo().isEmpty());
    }

    @Test
    public void getIntentInstanceBandwidth() throws IOException {
        Query query1 = Mockito.mock(Query.class);
        when(session.createQuery("from CCVPNInstance where deleteState = 0 and status = '1'")).thenReturn(query1);
        List<CCVPNInstance> q = new ArrayList<>();
        CCVPNInstance instance = new CCVPNInstance();
        instance.setInstanceId("1");
        instance.setResourceInstanceId("1");
        q.add(instance);
        when(query1.list()).thenReturn(q);

        Call mockCall = PowerMockito.mock(Call.class);
        JSONObject jsonObject = JSONObject.parseObject("{\n" +
                "    \"service-instance-id\":\"cll-101\",\n" +
                "    \"service-instance-name\":\"cloud-leased-line-101\",\n" +
                "    \"service-type\":\"CLL\",\n" +
                "    \"service-role\":\"cll\",\n" +
                "    \"environment-context\":\"cll\",\n" +
                "    \"model-invariant-id\":\"6790ab0e-034f-11eb-adc1-0242ac120002\",\n" +
                "    \"model-version-id\":\"6790ab0e-034f-11eb-adc1-0242ac120002\",\n" +
                "    \"resource-version\":\"1628714665927\",\n" +
                "    \"orchestration-status\":\"created\",\n" +
                "    \"allotted-resources\":{\n" +
                "        \"allotted-resource\":[\n" +
                "            {\n" +
                "                \"id\":\"cll-101-network-001\",\n" +
                "                \"resource-version\":\"1628714665798\",\n" +
                "                \"type\":\"TsciNetwork\",\n" +
                "                \"allotted-resource-name\":\"network_cll-101-network-001\",\n" +
                "                \"relationship-list\":{\n" +
                "                    \"relationship\":[\n" +
                "                        {\n" +
                "                            \"related-to\":\"logical-link\",\n" +
                "                            \"relationship-label\":\"org.onap.relationships.inventory.ComposedOf\",\n" +
                "                            \"related-link\":\"/aai/v24/network/logical-links/logical-link/tranportEp_UNI_ID_311_1\",\n" +
                "                            \"relationship-data\":[\n" +
                "                                {\n" +
                "                                    \"relationship-key\":\"logical-link.link-name\",\n" +
                "                                    \"relationship-value\":\"tranportEp_UNI_ID_311_1\"\n" +
                "                                }\n" +
                "                            ]\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"related-to\":\"network-policy\",\n" +
                "                            \"relationship-label\":\"org.onap.relationships.inventory.Uses\",\n" +
                "                            \"related-link\":\"/aai/v24/network/network-policies/network-policy/de00a0a0-be2e-4d19-974a-80a2bca6bdf9\",\n" +
                "                            \"relationship-data\":[\n" +
                "                                {\n" +
                "                                    \"relationship-key\":\"network-policy.network-policy-id\",\n" +
                "                                    \"relationship-value\":\"de00a0a0-be2e-4d19-974a-80a2bca6bdf9\"\n" +
                "                                }\n" +
                "                            ],\n" +
                "                            \"related-to-property\":[\n" +
                "                                {\n" +
                "                                    \"property-key\":\"network-policy.network-policy-fqdn\",\n" +
                "                                    \"property-value\":\"cll-101\"\n" +
                "                                }\n" +
                "                            ]\n" +
                "                        }\n" +
                "                    ]\n" +
                "                }\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}");
        Response<JSONObject> response = Response.success(jsonObject);
        Mockito.when(intentAaiClient.getInstanceNetworkInfo(any())).thenReturn(mockCall);
        Mockito.when(mockCall.execute()).thenReturn(response);

        Call mockCall1 = PowerMockito.mock(Call.class);
        JSONObject jsonObject1 = JSONObject.parseObject("{\n" +
                "    \"network-policy-id\":\"de00a0a0-be2e-4d19-974a-80a2bca6bdf9\",\n" +
                "    \"network-policy-fqdn\":\"cll-101\",\n" +
                "    \"resource-version\":\"1628714665619\",\n" +
                "    \"name\":\"TSCi policy\",\n" +
                "    \"type\":\"SLA\",\n" +
                "    \"latency\":2,\n" +
                "    \"max-bandwidth\":3000,\n" +
                "    \"relationship-list\":{\n" +
                "        \"relationship\":[\n" +
                "            {\n" +
                "                \"related-to\":\"allotted-resource\",\n" +
                "                \"relationship-label\":\"org.onap.relationships.inventory.Uses\",\n" +
                "                \"related-link\":\"/aai/v24/business/customers/customer/IBNCustomer/service-subscriptions/service-subscription/IBN/service-instances/service-instance/cll-101/allotted-resources/allotted-resource/cll-101-network-001\",\n" +
                "                \"relationship-data\":[\n" +
                "                    {\n" +
                "                        \"relationship-key\":\"customer.global-customer-id\",\n" +
                "                        \"relationship-value\":\"IBNCustomer\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"relationship-key\":\"service-subscription.service-type\",\n" +
                "                        \"relationship-value\":\"IBN\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"relationship-key\":\"service-instance.service-instance-id\",\n" +
                "                        \"relationship-value\":\"cll-101\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"relationship-key\":\"allotted-resource.id\",\n" +
                "                        \"relationship-value\":\"cll-101-network-001\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"related-to-property\":[\n" +
                "                    {\n" +
                "                        \"property-key\":\"allotted-resource.description\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"property-key\":\"allotted-resource.allotted-resource-name\",\n" +
                "                        \"property-value\":\"network_cll-101-network-001\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}");
        Response<JSONObject> response1 = Response.success(jsonObject1);
        Mockito.when(intentAaiClient.getInstanceNetworkPolicyInfo(any())).thenReturn(mockCall1);
        Mockito.when(mockCall1.execute()).thenReturn(response1);

        Call mockCall2 = PowerMockito.mock(Call.class);
        JSONObject jsonObject2 = JSONObject.parseObject("{\n" +
                "    \"metadatum\":[\n" +
                "        {\n" +
                "            \"metaname\":\"ethernet-uni-id-1\",\n" +
                "            \"metaval\":\"1234\",\n" +
                "            \"resource-version\":\"1629409084707\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"metaname\":\"ethernet-uni-id-2\",\n" +
                "            \"metaval\":\"5678\",\n" +
                "            \"resource-version\":\"1629409204904\"\n" +
                "        }\n" +
                "    ]\n" +
                "}");
        Response<JSONObject> response2 = Response.success(jsonObject2);
        Mockito.when(intentAaiClient.getInstanceBandwidth(any())).thenReturn(mockCall2);
        Mockito.when(mockCall2.execute()).thenReturn(response2);

        Transaction tx = Mockito.mock(Transaction.class);
        Mockito.when(session.beginTransaction()).thenReturn(tx);
        Serializable save = Mockito.mock(Serializable.class);
        Mockito.when(session.save(any())).thenReturn(save);
        Mockito.doNothing().when(tx).commit();

        intentInstanceService.getIntentInstanceBandwidth();
    }

    @Test
    public void deleteIntentInstance() throws IOException {
        CCVPNInstance instance = new CCVPNInstance();
        instance.setResourceInstanceId("1");

        Query query = Mockito.mock(Query.class);
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(instance);

        Call mockCall = PowerMockito.mock(Call.class);
        when(intentSoClient.deleteIntentInstance(any())).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(null);

        Transaction tx = PowerMockito.mock(Transaction.class);
        when(session.beginTransaction()).thenReturn(tx);
        Serializable save = PowerMockito.mock(Serializable.class);
        doNothing().when(session).delete(any());
        doNothing().when(tx).commit();

        IntentInstanceServiceImpl spy = spy(intentInstanceService);
        doNothing().when(spy).deleteIntentInstanceToAAI(anyString());

        spy.deleteIntentInstance("1");
    }


    @Test
    public void invalidIntentInstanceTest() throws IOException {
        CCVPNInstance instance = new CCVPNInstance();
        instance.setResourceInstanceId("1");

        Query query = Mockito.mock(Query.class);
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(instance);

        Call mockCall = PowerMockito.mock(Call.class);
        when(intentSoClient.deleteIntentInstance(any())).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(null);

        Transaction tx = PowerMockito.mock(Transaction.class);
        when(session.beginTransaction()).thenReturn(tx);
        doNothing().when(tx).commit();

        intentInstanceService.invalidIntentInstance("1");
    }
    @Test
    public void queryInstancePerformanceDataTest() throws IOException {
        CCVPNInstance instance = new CCVPNInstance();
        instance.setResourceInstanceId("1");

        InstancePerformance instancePerformance = new InstancePerformance();
        instancePerformance.setBandwidth(2000);
        instancePerformance.setMaxBandwidth(20000);
        instancePerformance.setDate(new Date());
        Object[] o = {null,instancePerformance};
        List<Object[]> queryResult= new ArrayList<>();
        queryResult.add(o);

        Query query = Mockito.mock(Query.class);
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.list()).thenReturn(queryResult);

        intentInstanceService.queryInstancePerformanceData("1");





        Call mockCall = PowerMockito.mock(Call.class);
        Transaction tx = PowerMockito.mock(Transaction.class);
        Serializable save = PowerMockito.mock(Serializable.class);
        intentInstanceService.invalidIntentInstance("1");
    }

    @Test
    public void activeIntentInstance() throws IOException {
        CCVPNInstance instance = new CCVPNInstance();
        instance.setInstanceId("1");
        instance.setJobId("1");
        instance.setStatus("1");

        Query query = Mockito.mock(Query.class);
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(instance);


        Call mockCall = PowerMockito.mock(Call.class);
        JSONObject body = JSONObject.parseObject("{\"jobId\":\"123\"}");
        Response<JSONObject> response = Response.success(body);
        Mockito.when(intentSoClient.createIntentInstance(any())).thenReturn(mockCall);
        Mockito.when(mockCall.execute()).thenReturn(response);

        Transaction tx = Mockito.mock(Transaction.class);
        Mockito.when(session.beginTransaction()).thenReturn(tx);
        Serializable save = Mockito.mock(Serializable.class);
        Mockito.when(session.save(any())).thenReturn(save);
        Mockito.doNothing().when(tx).commit();

        intentInstanceService.activeIntentInstance("1");

    }

    @Test
    public void queryAccessNodeInfo() throws IOException {

        Call mockCall = PowerMockito.mock(Call.class);
        JSONObject body = JSONObject.parseObject("{\n" +
                "    \"network-route\": [\n" +
                "        {\n" +
                "            \"route-id\": \"tranportEp_src_ID_111_1\",\n" +
                "            \"type\": \"LEAF\",\n" +
                "            \"role\": \"3gppTransportEP\",\n" +
                "            \"function\": \"3gppTransportEP\",\n" +
                "            \"ip-address\": \"10.2.3.4\",\n" +
                "            \"prefix-length\": 24,\n" +
                "            \"next-hop\": \"networkId-providerId-10-clientId-0-topologyId-2-nodeId-10.1.1.1-ltpId-1000\",\n" +
                "            \"address-family\": \"ipv4\",\n" +
                "            \"resource-version\": \"1634198223345\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"route-id\": \"tranportEp_src_ID_113_1\",\n" +
                "            \"type\": \"LEAF\",\n" +
                "            \"role\": \"3gppTransportEP\",\n" +
                "            \"function\": \"3gppTransportEP\",\n" +
                "            \"ip-address\": \"10.2.3.4\",\n" +
                "            \"prefix-length\": 24,\n" +
                "            \"next-hop\": \"networkId-providerId-10-clientId-0-topologyId-2-nodeId-10.1.1.3-ltpId-1000\",\n" +
                "            \"address-family\": \"ipv4\",\n" +
                "            \"resource-version\": \"1634198260496\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"route-id\": \"tranportEp_src_ID_111_2\",\n" +
                "            \"type\": \"LEAF\",\n" +
                "            \"role\": \"3gppTransportEP\",\n" +
                "            \"function\": \"3gppTransportEP\",\n" +
                "            \"ip-address\": \"10.2.3.4\",\n" +
                "            \"prefix-length\": 24,\n" +
                "            \"next-hop\": \"networkId-providerId-10-clientId-0-topologyId-2-nodeId-10.1.1.1-ltpId-2000\",\n" +
                "            \"address-family\": \"ipv4\",\n" +
                "            \"resource-version\": \"1634198251534\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"route-id\": \"tranportEp_dst_ID_212_1\",\n" +
                "            \"type\": \"ROOT\",\n" +
                "            \"role\": \"3gppTransportEP\",\n" +
                "            \"function\": \"3gppTransportEP\",\n" +
                "            \"ip-address\": \"10.2.3.4\",\n" +
                "            \"prefix-length\": 24,\n" +
                "            \"next-hop\": \"networkId-providerId-20-clientId-0-topologyId-2-nodeId-10.2.1.2-ltpId-512\",\n" +
                "            \"address-family\": \"ipv4\",\n" +
                "            \"resource-version\": \"1634198274852\"\n" +
                "        }\n" +
                "    ]\n" +
                "}");
        Response<JSONObject> response = Response.success(body);
        Mockito.when(intentAaiClient.queryNetworkRoute()).thenReturn(mockCall);
        Mockito.when(mockCall.execute()).thenReturn(response);
        Map<String, Object> result = (Map<String, Object>) intentInstanceService.queryAccessNodeInfo();
        assertEquals(((List)result.get("accessNodeList")).size(), 3);
    }

    @Test
    public void getInstanceStatusTest() {
        List<CCVPNInstance> queryResult = new ArrayList<>();
        CCVPNInstance instance = new CCVPNInstance();
        instance.setInstanceId("id1");
        instance.setStatus("1");
        queryResult.add(instance);

        Query query = Mockito.mock(Query.class);
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.list()).thenReturn(queryResult);


        JSONObject instanceStatus = intentInstanceService.getInstanceStatus(new JSONArray());
        assertEquals(instanceStatus.getJSONArray("IntentInstances").getJSONObject(0).getString("id"), "id1");
    }
    @Test
    public void formatBandwidthTest() {

        String bandwidth = intentInstanceService.formatBandwidth("2Gbps");
        assertEquals(bandwidth, "2000");
    }
    @Test
    public void formatCloudPointTest() {

        String bandwidth = intentInstanceService.formatCloudPoint("Cloud one");
        assertEquals(bandwidth, "tranportEp_dst_ID_212_1");
    }
    @Test
    public void formatAccessPointOneTest() {
        String bandwidth = intentInstanceService.formatAccessPoint("Access one");
        assertEquals(bandwidth, "tranportEp_src_ID_111_1");
    }
    @Test
    public void formatAccessPointTwoTest() {
        String bandwidth = intentInstanceService.formatAccessPoint("Access two");
        assertEquals(bandwidth, "tranportEp_src_ID_111_2");
    }
    @Test
    public void formatAccessPointThreeTest() {
        String bandwidth = intentInstanceService.formatAccessPoint("Access three");
        assertEquals(bandwidth, "tranportEp_src_ID_113_1");
    }

    @Test
    public void addCustomerTest() throws IOException {

        Call mockCall = PowerMockito.mock(Call.class);
        Response<Object> response = Response.error(404, new ResponseBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public long contentLength() {
                return 0;
            }

            @Override
            public BufferedSource source() {
                return null;
            }
        });

        when(intentAaiClient.queryCustomer(anyString())).thenReturn(mockCall);
        when(intentAaiClient.addCustomer(anyString(), any())).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(response);

        Properties properties = new Properties();
        properties.put("ccvpn.globalCustomerId", "IBNCustomer");
        properties.put("ccvpn.subscriberName", "IBNCustomer");
        properties.put("ccvpn.subscriberType", "INFRA");
        properties.put("ccvpn.serviceType", "IBN");
        IntentInstanceServiceImpl spy = spy(intentInstanceService);
        // doReturn(properties).when(spy).getProperties();

        spy.addCustomer();
        Mockito.verify(intentAaiClient,Mockito.times(1)).addCustomer(anyString(),any());
    }


    @Test
    public void addSubscriptionTest() throws IOException {

        Call mockCall = PowerMockito.mock(Call.class);
        Response<Object> response = Response.error(404, new ResponseBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public long contentLength() {
                return 0;
            }

            @Override
            public BufferedSource source() {
                return null;
            }
        });
        when(intentProperties.getServiceType()).thenReturn("someServiceType");
        when(intentAaiClient.querySubscription(anyString(),anyString())).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(response);

        IntentInstanceServiceImpl spy = spy(intentInstanceService);

        Call mockCall2 = PowerMockito.mock(Call.class);
        when(intentAaiClient.addSubscription(anyString(),anyString(),any())).thenReturn(mockCall2);

        spy.addSubscription();
        Mockito.verify(intentAaiClient,Mockito.times(1)).addSubscription(anyString(),anyString(),any());
    }

    @Test
    public void saveIntentInstanceToAAITest() throws IOException {
        when(intentProperties.getServiceType()).thenReturn("someServiceType");
        IntentInstanceServiceImpl spy = spy(intentInstanceService);
        doNothing().when(spy).addCustomer();
        doNothing().when(spy).addSubscription();

        JSONObject body = new JSONObject();
        body.put("resource-version",123);
        Call mockCall = PowerMockito.mock(Call.class);
        Response<JSONObject> response = Response.success(body);
        when(intentAaiClient.queryServiceInstance(anyString(),anyString(),anyString())).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(response);

        CCVPNInstance instance = new CCVPNInstance();
        instance.setName("name");
        instance.setInstanceId("id");

        Call mockCall2 = PowerMockito.mock(Call.class);
        Response<JSONObject> response2 = Response.success(body);
        when(intentAaiClient.saveServiceInstance(anyString(),anyString(),anyString(),any())).thenReturn(mockCall2);
        when(mockCall2.execute()).thenReturn(response2);

        spy.saveIntentInstanceToAAI("CCVPN-id",instance);
        Mockito.verify(intentAaiClient, Mockito.times(1)).saveServiceInstance(anyString(),anyString(),anyString(),any());

    }
    @Test
    public void deleteIntentInstanceToAAITest() throws IOException {
        when(intentProperties.getServiceType()).thenReturn("someServiceType");
        IntentInstanceServiceImpl spy = spy(intentInstanceService);
        doNothing().when(spy).addCustomer();
        doNothing().when(spy).addSubscription();

        JSONObject body = new JSONObject();
        body.put("resource-version",123);
        Call mockCall = PowerMockito.mock(Call.class);
        Response<JSONObject> response = Response.success(body);
        when(intentAaiClient.queryServiceInstance(anyString(),anyString(),anyString())).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(response);

        Call mockCall2 = PowerMockito.mock(Call.class);
        Response<JSONObject> response2 = Response.success(body);
        when(intentAaiClient.deleteServiceInstance(anyString(),anyString(),anyString(),anyString())).thenReturn(mockCall2);
        when(mockCall2.execute()).thenReturn(response2);

        spy.deleteIntentInstanceToAAI("CCVPN-id");
        Mockito.verify(intentAaiClient, Mockito.times(1)).deleteServiceInstance(anyString(),anyString(),anyString(),any());

    }
    @Test
    public void createIntentInstanceWithCCVPNInstanceTest() {
        Map<String, Object> body = new HashMap<>();
        body.put("intentContent", "this is intent content");
        body.put("name", "this is name");
        Transaction tx = PowerMockito.mock(Transaction.class);
        when(session.beginTransaction()).thenReturn(tx);

        Serializable save = Mockito.mock(Serializable.class);
        when(session.save(any(IntentInstance.class))).thenReturn(save);

        doNothing().when(tx).commit();
        doNothing().when(session).close();
        IntentInstance instance = intentInstanceService.createIntentInstance(body, "id", "name", IntentConstant.MODEL_TYPE_CCVPN);
        assertEquals(instance.getBusinessInstanceId(), "id");
    }
    @Test
    public void createIntentInstanceWithSlicingInstanceTest() {
        Map<String, Object> slicingOrderInfo = new HashMap<>();
        slicingOrderInfo.put("intentContent", "this is intent content");
        slicingOrderInfo.put("name", "this is name");

        Map<String, Object> body = new HashMap<>();
        body.put("slicing_order_info", slicingOrderInfo);
        Transaction tx = PowerMockito.mock(Transaction.class);
        when(session.beginTransaction()).thenReturn(tx);

        Serializable save = Mockito.mock(Serializable.class);
        when(session.save(any(IntentInstance.class))).thenReturn(save);

        doNothing().when(tx).commit();
        doNothing().when(session).close();
        IntentInstance instance = intentInstanceService.createIntentInstance(body, "id", "name", IntentConstant.MODEL_TYPE_5GS);
        assertEquals(instance.getBusinessInstanceId(), "id");
    }
    @Test
    public void createIntentInstanceWithThrowErrorTest() {
        Map<String, Object> slicingOrderInfo = new HashMap<>();
        slicingOrderInfo.put("intentContent", "this is intent content");
        slicingOrderInfo.put("name", "this is name");

        Map<String, Object> body = new HashMap<>();
        body.put("slicing_order_info", slicingOrderInfo);
        Transaction tx = PowerMockito.mock(Transaction.class);
        when(session.beginTransaction()).thenReturn(tx);

        when(session.save(any(IntentInstance.class))).thenThrow(new RuntimeException());

        doNothing().when(session).close();
        IntentInstance instance = intentInstanceService.createIntentInstance(body, "id", "name", IntentConstant.MODEL_TYPE_5GS);
        assertNull(instance);
    }

    @Test
    public void deleteIntentWithDeleteCCVPNInstanceTest() {

        IntentInstanceServiceImpl spy = spy(intentInstanceService);

        IntentInstance instance = new IntentInstance();
        instance.setId(1);
        instance.setIntentSource(IntentConstant.MODEL_TYPE_CCVPN);
        instance.setBusinessInstanceId("1");

        Query query = PowerMockito.mock(Query.class);
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter("id", 1)).thenReturn(query);
        when(query.uniqueResult()).thenReturn(instance);

        doNothing().when(spy).deleteIntentInstance(anyString());

        Transaction tx = PowerMockito.mock(Transaction.class);
        when(session.beginTransaction()).thenReturn(tx);
        doNothing().when(session).delete(any());
        doNothing().when(tx).commit();
        doNothing().when(session).close();

        spy.deleteIntent(1);

        Mockito.verify(spy, Mockito.times(1)).deleteIntentInstance("1");
    }

    @Test
    public void deleteIntentWithDeleteSlicingInstanceTest() {


        IntentInstance instance = new IntentInstance();
        instance.setId(1);
        instance.setIntentSource(IntentConstant.MODEL_TYPE_5GS);
        instance.setBusinessInstanceId("1");

        Query query = PowerMockito.mock(Query.class);
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter("id", 1)).thenReturn(query);
        when(query.uniqueResult()).thenReturn(instance);

        ServiceResult serviceResult = new ServiceResult();
        when(resourceMgtService.terminateSlicingService(anyString())).thenReturn(serviceResult);

        Transaction tx = PowerMockito.mock(Transaction.class);
        when(session.beginTransaction()).thenReturn(tx);
        doNothing().when(session).delete(any());
        doNothing().when(tx).commit();
        doNothing().when(session).close();

        intentInstanceService.deleteIntent(1);

        Mockito.verify(resourceMgtService, Mockito.times(1)).terminateSlicingService(anyString());
    }
    @Test
    public void deleteIntentWithThrowErrorTest() {


        IntentInstance instance = new IntentInstance();
        instance.setId(1);
        instance.setIntentSource(IntentConstant.MODEL_TYPE_5GS);
        instance.setBusinessInstanceId("1");

        when(session.createQuery(anyString())).thenThrow(new RuntimeException());

        doNothing().when(session).close();

        intentInstanceService.deleteIntent(1);

        Mockito.verify(resourceMgtService, Mockito.times(0)).terminateSlicingService(anyString());
    }

    @Test
    public void getIntentInstanceListTest() {
        IntentInstanceServiceImpl spy = spy(intentInstanceService);
        doReturn(2).when(spy).getIntentInstanceAllCount();

        Query query = PowerMockito.mock(Query.class);
        when(session.createQuery("from IntentInstance order by id")).thenReturn(query);
        when(query.setFirstResult(anyInt())).thenReturn(query);
        when(query.setMaxResults(anyInt())).thenReturn(query);

        List<IntentInstance> list = new ArrayList<>();
        list.add(new IntentInstance());
        list.add(new IntentInstance());
        when(query.list()).thenReturn(list);
        doNothing().when(session).close();
        int totalRecords = spy.getIntentInstanceList(1, 10).getTotalRecords();
        assertEquals(totalRecords,2);
    }

    @Test
    public void getIntentInstanceListThrowErrorTest() {
        IntentInstanceServiceImpl spy = spy(intentInstanceService);
        doReturn(2).when(spy).getIntentInstanceAllCount();

        when(session.createQuery("from IntentInstance order by id")).thenThrow(new RuntimeException());
        doNothing().when(session).close();
        assertEquals(spy.getIntentInstanceList(1, 10),null);
    }

    @Test
    public void createSlicingServiceWithIntent() throws IOException {
        IntentInstanceServiceImpl spy = spy(intentInstanceService);

        SlicingOrder slicingOrder = new SlicingOrder();
        slicingOrder.setSlicing_order_info(new SlicingOrderDetail());
        slicingOrder.getSlicing_order_info().setName("name");

        ServiceResult serviceResult = new ServiceResult();
        ServiceCreateResult serviceCreateResult = new ServiceCreateResult();
        serviceCreateResult.setService_id("id");
        serviceResult.setResult_body(serviceCreateResult);
        when(slicingService.createSlicingService(any())).thenReturn(serviceResult);
        Assert.assertThrows(RuntimeException.class,()->intentInstanceService.createSlicingServiceWithIntent(slicingOrder));
    }

    @Test
    public void getIntentInstanceAllCountTest() {

        Query query = PowerMockito.mock(Query.class);
        when(session.createQuery("select count(*) from IntentInstance")).thenReturn(query);
        when(query.uniqueResult()).thenReturn(2L);


        assertEquals(intentInstanceService.getIntentInstanceAllCount(),2);
    }

    @Test
    public void getIntentInstanceAllCountThrowErrorTest() {

        when(session.createQuery("select count(*) from IntentInstance")).thenThrow(new RuntimeException());
        assertEquals(intentInstanceService.getIntentInstanceAllCount(),-1);
    }

    @Test
    public void updateCCVPNInstanceTest() throws IOException {
        CCVPNInstance instance = new CCVPNInstance();
        instance.setInstanceId("1");
        instance.setAccessPointOneBandWidth(1);

        CCVPNInstance ccvpnInstance = new CCVPNInstance();
        ccvpnInstance.setInstanceId(instance.getInstanceId());

        Query query = mock(Query.class);
        when(session.createQuery("from CCVPNInstance where instanceId = :instanceId")).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(ccvpnInstance);

        IntentInstanceServiceImpl spy = PowerMockito.spy(intentInstanceService);
        doNothing().when(spy).saveIntentInstanceToAAI(anyString(),any(CCVPNInstance.class));

        Transaction tx = Mockito.mock(Transaction.class);
        Mockito.when(session.beginTransaction()).thenReturn(tx);
        doNothing().when(session).update(ccvpnInstance);
        Mockito.doNothing().when(tx).commit();

        assertEquals(spy.updateCCVPNInstance(instance), 1);
    }
}
