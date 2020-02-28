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
        "sitewanportvf_sitewanport_description",
        "sitewanportvf_sitewanport_deviceName",
        "sitewanportvf_sitewanport_inputBandwidth",
        "sitewanportvf_sitewanport_ipAddress",
        "sitewanportvf_sitewanport_name",
        "sitewanportvf_sitewanport_outputBandwidth",
        "sitewanportvf_sitewanport_portNumber",
        "sitewanportvf_sitewanport_portType",
        "sitewanportvf_sitewanport_providerIpAddress",
        "sitewanportvf_sitewanport_transportNetworkName"
})
public class Sitewanport {
    @JsonProperty("sitewanportvf_sitewanport_description")
    private String sitewanportvfSitewanportDescription;
    @JsonProperty("sitewanportvf_sitewanport_deviceName")
    private String sitewanportvfSitewanportDeviceName;
    @JsonProperty("sitewanportvf_sitewanport_inputBandwidth")
    private String sitewanportvfSitewanportInputBandwidth;
    @JsonProperty("sitewanportvf_sitewanport_ipAddress")
    private String sitewanportvfSitewanportIpAddress;
    @JsonProperty("sitewanportvf_sitewanport_name")
    private String sitewanportvfSitewanportName;
    @JsonProperty("sitewanportvf_sitewanport_outputBandwidth")
    private String sitewanportvfSitewanportOutputBandwidth;
    @JsonProperty("sitewanportvf_sitewanport_portNumber")
    private String sitewanportvfSitewanportPortNumber;
    @JsonProperty("sitewanportvf_sitewanport_portType")
    private String sitewanportvfSitewanportPortType;
    @JsonProperty("sitewanportvf_sitewanport_providerIpAddress")
    private String sitewanportvfSitewanportProviderIpAddress;
    @JsonProperty("sitewanportvf_sitewanport_transportNetworkName")
    private String sitewanportvfSitewanportTransportNetworkName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("sitewanportvf_sitewanport_description")
    public String getSitewanportvfSitewanportDescription() {
        return sitewanportvfSitewanportDescription;
    }

    @JsonProperty("sitewanportvf_sitewanport_description")
    public void setSitewanportvfSitewanportDescription(String sitewanportvfSitewanportDescription) {
        this.sitewanportvfSitewanportDescription = sitewanportvfSitewanportDescription;
    }

    @JsonProperty("sitewanportvf_sitewanport_deviceName")
    public String getSitewanportvfSitewanportDeviceName() {
        return sitewanportvfSitewanportDeviceName;
    }

    @JsonProperty("sitewanportvf_sitewanport_deviceName")
    public void setSitewanportvfSitewanportDeviceName(String sitewanportvfSitewanportDeviceName) {
        this.sitewanportvfSitewanportDeviceName = sitewanportvfSitewanportDeviceName;
    }

    @JsonProperty("sitewanportvf_sitewanport_inputBandwidth")
    public String getSitewanportvfSitewanportInputBandwidth() {
        return sitewanportvfSitewanportInputBandwidth;
    }

    @JsonProperty("sitewanportvf_sitewanport_inputBandwidth")
    public void setSitewanportvfSitewanportInputBandwidth(String sitewanportvfSitewanportInputBandwidth) {
        this.sitewanportvfSitewanportInputBandwidth = sitewanportvfSitewanportInputBandwidth;
    }

    @JsonProperty("sitewanportvf_sitewanport_ipAddress")
    public String getSitewanportvfSitewanportIpAddress() {
        return sitewanportvfSitewanportIpAddress;
    }

    @JsonProperty("sitewanportvf_sitewanport_ipAddress")
    public void setSitewanportvfSitewanportIpAddress(String sitewanportvfSitewanportIpAddress) {
        this.sitewanportvfSitewanportIpAddress = sitewanportvfSitewanportIpAddress;
    }

    @JsonProperty("sitewanportvf_sitewanport_name")
    public String getSitewanportvfSitewanportName() {
        return sitewanportvfSitewanportName;
    }

    @JsonProperty("sitewanportvf_sitewanport_name")
    public void setSitewanportvfSitewanportName(String sitewanportvfSitewanportName) {
        this.sitewanportvfSitewanportName = sitewanportvfSitewanportName;
    }

    @JsonProperty("sitewanportvf_sitewanport_outputBandwidth")
    public String getSitewanportvfSitewanportOutputBandwidth() {
        return sitewanportvfSitewanportOutputBandwidth;
    }

    @JsonProperty("sitewanportvf_sitewanport_outputBandwidth")
    public void setSitewanportvfSitewanportOutputBandwidth(String sitewanportvfSitewanportOutputBandwidth) {
        this.sitewanportvfSitewanportOutputBandwidth = sitewanportvfSitewanportOutputBandwidth;
    }

    @JsonProperty("sitewanportvf_sitewanport_portNumber")
    public String getSitewanportvfSitewanportPortNumber() {
        return sitewanportvfSitewanportPortNumber;
    }

    @JsonProperty("sitewanportvf_sitewanport_portNumber")
    public void setSitewanportvfSitewanportPortNumber(String sitewanportvfSitewanportPortNumber) {
        this.sitewanportvfSitewanportPortNumber = sitewanportvfSitewanportPortNumber;
    }

    @JsonProperty("sitewanportvf_sitewanport_portType")
    public String getSitewanportvfSitewanportPortType() {
        return sitewanportvfSitewanportPortType;
    }

    @JsonProperty("sitewanportvf_sitewanport_portType")
    public void setSitewanportvfSitewanportPortType(String sitewanportvfSitewanportPortType) {
        this.sitewanportvfSitewanportPortType = sitewanportvfSitewanportPortType;
    }

    @JsonProperty("sitewanportvf_sitewanport_providerIpAddress")
    public String getSitewanportvfSitewanportProviderIpAddress() {
        return sitewanportvfSitewanportProviderIpAddress;
    }

    @JsonProperty("sitewanportvf_sitewanport_providerIpAddress")
    public void setSitewanportvfSitewanportProviderIpAddress(String sitewanportvfSitewanportProviderIpAddress) {
        this.sitewanportvfSitewanportProviderIpAddress = sitewanportvfSitewanportProviderIpAddress;
    }

    @JsonProperty("sitewanportvf_sitewanport_transportNetworkName")
    public String getSitewanportvfSitewanportTransportNetworkName() {
        return sitewanportvfSitewanportTransportNetworkName;
    }

    @JsonProperty("sitewanportvf_sitewanport_transportNetworkName")
    public void setSitewanportvfSitewanportTransportNetworkName(String sitewanportvfSitewanportTransportNetworkName) {
        this.sitewanportvfSitewanportTransportNetworkName = sitewanportvfSitewanportTransportNetworkName;
    }

}
