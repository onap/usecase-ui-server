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
        "device-id",
        "device-name",
        "description",
        "vendor",
        "type",
        "version",
        "selflink",
        "operational-status",
        "model-customization-id",
        "model-invariant-id",
        "model-version-id",
        "resource-version",
        "relationship-list"
})

public class DeviceInfo {
    @JsonProperty("device-id")
    private String deviceId;
    @JsonProperty("device-name")
    private String deviceName;
    @JsonProperty("description")
    private String description;
    @JsonProperty("vendor")
    private String vendor;
    @JsonProperty("type")
    private String type;
    @JsonProperty("version")
    private String version;
    @JsonProperty("selflink")
    private String selflink;
    @JsonProperty("operational-status")
    private String operationalStatus;
    @JsonProperty("model-customization-id")
    private String modelCustomizationId;
    @JsonProperty("model-invariant-id")
    private String modelInvariantId;
    @JsonProperty("model-version-id")
    private String modelVersionId;
    @JsonProperty("resource-version")
    private String resourceVersion;
    @JsonProperty("relationship-list")
    private RelationshipList relationshipList;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("device-id")
    public String getDeviceId() {
        return deviceId;
    }

    @JsonProperty("device-id")
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @JsonProperty("device-name")
    public String getDeviceName() {
        return deviceName;
    }

    @JsonProperty("device-name")
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("vendor")
    public String getVendor() {
        return vendor;
    }

    @JsonProperty("vendor")
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    @JsonProperty("selflink")
    public String getSelflink() {
        return selflink;
    }

    @JsonProperty("selflink")
    public void setSelflink(String selflink) {
        this.selflink = selflink;
    }

    @JsonProperty("operational-status")
    public String getOperationalStatus() {
        return operationalStatus;
    }

    @JsonProperty("operational-status")
    public void setOperationalStatus(String operationalStatus) {
        this.operationalStatus = operationalStatus;
    }

    @JsonProperty("model-customization-id")
    public String getModelCustomizationId() {
        return modelCustomizationId;
    }

    @JsonProperty("model-customization-id")
    public void setModelCustomizationId(String modelCustomizationId) {
        this.modelCustomizationId = modelCustomizationId;
    }

    @JsonProperty("model-invariant-id")
    public String getModelInvariantId() {
        return modelInvariantId;
    }

    @JsonProperty("model-invariant-id")
    public void setModelInvariantId(String modelInvariantId) {
        this.modelInvariantId = modelInvariantId;
    }

    @JsonProperty("model-version-id")
    public String getModelVersionId() {
        return modelVersionId;
    }

    @JsonProperty("model-version-id")
    public void setModelVersionId(String modelVersionId) {
        this.modelVersionId = modelVersionId;
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
