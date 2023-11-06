/*
 * Copyright (C) 2019 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.bean.nsmf.common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class ServiceResultTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testGetServiceResult() throws Exception {

        ServiceResult serviceResult = new ServiceResult();
        serviceResult.getResult_body();
        serviceResult.getResult_header();
    }

    @Test
    public void testSetResultHeader() throws Exception {

        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader("200", "command is ok");
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body("test body");

        ServiceResult serviceResult1 = new ServiceResult(resultHeader, "test body");
    }
}
