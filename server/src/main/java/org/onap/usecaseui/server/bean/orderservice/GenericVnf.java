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

package org.onap.usecaseui.server.bean.orderservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.onap.usecaseui.server.bean.activateEdge.RelationshipList;


public class GenericVnf {

    @JsonProperty("vnf-id")
    private String vnfId;

    @JsonProperty("vnf-type")
    private String vnfType;

    @JsonProperty("orchestration-status")
    private String orchestrationStatus;

    @JsonProperty("in-maint")
    private String inMaint;

    @JsonProperty("is-closed-loop-disabled")
    private String isClosedLoopDisabled;

    @JsonProperty("resource-version")
    private String resourceVersion;

    @JsonProperty("relationship-list")
    private RelationshipList relationshipList;

    public String getVnfId() {
        return vnfId;
    }

    public void setVnfId(String vnfId) {
        this.vnfId = vnfId;
    }

    public String getVnfType() {
        return vnfType;
    }

    public void setVnfType(String vnfType) {
        this.vnfType = vnfType;
    }

    public String getOrchestrationStatus() {
        return orchestrationStatus;
    }

    public void setOrchestrationStatus(String orchestrationStatus) {
        this.orchestrationStatus = orchestrationStatus;
    }

    public String getInMaint() {
        return inMaint;
    }

    public void setInMaint(String inMaint) {
        this.inMaint = inMaint;
    }

    public String getIsClosedLoopDisabled() {
        return isClosedLoopDisabled;
    }

    public void setIsClosedLoopDisabled(String isClosedLoopDisabled) {
        this.isClosedLoopDisabled = isClosedLoopDisabled;
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
}
