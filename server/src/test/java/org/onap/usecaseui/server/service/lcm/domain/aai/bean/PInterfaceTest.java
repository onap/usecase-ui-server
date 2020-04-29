/**
 * Copyright (C) 2020 Huawei, Inc. and others. All rights reserved.
 *===================================================================
 * Modifications Copyright (C) 2020 IBM.
 * ===================================================================
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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
public class PInterfaceTest {

    @Test
    public void testGetMethods() throws  Exception
    {
        PInterface pInterface = new PInterface();
        RelationshipList relationshipList = new RelationshipList();

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
        pInterface.setResourceVersion("1.0.0");

        Assert.assertEquals(pInterface.getInMaint(),"123");
        Assert.assertEquals(pInterface.getInterfaceName(),"123");
        Assert.assertEquals(pInterface.getInterfaceType(),"123");
        Assert.assertEquals(pInterface.getNetworkInterfaceType(),"123");
        Assert.assertEquals(pInterface.getOperationalStatus(),"123");
        Assert.assertEquals(pInterface.getNetworkRef(),"123");
        Assert.assertEquals(pInterface.getPortDescription(),"123");
        Assert.assertNotNull(pInterface.getRelationshipList());
        Assert.assertEquals(pInterface.getResourceVersion(),"1.0.0");
        Assert.assertEquals(pInterface.getSpeedUnits(),"123");
        Assert.assertEquals(pInterface.getSpeedValue(),"123");

    }

}
