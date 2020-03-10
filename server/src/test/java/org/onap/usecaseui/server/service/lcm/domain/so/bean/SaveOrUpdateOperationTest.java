package org.onap.usecaseui.server.service.lcm.domain.so.bean;
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
import org.junit.After;
import org.junit.Before;

public class SaveOrUpdateOperationTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    public void saveorupdateoperation () throws Exception
    {
        SaveOrUpdateOperationRsp saveOrUpdateOperationRsp =new SaveOrUpdateOperationRsp();

        saveOrUpdateOperationRsp.getOperationId();
        saveOrUpdateOperationRsp.setOperationId("abc");
    }
}
