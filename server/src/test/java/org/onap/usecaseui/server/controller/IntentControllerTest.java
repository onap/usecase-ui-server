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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.onap.usecaseui.server.bean.HttpResponseResult;
import org.onap.usecaseui.server.bean.intent.IntentInstance;
import org.onap.usecaseui.server.bean.intent.IntentModel;
import org.onap.usecaseui.server.service.intent.IntentInstanceService;
import org.onap.usecaseui.server.service.intent.IntentService;
import org.onap.usecaseui.server.util.HttpUtil;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpUtil.class})
public class IntentControllerTest {

    public IntentControllerTest(){}

    @InjectMocks
    private IntentController intentController;

    @Mock
    @Resource(name = "IntentService")
    private IntentService intentService;

    @Mock
    private IntentInstanceService intentInstanceService;

    @Before
    public void before() throws IllegalAccessException {
        MemberModifier.field(IntentController.class, "intentService").set(intentController , intentService);
        MemberModifier.field(IntentController.class, "intentInstanceService").set(intentController , intentInstanceService);

    }

    @Test
    public void activeModelTest() {
        IntentModel model = new IntentModel();
        String path = "path";
        String modelId = "1";
        when(intentService.activeModel(anyString())).thenReturn(model);
        when(intentService.activeModelFile(model)).thenReturn(path);

        HttpResponseResult mock = PowerMockito.mock(HttpResponseResult.class);
        PowerMockito.mockStatic(HttpUtil.class);
        Mockito.when(HttpUtil.sendPostRequestByJson(anyString(), any(Map.class), anyString())).thenReturn(mock);
        when(mock.getResultContent()).thenReturn("{'Status':'Success'}");

        assertEquals(intentController.activeModel(modelId), "1");
    }

    @Test
    public void deleteModelTest() throws Exception {
        String modelId = "1";
        IntentModel model = new IntentModel();
        model.setModelName("filename.zip");
        when(intentService.getModel(anyString())).thenReturn(model);
        when(intentService.deleteModel(anyString())).thenReturn("1");

        File file=PowerMockito.mock(File.class);
        PowerMockito.whenNew(File.class).withArguments(Mockito.anyString()).thenReturn(file);
        PowerMockito.when(file.exists()).thenReturn(true);
        PowerMockito.when(file.delete()).thenReturn(true);

        assertEquals(intentController.deleteModel(modelId), "1");

    }

    @Test
    public void predictTest() throws ParseException {
        Map<String,Object> body = new HashMap<>();
        body.put("text", "text");
        String respContent = "";
        HttpResponseResult mock = PowerMockito.mock(HttpResponseResult.class);
        PowerMockito.mockStatic(HttpUtil.class);
        Mockito.when(HttpUtil.sendPostRequestByJson(anyString(), any(Map.class), anyString())).thenReturn(mock);
        when(mock.getResultContent()).thenReturn("{'Region':'chengnan'}");
        when(intentService.calcFieldValue(anyString(), anyString())).thenReturn("Beijing Changping District Chengnan Street");
        String predict = intentController.predict(body);
        JSONObject jsonObject = JSON.parseObject(predict);

        assertEquals(jsonObject.getString("coverageArea"), "Beijing Changping District Chengnan Street");
    }

    @Test
    public void tranlateFieldNameTest() throws InvocationTargetException, IllegalAccessException {
        String key = "Region";
        IntentController spy = PowerMockito.spy(intentController);
        Method method = PowerMockito.method(IntentController.class, "tranlateFieldName", String.class);
        Object result = method.invoke(spy, key);
        assertEquals(result, "coverageArea");
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
        Mockito.when(intentInstanceService.queryIntentInstance(null,1,2)).thenReturn(null);
        assertEquals(intentController.getInstanceList(body), null);
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
        Mockito.when(intentInstanceService.createIntentInstance(any())).thenReturn(1);
        assertEquals(intentController.createIntentInstance(body), "OK");
    }
    @Test
    public void getFinishedInstanceInfo() {
        List<IntentInstance> instanceList = new ArrayList<>();
        IntentInstance instance = new IntentInstance();
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
}