/**
 * Copyright 2016-2017 ZTE Corporation.
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
        "pnf-name",
        "pnf-id",
        "in-maint",
        "resource-version",
        "admin-status",
        "operational-status",
        "relationship-list"
})
public class Pnf {
    @JsonProperty("pnf-name")
    private String pnfName;
    @JsonProperty("pnf-id")
    private String pnfId;
    @JsonProperty("in-maint")
    private Boolean inMaint;
    @JsonProperty("resource-version")
    private String resourceVersion;
    @JsonProperty("admin-status")
    private String adminStatus;
    @JsonProperty("operational-status")
    private String operationalStatus;
    @JsonProperty("relationship-list")
    private RelationshipList relationshipList;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("pnf-name")
    public String getPnfName() {
        return pnfName;
    }

    @JsonProperty("pnf-name")
    public void setPnfName(String pnfName) {
        this.pnfName = pnfName;
    }

    @JsonProperty("pnf-id")
    public String getPnfId() {
        return pnfId;
    }

    @JsonProperty("pnf-id")
    public void setPnfId(String pnfId) {
        this.pnfId = pnfId;
    }

    @JsonProperty("in-maint")
    public Boolean getInMaint() {
        return inMaint;
    }

    @JsonProperty("in-maint")
    public void setInMaint(Boolean inMaint) {
        this.inMaint = inMaint;
    }

    @JsonProperty("resource-version")
    public String getResourceVersion() {
        return resourceVersion;
    }

    @JsonProperty("resource-version")
    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    @JsonProperty("admin-status")
    public String getAdminStatus() {
        return adminStatus;
    }

    @JsonProperty("admin-status")
    public void setAdminStatus(String adminStatus) {
        this.adminStatus = adminStatus;
    }

    @JsonProperty("operational-status")
    public String getOperationalStatus() {
        return operationalStatus;
    }

    @JsonProperty("operational-status")
    public void setOperationalStatus(String operationalStatus) {
        this.operationalStatus = operationalStatus;
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
