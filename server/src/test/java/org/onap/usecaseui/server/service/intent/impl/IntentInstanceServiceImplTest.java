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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.onap.usecaseui.server.bean.intent.IntentInstance;
import org.onap.usecaseui.server.bean.intent.IntentModel;
import org.onap.usecaseui.server.service.intent.IntentApiService;
import org.onap.usecaseui.server.service.lcm.domain.so.SOService;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.OperationProgress;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.OperationProgressInformation;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.when;

import retrofit2.Call;
import retrofit2.Response;

@RunWith(PowerMockRunner.class)
public class IntentInstanceServiceImplTest {

    public IntentInstanceServiceImplTest() {
    }

    @InjectMocks
    private IntentInstanceServiceImpl intentInstanceService;

    @Mock
    private IntentApiService intentApiService;

    @Mock
    private SOService soService;


    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Before
    public void before() throws Exception {
        MemberModifier.field(IntentInstanceServiceImpl.class, "sessionFactory").set(intentInstanceService , sessionFactory);
        doReturn(session).when(sessionFactory,"openSession");
    }

    @Test
    public void queryIntentInstance() {
        IntentInstance instance = new IntentInstance();
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
    public void createIntentInstance() throws IOException {
        IntentInstance instance = new IntentInstance();
        instance.setInstanceId("1");
        instance.setJobId("1");
        instance.setStatus("1");

        Call mockCall = PowerMockito.mock(Call.class);
        JSONObject body = JSONObject.parseObject("{\"jobId\":\"123\"}");
        Response<JSONObject> response = Response.success(body);
        Mockito.when(intentApiService.createIntentInstance(any())).thenReturn(mockCall);
        Mockito.when(mockCall.execute()).thenReturn(response);


        Transaction tx = Mockito.mock(Transaction.class);
        Mockito.when(session.beginTransaction()).thenReturn(tx);
        Serializable save = Mockito.mock(Serializable.class);
        Mockito.when(session.save(any())).thenReturn(save);
        Mockito.doNothing().when(tx).commit();

        assertEquals(intentInstanceService.createIntentInstance(instance), 1);
    }
    @Test
    public void getIntentInstanceProgress() throws IOException {

        Query query1 = Mockito.mock(Query.class);
        when(session.createQuery("from IntentInstance where deleteState = 0 and status = '0'")).thenReturn(query1);
        List<IntentInstance> q = new ArrayList<>();
        IntentInstance instance = new IntentInstance();
        instance.setInstanceId("1");
        q.add(instance);
        when(query1.list()).thenReturn(q);

        OperationProgressInformation operationProgressInformation = new OperationProgressInformation();
        OperationProgress operationProgress = new OperationProgress();
        operationProgress.setProgress(100);
        operationProgressInformation.setOperationStatus(operationProgress);
        Call mockCall = PowerMockito.mock(Call.class);
        Response<OperationProgressInformation> response = Response.success(operationProgressInformation);
        Mockito.when(soService.queryOperationProgress(any(),any())).thenReturn(mockCall);
        Mockito.when(mockCall.execute()).thenReturn(response);

        Transaction tx = Mockito.mock(Transaction.class);
        Mockito.when(session.beginTransaction()).thenReturn(tx);
        Serializable save = Mockito.mock(Serializable.class);
        Mockito.when(session.save(any())).thenReturn(save);
        Mockito.doNothing().when(tx).commit();

        intentInstanceService.getIntentInstanceProgress();
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
        when(session.createQuery("from IntentInstance where deleteState = 0 and status = '1'")).thenReturn(query1);
        List<IntentInstance> q = new ArrayList<>();
        IntentInstance instance = new IntentInstance();
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
        Mockito.when(intentApiService.getInstanceNetworkInfo(any())).thenReturn(mockCall);
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
        Mockito.when(intentApiService.getInstanceNetworkPolicyInfo(any())).thenReturn(mockCall1);
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
        Mockito.when(intentApiService.getInstanceBandwidth(any())).thenReturn(mockCall2);
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
        IntentInstance instance = new IntentInstance();
        instance.setResourceInstanceId("1");

        Query query = Mockito.mock(Query.class);
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(instance);

        Call mockCall = PowerMockito.mock(Call.class);
        when(intentApiService.deleteIntentInstance(any())).thenReturn(mockCall);
        Mockito.when(mockCall.execute()).thenReturn(null);

        Transaction tx = Mockito.mock(Transaction.class);
        Mockito.when(session.beginTransaction()).thenReturn(tx);
        Serializable save = Mockito.mock(Serializable.class);
        Mockito.doNothing().when(session).delete(any());
        Mockito.doNothing().when(tx).commit();

        intentInstanceService.deleteIntentInstance("1");
    }

    @Test
    public void activeIntentInstance() throws IOException {
        IntentInstance instance = new IntentInstance();
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
        Mockito.when(intentApiService.createIntentInstance(any())).thenReturn(mockCall);
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
        JSONObject body = JSONObject.parseObject("{\"data\":[{\"type\":\"ROOT\",\"route-id\":\"route1\"},{\"type\":\"route\",\"route-id\":\"route2\"}]}");
        Response<JSONObject> response = Response.success(body);
        Mockito.when(intentApiService.queryNetworkRoute()).thenReturn(mockCall);
        Mockito.when(mockCall.execute()).thenReturn(response);
        Map<String, Object> result = (Map<String, Object>) intentInstanceService.queryAccessNodeInfo();
        assertEquals(((List)result.get("accessNodeList")).size(), 1);
    }
}