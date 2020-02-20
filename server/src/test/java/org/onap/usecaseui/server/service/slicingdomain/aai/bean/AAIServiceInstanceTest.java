/*
 * Copyright (C) 2020 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.service.slicingdomain.aai.bean;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.AAIServiceInstance.RelationshipList;

public class AAIServiceInstanceTest {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSetAndGetAAIServiceInstance() throws Exception {
        AAIServiceInstance aaiServiceInstance = new AAIServiceInstance();
        aaiServiceInstance.setServiceType("eMbb");
        aaiServiceInstance.setServiceRole("role");
        aaiServiceInstance.setServiceInstanceName("name001");
        aaiServiceInstance.setServiceInstanceLocationId("id001");
        aaiServiceInstance.setServiceInstanceId("id001");
        aaiServiceInstance.setResourceVersion("ver001");

        AAIServiceInstance.RelationshipList relationshipList;
        aaiServiceInstance.setOrchestrationStatus("processing");
        aaiServiceInstance.setModelVersionId("id0001");
        aaiServiceInstance.setModelInvariantId("id001");
        aaiServiceInstance.setEnvironmentContext("cn");
        aaiServiceInstance.setDescription("des");

        aaiServiceInstance.getDescription();
        aaiServiceInstance.getEnvironmentContext();
        aaiServiceInstance.getModelInvariantId();
        aaiServiceInstance.getModelVersionId();
        aaiServiceInstance.getOrchestrationStatus();
        aaiServiceInstance.getRelationshipList();
        aaiServiceInstance.getResourceVersion();
        aaiServiceInstance.getServiceInstanceId();
        aaiServiceInstance.getServiceInstanceLocationId();
        aaiServiceInstance.getEnvironmentContext();
        aaiServiceInstance.getServiceInstanceName();
        aaiServiceInstance.getServiceRole();
        aaiServiceInstance.getServiceType();
    }

    @Test
    public void testSetAndGetRelationship() throws Exception {
        Relationship relationship = new Relationship();
        List<RelationshipData> relationshipDataList = new ArrayList<>();
        List<RelatedToProperty> relatedToPropertyList = new ArrayList<>();
        relationship.setRelationshipData(relationshipDataList);
        relationship.setRelatedToProperty(relatedToPropertyList);
        relationship.setRelatedTo("related");
        relationship.setRelatedLink("link");
        relationship.setRelationshipLabel("label");

        relationship.getRelatedLink();
        relationship.getRelatedTo();
        relationship.getRelatedToProperty();
        relationship.getRelationshipData();
        relationship.getRelationshipLabel();

    }

    @Test
    public void testSetAndGetRelationshipData() throws Exception {
        RelationshipData relationshipData = new RelationshipData();
        relationshipData.setRelationshipKey("key");
        relationshipData.setRelationshipValue("value");
        relationshipData.getRelationshipKey();
        relationshipData.getRelationshipValue();

    }

    @Test
    public void testSetAndGetRelatedToProperty() throws Exception {
        RelatedToProperty relatedToProperty = new RelatedToProperty();
        relatedToProperty.setPropertyKey("key");
        relatedToProperty.setPropertyValue("value");

        relatedToProperty.getPropertyValue();
        relatedToProperty.getPropertyKey();

    }


}
