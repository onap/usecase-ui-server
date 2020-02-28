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
        "sitelanportvf_sitelanport_portNumber",
        "sitelanportvf_sitelanport_deviceName",
        "sitelanportvf_sitelanport_portType",
        "sitelanportvf_sitelanport_vlanId",
        "sitelanportvf_sitelanport_ipAddress"
})
public class Sdwanlan {
    @JsonProperty("sitelanportvf_sitelanport_portNumber")
    private String sitelanportvfSitelanportPortNumber;
    @JsonProperty("sitelanportvf_sitelanport_deviceName")
    private String sitelanportvfSitelanportDeviceName;
    @JsonProperty("sitelanportvf_sitelanport_portType")
    private String sitelanportvfSitelanportPortType;
    @JsonProperty("sitelanportvf_sitelanport_vlanId")
    private String sitelanportvfSitelanportVlanId;
    @JsonProperty("sitelanportvf_sitelanport_ipAddress")
    private String sitelanportvfSitelanportIpAddress;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("sitelanportvf_sitelanport_portNumber")
    public String getSitelanportvfSitelanportPortNumber() {
        return sitelanportvfSitelanportPortNumber;
    }

    @JsonProperty("sitelanportvf_sitelanport_portNumber")
    public void setSitelanportvfSitelanportPortNumber(String sitelanportvfSitelanportPortNumber) {
        this.sitelanportvfSitelanportPortNumber = sitelanportvfSitelanportPortNumber;
    }

    @JsonProperty("sitelanportvf_sitelanport_deviceName")
    public String getSitelanportvfSitelanportDeviceName() {
        return sitelanportvfSitelanportDeviceName;
    }

    @JsonProperty("sitelanportvf_sitelanport_deviceName")
    public void setSitelanportvfSitelanportDeviceName(String sitelanportvfSitelanportDeviceName) {
        this.sitelanportvfSitelanportDeviceName = sitelanportvfSitelanportDeviceName;
    }

    @JsonProperty("sitelanportvf_sitelanport_portType")
    public String getSitelanportvfSitelanportPortType() {
        return sitelanportvfSitelanportPortType;
    }

    @JsonProperty("sitelanportvf_sitelanport_portType")
    public void setSitelanportvfSitelanportPortType(String sitelanportvfSitelanportPortType) {
        this.sitelanportvfSitelanportPortType = sitelanportvfSitelanportPortType;
    }

    @JsonProperty("sitelanportvf_sitelanport_vlanId")
    public String getSitelanportvfSitelanportVlanId() {
        return sitelanportvfSitelanportVlanId;
    }

    @JsonProperty("sitelanportvf_sitelanport_vlanId")
    public void setSitelanportvfSitelanportVlanId(String sitelanportvfSitelanportVlanId) {
        this.sitelanportvfSitelanportVlanId = sitelanportvfSitelanportVlanId;
    }

    @JsonProperty("sitelanportvf_sitelanport_ipAddress")
    public String getSitelanportvfSitelanportIpAddress() {
        return sitelanportvfSitelanportIpAddress;
    }

    @JsonProperty("sitelanportvf_sitelanport_ipAddress")
    public void setSitelanportvfSitelanportIpAddress(String sitelanportvfSitelanportIpAddress) {
        this.sitelanportvfSitelanportIpAddress = sitelanportvfSitelanportIpAddress;
    }
}
