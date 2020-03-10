package org.onap.usecaseui.server.service.lcm.domain.aai.bean;

import org.junit.After;
import org.junit.Before;

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

public class PInterfaceTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    public void pinterface() throws  Exception
    {
        PInterface pInterface = new PInterface();
        RelationshipList relationshipList = new RelationshipList();

        pInterface.getInMaint();
        pInterface.getInterfaceName();
        pInterface.getInterfaceType();
        pInterface.getNetworkInterfaceType();
        pInterface.getOperationalStatus();
        pInterface.getNetworkRef();
        pInterface.getPortDescription();
        pInterface.getRelationshipList();
        pInterface.getResourceVersion();
        pInterface.getSpeedUnits();
        pInterface.getSpeedValue();

        pInterface.setInMaint("123");
        pInterface.setInterfaceName("123");
        pInterface.setInterfaceType("123");
        pInterface.setNetworkInterfaceType("123");
        pInterface.setNetworkRef("123");
        pInterface.setOperationalStatus("123");
        pInterface.setRelationshipList(relationshipList);
        pInterface.setPortDescription("123");
        pInterface.setSpeedValue("123");
        pInterface.setSpeedUnits("123");


    }

}
