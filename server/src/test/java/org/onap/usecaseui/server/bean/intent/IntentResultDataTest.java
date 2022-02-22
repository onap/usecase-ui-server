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

public class IntentResultDataTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetIntentResultData() throws Exception {
        IntentResultData intentResultData = new IntentResultData();
        intentResultData.setCode(123);
        intentResultData.setData(new Object());
        intentResultData.setMessage("123");

        intentResultData.getCode();
        intentResultData.getData();
        intentResultData.getMessage();
        intentResultData.toString();
    }
}
