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
        "devicevf_device_class",
        "devicevf_device_esn",
        "devicevf_device_name",
        "devicevf_device_systemIp",
        "devicevf_device_type",
        "devicevf_device_vendor",
        "devicevf_device_version"
})
public class Sdwandevice {
    @JsonProperty("devicevf_device_class")
    private String devicevfDeviceClass;
    @JsonProperty("devicevf_device_esn")
    private String devicevfDeviceEsn;
    @JsonProperty("devicevf_device_name")
    private String devicevfDeviceName;
    @JsonProperty("devicevf_device_systemIp")
    private String devicevfDeviceSystemIp;
    @JsonProperty("devicevf_device_type")
    private String devicevfDeviceType;
    @JsonProperty("devicevf_device_vendor")
    private String devicevfDeviceVendor;
    @JsonProperty("devicevf_device_version")
    private String devicevfDeviceVersion;

    @JsonProperty("devicevf_device_class")
    public String getDevicevfDeviceClass() {
        return devicevfDeviceClass;
    }

    @JsonProperty("devicevf_device_class")
    public void setDevicevfDeviceClass(String devicevfDeviceClass) {
        this.devicevfDeviceClass = devicevfDeviceClass;
    }

    @JsonProperty("devicevf_device_esn")
    public String getDevicevfDeviceEsn() {
        return devicevfDeviceEsn;
    }

    @JsonProperty("devicevf_device_esn")
    public void setDevicevfDeviceEsn(String devicevfDeviceEsn) {
        this.devicevfDeviceEsn = devicevfDeviceEsn;
    }

    @JsonProperty("devicevf_device_name")
    public String getDevicevfDeviceName() {
        return devicevfDeviceName;
    }

    @JsonProperty("devicevf_device_name")
    public void setDevicevfDeviceName(String devicevfDeviceName) {
        this.devicevfDeviceName = devicevfDeviceName;
    }

    @JsonProperty("devicevf_device_systemIp")
    public String getDevicevfDeviceSystemIp() {
        return devicevfDeviceSystemIp;
    }

    @JsonProperty("devicevf_device_systemIp")
    public void setDevicevfDeviceSystemIp(String devicevfDeviceSystemIp) {
        this.devicevfDeviceSystemIp = devicevfDeviceSystemIp;
    }

    @JsonProperty("devicevf_device_type")
    public String getDevicevfDeviceType() {
        return devicevfDeviceType;
    }

    @JsonProperty("devicevf_device_type")
    public void setDevicevfDeviceType(String devicevfDeviceType) {
        this.devicevfDeviceType = devicevfDeviceType;
    }

    @JsonProperty("devicevf_device_vendor")
    public String getDevicevfDeviceVendor() {
        return devicevfDeviceVendor;
    }

    @JsonProperty("devicevf_device_vendor")
    public void setDevicevfDeviceVendor(String devicevfDeviceVendor) {
        this.devicevfDeviceVendor = devicevfDeviceVendor;
    }

    @JsonProperty("devicevf_device_version")
    public String getDevicevfDeviceVersion() {
        return devicevfDeviceVersion;
    }

    @JsonProperty("devicevf_device_version")
    public void setDevicevfDeviceVersion(String devicevfDeviceVersion) {
        this.devicevfDeviceVersion = devicevfDeviceVersion;
    }
}
