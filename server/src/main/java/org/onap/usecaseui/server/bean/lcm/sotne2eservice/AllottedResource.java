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

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.onap.usecaseui.server.bean.activateEdge.RelationshipList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "selflink",
        "model-invariant-id",
        "model-version-id",
        "resource-version",
        "operational-status",
        "access-provider-id",
        "access-client-id",
        "access-topology-id",
        "access-node-id",
        "access-ltp-id",
        "cvlan",
        "vpn-name",
        "relationship-list"
})
public class AllottedResource {

    @JsonProperty("id")
    private String id;
    @JsonProperty("selflink")
    private String selflink;
    @JsonProperty("model-invariant-id")
    private String modelInvariantId;
    @JsonProperty("model-version-id")
    private String modelVersionId;
    @JsonProperty("resource-version")
    private String resourceVersion;
    @JsonProperty("operational-status")
    private String operationalStatus;
    @JsonProperty("access-provider-id")
    private String accessProviderId;
    @JsonProperty("access-client-id")
    private String accessClientId;
    @JsonProperty("access-topology-id")
    private String accessTopologyId;
    @JsonProperty("access-node-id")
    private String accessNodeId;
    @JsonProperty("access-ltp-id")
    private String accessLtpId;
    @JsonProperty("cvlan")
    private String cvlan;
    @JsonProperty("vpn-name")
    private String vpnName;
    @JsonProperty("relationship-list")
    private RelationshipList relationshipList;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("selflink")
    public String getSelflink() {
        return selflink;
    }

    @JsonProperty("selflink")
    public void setSelflink(String selflink) {
        this.selflink = selflink;
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

    @JsonProperty("operational-status")
    public String getOperationalStatus() {
        return operationalStatus;
    }

    @JsonProperty("operational-status")
    public void setOperationalStatus(String operationalStatus) {
        this.operationalStatus = operationalStatus;
    }

    @JsonProperty("access-provider-id")
    public String getAccessProviderId() {
        return accessProviderId;
    }

    @JsonProperty("access-provider-id")
    public void setAccessProviderId(String accessProviderId) {
        this.accessProviderId = accessProviderId;
    }

    @JsonProperty("access-client-id")
    public String getAccessClientId() {
        return accessClientId;
    }

    @JsonProperty("access-client-id")
    public void setAccessClientId(String accessClientId) {
        this.accessClientId = accessClientId;
    }

    @JsonProperty("access-topology-id")
    public String getAccessTopologyId() {
        return accessTopologyId;
    }

    @JsonProperty("access-topology-id")
    public void setAccessTopologyId(String accessTopologyId) {
        this.accessTopologyId = accessTopologyId;
    }

    @JsonProperty("access-node-id")
    public String getAccessNodeId() {
        return accessNodeId;
    }

    @JsonProperty("access-node-id")
    public void setAccessNodeId(String accessNodeId) {
        this.accessNodeId = accessNodeId;
    }

    @JsonProperty("access-ltp-id")
    public String getAccessLtpId() {
        return accessLtpId;
    }

    @JsonProperty("access-ltp-id")
    public void setAccessLtpId(String accessLtpId) {
        this.accessLtpId = accessLtpId;
    }

    @JsonProperty("cvlan")
    public String getCvlan() {
        return cvlan;
    }

    @JsonProperty("cvlan")
    public void setCvlan(String cvlan) {
        this.cvlan = cvlan;
    }

    @JsonProperty("vpn-name")
    public String getVpnName() {
        return vpnName;
    }

    @JsonProperty("vpn-name")
    public void setVpnName(String vpnName) {
        this.vpnName = vpnName;
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
