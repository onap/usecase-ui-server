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
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "sitevf_site_address",
        "sitevf_site_controlPoint",
        "sitevf_site_description",
        "sitevf_site_emails",
        "sitevf_site_latitude",
        "sitevf_site_longitude",
        "sitevf_site_name",
        "sitevf_site_postcode",
        "sitevf_site_role",
        "sitevf_site_type"
})

public class Sites {
    @JsonProperty("sitevf_site_address")
    private String sitevfSiteAddress;
    @JsonProperty("sitevf_site_controlPoint")
    private String sitevfSiteControlPoint;
    @JsonProperty("sitevf_site_description")
    private String sitevfSiteDescription;
    @JsonProperty("sitevf_site_emails")
    private String sitevfSiteEmails;
    @JsonProperty("sitevf_site_latitude")
    private String sitevfSiteLatitude;
    @JsonProperty("sitevf_site_longitude")
    private String sitevfSiteLongitude;
    @JsonProperty("sitevf_site_name")
    private String sitevfSiteName;
    @JsonProperty("sitevf_site_postcode")
    private String sitevfSitePostcode;
    @JsonProperty("sitevf_site_role")
    private String sitevfSiteRole;
    @JsonProperty("sitevf_site_type")
    private String sitevfSiteType;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("sitevf_site_address")
    public String getSitevfSiteAddress() {
        return sitevfSiteAddress;
    }

    @JsonProperty("sitevf_site_address")
    public void setSitevfSiteAddress(String sitevfSiteAddress) {
        this.sitevfSiteAddress = sitevfSiteAddress;
    }

    @JsonProperty("sitevf_site_controlPoint")
    public String getSitevfSiteControlPoint() {
        return sitevfSiteControlPoint;
    }

    @JsonProperty("sitevf_site_controlPoint")
    public void setSitevfSiteControlPoint(String sitevfSiteControlPoint) {
        this.sitevfSiteControlPoint = sitevfSiteControlPoint;
    }

    @JsonProperty("sitevf_site_description")
    public String getSitevfSiteDescription() {
        return sitevfSiteDescription;
    }

    @JsonProperty("sitevf_site_description")
    public void setSitevfSiteDescription(String sitevfSiteDescription) {
        this.sitevfSiteDescription = sitevfSiteDescription;
    }

    @JsonProperty("sitevf_site_emails")
    public String getSitevfSiteEmails() {
        return sitevfSiteEmails;
    }

    @JsonProperty("sitevf_site_emails")
    public void setSitevfSiteEmails(String sitevfSiteEmails) {
        this.sitevfSiteEmails = sitevfSiteEmails;
    }

    @JsonProperty("sitevf_site_latitude")
    public String getSitevfSiteLatitude() {
        return sitevfSiteLatitude;
    }

    @JsonProperty("sitevf_site_latitude")
    public void setSitevfSiteLatitude(String sitevfSiteLatitude) {
        this.sitevfSiteLatitude = sitevfSiteLatitude;
    }

    @JsonProperty("sitevf_site_longitude")
    public String getSitevfSiteLongitude() {
        return sitevfSiteLongitude;
    }

    @JsonProperty("sitevf_site_longitude")
    public void setSitevfSiteLongitude(String sitevfSiteLongitude) {
        this.sitevfSiteLongitude = sitevfSiteLongitude;
    }

    @JsonProperty("sitevf_site_name")
    public String getSitevfSiteName() {
        return sitevfSiteName;
    }

    @JsonProperty("sitevf_site_name")
    public void setSitevfSiteName(String sitevfSiteName) {
        this.sitevfSiteName = sitevfSiteName;
    }

    @JsonProperty("sitevf_site_postcode")
    public String getSitevfSitePostcode() {
        return sitevfSitePostcode;
    }

    @JsonProperty("sitevf_site_postcode")
    public void setSitevfSitePostcode(String sitevfSitePostcode) {
        this.sitevfSitePostcode = sitevfSitePostcode;
    }

    @JsonProperty("sitevf_site_role")
    public String getSitevfSiteRole() {
        return sitevfSiteRole;
    }

    @JsonProperty("sitevf_site_role")
    public void setSitevfSiteRole(String sitevfSiteRole) {
        this.sitevfSiteRole = sitevfSiteRole;
    }

    @JsonProperty("sitevf_site_type")
    public String getSitevfSiteType() {
        return sitevfSiteType;
    }

    @JsonProperty("sitevf_site_type")
    public void setSitevfSiteType(String sitevfSiteType) {
        this.sitevfSiteType = sitevfSiteType;
    }

}
