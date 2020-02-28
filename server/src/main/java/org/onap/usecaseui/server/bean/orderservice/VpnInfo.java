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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class VpnInfo {
    @JsonProperty("vpnName")
    private String vpnName;

    @JsonProperty("vpnId")
    private String vpnId;

    @JsonProperty("vpnType")
    private String vpnType;

    @JsonProperty("vpnBandwidth")
    private String vpnBandwidth;

    @JsonProperty("vpnThreshold")
    private String vpnThreshold;

    @JsonProperty("cameras")
    private String cameras;

    @JsonProperty("vpnCost")
    private String vpnCost;

    @JsonProperty("totalCost")
    private String totalCost;

    @JsonProperty("sites")
    private List<Site> sites;


    public String getVpnName() {
        return vpnName;
    }

    public void setVpnName(String vpnName) {
        this.vpnName = vpnName;
    }

    public String getVpnId() {
        return vpnId;
    }

    public void setVpnId(String vpnId) {
        this.vpnId = vpnId;
    }

    public String getVpnType() {
        return vpnType;
    }

    public void setVpnType(String vpnType) {
        this.vpnType = vpnType;
    }

    public String getVpnBandwidth() {
        return vpnBandwidth;
    }

    public void setVpnBandwidth(String vpnBandwidth) {
        this.vpnBandwidth = vpnBandwidth;
    }

    public String getVpnThreshold() {
        return vpnThreshold;
    }

    public void setVpnThreshold(String vpnThreshold) {
        this.vpnThreshold = vpnThreshold;
    }

    public String getCameras() {
        return cameras;
    }

    public void setCameras(String cameras) {
        this.cameras = cameras;
    }

    public String getVpnCost() {
        return vpnCost;
    }

    public void setVpnCost(String vpnCost) {
        this.vpnCost = vpnCost;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public List<Site> getSites() {
        return sites;
    }

    public void setSites(List<Site> sites) {
        sites = sites;
    }

    @Override
    public String toString() {
        return "VpnInfo{" +
                "vpnName='" + vpnName + '\'' +
                ", vpnId='" + vpnId + '\'' +
                ", vpnType='" + vpnType + '\'' +
                ", vpnBandwidth='" + vpnBandwidth + '\'' +
                ", vpnThreshold='" + vpnThreshold + '\'' +
                ", cameras='" + cameras + '\'' +
                ", vpnCost='" + vpnCost + '\'' +
                ", totalCost='" + totalCost + '\'' +
                ", sites=" + sites +
                '}';
    }
}
