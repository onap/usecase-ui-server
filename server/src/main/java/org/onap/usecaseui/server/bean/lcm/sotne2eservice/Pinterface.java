/**
 * Copyright 2020 Huawei Corporation.
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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.onap.usecaseui.server.bean.activateEdge.RelationshipList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "interface-name",
        "speed-value",
        "equipment-identifier",
        "resource-version",
        "in-maint",
        "network-ref",
        "transparent",
        "operational-status",
        "speed-units",
        "port-description"
})

public class Pinterface {
    @JsonProperty("interface-name")
    private String interfaceName;
    @JsonProperty("speed-units")
    private String speedUnits;
    @JsonProperty("port-description")
    private String portDescription;
    @JsonProperty("speed-value")
    private String speedValue;
    @JsonProperty("equipment-identifier")
    private String equipmentIdentifier;
    @JsonProperty("resource-version")
    private String resourceVersion;
    @JsonProperty("in-maint")
    private Boolean inMaint;
    @JsonProperty("network-ref")
    private String networkRef;
    @JsonProperty("transparent")
    private String transparent;
    @JsonProperty("operational-status")
    private String operationalStatus;
    @JsonProperty("relationship-list")
    private RelationshipList relationshipList;


    @JsonProperty("interface-name")
    public String getInterfaceName() {
        return interfaceName;
    }

    @JsonProperty("interface-name")
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    @JsonProperty("speed-value")
    public String getSpeedValue() {
        return speedValue;
    }

    @JsonProperty("speed-value")
    public void setSpeedValue(String speedValue) {
        this.speedValue = speedValue;
    }

    @JsonProperty("equipment-identifier")
    public String getEquipmentIdentifier() {
        return equipmentIdentifier;
    }

    @JsonProperty("equipment-identifier")
    public void setEquipmentIdentifier(String equipmentIdentifier) {
        this.equipmentIdentifier = equipmentIdentifier;
    }

    @JsonProperty("resource-version")
    public String getResourceVersion() {
        return resourceVersion;
    }

    @JsonProperty("resource-version")
    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    @JsonProperty("in-maint")
    public Boolean getInMaint() {
        return inMaint;
    }

    @JsonProperty("in-maint")
    public void setInMaint(Boolean inMaint) {
        this.inMaint = inMaint;
    }

    @JsonProperty("network-ref")
    public String getNetworkRef() {
        return networkRef;
    }

    @JsonProperty("network-ref")
    public void setNetworkRef(String networkRef) {
        this.networkRef = networkRef;
    }

    @JsonProperty("transparent")
    public String getTransparent() {
        return transparent;
    }

    @JsonProperty("transparent")
    public void setTransparent(String transparent) {
        this.transparent = transparent;
    }

    @JsonProperty("operational-status")
    public String getOperationalStatus() {
        return operationalStatus;
    }

    @JsonProperty("operational-status")
    public void setOperationalStatus(String operationalStatus) {
        this.operationalStatus = operationalStatus;
    }

    public RelationshipList getRelationshipList() {
        return relationshipList;
    }

    public void setRelationshipList(RelationshipList relationshipList) {
        this.relationshipList = relationshipList;
    }

    @JsonProperty("speed-units")
    public String getSpeedUnits() {
        return speedUnits;
    }

    @JsonProperty("speed-units")
    public void setSpeedUnits(String speedUnits) {
        this.speedUnits = speedUnits;
    }

    @JsonProperty("port-description")
    public String getPortDescription() {
        return portDescription;
    }

    @JsonProperty("port-description")
    public void setPortDescription(String portDescription) {
        this.portDescription = portDescription;
    }
}
