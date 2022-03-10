/*
 * Copyright (C) 2017 CTC, Inc. and others. All rights reserved.
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

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.onap.usecaseui.server.bean.HttpResponseResult;
import org.onap.usecaseui.server.bean.intent.IntentModel;
import org.onap.usecaseui.server.constant.IntentConstant;
import org.onap.usecaseui.server.util.HttpUtil;
import org.onap.usecaseui.server.util.ZipUtil;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.powermock.api.mockito.PowerMockito.*;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ZipUtil.class, HttpUtil.class})
public class IntentServiceImplTest {
    public IntentServiceImplTest(){}


    @InjectMocks
    private IntentServiceImpl intentService;

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Before
    public void before() throws Exception {
        MemberModifier.field(IntentServiceImpl.class, "sessionFactory").set(intentService , sessionFactory);
        doReturn(session).when(sessionFactory,"openSession");
    }

    @Test
    public void addModelTest() throws Exception {
        IntentModel model = new IntentModel();
        model.setId(1);
        Transaction tx = Mockito.mock(Transaction.class);
        doReturn(tx).when(session,"beginTransaction");
        Serializable save = Mockito.mock(Serializable.class);
        Mockito.when(session.save(model)).thenReturn(save);
        Mockito.doNothing().when(tx).commit();
        Mockito.doNothing().when(session).flush();
        assertEquals(intentService.addModel(model), "1");

    }

    @Test
    public void listModelsTest() {
        Query query = Mockito.mock(Query.class);
        when(session.createQuery(anyString())).thenReturn(query);
        List<IntentModel> list = new ArrayList<>();
        when(query.list()).thenReturn(list);
        assertTrue(intentService.listModels().isEmpty());

    }

    @Test
    public void getModel() {
        Query query = Mockito.mock(Query.class);
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter("modelId", 1)).thenReturn(query);
        when(query.uniqueResult()).thenReturn(null);
        assertEquals(intentService.getModel("1"), null);

    }

    @Test
    public void deleteModel() throws Exception {
        Transaction tx = Mockito.mock(Transaction.class);
        doReturn(tx).when(session,"beginTransaction");
        Mockito.doNothing().when(session).delete(any());
        Mockito.doNothing().when(tx).commit();
        assertEquals(intentService.deleteModel("1"), "1");

    }
    @Test
    public void activeModel() throws Exception {
        Transaction tx = Mockito.mock(Transaction.class);
        doReturn(tx).when(session,"beginTransaction");

        Query query = Mockito.mock(Query.class);
        when(session.createQuery("from IntentModel where active=1")).thenReturn(query);
        List<IntentModel> list = new ArrayList<>();
        IntentModel intentModel = new IntentModel();
        intentModel.setActive(1);
        list.add(intentModel);
        when(query.list()).thenReturn(list);

        Query query2 = Mockito.mock(Query.class);
        when(session.createQuery("from IntentModel where id = :modelId")).thenReturn(query2);
        when(query2.setParameter("modelId",1)).thenReturn(query2);
        IntentModel intentModel2 = new IntentModel();
        intentModel2.setActive(0);
        when(query2.uniqueResult()).thenReturn(intentModel2);
        Serializable save = Mockito.mock(Serializable.class);
        Mockito.when(session.save(any())).thenReturn(save);

        Mockito.doNothing().when(tx).commit();
        assertEquals(intentService.activeModel("1"), intentModel2);

    }
    @Test
    public void activeModelFileModelIsNullTest() {
        assertEquals(intentService.activeModelFile(null), null);
    }
    @Test
    public void activeModelFileFilePathIsNullTest() {
        IntentModel model = new IntentModel();
        assertEquals(intentService.activeModelFile(model), null);
    }
    @Test
    public void activeModelFileFilePathIsZIPTest() {
        IntentModel model = new IntentModel();
        model.setModelName("fileName.zip");

        PowerMockito.mockStatic(HttpUtil.class);
        HttpResponseResult mock = PowerMockito.mock(HttpResponseResult.class);
        when(HttpUtil.sendGetRequest(anyString(),any(Map.class))).thenReturn(mock);
        when(mock.getResultContent()).thenReturn("OK");

        assertEquals(intentService.activeModelFile(model), "fileName");
    }


    @Test
    public void calcFieldValueValueIsNullTest() {
        assertEquals(intentService.calcFieldValue(null, null), "");
    }
    @Test
    public void calcFieldValueKeyIsResourceSharingLevelTest() {
        assertEquals(intentService.calcFieldValue("resourceSharingLevel", "shared"), "shared");
    }
    @Test
    public void calcFieldValueKeyIsUEMobilityLevelNomadicTest() {
        assertEquals(intentService.calcFieldValue("uEMobilityLevel", "Nomadic"), "nomadic");
    }
    @Test
    public void calcFieldValueKeyIsUEMobilityLevelRestrictedTest() {
        assertEquals(intentService.calcFieldValue("uEMobilityLevel", "restricted"), "Spatially Restricted Mobility");
    }
    @Test
    public void calcFieldValueKeyIsUEMobilityLevelFullyTest() {
        assertEquals(intentService.calcFieldValue("uEMobilityLevel", "fully"), "Fully Mobility");
    }
    @Test
    public void calcFieldValueKeyIsCoverageAreaTest() {
        assertEquals(intentService.calcFieldValue("coverageArea", "zhongguancun"), "Beijing;Beijing;Haidian District;Zhongguancun Street");
    }
    @Test
    public void calcFieldValueKeyIsMaxNumberofUEsTest() {
        assertEquals(intentService.calcFieldValue("maxNumberofUEs", "5"), "5");
    }
    @Test
    public void calcFieldValueKeyIsExpDataRateDLTest() {
        assertEquals(intentService.calcFieldValue("expDataRateDL", "1gb"), "1000");
    }
    @Test
    public void calcFieldValueKeyIsExpDataRateDLMBTest() {
        assertEquals(intentService.calcFieldValue("expDataRateDL", "1mbpss"), "100");
    }
    @Test
    public void calcFieldValueKeyIsExpDataRateULTest() {
        assertEquals(intentService.calcFieldValue("expDataRateUL", "1gb"), "1000");
    }
    @Test
    public void calcFieldValueKeyIsExpDataRateULMBTest() {
        assertEquals(intentService.calcFieldValue("expDataRateUL", "1mbpss"), "100");
    }
    @Test
    public void calcFieldValueKeyIsLatencyTest() {
        assertEquals(intentService.calcFieldValue("latency", "1s"), "200");
    }
    @Test
    public void calcFieldValueKeyIsLatencyDefaultTest() {
        assertEquals(intentService.calcFieldValue("latency", "default"), "200");
    }
    @Test
    public void calcFieldValueKeyIsLatencyLowTest() {
        assertEquals(intentService.calcFieldValue("latency", "low"), "10");
    }
    @Test
    public void calcFieldValueKeyIsLatencyOtherTest() {
        assertEquals(intentService.calcFieldValue("latency", "1min"), "200");
    }


    @Test
    public void formatValueForResourcesSharingLevelTest() throws InvocationTargetException, IllegalAccessException {
        String value = "shared";
        IntentServiceImpl spy = PowerMockito.spy(intentService);
        Method method = PowerMockito.method(IntentServiceImpl.class, "formatValueForResourcesSharingLevel", String.class);//如果多个参数，逗号分隔，然后写参数类型.class
        Object result = method.invoke(spy, value);
        assertEquals(result, "shared");
    }

    @Test
    public void getActiveModelTypeTest() {
        IntentModel intentModel = new IntentModel();
        intentModel.setModelType("ccvpn");
        Query query = PowerMockito.mock(Query.class);
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(intentModel);
        assertEquals(intentService.getActiveModelType(), "ccvpn");
    }
    @Test
    public void getActiveModelTypeThrowErrorTest() {
        assertEquals(intentService.getActiveModelType(), null);
    }
    @Test
    public void getModelTypeByIntentTextCCVPNTest() {
        assertEquals(intentService.getModelTypeByIntentText("Cloud"), IntentConstant.MODEL_TYPE_CCVPN);
    }
    @Test
    public void getModelTypeByIntentText5GSTest() {
        assertEquals(intentService.getModelTypeByIntentText("5gs"), IntentConstant.MODEL_TYPE_5GS);
    }
    @Test
    public void activeModelByTypeTest() {
        Transaction tx = PowerMockito.mock(Transaction.class);
        when(session.beginTransaction()).thenReturn(tx);
        Query query = PowerMockito.mock(Query.class);
        when(session.createQuery("from IntentModel where active=1")).thenReturn(query);
        IntentModel intentModel = new IntentModel();
        intentModel.setActive(1);
        List<IntentModel> list = new ArrayList<>();
        list.add(intentModel);
        when(query.list()).thenReturn(list);
        Serializable save = PowerMockito.mock(Serializable.class);
        when(session.save(intentModel)).thenReturn(save);

        Query query1 = PowerMockito.mock(Query.class);
        when(session.createQuery("from IntentModel where modelType = :modelType order by createTime desc")).thenReturn(query1);
        when(query1.setParameter("modelType", 1)).thenReturn(query1);
        List<IntentModel> list1 = new ArrayList<>();
        IntentModel intentModel1 = new IntentModel();
        intentModel1.setActive(0);
        list1.add(intentModel1);
        when(query1.list()).thenReturn(list1);
        when(session.save(intentModel1)).thenReturn(save);
        doNothing().when(tx).commit();

        IntentServiceImpl spy = spy(intentService);
        doReturn("fileName").when(spy).activeModelFile(intentModel1);
        doReturn("OK").when(spy).load(anyString());


        assertEquals(spy.activeModelByType(IntentConstant.MODEL_TYPE_CCVPN), intentModel1);
    }
    @Test
    public void loadTest() {
        PowerMockito.mockStatic(HttpUtil.class);
        HttpResponseResult result = PowerMockito.mock(HttpResponseResult.class);
        when(HttpUtil.sendPostRequestByJson(anyString(), any(), anyString())).thenReturn(result);
        when(result.getResultContent()).thenReturn("{\"Status\":\"OK\"}");
        assertEquals(intentService.load("filename"), "OK");

    }

}