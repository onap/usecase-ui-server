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
package org.onap.usecaseui.server.bean;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServiceBeanTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetServiceBean() throws Exception {
        ServiceBean serviceBean = new ServiceBean();
        serviceBean.setCustomerId("123");
        serviceBean.setId("123");
        serviceBean.setInvariantUuuid("123");
        serviceBean.setServiceDomain("domain");
        serviceBean.setServiceInstanceId("123");
        serviceBean.setServiceType("type1");
        serviceBean.setUuid("123");

        serviceBean.getCustomerId();
        serviceBean.getId();
        serviceBean.getInvariantUuuid();
        serviceBean.getServiceDomain();
        serviceBean.getServiceInstanceId();
        serviceBean.getServiceType();
        serviceBean.getUuid();

        ServiceBean serviceBean1 = new ServiceBean("123", "123", "123", "123", "123", "123", "123");
    }
}
