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
package org.onap.usecaseui.server.bean.lcm.sotne2eservice;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import org.onap.usecaseui.server.bean.activateEdge.RelationshipList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "vnf-id",
        "in-maint",
        "resource-version",
        "vnf-instance-id",
        "vnf-name",
        "vnf-type",
        "is-closed-loop-disabled",
        "relationship-list"
})
public class Vnfs {
    @JsonProperty("vnf-id")
    private String pnfName;
    @JsonProperty("in-maint")
    private Boolean inMaint;
    @JsonProperty("resource-version")
    private String resourceVersion;
    @JsonProperty("vnf-instance-id")
    private String vnfInstanceId;
    @JsonProperty("vnf-name")
    private String vnfName;
    @JsonProperty("vnf-type")
    private String vnfType;
    @JsonProperty("is-closed-loop-disabled")
    private boolean isClosedLoopDisabled;
    @JsonProperty("relationship-list")
    private RelationshipList relationshipList;

    public String getPnfName() {
        return pnfName;
    }

    public void setPnfName(String pnfName) {
        this.pnfName = pnfName;
    }

    public Boolean getInMaint() {
        return inMaint;
    }

    public void setInMaint(Boolean inMaint) {
        this.inMaint = inMaint;
    }

    public String getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    public String getVnfInstanceId() {
        return vnfInstanceId;
    }

    public void setVnfInstanceId(String vnfInstanceId) {
        this.vnfInstanceId = vnfInstanceId;
    }

    public String getVnfName() {
        return vnfName;
    }

    public void setVnfName(String vnfName) {
        this.vnfName = vnfName;
    }

    public String getVnfType() {
        return vnfType;
    }

    public void setVnfType(String vnfType) {
        this.vnfType = vnfType;
    }

    public boolean isClosedLoopDisabled() {
        return isClosedLoopDisabled;
    }

    public void setClosedLoopDisabled(boolean closedLoopDisabled) {
        isClosedLoopDisabled = closedLoopDisabled;
    }

    public RelationshipList getRelationshipList() {
        return relationshipList;
    }

    public void setRelationshipList(RelationshipList relationshipList) {
        this.relationshipList = relationshipList;
    }
}
