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
        "vpn-id",
        "vpn-name",
        "vpn-type",
        "vpn-region",
        "access-provider-id",
        "access-client-id",
        "access-topology-id",
        "src-access-node-id",
        "src-access-ltp-id",
        "dst-access-node-id",
        "dst-access-ltp-id",
        "operational-status",
        "resource-version"
})
public class VpnInformation {
    @JsonProperty("vpn-id")
    private String vpnId;
    @JsonProperty("vpn-name")
    private String vpnName;
    @JsonProperty("vpn-type")
    private String vpnType;
    @JsonProperty("vpn-region")
    private String vpnRegion;
    @JsonProperty("access-provider-id")
    private String accessProviderId;
    @JsonProperty("access-client-id")
    private String accessClientId;
    @JsonProperty("access-topology-id")
    private String accessTopologyId;
    @JsonProperty("src-access-node-id")
    private String srcAccessNodeId;
    @JsonProperty("src-access-ltp-id")
    private String srcAccessLtpId;
    @JsonProperty("dst-access-node-id")
    private String dstAccessNodeId;
    @JsonProperty("dst-access-ltp-id")
    private String dstAccessLtpId;
    @JsonProperty("operational-status")
    private String operationalStatus;
    @JsonProperty("resource-version")
    private String resourceVersion;
    @JsonProperty("relationship-list")
    private RelationshipList relationshipList;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("vpn-id")
    public String getVpnId() {
        return vpnId;
    }

    @JsonProperty("vpn-id")
    public void setVpnId(String vpnId) {
        this.vpnId = vpnId;
    }

    @JsonProperty("vpn-name")
    public String getVpnName() {
        return vpnName;
    }

    @JsonProperty("vpn-name")
    public void setVpnName(String vpnName) {
        this.vpnName = vpnName;
    }

    @JsonProperty("vpn-type")
    public String getVpnType() {
        return vpnType;
    }

    @JsonProperty("vpn-type")
    public void setVpnType(String vpnType) {
        this.vpnType = vpnType;
    }

    @JsonProperty("vpn-region")
    public String getVpnRegion() {
        return vpnRegion;
    }

    @JsonProperty("vpn-region")
    public void setVpnRegion(String vpnRegion) {
        this.vpnRegion = vpnRegion;
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

    @JsonProperty("src-access-node-id")
    public String getSrcAccessNodeId() {
        return srcAccessNodeId;
    }

    @JsonProperty("src-access-node-id")
    public void setSrcAccessNodeId(String srcAccessNodeId) {
        this.srcAccessNodeId = srcAccessNodeId;
    }

    @JsonProperty("src-access-ltp-id")
    public String getSrcAccessLtpId() {
        return srcAccessLtpId;
    }

    @JsonProperty("src-access-ltp-id")
    public void setSrcAccessLtpId(String srcAccessLtpId) {
        this.srcAccessLtpId = srcAccessLtpId;
    }

    @JsonProperty("dst-access-node-id")
    public String getDstAccessNodeId() {
        return dstAccessNodeId;
    }

    @JsonProperty("dst-access-node-id")
    public void setDstAccessNodeId(String dstAccessNodeId) {
        this.dstAccessNodeId = dstAccessNodeId;
    }

    @JsonProperty("dst-access-ltp-id")
    public String getDstAccessLtpId() {
        return dstAccessLtpId;
    }

    @JsonProperty("dst-access-ltp-id")
    public void setDstAccessLtpId(String dstAccessLtpId) {
        this.dstAccessLtpId = dstAccessLtpId;
    }

    @JsonProperty("operational-status")
    public String getOperationalStatus() {
        return operationalStatus;
    }

    @JsonProperty("operational-status")
    public void setOperationalStatus(String operationalStatus) {
        this.operationalStatus = operationalStatus;
    }

    @JsonProperty("resource-version")
    public String getResourceVersion() {
        return resourceVersion;
    }

    @JsonProperty("resource-version")
    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public RelationshipList getRelationshipList() {
        return relationshipList;
    }

    public void setRelationshipList(RelationshipList relationshipList) {
        this.relationshipList = relationshipList;
    }
}
