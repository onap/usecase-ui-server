/**
 * Copyright 2019 HUAWEI Corporation.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.onap.usecaseui.server.service.slicingdomain.aai.bean;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AAIServiceInstance implements Serializable {

    private static final long serialVersionUID = -2847142014162429886L;

    @JsonProperty("service-instance-id")
    private String serviceInstanceId;

    @JsonProperty("service-instance-name")
    private String serviceInstanceName;

    @JsonProperty("service-type")
    private String serviceType;

    @JsonProperty("service-role")
    private String serviceRole;

    @JsonProperty("environment-context")
    private String environmentContext;

    @JsonProperty("description")
    private String description;

    @JsonProperty("model-invariant-id")
    private String modelInvariantId;

    @JsonProperty("model-version-id")
    private String modelVersionId;

    @JsonProperty("resource-version")
    private String resourceVersion;

    @JsonProperty("service-instance-location-id")
    private String serviceInstanceLocationId;

    @JsonProperty("orchestration-status")
    private String orchestrationStatus;

    @JsonProperty("relationship-list")
    public RelationshipList relationshipList;


    public AAIServiceInstance() {
        super();
    }

    public String getEnvironmentContext() {
        return environmentContext;
    }

    public void setEnvironmentContext(String environmentContext) {
        this.environmentContext = environmentContext;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceInstanceId() {
        return serviceInstanceId;
    }

    public void setServiceInstanceId(String serviceInstanceId) {
        this.serviceInstanceId = serviceInstanceId;
    }

    public String getServiceInstanceName() {
        return serviceInstanceName;
    }

    public void setServiceInstanceName(String serviceInstanceName) {
        this.serviceInstanceName = serviceInstanceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceRole() {
        return serviceRole;
    }

    public void setServiceRole(String serviceRole) {
        this.serviceRole = serviceRole;
    }

    public String getServiceInstanceLocationId() {
        return serviceInstanceLocationId;
    }

    public void setServiceInstanceLocationId(String serviceInstanceLocationId) {
        this.serviceInstanceLocationId = serviceInstanceLocationId;
    }

    public String getOrchestrationStatus() {
        return orchestrationStatus;
    }

    public void setOrchestrationStatus(String orchestrationStatus) {
        this.orchestrationStatus = orchestrationStatus;
    }

    public String getModelInvariantId() {
        return modelInvariantId;
    }

    public void setModelInvariantId(String modelInvariantId) {
        this.modelInvariantId = modelInvariantId;
    }

    public String getModelVersionId() {
        return modelVersionId;
    }

    public void setModelVersionId(String modelVersionId) {
        this.modelVersionId = modelVersionId;
    }

    public String getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    public RelationshipList getRelationshipList() {
        return relationshipList;
    }

    public void setRelationshipList(RelationshipList relationshipList) {
        this.relationshipList = relationshipList;
    }


    public class RelationshipList {

        public RelationshipList() {
            super();
        }

        @JsonProperty("relationship")
        public List<Relationship> relationship;

        public List<Relationship> getRelationship() {
            return relationship;
        }

        public void setRelationship(List<Relationship> relationship) {
            this.relationship = relationship;
        }

    }

    public class Relationship {

        public Relationship() {
            super();
        }

        @JsonProperty("related-to")
        private String relatedTo;

        @JsonProperty("relationship-label")
        private String relationshipLabel;

        @JsonProperty("related-link")
        private String relatedLink;

        @JsonProperty("relationship-data")
        private List<RelationshipData> relationshipData;

        @JsonProperty("related-to-property")
        private List<RelatedToProperty> relatedToProperty;

        public String getRelatedTo() {
            return relatedTo;
        }

        public void setRelatedTo(String relatedTo) {
            this.relatedTo = relatedTo;
        }

        public String getRelationshipLabel() {
            return relationshipLabel;
        }

        public void setRelationshipLabel(String relationshipLabel) {
            this.relationshipLabel = relationshipLabel;
        }

        public String getRelatedLink() {
            return relatedLink;
        }

        public void setRelatedLink(String relatedLink) {
            this.relatedLink = relatedLink;
        }

        public List<RelationshipData> getRelationshipData() {
            return relationshipData;
        }

        public void setRelationshipData(List<RelationshipData> relationshipData) {
            this.relationshipData = relationshipData;
        }

        public List<RelatedToProperty> getRelatedToProperty() {
            return relatedToProperty;
        }

        public void setRelatedToProperty(List<RelatedToProperty> relatedToProperty) {
            this.relatedToProperty = relatedToProperty;
        }

    }

    public class RelationshipData {

        @JsonProperty("relationship-key")
        private String relationshipKey;

        @JsonProperty("relationship-value")
        private String relationshipValue;

        public String getRelationshipKey() {
            return relationshipKey;
        }

        public void setRelationshipKey(String relationshipKey) {
            this.relationshipKey = relationshipKey;
        }

        public String getRelationshipValue() {
            return relationshipValue;
        }

        public void setRelationshipValue(String relationshipValue) {
            this.relationshipValue = relationshipValue;
        }

    }

    public class RelatedToProperty {

        @JsonProperty("property-key")
        private String propertyKey;

        @JsonProperty("property-value")
        private String propertyValue;

        public String getPropertyKey() {
            return propertyKey;
        }

        public void setPropertyKey(String propertyKey) {
            this.propertyKey = propertyKey;
        }

        public String getPropertyValue() {
            return propertyValue;
        }

        public void setPropertyValue(String propertyValue) {
            this.propertyValue = propertyValue;
        }

    }

}
