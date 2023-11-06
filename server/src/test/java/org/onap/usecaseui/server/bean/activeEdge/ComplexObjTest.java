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
package org.onap.usecaseui.server.bean.activeEdge;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.bean.activateEdge.ComplexObj;
import org.onap.usecaseui.server.bean.activateEdge.RelationshipList;


public class ComplexObjTest {
    RelationshipList relationshipList;
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testGetComplexObj() throws Exception {
        ComplexObj co = new ComplexObj();
        co.getCity();
        co.getPostalCode();
        co.getAdditionalProperties();
        co.getCountry();
        co.getLatitude();
        co.getLongitude();
        co.getPhysicalLocationId();
        co.getPhysicalLocationType();
        co.getRegion();
        co.getRelationshipList();
        co.getResourceVersion();
        co.getStreet1();
        co.toString();
    }

    @Test
    public void testSetComplexObj() throws Exception {
        ComplexObj co = new ComplexObj();
        co.setCity("");
        co.setPostalCode("");
        co.setAdditionalProperty("", "");
        co.setCountry("");
        co.setLatitude("");
        co.setLongitude("");
        co.setPhysicalLocationId("");
        co.setPhysicalLocationType("");
        co.setRegion("");
        co.setRelationshipList(relationshipList);
        co.setResourceVersion("");
        co.setStreet1("");
    }
}
