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

public class ServiceInstanceOperationsTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetServiceInstanceOperations() throws Exception {
        ServiceInstanceOperations serviceInstanceOperations = new ServiceInstanceOperations();
        serviceInstanceOperations.setEndTime("123");
        serviceInstanceOperations.setOperationId("123");
        serviceInstanceOperations.setOperationProgress("50%");
        serviceInstanceOperations.setOperationResult("success");
        serviceInstanceOperations.setOperationType("type1");
        serviceInstanceOperations.setServiceInstanceId("123");
        serviceInstanceOperations.setStartTime("123");

        serviceInstanceOperations.getOperationId();
        serviceInstanceOperations.getOperationType();
        serviceInstanceOperations.getEndTime();
        serviceInstanceOperations.getOperationProgress();
        serviceInstanceOperations.getOperationResult();
        serviceInstanceOperations.getServiceInstanceId();
        serviceInstanceOperations.getStartTime();

        ServiceInstanceOperations serviceInstanceOperations2 = new ServiceInstanceOperations("123", "123", "123", "123", "123", "123","123");
        serviceInstanceOperations2.hashCode();
        serviceInstanceOperations2.equals(serviceInstanceOperations);
    }
}
