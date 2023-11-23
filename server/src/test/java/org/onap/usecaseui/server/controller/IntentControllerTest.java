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
package org.onap.usecaseui.server.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.onap.usecaseui.server.bean.HttpResponseResult;
import org.onap.usecaseui.server.bean.intent.CCVPNInstance;
import org.onap.usecaseui.server.bean.intent.IntentModel;
import org.onap.usecaseui.server.service.intent.IntentInstanceService;
import org.onap.usecaseui.server.service.intent.impl.IntentServiceImpl;
import org.onap.usecaseui.server.util.HttpUtil;
import org.onap.usecaseui.server.util.Page;
import org.onap.usecaseui.server.util.UploadFileUtil;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class IntentControllerTest {

    public IntentControllerTest(){}

    @InjectMocks
    private IntentController intentController;

    @Mock
    @Resource(name = "IntentService")
    private IntentServiceImpl intentService;

    @InjectMocks
    private IntentServiceImpl intentService1;

    @Mock
    private IntentInstanceService intentInstanceService;

    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;

    @Before
    public void before() throws IllegalAccessException {
        MemberModifier.field(IntentController.class, "intentService").set(intentController , intentService);
        MemberModifier.field(IntentController.class, "intentInstanceService").set(intentController , intentInstanceService);
        MemberModifier.field(IntentServiceImpl.class, "sessionFactory").set(intentService1 , sessionFactory);
    }

    @BeforeClass
    public static void init(){
        Mockito.mockStatic(UploadFileUtil.class);
        Mockito.mockStatic(HttpUtil.class,"IntentControllerTest");
    }

    @Test
    public void uploadModelTest() throws Exception {
        MultipartFile file=PowerMockito.mock(MultipartFile.class);
        PowerMockito.when(file.getOriginalFilename()).thenReturn("filename.zip");
        IntentController spy = PowerMockito.spy(intentController);
        File dest=PowerMockito.mock(File.class);
        when(spy.newFile(anyString())).thenReturn(dest);
        File parent=PowerMockito.mock(File.class);
        when(dest.getParentFile()).thenReturn(parent);
        when(parent.mkdirs()).thenReturn(true);
        doNothing().when(file).transferTo(dest);
        when(dest.length()).thenReturn(1024L);
        when(UploadFileUtil.formUpload(anyString(), any(Map.class), any(Map.class),anyString())).thenReturn("ok");
        when(intentService.addModel(any(IntentModel.class))).thenReturn("1");
        assertEquals(spy.uploadModel(file, "5gs"), "1");
    }
    @Test
    public void uploadModelTestThrowError() throws Exception {
        MultipartFile file=PowerMockito.mock(MultipartFile.class);
        PowerMockito.when(file.getOriginalFilename()).thenReturn("filename.zip");
        IntentController spy = PowerMockito.spy(intentController);
        File dest=PowerMockito.mock(File.class);
        when(spy.newFile(anyString())).thenReturn(dest);
        File parent=PowerMockito.mock(File.class);
        when(dest.getParentFile()).thenReturn(parent);
        when(parent.mkdirs()).thenReturn(true);
        doThrow(new RuntimeException()).when(file).transferTo(dest);

        assertEquals(spy.uploadModel(file, "5gs"), "0");

    }


    @Test
    public void activeModelTest() {
        IntentModel model = new IntentModel();
        String path = "path";
        String modelId = "1";
        when(intentService.activeModel(anyString())).thenReturn(model);
        when(intentService.activeModelFile(model)).thenReturn(path);

        HttpResponseResult mock = PowerMockito.mock(HttpResponseResult.class);
        Mockito.when(HttpUtil.sendPostRequestByJson(anyString(), any(), anyString())).thenReturn(mock);
        assertEquals(intentController.activeModel(modelId), "1");
    }

    @Test
    public void deleteModelTest() throws Exception {
        String modelId = "1";
        IntentModel model = new IntentModel();
        model.setModelName("filename.zip");
        when(intentService.getModel(eq(modelId))).thenReturn(model);
        when(intentService.deleteModel(anyString())).thenReturn("1");

        File file=PowerMockito.mock(File.class);
        IntentController spy = PowerMockito.spy(intentController);
        when(spy.newFile(anyString())).thenReturn(file);
        PowerMockito.when(file.exists()).thenReturn(true);
        PowerMockito.when(file.delete()).thenReturn(true);

        HttpResponseResult mock = PowerMockito.mock(HttpResponseResult.class);
        when(HttpUtil.sendGetRequest(anyString(), any(Map.class))).thenReturn(mock);
        when(mock.getResultContent()).thenReturn("{}");

        assertEquals(spy.deleteModel(modelId), "1");

    }

    @Test
    public void predictTest() throws ParseException {
        Map<String,Object> body = new HashMap<>();
        body.put("text", "text");
        body.put("modelType", "5gs");
        String respContent = "";
        HttpResponseResult mock = PowerMockito.mock(HttpResponseResult.class);
        Mockito.when(HttpUtil.sendPostRequestByJson(anyString(), any(Map.class), anyString())).thenReturn(mock);
        when(mock.getResultContent()).thenReturn("{'Area':'chengnan'}");
        when(intentService.calcFieldValue(anyString(), anyString())).thenReturn("Beijing Changping District Chengnan Street");
        when(intentService.getActiveModelType()).thenReturn("5gs");
        Map<String, Object> predict = intentController.predict(body);
        JSONObject jsonObject = new JSONObject(predict);

        assertEquals(jsonObject.getString("coverageArea"), "Beijing Changping District Chengnan Street");
    }

    @Test
    public void unifyPredict_5gs_Test() throws ParseException {
        Map<String,Object> body = new HashMap<>();
        body.put("text", "Service");
        String respContent = "";
        when(intentService.getModelTypeByIntentText(anyString())).thenReturn("5gs");
        when(intentService.getActiveModelType()).thenReturn("5gs");

        HttpResponseResult mock = PowerMockito.mock(HttpResponseResult.class);
        Mockito.when(HttpUtil.sendPostRequestByJson(anyString(), any(Map.class), anyString())).thenReturn(mock);
        when(mock.getResultContent()).thenReturn("{'Area':'chengnan'}");
        when(intentService.calcFieldValue(anyString(), anyString())).thenReturn("Beijing Changping District Chengnan Street");
        Map<String, Object> predict = intentController.unifyPredict(body);
        JSONObject jsonObject = new JSONObject(predict);

        assertEquals(jsonObject.getString("type"), "5gs");
        assertEquals(jsonObject.getJSONObject("formData").getString("coverageArea"), "Beijing Changping District Chengnan Street");
    }
    @Test
    public void unifyPredict_ccvpn_Test() throws ParseException {
        Map<String,Object> body = new HashMap<>();
        body.put("text", "I need create a Cloud Leased Line, I need a line from Access two to Cloud one, 20Gbps");
        String respContent = "";
        when(intentService.getModelTypeByIntentText(anyString())).thenReturn("ccvpn");
        when(intentService.getActiveModelType()).thenReturn("ccvpn");

        HttpResponseResult mock = Mockito.mock(HttpResponseResult.class);
        Mockito.when(HttpUtil.sendPostRequestByJson(anyString(), any(Map.class), anyString())).thenReturn(mock);
        when(mock.getResultContent()).thenReturn("{'access point':'','cloud point':'','bandwidth':''}");
        when(intentInstanceService.formatAccessPoint(anyString())).thenReturn("");
        when(intentInstanceService.formatCloudPoint(anyString())).thenReturn("");
        Map<String, Object> predict = intentController.unifyPredict(body);
        JSONObject jsonObject = new JSONObject(predict);


        assertEquals(jsonObject.getString("type"), "ccvpn");
        Assert.assertNotNull(jsonObject.getJSONObject("formData").getJSONObject("accessPointOne").getString("name"));
        Assert.assertNotNull(jsonObject.getJSONObject("formData").getString("cloudPointName"));
    }

    @Test
    public void getInstanceId() {
        assertEquals(intentController.getInstanceId().containsKey("instanceId"), true);
    }
    @Test
    public void getInstanceList() {
        Map<String, Object> body = new HashMap<>();

        body.put("currentPage",1);
        body.put("pageSize",2);
        Page<CCVPNInstance> page = new Page<>();
        CCVPNInstance ccvpnInstance = new CCVPNInstance();
        ccvpnInstance.setAccessPointOneName("xx");
        ccvpnInstance.setCloudPointName("bb");
        page.setList(List.of(ccvpnInstance));
        Mockito.when(intentInstanceService.queryIntentInstance(null,1,2)).thenReturn(page);
        Assert.assertNotNull(intentController.getInstanceList(body));
    }
    @Test
    public void createIntentInstance() throws IOException {
        Map<String, Object> body = new HashMap<>();
        body.put("instanceId","instanceId");
        body.put("name","name");
        body.put("lineNum","lineNum");
        body.put("cloudPointName","cloudPointName");
        Map<String, Object> accessPointOne = new HashMap<>();
        accessPointOne.put("name","name");
        accessPointOne.put("bandwidth","1");
        body.put("accessPointOne",accessPointOne);
        Mockito.when(intentInstanceService.createCCVPNInstance(any())).thenReturn(1);
        assertEquals(intentController.createCCVPNInstance(body), "OK");
    }
    @Test
    public void getFinishedInstanceInfo() {
        List<CCVPNInstance> instanceList = new ArrayList<>();
        CCVPNInstance instance = new CCVPNInstance();
        instance.setInstanceId("instanceId");
        instance.setName("name");
        instanceList.add(instance);
        Mockito.when(intentInstanceService.getFinishedInstanceInfo()).thenReturn(instanceList);
        assertEquals(((List)intentController.getFinishedInstanceInfo()).size(), 1);
    }
    @Test
    public void deleteIntentInstance() {
        Map<String, Object> body = new HashMap<>();
        body.put("instanceId", "instanceId");
        Mockito.doNothing().when(intentInstanceService).deleteIntentInstance(anyString());
        assertEquals(intentController.deleteIntentInstance("instanceId"), "ok");
    }
    @Test
    public void activeIntentInstance() {
        Map<String, Object> body = new HashMap<>();
        body.put("instanceId", "instanceId");
        Mockito.doNothing().when(intentInstanceService).activeIntentInstance(anyString());
        assertEquals(intentController.activeIntentInstance(body), "ok");
    }
    @Test
    public void invalidIntentInstance() {
        Map<String, Object> body = new HashMap<>();
        body.put("instanceId", "instanceId");
        Mockito.doNothing().when(intentInstanceService).invalidIntentInstance(anyString());
        assertEquals(intentController.invalidIntentInstance(body), "ok");
    }
    @Test
    public void queryInstancePerformanceData() {
        Map<String, Object> body = new HashMap<>();
        body.put("instanceId", "instanceId");
        Mockito.when(intentInstanceService.queryInstancePerformanceData(anyString())).thenReturn(body);
        assertEquals(intentController.queryInstancePerformanceData(body), body);
    }
    @Test
    public void queryAccessNodeInfoTest() throws IOException {
        Mockito.when(intentInstanceService.queryAccessNodeInfo()).thenReturn("ok");
        assertEquals(intentController.queryAccessNodeInfo(), "ok");
    }

    @Test
    public void getInstanceStatusTest() {
        Map<String, Object> body = new HashMap<>();
        List<String> ids = new ArrayList<>();
        ids.add("1");
        ids.add("2");
        ids.add("3");
        body.put("ids", ids);
        when(intentInstanceService.getInstanceStatus(any(JSONArray.class))).thenReturn(new JSONObject());
        assertTrue(intentController.getInstanceStatus(body) instanceof JSONObject);
    }
    @Test
    public void loadTest() {
        HttpResponseResult result = Mockito.mock(HttpResponseResult.class);
        PowerMockito.when(HttpUtil.sendPostRequestByJson(anyString(), any(), anyString())).thenReturn(result);
        PowerMockito.when(result.getResultContent()).thenReturn("{\"Status\":\"OK\"}");
        assertEquals(intentService1.load("filename"), "OK");
    }
}