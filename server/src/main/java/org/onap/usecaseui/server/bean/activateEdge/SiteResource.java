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
package org.onap.usecaseui.server.bean.activateEdge;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "site-resource-id",
        "site-resource-name",
        "description",
        "type",
        "role",
        "selflink",
        "operational-status",
        "resource-version",
        "relationship-list"
})

public class SiteResource {
    @JsonProperty("site-resource-id")
    private String siteResourceId;
    @JsonProperty("site-resource-name")
    private String siteResourceName;
    @JsonProperty("description")
    private String description;
    @JsonProperty("type")
    private String type;
    @JsonProperty("role")
    private String role;
    @JsonProperty("selflink")
    private String selflink;
    @JsonProperty("operational-status")
    private String operationalStatus;
    @JsonProperty("resource-version")
    private String resourceVersion;
    @JsonProperty("relationship-list")
    private RelationshipList relationshipList;



    // Added by Isaac for handling city and postal code : Begin
    @JsonProperty("city")
    private String city;
    @JsonProperty("postal-code")
    private String postalcode;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }
    // Added by Isaac for handling city and postal code : End

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("site-resource-id")
    public String getSiteResourceId() {
        return siteResourceId;
    }

    @JsonProperty("site-resource-id")
    public void setSiteResourceId(String siteResourceId) {
        this.siteResourceId = siteResourceId;
    }

    @JsonProperty("site-resource-name")
    public String getSiteResourceName() {
        return siteResourceName;
    }

    @JsonProperty("site-resource-name")
    public void setSiteResourceName(String siteResourceName) {
        this.siteResourceName = siteResourceName;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("role")
    public String getRole() {
        return role;
    }

    @JsonProperty("role")
    public void setRole(String role) {
        this.role = role;
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

    @Override
    public String toString() {
        return "{" +
                "\"site-resource-id\":\"" + siteResourceId + '\"' +
                ", \"site-resource-name\":\"" + siteResourceName + '\"' +
                ", \"description\":\"" + description + '\"' +
                ", \"type\":\"" + type + '\"' +
                ", \"role\":\"" + role + '\"' +
                ", \"selflink\":\"" + selflink + '\"' +
                ", \"operational-status\":\"" + operationalStatus + '\"' +
                ", \"resource-version\":\"" + resourceVersion + '\"' +
                ", \"relationship-list\":" + relationshipList +
                '}';
    }


}
