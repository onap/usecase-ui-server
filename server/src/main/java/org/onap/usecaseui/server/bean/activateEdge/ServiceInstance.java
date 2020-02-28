/*
 * Copyright (C) 2017 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.bean.activateEdge;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "service-instance-id",
        "service-instance-name",
        "service-type",
        "service-role",
        "resource-version",
        "selflink",
        "relationship-list"
})

public class ServiceInstance {
    @JsonProperty("service-instance-id")
    private String serviceInstanceId;
    @JsonProperty("service-instance-name")
    private String serviceInstanceName;
    @JsonProperty("service-type")
    private String serviceType;
    @JsonProperty("service-role")
    private String serviceRole;
    @JsonProperty("resource-version")
    private String resourceVersion;
    @JsonProperty("selflink")
    private String selflink;
    @JsonProperty("orchestration-status")
    private String orchestrationstatus;
    @JsonProperty("input-parameters")
    private String inputparameters;
    @JsonProperty("relationship-list")
    private RelationshipList relationshipList;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("service-instance-id")
    public String getServiceInstanceId() {
        return serviceInstanceId;
    }

    @JsonProperty("service-instance-id")
    public void setServiceInstanceId(String serviceInstanceId) {
        this.serviceInstanceId = serviceInstanceId;
    }

    @JsonProperty("service-instance-name")
    public String getServiceInstanceName() {
        return serviceInstanceName;
    }

    @JsonProperty("service-instance-name")
    public void setServiceInstanceName(String serviceInstanceName) {
        this.serviceInstanceName = serviceInstanceName;
    }

    @JsonProperty("service-type")
    public String getServiceType() {
        return serviceType;
    }

    @JsonProperty("service-type")
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @JsonProperty("service-role")
    public String getServiceRole() {
        return serviceRole;
    }

    @JsonProperty("service-role")
    public void setServiceRole(String serviceRole) {
        this.serviceRole = serviceRole;
    }

    @JsonProperty("resource-version")
    public String getResourceVersion() {
        return resourceVersion;
    }

    @JsonProperty("resource-version")
    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    @JsonProperty("selflink")
    public String getSelflink() {
        return selflink;
    }

    @JsonProperty("selflink")
    public void setSelflink(String selflink) {
        this.selflink = selflink;
    }

    @JsonProperty("relationship-list")
    public RelationshipList getRelationshipList() {
        return relationshipList;
    }

    @JsonProperty("relationship-list")
    public void setRelationshipList(RelationshipList relationshipList) {
        this.relationshipList = relationshipList;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String getOrchestrationstatus() {
        return orchestrationstatus;
    }

    public void setOrchestrationstatus(String orchestrationstatus) {
        this.orchestrationstatus = orchestrationstatus;
    }

    public String getInputparameters() {
        return inputparameters;
    }

    public void setInputparameters(String inputparameters) {
        this.inputparameters = inputparameters;
    }
}
