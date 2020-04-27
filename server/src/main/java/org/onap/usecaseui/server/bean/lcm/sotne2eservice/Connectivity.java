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
        "connectivity-id",
        "bandwidth-profile-name",
        "cir",
        "eir",
        "cbs",
        "ebs",
        "color-aware",
        "coupling-flag",
        "etht-svc-name",
        "access-provider-id",
        "access-client-id",
        "access-topology-id",
        "access-node-id",
        "access-ltp-id",
        "connectivity-selflink",
        "operational-status",
        "model-customization-id",
        "model-invariant-id",
        "model-version-id",
        "resource-version"
})

public class Connectivity {

        @JsonProperty("connectivity-id")
        private String connectivityId;
        @JsonProperty("bandwidth-profile-name")
        private String bandwidthProfileName;
        @JsonProperty("cir")
        private String cir;
        @JsonProperty("eir")
        private String eir;
        @JsonProperty("cbs")
        private String cbs;
        @JsonProperty("ebs")
        private String ebs;
        @JsonProperty("color-aware")
        private String colorAware;
        @JsonProperty("coupling-flag")
        private String couplingFlag;
        @JsonProperty("etht-svc-name")
        private String ethtSvcName;
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
        @JsonProperty("connectivity-selflink")
        private String connectivitySelflink;
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

        @JsonProperty("connectivity-id")
        public String getConnectivityId() {
            return connectivityId;
        }

        @JsonProperty("connectivity-id")
        public void setConnectivityId(String connectivityId) {
            this.connectivityId = connectivityId;
        }

        @JsonProperty("bandwidth-profile-name")
        public String getBandwidthProfileName() {
            return bandwidthProfileName;
        }

        @JsonProperty("bandwidth-profile-name")
        public void setBandwidthProfileName(String bandwidthProfileName) {
            this.bandwidthProfileName = bandwidthProfileName;
        }

        @JsonProperty("cir")
        public String getCir() {
            return cir;
        }

        @JsonProperty("cir")
        public void setCir(String cir) {
            this.cir = cir;
        }

        @JsonProperty("eir")
        public String getEir() {
            return eir;
        }

        @JsonProperty("eir")
        public void setEir(String eir) {
            this.eir = eir;
        }

        @JsonProperty("cbs")
        public String getCbs() {
            return cbs;
        }

        @JsonProperty("cbs")
        public void setCbs(String cbs) {
            this.cbs = cbs;
        }

        @JsonProperty("ebs")
        public String getEbs() {
            return ebs;
        }

        @JsonProperty("ebs")
        public void setEbs(String ebs) {
            this.ebs = ebs;
        }

        @JsonProperty("color-aware")
        public String getColorAware() {
            return colorAware;
        }

        @JsonProperty("color-aware")
        public void setColorAware(String colorAware) {
            this.colorAware = colorAware;
        }

        @JsonProperty("coupling-flag")
        public String getCouplingFlag() {
            return couplingFlag;
        }

        @JsonProperty("coupling-flag")
        public void setCouplingFlag(String couplingFlag) {
            this.couplingFlag = couplingFlag;
        }

        @JsonProperty("etht-svc-name")
        public String getEthtSvcName() {
            return ethtSvcName;
        }

        @JsonProperty("etht-svc-name")
        public void setEthtSvcName(String ethtSvcName) {
            this.ethtSvcName = ethtSvcName;
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

        @JsonProperty("connectivity-selflink")
        public String getConnectivitySelflink() {
            return connectivitySelflink;
        }

        @JsonProperty("connectivity-selflink")
        public void setConnectivitySelflink(String connectivitySelflink) {
            this.connectivitySelflink = connectivitySelflink;
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

    @Override
    public String toString() {
        return "{" +
                "\"connectivityId\":\"" + connectivityId + '\"' +
                ", \"bandwidthProfileName\":\"" + bandwidthProfileName + '\"' +
                ", \"cir\":\"" + cir + '\"' +
                ", \"eir\":\"" + eir + '\"' +
                ", \"cbs\":\"" + cbs + '\"' +
                ", \"ebs\":\"" + ebs + '\"' +
                ", \"colorAware\":\"" + colorAware + '\"' +
                ", \"couplingFlag\":\"" + couplingFlag + '\"' +
                ", \"ethtSvcName\":\"" + ethtSvcName + '\"' +
                ", \"accessProviderId\":\"" + accessProviderId + '\"' +
                ", \"accessClientId\":\"" + accessClientId + '\"' +
                ", \"accessTopologyId\":\"" + accessTopologyId + '\"' +
                ", \"accessNodeId\":\"" + accessNodeId + '\"' +
                ", \"accessLtpId\":\"" + accessLtpId + '\"' +
                ", \"connectivitySelflink\":\"" + connectivitySelflink + '\"' +
                ", \"operationalStatus\":\"" + operationalStatus + '\"' +
                ", \"modelCustomizationId\":\"" + modelCustomizationId + '\"' +
                ", \"modelInvariantId\":\"" + modelInvariantId + '\"' +
                ", \"modelVersionId\":\"" + modelVersionId + '\"' +
                ", \"resourceVersion\":\"" + resourceVersion + '\"' +
                '}';
    }


}
