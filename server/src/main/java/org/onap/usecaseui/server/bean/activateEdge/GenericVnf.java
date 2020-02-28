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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "vnf-id",
        "vnf-instance-id",
        "vnf-name",
        "vnf-type",
        "orchestration-status",
        "in-maint",
        "is-closed-loop-disabled",
        "resource-version",
        "relationship-list"
})
public class GenericVnf {
    @JsonProperty("vnf-id")
    private String vnfId;
    @JsonProperty("vnf-instance-id")
    private String vnfInstanceId;
    @JsonProperty("vnf-name")
    private String vnfName;
    @JsonProperty("vnf-type")
    private String vnfType;
    @JsonProperty("orchestration-status")
    private String orchestrationStatus;
    @JsonProperty("in-maint")
    private Boolean inMaint;
    @JsonProperty("is-closed-loop-disabled")
    private Boolean isClosedLoopDisabled;
    @JsonProperty("resource-version")
    private String resourceVersion;
    @JsonProperty("relationship-list")
    private RelationshipList relationshipList;

    @JsonProperty("vnf-id")
    public String getVnfId() {
        return vnfId;
    }

    @JsonProperty("vnf-id")
    public void setVnfId(String vnfId) {
        this.vnfId = vnfId;
    }

    @JsonProperty("vnf-instance-id")
    public String getVnfInstanceId() {
        return vnfInstanceId;
    }

    @JsonProperty("vnf-instance-id")
    public void setVnfInstanceId(String vnfInstanceId) {
        this.vnfInstanceId = vnfInstanceId;
    }

    @JsonProperty("vnf-name")
    public String getVnfName() {
        return vnfName;
    }

    @JsonProperty("vnf-name")
    public void setVnfName(String vnfName) {
        this.vnfName = vnfName;
    }

    @JsonProperty("vnf-type")
    public String getVnfType() {
        return vnfType;
    }

    @JsonProperty("vnf-type")
    public void setVnfType(String vnfType) {
        this.vnfType = vnfType;
    }

    @JsonProperty("orchestration-status")
    public String getOrchestrationStatus() {
        return orchestrationStatus;
    }

    @JsonProperty("orchestration-status")
    public void setOrchestrationStatus(String orchestrationStatus) {
        this.orchestrationStatus = orchestrationStatus;
    }

    @JsonProperty("in-maint")
    public Boolean getInMaint() {
        return inMaint;
    }

    @JsonProperty("in-maint")
    public void setInMaint(Boolean inMaint) {
        this.inMaint = inMaint;
    }

    @JsonProperty("is-closed-loop-disabled")
    public Boolean getIsClosedLoopDisabled() {
        return isClosedLoopDisabled;
    }

    @JsonProperty("is-closed-loop-disabled")
    public void setIsClosedLoopDisabled(Boolean isClosedLoopDisabled) {
        this.isClosedLoopDisabled = isClosedLoopDisabled;
    }

    @JsonProperty("resource-version")
    public String getResourceVersion() {
        return resourceVersion;
    }

    @JsonProperty("resource-version")
    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    @JsonProperty("relationship-list")
    public RelationshipList getRelationshipList() {
        return relationshipList;
    }

    @JsonProperty("relationship-list")
    public void setRelationshipList(RelationshipList relationshipList) {
        this.relationshipList = relationshipList;
    }
}
