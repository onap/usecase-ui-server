package org.onap.usecaseui.server.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.onap.usecaseui.server.bean.HttpResponseResult;
import org.onap.usecaseui.server.bean.intent.IntentModel;
import org.onap.usecaseui.server.service.intent.IntentService;
import org.onap.usecaseui.server.util.HttpUtil;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({IntentController.class, HttpUtil.class})
class IntentControllerTest {

    public IntentControllerTest(){}

    @InjectMocks
    private IntentController intentController;

    @Mock
    private IntentService intentService;

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
        Method method = PowerMockito.method(IntentController.class, "tranlateFieldName", String.class);//如果多个参数，逗号分隔，然后写参数类型.class
        Object result = method.invoke(spy, key);
        assertEquals(result, "coverageArea");
    }
}