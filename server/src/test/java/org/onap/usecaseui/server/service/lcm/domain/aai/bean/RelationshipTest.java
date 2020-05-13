/**
 * Copyright (C) 2020 Huawei, Inc. and others. All rights reserved.
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
import static org.junit.Assert.assertNotNull;

public class RelationshipTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void relationshiptest() throws  Exception
    {
        Relationship relationship = new Relationship();
        RelationshipData relationshipData = new RelationshipData();
        relationshipData.setRelationshipKey("123");
        relationshipData.setRelationshipValue("123");
        RelationshipData[] rd=new RelationshipData[]{relationshipData};
        relationship.setRelationshipData(rd);
        relationship.setRelatedLink("123");
        relationship.setRelatedTo("123");
        relationship.setRelationshipLabel("123");

        assertEquals("123",relationship.getRelatedLink());
        assertEquals("123",relationship.getRelatedTo());
        assertNotNull(relationship.getRelationshipData());
        assertEquals("123",relationship.getRelationshipLabel());




    }

}


