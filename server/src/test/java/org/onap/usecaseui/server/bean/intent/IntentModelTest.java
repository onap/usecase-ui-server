/*
 * Copyright (C) 2021 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.bean.intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IntentModelTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetIntentModel() throws Exception {
        IntentModel intentModel = new IntentModel();
        intentModel.setActive(123);
        intentModel.setCreateTime("2022-02-21");
        intentModel.setFilePath("/opt/dev");
        intentModel.setId(123);
        intentModel.setModelName("modelName");
        intentModel.setModelType("type1");
        intentModel.getModelType();
        intentModel.setModelType("ccvpn");
        intentModel.getModelType();
        intentModel.setSize(new Float(1));

        intentModel.getActive();
        intentModel.getCreateTime();
        intentModel.getFilePath();
        intentModel.getId();
        intentModel.getModelName();
        intentModel.getModelType();
        intentModel.getSize();
    }
}
