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

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.onap.usecaseui.server.bean.intent.IntentModel;
import org.onap.usecaseui.server.util.ZipUtil;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ZipUtil.class})
class IntentServiceImplTest {
    public IntentServiceImplTest(){}


    @InjectMocks
    private IntentServiceImpl intentService;

    @Before
    public void before() throws Exception {
        //doReturn(session).when(sessionFactory,"openSession");
    }


    //public String addModel(IntentModel model)
    /*@Test
    public void addModelTest() throws Exception {
        IntentModel model = new IntentModel();
        model.setId(1);
        Transaction tx = Mockito.mock(Transaction.class);
        doReturn(tx).when(session,"beginTransaction");
        Serializable save = Mockito.mock(Serializable.class);
        Mockito.when(session.save(model)).thenReturn(save);

    }*/
    /*@Test
    public void activeModelFileTest() throws Exception {
        IntentModel model = new IntentModel();
        String filePath = "filePath.zip";
        String parentPath = "parentPath";
        String unzipPath = "filePath";
        model.setFilePath(filePath);

        File file=PowerMockito.mock(File.class);
        PowerMockito.whenNew(File.class).withArguments(Mockito.anyString()).thenReturn(file);
        PowerMockito.when(file.exists()).thenReturn(true);
        PowerMockito.when(file.getParent()).thenReturn(model.getFilePath());

        assertThat(intentService.activeModelFile(model), is(unzipPath));
    }*/
    @Test
    public void activeModelFileModelIsNullTest() throws Exception {
        assertEquals(intentService.activeModelFile(null), null);
    }
    @Test
    public void activeModelFileFilePathIsNullTest() throws Exception {
        IntentModel model = new IntentModel();
        assertEquals(intentService.activeModelFile(model), null);
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
        assertEquals(intentService.calcFieldValue("coverageArea", "zhongguancun"), "Beijing Haidian District Zhongguancun");
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
    public void calcFieldValueKeyIsLatencyTest() {
        assertEquals(intentService.calcFieldValue("latency", "1s"), "200");
    }


    @Test
    public void formatValueForResourcesSharingLevelTest() throws InvocationTargetException, IllegalAccessException {
        String value = "shared";
        IntentServiceImpl spy = PowerMockito.spy(intentService);
        Method method = PowerMockito.method(IntentServiceImpl.class, "formatValueForResourcesSharingLevel", String.class);//如果多个参数，逗号分隔，然后写参数类型.class
        Object result = method.invoke(spy, value);
        assertEquals(result, "shared");
    }
}