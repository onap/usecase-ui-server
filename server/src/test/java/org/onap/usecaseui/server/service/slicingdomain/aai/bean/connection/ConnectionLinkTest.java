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
package org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConnectionLinkTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetConnectionLink() throws Exception {
        ConnectionLink connectionLink = new ConnectionLink();
        connectionLink.setInMaint("main");
        connectionLink.setLinkId("000-111");
        connectionLink.setLinkName("name1");
        connectionLink.setLinkName2("name2");
        connectionLink.setLinkType("type1");
        connectionLink.setRelationshipList(new RelationshipList());
        connectionLink.setResourceVersion("001");
        connectionLink.setServiceFunction("fuc001");

        connectionLink.getLinkId();
        connectionLink.getInMaint();
        connectionLink.getLinkName();
        connectionLink.getLinkName2();
        connectionLink.getLinkType();
        connectionLink.getRelationshipList();
        connectionLink.getResourceVersion();
        connectionLink.getServiceFunction();
    }
}
