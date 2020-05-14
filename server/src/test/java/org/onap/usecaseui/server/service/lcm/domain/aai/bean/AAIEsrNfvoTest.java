/**
 * Copyright 2020 Huawei Corporation.
 *
 * ================================================================================
 *  Modifications Copyright (C) 2020 IBM.
 * ================================================================================
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

package org.onap.usecaseui.server.service.lcm.domain.aai.bean;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AAIEsrNfvoTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testAAIEsrNfvoTest() throws Exception {
        AAIEsrNfvo aaiEsrNfvo = new AAIEsrNfvo("123", "123", "123", "123");

        aaiEsrNfvo.getApiRoot();
        aaiEsrNfvo.getName();
        aaiEsrNfvo.getNfvoId();
        aaiEsrNfvo.getResourceVersion();

        assertEquals("123",aaiEsrNfvo.getApiRoot());
        assertEquals("123",aaiEsrNfvo.getName());
        assertEquals("123",aaiEsrNfvo.getNfvoId());
        assertEquals("123",aaiEsrNfvo.getResourceVersion());
        aaiEsrNfvo.setName("test");
        assertEquals("test",aaiEsrNfvo.getName());

    }
}
